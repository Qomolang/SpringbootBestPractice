package com.magnus.excel.infra.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.magnus.excel.infra.HeadListener;
import com.magnus.excel.biz.model.BaseExcelEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author gaosong
 */
@Slf4j
public class EasyExcelUtils {

    /**
     * 将输入流变为可重复读流
     * <p>
     * 使用过输入流之后，reset即可
     * repeatableInputSteam.reset();
     */
    public static ByteArrayInputStream repeatableStream(InputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            IOUtils.copy(inputStream, byteArrayOutputStream);
        } catch (IOException e) {
            log.error("[EasyExcelUtils repeatableStream] io异常", e);
            throw new RuntimeException("", e);
        }

        ByteArrayInputStream repeatableInputSteam = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        return repeatableInputSteam;
    }

    /**
     * 仅读表头行
     *
     * @param inputStream
     * @param headNumber  表头有几行
     * @return
     */
    public static List<Map<Integer, String>> getExcelHeadCellList(InputStream inputStream, int headNumber) {

        HeadListener headListener = new HeadListener(headNumber);

        try {
            EasyExcel.read(inputStream, headListener)
                    .headRowNumber(headListener.getHeadRowNumber())
                    //读取合并单元格的信息，默认不读取
                    .extraRead(CellExtraTypeEnum.MERGE)
                    .sheet()
                    .doRead();
        } catch (Exception e) {
            log.error("getExcelHeadCellList parse error", e);
            throw new RuntimeException("import parse error");
        }

        return headListener.getHeadList();
    }

    /**
     * 单sheet 按模型读
     * headNumber todo 测试传0 传1的场景
     */
    public static <T extends BaseExcelEntity> List<T> getExcelDataCellList(InputStream inputStream, Class<T> clazz, int headNumber) {
        List<T> excelEntityList = EasyExcel.read(inputStream)
                //读取合并的单元格的信息
                .extraRead(CellExtraTypeEnum.MERGE)
                .head(clazz)
                .headRowNumber(headNumber)
                .sheet()
                .doReadSync();

        if (CollectionUtils.isNotEmpty(excelEntityList)) {
            for (int i = 0; i < excelEntityList.size(); i++) {
                T excelEntity = excelEntityList.get(i);
                excelEntity.setRowNumber(i);
            }
        }

        return excelEntityList;
    }


    /**
     * 导出
     * 将所有数据以无格式、无表头的形式导出，且所有内容均处于第一格
     */
    public static ByteArrayOutputStream writeStream(List<List<Object>> dataList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ExcelWriter excelWriter = EasyExcel.write(outputStream)
                //cell内容策略 居左
                .registerWriteHandler(CellStyleFactory.buildCellStyle())
                //行高 自动
                .registerWriteHandler(CellStyleFactory.buildAutoHeightStyleStrategy())
                //行宽 固定
                .registerWriteHandler(CellStyleFactory.buildFixedWidthStyleStrategy())
                .build();

        excelWriter.write(dataList, new WriteSheet())
                .finish();

        return outputStream;
    }

}
