package com.magnus.excel.biz.emp.exportexcel;

/**
 * @author gaosong
 */


import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;
import java.util.Map;

/**
 * 导出时设置下拉框
 */
@Slf4j
@AllArgsConstructor
public class EmpDropDownSheetMultiWriteHandler implements SheetWriteHandler {

    private final Map<Integer, List<String>> columnAndValuesMap;

    /**
     * 创建sheet保存下拉框选项，突破下拉框255的限制
     *
     * @param writeWorkbookHolder
     * @param writeSheetHolder
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        buildMultiDropDownList(writeWorkbookHolder, writeSheetHolder);

    }

    public void buildMultiDropDownList(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        Integer sheetIndex = 0;
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();

        //下拉框的开始行和结束行
        int firstRow = 2;
        int lastRow = 5000;

        for (Map.Entry<Integer, List<String>> entry : columnAndValuesMap.entrySet()) {
            String dictSheetName = "字典sheet" + sheetIndex;
            Workbook workbook = writeWorkbookHolder.getWorkbook();
            // 数据字典的sheet页
            Sheet dictSheet = workbook.createSheet(dictSheetName);
            // 从第二个工作簿开始隐藏，为了用户的友好性，将字典sheet隐藏掉
            sheetIndex++;
            workbook.setSheetHidden(sheetIndex, true);

            // 设置下拉单元格的首行、末行、首列、末列
            CellRangeAddressList rangeAddressList = new CellRangeAddressList(firstRow, lastRow, entry.getKey(), entry.getKey());
            int rowLen = entry.getValue().size();
            if (rowLen == 0) {
                continue;
            }

            // 设置字典sheet页的值 每一列一个字典项
            for (int i = 0; i < rowLen; i++) {
                Row row = dictSheet.getRow(i);
                if (row == null) {
                    row = dictSheet.createRow(i);
                }
                row.createCell(entry.getKey()).setCellValue(entry.getValue().get(i));
            }
            String excelColumn = getExcelLine(entry.getKey());
            // 下拉框数据来源 eg:字典sheet!$B1:$B2
            String refers = dictSheetName + "!$" + excelColumn + "$1:$" + excelColumn + "$" + rowLen;
            // 创建可被其他单元格引用的名称
            Name name = workbook.createName();
            // 设置名称的名字
            name.setNameName("dict" + entry.getKey());
            // 设置公式
            name.setRefersToFormula(refers);
            // 设置引用约束
            DataValidationConstraint constraint = helper.createFormulaListConstraint("dict" + entry.getKey());
            // 设置约束
            DataValidation validation = helper.createValidation(constraint, rangeAddressList);
            if (validation instanceof HSSFDataValidation) {
                validation.setSuppressDropDownArrow(false);
            } else {
                validation.setSuppressDropDownArrow(true);
                validation.setShowErrorBox(true);
            }
            // 阻止输入非下拉框的值
            validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            validation.createErrorBox("提示", "此值与单元格定义格式不一致！");
            // 添加下拉框约束
            writeSheetHolder.getSheet().addValidationData(validation);

        }
    }

    /**
     * 创建sheet页前的操作
     *
     * @param writeWorkbookHolder
     * @param writeSheetHolder
     */
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    }

    /**
     * 返回excel列标A-Z-AA-ZZ
     *
     * @param num 列数
     * @return java.lang.String
     */
    private String getExcelLine(int num) {
        String line = "";
        int first = num / 26;
        int second = num % 26;
        if (first > 0) {
            line = (char) ('A' + first - 1) + "";
        }
        line += (char) ('A' + second) + "";
        return line;
    }

}