-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `${(name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}`;
USE `${(name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}`;

<#list tables as table>
DROP TABLE IF EXISTS `${(table.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!"tb_"+table_index}`;
CREATE TABLE IF NOT EXISTS `${(table.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!"tb_"+table_index}`(
    <#list table.columns as column>
    `${(column.name?replace("([a-z])([A-Z]+)","$1_$2","r")?lower_case)!""}` <#if column.typ??>${column.typ!""}</#if><#if column.unsigned?? && column.unsigned> UNSIGNED</#if><#if column.def?? && column.def?length gt 0> ${column.def!""}<#elseif column.notnull?? && column.notnull> NOT NULL<#else> DEFAULT NULL</#if><#if column.autoIncrement?? && column.autoIncrement> AUTO_INCREMENT</#if><#if column.onUpdate?? && column.onUpdate?length gt 0> ${column.onUpdate!""}</#if> COMMENT '${column.comment!""}',
    </#list>
    `tenant_id` bigint(19) COMMENT '租户id',
    ${table.pkStatement!""}<#if table.uniquesStatements?? && table.uniquesStatements?size gt 0 && table.indexesStatements?? && table.uniquesStatements?size gt 0>,</#if>
    <#if table.uniquesStatements?? && table.uniquesStatements?size gt 0>
    <#list table.uniquesStatements as unqSql>
    ${unqSql}<#if unqSql_has_next || (table.indexesStatements?? && table.indexesStatements?size gt 0)>,</#if>
    </#list></#if>
    <#if table.indexesStatements?? && table.indexesStatements?size gt 0>
    <#list table.indexesStatements as idxSql>
    ${idxSql}<#if idxSql_has_next>,</#if>
    </#list></#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='${table.comment!""}';

</#list>
