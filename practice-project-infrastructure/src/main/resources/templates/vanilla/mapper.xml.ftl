<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackagePath}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${doPackagePath}.${entity}DO">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.columnName},
</#list>
        ${table.fieldNames}
    </sql>

    <sql id="Table_Name">
        <!-- 表名列 -->
        ${table.name}
    </sql>

    <select id="getById" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM  <include refid="Table_Name"/>
        WHERE id = <#noparse>#{id}</#noparse>
    </select>

    <select id="listByIds" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM  <include refid="Table_Name"/>
        WHERE id IN
        <foreach collection="ids" item="id" separator="," open="(" close=")">
            <#noparse>#{id}</#noparse>
        </foreach>
    </select>

    <insert id="insert" parameterType="${doPackagePath}.${entity}DO" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO  <include refid="Table_Name"/>
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.commonFields as field>
            <#noparse><if test="</#noparse>${field.propertyName}<#noparse> != null"></#noparse>
                ${field.name},
            <#noparse></if></#noparse>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--跳过主键字段-->
                <#noparse><if test="</#noparse>${field.propertyName}<#noparse> != null"></#noparse>
                    ${field.name},
                <#noparse></if></#noparse>
            </#if>
        </#list>
        </trim>
        VALUES
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list table.commonFields as field>
            <#noparse><if test="</#noparse>${field.propertyName}<#noparse> != null"></#noparse>
                <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>,
            <#noparse></if></#noparse>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--跳过主键字段-->
                <#noparse><if test="</#noparse>${field.propertyName}<#noparse> != null"></#noparse>
                    <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>,
                <#noparse></if></#noparse>
            </#if>
        </#list>
        </trim>
    </insert>

    <update id="updateById" parameterType="${doPackagePath}.${entity}DO">
        UPDATE  <include refid="Table_Name"/>
        <set>
        <#list table.commonFields as field>
            <#noparse><if test="</#noparse>${field.propertyName}<#noparse> != null"></#noparse>
                ${field.name} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>,
            <#noparse></if></#noparse>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag>
                <#noparse><if test="</#noparse>${field.propertyName}<#noparse> != null"></#noparse>
                    ${field.name} = <#noparse>#{</#noparse>${field.propertyName}<#noparse>}</#noparse>,
                <#noparse></if></#noparse>
            </#if>
        </#list>
        </set>
        WHERE id = <#noparse>#{id}</#noparse>
    </update>

    <delete id="deleteById" parameterType="Long">
        DELETE FROM  <include refid="Table_Name"/>
        WHERE id = <#noparse>#{id}</#noparse>
    </delete>

</#if>
</mapper>
