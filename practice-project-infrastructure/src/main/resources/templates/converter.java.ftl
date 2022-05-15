package ${converterPackagePath};

import ${domainEntityPackagePath}.${entity};
import ${doPackagePath}.${entity}DO;
import org.mapstruct.Mapper;

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
    //todo 检查数据库中是否有is开头的布尔字段 如果有可以考虑entity中设置为布尔 并在这里int转boolean
    ${entity} to${entity}(${entity}DO entityDO);

    /**
    * ${entity}: Domain -> DO
    *
    * @param domain
    * @return
    */
    //todo 检查数据库中是否有is开头的布尔字段 如果有可以考虑entity中设置为布尔 并在这里boolean转int
    ${entity}DO to${entity}DO(${entity} domain);
}
