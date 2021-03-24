package site.kexing.redis.key;

import site.kexing.redis.BasePrefix;

public class AccessLimitKey extends BasePrefix {
    AccessLimitKey(int expireSeconds,String prefix){
        super(expireSeconds, prefix);
    }

    public static AccessLimitKey withExpire(int expireSeconds){
        return new AccessLimitKey(expireSeconds,"accessLimit");
    }
}
