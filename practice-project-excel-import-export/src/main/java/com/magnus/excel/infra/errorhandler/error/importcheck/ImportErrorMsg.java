package com.magnus.excel.infra.errorhandler.error.importcheck;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gaosong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportErrorMsg {

    /**
     * 1.表头错误等一句话错误
     * 2.异步异常
     */
    private String plainErrorMsg;

    private List<DataFormatErrorMsg> dataFormatErrorMsgList;

    @Data
    @Builder
    public static class DataFormatErrorMsg {
        private Integer rowIndex;
        private Integer lineIndex;
        private String lineName;
        private String msg;
    }

}
