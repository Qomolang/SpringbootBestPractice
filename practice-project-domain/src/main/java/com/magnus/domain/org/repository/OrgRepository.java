package com.magnus.domain.org.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magnus.domain.org.model.Org;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  repository类
 * get开头的方法为单个查询
 * list开头的方法为批量查询
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
public interface OrgRepository {
    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    Org getById(Long id);

    /**
     * 根据 ID 列表查找  列表
     * @param ids
     * @return
     */
    List<Org> listEntityByIds(Collection<Long> ids);

    Page<Org> listAllInPage(Long pageNumber, Long pageSize);

    /**
     * 单条插入 
     *
     * @param domain
     * @return
    */
    Org create(Org domain);

    /**
     * 批量插入 
     *
     * @param domains
     * @return
    */
    List<Org> createBatch(List<Org> domains);

    /**
     * 单个更新
     *
     * @param domain
     * @return
    */
    boolean updateById(Org domain);

    /**
     * 批量更新
     *
     * @param domains
     * @return
    */
    boolean updateBatchByIds(List<Org> domains);

    /**
     * 根据 ID 删除某个 
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
