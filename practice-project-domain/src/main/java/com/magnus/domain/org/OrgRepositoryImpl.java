package com.magnus.domain.org;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.magnus.infrastructure.dao.org.model.OrgDO;
import com.magnus.domain.org.model.Org;
import com.magnus.domain.org.converter.OrgConverter;
import com.magnus.infrastructure.dao.org.mapper.OrgMapper;
import com.magnus.domain.org.repository.OrgRepository;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * repository实现类
 * get开头的方法为单个查询
 * list开头的方法为批量查询
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Repository
public class OrgRepositoryImpl extends ServiceImpl<OrgMapper, OrgDO> implements OrgRepository {
    @Resource
    private OrgConverter cv;

    @Override
    public Org getById(Long id) {
        OrgDO output = this.baseMapper.selectOne(Wrappers.<OrgDO>lambdaQuery()
                .eq(OrgDO::getId, id)
                .eq(OrgDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toOrg(output);
    }

    @Override
    public List<Org> listEntityByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<OrgDO> output = list(Wrappers.<OrgDO>lambdaQuery()
                .in(OrgDO::getId, ids)
                .eq(OrgDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toOrg(output);
    }

    @Override
    public Page<Org> listAllInPage(Long pageNumber, Long pageSize) {

        Page<OrgDO> page = new Page<>(pageNumber, pageSize);

        Page<OrgDO> pageResult = this.page(page, Wrappers.<OrgDO>lambdaQuery()
                .eq(OrgDO::getDeleteTag, Boolean.FALSE));

        if (pageResult == null) {
            return new Page();
        }

        Page<Org> output = Page.of(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal());
        output.setRecords(cv.toOrg(pageResult.getRecords()));

        return output;
    }

    @Override
    public Org create(Org domain) {
        OrgDO entityDO = cv.toOrgDO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toOrg(entityDO);
    }

    @Override
    public List<Org> createBatch(List<Org> domains) {
        List<OrgDO> entityDOs = cv.toOrgDO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toOrg(entityDOs);
    }

    @Override
    public boolean updateById(Org domain) {
        OrgDO entityDO = cv.toOrgDO(domain);

        return update(entityDO, Wrappers.<OrgDO>lambdaQuery()
                .eq(OrgDO::getId, domain.getId())
                .eq(OrgDO::getDeleteTag, 0)
        );
    }

    @Override
    public boolean updateBatchByIds(List<Org> domains) {
        //注意 批量更新不会考虑逻辑删字段
        List<OrgDO> entityDOs = cv.toOrgDO(domains);
        return updateBatchById(entityDOs);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean deleteLogicallyById(Long id) {

        update(Wrappers.<OrgDO>lambdaUpdate()
                .eq(OrgDO::getId, id)
                .set(OrgDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }

    @Override
    public boolean deleteLogicallyByIds(Collection<Long> ids) {

        update(Wrappers.<OrgDO>lambdaUpdate()
                .in(OrgDO::getId, ids)
                .set(OrgDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }


}
