package com.magnus.excel.model;

import lombok.Data;

/**
 * 导入 校验结果
 *
 * @author gaosong
 */
@Data
public class ImportCheckResult<T> {

    private boolean success;

    private ExcelErrorMsg errorMsg;

    private T result;

    private static <T> ImportCheckResult<T> buildSuccessResult(T t) {

        ImportCheckResult<T> result = new ImportCheckResult<>();
        result.setSuccess(true);
        result.setResult(t);

        return result;
    }

    private static <T> ImportCheckResult<T> buildFailureResult(ExcelErrorMsg errorMsg) {

        ImportCheckResult<T> result = new ImportCheckResult<>();
        result.setSuccess(false);
        result.setErrorMsg(errorMsg);

        return result;
    }

}
