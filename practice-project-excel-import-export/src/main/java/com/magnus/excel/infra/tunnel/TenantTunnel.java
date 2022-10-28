package com.magnus.excel.infra.tunnel;

import com.magnus.domain.employee.model.Employee;
import com.magnus.domain.org.model.Org;
import com.magnus.service.employee.EmployeeReadService;
import com.magnus.service.employee.EmployeeWriteService;
import com.magnus.service.org.OrgReadService;
import com.magnus.service.org.OrgWriteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gaosong
 */
@Service
public class TenantTunnel {

    @Resource
    private OrgReadService orgReadService;
    @Resource
    private OrgWriteService orgWriteService;

    public List<Org> listAll(){
        return new ArrayList<>();
    }

}
