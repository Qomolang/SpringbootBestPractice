package com.magnus.excel.biz;

import org.springframework.stereotype.Service;

@Service
public class UserExcelBizService {

    /**
     * 同步导出（模板）
     */
    public void exportSync(Long tenantId, Long userId){

        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认

        //3. 获取数据库中的records

        //4. 数据库records -> ExcelEntity

        //5. ExcelEntity -> File Stream

        //6. File Stream -> URL

        //7. 返回结果
    }

    /**
     * 异步导出
     */
    public void exportAsync(Long tenantId, Long userId){

        //1, 校验权限

        //2. 校验是否已经正在导出，任务类型+唯一键确认

        //3. 获取数据库中的records

        //4. 数据库records -> ExcelEntity

        //5. ExcelEntity -> File Stream

        //6. File Stream -> URL

        //7. 返回结果
    }

    /**
     * 轮询检查异步导出结果
     */
    public void pollExportAsyncResult(Long tenantId, Long userId){

    }

    /**
     * 异步导入
     */
    public void importAsync(Long tenantId, Long userId){

    }

    /**
     * 轮询检查异步导入结果
     */
    public void pollImportAsyncResult(Long tenantId, Long userId){

    }


}
