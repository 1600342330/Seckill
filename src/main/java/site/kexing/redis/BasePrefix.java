package site.kexing.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;
    private String prefix;

    /**
     * 0默认Key永不过期
     * @return
     */
    public BasePrefix(String prefix){
        this.expireSeconds = 0;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return getClass().getSimpleName()+":"+prefix;
    }
}
