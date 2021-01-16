/*
Navicat MySQL Data Transfer

Source Server         : 199
Source Server Version : 50726
Source Host           : 192.168.1.199:3306
Source Database       : unichain_product

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-08-01 02:13:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_resources
-- ----------------------------
DROP TABLE IF EXISTS `app_resources`;
CREATE TABLE `app_resources` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '标识主键',
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `val` varchar(1000) DEFAULT NULL COMMENT '应用值',
  `type` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for category_attr
-- ----------------------------
DROP TABLE IF EXISTS `category_attr`;
CREATE TABLE `category_attr` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(11) DEFAULT NULL COMMENT '类目ID',
  `name` varchar(25) DEFAULT NULL COMMENT '类目名称',
  `key` varchar(25) DEFAULT NULL COMMENT '类目英文KEY',
  `disable` int(1) unsigned zerofill DEFAULT NULL COMMENT '石否可用',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for category_attr_opts
-- ----------------------------
DROP TABLE IF EXISTS `category_attr_opts`;
CREATE TABLE `category_attr_opts` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(11) DEFAULT NULL COMMENT '类目ID',
  `category_attr_id` bigint(11) DEFAULT NULL COMMENT '类目属性ID',
  `lable` varchar(25) DEFAULT NULL COMMENT '属性lable\r\n',
  `val` varchar(25) DEFAULT NULL COMMENT '类目属性值',
  `disable` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否可用（0：可用，1：不可用）',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for channel_account
-- ----------------------------
DROP TABLE IF EXISTS `channel_account`;
CREATE TABLE `channel_account` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_id` bigint(10) NOT NULL COMMENT '账号编号',
  `channel_id` bigint(10) NOT NULL,
  `disable` int(1) unsigned zerofill DEFAULT '0' COMMENT '石否可用',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for channel_config
-- ----------------------------
DROP TABLE IF EXISTS `channel_config`;
CREATE TABLE `channel_config` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '渠道',
  `channel_code` varchar(30) DEFAULT NULL COMMENT '渠道对应代码',
  `uv_price` decimal(10,2) DEFAULT NULL COMMENT 'uv单价',
  `reg_price` decimal(10,2) DEFAULT NULL COMMENT '注册单价',
  `spread_url` varchar(255) DEFAULT NULL COMMENT '引流页地址',
  `open_status` tinyint(2) DEFAULT '0' COMMENT '熔断机制  1 开启  0 关闭',
  `account_id` bigint(10) DEFAULT NULL COMMENT '登录账号',
  `disable` int(1) unsigned zerofill DEFAULT '0' COMMENT '石否可用',
  `optimize_value` decimal(3,2) DEFAULT NULL,
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for channel_mer
-- ----------------------------
DROP TABLE IF EXISTS `channel_mer`;
CREATE TABLE `channel_mer` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道商',
  `channel_code` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道',
  `account_id` bigint(10) DEFAULT NULL COMMENT '账号编号',
  `disable` int(1) unsigned zerofill DEFAULT '0' COMMENT '石否可用',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for finance_category
-- ----------------------------
DROP TABLE IF EXISTS `finance_category`;
CREATE TABLE `finance_category` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(11) DEFAULT NULL COMMENT '父ID',
  `name` varchar(20) DEFAULT NULL COMMENT '类目名称',
  `code` varchar(20) DEFAULT NULL COMMENT '类目码',
  `icon` varchar(255) DEFAULT NULL COMMENT '类目ICON',
  `sort` tinyint(3) DEFAULT '1' COMMENT '排序',
  `state` int(1) DEFAULT NULL COMMENT '状态',
  `app_code` varchar(30) DEFAULT NULL COMMENT 'app标识',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for finance_spu
-- ----------------------------
DROP TABLE IF EXISTS `finance_spu`;
CREATE TABLE `finance_spu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(55) DEFAULT NULL COMMENT '金融产品名称',
  `code` varchar(20) DEFAULT NULL COMMENT '产品码',
  `amount` decimal(11,0) DEFAULT NULL COMMENT '额度',
  `rate` decimal(11,5) DEFAULT NULL COMMENT '费率',
  `min_period` int(10) NOT NULL COMMENT '最小周期',
  `max_period` int(10) DEFAULT NULL COMMENT '最大周期',
  `period_unit` varchar(10) DEFAULT NULL COMMENT '周期单位（天/月/年）',
  `source_url` varchar(255) DEFAULT NULL COMMENT '产品链接',
  `remain` decimal(3,2) DEFAULT NULL COMMENT '剩余数量',
  `app_code` varchar(20) DEFAULT NULL COMMENT '应用码',
  `icon` varchar(255) DEFAULT NULL COMMENT 'ICON图标/URL',
  `descr` varchar(255) DEFAULT NULL,
  `uv_limit` bigint(100) DEFAULT NULL COMMENT 'uv限额',
  `sort` tinyint(3) DEFAULT '1' COMMENT '排序',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for finance_spu_attr
-- ----------------------------
DROP TABLE IF EXISTS `finance_spu_attr`;
CREATE TABLE `finance_spu_attr` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `fspu_id` bigint(11) DEFAULT NULL COMMENT '金融产品ID',
  `key` varchar(25) DEFAULT NULL COMMENT '属性KEY',
  `name` varchar(25) DEFAULT NULL COMMENT '属性名称',
  `val` varchar(25) DEFAULT NULL COMMENT '属性值',
  `app_code` varchar(25) DEFAULT NULL COMMENT '应用码',
  `attr_type` int(2) DEFAULT NULL COMMENT '属性类型',
  `disable` int(1) unsigned zerofill DEFAULT NULL COMMENT '是否可用（0：可用，1不可用）',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for finance_spu_category
-- ----------------------------
DROP TABLE IF EXISTS `finance_spu_category`;
CREATE TABLE `finance_spu_category` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `spu_id` bigint(11) DEFAULT NULL COMMENT '金融产品ID',
  `category_id` bigint(11) DEFAULT NULL COMMENT '金融类目ID',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for finance_spu_channel
-- ----------------------------
DROP TABLE IF EXISTS `finance_spu_channel`;
CREATE TABLE `finance_spu_channel` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `spu_id` bigint(10) DEFAULT NULL COMMENT '产品Id',
  `channel_id` bigint(10) DEFAULT NULL COMMENT '渠道编号',
  `disable` int(1) unsigned zerofill DEFAULT NULL COMMENT '石否可用',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for finance_spu_market
-- ----------------------------
DROP TABLE IF EXISTS `finance_spu_market`;
CREATE TABLE `finance_spu_market` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `spu_id` bigint(11) DEFAULT NULL COMMENT '金融产品编号',
  `url` varchar(300) DEFAULT NULL COMMENT '引流页产品',
  `admin_url` varchar(255) DEFAULT NULL COMMENT '看板后台Url',
  `account_name` varchar(50) DEFAULT NULL COMMENT '账号',
  `pwd` varchar(20) DEFAULT NULL COMMENT '密码',
  `disable` int(1) unsigned zerofill DEFAULT NULL COMMENT '石否可用',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for finance_spu_statistics
-- ----------------------------
DROP TABLE IF EXISTS `finance_spu_statistics`;
CREATE TABLE `finance_spu_statistics` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '标识列',
  `spu_id` bigint(10) DEFAULT NULL COMMENT '产品编号',
  `uv_price` decimal(10,2) DEFAULT NULL COMMENT 'uv单价',
  `cpa_price` decimal(10,2) DEFAULT NULL COMMENT 'cpa单价',
  `cps_price` decimal(10,2) DEFAULT NULL COMMENT 'cps单价',
  `pv_price` decimal(10,2) DEFAULT NULL COMMENT 'pv单价',
  `disable` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
