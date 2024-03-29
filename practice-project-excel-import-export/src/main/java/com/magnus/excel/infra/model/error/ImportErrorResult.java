package com.magnus.excel.infra.model.error;

import com.magnus.excel.infra.model.error.importcheck.ImportErrorMsg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author gaosong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportErrorResult implements Serializable {

    /**
     * 1.表头错误等一句话错误
     * 2.异步异常
     */
    private String plainErrorMsg;

    /**
     * 错误以前端展示的模式透出
     */
    private List<ImportErrorMsg.DataFormatErrorMsg> dataFormatErrorMsgList;

    /**
     * 错误以excel表格的模式透出
     */
    private String errorExcelLink;

}
