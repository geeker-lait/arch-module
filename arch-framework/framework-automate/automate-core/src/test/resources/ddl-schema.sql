CREATE DATABASE IF NOT EXISTS `arch_module`;
USE `arch_module`;
-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `form_schema`(
    `id`  bigint(19) COMMENT '主键ID',
    `schema_name`  varchar(32) COMMENT '库名称/项目名称',
    `schema_code`  varchar(32) COMMENT '库名称/项目名称的code',
    `descr`  varchar(32) COMMENT '描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单schema';

CREATE TABLE IF NOT EXISTS `form_project_config`(
    `id`  bigint(19) COMMENT 'id主键',
    `project_id`  bigint(19) COMMENT '项目id',
    `mvn_json`  varchar(128) COMMENT '技术框架',
    `devops`  varchar(128) COMMENT '卡发环境配置',
    `git`  varchar(128) COMMENT '仓库地址',
    `docker`  varchar(128) COMMENT 'docker配置',
    `descr`  varchar(128) COMMENT '描述',
    `deleted` boolean COMMENT '是否逻辑删除',
    `st`  datatime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目配置';

CREATE TABLE IF NOT EXISTS `form_biz`(
    `id`  bigint(19) COMMENT 'id主键',
    `project_id`  bigint(19) COMMENT '项目id',
    `biz_name`  varchar(32) COMMENT '业务名称',
    `biz_code`  varchar(32) COMMENT '业务码',
    `descr`  varchar(128) COMMENT '业务说明',
    `sorted`  int(2) COMMENT '排序',
    `deleted` boolean COMMENT '是否逻辑删除',
    `st`  datatime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目业务';

CREATE TABLE IF NOT EXISTS `form_table`(
    `id`  bigint(19) COMMENT 'id主键',
    `project_id`  bigint(19) COMMENT '项目id',
    `biz_id`  bigint(19) COMMENT '业务id',
    `table_instance_id`  bigint(19) COMMENT '表单实力id',
    `deleted` boolean COMMENT '是否逻辑删除',
    `st`  datatime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务表单';

CREATE TABLE IF NOT EXISTS `form_interface`(
    `id`  bigint(19) COMMENT 'id主键',
    `interface_name`  varchar(32) COMMENT '接口名称',
    `interface_code`  varchar(32) COMMENT '接口',
    `param_json`  varchar(256) COMMENT '接口参数',
    `descr`  varchar(128) COMMENT '接口描述',
    `deleted` boolean COMMENT '是否逻辑删除',
    `st`  datatime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单接口';

CREATE TABLE IF NOT EXISTS `form_definition`(
    `category`  varchar(32) COMMENT '表单分类',
    `schema_id`  bigint(19) COMMENT 'schema主键id',
    `form_name`  varchar(32) COMMENT '表单名称',
    `form_code`  varchar(32) COMMENT '表单code,用作页面表单',
    `table_name`  varchar(32) COMMENT '关联数据表',
    `field_id`  bigint(19) COMMENT '表单部署字段ID',
    `layout_id`  bigint(19) COMMENT '表单部署布局ID',
    `version`  int(2) COMMENT '版本',
    `descr`  varchar(64) COMMENT '表单描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单定义';

CREATE TABLE IF NOT EXISTS `form_table_instance`(
    `schema_id`  bigint(19) COMMENT 'schema主键id',
    `form_id`  bigint(19) COMMENT '表单ID',
    `form_name`  varchar(32) COMMENT '表单名称',
    `form_code`  varchar(32) COMMENT '表单code,用作页面表单',
    `form_table_id`  bigint(19) COMMENT '表单对应的表ID',
    `form_table_name`  varchar(32) COMMENT '表单对应数据库中生成的table name',
    `form_descr`  varchar(32) COMMENT '表单描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单实例';

CREATE TABLE IF NOT EXISTS `form_field`(
    `form_id`  bigint(19) COMMENT '表单Id',
    `field_id`  bigint(19) COMMENT '字段Id',
    `field_code`  varchar(32) COMMENT '字段编码，自动生成，对应到数据库中的字段名',
    `field_typ`  varchar(32) COMMENT '字段类型，对应到field_type表中的type_code',
    `field_name`  varchar(32) COMMENT '字段名称',
    `descr`  varchar(32) COMMENT '字段描述',
    `sorted`  int(2) COMMENT '字段排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段';

CREATE TABLE IF NOT EXISTS `form_field_typ`(
    `typ_code`  varchar(32) COMMENT '类型code：input/checkbox/radio/select/textarea',
    `typ_name`  varchar(32) COMMENT '类型名称',
    `descr`  varchar(32) COMMENT '描述',
    `sorted`  int(2) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段类型';

CREATE TABLE IF NOT EXISTS `form_field_option`(
    `field_id`  bigint(19) COMMENT '字段id',
    `option_id`  bigint(19) COMMENT '选项id',
    `option_name`  varchar(32) COMMENT '选项名',
    `option_val`  varchar(32) COMMENT '选项值',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段选项';

CREATE TABLE IF NOT EXISTS `form_layout`(
    `table_id`  bigint(19) COMMENT '表ID',
    `layout_name`  varchar(32) COMMENT '布局名称',
    `layout_code`  varchar(32) COMMENT '布局码',
    `style`  varchar(255) COMMENT '样式',
    `descr`  varchar(32) COMMENT '描述',
    `editor_source_id`  bigint(19) COMMENT '原sourceId',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单布局';

CREATE TABLE IF NOT EXISTS `from_bytearray`(
    `id`  bigint(19) COMMENT 'id主键',
    `table_id`  bigint(19) COMMENT '表单id',
    `field_id`  bigint(19) COMMENT '字段Id',
    `name`  varchar(32) COMMENT '名称',
    `content_byte`  varchar(256) COMMENT '二进制内容',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单数据';

