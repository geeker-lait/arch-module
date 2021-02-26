-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `${(database?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}`;
USE `${(database?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}`;

<#list tables as table>
<#if cover == true>
DROP TABLE `${(table.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!"tb_"+table_index}`;
</#if>
CREATE TABLE IF NOT EXISTS `${(table.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!"tb_"+table_index}`(
    <#list table.columns as column>
    `${(column.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}` <#if column.typ?? && (column.typ == 'datetime' || column.typ == 'boolean')>${column.typ!""}<#elseif column.typ??> ${column.typ!""}(${column.length!"255"})</#if> COMMENT '${column.comment!""}',
    </#list>
    `tenant_id` bigint(19) COMMENT '租户id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='${table.comment!""}';

</#list>
