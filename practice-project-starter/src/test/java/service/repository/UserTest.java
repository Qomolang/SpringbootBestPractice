package service.repository;

import com.magnus.ServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author gaosong
 */
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class UserTest {
}
