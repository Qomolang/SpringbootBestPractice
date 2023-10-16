package ${converterPackagePath};

import ${domainEntityPackagePath}.${entity};
import ${doPackagePath}.${entity}DO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

/**
* ${entity} 转换器
*
* @author mybatis-plus-auto-generator
* @description
* @since ${date}
*/
@Mapper(componentModel = "spring")
public interface ${entity}Converter {
    /**
     * ${entity}: DO -> Domain
     *
     * @param entityDO
     * @return
     */
    ${entity} to${entity}(${entity}DO entityDO);

    /**
     * ${entity}: DO list-> Domain list
     *
     * @param entityDO
     * @return
     */
    List<${entity}> to${entity}(Collection<${entity}DO> entityDO);

    /**
     * ${entity}: Domain -> DO
     *
     * @param domain
     * @return
     */
    ${entity}DO to${entity}DO(${entity} domain);

    /**
     * ${entity}: Domain list -> DO list
     *
     * @param domain
     * @return
     */
    List<${entity}DO> to${entity}DO(Collection<${entity}> domain);

}
