package com.magnus.domain.dept.converter;

import com.magnus.domain.dept.model.Dept;
import com.magnus.infrastructure.dao.dept.model.DeptDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
* Dept 转换器
*
* @author mybatis-plus-auto-generator
* @description
* @since 2023-02-25
*/
@Mapper(componentModel = "spring")
public interface DeptConverter {
    /**
     * Dept: DO -> Domain
     *
     * @param entityDO
     * @return
     */
    Dept toDept(DeptDO entityDO);

    /**
     * Dept: DO list-> Domain list
     *
     * @param entityDO
     * @return
     */
    List<Dept> toDept(List<DeptDO> entityDO);

    /**
     * Dept: Domain -> DO
     *
     * @param domain
     * @return
     */
    DeptDO toDeptDO(Dept domain);

    /**
     * Dept: Domain list -> DO list
     *
     * @param domain
     * @return
     */
    List<DeptDO> toDeptDO(List<Dept> domain);

}
