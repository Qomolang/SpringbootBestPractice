package com.magnus.excel.biz.emp;

import com.magnus.ServiceApplication;
import com.magnus.excel.biz.emp.exportexcel.EmpExportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author gaosong
 */
@RunWith(SpringRunner.class)
//加载测试需要的类，一定要加入启动类，其次是本类
@SpringBootTest(classes = ServiceApplication.class)
public class EmpBizServiceTest {

//    @Mock
//    private EmpExportService empExportService;
//
//    @InjectMocks
//    private EmpBizService empBizService;

    @Resource
    private EmpBizService exportSync;

    @Test
    public void exportSync() {
        exportSync.exportSync(1L,1L);
    }
}