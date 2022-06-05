package ${serviceConverterPackagePath};

import ${domainEntityPackagePath}.${entity};
import ${requestPackagePath}.${entity}ExampleRequest;
import org.mapstruct.Mapper;

/**
* ${entity} controller-service 层转换器
* request -> command
*
* @author mybatis-plus-auto-generator
* @description
* @since ${date}
*/
@Mapper(componentModel = "spring")
public interface ${entity}ServiceConverter {

    ${entity} to${entity}(${entity}ExampleRequest request);

}
