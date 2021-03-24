package site.kexing.redis.key;

import site.kexing.redis.BasePrefix;

public class MiaoShaKey extends BasePrefix {
    MiaoShaKey(int expireSeconds,String prefix){
        super(expireSeconds, prefix);
    }

    public static MiaoShaKey miaoShaPathKey = new MiaoShaKey(60,"miaoShaPathKey");
}
