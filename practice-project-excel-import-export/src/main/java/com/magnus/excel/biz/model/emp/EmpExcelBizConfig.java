package com.magnus.excel.biz.model.emp;

import com.magnus.excel.infra.model.constants.TimeConstants;

/**
 * 业务专属常量字段
 * @author gaosong
 */

public class EmpExcelBizConfig {

    /**
     * excel表格最大条数
     */
    public static final long MAX_ENTITY_NUMBER = 5000;

    /**
     * 表头行共几行
     */
    public static final int HEADER_LINE_NUMBER = 2;

    /**
     * 错误条数限制，如果超出则不应继续校验，应直接返回
     *
     */
    public static final int ERROR_LINE_NUMBER = 50;

    /**
     * 导入状态标记保留时间 1h
     */
    public static final int IMPORT_STATUS_DURATION_SECONDS = TimeConstants.ONE_HOUR_SECONDS;

    /**
     * 导入结果标记保留时间 1h
     */
    public static final int IMPORT_RESULT_DURATION_SECONDS = TimeConstants.ONE_HOUR_SECONDS;
}
