package com.magnus.service.employee;

import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  WriteService类
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Service
public class EmployeeWriteService {

    @Resource
    private EmployeeRepository repository;

    /**
     * 保存 
     *
     * @param domain
     * @return
    */
    public Employee createEmployee(Employee domain){
        //todo 补充校验
         return repository.create(domain);
    };

    /**
     * 根据 ID 删除某个 
     *
     * @param id
     * @return 删除是否成功
     */
    public boolean deleteEmployeeById(Long id){
        //todo 补充校验
        return repository.deleteById(id);
    };
}
