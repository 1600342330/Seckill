package site.kexing.redis;

/**
 * Key的前缀接口
 * 用来区分各个模块
 */
public interface KeyPrefix {
    /**
     * 过期时间
     * @return
     */
    int expireSeconds();

    /**
     * get前缀
     * @return
     */
    String getPrefix();
}
