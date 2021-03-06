package ${starterPackagePath};

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ${servicePackagePath}.${entity}BizService;
import ${requestPackagePath}.${entity}ExampleRequest;
import ${serviceConverterPackagePath}.${entity}ServiceConverter;

import javax.annotation.Resource;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Resource
    private ${entity}BizService bizService;
    @Resource
    private ${entity}ServiceConverter cv;

    @GetMapping("/example")
    public String example(${entity}ExampleRequest request) {
        cv.to${entity}(request);
        return "guten tag";
    }

    @GetMapping("/api/create")
    public String create() {
        // bizService.create${entity}();
        return "success";
    }

}
</#if>
