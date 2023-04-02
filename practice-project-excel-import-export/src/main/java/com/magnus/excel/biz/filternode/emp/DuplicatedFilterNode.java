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
public class DuplicatedFilterNode {

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

        return result;
    }
}
