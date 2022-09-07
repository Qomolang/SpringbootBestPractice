package com.magnus.domain.employee.repository;

import com.magnus.domain.employee.model.Employee;

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
public interface EmployeeRepository {
    /**
     * 根据 ID 查找 
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 根据 ID 列表查找  列表
     * @param ids
     * @return
     */
    List<Employee> listEntityByIds(Collection<Long> ids);

    /**
     * 单条插入 
     *
     * @param domain
     * @return
    */
    Employee create(Employee domain);

    /**
     * 批量插入 
     *
     * @param domains
     * @return
    */
    List<Employee> createBatch(List<Employee> domains);

    /**
     * 单个更新
     *
     * @param domain
     * @return
    */
    boolean updateById(Employee domain);

    /**
     * 批量更新
     *
     * @param domains
     * @return
    */
    boolean updateBatchByIds(List<Employee> domains);

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
