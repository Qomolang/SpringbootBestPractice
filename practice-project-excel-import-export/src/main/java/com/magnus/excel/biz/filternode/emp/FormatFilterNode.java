package com.magnus.excel.biz.filternode.emp;

import com.alibaba.excel.util.StringUtils;
import com.magnus.excel.infra.model.constants.RegexConstant;
import com.magnus.excel.infra.model.error.importcheck.ImportErrorMsg.DataFormatErrorMsg;
import com.magnus.excel.biz.model.emp.EmpExcelEntity;
import com.magnus.excel.biz.model.emp.EmpHeaderConstants;
import org.apache.commons.collections4.CollectionUtils;
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
            List<DataFormatErrorMsg> mustFilledInMsg = this.checkMustFilledIn(empExcelEntity);
            if (CollectionUtils.isNotEmpty(mustFilledInMsg)) {
                result.addAll(mustFilledInMsg);
            }

            //数据格式校验
            List<DataFormatErrorMsg> formatErrorMsg = this.checkContentFormat(empExcelEntity);
            if (CollectionUtils.isNotEmpty(formatErrorMsg)) {
                result.addAll(formatErrorMsg);
            }
        }

        return result;
    }

    /**
     * 必填校验
     */
    private List<DataFormatErrorMsg> checkMustFilledIn(EmpExcelEntity empExcelEntity) {
        List<DataFormatErrorMsg> output = new ArrayList<>();

        String mustFilledInMsg = "请填写必填项";
        if (StringUtils.isBlank(empExcelEntity.getMobile())) {
            DataFormatErrorMsg errorMsg = DataFormatErrorMsg.builder()
                    .rowIndex(empExcelEntity.getRowNumber())
                    .lineName(EmpHeaderConstants.MOBILE)
                    .msg(mustFilledInMsg)
                    .build();
            output.add(errorMsg);
        }

        return output;

    }

    /**
     * 数据格式校验
     */
    private List<DataFormatErrorMsg> checkContentFormat(EmpExcelEntity empExcelEntity) {
        List<DataFormatErrorMsg> output = new ArrayList<>();

        //校验手机号
        String mobile = empExcelEntity.getMobile();
        if (StringUtils.isNotBlank(mobile)) {
            if (!mobile.matches(RegexConstant.NAME_REGEX)) {
                DataFormatErrorMsg errorMsg = DataFormatErrorMsg.builder()
                        .lineName(EmpHeaderConstants.MOBILE)
                        .msg("手机号格式错误")
                        .rowIndex(empExcelEntity.getRowNumber())
                        .build();
                output.add(errorMsg);
            }
        }

        return output;
    }

}
