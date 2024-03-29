package com.magnus.excel.biz.service.emp.importexcel;

import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.biz.service.emp.EmpExcelCacheKeyFactory;
import com.magnus.excel.infra.ImportErrorHandlerService;
import com.magnus.excel.biz.service.emp.converter.EmpExcelConverter;
import com.magnus.excel.biz.model.emp.EmpContext;
import com.magnus.excel.infra.model.enums.ErrorHandleModeEnum;
import com.magnus.excel.infra.tunnel.EmpTunnel;
import com.magnus.excel.infra.utils.EasyExcelOps;
import com.magnus.excel.infra.model.error.ImportErrorResult;
import com.magnus.excel.infra.model.error.importcheck.ImportErrorMsg;
import com.magnus.excel.infra.model.error.importcheck.ImportCheckResult;
import com.magnus.excel.biz.model.emp.EmpExcelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.magnus.excel.biz.model.emp.EmpExcelBizConfig.IMPORT_RESULT_DURATION_SECONDS;

/**
 * @author gaosong
 */
@Slf4j
@Service
public class EmpImportService {

    @Resource
    private EmpExcelConverter cv;
    @Resource
    private EmpImportFilterChain empImportFilterChain;
    @Resource
    private EmpTunnel empTunnel;
    @Resource
    private ImportErrorHandlerService importErrorHandlerService;
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    public void doImportAsync(Long tenantId, Long userId, String fileUrl) {

        try {
            ImportCheckResult<List<EmpExcelEntity>> importCheckResult = null;

            //1. 获取File Stream
            ByteArrayInputStream inputStream = null;
            try {
                inputStream = this.getFileStream(tenantId, fileUrl);
            } catch (Exception e) {
                log.error("EmpImportService doImportAsync 获取文件失败");
                importCheckResult = ImportCheckResult.buildFailureResult(ImportErrorMsg.builder()
                        .plainErrorMsg("获取excel文件失败")
                        .build());
            }

            //2. File Stream -> ExcelEntity
            if (importCheckResult == null) {
                importCheckResult = this.stream2ExcelEntity(EmpContext.builder()
                        .fileStream(inputStream)
                        .build());
            }

            //3. 转换后处理
            if (!importCheckResult.isSuccess()) {
                //3.1 转换失败 进入错误处理流程
                ImportErrorMsg importErrorMsg = importCheckResult.getErrorMsg();
                log.info("emp import fail excelErrorMsg:{}", importErrorMsg);

                ImportErrorResult importErrorResult = importErrorHandlerService.handleImportErrorMsg(importErrorMsg, ErrorHandleModeEnum.FRONTEND_SHOWING);
                log.info("emp import fail importResult:{}", importErrorResult);

            } else {
                //3.2 转换成功 准备导入
                //ExcelEntity -> 数据库records
                List<Employee> employeeList = this.excelEntity2Records(importCheckResult.getResult());
                log.info("emp import success employeeList:{}", employeeList);

                //todo 执行导入

                //如果成功，
                String importResultRedisKey = EmpExcelCacheKeyFactory.buildImportResultKey(tenantId, userId);
                redisTemplate.opsForValue().set(importResultRedisKey, new ImportErrorResult(), IMPORT_RESULT_DURATION_SECONDS, TimeUnit.SECONDS);

                //如果失败，进入错误处理流程
            }
        } catch (Exception e) {
            log.error("EmpImportService doImportAsync e:", e);
            //todo 进入错误处理流程

        } finally {
            String importStatusRedisKey = EmpExcelCacheKeyFactory.buildImportStatusKey(tenantId, userId);
            redisTemplate.delete(importStatusRedisKey);
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
            throw new RuntimeException("获取文件失败");
        }

        //2. 将流变为可重复读流
        ByteArrayInputStream fileStream = EasyExcelOps.repeatableStream(fileInputStream);

        return fileStream;
    }

    /**
     * 文件流 -> ExcelEntity
     */
    public ImportCheckResult<List<EmpExcelEntity>> stream2ExcelEntity(EmpContext empContext) {

        //责任链校验
        ImportCheckResult<List<EmpExcelEntity>> importCheckResult = empImportFilterChain.doCheck(empContext);

        return importCheckResult;
    }

    /**
     * ExcelEntity -> 数据库records
     */
    public List<Employee> excelEntity2Records(List<EmpExcelEntity> excelEntities) {
        return cv.excelEntity2Records(excelEntities);
    }

}
