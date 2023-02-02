package com.magnus.excel.biz.emp;

import com.google.common.collect.Lists;
import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.api.ExcelImportResponse;
import com.magnus.excel.biz.emp.exportexcel.EmpExportService;
import com.magnus.excel.biz.emp.importexcel.EmpImportService;
import com.magnus.excel.infra.ExcelThreadService;
import com.magnus.excel.infra.model.constants.RedisFlagConstants;
import com.magnus.excel.biz.model.ExcelFlagEnum;
import com.magnus.excel.infra.model.error.ImportErrorResult;
import com.magnus.excel.infra.utils.RedisKeyOps;
import com.magnus.excel.biz.model.ExcelActionEnum;
import com.magnus.excel.biz.model.ExcelSceneEnum;
import com.magnus.excel.biz.model.emp.EmpExcelEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 请注意，一般同步导入/出数据和异步导入/出数据只需要一个，请酌情删除另一个
 *
 * @author gaosong
 */
@Service
public class EmpBizService {

    @Resource
    private EmpExportService empExportService;
    @Resource
    private EmpImportService empImportService;
    @Resource
    private ExcelThreadService excelThreadService;
    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * 同步导出模板
     * 同步导出推荐场景：1.导出模板，2. 导出数据 数据量有限且链路简单
     */
    public void exportTemplateSync(Long tenantId, Long userId) {
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认

        //3. 构建ExcelEntity
        List<EmpExcelEntity> empExcelEntities = Lists.newArrayList(EmpExcelEntity.builder().build());

        //4. ExcelEntity -> File Stream
        ByteArrayOutputStream fileStream = empExportService.excelEntity2FileStream(empExcelEntities);

        //5. File Stream -> URL
        String filePath = empExportService.fileStream2FilePath(fileStream);

        //6. 返回结果（如果上传云端，则返回url等下载凭证；如果返回文件，则直接返回文件流）
    }

    /**
     * 同步导出数据
     * 同步导出推荐场景：1.导出模板，2. 导出数据 数据量有限且链路简单
     */
    public void exportDataSync(Long tenantId, Long userId) {
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认
        String exportRedisKey = EmpExcelCacheKeyFactory.buildExportStatusKey(tenantId, userId);
        String exportingStatusFlag = (String) redisTemplate.opsForValue().get(exportRedisKey);
        if (StringUtils.isNotBlank(exportingStatusFlag)) {
            throw new RuntimeException("正在导出中，请稍等");
        }

        //3. 获取数据库中的records
        List<Employee> employeeList = empExportService.getDbRecords(tenantId);

        //4. 数据库records -> ExcelEntity
        List<EmpExcelEntity> empExcelEntities = empExportService.records2ExcelEntity(employeeList);

        //5. ExcelEntity -> File Stream 设置格式等
        ByteArrayOutputStream fileStream = empExportService.excelEntity2FileStream(empExcelEntities);

        //6. File Stream -> URL
        String filePath = empExportService.fileStream2FilePath(fileStream);

        //7. 返回结果（如果上传云端，则返回url等下载凭证；如果返回文件，则直接返回文件流）
    }

    /**
     * 异步导出
     * 模板异步导出没有意义，因此异步导出仅指导出数据
     */
    public void exportDataAsync(Long tenantId, Long userId) {
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认
        String exportRedisKey = EmpExcelCacheKeyFactory.buildExportStatusKey(tenantId, userId);
        String exportingStatusFlag = (String) redisTemplate.opsForValue().get(exportRedisKey);
        if (StringUtils.isNotBlank(exportingStatusFlag)) {
            throw new RuntimeException("正在导出中，请稍等");
        }

        //todo 以下步骤异步进行

        //3. 获取数据库中的records
        List<Employee> employeeList = empExportService.getDbRecords(tenantId);

        //4. 数据库records -> ExcelEntity
        List<EmpExcelEntity> empExcelEntities = empExportService.records2ExcelEntity(employeeList);

        //5. ExcelEntity -> File Stream 设置格式等
        ByteArrayOutputStream fileStream = empExportService.excelEntity2FileStream(empExcelEntities);

        //6. File Stream -> URL
        String filePath = empExportService.fileStream2FilePath(fileStream);

        //7. 将结果保存至缓存中

        //8. 将异步导出结果flag标为true
    }

    /**
     * 轮询检查异步导出结果
     */
    public void pollExportAsyncResult(Long tenantId, Long userId) {

    }

    /**
     * 异步导入
     */
    public void importAsync(Long tenantId, Long userId, String fileUrl) {

        //1, 校验权限

        //2. 校验是否已经正在导入，任务类型+唯一键确认
        String importRedisKey = RedisKeyOps.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.IMPORTING, ExcelFlagEnum.STATUS, String.valueOf(tenantId), String.valueOf(userId));
        String importingStatusFlag = (String) redisTemplate.opsForValue().get(importRedisKey);

        //2.1 未通过 直接抛出异常
        if (StringUtils.isNotBlank(importingStatusFlag)) {
            throw new RuntimeException("正在导出中，请稍等");
        }

        //2.2 通过 打上导入标（如果发现用户已经在导入，不允许用户再次进行导入）
        redisTemplate.opsForValue().set(importRedisKey, RedisFlagConstants.IMPORTING_STATUS_FLAG_VALUE);

        //删除用户上次导入结果
        String resultKey = RedisKeyOps.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.IMPORTING, ExcelFlagEnum.RESULT, String.valueOf(tenantId), String.valueOf(userId));
        redisTemplate.delete(resultKey);


        //6. 执行插入
        empImportService.doImportAsync(tenantId, fileUrl);
    }

    /**
     * 轮询检查异步导入结果
     */
    public ExcelImportResponse pollImportAsyncResult(Long tenantId, Long userId) {
        ExcelImportResponse response = new ExcelImportResponse();

        //1. 查询导入标记，是否正在异步导入
        String importStatusRedisKey = RedisKeyOps.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.IMPORTING, ExcelFlagEnum.STATUS, String.valueOf(tenantId), String.valueOf(userId));
        String importStatusFlag = (String) redisTemplate.opsForValue().get(importStatusRedisKey);
        //不为空，表明正在导入中
        if (StringUtils.isNotBlank(importStatusFlag)) {
            response.setIsImporting(true);
            return response;
        }

        //2. 查询导入结果标记
        String importResultRedisKey = RedisKeyOps.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.IMPORTING, ExcelFlagEnum.STATUS, String.valueOf(tenantId), String.valueOf(userId));
        ImportErrorResult importErrorResult = (ImportErrorResult) redisTemplate.opsForValue().get(importResultRedisKey);
        //导入状态为空，导入结果也为空，说明没有触发过导入
        if (importErrorResult == null) {
            response.setIsImporting(false);
            return response;
        }

        //3. 错误处理
        //普通错误不为空，说明导入结果失败
        if (StringUtils.isNotBlank(importErrorResult.getPlainErrorMsg())) {
            response.setIsSuccess(false);
            response.setPlainError(importErrorResult.getPlainErrorMsg());
        }
        //数据格式错误不为空，也说明导入结果失败
        if (StringUtils.isNotBlank(importErrorResult.getErrorExcelLink())) {
            response.setIsSuccess(false);
            response.setErrorMsgDownloadUrl(importErrorResult.getErrorExcelLink());
        }
        if (CollectionUtils.isNotEmpty(importErrorResult.getDataFormatErrorMsgList())) {
            response.setIsSuccess(false);
            response.setDataFormatErrorMsgList(importErrorResult.getDataFormatErrorMsgList());
        }

        //4. 没有错误，说明成功
        response.setIsSuccess(true);
        return response;
    }

}
