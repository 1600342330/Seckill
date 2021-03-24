package site.kexing.redis.key;

import site.kexing.redis.BasePrefix;

public class MiaoShaOrderKey extends BasePrefix {
    MiaoShaOrderKey(String prefix){
        super(prefix);
    }

    public static MiaoShaOrderKey orderKey = new MiaoShaOrderKey("orderKey");

}
