package service.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magnus.ServiceApplication;
import com.magnus.domain.dept.model.Dept;
import com.magnus.domain.dept.repository.DeptRepository;
import com.magnus.domain.org.model.Org;
import com.magnus.domain.org.repository.OrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class DeptTest {

    @Resource
    private DeptRepository deptRepository;

    @Test
    public void test() {
        ExecutorService threadPool = Executors.newFixedThreadPool(500);

        CyclicBarrier barrier = new CyclicBarrier(500, () -> {
            log.info("500一批放行");
        });

        for (int j = 0; j < 10000; j++) {
            threadPool.execute(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
                deptRepository.addMemberSizeOne(10L);
            });
        }

        threadPool.shutdown();

        //测试线程似乎不会等线程池任务执行结束再结束，而是直接停止程序，因此手动检测
        for (; ; ) {
            try {
                Thread.sleep(1000);
                log.info("等候一秒");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (threadPool.isTerminated()){
                break;
            }
        }

//        deptRepository.create(Dept.builder()
//                .createBy(-1L)
//                .modifiedBy(-1L)
//                .orgId("testCode5")
//                .name("yz测试部门5")
//                .memberSize(0L)
//                .build());
    }

}
