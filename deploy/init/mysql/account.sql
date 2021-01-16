
use unichain_account;
drop table if exists unichain_account.account;

/*==============================================================*/
/* Table: account                                               */
/*==============================================================*/
create table unichain_account.account
(
   id                   bigint(20) not null auto_increment comment 'id主键自增',
   account_id           bigint(20) not null default 0 comment '账户id',
   account_name         varchar(50) not null comment '登陆账号(用户自定义)',
   app_code             varchar(50) not null comment 'app编号',
   source               varchar(50) not null default '' comment '引流来源',
   type                 tinyint not null comment '类型',
   merchant             tinyint not null default 0 comment '是否是渠道商',
   remark               varchar(50) not null default '' comment '备注',
   status               tinyint(1) not null default 1 comment '状态(1正常 0禁用 2重置密码禁用)',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.account
(
   account_id
);

/*==============================================================*/
/* Index: idx_status                                            */
/*==============================================================*/
create index idx_status on unichain_account.account
(
   status
);

drop table if exists unichain_account.user_account;

/*==============================================================*/
/* Table: user_account                                          */
/*==============================================================*/
create table unichain_account.user_account
(
   id                   bigint(20) not null auto_increment comment 'id主键自增',
   account_id           bigint(20) not null default 0 comment '账户id',
   user_id              bigint(20) not null comment '用户id',
   app_code             varchar(50) not null comment 'app编号',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.user_account
(
   account_id
);

/*==============================================================*/
/* Index: idx_app_code                                          */
/*==============================================================*/
create index idx_app_code on unichain_account.user_account
(
   app_code
);


drop table if exists unichain_account.account_step;

/*==============================================================*/
/* Table: account_step                                          */
/*==============================================================*/
create table unichain_account.account_step
(
   id                   bigint(20) not null auto_increment,
   app_code             varchar(50) not null comment 'appcode',
   type                 varchar(50) not null comment '类型',
   key_id               varchar(50) not null comment '前端页面唯一标识',
   key_order            tinyint not null default 2 comment '顺序',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.account_step
(
   app_code
);

/*==============================================================*/
/* Index: key_idx                                               */
/*==============================================================*/
create index key_idx on unichain_account.account_step
(
   key_id
);


drop table if exists unichain_account.account_step_stat;

/*==============================================================*/
/* Table: account_step_stat                                     */
/*==============================================================*/
create table unichain_account.account_step_stat
(
   id                   bigint(20) not null auto_increment,
   account_id           bigint(20) not null comment '账号ID',
   step_id              bigint(20) not null comment '步骤ID',
   status               tinyint not null default 2 comment '状态，1-完成填写，2-未完成',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.account_step_stat
(
   account_id
);

/*==============================================================*/
/* Index: status_idx                                            */
/*==============================================================*/
create index status_idx on unichain_account.account_step_stat
(
   step_id
);


drop table if exists unichain_account.account_phone;

/*==============================================================*/
/* Table: account_phone                                         */
/*==============================================================*/
create table unichain_account.account_phone
(
   id                   bigint(20) not null auto_increment comment 'id主键自增',
   account_id           bigint(20) not null default 0 comment '账户id',
   app_code             varchar(50) not null comment 'app编号',
   phone                varchar(20) not null default '1' comment '绑定手机号',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.account_phone
(
   account_id
);

/*==============================================================*/
/* Index: idx_app_code                                          */
/*==============================================================*/
create index idx_app_code on unichain_account.account_phone
(
   app_code
);


drop table if exists unichain_account.pwd;

/*==============================================================*/
/* Table: pwd                                                   */
/*==============================================================*/
create table unichain_account.pwd
(
   id                   bigint(20) not null auto_increment comment 'id主键自增',
   account_id           bigint(20) not null default 0 comment '账户id',
   pwd                  varchar(20) not null comment '密码',
   is_lock              tinyint(1) not null comment '是否锁定',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.pwd
(
   account_id
);

/*==============================================================*/
/* Index: idx_islock                                            */
/*==============================================================*/
create index idx_islock on unichain_account.pwd
(
   is_lock
);


create table unichain_account.access_login
(
   id                   bigint(20) not null auto_increment comment 'id主键自增',
   account_id           bigint(20) not null default 0 comment '账户id',
   access_token         varchar(20) not null comment 'token',
   openid               varchar(20) not null comment '开放平台编号',
   expires_in           bigint not null comment '到期时间',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.access_login
(
   account_id
);

create table unichain_account.account_group
(
   id                   bigint(20) not null auto_increment,
   account_id           bigint(50) not null comment '账号Id',
   group_id             bigint(20) not null comment '组名',
   status               tinyint not null default 0 comment '状态',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.account_group
(
   account_id
);



drop table if exists unichain_account.account_role;

/*==============================================================*/
/* Table: account_role                                          */
/*==============================================================*/
create table unichain_account.account_role
(
   id                   bigint(20) not null auto_increment,
   account_id           bigint(50) not null comment '账号ID',
   role_id              bigint(20) not null comment '角色ID',
   status               tinyint not null default 0 comment '状态',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_role_id                                           */
/*==============================================================*/
create index idx_role_id on unichain_account.account_role
(
   role_id
);

/*==============================================================*/
/* Index: idx_account_id                                        */
/*==============================================================*/
create index idx_account_id on unichain_account.account_role
(
   account_id
);

/*==============================================================*/
/* Index: uidx                                                  */
/*==============================================================*/
create index uidx on unichain_account.account_role
(
   account_id,
   role_id
);

drop table if exists unichain_account.groups;

/*==============================================================*/
/* Table: groups                                                */
/*==============================================================*/
create table unichain_account.groups
(
   id                   bigint(20) not null auto_increment,
   pid                  bigint(20) not null comment '上级组织ID',
   name                 varchar(50) not null comment '组名',
   code                 varchar(50) not null comment '组编码',
   status               tinyint(1) not null comment '状态（1-启用 0-停用）',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_pid                                               */
/*==============================================================*/
create index idx_pid on unichain_account.groups
(
   pid
);

drop table if exists unichain_account.group_role;

/*==============================================================*/
/* Table: group_role                                            */
/*==============================================================*/
create table unichain_account.group_role
(
   id                   bigint(20) not null auto_increment,
   role_id              bigint(50) not null comment '角色ID',
   group_id             bigint(20) not null comment '组织ID',
   status               tinyint not null comment '狀態',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_role_id                                           */
/*==============================================================*/
create index idx_role_id on unichain_account.group_role
(
   role_id
);


drop table if exists unichain_account.role;

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table unichain_account.role
(
   id                   bigint(20) not null auto_increment,
   name                 varchar(50) not null comment '角色名',
   status               tinyint(1) not null comment '状态',
   icon                 varchar(50) not null default '' comment '角色对应的图标',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);


drop table if exists unichain_account.accredit;

/*==============================================================*/
/* Table: accredit                                              */
/*==============================================================*/
create table unichain_account.accredit
(
   id                   bigint(20) not null auto_increment,
   role_id              bigint not null comment '角色ID',
   resource_id          varchar(50) not null comment '资源ID',
   permission_id        bigint not null comment '权限',
   status               tinyint not null default 0,
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_role_id                                           */
/*==============================================================*/
create index idx_role_id on unichain_account.accredit
(
   role_id
);

/*==============================================================*/
/* Index: uidx_roles                                            */
/*==============================================================*/
create unique index uidx_roles on unichain_account.accredit
(
   role_id,
   resource_id,
   permission_id
);


drop table if exists unichain_account.resources;

/*==============================================================*/
/* Table: resources                                             */
/*==============================================================*/
create table unichain_account.resources
(
   id                   bigint(20) not null auto_increment,
   resource_id          varchar(50) not null comment '资源ID',
   name                 varchar(50) not null default '' comment '资源名称',
   path                 varchar(100) not null default '' comment '路径',
   val                  varchar(100) not null default '' comment '资源值',
   icon                 varchar(100) not null default '' comment '资源icon图标',
   level                bigint(20) not null default 0 comment '层级',
   descr                varchar(50) not null default '' comment '资源描述',
   app_code             varchar(50) not null default '' comment 'app_code',
   type                 varchar(50) not null default '' comment '资源类型',
   status               tinyint(1) not null default 0 comment '状态(1-可用，0-禁用)',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);

/*==============================================================*/
/* Index: idx_resources_id                                      */
/*==============================================================*/
create index idx_resources_id on unichain_account.resources
(
   resource_id
);

/*==============================================================*/
/* Index: idx_val                                               */
/*==============================================================*/
create index idx_val on unichain_account.resources
(
   val
);


drop table if exists unichain_account.permission;

/*==============================================================*/
/* Table: permission                                            */
/*==============================================================*/
create table unichain_account.permission
(
   id                   bigint(20) not null auto_increment,
   name                 varchar(50) not null comment '权限名称',
   icon                 varchar(50) not null default '' comment '权限对应图标',
   val                  tinyint(1) not null comment '权限值',
   status               tinyint(1) not null comment '状态（1-可用，0-禁用）',
   ctime                timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
   utime                timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
);


INSERT INTO `role` (`id`,`name`,`status`,`icon`,`ctime`,`utime`) VALUES (1,'管理员',1,'','2019-07-16 10:00:48','2019-07-16 11:45:38');
INSERT INTO `role` (`id`,`name`,`status`,`icon`,`ctime`,`utime`) VALUES (2,'查看者',1,'','2019-07-16 11:46:49','2019-07-16 11:46:49');

INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (10,'101919719000010','产品管理','/product-manage','','bug','','portal','MENU',0,1,'2019-07-18 09:44:24','2019-07-19 09:10:34');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (11,'101919719000011','市场管理','/market-manage','','drag','','portal','MENU',0,1,'2019-07-18 09:46:21','2019-07-19 09:10:00');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (12,'101919719000012','用户管理','/user-manage','','dashboard','','portal','MENU',0,1,'2019-07-18 09:46:42','2019-07-19 02:59:21');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (13,'101919719000013','运营管理','/operate-manage','','dashboard','','portal','MENU',0,1,'2019-07-18 09:47:33','2019-07-19 02:59:27');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (14,'101919719000014','系统管理','/sys-manage','','dashboard','','portal','MENU',0,1,'2019-07-18 09:47:51','2019-07-19 02:59:36');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (15,'101919719000015','推广渠道列表','/market-manage/spread-list','/market-manage/spread-list','dashboard','','portal','MENU',101919719000011,1,'2019-07-18 09:50:28','2019-07-19 03:03:46');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (16,'101919719000016','推广渠道数据','/market-manage/spread-data','/market-manage/spread-data','dashboard','','portal','MENU',101919719000011,1,'2019-07-18 09:52:25','2019-07-19 03:03:52');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (17,'101919719000017','产品数据','/market-manage/product-data','/market-manage/product-data','dashboard','','portal','MENU',101919719000011,1,'2019-07-18 09:52:53','2019-07-19 03:03:54');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (18,'101919719000018','产品列表','/product-manage/index','/product-manage/index','dashboard','','portal','MENU',101919719000010,1,'2019-07-18 09:56:54','2019-07-19 03:03:56');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (19,'101919719000019','用户申请','/user-manage/apply','/user-manage/apply','dashboard','','portal','MENU',101919719000012,1,'2019-07-18 10:01:16','2019-07-19 03:03:58');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (20,'101919719000020','用户列表','/user-manage/list','/user-manage/list','dashboard','','portal','MENU',101919719000012,1,'2019-07-18 10:01:39','2019-07-19 03:04:01');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (21,'101919719000021','日常运营','/operate-manage/day/day','/operate-manage/day/day','dashboard','','portal','MENU',101919719000013,1,'2019-07-18 10:02:27','2019-07-19 03:04:04');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (22,'101919719000022','帮助中心','/operate-manage/help-center/help-center','/operate-manage/help-center/help-center','dashboard','','portal','MENU',101919719000013,1,'2019-07-18 10:02:50','2019-07-22 10:06:20');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (23,'101919719000023','系统权限','/sys-manage/sys-permission','/sys-manage/sys-permission','dashboard','','portal','MENU',101919719000014,1,'2019-07-18 10:03:48','2019-07-19 03:04:08');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (24,'101919719000024','意见反馈','/operate-manage/feedback/feedback','/operate-manage/feedback/feedback','dashboard','','portal','MENU',101919719000013,1,'2019-07-23 01:43:03','2019-07-23 01:43:24');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (25,'101919719000025','其他','/other','','drag','','portal','MENU',0,1,'2019-07-26 01:13:45','2019-07-26 01:14:28');
INSERT INTO `resources` (`id`,`resource_id`,`name`,`path`,`val`,`icon`,`descr`,`app_code`,`type`,`level`,`status`,`ctime`,`utime`) VALUES (26,'101919719000026','渠道商页面','/other/channel-page','/other/channel-page','bug','','portal','MENU',101919719000025,1,'2019-07-26 01:14:47','2019-07-26 01:15:53');


INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (2,1,'101919719000010',2,1,'2019-07-16 11:49:57','2019-07-18 09:57:36');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (3,1,'101919719000011',2,1,'2019-07-16 11:50:03','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (6,1,'101919719000012',2,1,'2019-07-16 11:50:11','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (7,1,'101919719000013',2,1,'2019-07-16 11:50:14','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (8,1,'101919719000014',2,1,'2019-07-17 12:32:46','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (9,1,'101919719000015',2,1,'2019-07-17 12:32:46','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (10,1,'101919719000016',2,1,'2019-07-16 11:50:20','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (11,1,'101919719000017',2,1,'2019-07-17 12:32:47','2019-07-18 09:57:37');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (12,1,'101919719000018',2,1,'2019-07-18 10:11:40','2019-07-18 10:11:40');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (13,1,'101919719000019',2,1,'2019-07-18 10:11:40','2019-07-18 10:11:40');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (14,1,'101919719000020',2,1,'2019-07-18 10:11:40','2019-07-18 10:11:40');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (15,1,'101919719000021',2,1,'2019-07-18 10:11:40','2019-07-18 10:11:40');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (16,1,'101919719000022',2,1,'2019-07-18 10:11:40','2019-07-18 10:11:40');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (17,1,'101919719000023',2,1,'2019-07-18 10:11:40','2019-07-18 10:11:40');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (18,1,'101919719000024',2,1,'2019-07-23 02:01:25','2019-07-23 02:01:32');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (257,1,'101919719000025',2,1,'2019-07-31 01:17:29','2019-07-31 01:17:29');
INSERT INTO `accredit` (`id`,`role_id`,`resource_id`,`permission_id`,`status`,`ctime`,`utime`) VALUES (258,1,'101919719000026',2,1,'2019-07-31 01:20:22','2019-07-31 01:20:22');

INSERT INTO `permission` (`id`,`name`,`icon`,`val`,`status`,`ctime`,`utime`) VALUES (1,'查看','',1,1,'2019-07-16 10:03:47','2019-07-23 07:27:49');
INSERT INTO `permission` (`id`,`name`,`icon`,`val`,`status`,`ctime`,`utime`) VALUES (2,'编辑','',2,1,'2019-07-16 10:03:59','2019-07-23 07:27:49');

INSERT INTO `pwd` (`id`,`account_id`,`pwd`,`is_lock`,`ctime`,`utime`) VALUES (3,123456789,'123456',1,'2019-07-16 07:16:32','2019-07-16 07:16:32');

INSERT INTO `account_role` (`id`,`account_id`,`role_id`,`status`,`ctime`,`utime`) VALUES (1,123456789,1,1,'2019-07-17 01:04:47','2019-07-17 01:04:47');

INSERT INTO `account` (`id`,`account_id`,`account_name`,`app_code`,`source`,`type`,`status`,`ctime`,`utime`,`remark`,`merchant`) VALUES (3,123456789,'brother','portal','xx',5,1,'2019-07-16 07:15:28','2019-07-30 11:40:15','超級管理員2',0);
