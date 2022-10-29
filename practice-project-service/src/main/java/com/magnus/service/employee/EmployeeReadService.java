package com.magnus.service.employee;

import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  ReadService类
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Service
public class EmployeeReadService {

    @Resource
    private EmployeeRepository repository;

    /**
     * 根据 ID 查找 
     */
    public Employee getEmployeeById(Long id){
        //todo 补充校验
        return repository.getById(id);
    };

    /**
     *
     */
    public List<Employee> listAllByTenantId(Long tenantId){
        //todo 补充校验
        return repository.listAllByTenantId(tenantId);
    };

}
