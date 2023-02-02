package com.magnus.excel.infra.utils;

import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.AbstractRowHeightStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

/**
 * @author gaosong
 */
public class CellStyleOps {

    /**
     * 设置头和数据行的内容样式
     */
    public static HorizontalCellStyleStrategy buildCellStyle() {
        //头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();

        //自动换行
        //headWriteCellStyle.setWrapped(true);
        //设置背景色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        //内容顶格居左
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);

        //内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //自动换行
        //writeCellStyle.setWrapped(true);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 设置头和数据行的格式样式
     * 固定行宽
     */
    public static WriteHandler buildFixedWidthStyleStrategy() {
        return new SimpleColumnWidthStyleStrategy(12);
    }

    /**
     * 设置头和数据行的格式样式
     * 自动行宽
     */
    public static WriteHandler buildAutoWidthStyleStrategy() {
        return new LongestMatchColumnWidthStyleStrategy();
    }

    /**
     * 设置头和数据行的格式样式
     * 固定行高
     */
    public static WriteHandler buildFixedHeightStyleStrategy() {
        return new SimpleRowHeightStyleStrategy((short) 50, (short) 30);
    }

    /**
     * 自动行高
     */
    protected static WriteHandler buildAutoHeightStyleStrategy() {
        return new AbstractRowHeightStyleStrategy() {

            @Override
            protected void setHeadColumnHeight(Row row, int relativeRowIndex) {
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!cellIterator.hasNext()) {
                    return;
                }

                // 默认为 1行高度
                Integer maxHeight = 1;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            if (cell.getStringCellValue().contains("\n")) {
                                int length = cell.getStringCellValue().split("\n").length;
                                maxHeight = Math.max(maxHeight, length);
                            }
                            break;
                        default:
                            break;
                    }
                }

                row.setHeight((short) (maxHeight * (Integer) 300));
            }

            @Override
            protected void setContentColumnHeight(Row row, int relativeRowIndex) {

            }
        };
    }

}
