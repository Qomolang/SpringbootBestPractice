package com.magnus.excel.model;

import com.alibaba.excel.annotation.ExcelIgnore;

/**
 * 一个名称为“*ExcelEntity”的实体，对应excel文件中的一行
 * @author gaosong
 */
public class BaseExcelEntity {
    /**
     * 行号
     */
    @ExcelIgnore
    private Integer lineNumber;
}
