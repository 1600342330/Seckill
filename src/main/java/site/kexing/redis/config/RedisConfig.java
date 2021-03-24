package site.kexing.redis.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;

@Repository
@ConfigurationProperties(prefix = "spring.redis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisConfig {
    private String host;
    private int port;
    private int maxActive;
    private int maxWait;
    private int maxIdle;
    private int minIdle;
}
