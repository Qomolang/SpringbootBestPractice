package com.magnus.excel.biz.filterchain.filternode.emp;

import com.alibaba.excel.util.StringUtils;
import com.magnus.excel.infra.constants.RegexConstant;
import com.magnus.excel.infra.errorhandler.error.importcheck.ImportErrorMsg.DataFormatErrorMsg;
import com.magnus.excel.model.emp.EmpExcelEntity;
import com.magnus.excel.model.emp.EmpHeaderConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaosong
 */
@Component
public class FormatFilterNode {

    public List<DataFormatErrorMsg> checkFormat(List<EmpExcelEntity> empExcelEntityList) {
        List<DataFormatErrorMsg> result = new ArrayList<>();

        for (EmpExcelEntity empExcelEntity : empExcelEntityList) {

            //必填校验
            DataFormatErrorMsg mustFilledInMsg = this.checkMustFilledIn(empExcelEntity);
            if (mustFilledInMsg != null) {
                result.add(mustFilledInMsg);
            }

            //数据格式校验
            DataFormatErrorMsg formatErrorMsg = this.checkContentFormat(empExcelEntity);
            if (formatErrorMsg != null) {
                result.add(formatErrorMsg);
            }

        }

        return result;
    }

    /**
     * 必填校验
     */
    private DataFormatErrorMsg checkMustFilledIn(EmpExcelEntity empExcelEntity) {
        DataFormatErrorMsg errorMsg = DataFormatErrorMsg.builder()
                .rowIndex(empExcelEntity.getRowNumber())
                .build();
        boolean errorFlag = false;
        String mustFilledInMsg = "请填写必填项";

        if (StringUtils.isBlank(empExcelEntity.getMobile())) {
            errorFlag = true;
            errorMsg.setLineName(EmpHeaderConstants.MOBILE);
            errorMsg.setMsg(mustFilledInMsg);
        }

        if (!errorFlag) {
            return null;
        }
        return errorMsg;

    }

    /**
     * 数据格式校验
     */
    private DataFormatErrorMsg checkContentFormat(EmpExcelEntity empExcelEntity) {
        DataFormatErrorMsg errorMsg = DataFormatErrorMsg.builder()
                .rowIndex(empExcelEntity.getRowNumber())
                .build();
        boolean errorFlag = false;

        //校验手机号
        String mobile = empExcelEntity.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            if (!mobile.matches(RegexConstant.NAME_REGEX)) {
                errorFlag = true;
                errorMsg.setLineName(EmpHeaderConstants.MOBILE);
                errorMsg.setMsg("手机号格式错误");
            }
        }

        if (!errorFlag) {
            return null;
        }
        return errorMsg;

    }

}
