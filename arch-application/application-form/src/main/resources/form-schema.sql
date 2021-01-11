create database if not exists `arch`;
use `arch`;
-- 表单表
create table upload_form
(
    form_id         int primary key auto_increment,
    form_name       varchar(100),
    form_desc       varchar(100),
    form_table_name varchar(100),
    create_time     date
);
-- 字段表
create table upload_field
(
    field_id    int primary key auto_increment,
    form_id     int,
    field_code  varchar(100),
    field_type  varchar(100),
    field_name  varchar(100),
    field_desc  varchar(100),
    field_order varchar(100),
    create_time date
);
-- 创建字段类型表，方便维护新增的字段类型
create table upload_field_type
(
    type_code  varchar(50) primary key,
    type_name  varchar(50),
    type_order varchar(10),
    type_desc  varchar(100)
);

-- 字段选项表
create table upload_option
(
    option_id   int,
    field_id    int,
    option_name varchar(300)
);
