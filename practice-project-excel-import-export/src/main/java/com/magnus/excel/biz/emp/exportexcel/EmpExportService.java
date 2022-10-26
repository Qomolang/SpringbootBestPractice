package com.magnus.excel.biz.emp.exportexcel;

import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.model.emp.EmpExcelEntity;

import java.util.List;

/**
 * gaosong
 */
public class EmpExportService {

    /**
     * 获取数据库中的数据
     */
    public List<Employee> getDbRecords(){
        return null;
    }

    /**
     * 数据库records -> ExcelEntity
     */
    public List<EmpExcelEntity> records2ExcelEntity(List<Employee> records){
        return null;
    }

    /**
     * 数据库records -> ExcelEntity
     */
    public void excelEntity2Stream(List<EmpExcelEntity> excelEntities){

    }

}
