package com.magnus.excel.biz.emp.exportexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.infra.tunnel.EmpTunnel;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author gaosong
 */
@Service
public class EmpExportService {

    @Resource
    private EmpTunnel empTunnel;

    /**
     * 获取数据库中的数据
     */
    public List<Employee> getDbRecords(Long tenantId){

        List<Employee> employees = empTunnel.listAllByTenantId(tenantId);
        return employees;
    }

    /**
     * 数据库records -> ExcelEntity
     */
    public List<EmpExcelEntity> records2ExcelEntity(List<Employee> records){

        return null;
    }

    /**
     * ExcelEntity -> File Stream
     */
    public void excelEntity2Stream(List<EmpExcelEntity> excelEntities){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(byteArrayOutputStream, EmpExcelEntity.class);

        //设置单元格模式
        excelWriterBuilder
                //内容格式
                .registerWriteHandler()

    }

}
