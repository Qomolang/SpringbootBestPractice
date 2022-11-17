package com.magnus.excel.biz.emp.importexcel;

import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author gaosong
 */
@Service
public class EmpImportService {

    /**
     * 获取文件流
     * 如果前端直接上传，则此步可跳过
     * 如果前端先上传到OSS，则通过前端上传路径从OSS获得文件流
     */
    public List<Employee> getFileStream(Long tenantId, String fileUrl) {
        return null;
    }

    /**
     * 文件流 -> ExcelEntity
     */
    public List<EmpExcelEntity> stream2ExcelEntity() {
        return null;
    }

    /**
     * ExcelEntity -> 数据库records
     */
    public List<Employee> excelEntity2Records(List<EmpExcelEntity> excelEntities) {
        return null;
    }

}
