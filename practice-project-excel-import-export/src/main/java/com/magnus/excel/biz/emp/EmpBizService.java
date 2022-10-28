package com.magnus.excel.biz.emp;

import com.magnus.excel.biz.emp.exportexcel.EmpExportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gaosong
 */
@Service
public class EmpBizService {

    @Resource
    private EmpExportService empExportService;

}
