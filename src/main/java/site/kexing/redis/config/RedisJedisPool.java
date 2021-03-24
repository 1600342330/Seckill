package site.kexing.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisJedisPool {
    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private RedisPoolConfig redisPoolConfig;

    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisPoolConfig.getMaxIdle());
        poolConfig.setMaxWaitMillis(redisPoolConfig.getMaxWait());
        poolConfig.setMinIdle(redisConfig.getMinIdle());
        JedisPool jedisPool = new JedisPool(poolConfig,redisConfig.getHost(),redisConfig.getPort(),100000);
        return jedisPool;
    }
}
