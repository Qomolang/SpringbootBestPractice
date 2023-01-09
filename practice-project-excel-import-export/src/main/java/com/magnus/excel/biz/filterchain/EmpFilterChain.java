package com.magnus.excel.biz.filterchain;

import com.google.common.base.Preconditions;
import com.magnus.excel.biz.emp.EmpExcelBizConfig;
import com.magnus.excel.biz.filterchain.filternode.ScaleFilterNode;
import com.magnus.excel.infra.utils.EasyExcelUtils;
import com.magnus.excel.infra.errorhandler.error.importcheck.ImportErrorMsg;
import com.magnus.excel.infra.errorhandler.error.importcheck.ImportCheckResult;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author gaosong
 */
@Service
public class EmpFilterChain {

    @Resource
    private ScaleFilterNode scaleFilterNode;

    /**
     * true代表通过
     */
    public ImportCheckResult<List<EmpExcelEntity>> doCheck(EmpContext empContext) {
        Preconditions.checkNotNull(empContext);
        ByteArrayInputStream fileStream = empContext.getFileStream();

        //文件流 -> Entity
        List<EmpExcelEntity> empExcelEntityList = EasyExcelUtils.getExcelDataCellList(fileStream, EmpExcelEntity.class, 2);

        //数量校验，最多不能超过xx条
        boolean scaleFlag = scaleFilterNode.checkScale(empExcelEntityList, EmpExcelBizConfig.MAX_ENTITY_NUMBER);
        if (!scaleFlag) {
            ImportErrorMsg errorMsg = ImportErrorMsg.builder()
                    .plainErrorMsg("不能超过 xx 条")
                    .build();
            return ImportCheckResult.buildFailureResult(errorMsg);
        }

        return ImportCheckResult.buildSuccessResult(empExcelEntityList);
    }
}
