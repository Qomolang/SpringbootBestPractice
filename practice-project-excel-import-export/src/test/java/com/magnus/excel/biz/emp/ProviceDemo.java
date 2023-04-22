package com.magnus.excel.biz.emp;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.google.common.collect.Lists;
import com.magnus.excel.biz.emp.exportexcel.EmpDropDownSheetMultiWriteHandler;
import com.magnus.excel.biz.emp.exportexcel.EmpDropDownSingleSheetWriteHandler;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author gaosong
 */
public class ProviceDemo {

    @Test
    public void test() throws IOException {
        // 文件输出位置""
        OutputStream out = new FileOutputStream("demo.xlsx" + System.currentTimeMillis() + ".xlsx");
        ExcelWriter writer = EasyExcelFactory
                .write(out)
                .registerWriteHandler(new EmpDropDownSheetMultiWriteHandler(Map.of(2,List.of("super","staff"))))
                .build();

        // 动态添加表头，适用一些表头动态变化的场景
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetName("客户明细");
        sheet1.setSheetNo(0);
        // 写多个sheet

        // 创建一个表格，用于 Sheet 中使用
        WriteTable table = new WriteTable();
        table.setTableNo(1);
        table.setHead(head());
        // 写数据
        writer.write(contentData(), sheet1, table);
        writer.finish();
        out.close();
    }

    private List<List<Object>> contentData() {
        List<List<Object>> contentList = Lists.newArrayList();
        //这里一个List<Object>才代表一行数据，需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
        contentList.add(Lists.newArrayList("苹果🍎", "深圳市"));
        contentList.add(Lists.newArrayList("橙子🍊", "北京市"));
        return contentList;
    }

    private List<List<String>> head() {
        List<List<String>> headTitles = Lists.newArrayList();
        String empty = " ";
        //第一列，1/2/3行
        headTitles.add(Lists.newArrayList(empty, empty, "姓名"));
        //第二列，1/2/3行
        headTitles.add(Lists.newArrayList(empty, empty, "城市"));
        return headTitles;
    }
}
