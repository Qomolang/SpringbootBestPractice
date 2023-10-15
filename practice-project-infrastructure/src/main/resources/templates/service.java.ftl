package ${servicePackagePath};

import ${domainEntityPackagePath}.${entity};
import ${repositoryPackagePath}.${entity}Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * ${table.comment!} bizService类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
class ${entity}BizService
<#else>
@Service
public class ${entity}Service {

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
    };

    /**
     * 保存 ${table.comment!}
     *
     * @param domain
     * @return
    */
    public ${entity} create${entity}(${entity} domain){
        //todo 补充校验
         return repository.create(domain);
    };

    /**
     * 根据 ID 删除某个 ${table.comment!}
     *
     * @param id
     * @return 删除是否成功
     */
    public boolean delete${entity}ById(Long id){
        //todo 补充校验
        return repository.deleteById(id);
    };
}
</#if>
