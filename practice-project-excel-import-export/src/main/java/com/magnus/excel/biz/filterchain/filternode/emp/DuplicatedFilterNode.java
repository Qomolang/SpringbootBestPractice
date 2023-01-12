package com.magnus.excel.biz.filterchain.filternode.emp;

import com.magnus.domain.employee.model.Employee;
import com.magnus.excel.infra.errorhandler.error.importcheck.ImportErrorMsg.DataFormatErrorMsg;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author gaosong
 */
@Component
public class DuplicatedFilterNode {

    /**
     * 校验不应重复的项目 1.excel内是否重复 2.数据库中数据是否重复
     *
     * @return
     */
    public List<DataFormatErrorMsg> checkDuplicatedInfo(List<EmpExcelEntity> empExcelEntityList, List<Employee> employeeListInDb) {
        List<DataFormatErrorMsg> result = new ArrayList<>();

        //0. 数据准备
        Set<String> dbEmpCodeSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(employeeListInDb)) {
            dbEmpCodeSet = employeeListInDb.stream()
                    .map(foo->foo.getEmpCode())
                    .collect(Collectors.toSet());
        }



        return result;

    }

}
