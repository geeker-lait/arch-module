<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePkg!""}.mapper.${(name?cap_first)!""}Mapper">

    <resultMap type="${basePkg!""}.entity.${(name?cap_first)!""}" id="${(name?uncap_first)!""}Map">
        <#list columns as column >
            <#if pk?? && pk == cloumn.name!"">
        # TODO 增加对列(xx_xx)转换为驼峰类型字段, 不能获取数据库类型对应的 JavaType
        <id property="${column.name!""}" column="${column.name!""}" jdbcType="${column.typ!""}"/>
            <#else>
        <result property="${column.name!""}" column="${column.name!""}" jdbcType="${column.typ!""}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="tableName">
        `${name!""}`
    </sql>

    <sql id="baseColumn">
        <#if columns?? && (columns?size >0)><#list columns as column >`${column.name!""}`<#sep>,</#list></#if>
    </sql>

</mapper>