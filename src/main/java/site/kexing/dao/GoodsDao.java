package site.kexing.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.kexing.vo.GoodsList;

import java.util.List;

@Repository
@Mapper
public interface GoodsDao {
    List<GoodsList> getGoodsList();
    GoodsList getGoodsListById(int id);
}
