package ${servicePackagePath};

import ${domainEntityPackagePath}.${entity};
import ${repositoryPackagePath}.${entity}Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * ${table.comment!} ReadService类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
class ${entity}BizService
<#else>
@Service
public class ${entity}ReadService {

    @Resource
    private ${entity}Repository repository;

    /**
     * 根据 ID 查找 ${table.comment!}
     * @param id
     * @return
     */
    public ${entity} getById(Long id){
        //todo 补充校验
        return repository.getById(id);
    }
}
</#if>
