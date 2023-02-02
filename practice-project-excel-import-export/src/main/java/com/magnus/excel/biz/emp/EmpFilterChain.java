package com.magnus.excel.biz.emp;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.magnus.excel.biz.filternode.ExcelHeaderFilterNode;
import com.magnus.excel.biz.filternode.ScaleFilterNode;
import com.magnus.excel.biz.filternode.emp.DuplicatedFilterNode;
import com.magnus.excel.biz.filternode.emp.FormatFilterNode;
import com.magnus.excel.biz.model.emp.EmpExcelBizConfig;
import com.magnus.excel.infra.utils.EasyExcelUtils;
import com.magnus.excel.infra.model.error.importcheck.ImportErrorMsg;
import com.magnus.excel.infra.model.error.importcheck.ImportCheckResult;
import com.magnus.excel.biz.model.emp.EmpContext;
import com.magnus.excel.biz.model.emp.EmpExcelEntity;
import com.magnus.excel.biz.model.emp.EmpHeaderConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * @author gaosong
 */
@Service
public class EmpFilterChain {

    @Resource
    private ScaleFilterNode scaleFilterNode;
    @Resource
    private ExcelHeaderFilterNode excelHeaderFilterNode;
    @Resource
    private DuplicatedFilterNode duplicatedFilterNode;
    @Resource
    private FormatFilterNode formatFilterNode;

    /**
     * true代表通过
     */
    public ImportCheckResult<List<EmpExcelEntity>> doCheck(EmpContext empContext) {
        Preconditions.checkNotNull(empContext);

        List<ImportErrorMsg.DataFormatErrorMsg> errorMsgList = new ArrayList<>();
        ByteArrayInputStream fileStream = empContext.getFileStream();

        //文件流 -> Entity
        List<EmpExcelEntity> empExcelEntityList = EasyExcelUtils.getExcelDataCellList(fileStream, EmpExcelEntity.class, EmpExcelBizConfig.HEADER_LINE_NUMBER);

        //1.表头校验
        fileStream.reset();
        Set<String> defaultHeaderSet = Sets.newHashSet(EmpHeaderConstants.MOBILE, EmpHeaderConstants.USER_NAME);
        List<Map<Integer, String>> headerLines = EasyExcelUtils.getExcelHeadCellList(fileStream, EmpExcelBizConfig.HEADER_LINE_NUMBER);
        Set<String> inputHeaderSet = new HashSet<>(headerLines.get(EmpExcelBizConfig.HEADER_LINE_NUMBER - 1).values());
        excelHeaderFilterNode.checkExcelHeader(defaultHeaderSet, inputHeaderSet);

        //2.数量下限校验，excel不能为空（0）
        if (CollectionUtils.isEmpty(empExcelEntityList)) {
            ImportErrorMsg errorMsg = ImportErrorMsg.builder()
                    .plainErrorMsg("excel为空，请填写数据")
                    .build();
            return ImportCheckResult.buildFailureResult(errorMsg);
        }

        //3.数量上限校验，最多不能超过xx条
        boolean scaleFlag = scaleFilterNode.checkScale(empExcelEntityList, EmpExcelBizConfig.MAX_ENTITY_NUMBER);
        if (!scaleFlag) {
            ImportErrorMsg errorMsg = ImportErrorMsg.builder()
                    .plainErrorMsg("不能超过 xx 条")
                    .build();
            return ImportCheckResult.buildFailureResult(errorMsg);
        }

        //4. 格式校验

        //5. 重复行校验

        //6. 业务前置校验

        //错误排序
        if (CollectionUtils.isNotEmpty(errorMsgList)) {
            errorMsgList.sort(Comparator.nullsFirst(Comparator.comparingInt(ImportErrorMsg.DataFormatErrorMsg::getRowIndex)));
        }

        //有错返回代错误结果，无错返回正确结果
        if (CollectionUtils.isNotEmpty(errorMsgList)) {
            return ImportCheckResult.buildFailureResult(ImportErrorMsg.builder()
                    .dataFormatErrorMsgList(errorMsgList)
                    .build());
        }
        return ImportCheckResult.buildSuccessResult(empExcelEntityList);
    }
}
