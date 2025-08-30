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
     * 分页查，基于raw sql offset分页
     * 适用于：function需要的参数是offset
     */
    public static <R> List<R> listInPageByFunctionV2(Integer pageSize, Function<Long, List<R>> function) {
        List<R> result = new ArrayList<>();
        long pageNumber = 1L;
        while (true) {
            long offset = pageSize * (pageNumber - 1);
            List<R> pageInfo = function.apply(offset);
            if (CollectionUtils.isEmpty(pageInfo)) {
                break;
            }
            result.addAll(pageInfo);
            pageNumber++;
        }
        return result;
    }

    /**
     * 与 listAllInPageByFunction 区别在于，入参和内部无Page类
     * 适用于：中间有一层处理了Page（sqp），或PageResponse（rpc）
     */
    public static <R> List<R> listAllInPageByFunctionV3(Function<Long, List<R>> function) {
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