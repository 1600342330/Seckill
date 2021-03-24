package site.kexing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.kexing.pojo.Order_info;

@Mapper
@Repository
public interface Order_infoDao {
    int createOrder_info(Order_info order_info);
    Order_info selectOrderById(int id);
}
