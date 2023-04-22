package service;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.magnus.ServiceApplication;
import com.magnus.domain.dept.model.Dept;
import com.magnus.domain.dept.repository.DeptRepository;
import com.magnus.infrastructure.utils.PageOps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gaosong
 */
@Slf4j
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class PageUtilTest {
    @Resource
    DeptRepository deptRepository;

    @Test
    public void listAllInPageWithGenetic() {
        long pageSize = 20;

        List<Dept> result = PageOps.listAllInPageByFunction((pageNumber) -> deptRepository.listAllInPage(pageNumber, pageSize));

        //验证结果
        Multiset<Long> multiset = HashMultiset.create();
        for (Dept dept : result) {
            multiset.add(dept.getId());
        }

        for (Multiset.Entry<Long> longEntry : multiset.entrySet()) {
            if (longEntry.getCount() > 1) {
                log.info("more than one count:{}", longEntry.getElement());
            }
        }
        log.info("deptPage size ={}", result.size());
        log.info("output ={}", result);
    }
}
