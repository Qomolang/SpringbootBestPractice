package ${servicePackagePath};

import ${domainEntityPackagePath}.${entity};
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * ${table.comment!} BizService类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
class ${entity}BizService
<#else>
@Service
public class ${entity}BizService {

    @Resource
    private ${entity}WriteService writeService;
    @Resource
    private ${entity}ReadService readService;

    /**
     * 保存 ${table.comment!}
     *
     * @param domain
     * @return
     */
    public ${entity} create${entity}(${entity} domain){
        //todo 补充校验
         return writeService.create${entity}(domain);
    }

}
</#if>
