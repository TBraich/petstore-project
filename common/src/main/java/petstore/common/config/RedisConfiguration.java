package petstore.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
  @Value("${spring.redis.host:localhost}")
  private String host;

  @Value("${spring.redis.port:6379}")
  private String port;

  @Value("${spring.redis.password:root}")
  private String redisPassword;

  @Bean
  JedisConnectionFactory redisConnectionFactory() {
    var config = new RedisStandaloneConfiguration(host, Integer.parseInt(port));
    config.setPassword(RedisPassword.of(redisPassword));
    return new JedisConnectionFactory(config);
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate() {
    var template = new RedisTemplate<String, String>();
    template.setConnectionFactory(redisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
  }
}
