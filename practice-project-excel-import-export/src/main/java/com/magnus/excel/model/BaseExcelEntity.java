package com.magnus.excel.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 一个名称为“*ExcelEntity”的实体，对应excel文件中的一行
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseExcelEntity {
    /**
     * 行号
     */
    @ExcelIgnore
    private Integer rowNumber;
}
