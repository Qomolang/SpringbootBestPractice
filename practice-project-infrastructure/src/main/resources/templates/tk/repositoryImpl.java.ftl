package ${repositoryImplPackagePath};

import ${doPackagePath}.${entity}DO;
import ${domainEntityPackagePath}.${entity};
import ${converterPackagePath}.${entity}Converter;
import ${mapperPackagePath}.${table.mapperName};
import ${superServiceImplClassPackage};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * ${table.comment!} repository实现类
 * get开头的方法为单个查询
 * list开头的方法为批量查询
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
public class ${entity}RepositoryImpl {
    @Resource
    private ${table.mapperName} mapper;
    @Resource
    private ${entity}Converter cv;

    public ${entity} getById(Long id) {
        //${entity}DO output = mapper.selectOne(new ${entity}DO().setId(id));

        ${entity}DO output = mapper.selectOneByExample(new Example.Builder(${entity}DO.class)
            .where(WeekendSqls.<${entity}DO>custom()
                .andEqualTo(${entity}DO::getId, id)
                .andEqualTo(${entity}DO::getDeleteTag, Boolean.FALSE)
            ));
        return cv.to${entity}(output);
    }

    public List<${entity}> listEntityByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<${entity}DO> output = list(Wrappers.<${entity}DO>lambdaQuery()
                .in(${entity}DO::getId, ids)
                .eq(${entity}DO::getDeleteTag, Boolean.FALSE)
        );

        return cv.to${entity}(output);
    }
    
    public Page<${entity}> listAllInPage(Long pageNumber, Long pageSize) {

        Page<${entity}DO> page = new Page<>(pageNumber, pageSize);

        Page<${entity}DO> pageResult = this.page(page, Wrappers.<${entity}DO>lambdaQuery()
                .eq(${entity}DO::getDeleteTag, Boolean.FALSE));

        if (pageResult == null) {
            return new Page();
        }

        Page<${entity}> output = Page.of(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal());
        output.setRecords(cv.to${entity}(pageResult.getRecords()));

        return output;
    }

    public ${entity} create(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.to${entity}(entityDO);
    }

    public List<${entity}> createBatch(Collection<${entity}> domains) {
        List<${entity}DO> entityDOs = cv.to${entity}DO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.to${entity}(entityDOs);
    }

    public boolean updateById(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);

        return update(entityDO, Wrappers.<${entity}DO>lambdaQuery()
                .eq(${entity}DO::getId, domain.getId())
                .eq(${entity}DO::getDeleteTag, 0)
        );
    }

    public boolean deleteLogicallyById(Long id) {

        update(Wrappers.<${entity}DO>lambdaUpdate()
                .eq(${entity}DO::getId, id)
                .set(${entity}DO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }

    public boolean deleteLogicallyByIds(Collection<Long> ids) {

        update(Wrappers.<${entity}DO>lambdaUpdate()
                .in(${entity}DO::getId, ids)
                .set(${entity}DO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }


}
</#if>
