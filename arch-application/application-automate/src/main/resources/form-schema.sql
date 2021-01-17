

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `ims`;
USE `ims`;

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `sys`;
USE `sys`;

CREATE TABLE IF NOT EXISTS `form_definition`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category` varchar(255) COMMENT '表单分类',
    `form_name` varchar(255) COMMENT '表单名称',
    `form_code` varchar(255) COMMENT '表单code,用作页面表单',
    `table_name` varchar(255) COMMENT '关联数据表',
    `field_id` bingint(255) COMMENT '表单部署字段ID',
    `layout_id` bingint(255) COMMENT '表单部署布局ID',
    `version` int(255) COMMENT '版本',
    `descr` varchar(255) COMMENT '表单描述',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单定义';

CREATE TABLE IF NOT EXISTS `form_instance`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `form_id` bigint(255) COMMENT '表单ID',
    `form_name` varchar(255) COMMENT '表单名称',
    `form_code` varchar(255) COMMENT '表单code,用作页面表单',
    `form_table_id` bigint(255) COMMENT '表单对应的表ID',
    `form_table_name` varchar(255) COMMENT '表单对应数据库中生成的table name',
    `form_descr` varchar(255) COMMENT '表单描述',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单实例';

CREATE TABLE IF NOT EXISTS `form_field`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `form_id` bigint(255) COMMENT '表单Id',
    `field_id` bigint(255) COMMENT '字段Id',
    `field_code` varchar(255) COMMENT '字段编码，自动生成，对应到数据库中的字段名',
    `field_typ` varchar(255) COMMENT '字段类型，对应到field_type表中的type_code',
    `field_name` varchar(255) COMMENT '字段名称',
    `descr` varchar(255) COMMENT '字段描述',
    `sorted` int(255) COMMENT '字段排序',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段';

CREATE TABLE IF NOT EXISTS `form_field_typ`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id` bigint(255) COMMENT 'id',
    `typ_code` varchar(255) COMMENT '类型code：input/checkbox/radio/select/textarea',
    `typ_name` varchar(255) COMMENT '类型名称',
    `descr` varchar(255) COMMENT '描述',
    `sorted` int(255) COMMENT '排序',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段类型';

CREATE TABLE IF NOT EXISTS `form_field_option`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `field_id` bigint(255) COMMENT '字段id',
    `option_id` bigint(255) COMMENT '选项id',
    `option_name` varchar(255) COMMENT '选项名',
    `option_val` varchar(255) COMMENT '选项值',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段选项';

CREATE TABLE IF NOT EXISTS `form_layout`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `table_id` bigint(255) COMMENT '表ID',
    `layout_name` varchar(255) COMMENT '布局名称',
    `layout_code` varchar(255) COMMENT '布局码',
    `descr` varchar(255) COMMENT '描述',
    `editor_source_id` bigint(255) COMMENT '原sourceId',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单布局';

CREATE TABLE IF NOT EXISTS `from_bytearray`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(255) COMMENT '名称',
    `content_byte` varchar(255) COMMENT '二进制内容',
    `st` timestamp(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单数据';
