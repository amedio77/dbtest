package spring.datasource.example.springdatasource.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Queue;

@Configuration
@Profile("cloud")
public class CloudDataConConfig extends AbstractCloudConfig {

    @Autowired
    private Environment env;


    @Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }

    /* VCAP_SERVICES 설정정보를 단순히 텍스트로 가져오는 샘플입니다. 가저온 스트링 텍스트 정보를 파싱하여 원하는 정보를 획득하여  획득한 정보를 통해 데이터 소스를 가져옵니다.
    *  스트링 파싱 부분은 따로 작성하지 않았습니다.
    * */
/*
    @Bean
    public DataSource dataSource() {

        String vcapServices = System.getenv("VCAP_SERVICES");
        System.out.println("VCAP_SERVICES DataSourceConfig ::: "+vcapServices);

        System.out.println("Connecting to database...");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("파싱한값");
        dataSource.setUrl("파싱한값");
        dataSource.setUsername("파싱한값");
        dataSource.setPassword("파싱한값");
        return dataSource;
    }
*/

    /* cloud().getServiceConnector(서비스명, DataSource.class, null); 을 이용하여 PAAS 상의 앱과 바인드된 서비스 데이터소스를 가지고 옵니다 */
    /*
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = cloud().getServiceConnector("dbtest-db", DataSource.class, null);
        return dataSource;
    }
    */

    /* extends AbstractCloudConfig 를 통해서  connectionFactory().dataSource(서비스명) 을 이용하여 PAAS 상의 앱과 바인드된 서비스 데이터소스를 가지고 옵니다 */
    @Bean
    public DataSource dataSource() {
        //extends AbstractCloudConfig
        return connectionFactory().dataSource("dbtest-db");
   }

    //redis
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // return connectionFactory().redisConnectionFactory(); redis Single Service
        return connectionFactory().redisConnectionFactory("dbtest-redis");
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    //rabbitmq
    /*
    @Bean
    public ConnectionFactory rabbitFactory() {
        //RabbitConnectionFactory
        return connectionFactory().rabbitConnectionFactory("rabbit-servicename");
    }
    */


    //mongodb
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoFactory());
    }

    @Bean
    public MongoDbFactory mongoFactory() {
        return connectionFactory().mongoDbFactory("dbtest-mongo");
    }

}
