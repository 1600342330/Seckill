package site.kexing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import site.kexing.pojo.Miaosha_order;
import site.kexing.pojo.User;
import site.kexing.vo.GoodsList;

@Repository
@Mapper
public interface MiaoSha_OrderDao {
    Miaosha_order getOrderByUidAndGoodsId(@Param("user") User user,@Param("good_id") int good_id);
    int createMiaoSha_Order(@Param("user") User user, @Param("good")GoodsList good, @Param("order_id")int order_id);
}
