package com.magnus.excel.infra.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.magnus.excel.infra.HeadListener;
import lombok.extern.slf4j.Slf4j;
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
     *
     * 使用过输入流之后，reset即可
     * repeatableInputSteam.reset();
     *
     */
    public static ByteArrayInputStream repeatableStream(InputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            IOUtils.copy(inputStream, byteArrayOutputStream);
        } catch (IOException e) {
            log.error("[EasyExcelUtils repeatableStream] io异常", e);
            throw new RuntimeException("",e);
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

}
