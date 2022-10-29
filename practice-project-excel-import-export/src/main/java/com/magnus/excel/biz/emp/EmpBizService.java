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

    /**
     * 同步导出（模板）
     */
    public void exportSync(Long tenantId, Long userId){
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认

        //3. 获取数据库中的records

        //4. 数据库records -> ExcelEntity

        //5. ExcelEntity -> File Stream

        //6. File Stream -> URL

        //7. 返回结果
    }

}
