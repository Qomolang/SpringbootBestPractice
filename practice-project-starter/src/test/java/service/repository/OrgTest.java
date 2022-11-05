package service.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magnus.ServiceApplication;
import com.magnus.domain.org.model.Org;
import com.magnus.domain.org.repository.OrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class OrgTest {

    @Resource
    private OrgRepository orgRepository;

    @Test
    public void test() {
        orgRepository.create(Org.builder()
                .createBy(-1L)
                .modifiedBy(-1L)
                .corpId("testCode5")
                .fullName("浙江省约周测试组织5")
                .name("yz测试组织5")
                .superAdminUid(-1L)
                .build());
    }

    @Test
    public void testListPage() {
        Page<Org> output = orgRepository.listAllInPage(1L,2L);
        List<Org> outputRecords = output.getRecords();

        log.info("size:{}",outputRecords.size());
        log.info("outputRecords:{}",outputRecords);
    }

}
