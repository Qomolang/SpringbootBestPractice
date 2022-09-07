package com.magnus.domain.user;

import com.magnus.infrastructure.dao.user.model.UserDO;
import com.magnus.domain.user.model.User;
import com.magnus.domain.user.converter.UserConverter;
import com.magnus.infrastructure.dao.user.mapper.UserMapper;
import com.magnus.domain.user.repository.UserRepository;
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
 *  repository实现类
 * get开头的方法为单个查询
 * list开头的方法为批量查询
 * </p>
 *
 * @author gs
 * @since 2022-09-08
 */
@Repository
public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserDO> implements UserRepository {
    @Resource
    private UserConverter cv;

    @Override
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
    public User create(User domain) {
        UserDO entityDO = cv.toUserDO(domain);
        save(entityDO);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toUser(entityDO);
    }

    @Override
    public List<User> createBatch(List<User> domains) {
        List<UserDO> entityDOs = cv.toUserDO(domains);
        saveBatch(entityDOs);
        //todo 有逻辑删标记字段，需要在这里赋值，或者在数据设置默认值
        return cv.toUser(entityDOs);
    }

    @Override
    public boolean updateById(User domain) {
        UserDO entityDO = cv.toUserDO(domain);

        return update(entityDO, Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getId, domain.getId())
                .eq(UserDO::getDeleteTag, 0)
        );
    }

    @Override
    public boolean updateBatchByIds(List<User> domains) {
        //注意 批量更新不会考虑逻辑删字段
        List<UserDO> entityDOs = cv.toUserDO(domains);
        return updateBatchById(entityDOs);
    }

    @Override
    public boolean deleteById(Long id) {
        return super.removeById(id);
    }

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
