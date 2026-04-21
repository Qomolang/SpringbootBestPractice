package ${repositoryImplPackagePath};

import ${doPackagePath}.${entity}DO;
import ${domainEntityPackagePath}.${entity};
import ${converterPackagePath}.${entity}Converter;
import ${mapperPackagePath}.${table.mapperName};
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

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
public class ${entity}RepositoryImpl {
    @Resource
    private ${table.mapperName} mapper;
    @Resource
    private ${entity}Converter cv;

    public ${entity} getById(Long id) {
        //${entity}DO output = mapper.selectOne(new ${entity}DO().setId(id));
        ${entity}DO output = mapper.getById(id);
        return cv.to${entity}(output);
    }

    public List<${entity}> listEntityByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<${entity}DO> output = mapper.listByIds(ids);

        return cv.to${entity}(output);
    }

    public ${entity} create(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);
        mapper.insert(entityDO);
        return cv.to${entity}(entityDO);
    }

    public int updateById(${entity} domain) {
        ${entity}DO entityDO = cv.to${entity}DO(domain);
        return mapper.updateById(entityDO);
    }

}