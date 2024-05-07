package com.magnus.domain.user.converter;

import com.magnus.domain.user.model.User;
import com.magnus.infrastructure.dao.user.model.UserDO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

/**
* User 转换器
*
* @author mybatis-plus-auto-generator
* @description
* @since 2024-05-07
*/
@Mapper(componentModel = "spring")
public interface UserConverter {
    /**
     * User: DO -> Domain
     *
     * @param entityDO
     * @return
     */
    User toUser(UserDO entityDO);

    /**
     * User: DO list-> Domain list
     *
     * @param entityDO
     * @return
     */
    List<User> toUser(Collection<UserDO> entityDO);

    /**
     * User: Domain -> DO
     *
     * @param domain
     * @return
     */
    UserDO toUserDO(User domain);

    /**
     * User: Domain list -> DO list
     *
     * @param domain
     * @return
     */
    List<UserDO> toUserDO(Collection<User> domain);

}
