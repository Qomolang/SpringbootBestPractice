package com.magnus.excel.model;

import com.alibaba.excel.annotation.ExcelIgnore;

/**
 * @author gaosong
 */
public class BaseExcelEntity {
    /**
     * 行号
     */
    @ExcelIgnore
    private Integer lineNumber;
}
