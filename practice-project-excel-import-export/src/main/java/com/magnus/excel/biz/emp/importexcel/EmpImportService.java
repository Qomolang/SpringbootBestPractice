package com.magnus.excel.biz.emp.importexcel;

import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.infra.errorhandler.ImportErrorHandlerService;
import com.magnus.excel.biz.emp.converter.EmpExcelConverter;
import com.magnus.excel.biz.filterchain.EmpContext;
import com.magnus.excel.biz.filterchain.EmpFilterChain;
import com.magnus.excel.infra.enums.ErrorHandleModeEnum;
import com.magnus.excel.infra.tunnel.EmpTunnel;
import com.magnus.excel.infra.utils.EasyExcelUtils;
import com.magnus.excel.infra.errorhandler.error.ImportResult;
import com.magnus.excel.infra.errorhandler.error.importcheck.ImportErrorMsg;
import com.magnus.excel.infra.errorhandler.error.importcheck.ImportCheckResult;
import com.magnus.excel.model.emp.EmpExcelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author gaosong
 */
@Slf4j
@Service
public class EmpImportService {

    @Resource
    private EmpExcelConverter cv;
    @Resource
    private EmpFilterChain empFilterChain;
    @Resource
    private EmpTunnel empTunnel;
    @Resource
    private ImportErrorHandlerService importErrorHandlerService;

    public void doImportAsync(Long tenantId, String fileUrl) {

        //1. 获取File Stream
        ByteArrayInputStream inputStream = this.getFileStream(tenantId, fileUrl);

        //2. File Stream -> ExcelEntity
        ImportCheckResult<List<EmpExcelEntity>> importCheckResult = this.stream2ExcelEntity(inputStream);

        if (!importCheckResult.isSuccess()) {
            //3.1 转换失败 进入错误处理流程
            ImportErrorMsg importErrorMsg = importCheckResult.getErrorMsg();
            log.info("emp import fail excelErrorMsg:{}", importErrorMsg);

            ImportResult importResult = importErrorHandlerService.handleImportErrorMsg(importErrorMsg, ErrorHandleModeEnum.FRONTEND_SHOWING);
            log.info("emp import fail importResult:{}", importResult);

        } else {
            //3.2 转换成功 准备导入
            //ExcelEntity -> 数据库records
            List<Employee> employeeList = this.excelEntity2Records(importCheckResult.getResult());
            log.info("emp import success employeeList:{}", employeeList);


            //todo 执行导入

        }

    }

    /**
     * 获取文件流
     * 如果前端直接上传，则此步可跳过
     * 如果前端先上传到OSS，则通过前端上传路径从OSS获得文件流
     */
    public ByteArrayInputStream getFileStream(Long tenantId, String fileUrl) {
        //1. 获取文件流
        File file = new File(fileUrl);
        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //2. 将流变为可重复读流
        ByteArrayInputStream fileStream = EasyExcelUtils.repeatableStream(fileInputStream);

        return fileStream;
    }

    /**
     * 文件流 -> ExcelEntity
     */
    public ImportCheckResult<List<EmpExcelEntity>> stream2ExcelEntity(ByteArrayInputStream fileStream) {

        //责任链校验
        ImportCheckResult<List<EmpExcelEntity>> importCheckResult = empFilterChain.doCheck(EmpContext.builder()
                .fileStream(fileStream)
                .build());


        return importCheckResult;
    }

    /**
     * ExcelEntity -> 数据库records
     */
    public List<Employee> excelEntity2Records(List<EmpExcelEntity> excelEntities) {
        return cv.excelEntity2Records(excelEntities);
    }

}
