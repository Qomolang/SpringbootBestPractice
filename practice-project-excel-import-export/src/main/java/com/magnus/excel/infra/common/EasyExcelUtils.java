package com.magnus.excel.infra.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.magnus.excel.infra.HeadListener;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author gaosong
 */
@Slf4j
public class EasyExcelUtils {

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
