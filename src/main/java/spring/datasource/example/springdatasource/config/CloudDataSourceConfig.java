package spring.datasource.example.springdatasource.config;


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
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@Profile("cloud")
public class CloudDataSourceConfig extends AbstractCloudConfig {

    @Autowired
    private Environment env;


    @Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }
    /* cloud().getServiceConnector(서비스명, DataSource.class, null); 을 이용하여 PAAS 상의 앱과 바인드된 서비스 데이터소스를 가지고 옵니다 */
    /*
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = cloud().getServiceConnector("dbtest-db", DataSource.class, null);
        return dataSource;
    }
    */

    /* extends AbstractCloudConfig 를 통해서  connectionFactory().dataSource(서비스명) 을 이용하여 PAAS 상의 앱과 바인드된 서비스 데이터소스를 가지고 옵니다 */
  /*
    @Bean
    public DataSource dataSource() {
        //extends AbstractCloudConfig
        return connectionFactory().dataSource("dbtest-db");
    }
   */


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
        dataSource.setDriverClassName( env.getProperty("user.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("user.datasource.url"));
        dataSource.setUsername(env.getProperty("user.datasource.username"));
        dataSource.setPassword(env.getProperty("user.datasource.password"));
        return dataSource;
    }
    */

    /*cloud().getCloudProperties() 를 이용하여 PAAS 상의 앱과 바인드된 서비스 연결정보를 획득하여 획득한 정보를 통해 데이터 소스를 가져옵니다.*/
    @Bean
    public DataSource dataSource() {

        /* cloud().getCloudProperties() -->
        {cloud.services.mysql.connection.schemespecificpart=//uLc8cyrRy3K30Xq4:FYDECDZmeAY7chcd@10.41.61.87:3306/cf_59912ee9_749d_421e_bc1d_7f41095c20b5?reconnect=true,
        cloud.services.dbtest-db.id=dbtest-db,
        cloud.application.space_id=edcb8281-47b3-44e9-9f46-42036b75fd2c,
        cloud.services.dbtest-db.connection.scheme=mysql,
        cloud.services.dbtest-db.connection.path=cf_59912ee9_749d_421e_bc1d_7f41095c20b5,
        cloud.application.cf_api=https://api.galaxycloud.co.kr,
        cloud.services.mysql.connection.uri=mysql://uLc8cyrRy3K30Xq4:FYDECDZmeAY7chcd@10.41.61.87:3306/cf_59912ee9_749d_421e_bc1d_7f41095c20b5?reconnect=true,
        cloud.services.mysql.connection.scheme=mysql,
        cloud.application.host=0.0.0.0,
        cloud.services.dbtest-db.connection.password=FYDECDZmeAY7chcd,
        cloud.services.dbtest-db.connection.username=uLc8cyrRy3K30Xq4,
        cloud.services.dbtest-db.connection.host=10.41.61.87,
        cloud.application.application_name=dbtest,
        cloud.application.limits={disk=1024, fds=16384, mem=1024},
        cloud.services.mysql.connection.port=3306,
        cloud.application.application_uris=[dbtest-lean-mongoose.galaxycloud.co.kr],
        cloud.services.dbtest-db.connection.query=reconnect=true,
        cloud.application.instance_index=0,
        cloud.services.mysql.connection.password=FYDECDZmeAY7chcd,
        cloud.services.mysql.connection.jdbcurl=jdbc:mysql://10.41.61.87:3306/cf_59912ee9_749d_421e_bc1d_7f41095c20b5?user=uLc8cyrRy3K30Xq4&password=FYDECDZmeAY7chcd,
        cloud.services.dbtest-db.connection.uri=mysql://uLc8cyrRy3K30Xq4:FYDECDZmeAY7chcd@10.41.61.87:3306/cf_59912ee9_749d_421e_bc1d_7f41095c20b5?reconnect=true,
        cloud.application.name=dbtest, cloud.services.mysql.connection.username=uLc8cyrRy3K30Xq4,
        cloud.application.instance-id=f78eb38c-b659-4cf1-4160-9251,
        cloud.services.mysql.connection.path=cf_59912ee9_749d_421e_bc1d_7f41095c20b5,
        cloud.application.uris=[dbtest-lean-mongoose.galaxycloud.co.kr],
        cloud.services.mysql.id=dbtest-db,
        cloud.application.version=7c56614e-14f4-4670-a9ac-c39b286450a4,
        cloud.application.app-id=dbtest,
        cloud.application.instance_id=f78eb38c-b659-4cf1-4160-9251,
        cloud.application.application_id=2fe5371d-a555-4d74-87f1-0851dc9f6946,
        cloud.services.mysql.connection.host=10.41.61.87,
        cloud.services.dbtest-db.connection.schemespecificpart=//uLc8cyrRy3K30Xq4:FYDECDZmeAY7chcd@10.41.61.87:3306/cf_59912ee9_749d_421e_bc1d_7f41095c20b5?reconnect=true,
        cloud.application.application_version=7c56614e-14f4-4670-a9ac-c39b286450a4,
        cloud.services.mysql.connection.query=reconnect=true,
        cloud.services.dbtest-db.connection.jdbcurl=jdbc:mysql://10.41.61.87:3306/cf_59912ee9_749d_421e_bc1d_7f41095c20b5?user=uLc8cyrRy3K30Xq4&password=FYDECDZmeAY7chcd,
        cloud.application.port=8080,
        cloud.application.space_name=crossent-space,
        cloud.services.dbtest-db.connection.port=3306}
        */

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( env.getProperty("user.datasource.driverClassName"));
        dataSource.setUrl(cloud().getCloudProperties().getProperty("cloud.services.dbtest-db.connection.jdbcurl"));
        dataSource.setUsername(cloud().getCloudProperties().getProperty("cloud.services.dbtest-db.connection.username"));
        dataSource.setPassword(cloud().getCloudProperties().getProperty("cloud.services.dbtest-db.connection.password"));
        return dataSource;
    }


}
