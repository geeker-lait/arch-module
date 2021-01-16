/*
SQLyog Ultimate v12.2.6 (64 bit)
MySQL - 5.7.27 : Database - ums
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE IF NOT EXISTS `ums` DEFAULT CHARACTER SET utf8mb4;

USE `ums`;

/*Table structure for table `account_category` */

DROP TABLE IF EXISTS `account_category`;

CREATE TABLE `account_category` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '资源类目ID',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `pid` bigint(19) NOT NULL COMMENT '父节点ID',
  `category_name` varchar(64) NOT NULL COMMENT '资源类目名',
  `sorted` int(4) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `IDX_PID_SORTED` (`pid`,`sorted`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-资源类目';

/*Table structure for table `account_group` */

DROP TABLE IF EXISTS `account_group`;

CREATE TABLE `account_group` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '账号-权限ID',
  `group_pid` bigint(19) NOT NULL COMMENT '父ID',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `group_code` varchar(32) NOT NULL COMMENT '组code',
  `group_name` varchar(32) NOT NULL COMMENT '组织架构名',
  `group_icon` varchar(32) DEFAULT NULL COMMENT '组织架构ICON',
  `sorted` int(2) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `IDX_GROUP_PID_SORTED` (`group_pid`,`sorted`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-组织机构';

/*Table structure for table `account_identifier` */

DROP TABLE IF EXISTS `account_identifier`;

CREATE TABLE `account_identifier` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'AccountIdentifier ID',
  `aid` bigint(19) NOT NULL COMMENT '账号名ID',
  `identifier` varchar(32) NOT NULL COMMENT '识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；',
  `credential` varchar(32) NOT NULL COMMENT '授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `authorities` varchar(255) DEFAULT NULL COMMENT '用户角色:ROLE_xxx 与 租户id: TENANT_XXX',
  `channel_type` varchar(32) NOT NULL COMMENT '登录类型【IDENTITYTYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；',
  PRIMARY KEY (`aid`),
  UNIQUE KEY `IDX_IDENTIFIER_TENANT_ID` (`identifier`, `tenant_id`),
  KEY `IDX_AID` (`aid`),
  KEY `IDX_INTENT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-标识';

/*Table structure for table `account_member` */

DROP TABLE IF EXISTS `account_member`;

CREATE TABLE `account_member` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint(19) NOT NULL COMMENT '用户id',
  `member_level_id` int(3) NOT NULL COMMENT '会员级别ID',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`),
  KEY `IDX_ACCOUNT_ID` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-会员账号';

/*Table structure for table `account_menu` */

DROP TABLE IF EXISTS `account_menu`;

CREATE TABLE `account_menu` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '账号-菜单ID',
  `pid` bigint(19) NOT NULL COMMENT '父节点ID',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `menu_code` varchar(64) NOT NULL COMMENT '英文码',
  `menu_name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_val` varchar(64) DEFAULT NULL COMMENT '菜单值',
  `level` int(2) DEFAULT NULL COMMENT '层级',
  `sorted` int(2) NOT NULL COMMENT '排序',
  `frame` int(1) NOT NULL DEFAULT '1' COMMENT '是否iframe: 1是, 0不是, 默认: 1',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`),
  KEY `IDX_PID_SORTED` (`pid`,`sorted`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-菜单';

/*Table structure for table `account_name` */

DROP TABLE IF EXISTS `account_name`;

CREATE TABLE `account_name` (
  `account_id` bigint(19) NOT NULL COMMENT '账号ID/用户ID/会员ID/商户ID',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '用户昵称可随机生成',
  `avatar` varchar(64) DEFAULT NULL COMMENT '头像',
  `source` varchar(64) DEFAULT NULL COMMENT '来源, 推广统计用',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号名';

/*Table structure for table `account_oauth_client` */

DROP TABLE IF EXISTS `account_oauth_client`;

CREATE TABLE `account_oauth_client` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '授权客户端ID',
  `app_id` varchar(32) NOT NULL COMMENT 'appId 或 客户端ID',
  `app_code` varchar(32) NOT NULL COMMENT 'appSecret 或 客户端secret',
  `scopes` varchar(255) NOT NULL COMMENT 'openid/userinfo/token/code/资源服务器标识等',
  `app_type` varchar(32) NOT NULL COMMENT '客户端类型: web, 安卓, ios, 小程序…',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_CLIENT_ID_SECRET_TYP` (`app_id`,`app_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权客户端';

/*Table structure for table `account_oauth_token` */

DROP TABLE IF EXISTS `account_oauth_token`;

CREATE TABLE `account_oauth_token` (
  `account_identifier_id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'AccountIdentifierId',
  `enable_refresh` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否支持 refreshToken, 默认: 0. 1 表示支持, 0 表示不支持',
  `provider_id` varchar(20) DEFAULT NULL COMMENT '第三方服务商,如: qq,github',
  `access_token` varchar(64) DEFAULT NULL COMMENT 'accessToken',
  `expire_in` bigint(20) DEFAULT '-1' COMMENT 'accessToken过期时间 无过期时间默认为 -1',
  `refresh_token_expire_in` bigint(20) DEFAULT '-1' COMMENT 'refreshToken过期时间 无过期时间默认为 -1',
  `refresh_token` varchar(64) DEFAULT NULL COMMENT 'refreshToken',
  `uid` varchar(20) DEFAULT NULL COMMENT 'alipay userId',
  `open_id` varchar(64) DEFAULT NULL COMMENT 'qq/mi/toutiao/wechatMp/wechatOpen/weibo/jd/kujiale/dingTalk/douyin/feishu',
  `access_code` varchar(64) DEFAULT NULL COMMENT 'dingTalk, taobao 附带属性',
  `union_id` varchar(64) DEFAULT NULL COMMENT 'QQ附带属性',
  `scope` varchar(64) DEFAULT NULL COMMENT 'Google附带属性',
  `token_type` varchar(20) DEFAULT NULL COMMENT 'Google附带属性',
  `id_token` varchar(64) DEFAULT NULL COMMENT 'Google附带属性',
  `mac_algorithm` varchar(20) DEFAULT NULL COMMENT '小米附带属性',
  `mac_key` varchar(64) DEFAULT NULL COMMENT '小米附带属性',
  `code` varchar(64) DEFAULT NULL COMMENT '企业微信附带属性',
  `oauth_token` varchar(64) DEFAULT NULL COMMENT 'Twitter附带属性',
  `oauth_token_secret` varchar(64) DEFAULT NULL COMMENT 'Twitter附带属性',
  `user_id` varchar(64) DEFAULT NULL COMMENT 'Twitter附带属性',
  `screen_name` varchar(64) DEFAULT NULL COMMENT 'Twitter附带属性',
  `oauth_callback_confirmed` varchar(64) DEFAULT NULL COMMENT 'Twitter附带属性',
  `expire_time` bigint(20) DEFAULT '-1' COMMENT '过期时间, 基于 1970-01-01T00:00:00Z, 无过期时间默认为 -1',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`account_identifier_id`),
  KEY `IDX_ACCOUNT_IDENTIFIER_ID` (`account_identifier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方账号授权';

/*Table structure for table `account_operate_log` */

DROP TABLE IF EXISTS `account_operate_log`;

CREATE TABLE `account_operate_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint(19) NOT NULL COMMENT '用户id',
  `operator_typ` int(4) NOT NULL COMMENT '操作类型()',
  `operator_time` datetime NOT NULL COMMENT '操作时间',
  `record_val` varchar(512) NOT NULL COMMENT '记录的值json',
  PRIMARY KEY (`id`),
  KEY `IDX_ACCOUNT_ID` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号操作记录';

/*Table structure for table `account_permission` */

DROP TABLE IF EXISTS `account_permission`;

CREATE TABLE `account_permission` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '账号-菜单ID',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `permission_code` varchar(64) NOT NULL COMMENT '权限码(与RequestMethod对应)list(GET)/add(POST)/edit(PUT)/delete(DELETE)/..',
  `permission_name` varchar(64) DEFAULT NULL COMMENT '权限名称',
  `permission_val` varchar(64) DEFAULT NULL COMMENT '权限值',
  `permission_uri` varchar(64) NOT NULL COMMENT 'uri',
  `permission_typ` int(2) NOT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
  `sorted` int(3) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-权限';

/*Table structure for table `account_post` */

DROP TABLE IF EXISTS `account_post`;

CREATE TABLE `account_post` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_pid` bigint(19) NOT NULL COMMENT '父id',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `post_name` varchar(32) NOT NULL COMMENT '岗位名',
  `post_code` varchar(32) NOT NULL COMMENT '岗位code',
  `post_icon` varchar(32) DEFAULT NULL COMMENT 'icon',
  `salary` decimal(19,4) NOT NULL COMMENT '薪资',
  PRIMARY KEY (`id`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-岗位';

/*Table structure for table `account_relationship` */

DROP TABLE IF EXISTS `account_relationship`;

CREATE TABLE `account_relationship` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(19) NOT NULL COMMENT '父节点ID（数据库自增）',
  `org` int(11) NOT NULL COMMENT '组',
  `deep` int(11) NOT NULL COMMENT '深度',
  `seq` int(11) NOT NULL COMMENT '顺序',
  `from_user_id` bigint(19) NOT NULL COMMENT '推荐人ID',
  `from_user_name` varchar(32) NOT NULL COMMENT '推荐人姓名',
  `from_user_phone` varchar(11) NOT NULL COMMENT '推荐人手机',
  `to_user_id` varchar(19) NOT NULL COMMENT '账号ID',
  `to_user_name` varchar(32) NOT NULL COMMENT '用户名',
  `to_user_phone` varchar(11) NOT NULL COMMENT '用户手机',
  PRIMARY KEY (`id`),
  KEY `IDX_PID_SEQ` (`pid`, `seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-关系';

/*Table structure for table `account_resource` */

DROP TABLE IF EXISTS `account_resource`;

CREATE TABLE `account_resource` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '账号-资源表ID',
  `category_id` bigint(19) NOT NULL COMMENT '账号-资源类目ID',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `resource_name` varchar(64) NOT NULL COMMENT '资源名',
  `resource_code` varchar(64) NOT NULL COMMENT '资源码',
  `resource_Typ` int(4) NOT NULL COMMENT '类型: 1目录, 2菜单, 3按钮, 4链接',
  `resource_val` varchar(64) DEFAULT NULL COMMENT '资源值',
  `resource_path` varchar(64) NOT NULL COMMENT '资源路径',
  `resource_icon` varchar(64) DEFAULT NULL COMMENT '资源图标',
  `resource_desc` varchar(64) DEFAULT NULL COMMENT '资源描述',
  `visible` int(1) NOT NULL DEFAULT '0' COMMENT '是否隐藏: 0不隐藏, 1隐藏. 默认: 0',
  `level` int(2) DEFAULT NULL COMMENT '层级',
  PRIMARY KEY (`id`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-资源';

/*Table structure for table `account_role` */

DROP TABLE IF EXISTS `account_role`;

CREATE TABLE `account_role` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '账号角色ID',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `role_name` varchar(32) NOT NULL COMMENT '角色名',
  `icon` varchar(32) DEFAULT NULL COMMENT '角色icon',
  `sorted` int(3) NOT NULL COMMENT '顺序',
  PRIMARY KEY (`id`),
  KEY `IDX_SORTED` (`sorted`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色';

/*Table structure for table `account_role_group` */

DROP TABLE IF EXISTS `account_role_group`;

CREATE TABLE `account_role_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
  `role_id` bigint(19) NOT NULL COMMENT '角色ID',
  `group_id` bigint(19) NOT NULL COMMENT '组织ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_roleId_groupId_tenantId` (`role_id`,`group_id`, `tenant_id`),
  KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色组织或机构';

/*Table structure for table `account_role_menu` */

DROP TABLE IF EXISTS `account_role_menu`;

CREATE TABLE `account_role_menu` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
 `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
 `role_id` bigint(19) NOT NULL COMMENT '角色ID',
 `menu_id` bigint(19) NOT NULL COMMENT '菜单ID',
 PRIMARY KEY (`id`),
 UNIQUE KEY `IDX_roleId_menuId_tenantId` (`role_id`,`menu_id`, `tenant_id`),
 KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色菜单';

/*Table structure for table `account_role_permission` */

DROP TABLE IF EXISTS `account_role_permission`;

CREATE TABLE `account_role_permission` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
   `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
   `role_id` bigint(19) NOT NULL COMMENT '角色ID',
   `permission_id` bigint(19) NOT NULL COMMENT '权限ID',
   PRIMARY KEY (`id`,`role_id`,`permission_id`),
   UNIQUE KEY `IDX_roleId_permissionId_tenantId` (`role_id`,`permission_id`, `tenant_id`),
   KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色权限表';

/*Table structure for table `account_role_resource` */

DROP TABLE IF EXISTS `account_role_resource`;

CREATE TABLE `account_role_resource` (
 `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
 `tenant_id` VARCHAR(19) NOT NULL COMMENT '租户 id',
 `role_id` bigint(19) NOT NULL COMMENT '角色ID',
 `resource_id` bigint(19) NOT NULL COMMENT '资源ID',
 PRIMARY KEY (`id`),
 UNIQUE KEY `IDX_roleId_resourceId_tenantId` (`role_id`,`resource_id`, `tenant_id`),
 KEY `IDX_TENANT_ID` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色资源表';


/*Table structure for table `account_tag` */

DROP TABLE IF EXISTS `account_tag`;

CREATE TABLE `account_tag` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_id` bigint(19) NOT NULL COMMENT '用户id',
  `tag_category` varchar(32) NOT NULL COMMENT '标签类目',
  `tag_name` varchar(64) NOT NULL COMMENT '标签名',
  `tag_color` varchar(32) NOT NULL COMMENT '标签色',
  PRIMARY KEY (`id`),
  KEY `IDX_ACCOUNT_ID` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-标签';

/*Table structure for table `account_ticket` */

DROP TABLE IF EXISTS `account_ticket`;

CREATE TABLE `account_ticket` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '账号-券ID',
  `account_id` bigint(19) NOT NULL COMMENT '用户id',
  `ticket_typ` int(2) NOT NULL COMMENT '券类型：打折，优惠，抵用....',
  `ticket_no` varchar(32) NOT NULL COMMENT '券号',
  `ticket_val` varchar(6) NOT NULL COMMENT '券值',
  PRIMARY KEY (`id`),
  KEY `IDX_ACCOUNT_ID` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-券';

/*Table structure for table `auth_token` */

DROP TABLE IF EXISTS `auth_token`;

/*Table structure for table `user_address` */

DROP TABLE IF EXISTS `user_address`;

CREATE TABLE `user_address` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户地址信息表id',
  `user_id` bigint(19) NOT NULL COMMENT '用户id',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `district` varchar(32) DEFAULT NULL COMMENT '区',
  `street` varchar(32) DEFAULT NULL COMMENT '街道',
  `typ` int(1) DEFAULT NULL COMMENT '地址类型:工作地址/家庭地址/收获地址/..',
  `sorted` int(2) NOT NULL COMMENT '顺序',
  `contacts` varchar(32) DEFAULT NULL COMMENT '联系人',
  `phone_num` varchar(11) DEFAULT NULL COMMENT '手机号',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`,`user_id`),
  KEY `IDX_USER_ID_SORTED` (`user_id`,`sorted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户地址表';

/*Table structure for table `user_bank_card` */

DROP TABLE IF EXISTS `user_bank_card`;

CREATE TABLE `user_bank_card` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户银行卡信息表ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户id',
  `card_no` varchar(32) NOT NULL COMMENT '银行卡号',
  `card_bin` varchar(16) NOT NULL COMMENT '卡bin 如: 6221, 6222',
  `card_code` varchar(32) NOT NULL COMMENT '卡code 如: ICBC, ABC',
  `cvv` varchar(32) NOT NULL COMMENT '卡cvv',
  `cardTyp` int(1) NOT NULL COMMENT '卡类型:0: 储蓄卡, 1: 借记卡',
  `sorted` int(2) NOT NULL COMMENT '基于user_id的顺序',
  `validity` date NOT NULL COMMENT '有效期',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_SORTED` (`user_id`,`sorted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户银行卡信息';

/*Table structure for table `user_education` */

DROP TABLE IF EXISTS `user_education`;

CREATE TABLE `user_education` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户学历信息表ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  `certificate_no` varchar(24) NOT NULL COMMENT '证书编码',
  `certificate_name` varchar(32) DEFAULT NULL COMMENT '证书名称',
  `certificate_org` varchar(32) DEFAULT NULL COMMENT '证书登记机构',
  `certificate_level` varchar(32) NOT NULL COMMENT '学历(如: 大专, 本科, 硕士, 博士)',
  `sorted` int(2) NOT NULL COMMENT '顺序',
  `awardtime` date DEFAULT NULL COMMENT '颁发时间',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_SORTED` (`user_id`,`sorted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户学历信息';

/*Table structure for table `user_id_card` */

DROP TABLE IF EXISTS `user_id_card`;

CREATE TABLE `user_id_card` (
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  `idcard` varchar(24) NOT NULL COMMENT '身份证号',
  `name` varchar(32) NOT NULL COMMENT '名字',
  `age` int(2) NOT NULL COMMENT '年龄',
  `sex` int(1) NOT NULL COMMENT '性别',
  `birthday` date NOT NULL COMMENT '生日',
  `nation` varchar(32) NOT NULL COMMENT '民族',
  `domicile` varchar(32) NOT NULL COMMENT '居住地',
  `sign_org` varchar(64) NOT NULL COMMENT '颁发机构',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `IDX_ID_CARD` (`idcard`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户身份证表';

/*Table structure for table `user_job` */

DROP TABLE IF EXISTS `user_job`;

CREATE TABLE `user_job` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户工作信息表ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  `group_channel` varchar(32) DEFAULT NULL COMMENT '公司行业',
  `group_name` varchar(64) DEFAULT NULL COMMENT '公司名称',
  `post_name` varchar(12) DEFAULT NULL COMMENT '职位名称',
  `post_level` varchar(64) DEFAULT NULL COMMENT '职级',
  `sorted` int(2) NOT NULL COMMENT '顺序',
  `hiredate_time` date DEFAULT NULL COMMENT '入职时间',
  `dimission_time` date DEFAULT NULL COMMENT '离职时间',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_SORTED` (`user_id`,`sorted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户工作信息';

/*Table structure for table `user_phone` */

DROP TABLE IF EXISTS `user_phone`;

CREATE TABLE `user_phone` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户电话信息表ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  `phone_no` varchar(11) DEFAULT NULL COMMENT '手机号',
  `location` varchar(12) DEFAULT NULL COMMENT '号码归属地',
  `mno` varchar(12) DEFAULT NULL COMMENT '运营商: 移动/电信/联通/电话..',
  `sorted` int(2) DEFAULT NULL COMMENT '顺序',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_SORTED` (`user_id`,`sorted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户电话信息';

/*Table structure for table `user_relatives` */

DROP TABLE IF EXISTS `user_relatives`;

CREATE TABLE `user_relatives` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '用户亲朋信息表ID',
  `user_id` bigint(19) NOT NULL COMMENT '用户ID',
  `relatives_typ` int(1) DEFAULT NULL COMMENT '亲朋类型: 1. 家属, 2. 朋友',
  `relatives_name` varchar(12) DEFAULT NULL COMMENT '亲朋名称: 哥哥, 妹妹, 父亲, 母亲, 弟弟, 朋友, 同学',
  `relatives_sex` int(1) DEFAULT NULL COMMENT '亲朋性别: 0男, 1女',
  `sorted` int(2) NOT NULL COMMENT '顺序',
  `awardtime` date DEFAULT NULL COMMENT '颁发时间',
  `st` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  KEY `IDX_USER_ID_SORTED` (`user_id`,`sorted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户亲朋信息';


