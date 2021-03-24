package site.kexing.redis.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

@Repository
@ConfigurationProperties(prefix = "spring.redis.jedis.pool")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisPoolConfig {
    private int maxActive;
    private int maxWait;
    private int maxIdle;
    private int minIdle;
}
