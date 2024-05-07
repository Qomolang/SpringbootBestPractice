package com.magnus.domain.user.repository;

import com.magnus.domain.user.model.User;
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
 * @since 2024-05-07
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
     * 分页查找  列表
     * @return
     */
    Page<User> listAllInPage(Long pageNumber, Long pageSize);

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
    List<User> createBatch(Collection<User> domains);

    /**
     * 单个更新
     *
     * @param domain
     * @return
     */
    boolean updateById(User domain);

    /**
     * 逻辑删
     */
    boolean deleteLogicallyById(Long id);

    /**
     * 批量逻辑删
     */
    boolean deleteLogicallyByIds(Collection<Long> ids);

}
