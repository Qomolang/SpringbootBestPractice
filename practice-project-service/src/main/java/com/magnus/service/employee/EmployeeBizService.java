package com.magnus.service.employee;

import com.magnus.domain.employee.model.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  BizService类
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Service
public class EmployeeBizService {

    @Resource
    private EmployeeWriteService writeService;
    @Resource
    private EmployeeReadService readService;

    /**
     * 保存 
     *
     * @param domain
     * @return
    */
    public Employee createEmployee(Employee domain){
        //todo 补充校验
         return writeService.createEmployee(domain);
    };

}
