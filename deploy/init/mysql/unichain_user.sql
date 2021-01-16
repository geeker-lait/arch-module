
create database unichain_user;
use unichain_user;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for download_log
-- ----------------------------
DROP TABLE IF EXISTS `download_log`;
CREATE TABLE `download_log` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `phone_no` varchar(20) DEFAULT NULL COMMENT '用户Id，逻辑主键',
  `device_id` varchar(100) DEFAULT NULL COMMENT '设备id',
  `phone_type` varchar(32) DEFAULT NULL COMMENT '手机类型',
  `os` varchar(15) DEFAULT NULL COMMENT '系统类型',
  `download_type` tinyint(4) DEFAULT '0' COMMENT '下载类型 0：注册下载 1：直接下载',
  `ip` varchar(200) DEFAULT NULL COMMENT '请求ip',
  `location` varchar(30) DEFAULT NULL COMMENT '位置',
  `phone_info` varchar(1000) DEFAULT NULL COMMENT '手机信息',
  `source` varchar(100) DEFAULT NULL,
  `app_name` varchar(50) NOT NULL COMMENT 'app名称',
  `disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_ctime` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='下载点击日志';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `user_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '用户id（分布式ID生成）',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `age` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '年龄',
  `domicile` varchar(50) NOT NULL DEFAULT '' COMMENT '户籍地',
  `id_card` varchar(50) NOT NULL DEFAULT '' COMMENT '身份证号码',
  `sex` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别(0男 1女)',
  `birthday` varchar(50) NOT NULL DEFAULT '' COMMENT '生日',
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '状态(1正常 0禁用)',
  `test` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '测试(0非测试 1测试)',
  `app_name` varchar(50) NOT NULL DEFAULT '' COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0 未删除 1删除)',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_user_id_uindex` (`user_id`),
  KEY `idx_app_name` (`app_name`) USING BTREE COMMENT 'app'
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COMMENT='user';

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '用户地址',
  `province` varchar(32) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(32) NOT NULL DEFAULT '' COMMENT '市',
  `district` varchar(32) NOT NULL DEFAULT '' COMMENT '区',
  `adress` varchar(100) NOT NULL DEFAULT '' COMMENT '详细地址',
  `index` int(4) DEFAULT NULL COMMENT '地址顺序',
  `default_address` varchar(100) DEFAULT NULL COMMENT '默认地址',
  `app_name` varchar(50) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_bind_log
-- ----------------------------
DROP TABLE IF EXISTS `user_bind_log`;
CREATE TABLE `user_bind_log` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户Id，逻辑主键',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `id_number` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `card_no` varchar(25) DEFAULT NULL COMMENT '银行卡号',
  `bank_name` varchar(30) NOT NULL DEFAULT '' COMMENT '银行名称',
  `reserved_phone` varchar(11) DEFAULT '' COMMENT '银行预留手机号',
  `platform` varchar(30) DEFAULT NULL COMMENT '签要平台',
  `sign_status` varchar(4) DEFAULT NULL COMMENT '签约状态 0：发起中 1申请成功 2申请失败 3签约成功 4签约失败',
  `request_no` varchar(50) DEFAULT NULL COMMENT '获取验证码请求单号',
  `update_by` varchar(36) NOT NULL DEFAULT '' COMMENT '最后修改人',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_request_no` (`request_no`) USING BTREE,
  KEY `idx_sign_status` (`sign_status`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_card
-- ----------------------------
DROP TABLE IF EXISTS `user_card`;
CREATE TABLE `user_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `code` varchar(20) DEFAULT '' COMMENT '银行code',
  `account_name` varchar(30) NOT NULL COMMENT '用户',
  `bank_name` varchar(20) DEFAULT NULL COMMENT '银行名称',
  `user_id_code` varchar(30) DEFAULT NULL COMMENT '身份证号码',
  `account_type` varchar(30) DEFAULT NULL COMMENT '借记卡，信用卡',
  `reserved_phone` varchar(20) NOT NULL COMMENT '预留手机号',
  `card_num` varchar(20) NOT NULL COMMENT '银行卡号',
  `default_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否默认账号 0 否 1 是',
  `indexs` int(4) DEFAULT '1' COMMENT '银行卡顺序',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id_code` (`user_id_code`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE,
  KEY `idx_ctime` (`ctime`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_credit
-- ----------------------------
DROP TABLE IF EXISTS `user_credit`;
CREATE TABLE `user_credit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `zhima_scoreFlag` int(11) NOT NULL DEFAULT '0' COMMENT '芝麻分认证时间默认0未认证',
  `zhima_scoreTime` date NOT NULL COMMENT '认证时间',
  `salary_flag` int(11) NOT NULL DEFAULT '0' COMMENT '工资卡认证标记 默认0',
  `salary_time` date NOT NULL COMMENT '工资卡认证时间',
  `common_funds_flag` int(11) NOT NULL DEFAULT '0' COMMENT '公积金认证标记 默认0 未认证',
  `common_funds_time` date NOT NULL COMMENT '公积金认证时间',
  `social_security_flag` int(11) NOT NULL DEFAULT '0' COMMENT '社保认证标记默认0未认证',
  `social_security_time` date NOT NULL COMMENT '社保认证时间',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_extend
-- ----------------------------
DROP TABLE IF EXISTS `user_extend`;
CREATE TABLE `user_extend` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `wechat_number` varchar(20) NOT NULL COMMENT '微信号',
  `qq_number` varchar(20) NOT NULL COMMENT 'qq号',
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_id_card
-- ----------------------------
DROP TABLE IF EXISTS `user_id_card`;
CREATE TABLE `user_id_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `real_name` varchar(50) NOT NULL COMMENT '身份证姓名',
  `id_number` varchar(20) NOT NULL COMMENT '身份证号码',
  `sex` varchar(20) DEFAULT NULL COMMENT '性别',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `id_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `sign_org` varchar(30) DEFAULT NULL COMMENT '签发机关',
  `effect_time` varchar(30) DEFAULT '' COMMENT '身份证生效日期',
  `input_type` tinyint(2) DEFAULT NULL COMMENT '输入类型 1 手动输入 2 自动填充',
  `face_recognition` varchar(50) DEFAULT NULL COMMENT '人脸识别结果',
  `face_recognition_similarity` decimal(10,2) DEFAULT NULL COMMENT '人脸识别相似度',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_id_number` (`id_number`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE,
  KEY `idx_ctime` (`ctime`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_infos
-- ----------------------------
DROP TABLE IF EXISTS `user_infos`;
CREATE TABLE `user_infos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `marital_status` varchar(30) DEFAULT NULL COMMENT '婚姻状况',
  `highest_degree` varchar(100) DEFAULT NULL COMMENT '学历',
  `living_address` varchar(50) DEFAULT NULL COMMENT '居住详细地址',
  `living_province` varchar(50) DEFAULT NULL COMMENT '居住地省',
  `living_city` varchar(30) DEFAULT NULL COMMENT '居住地市',
  `living_district` varchar(30) DEFAULT NULL COMMENT '居住地区',
  `index` int(4) DEFAULT '1' COMMENT '顺序',
  `app_name` varchar(20) NOT NULL DEFAULT '' COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE,
  KEY `idx_ctime` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_job
-- ----------------------------
DROP TABLE IF EXISTS `user_job`;
CREATE TABLE `user_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `industry` varchar(30) DEFAULT NULL COMMENT '行业',
  `profession` varchar(30) DEFAULT NULL COMMENT '职业',
  `company` varchar(30) DEFAULT NULL COMMENT '公司名称',
  `company_address` varchar(50) DEFAULT NULL COMMENT '公司详细地址',
  `company_province` varchar(30) DEFAULT NULL COMMENT '省份',
  `company_city` varchar(30) DEFAULT NULL COMMENT '城市',
  `company_district` varchar(30) DEFAULT NULL COMMENT '地区',
  `position` varchar(30) DEFAULT NULL COMMENT '职位',
  `salary` varchar(30) DEFAULT NULL COMMENT '薪资',
  `tel_district_no` varchar(30) DEFAULT NULL COMMENT '公司区号',
  `telephone` varchar(30) DEFAULT NULL COMMENT '公司电话',
  `tel_ext_no` varchar(30) DEFAULT NULL COMMENT '分号机',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删除 1删除)',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_relationship
-- ----------------------------
DROP TABLE IF EXISTS `user_relationship`;
CREATE TABLE `user_relationship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '关系人名称',
  `relationship` varchar(10) NOT NULL DEFAULT '' COMMENT '关系：母亲/父亲',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '关系人手机号',
  `indexs` int(4) NOT NULL DEFAULT '1' COMMENT '顺序',
  `relationship_type` varchar(30) NOT NULL COMMENT '联系人类型(紧急联系人，间接联系人)',
  `app_name` varchar(20) NOT NULL COMMENT 'app名称',
  `disable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_ctime` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
