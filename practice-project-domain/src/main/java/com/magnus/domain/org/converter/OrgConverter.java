package com.magnus.domain.org.converter;

import com.magnus.domain.org.model.Org;
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
public interface OrgConverter {
    /**
    * Org: DO -> Domain
    *
    * @param entityDO
    * @return
    */
    Org toOrg(OrgDO entityDO);

    /**
    * Org: DO list-> Domain list
    *
    * @param entityDO
    * @return
    */
    List<Org> toOrg(List<OrgDO> entityDO);

    /**
    * Org: Domain -> DO
    *
    * @param domain
    * @return
    */
    OrgDO toOrgDO(Org domain);

    /**
    * Org: Domain list -> DO list
    *
    * @param domain
    * @return
    */
    List<OrgDO> toOrgDO(List<Org> domain);

}
