package ${repositoryImplPackagePath};

import ${doPackagePath}.${entity}DO;
import ${domainEntityPackagePath}.${entity};
import ${converterPackagePath}.${entity}Converter;
import ${mapperPackagePath}.${table.mapperName};
import ${repositoryPackagePath}.${entity}Repository;
import ${superServiceImplClassPackage};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

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
        UserDO output = this.baseMapper.selectOne(Wrappers.<${entity}DO>lambdaQuery()
                .eq(${entity}DO::getId, id)
                .eq(${entity}DO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toUser(output);
    }

    @Override
    public List<${entity}> listAllByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<${entity}DO> output = list(Wrappers.<${entity}DO>lambdaQuery()
                .in(UserDO::getId, ids)
                .eq(UserDO::getDeleteTag, Boolean.FALSE)
        );

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
    public List<${entity}> createBatch(List<${entity}> domains) {
        List<${entity}DO> entityDOs = cv.to${entity}DO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.to${entity}(entityDOs);
    }

    @Override
    public ${entity} update(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);

        update(entityDO, Wrappers.<${entity}DO>lambdaQuery()
                .eq(${entity}DO::getId, domain.getId())
                .eq(${entity}DO::getDeleteTag, 0)
        );

        return cv.to${entity}(entityDO);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteLogicallyById(Long id) {

        update(Wrappers.<${entity}DO>lambdaUpdate()
                .eq(${entity}DO::getId, id)
                .set(${entity}DO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }

    @Override
    public boolean deleteLogicallyByIds(Collection<Long> ids) {

        update(Wrappers.<${entity}DO>lambdaUpdate()
                .in(${entity}DO::getId, ids)
                .set(${entity}DO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }


}
</#if>
