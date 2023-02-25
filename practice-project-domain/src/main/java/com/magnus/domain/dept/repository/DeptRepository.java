package com.magnus.domain.dept.repository;

import com.magnus.domain.dept.model.Dept;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 * @since 2023-02-25
 */
public interface DeptRepository {
    void addMemberSizeOne(Long id);

    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    Dept getById(Long id);

    /**
     * 根据 ID 列表查找  列表
     * @param ids
     * @return
     */
    List<Dept> listEntityByIds(Collection<Long> ids);

    /**
     * 分页查找  列表
     * @param ids
     * @return
     */
    Page<Dept> listAllInPage(Long pageNumber, Long pageSize);

    /**
     * 单条插入 
     *
     * @param domain
     * @return
     */
    Dept create(Dept domain);

    /**
     * 批量插入 
     *
     * @param domains
     * @return
     */
    List<Dept> createBatch(List<Dept> domains);

    /**
     * 单个更新
     *
     * @param domain
     * @return
     */
    boolean updateById(Dept domain);

    /**
     * 批量更新
     *
     * @param domains
     * @return
     */
    boolean updateBatchByIds(List<Dept> domains);

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
