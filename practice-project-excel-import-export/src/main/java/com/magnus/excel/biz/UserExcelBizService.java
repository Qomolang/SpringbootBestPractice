package com.magnus.excel.biz;

import org.springframework.stereotype.Service;

@Service
public class UserExcelBizService {

    /**
     * 同步导出（模板）
     */
    public void exportAsync(Long tenantId, Long userId){

        //1, 校验权限

        //2. 校验是否已经有导出，任务类型+唯一键确认

        //3.

    }

    /**
     * 异步导出
     */
    public void exportSync(Long tenantId, Long userId){

    }

    /**
     * 异步导入
     */
    public void importSync(Long tenantId, Long userId){

    }

}
