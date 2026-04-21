package com.magnus.excel.biz.filternode.emp;

import com.alibaba.excel.util.StringUtils;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.magnus.excel.infra.model.error.importcheck.ImportErrorMsg.DataFormatErrorMsg;
import com.magnus.excel.biz.model.emp.EmpExcelEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gaosong
 */
@Component
public class EmpDuplicatedFilterNode {

    /**
     * 校验不应重复的值 只校验excel内是否重复
     */
    public List<DataFormatErrorMsg> checkDuplicatedInfo(List<EmpExcelEntity> empExcelEntityList) {
        List<DataFormatErrorMsg> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(empExcelEntityList)) {
            return Collections.emptyList();
        }

        //0. 数据准备
        Multimap<String, Integer> excelMobileIndexMap = MultimapBuilder.hashKeys().arrayListValues().build();
        for (EmpExcelEntity empExcelEntity : empExcelEntityList) {
            Integer index = empExcelEntity.getRowNumber();
            String mobile = empExcelEntity.getMobile();
            if (StringUtils.isNotBlank(mobile)) {
                excelMobileIndexMap.put(mobile, index);
            }
        }

        // 手机号重复
        for (Collection<Integer> value : excelMobileIndexMap.asMap().values()) {
            if (CollectionUtils.size(value) > 1) {
                String duplicatedMobileRow = value.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                result.add(DataFormatErrorMsg.builder()
                        .msg(duplicatedMobileRow + "行" + "手机号" + "重复")
                        .build());
            }
        }

        // 不允许同一个acctId有不同的name

        // 不允许有完全相同的两行？

        return result;
    }
}
