package com.magnus.excel.biz.filternode.emp;

import com.magnus.excel.biz.model.emp.EmpExcelDomain;
import com.magnus.excel.biz.model.emp.EmpExcelEntity;
import com.magnus.excel.infra.model.error.importcheck.ImportErrorMsg;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaosong
 */
@Component
public class BizPreFilterNode {

//    public List<ImportErrorMsg.DataFormatErrorMsg> checkBizRestriction(List<EmpExcelDomain> empExcelEntityList) {
//        return new ArrayList<>();
//    }
    public List<ImportErrorMsg.DataFormatErrorMsg> checkBizRestriction(List<EmpExcelEntity> empExcelEntityList) {
        return new ArrayList<>();
    }

}