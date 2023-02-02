package com.magnus.excel.biz.filternode;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 条数校验node
 * @author gaosong
 */
@Component
public class ScaleFilterNode {
    public <T> Boolean checkScale(Collection<T> input, long scale) {
        return CollectionUtils.size(input) < scale;
    }
}
