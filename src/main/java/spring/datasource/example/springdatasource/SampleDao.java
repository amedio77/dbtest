package spring.datasource.example.springdatasource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public  class SampleDao {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisTemplate redisTemplate;

   // @Autowired
   // MongoTemplate mongoTemplate;


    public void CreateTable( ) throws SQLException {

        Connection c = dataSource.getConnection();
        HashMap<String, Object> map = new HashMap<>();


        String strCreateTable =" CREATE TABLE IF NOT EXISTS `user_info` (         ";
        strCreateTable += "         `user_id` varchar(255) NOT NULL,";
        strCreateTable += " `client_id` varchar(255) DEFAULT NULL,";
        strCreateTable += " `client_secret` varchar(255) DEFAULT NULL,";
        strCreateTable += " `del_yn` varchar(255) DEFAULT NULL,";
        strCreateTable += " `email` varchar(255) DEFAULT NULL,";
        strCreateTable += " `name` varchar(255) DEFAULT NULL,";
        strCreateTable += " `pass` varchar(255) DEFAULT NULL,";
        strCreateTable += " `reg_dt` varchar(255) DEFAULT NULL,";
        strCreateTable += " `role_type` varchar(255) DEFAULT NULL,";
        strCreateTable += " `token` varchar(255) DEFAULT NULL,";
        strCreateTable += " PRIMARY KEY (`user_id`)  ";
        strCreateTable += ") ENGINE=InnoDB DEFAULT CHARSET=utf8";

        PreparedStatement ps = c.prepareStatement(strCreateTable);
        ps.execute();

        String insertSql = "INSERT ignore into `user_info` VALUES ('sbj', '', '', 'N', 'sbj@naver.com', 'sbj', 'qwer1234', '', 'ADMIN,USER', ''), ('test001', '', '', 'N', 'ws0222@naver.com', '홍길동1', 'qwer1234', '', 'ADMIN,USER', ''), ('test002', '', '', 'N', 'ws0222@naver.com', '홍길동2', 'qwer1234', '', 'ADMIN,USER', ''), ('test02', '', '', 'N', 'amedio77@gmail.com', '테스터22', '1234', '', 'ADMIN,USER', ''), ('test1', '', '', 'N', 'ws0222@naver.com', '테스터79', '1234', '', 'ADMIN,USER', ''), ('test2', '', '', 'N', 'ws0222@naver.com', 'test2name', 'qwer1234', '', 'USER', ''), ('test3', '', '', 'N', 'ws02221@naver.com', 'testname1', 'qwer1234', '', 'ADMIN,USER', ''), ('TTA', '', '', 'N', 'TTA@naver.com', 'TTATEST', 'qwer1234', '', 'ADMIN,USER', ''), ('TTATEST', '', '', 'N', 'TTA@naver.com', 'TTA', 'qwer1234', '', 'ADMIN,USER', '')";
        ps.execute(insertSql);

        ps.close();

    }

    public HashMap getData( ) throws SQLException {

        Connection c = dataSource.getConnection();
        HashMap<String, Object> map = new HashMap<>();

        PreparedStatement ps = c.prepareStatement("select * from user_info where user_id = ?");
        ps.setString(1,"test1");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println("user id = " + rs.getString("user_id"));
            System.out.println("email   = " + rs.getString("email"));

            map.put("user_id", rs.getString("user_id"));
            map.put("email", rs.getString("email"));
        }

        ps.close();
        rs.close();

        return map;

    }

    public HashMap redisTest(){
        HashMap<String, Object> map = new HashMap<>();
        redisTemplate.opsForList().leftPush("testRedis", "testredis@gmail.com");

        System.out.println(redisTemplate.opsForList().index("testRedis", 0));

        map.put("user_id", "testRedis");
        map.put("email", redisTemplate.opsForList().index("testRedis", 0));

        return map;
    }
/*
    public HashMap mongoTest(){

        HashMap<String, Object> map = new HashMap<>();
        User user = new User("mkyong", "mogoemail");
        mongoTemplate.save(user);

        // query to search user
        Query searchUserQuery = new Query(Criteria.where("username").is("mkyong"));

        // find the saved user again.
        User savedUser = mongoTemplate.findOne(searchUserQuery, User.class);

        map.put("user_id", savedUser.getUsername());
        map.put("email", savedUser.getEmail());

        return map;
    }
*/

}
