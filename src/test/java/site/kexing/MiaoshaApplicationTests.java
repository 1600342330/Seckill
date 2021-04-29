package site.kexing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;
import site.kexing.dao.GoodsDao;
import site.kexing.redis.config.RedisConfig;
import site.kexing.redis.config.RedisPoolConfig;
import site.kexing.redis.RedisService;
import site.kexing.vo.GoodsList;

import java.util.List;

@SpringBootTest
class MiaoshaApplicationTests {
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private RedisPoolConfig redisPoolConfig;
    @Autowired
    private JedisPool jedisPool;

    @Test
    void contextLoads() {
        List<GoodsList> goodsList = goodsDao.getGoodsList();
        for (GoodsList list : goodsList) {
            System.out.println(list);
        }
    }

}
