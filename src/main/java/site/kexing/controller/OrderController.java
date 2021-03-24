package site.kexing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import site.kexing.pojo.Order_info;
import site.kexing.result.Result;
import site.kexing.service.GoodsService;
import site.kexing.service.OrderinfoService;
import site.kexing.vo.GoodsList;
import site.kexing.vo.OrderDetailStatic;


@Controller
public class OrderController {
    @Autowired
    private OrderinfoService order_infoService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/getOrderDetail")
    @ResponseBody
    public Result<OrderDetailStatic> getOrderDetail(@RequestParam("orderId") String orderId){
        int id = Integer.valueOf(orderId);
        //获取订单
        Order_info order_info = order_infoService.selectOrderById(id);
        int goodId = order_info.getGoods_id();
        //获取该id对应的商品信息
        GoodsList good = goodsService.getGoodsListById(goodId);
        //封装进vo
        OrderDetailStatic orderDetailStatic = new OrderDetailStatic();
        orderDetailStatic.setOrder_info(order_info);
        orderDetailStatic.setOrder_good(good);
        return Result.success(orderDetailStatic);
    }
}
