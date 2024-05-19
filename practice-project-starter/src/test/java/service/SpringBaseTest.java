package service;

import com.magnus.ServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.Serializable;

@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class SpringBaseTest {

    @Before
    public void init() {
    }

    @Test
    public void test() {
        System.out.println("success");
    }
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void testCache(){
        redisTemplate.opsForValue().set("test","testValue");
    }
}
