package site.kexing.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import site.kexing.Annotation.UserParameter;
import site.kexing.Annotation.AccessLimit;
import site.kexing.pojo.Miaosha_order;
import site.kexing.pojo.User;
import site.kexing.rabbitmq.Provider;
import site.kexing.rabbitmq.msg.MiaoshaMsg;
import site.kexing.redis.key.GoodListKey;
import site.kexing.redis.key.MiaoShaKey;
import site.kexing.redis.key.MiaoShaOrderKey;
import site.kexing.redis.RedisService;
import site.kexing.result.CodeMsg;
import site.kexing.result.Result;
import site.kexing.service.*;
import site.kexing.utils.Md5Utils;
import site.kexing.utils.UUIDUtil;
import site.kexing.vo.GoodsList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MiaoShaController implements InitializingBean {
    @Autowired
    private MiaoShaService miaoShaService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private Provider provider;
    private Map<Integer,Boolean> isOver = new HashMap<>();


    /**
     * 系统每次初始化时，将秒杀商品的库存数量加载到redis
     */
    @Override
    public void afterPropertiesSet(){
        List<GoodsList> goodsList = goodsService.getGoodsList();
        if(goodsList == null){
            return;
        }
        for(GoodsList good : goodsList){
            isOver.put(good.getId(),false);
            Integer stock_count = good.getStock_count();
            redisService.set(GoodListKey.miaoshaGoodCountKey,good.getId()+"",stock_count);
        }
    }

    /**
     * 页面静态化
     * @param good_id
     * @return
     */
    @RequestMapping("/miaosha_static/{path}")
    @ResponseBody
    public Result<Integer> miaoSha_static(@RequestParam("good_id") int good_id,
                                          @UserParameter User user,
                                          @PathVariable("path") String path){
        //验证path是否正确
        boolean isPathCorrect = miaoShaService.checkPath(user, good_id, path);
        if(!isPathCorrect){
            return Result.error(CodeMsg.SERVER_ERROR);
        }

        //库存标记，如果库存小于零则直接结束，不操作redis
        if(isOver.get(good_id)){
            return Result.error(CodeMsg.OUT_OF_STOCK);
        }

        //判断是否还有库存
        //预减库存
        Long decr = redisService.decr(GoodListKey.miaoshaGoodCountKey, good_id + "");
        if(decr < 0){
            isOver.put(good_id,true);
            return Result.error(CodeMsg.OUT_OF_STOCK);
        }

        //判断该用户是第一次秒杀，不可重复秒杀
        //查找redis缓存，绕开数据库
        Miaosha_order miaosha_order = redisService.get(MiaoShaOrderKey.orderKey,user.getId()+":"+good_id,Miaosha_order.class);
        if(miaosha_order != null){
            return Result.error(CodeMsg.NO_REPEAT_MIAOSHA);
        }

        MiaoshaMsg msg = new MiaoshaMsg();
        msg.setUser(user);
        msg.setGood_id(good_id);
        //秒杀入队
        provider.miaoshaProvider(msg);
        return Result.success(0);  //0代表排队中

        /*
          //未做优化
          GoodsList good = goodsService.getGoodsListById(good_id);
          //判断该商品是否还有库存
          int stock_count = miaoSha_goodsService.getMiaosha_goodsByid(good.getId());
          if(stock_count <= 0){
              return Result.error(CodeMsg.OUT_OF_STOCK);
          }

          //判断该用户是第一次秒杀，不可重复秒杀
          //查找redis缓存，绕开数据库
          Miaosha_order miaosha_order = redisService.get(MiaoShaOrderKey.orderKey,user.getId()+":"+good.getId(),Miaosha_order.class);
          if(miaosha_order != null){
              return Result.error(CodeMsg.NO_REPEAT_MIAOSHA);
          }

          //允许秒杀
          //库存-1 生成order_info订单 生成秒杀订单
          int i = miaoShaService.miaoSha(user, good);
          //查找订单
          Order_info order_info = order_infoService.selectOrderById(i);
          return Result.success(order_info);
         */
    }

    /**
     * orderId 秒杀成功
     * 0 排队中
     * -1 失败
     * @param user
     * @param good_id
     * @return
     */
    @RequestMapping("/getmiaoshadetail")
    @ResponseBody
    public Result<Integer> getMiaoshaDetail(@UserParameter User user,@RequestParam("good_id") int good_id){
        int detail = miaoShaService.getMiaoshaDetail(user, good_id);
        return Result.success(detail);
    }

    @AccessLimit(second = 5,maxCount = 4,needLogin = true)
    @RequestMapping("/getmiaoshapath")
    @ResponseBody
    public Result<String> getMiaoshaPath(@UserParameter User user,@RequestParam("good_id") int good_id){
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        String pathUUID = Md5Utils.md5(UUIDUtil.uuid()+"miaoshadizhi");
        redisService.set(MiaoShaKey.miaoShaPathKey,user.getId()+":"+good_id,pathUUID);
        return Result.success(pathUUID);
    }
}
