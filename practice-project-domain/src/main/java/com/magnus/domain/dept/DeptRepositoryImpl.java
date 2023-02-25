package com.magnus.domain.dept;

import com.magnus.infrastructure.dao.dept.model.DeptDO;
import com.magnus.domain.dept.model.Dept;
import com.magnus.domain.dept.converter.DeptConverter;
import com.magnus.infrastructure.dao.dept.mapper.DeptMapper;
import com.magnus.domain.dept.repository.DeptRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  repository实现类
 * get开头的方法为单个查询
 * list开头的方法为批量查询
 * </p>
 *
 * @author gs
 * @since 2023-02-25
 */
@Repository
public class DeptRepositoryImpl extends ServiceImpl<DeptMapper, DeptDO> implements DeptRepository {
    @Resource
    private DeptConverter cv;

    @Override
    public void addMemberSizeOne(Long id){
        this.baseMapper.addMemberSize(id);
    }

    @Override
    public Dept getById(Long id) {
        DeptDO output = this.baseMapper.selectOne(Wrappers.<DeptDO>lambdaQuery()
                .eq(DeptDO::getId, id)
                .eq(DeptDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toDept(output);
    }

    @Override
    public List<Dept> listEntityByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<DeptDO> output = list(Wrappers.<DeptDO>lambdaQuery()
                .in(DeptDO::getId, ids)
                .eq(DeptDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toDept(output);
    }
    
    @Override
    public Page<Dept> listAllInPage(Long pageNumber, Long pageSize) {

        Page<DeptDO> page = new Page<>(pageNumber, pageSize);

        Page<DeptDO> pageResult = this.page(page, Wrappers.<DeptDO>lambdaQuery()
                .eq(DeptDO::getDeleteTag, Boolean.FALSE));

        if (pageResult == null) {
            return new Page();
        }

        Page<Dept> output = Page.of(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal());
        output.setRecords(cv.toDept(pageResult.getRecords()));

        return output;
    }

    @Override
    public Dept create(Dept domain) {
        DeptDO entityDO = cv.toDeptDO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toDept(entityDO);
    }

    @Override
    public List<Dept> createBatch(List<Dept> domains) {
        List<DeptDO> entityDOs = cv.toDeptDO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toDept(entityDOs);
    }

    @Override
    public boolean updateById(Dept domain) {
        DeptDO entityDO = cv.toDeptDO(domain);

        return update(entityDO, Wrappers.<DeptDO>lambdaQuery()
                .eq(DeptDO::getId, domain.getId())
                .eq(DeptDO::getDeleteTag, 0)
        );
    }

    @Override
    public boolean updateBatchByIds(List<Dept> domains) {
        //注意 批量更新不会考虑逻辑删字段
        List<DeptDO> entityDOs = cv.toDeptDO(domains);
        return updateBatchById(entityDOs);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteLogicallyById(Long id) {

        update(Wrappers.<DeptDO>lambdaUpdate()
                .eq(DeptDO::getId, id)
                .set(DeptDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }

    @Override
    public boolean deleteLogicallyByIds(Collection<Long> ids) {

        update(Wrappers.<DeptDO>lambdaUpdate()
                .in(DeptDO::getId, ids)
                .set(DeptDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }


}
