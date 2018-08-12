package spring.datasource.example.springdatasource.config;

import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@Profile("default")
public class DataSourceConfig {

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

}