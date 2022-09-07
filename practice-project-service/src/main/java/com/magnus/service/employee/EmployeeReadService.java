package com.magnus.service.employee;

import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * @param id
     * @return
     */
    public Employee getEmployeeById(Long id){
        //todo 补充校验
        return repository.getById(id);
    };
}
