package com.magnus.excel.biz.emp.exportexcel;


import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;

/**
 * 导出时设置下拉框
 */
@Slf4j
@AllArgsConstructor
public class EmpDropDownSingleSheetWriteHandler implements SheetWriteHandler {

    /**
     * 下拉框候选值
     */
    List<String> dropDownList;

    /**
     * 创建sheet页前的操作
     *
     * @param writeWorkbookHolder
     * @param writeSheetHolder
     */
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        log.info("创建sheet之前");
    }

    /**
     * 单下拉框
     * 创建sheet保存下拉框选项，突破下拉框255的限制
     *
     * @param writeWorkbookHolder
     * @param writeSheetHolder
     */
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

        log.info("第{}个Sheet写入成功。", writeSheetHolder.getSheetNo());
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();

        //工作簿，代表一个excel的整个文档
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        //1.创建一个隐藏的sheet
        String sheetName = "下拉框选项列表sheet";
        Sheet proviceSheet = workbook.createSheet(sheetName);
        //workbook.setSheetHidden(workbook.getSheetIndex(sheetName),true);

        //2.向下拉框选项列表sheet中写入选项值（为了防止下拉框的行数与隐藏域的行数相对应，将隐藏域加到结束行之后）
        for (int i = 0, length = dropDownList.size(); i < length; i++) {
            proviceSheet
                    //行
                    .createRow(i)
                    //列，0即第一列
                    .createCell(0)
                    .setCellValue(dropDownList.get(i));
        }

        //3.Name代表a range of cells。以$A$1:$A$N为例，表示以A列1行到A列N行的数据
        Name rangeName = workbook.createName();
        rangeName.setNameName(sheetName);
        rangeName.setRefersToFormula(sheetName + "!$A$1:$A$" + (dropDownList.size()));

        //4.设置下拉列表
        //下拉列表范围
        CellRangeAddressList addressList = new CellRangeAddressList(3, 20, 1, 1);
        //下拉列表候选值
        DataValidationConstraint constraint = helper.createFormulaListConstraint(sheetName);
        //建立约束关系
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        //向sheet中添加该约束关系
        writeSheetHolder.getSheet().addValidationData(dataValidation);

    }

}

