package com.magnus.excel.biz.model.emp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.magnus.excel.biz.model.BaseExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmpExcelEntity extends BaseExcelEntity {

    /**
     * 手机号
     */
    @ExcelProperty({EmpHeaderConstants.HINT_LINE, EmpHeaderConstants.MOBILE})
    private String mobile;

    /**
     * 用户名称
     */
    @ExcelProperty({EmpHeaderConstants.HINT_LINE, EmpHeaderConstants.USER_NAME})
    private String userName;

}
