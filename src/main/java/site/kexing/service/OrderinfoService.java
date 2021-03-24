package site.kexing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.kexing.dao.Order_infoDao;
import site.kexing.pojo.Miaosha_order;
import site.kexing.pojo.Order_info;
import site.kexing.pojo.User;
import site.kexing.redis.key.MiaoShaOrderKey;
import site.kexing.redis.RedisService;
import site.kexing.vo.GoodsList;
import java.util.Date;

@Service
public class OrderinfoService {
    @Autowired
    private Order_infoDao order_infoDao;
    @Autowired
    private MiaoShaOrderService miaoSha_orderService;
    @Autowired
    private RedisService redisService;

    @Transactional
    public int createOrder_Info(User user, GoodsList good){
        Order_info order_info = new Order_info();
        order_info.setUser_id(user.getId());
        order_info.setGoods_id(good.getId());
        order_info.setDelivery_addr_id(1);
        order_info.setGoods_name(good.getGoods_name());
        order_info.setGoods_count(good.getStock_count());
        order_info.setGoods_price(good.getMiaosha_price());
        order_info.setOrder_channel(1);
        order_info.setStatus(0);
        order_info.setCreate_date(new Date());
        order_info.setPay_date(null);
        //创建订单
        order_infoDao.createOrder_info(order_info);
        int order_id = order_info.getId();
        //创建秒杀订单
        miaoSha_orderService.createMiaoSha_Order(user, good, order_id);

        Miaosha_order miaosha_order = new Miaosha_order();
        miaosha_order.setUser_id(user.getId());
        miaosha_order.setOrder_id(order_id);
        miaosha_order.setGoods_id(good.getId());
        //订单存入缓存
        redisService.set(MiaoShaOrderKey.orderKey,user.getId()+":"+good.getId(),miaosha_order);
        return order_id;
    }

    public Order_info selectOrderById(int id){
        return order_infoDao.selectOrderById(id);
    }
}
