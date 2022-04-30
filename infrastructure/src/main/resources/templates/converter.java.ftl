package ${package};

<#list modelNames as modelName>
    import ${domainPackage}.${modelName};
</#list>
<#list modelNames as modelName>
    import ${modelPackage}.${modelName}DO;
</#list>
import org.mapstruct.Mapper;

/**
* ${moduleName} 转换器
*
* @author mybatis-plus-auto-generator
* @description
* @since ${date}
*/
@Mapper(componentModel = "spring")
public interface ${moduleName}Converter {
<#list modelNames as modelName>
    /**
    * ${modelName}: DO -> Domain
    *
    * @param entityDO
    * @return
    */
    ${modelName} to${modelName}(${modelName}DO entityDO);

    /**
    * ${modelName}: Domain -> DO
    *
    * @param domain
    * @return
    */
    ${modelName}DO to${modelName}DO(${modelName} domain);
</#list>
}
