CREATE DATABASE IF NOT EXISTS workflow_form DEFAULT CHARSET utf8 COLLATE utf8_general_ci$$
USE workflow_form$$


SET FOREIGN_KEY_CHECKS=0$$


-- ----------------------------
-- 表单字节表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `form_bytearray` (
  `id_` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name_` varchar(255) NOT NULL DEFAULT '' COMMENT '名称',
  `content_byte_` longblob COMMENT '存放内容',
  `create_time_` timestamp(3) NULL COMMENT '创建时间',
  `last_update_time_` timestamp(3) NULL COMMENT '更新时间',
  `rev_` int(10) unsigned NOT NULL COMMENT '乐观锁版本号',
  `tenant_id_` varchar(255) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单字节表'$$

-- ----------------------------
-- 表单数据表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `form_table`(
  `id_` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name_` varchar(64) NOT NULL DEFAULT '' COMMENT '数据表名称',
  `key_` varchar(255) NOT NULL DEFAULT '' COMMENT '数据表标识',
  `category_` varchar(500) DEFAULT '' COMMENT '数据表分类',
  `remark_` varchar(500) DEFAULT '' COMMENT '数据表备注',
  `create_time_` timestamp(3) NULL COMMENT '创建时间',
  `last_update_time_` timestamp(3) NULL COMMENT '更新时间',
  `rev_` int(10) unsigned NOT NULL COMMENT '乐观锁版本号',
  `tenant_id_` varchar(255) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单模型表'$$

-- ----------------------------
-- 表单字段表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `form_field`(
  `id_` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `table_id_` int(10) unsigned NOT NULL COMMENT '数据表ID',
  `key_` varchar(64) NOT NULL DEFAULT '' COMMENT '字段标识',
  `name_` varchar(64) NOT NULL DEFAULT '' COMMENT '字段名称',
  `remark_` varchar(500) DEFAULT '' COMMENT '字段备注',
  `create_time_` timestamp(3) NULL COMMENT '创建时间',
  `last_update_time_` timestamp(3) NULL COMMENT '更新时间',
  `rev_` int(10) unsigned NOT NULL COMMENT '乐观锁版本号',
  `tenant_id_` varchar(255) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单字段表'$$

-- ----------------------------
-- 表单模型表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `form_layout`(
  `id_` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `table_id_` int(10) unsigned NOT NULL COMMENT '数据表ID',
  `name_` varchar(64) NOT NULL DEFAULT '' COMMENT '布局名称',
  `key_` varchar(64) NOT NULL DEFAULT '' COMMENT '布局标识',
  `remark_` varchar(500) DEFAULT '' COMMENT '布局备注',
  `editor_source_id_` int(10) unsigned DEFAULT NULL COMMENT '表单编辑器内容ID',
  `create_time_` timestamp(3) NULL COMMENT '创建时间',
  `last_update_time_` timestamp(3) NULL COMMENT '更新时间',
  `rev_` int(10) unsigned NOT NULL COMMENT '乐观锁版本号',
  `tenant_id_` varchar(255) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单模型表'$$

-- ----------------------------
-- 表单定义表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `form_definition`(
  `id_` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `relation_table_` varchar(64) NOT NULL COMMENT '关联数据表',
  `name_` varchar(64) NOT NULL DEFAULT '' COMMENT '表单名称',
  `version_` int(10) unsigned NOT NULL DEFAULT 0 COMMENT '版本号',
  `key_` varchar(255) NOT NULL DEFAULT '' COMMENT '表单标识',
  `category_` varchar(500) DEFAULT '' COMMENT '表单分类',
  `field_source_id_` int(10) unsigned DEFAULT NULL COMMENT '表单部署内容ID',
  `layout_source_id_` int(10) unsigned DEFAULT NULL COMMENT '表单部署内容ID',
  `create_time_` timestamp(3) NULL COMMENT '创建时间',
  `last_update_time_` timestamp(3) NULL COMMENT '更新时间',
  `rev_` int(10) unsigned NOT NULL COMMENT '乐观锁版本号',
  `tenant_id_` varchar(255) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单定义表'$$

-- ----------------------------
-- 表单实例表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `form_instance`(
  `id_` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `table_relation_id_` int(10) unsigned NOT NULL COMMENT '数据表主键ID',
  `form_definition_id_` int(10) unsigned NOT NULL COMMENT '表单定义ID',
  `form_definition_name_`  varchar(64) NOT NULL DEFAULT '' COMMENT '表单名称',
  `relation_table_` varchar(64) NOT NULL COMMENT '关联数据表',
  `create_time_` timestamp(3) NULL COMMENT '创建时间',
  `last_update_time_` timestamp(3) NULL COMMENT '更新时间',
  `rev_` int(10) unsigned NOT NULL COMMENT '乐观锁版本号',
  `tenant_id_` varchar(255) DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单实例表'$$


