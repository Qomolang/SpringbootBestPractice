package ${repositoryPackagePath};

import ${domainEntityPackagePath}.${entity};

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ${table.comment!} repository类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${entity}Repository
<#else>
public interface ${entity}Repository {
    /**
     * 根据 ID 查找 ${table.comment!}
     * @param id
     * @return
     */
    ${entity} getOneById(Long id);

    /**
     * 根据 ID 列表查找 ${table.comment!} 列表
     * @param ids
     * @return
     */
    List<${entity}> listAllByIds(Collection<Long> ids);

    /**
     * 单条插入 ${table.comment!}
     *
     * @param domain
     * @return
    */
    ${entity} create(${entity} domain);

    /**
     * 批量插入 ${table.comment!}
     *
     * @param domain
     * @return
    */
    List<${entity}> createBatch(List<${entity}> domains);

    /**
     * 单个修改
     *
     * @param domain
     * @return
    */
    ${entity} update(${entity} domain);

    /**
     * 根据 ID 删除某个 ${table.comment!}
     *
     * @param id
     * @return 删除是否成功
     */
    boolean deleteById(Long id);

    /**
     * 逻辑删
     */
    boolean deleteLogicallyById(Long id);

    /**
     * 批量逻辑删
     */
    boolean deleteLogicallyByIds(Collection<Long> ids);

}
</#if>
