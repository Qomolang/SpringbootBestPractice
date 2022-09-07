package com.magnus.domain.user.repository;

import com.magnus.domain.user.model.User;

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
public interface UserRepository {
    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 根据 ID 列表查找  列表
     * @param ids
     * @return
     */
    List<User> listEntityByIds(Collection<Long> ids);

    /**
     * 单条插入 
     *
     * @param domain
     * @return
    */
    User create(User domain);

    /**
     * 批量插入 
     *
     * @param domains
     * @return
    */
    List<User> createBatch(List<User> domains);

    /**
     * 单个更新
     *
     * @param domain
     * @return
    */
    boolean updateById(User domain);

    /**
     * 批量更新
     *
     * @param domains
     * @return
    */
    boolean updateBatchByIds(List<User> domains);

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
