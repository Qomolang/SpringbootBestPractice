package ${mapperPackagePath};

import ${doPackagePath}.${entity}DO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${table.mapperName} {

    ${entity}DO getById(@Param("id") Long id);

    List<${entity}DO> listByIds(@Param("ids") Collection<Long> ids);

    int insert(${entity}DO entity);

    int updateById(${entity}DO entity);

    int deleteById(Long id);

}
