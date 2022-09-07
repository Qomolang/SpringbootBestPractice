package com.magnus.domain.employee.converter;

import com.magnus.domain.employee.model.Employee;
import com.magnus.infrastructure.dao.employee.model.EmployeeDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
* Employee 转换器
*
* @author mybatis-plus-auto-generator
* @description
* @since 2022-09-08
*/
@Mapper(componentModel = "spring")
public interface EmployeeConverter {
    /**
    * Employee: DO -> Domain
    *
    * @param entityDO
    * @return
    */
    Employee toEmployee(EmployeeDO entityDO);

    /**
    * Employee: DO list-> Domain list
    *
    * @param entityDO
    * @return
    */
    List<Employee> toEmployee(List<EmployeeDO> entityDO);

    /**
    * Employee: Domain -> DO
    *
    * @param domain
    * @return
    */
    EmployeeDO toEmployeeDO(Employee domain);

    /**
    * Employee: Domain list -> DO list
    *
    * @param domain
    * @return
    */
    List<EmployeeDO> toEmployeeDO(List<Employee> domain);

}
