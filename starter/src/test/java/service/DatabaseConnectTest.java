package service;

import com.magnus.ServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
@Slf4j
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class DatabaseConnectTest {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Before
    public void init() {
    }

    @Test
    public void test() {
        //USERINFO是你自己连接数据库中的表，这里查询的是记录的数目
        Long aL = jdbcTemplate.queryForObject("select count(*) from user", Long.class);
        log.info("test OracleConnect : {} 条", aL);
        System.out.println("success");
    }

}
