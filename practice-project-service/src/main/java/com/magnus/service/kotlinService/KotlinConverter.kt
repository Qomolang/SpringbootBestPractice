package com.magnus.service.kotlinService

import com.magnus.domain.KotlinDomain
import com.magnus.domain.user.model.User
import com.magnus.infrastructure.dao.user.model.UserDO
import org.mapstruct.Mapper


@Mapper(componentModel = "spring")
interface KotlinConverter {
    /**
     * User: DO -> Domain
     *
     * @param entityDO
     * @return
     */
    fun toUser(entityDO: UserDO?): KotlinDomain?

    /**
     * User: DO list-> Domain list
     *
     * @param entityDO
     * @return
     */
    fun toUser(entityDO: List<UserDO?>?): List<KotlinDomain?>?

    /**
     * User: Domain -> DO
     *
     * @param domain
     * @return
     */
    fun toUserDO(domain: KotlinDomain?): UserDO?

    /**
     * User: Domain list -> DO list
     *
     * @param domain
     * @return
     */
    fun toUserDO(domain: List<KotlinDomain?>?): List<UserDO?>?
}
