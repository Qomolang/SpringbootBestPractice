package com.magnus.excel.infra.tunnel;

import com.magnus.domain.employee.model.Employee;
import com.magnus.service.employee.EmployeeReadService;
import com.magnus.service.employee.EmployeeWriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gaosong
 */
@Service
public class EmpTunnel {

    @Resource
    private EmployeeReadService employeeReadService;
    @Resource
    private EmployeeWriteService employeeWriteService;

    public List<Employee> listAllByTenantId(Long tenantId){
        return new ArrayList<>();
    }

}
