package site.kexing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;
import site.kexing.Annotation.AccessLimit;
import site.kexing.Annotation.UserParameter;
import site.kexing.pojo.User;
import site.kexing.redis.key.GoodListKey;
import site.kexing.redis.RedisService;
import site.kexing.result.Result;
import site.kexing.service.GoodsService;
import site.kexing.vo.GoodsDetailStatic;
import site.kexing.vo.GoodsList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /*
        线程数：5000*10
        QPS：1142
     */
    @AccessLimit(second = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/togoodslist",produces = "text/html")
    @ResponseBody
    public String toGoodsList(HttpServletRequest request, HttpServletResponse response, Model model, @UserParameter User user){
        //取缓存
        String html = redisService.get(GoodListKey.goodListKey, "goodList", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        model.addAttribute("loginUser",user);
        List<GoodsList> goodsList = goodsService.getGoodsList();
        model.addAttribute("goodsList",goodsList);

        //缓存中没有，手动渲染
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());                                     
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list",webContext);
        if(!StringUtils.isEmpty(html)){
            //装入缓存
            redisService.set(GoodListKey.goodListKey,"goodList",html);
        }
        return html;
    }

    @RequestMapping(value = "/goodsdetail_static/{goodid}")
    @ResponseBody
    public Result<GoodsDetailStatic> goodsDetail_Static(@PathVariable("goodid") String goodid){
        Integer id = Integer.valueOf(goodid);
        GoodsList good = goodsService.getGoodsListById(id);
        //秒杀开始时间戳
        long start = good.getStart_date().getTime();
        //秒杀结束时间戳
        long end = good.getEnd_date().getTime();
        //现在时间
        long now = System.currentTimeMillis();
        //秒杀状态码，0代表秒杀未开始，-1代表秒杀结束，1代表秒杀进行中
        int status = 0;
        //倒计时
        long m = 0;
        if(start > now){
            //秒杀未开始
            status = 0;
            m = (start - now)/1000;
        }else if(now > end){
            //秒杀结束
            status = -1;
            m = -1;
        }else{
            //秒杀进行中
            status = 1;
            m = 0;
        }
        GoodsDetailStatic goodsDetail = new GoodsDetailStatic();
        goodsDetail.setGood(good);
        goodsDetail.setSecond(m);
        goodsDetail.setStatus(status);
        return Result.success(goodsDetail);
    }
}
