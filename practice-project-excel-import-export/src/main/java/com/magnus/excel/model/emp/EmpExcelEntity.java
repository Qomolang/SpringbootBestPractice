package com.magnus.excel.model.emp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.magnus.excel.model.BaseExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpExcelEntity extends BaseExcelEntity {

    /**
     * 组织名称
     */
    @ExcelProperty({EmpHeaderConstants.HINT_LINE, EmpHeaderConstants.MOBILE})
    private String mobile;

    /**
     * 用户名称
     */
    @ExcelProperty({EmpHeaderConstants.HINT_LINE, EmpHeaderConstants.USER_NAME})
    private String userName;

}
