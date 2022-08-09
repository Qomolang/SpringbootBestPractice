package ${repositoryImplPackagePath};

import ${doPackagePath}.${entity}DO;
import ${domainEntityPackagePath}.${entity};
import ${converterPackagePath}.${entity}Converter;
import ${mapperPackagePath}.${table.mapperName};
import ${repositoryPackagePath}.${entity}Repository;
import ${superServiceImplClassPackage};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

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
    public ${entity} getOneById(Long id) {
        return cv.to${entity}(getById(id));
    }

    @Override
    public List<${entity}> listAllByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<${entity}DO> output = list(new LambdaQueryWrapper<${entity}DO>()
                .in(${entity}DO::getId, ids));

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

        update(entityDO, new LambdaUpdateWrapper<${entity}DO>()
                .eq(${entity}DO::getId, domain.getId())
                .eq(${entity}DO::getDeleteTag, 0));

        return cv.to${entity}(entityDO);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteLogicallyById(Long id) {

        update(new LambdaUpdateWrapper<${entity}DO>()
                .eq(${entity}DO::getId, id)
                //todo 根据情况调整
                .set(${entity}DO::getDeleteTag, Boolean.TRUE));

        return true;
    }


}
</#if>
