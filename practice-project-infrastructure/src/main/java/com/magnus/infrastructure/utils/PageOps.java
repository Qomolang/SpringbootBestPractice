package com.magnus.infrastructure.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author gaosong
 */
public class PageOps {


    /**
     * 分页查，分多次查所有
     * 1.防止接口耗时太长
     * 2.防止传送数据过大
     */
    public static <R> List<R> listAllInPageByFunction(Function<Long, Page<R>> function) {
        List<R> result = new ArrayList<>();
        long pageNumber = 1L;
        while (true) {
            long finalPageNumber = pageNumber;
            Page<R> deptPage = function.apply(finalPageNumber);
            if (deptPage == null) {
                break;
            }
            result.addAll(deptPage.getRecords());

            //少查一次
            if (!deptPage.hasNext()) {
                break;
            }
            pageNumber++;
        }
        return result;
    }

    /**
     * 适用于中间有一层处理了Page或者PageResponse，可以兼容sql分页和rpc分页
     */
    public static <R> List<R> listAllInPageByFunction2(Function<Long, List<R>> function) {
        List<R> result = new ArrayList<>();
        long pageNumber = 1L;
        while (true) {
            long finalPageNumber = pageNumber;
            List<R> deptPage = function.apply(finalPageNumber);
            if (CollectionUtils.isEmpty(deptPage)) {
                break;
            }
            result.addAll(deptPage);
            pageNumber++;
        }
        return result;
    }

    /**
     * 通过id分多次查所有
     * 1.防止接口耗时太长
     * 2.防止传送数据过大
     */
    public <T> List<T> listAllInPartitionByIds(List<Long> ids, int batchSize, Function<List<Long>, List<T>> function) {
        List<T> result = new ArrayList<>();
        for (List<Long> idsInPartition : Lists.partition(ids, batchSize)) {
            List<T> tempResult = function.apply(idsInPartition);
            result.addAll(tempResult);
        }
        return result;
    }

}
