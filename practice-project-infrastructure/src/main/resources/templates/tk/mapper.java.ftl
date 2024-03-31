package ${mapperPackagePath};

import ${doPackagePath}.${entity}DO;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}DO>
<#else>
public interface ${table.mapperName} extends $Mapper<${entity}DO> {

}
</#if>
