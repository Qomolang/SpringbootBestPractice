package com.magnus.excel.biz.emp.converter;

import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.org.model.Org;
import com.magnus.excel.model.emp.EmpExcelEntity;
import com.magnus.infrastructure.dao.org.model.OrgDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Org 转换器
 *
 * @author mybatis-plus-auto-generator
 * @description
 * @since 2022-09-08
 */
@Mapper(componentModel = "spring")
public interface EmpExcelConverter {

    List<EmpExcelEntity> records2ExcelEntity(List<Employee> records);

}
