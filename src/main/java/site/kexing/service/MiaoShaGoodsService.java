package site.kexing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.kexing.dao.MiaoShaGoodsDao;

@Service
public class MiaoShaGoodsService {
    @Autowired
    private MiaoShaGoodsDao miaoShaGoodsDao;

    public int getMiaosha_goodsByid(int good_id){
        int stock_count = miaoShaGoodsDao.getMiaosha_goodsByid(good_id);
        return stock_count;
    }

    public int reduceStockCount(int good_id){
        int i = miaoShaGoodsDao.reduceStockCount(good_id);
        return i;
    }

}
