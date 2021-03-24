package site.kexing.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    /**
     * get 值
     * @param prefix 模块前缀
     * @param key key
     * @param classzz key对应值的类型
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix,String key,Class<T> classzz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //key 拼接为前缀-key
            key = prefix.getPrefix() + "-" + key;
            String s = jedis.get(key);
            T t = StringToBean(s, classzz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }


    /**
     * set值
     * @param prefix 模块前缀
     * @param key key
     * @param value 值
     * @param <T>
     * @return
     */
    public <T> Boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //将需要set的值转为字符串
            String s = beanToString(value);
            if(s ==null || s.length() <=0){
                return false;
            }
            //key 拼接为前缀-key
            key = prefix.getPrefix() +"-"+ key;
            int expireSeconds = prefix.expireSeconds();
            //如果未设置过期时间 也就是默认为0
            if(expireSeconds <= 0){
                jedis.set(key, s);
            }else {
                jedis.setex(key,expireSeconds,s);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * key 是否存在
     * @param prefix
     * @param key
     * @return
     */
    public Boolean exist(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            Boolean exists = jedis.exists(key);
            return exists;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 自增一
     * @param prefix
     * @param key
     * @return
     */
    public Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            Long incr = jedis.incr(key);
            return incr;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 自减一
     * @param prefix
     * @param key
     * @return
     */
    public Long decr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            Long incr = jedis.decr(key);
            return incr;
        }finally {
            returnToPool(jedis);
        }
    }

    public void returnToPool(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    /**
     * 类型转字符串
     * @param value 需要转字符串的类型
     * @param <T>
     * @return
     */
    public <T> String beanToString(T value){
        Class<?> aClass = value.getClass();
        //判空
        if(value == null){
            return null;
        }
        if(aClass == Integer.class || aClass == int.class){
            return ""+value;
        }else if(aClass == String.class){
            return (String)value;
        }else if(aClass == long.class || aClass == Long.class){
            return ""+value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    /**
     * 字符串转类型
     * @param str 字符串
     * @param classzz 类型
     * @param <T>
     * @return
     */
    public <T> T StringToBean(String str,Class<T> classzz){
        //判空
        if(str ==  null || str.length()<=0 || classzz == null){
            return null;
        }
        if(classzz == Integer.class || classzz == int.class){
            return (T) Integer.valueOf(str);
        }else if(classzz == String.class){
            return (T)str;
        }else if(classzz == long.class || classzz == Long.class){
            return (T) Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),classzz);
        }
    }
}
