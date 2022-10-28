package com.magnus.excel.infra;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gaosong
 */
@Slf4j
public class HeadListener extends AnalysisEventListener<Map<Integer, String>> {

    protected List<Map<Integer, String>> headList;

    protected int headRowNumber;

    private Boolean stopFlag = false;

    public HeadListener(int headRowNumber) {
        this.headList = new ArrayList<>();
        this.headRowNumber = headRowNumber;
    }

    /**
     * 这里会一行行的返回头
     *
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", JSON.toJSONString(headMap));
        headList.add(headMap);
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        //不解析具体内容
        stopFlag = true;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        if (stopFlag) {
            return false;
        }
        return true;
    }
}
