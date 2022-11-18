package com.magnus.excel.biz.emp.importexcel;

import com.alibaba.excel.EasyExcel;
import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.infra.common.EasyExcelUtils;
import com.magnus.excel.model.emp.EmpExcelEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * @author gaosong
 */
@Slf4j
@Service
public class EmpImportService {

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
    public List<EmpExcelEntity> stream2ExcelEntity(ByteArrayInputStream fileStream) {
        //1. 责任链校验

        //校验不通过，

        return null;
    }

    /**
     * ExcelEntity -> 数据库records
     */
    public List<Employee> excelEntity2Records(List<EmpExcelEntity> excelEntities) {
        return null;
    }

}
