package ${mapperPackagePath};

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface ${table.mapperName} {

}
</#if>
