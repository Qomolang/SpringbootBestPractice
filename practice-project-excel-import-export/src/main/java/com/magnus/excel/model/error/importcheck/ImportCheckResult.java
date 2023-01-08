package com.magnus.excel.model.error.importcheck;

import lombok.Data;

/**
 * 导入 校验结果
 *
 * @author gaosong
 */
@Data
public class ImportCheckResult<T> {

    private boolean success;

    private ImportErrorMsg errorMsg;

    private T result;

    public static <T> ImportCheckResult<T> buildSuccessResult(T t) {

        ImportCheckResult<T> result = new ImportCheckResult<>();
        result.setSuccess(true);
        result.setResult(t);

        return result;
    }

    public static <T> ImportCheckResult<T> buildFailureResult(ImportErrorMsg errorMsg) {

        ImportCheckResult<T> result = new ImportCheckResult<>();
        result.setSuccess(false);
        result.setErrorMsg(errorMsg);

        return result;
    }

}
