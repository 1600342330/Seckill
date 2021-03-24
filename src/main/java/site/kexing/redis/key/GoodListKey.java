package site.kexing.redis.key;

import site.kexing.redis.BasePrefix;

public class GoodListKey extends BasePrefix {
    public GoodListKey() {
        super();
    }

    public GoodListKey(String prefix){
        super(prefix);
    }

    public GoodListKey(int expireSeconds,String prefix){
        super(expireSeconds,prefix);
    }

    public static GoodListKey goodListKey = new GoodListKey(60,"goodList");
    public static GoodListKey goodDetailKey = new GoodListKey(60,"goodDetail");
    public static GoodListKey miaoshaGoodCountKey = new GoodListKey("miaoshaGoodCount");
    public static GoodListKey miaoshaGoodCountIsOver = new GoodListKey("miaoshaGoodCountIsOver");
}
