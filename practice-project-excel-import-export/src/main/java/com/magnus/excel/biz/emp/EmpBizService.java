package com.magnus.excel.biz.emp;

import com.google.common.collect.Lists;
import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.biz.emp.exportexcel.EmpExportService;
import com.magnus.excel.biz.emp.importexcel.EmpImportService;
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
    @Resource
    private EmpImportService empImportService;


    /**
     * 同步导出
     * 仅推荐模板采用同步导出
     */
    public void exportSync(Long tenantId, Long userId) {
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认

        //3. 构建ExcelEntity
        List<EmpExcelEntity> empExcelEntities = Lists.newArrayList(EmpExcelEntity.builder().build());

        //4. ExcelEntity -> File Stream
        ByteArrayOutputStream fileStream = empExportService.excelEntity2Stream(empExcelEntities);

        //5. File Stream -> URL
        String filePath = empExportService.fileStream2FilePath(fileStream);

        //6. 返回结果（如果上传云端，则返回url等下载凭证；如果返回文件，则直接返回文件流）
    }

    /**
     * 异步导出
     */
    public void exportAsync(Long tenantId, Long userId) {
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认

        //todo 看readme 调整消除缓存的位置
        //todo 以下步骤异步进行

        //缓存中加 正在异步导出的flag

        //3. 获取数据库中的records
        List<Employee> employeeList = empExportService.getDbRecords(tenantId);

        //4. 数据库records -> ExcelEntity
        List<EmpExcelEntity> empExcelEntities = empExportService.records2ExcelEntity(employeeList);

        //5. ExcelEntity -> File Stream
        ByteArrayOutputStream fileStream = empExportService.excelEntity2Stream(empExcelEntities);

        //6. File Stream -> URL
        String filePath = empExportService.fileStream2FilePath(fileStream);

        //7. 将结果保存至缓存中

        //8. 将异步导出结果flag标为true
    }

    /**
     * 轮询检查异步导入结果
     */
    public void pollImportAsyncResult(Long tenantId, Long userId){
        //1. 查该任务是否正在异步导出

        //2 false则返回“没有导出”

        //3. 查询异步导出结果flag

        //4. 为false 返回“正在导出”

        //5. 当为true时，查询结果

        //6. 销毁两个标记

        //7. 返回
    }

    /**
     * 异步导入
     */
    public void importAsync(Long tenantId, Long userId, String fileUrl) {
        //1, 校验权限

        //2. 校验是否已经正在导入，任务类型+唯一键确认

        //3. 获取File Stream
        empImportService.getFileStream(tenantId, fileUrl);

        //4. File Stream -> ExcelEntity
        List<EmpExcelEntity> empExcelEntityList = empImportService.stream2ExcelEntity();

        //5. ExcelEntity -> 数据库records
        List<Employee> employeeList = empImportService.excelEntity2Records(empExcelEntityList);

        //6. 执行插入

    }

}
