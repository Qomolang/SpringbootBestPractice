package com.magnus.excel.biz.emp;

import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.biz.emp.exportexcel.EmpExportService;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;

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
        List<Employee> employeeList = empExportService.getDbRecords(tenantId);

        //4. 数据库records -> ExcelEntity
        List<EmpExcelEntity> empExcelEntities = empExportService.records2ExcelEntity(employeeList);

        //5. ExcelEntity -> File Stream
        ByteArrayOutputStream fileStream = empExportService.excelEntity2Stream(empExcelEntities);

        //6. File Stream -> URL
        empExportService.stream2File(fileStream);

        //7. 返回结果
    }

}
