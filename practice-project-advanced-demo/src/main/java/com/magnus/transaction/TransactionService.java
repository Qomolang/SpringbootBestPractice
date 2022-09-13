package com.magnus.transaction;

import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.employee.repository.EmployeeRepository;
import com.magnus.domain.org.model.Org;
import com.magnus.domain.org.repository.OrgRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TransactionService {

    @Resource
    private OrgRepository orgRepository;
    @Resource
    private EmployeeRepository employeeRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void stepIn() {
        Org org = this.createOrg(Org.builder()
                .createBy(-1L)
                .modifiedBy(-1L)
                .corpId("test")
                .fullName("浙江省约周测试组织")
                .name("yz测试组织")
                .superAdminUid(-1L)
                .build());

        System.out.println("orgId = "+org.getId());

        this.createEmployee(Employee.builder()
                .createBy(-1L)
                .empCode("aaa")
                .modifiedBy(-1L)
                .nickName("约周")
                .userId(-1L)
                .tenantId(org.getId())
                .build());

    }

    public Employee createEmployee(Employee domain) {
        Employee output = employeeRepository.create(domain);
        return output;
    }

    ;

    @Transactional(rollbackFor = Throwable.class)
    public Org createOrg(Org domain) {
        Org output = orgRepository.create(domain);
        return output;
    }

    ;
}
