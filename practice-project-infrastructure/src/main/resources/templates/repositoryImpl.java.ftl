package ${repositoryImplPackagePath};

import ${doPackagePath}.${entity}DO;
import ${domainEntityPackagePath}.${entity};
import ${converterPackagePath}.${entity}Converter;
import ${mapperPackagePath}.${table.mapperName};
import ${repositoryPackagePath}.${entity}Repository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${superServiceImplClassPackage};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    private ${entity}Converter cv;

    @Override
    public ${entity} findOneById(Long id) {
        return cv.to${entity}(getById(id));
    }

    @Override
    public List<${entity}> listAllByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<${entity}DO> output = list(new QueryWrapper<${entity}DO>()
                                             .in(${entity}DO.ID, ids));

        return cv.to${entity}(output);
    }

    @Override
    public ${entity} create(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.to${entity}(entityDO);
    }

    @Override
    public ${entity} update(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);

        update(entityDO, new QueryWrapper<${entity}DO>()
                .eq(${entity}DO.ID, domain.getId())
                //有逻辑删字段要解除下面的注释
                //eq(${entity}DO.IS_DELETED, 0)
                );

        return cv.to${entity}(entityDO);
    }

    @Override
    public boolean deleteById(Long id) {
        //todo 此处为物理删，如果为逻辑删需要手动更改
        return super.removeById(id);
    }

}
</#if>
