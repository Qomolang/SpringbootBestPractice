package com.magnus.excel.biz.emp;

import com.google.common.collect.Lists;
import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.biz.emp.exportexcel.EmpExportService;
import com.magnus.excel.biz.emp.importexcel.EmpImportService;
import com.magnus.excel.infra.utils.RedisKeyFactory;
import com.magnus.excel.infra.common.enums.ExcelActionEnum;
import com.magnus.excel.infra.common.enums.ExcelSceneEnum;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        String exportRedisKey = RedisKeyFactory.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.EXPORTING, String.valueOf(tenantId), String.valueOf(userId));
        //todo 从redis中找到
        boolean exportFlag = true;
        if (exportFlag) {
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
     * 模板异步导出没有意义，因此异步导出仅指导出数据，不用再在名称上体现
     */
    public void exportAsync(Long tenantId, Long userId) {
        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认
        String exportRedisKey = RedisKeyFactory.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.EXPORTING, String.valueOf(tenantId), String.valueOf(userId));
        //todo 从redis中找到
        boolean exportFlag = true;
        if (exportFlag) {
            throw new RuntimeException("正在导出中，请稍等");
        }

        //todo 以下步骤异步进行

        //缓存中加 正在导出的flag

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
    public void pollExportAsyncResult(Long tenantId, Long userId){

    }

    /**
     * 异步导入
     */
    public void importAsync(Long tenantId, Long userId, String fileUrl) {

        //1, 校验权限

        //2. 校验是否已经正在导入，任务类型+唯一键确认
        String importRedisKey = RedisKeyFactory.buildRedisKey(ExcelSceneEnum.EMP, ExcelActionEnum.IMPORTING, String.valueOf(tenantId), String.valueOf(userId));
        //todo 从redis中找到
        boolean importFlag = true;
        if (importFlag) {
            throw new RuntimeException("正在导入中，请稍等");
        }

        //todo 打上导入标

        //3. 获取File Stream
        ByteArrayInputStream inputStream = empImportService.getFileStream(tenantId, fileUrl);

        //4. File Stream -> ExcelEntity
        List<EmpExcelEntity> empExcelEntityList = empImportService.stream2ExcelEntity(inputStream);

        //5. ExcelEntity -> 数据库records
        List<Employee> employeeList = empImportService.excelEntity2Records(empExcelEntityList);

        //6. 执行插入

    }

    /**
     * 轮询检查异步导入结果
     */
    public void pollImportAsyncResult(Long tenantId, Long userId) {
        //todo 看readme 调整消除缓存的位置

        //1. 查该任务是否正在异步导出

        //2 false则返回“没有导出”

        //3. 查询异步导出结果flag

        //4. 为false 返回“正在导出”

        //5. 当为true时，查询结果

        //6. 销毁两个标记

        //7. 返回
    }

}
