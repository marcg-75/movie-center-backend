package se.giron.moviecenter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@Configuration
public class RedisConfiguration {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.sentinel.master:null}")
    private String master;

    @Value("${spring.redis.sentinel.nodes:null}")
    private List<String> nodes;

    @Profile("!sentinel")
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        if (!isEmpty(redisHost)) {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
            if (!isEmpty(redisPassword)) {
                redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
            }

            return new LettuceConnectionFactory(redisStandaloneConfiguration);
        }

        return new LettuceConnectionFactory();
    }

    @Profile("sentinel")
    @Bean
    public LettuceConnectionFactory connectionFactorySentinel() {
        final RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master(master);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.stream().map(node -> node.split(":")).forEach(arr -> sentinelConfig.sentinel(arr[0], Integer.parseInt(arr[1])));
        }
        return new LettuceConnectionFactory(sentinelConfig);
    }

    public String getRedisHost() {
        return redisHost;
    }

    public Integer getRedisPort() {
        return redisPort;
    }
}
