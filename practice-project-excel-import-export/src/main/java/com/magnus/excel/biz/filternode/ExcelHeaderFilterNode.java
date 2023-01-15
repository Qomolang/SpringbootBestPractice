package com.magnus.excel.biz.filternode;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * 表头校验node
 *
 * @author gaosong
 */
@Component
public class ExcelHeaderFilterNode {

    /**
     * 检查传入文件的表头和预设的表土是否一致
     */
    public Boolean checkExcelHeader(Set<String> defaultExcelHeader, Set<String> inputExcelHeader) {
        if (defaultExcelHeader == null) {
            return false;
        }

        return defaultExcelHeader.equals(inputExcelHeader);
    }
}
