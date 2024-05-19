package service.repository;

import com.magnus.ServiceApplication;
import com.magnus.domain.user.UserRepositoryImpl;
import com.magnus.domain.user.model.User;
import com.magnus.domain.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author gaosong
 */
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class UserTest {

    @Resource
    private UserRepositoryImpl userRepository;

    @Test
    public void testGetUser(){
        User user =  userRepository.getById(1L);
        System.out.println(user);
    }

    @Test
    public void testAddUser(){
        userRepository.create(User.builder()
                        .gmtCreate(LocalDateTime.now())
                        .gmtModified(LocalDateTime.now())
                        .name("gs")
                        .mobile("136")
                        .createBy(-1L)
                        .modifiedBy(-1L)
                .build());
    }


}
