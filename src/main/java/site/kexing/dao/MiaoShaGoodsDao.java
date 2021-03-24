package site.kexing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MiaoShaGoodsDao {
    int getMiaosha_goodsByid(int good_id);
    int reduceStockCount(int good_id);
}
