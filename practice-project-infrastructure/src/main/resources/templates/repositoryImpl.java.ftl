package ${package.ServiceImpl};

import ${package.Entity}.${entity}DO;
import ${package.Converter}.${cfg.moduleName}Converter;
import ${package.Domain}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${entity}Repository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${superServiceImplClassPackage};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * <p>
 * ${table.comment!} repository实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Repository
<#if kotlin>
open class ${entity}RepositoryImpl : ${superServiceImplClass}<${table.mapperName}, ${entity}DO>(), ${entity}Repository {

}
<#else>
public class ${entity}RepositoryImpl extends ${superServiceImplClass}<${table.mapperName}, ${entity}DO> implements ${entity}Repository {
    @Resource
    private ${cfg.moduleName}Converter cv;

    @Override
    public ${entity} findOneById(Long id) {
        return cv.to${entity}(getById(id));
    }

    @Override
    public Map<Long, ${entity}> listAll() {
        return list()
            .stream()
            .map(cv::to${entity})
            .collect(Collectors.toMap(${entity}::getId, Function.identity()));
    }

    @Override
    public Map<Long, ${entity}> listAllByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyMap();
        }

        return list(new QueryWrapper<${entity}DO>()
            .in(${entity}DO.ID, ids))
            .stream()
            .map(cv::to${entity})
            .collect(Collectors.toMap(${entity}::getId, Function.identity()));
    }

    @Override
    public ${entity} create(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段需要在这里赋值
        return cv.to${entity}(entityDO);
    }

    @Override
    public boolean deleteById(Long id) {
        //todo 物理删
        return super.removeById(id);
    }

}
</#if>
