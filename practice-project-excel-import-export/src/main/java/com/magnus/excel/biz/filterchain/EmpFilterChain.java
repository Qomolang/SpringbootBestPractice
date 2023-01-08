package com.magnus.excel.biz.filterchain;

import com.google.common.base.Preconditions;
import com.magnus.excel.biz.filterchain.filternode.ScaleFilterNode;
import com.magnus.excel.model.error.importcheck.ImportErrorMsg;
import com.magnus.excel.model.error.importcheck.ImportCheckResult;
import com.magnus.excel.model.emp.EmpExcelEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

        ImportCheckResult<List<EmpExcelEntity>> checkResult = new ImportCheckResult<>();

        boolean scaleFlag = scaleFilterNode.checkScale(null, 1);
        if (!scaleFlag) {
            ImportErrorMsg errorMsg = ImportErrorMsg.builder()
                    .plainErrorMsg("不能超过 xx 条")
                    .build();
            return ImportCheckResult.buildFailureResult(errorMsg);
        }

        return checkResult;
    }
}
