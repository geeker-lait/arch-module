-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `${(name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}`;
USE `${(name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}`;

<#list tables as table>
DROP TABLE IF EXISTS `${(table.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!"tb_"+table_index}`;
CREATE TABLE IF NOT EXISTS `${(table.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!"tb_"+table_index}`(
    <#list table.columns as column>
    `${(column.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}` <#if column.typ?? && (column.typ == 'datetime' || column.typ == 'boolean')>${column.typ!""}<#elseif column.typ?? && column.typ == 'bigint'> ${column.typ!""}(${column.length!"19"})<#elseif column.typ?? && column.typ == 'varchar'> ${column.typ!""}(${column.length!"64"})<#elseif column.typ?? && column.typ == 'int'> ${column.typ!""}(${column.length!"2"})</#if> COMMENT '${column.comment!""}',
    </#list>
    `tenant_id` bigint(19) COMMENT '租户id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='${table.comment!""}';

</#list>
