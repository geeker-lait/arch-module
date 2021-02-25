CREATE DATABASE IF NOT EXISTS `arch_module`;
USE `arch_module`;
<#list databaseInfos! as module>
-- 若库不存在创建一个
<#--CREATE DATABASE IF NOT EXISTS `${module.moduleName!"arch_lait"}`;
USE `${module.moduleName!"arch_lait"}`;-->

<#list module.entityInfos as entity>
CREATE TABLE IF NOT EXISTS `${entity.tableName!"tb_"+entity_index}`(
    <#list entity.fields as field>
    `${field.name!""}` <#if field.type?? && (field.type == 'datetime' || field.type == 'boolean')>${field.type!""}<#elseif field.type??> ${field.type!""}(${field.length!"255"})</#if> COMMENT '${field.comment!""}',
    </#list>
    `tenant_id` bigint(19) COMMENT '租户id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='${entity.comment!""}';
</#list>
</#list>
