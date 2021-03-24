package site.kexing.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.kexing.dao.MiaoSha_OrderDao;
import site.kexing.pojo.Miaosha_order;
import site.kexing.pojo.User;
import site.kexing.vo.GoodsList;

@Service
public class MiaoShaOrderService {
    @Autowired
    private MiaoSha_OrderDao miaoSha_orderDao;

    public Miaosha_order getOrderByUidAndGoodsId(User user, int good_id){
        return miaoSha_orderDao.getOrderByUidAndGoodsId(user, good_id);
    }


    public int createMiaoSha_Order(User user, GoodsList good, int order_id){
        return miaoSha_orderDao.createMiaoSha_Order(user, good, order_id);
    }
}
