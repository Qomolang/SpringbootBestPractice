package com.magnus.excel.api;

import com.magnus.excel.infra.errorhandler.error.importcheck.ImportErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gaosong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelImportResponse {

    /**
     * 是否在导入中
     */
    private Boolean isImporting;

    /**
     * 导入成功/失败
     */
    private Boolean isSuccess;

    /**
     * 表头错误等一句话错误
     */
    private String plainError;

    /**
     * 如果错误处理格式为前端处理格式
     */
    private List<ImportErrorMsg.DataFormatErrorMsg> dataFormatErrorMsgList;

    /**
     * 如果错误处理格式为将错误导入excel，此为excel下载连接
     */
    private String errorMsgDownloadUrl;

}
