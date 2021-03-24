package site.kexing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.kexing.pojo.Miaosha_order;
import site.kexing.pojo.User;
import site.kexing.redis.key.GoodListKey;
import site.kexing.redis.key.MiaoShaKey;
import site.kexing.redis.key.MiaoShaOrderKey;
import site.kexing.redis.RedisService;
import site.kexing.vo.GoodsList;

@Service
public class MiaoShaService {
    @Autowired
    private MiaoShaGoodsService miaoShaGoodsService;
    @Autowired
    private OrderinfoService order_infoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MiaoShaOrderService miaoSha_orderService;

    /**
     * 执行秒杀业务
     * 1、减少数据库库存
     * 2、创建订单
     * @param user
     * @param good
     * @return
     */
    @Transactional
    public Integer miaoSha(User user, GoodsList good){
        //减少库存
        int i = miaoShaGoodsService.reduceStockCount(good.getId());
        if(i == 1){
            //减库存成功
            //创建Order_info订单,秒杀订单
            int order_id = order_infoService.createOrder_Info(user, good);
            return order_id;
        }else {
            //减库存失败，无库存
            setGoodsCountIsOver(good.getId());
            return null;
        }
    }

    /**
     * 判断秒杀的详细状态
     * orderId 秒杀成功
     * 0 排队中
     * -1 失败
     * @param user
     * @param good_id
     * @return
     */
    public int getMiaoshaDetail(User user,int good_id){
        Miaosha_order order = redisService.get(MiaoShaOrderKey.orderKey,user.getId()+":"+good_id,Miaosha_order.class);
        if(order != null){
            //秒杀成功
            return order.getOrder_id();
        }else {
            boolean over = getGoodsCountIsOver(good_id);
            if(over){
                //无库存，秒杀失败
                return -1;
            }else {
                //排队中
                return 0;
            }
        }
    }

    public void setGoodsCountIsOver(int good_id){
        redisService.set(GoodListKey.miaoshaGoodCountIsOver,good_id+"",true);
    }

    public boolean getGoodsCountIsOver(int good_id){
       return redisService.exist(GoodListKey.miaoshaGoodCountIsOver,good_id+"");
    }

    public boolean checkPath(User user,long good_id,String path){
        String s = redisService.get(MiaoShaKey.miaoShaPathKey, user.getId() + ":" + good_id, String.class);
        return path.equals(s);
    }
}
