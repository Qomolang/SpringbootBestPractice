package com.magnus.excel.biz;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.magnus.excel.infra.common.enums.ErrorHandleModeEnum;
import com.magnus.excel.infra.utils.EasyExcelUtils;
import com.magnus.excel.model.error.ImportResult;
import com.magnus.excel.model.error.importcheck.ImportErrorMsg;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gaosong
 */
@Service
public class ImportErrorHandlerService {

    public ImportResult handleImportErrorMsg(ImportErrorMsg importErrorMsg, ErrorHandleModeEnum handleMode) {
        Preconditions.checkNotNull(importErrorMsg);

        ImportResult result = ImportResult.builder()
                .plainErrorMsg(importErrorMsg.getPlainErrorMsg())
                .build();

        switch (handleMode) {
            case EXCEL_ROW_LINE:
                //将错误信息转换为流
                ByteArrayOutputStream outputStream = this.buildRowLineErrorExcel(importErrorMsg.getDataFormatErrorMsgList());
                //todo将流上传到OSS并获得文件
                String filePath = null;
                //设置文件路径
                result.setErrorExcelLink(filePath);
                break;

            case FRONTEND_SHOWING:
                result.setDataFormatErrorMsgList(importErrorMsg.getDataFormatErrorMsgList());
                break;

            default:
                break;
        }

        return result;
    }

    /**
     * 错误信息 构建增量提示错误格的行列的excel流
     */
    public ByteArrayOutputStream buildRowLineErrorExcel(List<ImportErrorMsg.DataFormatErrorMsg> dataFormatErrorMsgList) {

        //格式转换
        List<List<Object>> errorMsg = this.convertErrorMsg(dataFormatErrorMsgList);

        //转换为文件流
        ByteArrayOutputStream outputStream = EasyExcelUtils.writeStream(errorMsg);

        return outputStream;
    }

    /**
     * 错误信息格式转换
     * 将POJO类格式的错误信息转换为单行字符串，并处理为excel单元格能处理的格式
     */
    private List<List<Object>> convertErrorMsg(List<ImportErrorMsg.DataFormatErrorMsg> dataFormatErrorMsgList) {

        List<List<Object>> output = new ArrayList<>();

        //todo 有空看下类型系统
        if (CollectionUtils.isNotEmpty(dataFormatErrorMsgList)) {
            for (ImportErrorMsg.DataFormatErrorMsg foo : dataFormatErrorMsgList) {
                String errMsgStr = "第" + foo.getRow() + "行" + "第" + foo.getLine() + "列" + "单元格:" + foo.getMsg();
                output.add(Lists.newArrayList(errMsgStr));
            }
//            output = dataFormatErrorMsgList.stream()
//                    .map(foo -> "第" + foo.getRow() + "行" + "第" + foo.getLine() + "列" + "单元格:" + foo.getMsg())
//                    .map(foo -> (List) Lists.newArrayList(foo))
//                    .collect(Collectors.toList());
        }
        return output;
    }

}
