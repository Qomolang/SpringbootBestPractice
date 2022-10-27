package com.magnus.excel.infra.common.excelstyle;

import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * @author gaosong
 */
public class CellStyleFactory {

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
    public static WriteHandler buildFixedWidthStyleStrategy(){
        return new SimpleColumnWidthStyleStrategy(12);
    }

    /**
     * 设置头和数据行的格式样式
     * 自动行宽
     */
    public static WriteHandler buildAutoWidthStyleStrategy(){
        return new LongestMatchColumnWidthStyleStrategy();
    }

    /**
     * 设置头和数据行的格式样式
     * 固定行高
     */
    public static WriteHandler buildFixedHeightStyleStrategy(){
        return new SimpleRowHeightStyleStrategy((short) 50, (short) 30);
    }


}
