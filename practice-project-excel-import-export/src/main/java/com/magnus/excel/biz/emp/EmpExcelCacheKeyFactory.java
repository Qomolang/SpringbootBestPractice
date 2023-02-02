package com.magnus.excel.biz.emp;

import com.magnus.excel.biz.model.ExcelActionEnum;
import com.magnus.excel.biz.model.ExcelFlagEnum;
import com.magnus.excel.biz.model.ExcelSceneEnum;
import com.magnus.excel.infra.utils.RedisKeyOps;

/**
 * @author gaosong
 */
public class EmpExcelCacheKeyFactory {

    public static String buildExportStatusKey(Long tenantId, Long userId) {
        return RedisKeyOps.buildRedisKey(
                ExcelSceneEnum.EMP, ExcelActionEnum.EXPORTING, ExcelFlagEnum.STATUS,
                String.valueOf(tenantId), String.valueOf(userId)
        );
    }

}
