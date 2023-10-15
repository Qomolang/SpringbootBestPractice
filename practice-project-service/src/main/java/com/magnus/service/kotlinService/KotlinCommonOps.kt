package com.magnus.service.kotlinService

import com.magnus.domain.org.model.Org
import com.magnus.infrastructure.dao.org.model.OrgDO

class KotlinCommonOps {

    fun testWithJava(){

        val orgDO = Org.builder().name("abc").build()
        orgDO.id
    }

}

fun test(){

}