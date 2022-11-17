package com.magnus.excel.biz.emp.exportexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.infra.common.excelstyle.CellStyleFactory;
import com.magnus.excel.infra.tunnel.EmpTunnel;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    public ByteArrayOutputStream excelEntity2Stream(List<EmpExcelEntity> excelEntities){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ExcelWriterBuilder excelWriterBuilder = EasyExcel.write(byteArrayOutputStream, EmpExcelEntity.class);

        //设置单元格模式
        excelWriterBuilder
                //内容样式
                .registerWriteHandler(CellStyleFactory.buildCellStyle())
                //行高
                .registerWriteHandler(CellStyleFactory.buildFixedHeightStyleStrategy())
                //行宽
                .registerWriteHandler(CellStyleFactory.buildFixedWidthStyleStrategy());

        excelWriterBuilder
                .sheet("模板")
                .doWrite(excelEntities);

        return byteArrayOutputStream;
    }

    /**
     * 由文件流获得文件因子
     */
    public String fileStream2FilePath(ByteArrayOutputStream byteArrayOutputStream) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        //todo 可上传到云端，此处为方便导出本地
        File targetFile = new File("temp.xlsx");

        try {
            FileUtils.copyInputStreamToFile(inputStream, targetFile);
        } catch (IOException e) {
            throw new RuntimeException("IO异常",e);
        }

        return targetFile.getAbsolutePath();
    }

}
