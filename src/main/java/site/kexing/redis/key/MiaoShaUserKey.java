package site.kexing.redis.key;

import site.kexing.redis.BasePrefix;

public class MiaoShaUserKey extends BasePrefix {
    public MiaoShaUserKey(int expireSeconds, String prefix){
        super(expireSeconds,prefix);
    }

    public static MiaoShaUserKey userKeyToken = new MiaoShaUserKey(60*60*24*7,"user_token");
}
