/*
Navicat MySQL Data Transfer

Source Server         : 199
Source Server Version : 50726
Source Host           : 192.168.1.199:3306
Source Database       : unichain_config

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-08-01 00:35:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_config
-- ----------------------------
DROP TABLE IF EXISTS `app_config`;
CREATE TABLE `app_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(50) NOT NULL DEFAULT '' COMMENT '配置的key值',
  `config_value` varchar(1000) NOT NULL DEFAULT '' COMMENT '配置的key值',
  `marks` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '备注',
  `belong_service` varchar(20) NOT NULL DEFAULT '' COMMENT '配置所属服务',
  `app_name` varchar(50) NOT NULL DEFAULT '' COMMENT 'app名称',
  `disable` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删除 1删除)',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `belong_service` (`belong_service`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of app_config
-- ----------------------------
INSERT INTO `app_config` VALUES ('1', 'joVerifyFlag', 'ON', '', 'user', 'wsj', '0', '2018-12-13 19:23:51', '2018-12-13 19:23:51');
INSERT INTO `app_config` VALUES ('2', 'authEffectiveTime', '14', '', 'user', 'wsj', '0', '2018-12-17 21:38:15', '2018-12-17 21:38:15');
INSERT INTO `app_config` VALUES ('3', 'assessDetailExpireTime', '7', '', 'order', 'wsj', '0', '2018-12-21 14:31:25', '2018-12-21 14:31:25');
INSERT INTO `app_config` VALUES ('4', 'yeeAppKey', 'SQKK10025894744', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('5', 'authbindcardreqUri', '/rest/v1.0/paperorder/unified/auth/request', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('6', 'authbindcardconfirmUri', '/rest/v1.0/paperorder/auth/confirm', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('7', 'authbindcardresendUri', '/rest/v1.0/paperorder/auth/resend', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('8', 'authrecordqueryUri', '/rest/v1.0/paperorder/auth/query', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('9', 'authListqueryUri', '/rest/v1.0/paperorder/auth/bindcard/list', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('10', 'borrowExpireTime', '7', '', 'order', 'wsj', '0', '2019-01-04 21:54:55', '2019-01-04 21:54:55');
INSERT INTO `app_config` VALUES ('11', 'UnbindcardUri', '/rest/v1.0/paperorder/unbind/request', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('12', 'unibindcardpayUri', '/rest/v1.0/paperorder/unified/pay', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('13', 'firstpaysmsconfUri', '/rest/v1.0/paperorder/firstpayorder/confirm', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('14', 'firstpaysmsresendUri', '/rest/v1.0/paperorder/firstpayorder/resend', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('15', 'firstpayqueryUri', '/rest/v1.0/paperorder/firstpayorder/query', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('16', 'batchpayUri', '/rest/v1.0/paperorder/api/pay/batchtempcard/order', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('17', 'batchpayqueryUri', '/rest/v1.0/paperorder/api/pay/batchtempcard/query', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('18', 'refundUri', '/rest/v1.0/paperorder/api/refund/request', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('19', 'bindcardconfirmUri', '/rest/v1.0/paperorder/pay/confirm', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('20', 'bindcardpayresendUri', '/rest/v1.0/paperorder/pay/resend', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('21', 'bindcardpayqueryUri', '/rest/v1.0/paperorder/api/pay/query', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('22', 'unionfirstpayUri', '/rest/v1.0/paperorder/unified/firstpay', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('23', 'payCallBack', 'http://148.70.49.214:60000/api/pay/syn/callbackPayMsg', '', 'pay', 'wsj', '0', '2019-01-04 21:20:02', '2019-01-04 21:20:02');
INSERT INTO `app_config` VALUES ('24', 'judgeAssessFeeFlag', 'ON', '是否关闭评估费支付', 'order', 'wsj', '0', '2019-01-06 14:12:04', '2019-01-06 14:12:04');
INSERT INTO `app_config` VALUES ('25', 'judgeNeedAssessFlag', 'ON', '是否需要进行评估流程', 'order', 'wsj', '0', '2019-01-15 21:21:26', '2019-01-15 21:21:26');
INSERT INTO `app_config` VALUES ('26', 'assessFeeExpireTime', '4', '评估费支付失败限制时间', 'order', 'wsj', '0', '2019-01-15 21:21:26', '2019-01-15 21:21:26');
INSERT INTO `app_config` VALUES ('27', 'assessFeeExpireFlag', 'ON', '评估费支付失败是否需要限制开关', 'order', 'wsj', '0', '2019-01-15 21:21:26', '2019-01-15 21:21:26');
INSERT INTO `app_config` VALUES ('28', 'wsjTel', '02161516788', '客服电话', 'order', 'wsj', '0', '2019-01-16 20:00:07', '2019-01-16 20:00:07');
INSERT INTO `app_config` VALUES ('29', 'defaultProductCode', 'wsj_20190318_001', '默认产品code', 'order', 'wsj', '0', '2019-01-16 20:03:32', '2019-01-16 20:03:32');
INSERT INTO `app_config` VALUES ('30', 'refundPlatformSize', '5', '退款平台数量', 'order', 'wsj', '0', '2019-01-21 23:26:54', '2019-01-21 23:26:54');
INSERT INTO `app_config` VALUES ('31', 'refundSwitchFlag', 'OFF', '退款开关', 'order', 'wsj', '0', '2019-01-21 23:26:54', '2019-01-21 23:26:54');
INSERT INTO `app_config` VALUES ('32', 'maxRecommendProductSize', '5', '尾量展示数量', 'order', 'wsj', '0', '2019-01-25 18:10:56', '2019-01-25 18:10:56');
INSERT INTO `app_config` VALUES ('33', 'withholdDiffDay', '1', '夜间代扣天数间隔', 'order', 'wsj', '0', '2019-02-16 01:48:36', '2019-02-16 01:48:36');
INSERT INTO `app_config` VALUES ('34', 'withholdSwitchFlag', 'ON', '夜间代扣开关', 'order', 'wsj', '0', '2019-02-16 01:48:36', '2019-02-16 01:48:36');
INSERT INTO `app_config` VALUES ('35', 'bbhTel', '02161516788', '客服电话', 'order', 'bbh', '0', '2019-02-20 11:43:15', '2019-02-20 11:43:15');
INSERT INTO `app_config` VALUES ('36', 'hfpTel', '02161516788', '客服电话', 'order', 'hfp', '0', '2019-02-20 11:43:40', '2019-02-20 11:43:40');
INSERT INTO `app_config` VALUES ('37', 'AssessCategory1false', '38', '极速审核费用', 'order', 'wsj', '0', '2019-03-28 21:50:25', '2019-03-28 21:50:25');
INSERT INTO `app_config` VALUES ('38', 'AssessCategory2false', '0', '普通审核费用', 'order', 'wsj', '0', '2019-03-28 21:50:25', '2019-03-28 21:50:25');
INSERT INTO `app_config` VALUES ('39', 'AssessCategory1true', '198', '极速审核原价', 'order', 'wsj', '0', '2019-03-28 21:50:25', '2019-03-28 21:50:25');
INSERT INTO `app_config` VALUES ('40', 'AssessCategory2true', '98', '普通审核原价', 'order', 'wsj', '0', '2019-03-28 21:50:25', '2019-03-28 21:50:25');
INSERT INTO `app_config` VALUES ('41', 'wsjAssessDetailExpireTime', '7', '万三借评估费过期时间', 'order', 'wsj', '0', '2019-05-08 11:37:08', '2019-05-08 11:37:08');
INSERT INTO `app_config` VALUES ('42', 'maxBorrowAuditTime', '24', '每天订单审核间隔', 'order', 'wsj', '0', '2019-05-21 18:31:04', '2019-05-21 18:31:04');

-- ----------------------------
-- Table structure for banner_config
-- ----------------------------
DROP TABLE IF EXISTS `banner_config`;
CREATE TABLE `banner_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `link_url` varchar(1000) NOT NULL DEFAULT '' COMMENT '图片跳转url',
  `file_url` varchar(200) NOT NULL DEFAULT '' COMMENT '图片url',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT 'banner类型',
  `title` varchar(30) NOT NULL DEFAULT '' COMMENT '标题',
  `activity_type` varchar(50) DEFAULT NULL COMMENT '活动类型',
  `app_name` varchar(30) NOT NULL DEFAULT 'wsj' COMMENT 'app名称',
  `person_number` int(11) DEFAULT '0' COMMENT '活动参与人数',
  `disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of banner_config
-- ----------------------------
INSERT INTO `banner_config` VALUES ('1', '', 'http://qiniubanner.ewangmi.com/new_year_wsj_20181228.jpg', '1', '新年特惠', 'new_year', 'wsj', '0', '0', '2018-12-17 20:57:50', '2018-12-28 16:58:58');
INSERT INTO `banner_config` VALUES ('2', '', 'http://qiniubanner.ewangmi.com/borrow_step_wsj_20181228.jpg', '1', '借款流程', 'borrow_flow', 'wsj', '0', '0', '2018-12-17 20:58:51', '2018-12-28 16:59:20');
INSERT INTO `banner_config` VALUES ('3', '', 'http://qiniubanner.ewangmi.com/trust_borrow20181228.jpg', '1', '诚信放款轻松借', 'trust_borrow', 'wsj', '0', '0', '2018-12-28 16:54:54', '2018-12-28 16:59:30');
INSERT INTO `banner_config` VALUES ('9', 'http://ewangmi.com/recommend/#/share?url=http%3a%2f%2fewangmi.com%2fbbh%2f%23%2flogin', 'http://qiniubanner.ewangmi.com/recommend_prize_20190220.png', '1', '无抵押', 'no_diya', 'bbh', '0', '0', '2019-02-12 20:30:14', '2019-02-12 20:30:14');
INSERT INTO `banner_config` VALUES ('10', 'http://ewangmi.com/recommend/#/share?url=http%3a%2f%2fewangmi.com%2freport%2fhfp%2fhfpLogin%2f%23%2f', 'http://qiniubanner.ewangmi.com/recommend_prize.png', '1', '推荐有奖', 'recommend_prize', 'hfp', '0', '0', '2019-02-19 19:30:11', '2019-02-19 19:30:11');
INSERT INTO `banner_config` VALUES ('11', 'http://ewangmi.com/recommend/#/share?url=https%3a%2f%2fewangmi.com%2fkld%2findex.html%23%2flogin', 'http://qiniubanner.ewangmi.com/recommend_prize_20190220.png', '1', '推荐有奖', 'recommend_prize', 'kld', '0', '0', '2019-02-20 18:06:41', '2019-02-20 18:06:41');
INSERT INTO `banner_config` VALUES ('13', 'http://ewangmi.com/recommend/#/share?url=https%3a%2f%2fewangmi.com%2fylh%2f%23%2fyld', 'http://qiniubanner.ewangmi.com/banner1_ylh_20190319.png', '1', '邀请好友', 'Invite_friends', 'ylh', '0', '0', '2019-03-19 14:09:28', '2019-03-19 14:09:28');
INSERT INTO `banner_config` VALUES ('14', '', 'http://qiniubanner.ewangmi.com/banner2_ylh_20190319.png', '1', '注册有礼', 'Registered_propriety', 'ylh', '0', '1', '2019-03-19 14:09:28', '2019-03-19 14:09:28');
INSERT INTO `banner_config` VALUES ('15', '', 'http://pka8crnyd.bkt.clouddn.com/trust_borrow20181228.jpg', '1', '诚信放款轻松借', 'trust_borrow', 'zjd', '0', '0', '2019-04-18 17:14:28', '2019-04-18 17:14:28');
INSERT INTO `banner_config` VALUES ('16', '', 'http://pka8crnyd.bkt.clouddn.com/trust_borrow20181228.jpg', '1', '诚信放款轻松借', 'trust_borrow', 'mslq', '0', '0', '2019-05-13 18:38:06', '2019-05-13 18:38:06');
INSERT INTO `banner_config` VALUES ('17', '', 'http://pka8crnyd.bkt.clouddn.com/trust_borrow20181228.jpg', '1', '诚信放款轻松借', 'trust_borrow', 'sxsd', '0', '0', '2019-05-15 15:22:32', '2019-05-15 15:22:32');
INSERT INTO `banner_config` VALUES ('18', '', 'http://pka8crnyd.bkt.clouddn.com/trust_borrow20181228.jpg', '1', '诚信放款轻松借', 'trust_borrow', 'dnd', '0', '0', '2019-05-20 16:28:59', '2019-05-20 16:28:59');
INSERT INTO `banner_config` VALUES ('19', '', 'http://pka8crnyd.bkt.clouddn.com/trust_borrow20181228.jpg', '1', '诚信放款轻松借', 'trust_borrow', 'kd', '0', '0', '2019-05-22 10:52:57', '2019-05-22 10:52:57');

-- ----------------------------
-- Table structure for borrow_config
-- ----------------------------
DROP TABLE IF EXISTS `borrow_config`;
CREATE TABLE `borrow_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `max_fee` decimal(10,2) DEFAULT '0.00' COMMENT '用户预期借款总金额',
  `max_periods` int(11) DEFAULT '0' COMMENT '期数',
  `app_name` varchar(30) NOT NULL DEFAULT 'wsj' COMMENT 'app名称 null：是都支持',
  `disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除 0：未删除 1：已删除',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of borrow_config
-- ----------------------------
INSERT INTO `borrow_config` VALUES ('1', '50000.00', '12', 'wsj', '0', '2018-12-17 23:18:10', '2018-12-19 14:08:25');
INSERT INTO `borrow_config` VALUES ('2', '29000.00', '12', 'bbh', '0', '2019-02-12 15:16:57', '2019-02-12 15:16:57');
INSERT INTO `borrow_config` VALUES ('3', '29000.00', '12', 'kld', '0', '2019-02-20 18:06:41', '2019-02-20 18:06:41');
INSERT INTO `borrow_config` VALUES ('5', '29000.00', '12', 'ylh', '0', '2019-03-19 14:10:25', '2019-03-19 14:10:25');
INSERT INTO `borrow_config` VALUES ('6', '29000.00', '12', 'mslq', '0', '2019-05-13 18:38:36', '2019-05-13 18:38:36');
INSERT INTO `borrow_config` VALUES ('7', '29000.00', '12', 'sxsd', '0', '2019-05-15 15:22:55', '2019-05-15 15:22:55');
INSERT INTO `borrow_config` VALUES ('8', '29000.00', '12', 'dnd', '0', '2019-05-20 16:29:18', '2019-05-20 16:29:18');
INSERT INTO `borrow_config` VALUES ('9', '29000.00', '12', 'kd', '0', '2019-05-22 10:53:11', '2019-05-22 10:53:11');

-- ----------------------------
-- Table structure for download_config
-- ----------------------------
DROP TABLE IF EXISTS `download_config`;
CREATE TABLE `download_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `download_url` varchar(255) NOT NULL COMMENT '下载地址',
  `version` varchar(10) NOT NULL COMMENT '版本',
  `os` varchar(10) DEFAULT NULL COMMENT '操作系统',
  `app_size` varchar(20) DEFAULT NULL COMMENT 'app大小',
  `app_name` varchar(255) DEFAULT NULL COMMENT 'app名称',
  `disable` varchar(255) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_os` (`os`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of download_config
-- ----------------------------
INSERT INTO `download_config` VALUES ('1', 'https://cdn.bvubu.top/appp.php/7382', '1.0', 'Iphone', null, 'wsj', '1', '2018-12-18 20:20:50', '2018-12-18 20:21:50');
INSERT INTO `download_config` VALUES ('2', 'https://apk.ipahc.com/0cc88bcf775ca17d792c9e1e3b9984e6.apk', '1.0', 'Android', null, 'wsj', '1', '2018-12-18 20:21:33', '2018-12-18 20:21:33');
INSERT INTO `download_config` VALUES ('3', 'https://app.ipahc.com/s/XslpA95', '1.1.1', 'Iphone', '30M', 'wsj', '1', '2019-01-13 16:16:42', '2019-01-13 16:16:42');
INSERT INTO `download_config` VALUES ('4', 'https://njysd-app-1256093684.cos.ap-guangzhou.myqcloud.com/wansanjie11.apk', '1.1.1', 'Android', null, 'wsj', '1', '2019-01-13 16:16:42', '2019-01-13 16:16:42');
INSERT INTO `download_config` VALUES ('5', 'https://njysd-app-1256093684.cos.ap-guangzhou.myqcloud.com/wansanjie11v2.apk', '1.1.1', 'Android', '30M', 'wsj', '1', '2019-01-18 16:55:23', '2019-01-18 16:55:23');
INSERT INTO `download_config` VALUES ('6', 'https://njysd-app-1256093684.cos.ap-guangzhou.myqcloud.com/wsj12.apk', '1.1.2', 'Android', '31M', 'wsj', '1', '2019-01-22 11:41:07', '2019-01-22 11:41:07');
INSERT INTO `download_config` VALUES ('7', 'https://njysd-app-1256093684.file.myqcloud.com/wsj/ios/wsj15.ipa', '1.0.15', 'Iphone', '27M', 'wsj', '0', '2019-01-22 11:42:54', '2019-01-22 11:42:54');
INSERT INTO `download_config` VALUES ('8', 'https://njysd-app-1256093684.file.myqcloud.com/wsj/wsj_V19.apk', '1.1.19', 'Android', '14M', 'wsj', '0', '2019-01-29 21:43:27', '2019-01-29 21:43:27');
INSERT INTO `download_config` VALUES ('9', 'https://njysd-app-1256093684.file.myqcloud.com/hfp/hfpV17.apk', '1.1.14', 'Android', '22M', 'hfp', '0', '2019-01-31 05:34:09', '2019-01-31 05:34:09');
INSERT INTO `download_config` VALUES ('10', 'https://ewangmi.com/hfp/#/download', '1.1.4', 'Iphone', '25M', 'hfp', '0', '2019-01-31 05:34:22', '2019-01-31 05:34:22');
INSERT INTO `download_config` VALUES ('11', 'http://132.232.115.29/report/hfp/hfpDownload/index.html#/', '1.0.0', 'Iphone', '10M', 'hfp', '1', '2019-02-01 15:32:24', '2019-02-01 15:32:24');
INSERT INTO `download_config` VALUES ('12', 'https://njysd-app-1256093684.file.myqcloud.com/txgj/txgj_V21.apk', '2.0.20', 'Android', '7M', 'txgj', '0', '2019-02-02 16:56:53', '2019-02-02 16:56:53');
INSERT INTO `download_config` VALUES ('13', 'https://njysd-app-1256093684.file.myqcloud.com/bbh/bbh_V1.apk', '1.1.0', 'Android', '25M', 'bbh', '1', '2019-02-13 20:28:55', '2019-02-13 20:28:55');
INSERT INTO `download_config` VALUES ('14', 'https://zbrnq.cn/bbhyl/#/download', '1.2.1', 'Iphone', '25M', 'bbh', '0', '2019-02-13 22:41:11', '2019-02-13 22:41:11');
INSERT INTO `download_config` VALUES ('15', 'https://njysd-app-1256093684.file.myqcloud.com/bbh/bbh_V35.apk', '1.3.5', 'Android', '8M', 'bbh', '0', '2019-02-16 00:44:23', '2019-02-16 00:44:23');
INSERT INTO `download_config` VALUES ('16', 'https://njysd-app-1256093684.file.myqcloud.com/kld/kld_V36.apk', '1.36', 'Android', '23M', 'kld', '0', '2019-02-21 18:59:21', '2019-02-21 18:59:21');
INSERT INTO `download_config` VALUES ('17', 'https://app.ipahc.com/#/pUyL295', '1.2.1', 'Iphone', '22M', 'kld', '0', '2019-02-21 21:18:04', '2019-02-21 21:18:04');
INSERT INTO `download_config` VALUES ('18', 'https://zbrnq.cn/ylh/#/download', '1.1.1', 'Iphone', '25M', 'ylh', '0', '2019-03-19 17:40:49', '2019-03-19 17:40:49');
INSERT INTO `download_config` VALUES ('19', 'https://njysd-app-1256093684.file.myqcloud.com/ylh/ylh_V35.apk', '1.3.5', 'Android', '25M', 'ylh', '0', '2019-03-19 17:40:49', '2019-03-19 17:40:49');
INSERT INTO `download_config` VALUES ('20', 'https://njysd-app-1256093684.file.myqcloud.com/zjd/zjd_V6.apk', '1.0.6', 'Android', '7M', 'zjd', '0', '2019-04-23 21:19:27', '2019-04-23 21:19:27');
INSERT INTO `download_config` VALUES ('21', 'https://evmvh.cn/zjdyl/#/download', '1.0.0', 'Iphone', '3.4M', 'zjd', '0', '2019-04-24 11:53:35', '2019-04-24 11:53:35');
INSERT INTO `download_config` VALUES ('22', 'https://btgpi.cn/txgjyl/#/iosTxgjDownload', '1.0.0', 'Iphone', '10M', 'txgj', '0', '2019-04-25 19:14:12', '2019-04-25 19:14:12');
INSERT INTO `download_config` VALUES ('23', 'https://njysd-app-1256093684.file.myqcloud.com/rrsd/rrsd_V6.apk', '1.1.6', 'Android', '8M', 'rrsd', '0', '2019-05-06 21:28:33', '2019-05-06 21:28:33');
INSERT INTO `download_config` VALUES ('24', 'https://b5dh.cn/rrsdyl/#/iosRrsdDownload', '1.0.0', 'Iphone', '8M', 'rrsd', '0', '2019-05-06 23:53:19', '2019-05-06 23:53:19');
INSERT INTO `download_config` VALUES ('25', 'https://btgpi.cn/wysdyl/#/iosWysdDownload', '1.0.0', 'Iphone', '8M', 'wysd', '0', '2019-05-06 23:53:46', '2019-05-06 23:53:46');
INSERT INTO `download_config` VALUES ('26', 'https://njysd-app-1256093684.file.myqcloud.com/wysd/wysd_V6.apk', '1.1.6', 'Android', '8M', 'wysd', '0', '2019-05-06 23:57:27', '2019-05-06 23:57:27');
INSERT INTO `download_config` VALUES ('27', 'https://tixianguanjia.com/txgjyl/#/iosTxgjDownload', '2.0.19', 'Iphone', '7M', 'txgj', '0', '2019-05-09 18:08:48', '2019-05-09 18:08:48');
INSERT INTO `download_config` VALUES ('28', '\r\nhttps://njysd-app-1256093684.file.myqcloud.com/mslq/mslq_V6.apk ', '1.0.6', 'Android', '8M', 'mslq', '0', '2019-05-14 11:52:43', '2019-05-14 11:52:43');
INSERT INTO `download_config` VALUES ('30', 'https://u5ih.cn/mslqyl/#/download', '1.0.1', 'Iphone', '5M', 'mslq', '0', '2019-05-15 18:27:24', '2019-05-15 18:27:24');
INSERT INTO `download_config` VALUES ('31', 'https://njysd-app-1256093684.file.myqcloud.com/sxsd/sxsd_V3.apk', '1.0.3', 'Android', '10M', 'sxsd', '0', '2019-05-21 14:21:18', '2019-05-21 14:21:18');
INSERT INTO `download_config` VALUES ('32', 'https://njysd-app-1256093684.file.myqcloud.com/zjd/zjd_V17_simple.apk', '1.1.7', 'Android', '8M', 'zjd_simple', '0', '2019-05-22 19:48:48', '2019-05-22 19:48:48');
INSERT INTO `download_config` VALUES ('33', 'https://njysd-app-1256093684.file.myqcloud.com/rrsd/rrsd_V6_simple.apk', '1.0.6', 'Android', '8M', 'rrsd_simple', '0', '2019-05-22 21:58:08', '2019-05-22 21:58:08');
INSERT INTO `download_config` VALUES ('34', 'https://njysd-app-1256093684.file.myqcloud.com/wysd/wysd_V6_simple.apk', '1.0.6', 'Android', '8M', 'wysd_simple', '0', '2019-05-22 22:27:06', '2019-05-22 22:27:06');
INSERT INTO `download_config` VALUES ('35', 'https://njysd-app-1256093684.file.myqcloud.com/mslq/mslq_V6_simple.apk', '1.0.6', 'Android', '8M', 'mslq_simple', '0', '2019-05-22 22:39:14', '2019-05-22 22:39:14');

-- ----------------------------
-- Table structure for fadada_config
-- ----------------------------
DROP TABLE IF EXISTS `fadada_config`;
CREATE TABLE `fadada_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(500) DEFAULT NULL COMMENT '请求地址',
  `app_id` varchar(36) DEFAULT NULL COMMENT 'appId',
  `app_secret` varchar(50) DEFAULT NULL COMMENT '秘钥',
  `app_name` varchar(36) DEFAULT NULL COMMENT 'app名称',
  `merchant_no` varchar(36) DEFAULT NULL COMMENT '商户号',
  `version` varchar(30) DEFAULT NULL COMMENT '版本',
  `notify_url` varchar(200) DEFAULT NULL COMMENT '就会掉地址',
  `belong_company` varchar(36) NOT NULL DEFAULT 'wangmi' COMMENT '公司名称',
  `company_name` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `disable` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of fadada_config
-- ----------------------------
INSERT INTO `fadada_config` VALUES ('1', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'wsj', '29DA52277F161969AE71AC8C14741A0D', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'jinchao', '镇江锦超网络科技有限公司', '0', '2018-12-25 18:11:30', '2018-12-28 10:55:52');
INSERT INTO `fadada_config` VALUES ('2', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'hfp', 'DA5AFBFA9DD6BB76E94157C3C068F89A', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'jinchao', '镇江锦超网络科技有限公司', '0', '2019-01-29 13:56:12', '2019-01-29 13:56:12');
INSERT INTO `fadada_config` VALUES ('3', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'bbh', 'DA5AFBFA9DD6BB76E94157C3C068F89A', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'jinchao', '镇江锦超网络科技有限公司', '0', '2019-02-12 15:17:05', '2019-02-12 15:17:05');
INSERT INTO `fadada_config` VALUES ('4', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'kld', '29DA52277F161969C36DC64E7489EE09', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'gude', '镇江顾得网络科技有限公司', '0', '2019-02-20 18:06:41', '2019-02-20 18:06:41');
INSERT INTO `fadada_config` VALUES ('6', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'ylh', 'DA5AFBFA9DD6BB76E94157C3C068F89A', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'jinchao', '镇江锦超网络科技有限公司', '0', '2019-03-19 14:11:17', '2019-03-19 14:11:17');
INSERT INTO `fadada_config` VALUES ('7', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'zjd', '29DA52277F161969C36DC64E7489EE09', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'gude', '镇江顾得网络科技有限公司', '0', '2019-04-18 18:56:14', '2019-04-18 18:56:14');
INSERT INTO `fadada_config` VALUES ('8', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'wysd', '29DA52277F161969AE71AC8C14741A0D', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'wanzhuoda', '安庆万卓达信息科技有限公司', '0', '2019-04-29 10:56:44', '2019-04-29 10:56:44');
INSERT INTO `fadada_config` VALUES ('9', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'rrsd', '29DA52277F1619692352716410817628', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'shengtong', '安庆盛通信息科技有限公司', '0', '2019-04-29 10:56:45', '2019-04-29 10:56:45');
INSERT INTO `fadada_config` VALUES ('10', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'test', '29DA52277F161969C36DC64E7489EE09', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'gude', '镇江顾得网络科技有限公司', '0', '2019-04-29 10:56:45', '2019-04-29 10:56:45');
INSERT INTO `fadada_config` VALUES ('12', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'test2', '\r\nDA5AFBFA9DD6BB76E94157C3C068F89A', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'jinchao', '镇江锦超网络科技有限公司', '0', '2019-05-09 20:41:48', '2019-05-09 20:41:48');
INSERT INTO `fadada_config` VALUES ('13', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'mslq', 'F49D6FCE30E4C008A800659AB41D86B5', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'xinghe', '星赫（梁山）信息科技有限公司', '0', '2019-05-13 09:10:17', '2019-05-13 09:10:17');
INSERT INTO `fadada_config` VALUES ('14', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'sxsd', '4780D0DD80FAA4DDA157D27EF87B0913', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'xianyu', '梁山仙域信息技术有限公司', '0', '2019-05-15 15:22:09', '2019-05-15 15:22:09');
INSERT INTO `fadada_config` VALUES ('15', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'dnd', '0A942385C47BD12B27860381FB1865A8 ', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'zening', '湖南泽宁信息科技有限公司', '0', '2019-05-20 16:28:04', '2019-05-20 16:28:04');
INSERT INTO `fadada_config` VALUES ('16', 'https://ext2api.fadada.com/api2/', '001094', 'zlR9SnOwn57c8hCpd6uYP4hN', 'kd', 'DC2CC1F6AD8089BC1216D392554A6366', '2.0', 'http://132.232.115.29/api/config/agreement/fadada/notify', 'zhanwang', '高安市展望信息科技有限公司', '0', '2019-05-22 10:52:03', '2019-05-22 10:52:03');

-- ----------------------------
-- Table structure for fadada_register_info
-- ----------------------------
DROP TABLE IF EXISTS `fadada_register_info`;
CREATE TABLE `fadada_register_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `simple_name` varchar(50) DEFAULT NULL COMMENT '公司简称（文件按简称命名）',
  `company` varchar(50) DEFAULT NULL COMMENT '公司',
  `name` varchar(20) DEFAULT NULL COMMENT '法人名称',
  `cred_no` varchar(20) DEFAULT NULL COMMENT '法人身份证',
  `social_number` varchar(50) DEFAULT NULL COMMENT '社会信用代码',
  `environment` varchar(3) DEFAULT NULL COMMENT '生效环境 : 0-法大大测试环境  1-法大大正式环境',
  `disable` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删除 1删除)',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_company_name` (`simple_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='法大大签章注册配置表';

-- ----------------------------
-- Records of fadada_register_info
-- ----------------------------
INSERT INTO `fadada_register_info` VALUES ('1', 'xinghe', '星赫（梁山）信息科技有限公司', '王光远', '370832198707253613', '91370832MA3PGJR06Q', '1', '0', '2019-05-10 22:00:55', '2019-05-10 22:00:55');
INSERT INTO `fadada_register_info` VALUES ('2', 'lsxc', '梁山仙域信息技术有限公司', '庞玺鉴', '370832198712223611', '91370832MA3PGHCM5A', '1', '0', '2019-05-15 11:50:19', '2019-05-15 11:50:19');
INSERT INTO `fadada_register_info` VALUES ('3', 'zening', '湖南泽宁信息科技有限公司', '罗树青', '432831197906192216', '91431002MA4QEL8W4H', '1', '0', '2019-05-16 17:53:04', '2019-05-16 17:53:04');
INSERT INTO `fadada_register_info` VALUES ('4', 'zhanwang', '高安市展望信息科技有限公司', '付火秀', '362222195910113326', '91360983MA38J49K5Y', '1', '0', '2019-05-21 20:51:15', '2019-05-21 20:51:15');

-- ----------------------------
-- Table structure for load_js_config
-- ----------------------------
DROP TABLE IF EXISTS `load_js_config`;
CREATE TABLE `load_js_config` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `transfer_app` varchar(20) NOT NULL DEFAULT '' COMMENT '转化成的app',
  `app_type` varchar(20) NOT NULL DEFAULT '' COMMENT 'app所属类型',
  `js_url` varchar(200) NOT NULL DEFAULT '' COMMENT 'jsUrl',
  `app_name` varchar(20) NOT NULL DEFAULT '' COMMENT '当前壳的app',
  `disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除 0：未删除 1：已删除',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='加载js配置';

-- ----------------------------
-- Records of load_js_config
-- ----------------------------
INSERT INTO `load_js_config` VALUES ('1', 'zjd', 'zjd_simple', 'https://evmvh.cn/zjdsimpleapp/dist/home.js', 'zjd', '0', '2019-05-22 21:09:37', '2019-05-22 21:09:37');
INSERT INTO `load_js_config` VALUES ('2', 'rrsd', 'rrsd_simple', 'https://b5dh.cn/rrsdsimpleapp/dist/home.js', 'rrsd', '0', '2019-05-22 21:44:53', '2019-05-22 21:44:53');
INSERT INTO `load_js_config` VALUES ('3', 'wysd', 'wysd_simple', 'https://btgpi.cn/wysdsimpleapp/dist/home.js', 'wysd', '0', '2019-05-22 22:10:47', '2019-05-22 22:10:47');
INSERT INTO `load_js_config` VALUES ('4', 'mslq', 'mslq_simple', 'https://u5ih.cn/mslqsimpleapp/dist/home.js', 'mslq', '0', '2019-05-22 22:26:17', '2019-05-22 22:26:17');

-- ----------------------------
-- Table structure for sys_app_name
-- ----------------------------
DROP TABLE IF EXISTS `sys_app_name`;
CREATE TABLE `sys_app_name` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_code` varchar(36) DEFAULT NULL,
  `app_name` varchar(36) DEFAULT NULL,
  `belong_company` varchar(50) DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `disable` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(36) DEFAULT 'sys',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_app_code` (`app_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_app_name
-- ----------------------------
INSERT INTO `sys_app_name` VALUES ('9', 'wsj', '万三借', 'jinchao', null, '2018-12-13 19:06:00', '0', '2018-12-21 16:53:50', 'sys');
INSERT INTO `sys_app_name` VALUES ('13', 'bbh', '宝宝花', 'jinchao', null, '2019-02-12 15:15:46', '1', '2019-02-12 15:15:46', 'sys');
INSERT INTO `sys_app_name` VALUES ('14', 'txgj', '提现管家', 'wanzhuoda', null, '2019-02-13 13:34:16', '0', '2019-02-13 13:34:16', 'sys');
INSERT INTO `sys_app_name` VALUES ('16', 'hfp', '豪放派', 'jinchao', null, '2019-02-15 22:50:34', '1', '2019-02-15 22:50:34', 'sys');
INSERT INTO `sys_app_name` VALUES ('17', 'kld', '快乐贷', 'gude', null, '2019-02-20 18:06:41', '1', '2019-02-20 18:06:41', 'sys');
INSERT INTO `sys_app_name` VALUES ('19', 'ylh', '一路花', 'jinchao', null, '2019-03-19 14:10:41', '1', '2019-03-19 14:10:41', 'sys');
INSERT INTO `sys_app_name` VALUES ('21', 'zjd', '指尖贷', 'gude', null, '2019-04-18 16:26:45', '0', '2019-04-18 16:26:45', 'sys');
INSERT INTO `sys_app_name` VALUES ('22', 'rrsd', '人人速贷', 'shengtong', null, '2019-04-29 09:54:34', '0', '2019-04-29 09:54:34', 'sys');
INSERT INTO `sys_app_name` VALUES ('23', 'wysd', '51速贷', 'wanzhuoda', null, '2019-04-29 09:55:02', '0', '2019-04-29 09:55:02', 'sys');
INSERT INTO `sys_app_name` VALUES ('24', 'mslq', '马上来钱', 'xinghe', '星赫', '2019-05-13 18:37:37', '0', '2019-06-10 10:33:56', 'sys');
INSERT INTO `sys_app_name` VALUES ('25', 'sxsd', '随心速贷', 'xianyu', '仙域', '2019-05-15 15:19:12', '0', '2019-06-10 10:38:18', 'sys');
INSERT INTO `sys_app_name` VALUES ('26', 'dnd', '大牛贷', 'zening', '泽宁', '2019-05-20 16:26:43', '0', '2019-05-20 16:26:43', 'sys');
INSERT INTO `sys_app_name` VALUES ('27', 'kd', '咖贷', 'zhanwang', '展望', '2019-05-22 10:51:19', '0', '2019-05-22 10:51:19', 'sys');
INSERT INTO `sys_app_name` VALUES ('28', 'hdqb', '好贷钱包', 'yulian', '宇链', '2019-07-23 15:53:20', '0', '2019-07-23 15:53:20', 'sys');

-- ----------------------------
-- Table structure for sys_sms_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_config`;
CREATE TABLE `sys_sms_config` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `channel_flag` varchar(1) NOT NULL COMMENT '1为天畅 2 梦网',
  `sms_plat_url_batch` varchar(255) NOT NULL COMMENT '短信平台批量url',
  `sms_plat_url_single` varchar(255) NOT NULL COMMENT '短信平台url',
  `sms_plat_account` varchar(64) NOT NULL COMMENT '短信平台账号',
  `sms_plat_pwd` varchar(200) NOT NULL COMMENT '短信平台密码',
  `status` int(1) NOT NULL COMMENT '1为启用  0位禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_sms_config
-- ----------------------------
INSERT INTO `sys_sms_config` VALUES ('6', '4', 'https://sms.253.com/msg/send/json', 'https://sms.253.com/msg/send/json', 'N9684770', 'TnM9l4u1j', '2');
INSERT INTO `sys_sms_config` VALUES ('7', '6', 'http://api.1cloudsp.com/api/v2/send', 'http://api.1cloudsp.com/api/v2/single_send', '6ljrVA4wpXgXfwkY', 'bvJYHKPUgBbV2lYsHV4HQtIUIXB7q2qy', '2');
INSERT INTO `sys_sms_config` VALUES ('8', '8', 'http://smssh1.253.com/msg/send/json', 'http://smssh1.253.com/msg/send/json', 'N9684770', 'TnM9l4u1j', '1');
INSERT INTO `sys_sms_config` VALUES ('10', '9', 'http://smssh1.253.com/msg/send/json', 'http://smssh1.253.com/msg/send/json', 'N9684770', 'TnM9l4u1j', '1');

-- ----------------------------
-- Table structure for sys_sms_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_sms_param`;
CREATE TABLE `sys_sms_param` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `channel_flag` varchar(1) NOT NULL COMMENT '渠道号',
  `sms_type` int(4) NOT NULL COMMENT 'sms 类型(1注册sms验证码)',
  `template_id` varchar(25) DEFAULT NULL,
  `sms_template` varchar(300) NOT NULL COMMENT '短信模板',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1启用 0停用',
  `content` varchar(20) DEFAULT NULL COMMENT '描述',
  `count` int(4) NOT NULL DEFAULT '8' COMMENT '最大次数',
  `code_flag` tinyint(1) DEFAULT NULL COMMENT '是否需要发验证码。1 需要发验证码 0不需要',
  `during` int(11) DEFAULT NULL COMMENT '当前秒内 不能超过count次。null表示 当天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_sms_param
-- ----------------------------
INSERT INTO `sys_sms_param` VALUES ('1', '4', '2', null, '【${appName}】您接收到的登陆验证码为：${code}', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('2', '4', '3', null, '【${appName}】您接收到的注册验证码为：${code}', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('28', '6', '4', '33929', '${code}', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('29', '6', '5', '', '新平台！身份证秒下8000额度，最高50000，无视黑户，最快30秒下款，点击 ${url} 退订回T', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('30', '4', '6', null, '【${appName}】您的服务费已支付成功，已为您生成《E盾征信评估报告》和大数据精准推荐次数,如有疑问请咨询客服电话:${tel}。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('31', '4', '8', null, '【${appName}】订购服务已支付成功，请及时登录平台查收，您的注册申请时间为${time}，服务审核时间1-5工作日，通过后次日0点生效，如有疑问请咨询客服电话：${tel}。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('32', '4', '9', null, '【${appName}】您已注册${appName}APP，请继续操作完成认证，认证只需3步，2分钟完成。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('33', '4', '10', null, '【${appName}】尊敬的${userName}用户，您的认证已审核通过，请登录app继续完成操作。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('35', '4', '11', null, '【${appName}】您的订单：${borrowId}，人民币：${amount}元已于${remitTime}发放，放款银行卡后4位为：${bankAccount}，还款日：${repayTime}。祝您生活愉快。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('37', '4', '12', null, '【${appName}】您订单号：${borrowId}的贷款，在${time}已还款人民币${amount}元，期待再次为您服务。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('38', '4', '13', null, '【${appName}】您的还款日为${time}，订单号：${borrowId}的贷款，本期还款人民币${amount}元，请您提前做好资金安排，以免产生逾期。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('39', '4', '14', null, '【${appName}】您订单号：${borrowId}应还款${amount}元。还款日为${time}。请确保及时还款余额充足，如已还清请忽略。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('40', '4', '15', null, '【${appName}】恭喜，您申请的订单：${borrowId}，已审核通过，额度：${amount}元，请前往APP完成确认下款。祝您生活愉快。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('41', '4', '16', null, '【${appName}】您订单号：${borrowId}，于${time}已结清，我们期待再次为您服务。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('42', '6', '17', null, '【${appName}】尊敬的用户，会话已结束，您的5000申请额度已通过电话审核,请关注${appName}查收。', '1', null, '20', null, null);
INSERT INTO `sys_sms_param` VALUES ('43', '6', '16', '43910', '${borrowId}', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('44', '6', '16', '43910', '${borrowId}', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('45', '6', '15', '43909', '${borrowId}##${amount}', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('46', '6', '14', '43908', '${borrowId}##${amount}##${time}', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('47', '6', '13', '43905', '${time}##${borrowId}##${amount}', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('48', '6', '11', '43903', '${borrowId}##${amount}##${remitTime}##${bankAccount}##${repayTime}', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('49', '8', '2', null, '【${appName}】您接收到的登陆验证码为：${code}。', '1', '1', '10', null, null);
INSERT INTO `sys_sms_param` VALUES ('50', '8', '6', null, '【${appName}】您的服务费已支付成功，已为您生成《E盾征信评估报告》和大数据精准推荐次数,如有疑问请咨询客服电话:02161516788。', '1', null, '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('51', '8', '7', null, '【${appName}】验证码：${code}，您正在通过万三借进行快捷支付账户绑定验证操作，为保障您的交易安全，确保持卡人信息一致，本平台已将该卡号与您的手机号${subPhone}进行绑定。如验证通过，可通过已绑账户直接进行支付。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('52', '8', '8', null, '【${appName}】订购服务已支付成功，请及时登录平台查收，您的注册申请时间为${time}，服务审核时间1-5工作日，通过后次日9点生效，如有疑问请咨询客服电话：02161516788。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('53', '8', '9', null, '【${appName}】您已注册${appName}APP，请继续操作完成认证，认证只需3步，2分钟完成。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('54', '8', '10', null, '【${appName}】尊敬的${userName}用户，您的认证已审核通过，请登录app继续完成操作。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('55', '8', '11', null, '【${appName}】您的订单：${borrowId}，人民币：${amount}元已于${remitTime}发放，放款银行卡后4位为：${bankAccount}，还款日：${repayTime}。祝您生活愉快。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('56', '8', '12', null, '【${appName}】您订单号：${borrowId}的贷款，在${time}已还款人民币${amount}元，期待再次为您服务。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('57', '8', '13', null, '【${appName}】您的还款日为${time}，订单号：${borrowId}的贷款，本期还款人民币${amount}元，请您提前做好资金安排，以免产生逾期。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('58', '8', '14', null, '【${appName}】您订单号：${borrowId}应还款${amount}元。还款日为${time}。请确保及时还款余额充足，如已还清请忽略。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('59', '8', '15', null, '【${appName}】恭喜，您申请的订单：${borrowId}，已审核通过，额度：${amount}元，请前往APP完成确认下款。祝您生活愉快。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('60', '8', '16', null, '【${appName}】您订单号：${borrowId}，于${time}已结清，我们期待再次为您服务。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('61', '8', '3', null, '【${appName}】您接收到的注册验证码为：${code}。', '1', '1', '8', null, null);
INSERT INTO `sys_sms_param` VALUES ('62', '9', '2', null, '【${appName}】您接收到的登陆验证码为：${code}。', '1', null, '8', null, null);

-- ----------------------------
-- Table structure for tip_msg
-- ----------------------------
DROP TABLE IF EXISTS `tip_msg`;
CREATE TABLE `tip_msg` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `show_type` int(4) NOT NULL DEFAULT '0' COMMENT '显示类别 0：通用显示 1：首页显示',
  `content` varchar(500) DEFAULT NULL COMMENT '显示内容',
  `content_type` int(4) DEFAULT NULL COMMENT '内容类别 1:提示成功的短信 2:客服电话',
  `app_name` varchar(20) DEFAULT '' COMMENT 'app名',
  `disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已删除 0 ：正常 1 ：已删除 ',
  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间 ',
  `utime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间 ',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_type` (`show_type`) USING BTREE,
  KEY `index_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=211 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='提示信息配置表';

-- ----------------------------
-- Records of tip_msg
-- ----------------------------
INSERT INTO `tip_msg` VALUES ('12', '1', '1314****424借款成功，额度1000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('13', '1', '1394****779借款成功，额度3000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('14', '1', '1594****345借款成功，额度3000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('15', '1', '1378****345借款成功，额度5000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('16', '1', '1398****887借款成功，额度3000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('17', '1', '1595****897借款成功，额度5000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('18', '1', '1594****986借款成功，额度1000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('19', '1', '1316****568借款成功，额度3000元', '1', 'wsj', '0', '2019-01-11 20:36:46', '2019-01-11 20:36:46');
INSERT INTO `tip_msg` VALUES ('20', '1', '1714****424借款成功，额度20000元', '1', 'wsj', '0', '2019-01-11 20:36:47', '2019-01-11 20:36:47');
INSERT INTO `tip_msg` VALUES ('21', '1', '02161516788', '2', 'wsj', '0', '2019-01-11 20:36:47', '2019-01-23 17:04:15');
INSERT INTO `tip_msg` VALUES ('22', '1', '2704736048', '3', 'wsj', '0', '2019-01-13 13:24:07', '2019-03-08 11:59:48');
INSERT INTO `tip_msg` VALUES ('23', '1', '2637653031', '3', 'wsj', '1', '2019-01-13 18:56:25', '2019-03-13 11:39:13');
INSERT INTO `tip_msg` VALUES ('24', '1', '3158436271', '3', 'wsj', '1', '2019-01-13 18:56:25', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('25', '1', '2016984891', '3', 'wsj', '1', '2019-01-13 18:56:25', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('26', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'wsj', '1', '2019-01-15 20:13:37', '2019-03-29 12:49:35');
INSERT INTO `tip_msg` VALUES ('29', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'wsj', '0', '2019-01-23 18:48:15', '2019-01-23 19:06:02');
INSERT INTO `tip_msg` VALUES ('30', '1', '1314****424借款成功，额度1000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('31', '1', '1394****779借款成功，额度3000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('32', '1', '1594****345借款成功，额度3000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('33', '1', '1378****345借款成功，额度5000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('34', '1', '1398****887借款成功，额度3000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('35', '1', '1595****897借款成功，额度5000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('36', '1', '1594****986借款成功，额度1000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('37', '1', '1316****568借款成功，额度3000元', '1', 'hfp', '0', '2019-01-29 10:19:57', '2019-01-29 10:19:57');
INSERT INTO `tip_msg` VALUES ('38', '1', '2704736048', '3', 'hfp', '0', '2019-01-29 10:23:28', '2019-03-08 11:59:48');
INSERT INTO `tip_msg` VALUES ('39', '1', '2637653031', '3', 'hfp', '1', '2019-01-29 10:23:28', '2019-03-13 11:39:13');
INSERT INTO `tip_msg` VALUES ('40', '1', '02161516788', '2', 'hfp', '0', '2019-01-29 10:29:09', '2019-02-14 14:22:23');
INSERT INTO `tip_msg` VALUES ('41', '1', '3158436271', '3', 'hfp', '1', '2019-01-29 10:29:09', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('42', '1', '2016984891', '3', 'hfp', '1', '2019-01-29 10:29:09', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('45', '1', '我们提供的平台能够90%借到款，因此需要你提供合作平台\n借款失败的图片，以作为审核的标准', '4', 'hfp', '0', '2019-01-29 10:29:09', '2019-01-29 10:29:09');
INSERT INTO `tip_msg` VALUES ('46', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'hfp', '0', '2019-01-29 10:29:09', '2019-03-29 12:49:23');
INSERT INTO `tip_msg` VALUES ('47', '1', '1314****424借款成功，额度1000元', '1', 'bbh', '0', '2019-02-12 15:17:19', '2019-02-12 15:18:48');
INSERT INTO `tip_msg` VALUES ('48', '1', '1394****779借款成功，额度3000元', '1', 'bbh', '0', '2019-02-12 15:17:21', '2019-02-12 15:18:49');
INSERT INTO `tip_msg` VALUES ('49', '1', '1594****345借款成功，额度3000元', '1', 'bbh', '0', '2019-02-12 15:17:24', '2019-02-12 15:18:51');
INSERT INTO `tip_msg` VALUES ('50', '1', '1378****345借款成功，额度5000元', '1', 'bbh', '0', '2019-02-12 15:17:27', '2019-02-12 15:18:52');
INSERT INTO `tip_msg` VALUES ('51', '1', '1398****887借款成功，额度3000元', '1', 'bbh', '0', '2019-02-12 15:17:30', '2019-02-12 15:18:52');
INSERT INTO `tip_msg` VALUES ('52', '1', '1595****897借款成功，额度5000元', '1', 'bbh', '0', '2019-02-12 15:17:32', '2019-02-12 15:18:53');
INSERT INTO `tip_msg` VALUES ('53', '1', '1594****986借款成功，额度1000元', '1', 'bbh', '0', '2019-02-12 15:17:35', '2019-02-12 15:18:54');
INSERT INTO `tip_msg` VALUES ('54', '1', '1316****568借款成功，额度3000元', '1', 'bbh', '0', '2019-02-12 15:17:38', '2019-02-12 15:18:55');
INSERT INTO `tip_msg` VALUES ('55', '1', '1714****424借款成功，额度20000元', '1', 'bbh', '0', '2019-02-12 15:17:42', '2019-02-12 15:18:56');
INSERT INTO `tip_msg` VALUES ('56', '1', '02161516788', '2', 'bbh', '0', '2019-02-12 15:20:36', '2019-02-12 15:20:36');
INSERT INTO `tip_msg` VALUES ('57', '1', '2704736048', '3', 'bbh', '0', '2019-02-12 15:20:36', '2019-03-08 11:59:48');
INSERT INTO `tip_msg` VALUES ('58', '1', '2637653031', '3', 'bbh', '1', '2019-02-12 15:20:36', '2019-03-13 11:39:13');
INSERT INTO `tip_msg` VALUES ('59', '1', '3158436271', '3', 'bbh', '1', '2019-02-12 15:20:36', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('60', '1', '2016984891', '3', 'bbh', '1', '2019-02-12 15:20:36', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('61', '1', 'https://webchat.7moor.com/wapchat.html?accessId=59760c50-0b3d-11e9-8992-39fce0b6b622&fromUrl=&urlTitle=', '5', 'bbh', '1', '2019-02-12 15:20:45', '2019-02-12 15:20:45');
INSERT INTO `tip_msg` VALUES ('64', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'bbh', '0', '2019-02-12 15:21:46', '2019-03-28 19:09:16');
INSERT INTO `tip_msg` VALUES ('65', '1', '1714****424借款成功，额度1000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('66', '1', '1594****779借款成功，额度3000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('67', '1', '1594****365借款成功，额度3000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('68', '1', '1378****365借款成功，额度5000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('69', '1', '1398****827借款成功，额度3000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('70', '1', '1505****817借款成功，额度5000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('71', '1', '1594****936借款成功，额度1000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('72', '1', '1317****568借款成功，额度3000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('73', '1', '1715****324借款成功，额度20000元', '1', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('74', '1', '02161516788', '2', 'kld', '0', '2019-02-20 18:07:02', '2019-02-20 18:07:02');
INSERT INTO `tip_msg` VALUES ('75', '1', '2704736048', '3', 'kld', '0', '2019-02-20 18:07:03', '2019-03-08 11:59:48');
INSERT INTO `tip_msg` VALUES ('76', '1', '2637653031', '3', 'kld', '1', '2019-02-20 18:07:03', '2019-03-13 11:39:13');
INSERT INTO `tip_msg` VALUES ('77', '1', '3158436271', '3', 'kld', '1', '2019-02-20 18:07:03', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('78', '1', '2016984891', '3', 'kld', '1', '2019-02-20 18:07:03', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('81', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'kld', '0', '2019-02-20 18:07:03', '2019-03-29 12:49:20');
INSERT INTO `tip_msg` VALUES ('82', '1', '1714****424提现成功，额度1000元', '1', 'txgj', '0', '2019-03-18 13:42:00', '2019-03-18 13:42:00');
INSERT INTO `tip_msg` VALUES ('83', '1', '1378****365提现成功，额度5000元', '1', 'txgj', '0', '2019-03-18 13:42:00', '2019-03-18 13:42:00');
INSERT INTO `tip_msg` VALUES ('84', '1', '1594****936提现成功，额度1000元', '1', 'txgj', '0', '2019-03-18 13:42:00', '2019-03-18 13:42:00');
INSERT INTO `tip_msg` VALUES ('85', '1', '1715****324提现成功，额度20000元', '1', 'txgj', '0', '2019-03-18 13:42:00', '2019-03-18 13:42:00');
INSERT INTO `tip_msg` VALUES ('86', '1', '1314****424借款成功，额度1000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('87', '1', '1394****779借款成功，额度3000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('88', '1', '1594****345借款成功，额度3000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('89', '1', '1378****345借款成功，额度5000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('90', '1', '1398****887借款成功，额度3000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('91', '1', '1595****897借款成功，额度5000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('92', '1', '1594****986借款成功，额度1000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('93', '1', '1316****568借款成功，额度3000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('94', '1', '1714****424借款成功，额度20000元', '1', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('95', '1', '02161516788', '2', 'ylh', '0', '2019-03-19 14:12:07', '2019-03-19 14:12:07');
INSERT INTO `tip_msg` VALUES ('96', '1', '3168104843', '3', 'ylh', '1', '2019-03-19 14:12:07', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('97', '1', '389725267', '3', 'ylh', '1', '2019-03-19 14:12:08', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('98', '1', '2704736048', '3', 'ylh', '0', '2019-03-19 14:12:08', '2019-03-19 14:12:08');
INSERT INTO `tip_msg` VALUES ('99', '1', '1703897867', '3', 'ylh', '1', '2019-03-19 14:12:08', '2019-03-29 11:21:58');
INSERT INTO `tip_msg` VALUES ('100', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'ylh', '0', '2019-03-19 14:12:08', '2019-03-19 14:12:08');
INSERT INTO `tip_msg` VALUES ('101', '1', '1313****424借款成功，额度1000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('102', '1', '1324****779借款成功，额度3000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('103', '1', '1397****345借款成功，额度3000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('104', '1', '1578****345借款成功，额度5000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('105', '1', '1798****887借款成功，额度3000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('106', '1', '1895****897借款成功，额度5000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('107', '1', '1394****976借款成功，额度1000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('108', '1', '1326****898借款成功，额度3000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('109', '1', '1754****904借款成功，额度20000元', '1', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('110', '1', '02161516788', '2', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('111', '1', '2704736048', '3', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('112', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'zjd', '0', '2019-04-18 18:27:37', '2019-04-18 18:27:37');
INSERT INTO `tip_msg` VALUES ('125', '1', '1314****424借款成功，额度1000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('126', '1', '1364****779借款成功，额度3000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('127', '1', '1394****345借款成功，额度3000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('128', '1', '1578****345借款成功，额度5000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('129', '1', '1398****887借款成功，额度3000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('130', '1', '1595****897借款成功，额度5000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('131', '1', '1594****986借款成功，额度1000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('132', '1', '1326****568借款成功，额度3000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('133', '1', '1754****424借款成功，额度20000元', '1', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('134', '1', '02161516788', '2', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('135', '1', '2704736048', '3', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('136', '1', '2637653031', '3', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('137', '1', '1662177962', '3', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('138', '1', '2016984891', '3', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('139', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'wysd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('140', '1', '1314****424借款成功，额度1000元', '1', 'rrsd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('141', '1', '1364****779借款成功，额度3000元', '1', 'rrsd', '0', '2019-04-29 10:59:31', '2019-04-29 10:59:31');
INSERT INTO `tip_msg` VALUES ('142', '1', '1394****345借款成功，额度3000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('143', '1', '1578****345借款成功，额度5000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('144', '1', '1398****887借款成功，额度3000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('145', '1', '1595****897借款成功，额度5000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('146', '1', '1594****986借款成功，额度1000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('147', '1', '1326****568借款成功，额度3000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('148', '1', '1754****424借款成功，额度20000元', '1', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('149', '1', '02161516788', '2', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('150', '1', '2704736048', '3', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('151', '1', '2637653031', '3', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('152', '1', '1662177962', '3', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('153', '1', '2016984891', '3', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('154', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'rrsd', '0', '2019-04-29 10:59:32', '2019-04-29 10:59:32');
INSERT INTO `tip_msg` VALUES ('155', '1', '1314****424借款成功，额度1000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('156', '1', '1364****779借款成功，额度3000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('157', '1', '1394****345借款成功，额度3000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('158', '1', '1578****345借款成功，额度5000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('159', '1', '1398****887借款成功，额度3000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('160', '1', '1595****897借款成功，额度5000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('161', '1', '1594****986借款成功，额度1000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('162', '1', '1326****568借款成功，额度3000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('163', '1', '1754****424借款成功，额度20000元', '1', 'mslq', '0', '2019-05-13 18:56:39', '2019-05-13 18:56:39');
INSERT INTO `tip_msg` VALUES ('164', '1', '02161516788', '2', 'mslq', '0', '2019-05-13 18:56:40', '2019-05-13 18:56:40');
INSERT INTO `tip_msg` VALUES ('165', '1', '2704736048', '3', 'mslq', '0', '2019-05-13 18:56:40', '2019-05-13 18:56:40');
INSERT INTO `tip_msg` VALUES ('166', '1', '2637653031', '3', 'mslq', '0', '2019-05-13 18:56:40', '2019-05-13 18:56:40');
INSERT INTO `tip_msg` VALUES ('167', '1', '2016984891', '3', 'mslq', '0', '2019-05-13 18:56:40', '2019-05-13 18:56:40');
INSERT INTO `tip_msg` VALUES ('168', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'mslq', '0', '2019-05-13 18:56:40', '2019-05-13 18:56:40');
INSERT INTO `tip_msg` VALUES ('169', '1', '1314****424借款成功，额度1000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('170', '1', '1364****779借款成功，额度3000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('171', '1', '1394****345借款成功，额度3000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('172', '1', '1578****345借款成功，额度5000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('173', '1', '1398****887借款成功，额度3000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('174', '1', '1595****897借款成功，额度5000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('175', '1', '1594****986借款成功，额度1000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('176', '1', '1326****568借款成功，额度3000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('177', '1', '1754****424借款成功，额度20000元', '1', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('178', '1', '02161516788', '2', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('179', '1', '2704736048', '3', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('180', '1', '2637653031', '3', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('181', '1', '2016984891', '3', 'sxsd', '0', '2019-05-15 15:23:54', '2019-05-15 15:23:54');
INSERT INTO `tip_msg` VALUES ('182', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'sxsd', '0', '2019-05-15 15:23:55', '2019-05-15 15:23:55');
INSERT INTO `tip_msg` VALUES ('183', '1', '1314****424借款成功，额度1000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('184', '1', '1364****779借款成功，额度3000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('185', '1', '1394****345借款成功，额度3000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('186', '1', '1578****345借款成功，额度5000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('187', '1', '1398****887借款成功，额度3000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('188', '1', '1595****897借款成功，额度5000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('189', '1', '1594****986借款成功，额度1000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('190', '1', '1326****568借款成功，额度3000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('191', '1', '1754****424借款成功，额度20000元', '1', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('192', '1', '02161516788', '2', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('193', '1', '2704736048', '3', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('194', '1', '2637653031', '3', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('195', '1', '2016984891', '3', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('196', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'dnd', '0', '2019-05-20 16:29:51', '2019-05-20 16:29:51');
INSERT INTO `tip_msg` VALUES ('197', '1', '1314****424借款成功，额度1000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('198', '1', '1364****779借款成功，额度3000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('199', '1', '1394****345借款成功，额度3000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('200', '1', '1578****345借款成功，额度5000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('201', '1', '1398****887借款成功，额度3000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('202', '1', '1595****897借款成功，额度5000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('203', '1', '1594****986借款成功，额度1000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('204', '1', '1326****568借款成功，额度3000元', '1', 'kd', '0', '2019-05-22 10:53:46', '2019-05-22 10:53:46');
INSERT INTO `tip_msg` VALUES ('205', '1', '1754****424借款成功，额度20000元', '1', 'kd', '0', '2019-05-22 10:53:47', '2019-05-22 10:53:47');
INSERT INTO `tip_msg` VALUES ('206', '1', '02161516788', '2', 'kd', '0', '2019-05-22 10:53:47', '2019-05-22 10:53:47');
INSERT INTO `tip_msg` VALUES ('207', '1', '2704736048', '3', 'kd', '0', '2019-05-22 10:53:47', '2019-05-22 10:53:47');
INSERT INTO `tip_msg` VALUES ('208', '1', '2637653031', '3', 'kd', '0', '2019-05-22 10:53:47', '2019-05-22 10:53:47');
INSERT INTO `tip_msg` VALUES ('209', '1', '2016984891', '3', 'kd', '0', '2019-05-22 10:53:47', '2019-05-22 10:53:47');
INSERT INTO `tip_msg` VALUES ('210', '1', 'https://webchat.7moor.com/wapchat.html?accessId=5c876c30-1edf-11e9-b6cb-d7b2521b5102&fromUrl=&urlTitle=', '5', 'kd', '0', '2019-05-22 10:53:47', '2019-05-22 10:53:47');

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `os` varchar(20) NOT NULL DEFAULT '' COMMENT '安卓或者IOS',
  `version_android` int(5) NOT NULL DEFAULT '0' COMMENT '安卓版本',
  `version_ios` int(5) NOT NULL DEFAULT '0' COMMENT 'ios版本名称',
  `if_gray` int(10) DEFAULT '0' COMMENT '是否灰度升级（0-否  1-是）',
  `download_url` varchar(255) NOT NULL DEFAULT '' COMMENT '更新地址',
  `force` tinyint(4) NOT NULL DEFAULT '0' COMMENT '强制更新：1强制更新0不强制更新',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `app_name` varchar(30) NOT NULL DEFAULT '0' COMMENT 'app名称',
  `disable` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0未删除 1删除)',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(30) NOT NULL DEFAULT 'sys' COMMENT '最后修改人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_os` (`os`) USING BTREE,
  KEY `idx_app_name` (`app_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('1', 'android', '6', '1', '1', 'https://apk.ipahc.com/0cc88bcf775ca17d792c9e1e3b9984e6.apk', '1', '1修改bug \r\n 2优化用户体验', 'wsj', '1', '2018-12-30 14:24:58', '2019-01-13 16:12:52', 'sys');
INSERT INTO `version` VALUES ('2', 'ios', '1', '1', '0', 'https://cdn.bvubu.top/appp.php/7382', '0', '1.优化UI，增加新功能\r\n2.如遇到无法下载等问题，\r\n添加公众号：万三借服务咨询', 'wsj', '1', '2019-01-06 19:20:06', '2019-01-13 16:12:54', 'sys');
INSERT INTO `version` VALUES ('3', 'android', '18', '0', '1', 'https://njysd-app-1256093684.file.myqcloud.com/wsj/wsj_V19.apk', '1', '1修改bug \r\n2优化用户体验', 'wsj', '0', '2019-01-13 16:12:32', '2019-05-23 10:16:20', 'sys');
INSERT INTO `version` VALUES ('4', 'ios', '0', '15', '1', 'https://njysd-app-1256093684.file.myqcloud.com/wsj/ios/wsj15.ipa', '1', '1修改bug,2优化用户体验  ', 'wsj', '0', '2019-01-13 16:12:32', '2019-05-21 10:15:00', 'sys');
INSERT INTO `version` VALUES ('5', 'android', '21', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/txgj/txgj_V21.apk', '1', '1修改bug,2优化用户体验', 'txgj', '0', '2019-02-02 17:03:43', '2019-05-15 20:46:30', 'sys');
INSERT INTO `version` VALUES ('6', 'android', '0', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/bbh/bbh_V1.apk', '0', '1修改bug,2优化用户体验', 'bbh', '1', '2019-02-13 20:31:34', '2019-02-16 00:46:01', 'sys');
INSERT INTO `version` VALUES ('7', 'ios', '0', '35', '0', 'https://zbrnq.cn/bbhyl/#/download', '1', '1修改bug,2优化用户体验', 'bbh', '0', '2019-02-13 22:51:32', '2019-05-16 16:33:04', 'sys');
INSERT INTO `version` VALUES ('8', 'android', '35', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/bbh/bbh_V35.apk', '1', '1修改bug,2优化用户体验', 'bbh', '0', '2019-02-16 00:45:32', '2019-05-16 17:19:10', 'sys');
INSERT INTO `version` VALUES ('9', 'android', '17', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/hfp/hfpV17.apk', '1', '1修改bug,2优化用户体验', 'hfp', '0', '2019-02-16 00:45:32', '2019-04-01 16:08:40', 'sys');
INSERT INTO `version` VALUES ('10', 'ios', '0', '16', '0', 'https://ewangmi.com/hfp/#/download', '1', '1修改bug,2优化用户体验', 'hfp', '0', '2019-02-16 00:45:32', '2019-03-29 16:23:20', 'sys');
INSERT INTO `version` VALUES ('11', 'android', '36', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/kld/kld_V36.apk', '1', '1修改bug,2优化用户体验', 'kld', '0', '2019-02-21 18:59:50', '2019-05-16 17:09:49', 'sys');
INSERT INTO `version` VALUES ('12', 'ios', '0', '35', '0', 'https://app.ipahc.com/#/pUyL295', '1', '1修改bug,2优化用户体验', 'kld', '0', '2019-02-21 21:18:32', '2019-05-16 16:33:37', 'sys');
INSERT INTO `version` VALUES ('13', 'ios', '0', '35', '0', 'https://zbrnq.cn/ylh/#/download', '1', '1修改bug,2优化用户体验', 'ylh', '0', '2019-03-19 17:41:59', '2019-05-16 16:33:22', 'sys');
INSERT INTO `version` VALUES ('14', 'android', '35', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/ylh/ylh_V35.apk', '1', '1修改bug\r\n2优化用户体验', 'ylh', '0', '2019-03-19 17:42:31', '2019-05-16 17:22:10', 'sys');
INSERT INTO `version` VALUES ('15', 'android', '6', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/zjd/zjd_V6.apk', '1', '1修改bug\r\n2优化用户体验', 'zjd', '0', '2019-04-23 21:19:50', '2019-05-21 16:31:23', 'sys');
INSERT INTO `version` VALUES ('16', 'ios', '0', '0', '0', 'https://evmvh.cn/zjdyl/#/download', '0', '1修改bug\r\n2优化用户体验', 'zjd', '0', '2019-04-25 16:55:40', '2019-05-16 11:13:19', 'sys');
INSERT INTO `version` VALUES ('18', 'android', '6', '0', '1', 'https://njysd-app-1256093684.file.myqcloud.com/rrsd/rrsd_V6.apk', '1', '1修改bug\r\n2优化用户体验', 'rrsd', '0', '2019-05-06 21:28:52', '2019-05-21 17:26:18', 'sys');
INSERT INTO `version` VALUES ('19', 'ios', '0', '0', '1', 'https://b5dh.cn/rrsdyl/#/iosRrsdDownload', '0', '1修改bug\r\n2优化用户体验', 'rrsd', '0', '2019-05-06 23:54:54', '2019-05-16 11:13:28', 'sys');
INSERT INTO `version` VALUES ('20', 'ios', '0', '0', '1', 'https://btgpi.cn/wysdyl/#/iosWysdDownload', '0', '1修改bug\r\n2优化用户体验', 'wysd', '0', '2019-05-06 23:55:30', '2019-05-16 11:13:36', 'sys');
INSERT INTO `version` VALUES ('21', 'android', '6', '0', '1', 'https://njysd-app-1256093684.file.myqcloud.com/wysd/wysd_V6.apk', '1', '1修改bug\r\n2优化用户体验', 'wysd', '0', '2019-05-06 23:56:20', '2019-05-21 17:40:37', 'sys');
INSERT INTO `version` VALUES ('23', 'ios', '0', '19', '0', 'https://btgpi.cn/txgjyl/#/iosTxgjDownload', '1', '1修改bug\r\n2优化用户体验', 'txgj', '0', '2019-05-09 18:09:50', '2019-05-16 11:12:49', 'sys');
INSERT INTO `version` VALUES ('24', 'android', '6', '0', '0', '\r\nhttps://njysd-app-1256093684.file.myqcloud.com/mslq/mslq_V6.apk', '1', '1修改bug\r\n2优化用户体验', 'mslq', '0', '2019-05-14 11:53:07', '2019-05-21 18:40:17', 'sys');
INSERT INTO `version` VALUES ('25', 'ios', '0', '0', '0', 'https://u5ih.cn/mslqyl/#/download', '0', '1修改bug\r\n2优化用户体验', 'mslq', '0', '2019-05-15 18:28:22', '2019-05-15 18:28:52', 'sys');
INSERT INTO `version` VALUES ('26', 'android', '3', '0', '0', 'https://njysd-app-1256093684.file.myqcloud.com/sxsd/sxsd_V3.apk', '1', '1修改bug\r\n2优化用户体验', 'sxsd', '0', '2019-05-21 14:21:44', '2019-05-23 15:21:24', 'sys');
