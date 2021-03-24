package site.kexing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.kexing.dao.GoodsDao;
import site.kexing.vo.GoodsList;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsList> getGoodsList(){
        List<GoodsList> goodsList = goodsDao.getGoodsList();
        return goodsList;
    }

    public GoodsList getGoodsListById(int id){
        GoodsList good = goodsDao.getGoodsListById(id);
        return good;
    }
}
