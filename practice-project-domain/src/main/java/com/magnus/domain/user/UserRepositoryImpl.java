package com.magnus.domain.user;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.magnus.infrastructure.dao.user.model.UserDO;
import com.magnus.domain.user.model.User;
import com.magnus.domain.user.converter.UserConverter;
import com.magnus.infrastructure.dao.user.mapper.UserMapper;
import com.magnus.domain.user.repository.UserRepository;
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
 * @since 2024-05-07
 */
@Repository
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserDO> implements UserRepository {
    @Resource
    private UserConverter cv;

    /**
     * 对于jetcache,key名称必须和参数名一致，否则不生效，为了避免更换参数名导致的异常，可以采用指定参数位置的方案
     */
    @Override
    @Cached(name="userCache-", key="#id", expire = 3600, cacheType = CacheType.REMOTE)
    //@Cached(name="userCache-", key="args[0]", expire = 3600, cacheType = CacheType.REMOTE)
    public User getById(Long id) {
        UserDO output = this.baseMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getId, id)
                .eq(UserDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toUser(output);
    }

    @Override
    public List<User> listEntityByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<UserDO> output = list(Wrappers.<UserDO>lambdaQuery()
                .in(UserDO::getId, ids)
                .eq(UserDO::getDeleteTag, Boolean.FALSE)
        );

        return cv.toUser(output);
    }

    @Override
    public Page<User> listAllInPage(Long pageNumber, Long pageSize) {

        Page<UserDO> page = new Page<>(pageNumber, pageSize);

        Page<UserDO> pageResult = this.page(page, Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getDeleteTag, Boolean.FALSE));

        if (pageResult == null) {
            return new Page();
        }

        Page<User> output = Page.of(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal());
        output.setRecords(cv.toUser(pageResult.getRecords()));

        return output;
    }

    @Override
    public User create(User domain) {
        UserDO entityDO = cv.toUserDO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toUser(entityDO);
    }

    @Override
    public List<User> createBatch(Collection<User> domains) {
        List<UserDO> entityDOs = cv.toUserDO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toUser(entityDOs);
    }

    @CacheUpdate(name="userCache-", key="#domain.id", value="#domain")
    @Override
    public boolean updateById(User domain) {
        UserDO entityDO = cv.toUserDO(domain);

        return update(entityDO, Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getId, domain.getId())
                .eq(UserDO::getDeleteTag, 0)
        );
    }

    @CacheInvalidate(name="userCache-", key="#id")
    @Override
    public boolean deleteLogicallyById(Long id) {

        update(Wrappers.<UserDO>lambdaUpdate()
                .eq(UserDO::getId, id)
                .set(UserDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }

    @Override
    public boolean deleteLogicallyByIds(Collection<Long> ids) {

        update(Wrappers.<UserDO>lambdaUpdate()
                .in(UserDO::getId, ids)
                .set(UserDO::getDeleteTag, Boolean.TRUE)
        );

        return true;
    }


}
