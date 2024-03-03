package petstore.pet.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("petstore.pet.repository")
public class MyBatisConfiguration {
  @Value(
      "${spring.dataSource.url:jdbc:mysql://127.0.0.1:3306/pet_database?createDatabaseIfNotExist=true}")
  private String url;

  @Value("${spring.dataSource.username:root}")
  private String username;

  @Value("${spring.dataSource.password:root}")
  private String password;

  @Value("${spring.dataSource.driver-class-name:com.mysql.cj.jdbc.Driver}")
  private String driverClassName;

  @Value("${spring.dataSource.hikari.connection-timeout:30000}")
  private long connectionTimeout;

  @Value("${spring.dataSource.hikari.idle-timeout:600000}")
  private long idleTimeout;

  @Value("${spring.dataSource.hikari.maximum-pool-size:5}")
  private int maxPoolSize;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setDriverClassName(driverClassName);

    // Additional HikariCP settings
    config.setMaximumPoolSize(maxPoolSize);
    config.setIdleTimeout(idleTimeout);
    config.setConnectionTimeout(connectionTimeout);

    return new HikariDataSource(config);
  }

  @Bean
  public org.apache.ibatis.session.Configuration mybatisConfig() {
    var config = new org.apache.ibatis.session.Configuration();
    config.setMapUnderscoreToCamelCase(true);
    config.setAggressiveLazyLoading(true);
    config.setLazyLoadingEnabled(true);
    return config;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    var sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setTypeAliasesPackage("petstore.pet.entity");
    sessionFactory.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
    sessionFactory.setConfiguration(mybatisConfig());
    return sessionFactory.getObject();
  }
}
