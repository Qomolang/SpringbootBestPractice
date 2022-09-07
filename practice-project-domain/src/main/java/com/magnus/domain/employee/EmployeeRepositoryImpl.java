package com.magnus.domain.employee;

import com.magnus.infrastructure.dao.employee.model.EmployeeDO;
import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.employee.converter.EmployeeConverter;
import com.magnus.infrastructure.dao.employee.mapper.EmployeeMapper;
import com.magnus.domain.employee.repository.EmployeeRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  repository实现类
 * get开头的方法为单个查询
 * list开头的方法为批量查询
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Repository
public class EmployeeRepositoryImpl extends ServiceImpl<EmployeeMapper, EmployeeDO> implements EmployeeRepository {
    @Resource
    private EmployeeConverter cv;

    @Override
    public Employee getById(Long id) {
        EmployeeDO output = this.baseMapper.selectOne(Wrappers.<EmployeeDO>lambdaQuery()
                .eq(EmployeeDO::getId, id)
                .eq(EmployeeDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toEmployee(output);
    }

    @Override
    public List<Employee> listEntityByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<EmployeeDO> output = list(Wrappers.<EmployeeDO>lambdaQuery()
                .in(EmployeeDO::getId, ids)
                .eq(EmployeeDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toEmployee(output);
    }

    @Override
    public Employee create(Employee domain) {
        EmployeeDO entityDO = cv.toEmployeeDO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toEmployee(entityDO);
    }

    @Override
    public List<Employee> createBatch(List<Employee> domains) {
        List<EmployeeDO> entityDOs = cv.toEmployeeDO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toEmployee(entityDOs);
    }

    @Override
    public boolean updateById(Employee domain) {
        EmployeeDO entityDO = cv.toEmployeeDO(domain);

        return update(entityDO, Wrappers.<EmployeeDO>lambdaQuery()
                .eq(EmployeeDO::getId, domain.getId())
                .eq(EmployeeDO::getDeleteTag, 0)
        );
    }

    @Override
    public boolean updateBatchByIds(List<Employee> domains) {
        //注意 批量更新不会考虑逻辑删字段
        List<EmployeeDO> entityDOs = cv.toEmployeeDO(domains);
        return updateBatchById(entityDOs);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteLogicallyById(Long id) {

        update(Wrappers.<EmployeeDO>lambdaUpdate()
                .eq(EmployeeDO::getId, id)
                .set(EmployeeDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }

    @Override
    public boolean deleteLogicallyByIds(Collection<Long> ids) {

        update(Wrappers.<EmployeeDO>lambdaUpdate()
                .in(EmployeeDO::getId, ids)
                .set(EmployeeDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }


}
