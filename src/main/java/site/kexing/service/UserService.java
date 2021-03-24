package site.kexing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.kexing.pojo.User;
import site.kexing.redis.key.MiaoShaUserKey;
import site.kexing.redis.RedisService;

@Service
public class UserService {
    @Autowired
    private RedisService redisService;

    /**'
     * 在redis中获取token对应的user
     * @param token
     * @return
     */
    public User getUserByToken(String token){
        return redisService.get(MiaoShaUserKey.userKeyToken,token,User.class);
    }
}
