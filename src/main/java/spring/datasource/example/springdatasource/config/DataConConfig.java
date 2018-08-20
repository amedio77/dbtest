package spring.datasource.example.springdatasource.config;

import com.mongodb.*;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import redis.clients.jedis.JedisPoolConfig;
import javax.sql.DataSource;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;


@Configuration
@Profile("default")
/*  @Profile("default") 의 경루 로컬 테스트 환경에서 사용되는 데이터소스 환경입니다. */
public class DataConConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {

        String vcapServices = System.getenv("VCAP_SERVICES");
        System.out.println("VCAP_SERVICES DataSourceConfig ::: "+vcapServices);

        System.out.println("Connecting to database...");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( env.getProperty("user.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("user.datasource.url"));
        dataSource.setUsername(env.getProperty("user.datasource.username"));
        dataSource.setPassword(env.getProperty("user.datasource.password"));
        return dataSource;
    }


    // Redis Local ConnectionFactory
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setUsePool(false);
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);

        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }




    // Mongo Local ConnectionFactory

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoFactory());
    }

    @Bean
    public MongoDbFactory mongoFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), "mongodb");
    }

}