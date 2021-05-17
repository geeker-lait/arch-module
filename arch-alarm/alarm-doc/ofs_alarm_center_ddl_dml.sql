/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : ofs_alarm_center

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 12/05/2021 21:57:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm_cat
-- ----------------------------
DROP TABLE IF EXISTS `alarm_cat`;
CREATE TABLE `alarm_cat`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `is_categ` tinyint(1) NULL DEFAULT NULL COMMENT '是否是类目 0:不是，1:是',
  `pid` bigint(19) NULL DEFAULT NULL COMMENT '父id，用于确定警告的层级关系，用于数据分析',
  `alarm_typ` int(11) NULL DEFAULT NULL COMMENT '预警分类名： 0：通知类（不可抗力延迟，快递操作延迟，节假日延迟，收货人要求延迟） 1：处理类（签收前破损，禁运品，联系信息有误，联系信息缺少，超出派送范围， 退回改寄件，拒收） 2：知晓类.....',
  `alarm_val` int(11) NULL DEFAULT NULL COMMENT '预警值:1,2,3,4.... 和alarmName一一对应',
  `alarm_level` int(11) NULL DEFAULT NULL COMMENT '预警级别0~N 从底到高，数字越大，级别越高',
  `alarm_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警类名称分类名（大类）：处理类；通知类；知晓类；作弊类；超时类；类名称：妥投预警 ；提货预警；派单预警；配送预警；......',
  `descr` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明描述',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警分类 预警树结构名称空间，对预警的分类，预警的级别，预警的名称等的定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_cat
-- ----------------------------
INSERT INTO `alarm_cat` VALUES (1, 1, 0, NULL, NULL, NULL, '仓内抛单预警', NULL, '2021-05-12 21:43:03.645249', '2021-05-12 21:43:03.645249', NULL);
INSERT INTO `alarm_cat` VALUES (2, 1, 0, NULL, NULL, NULL, '门店作弊预警', NULL, '2021-05-12 21:43:03.645249', '2021-05-12 21:43:03.645249', NULL);
INSERT INTO `alarm_cat` VALUES (3, 1, 0, NULL, NULL, NULL, '三方运力预警', NULL, '2021-05-12 21:43:03.645249', '2021-05-12 21:43:03.645249', NULL);

-- ----------------------------
-- Table structure for alarm_channel
-- ----------------------------
DROP TABLE IF EXISTS `alarm_channel`;
CREATE TABLE `alarm_channel`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓店号',
  `channel_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道码，msg，weixin，email，sys',
  `channel_val` int(11) NULL DEFAULT NULL COMMENT '通道值，1，2，3....',
  `channel_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道名称',
  `channel_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通道url',
  `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调url',
  `sign` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名',
  `app_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用id或应用key',
  `secrt_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密钥',
  `ext_jsonp` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展json属性',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警通道维度 信息爆炸，每人每天平均要收到2条以上垃圾短信，一些重要消息很可能被遗漏，或不被引起关注。因此我们需要更人性化的让用户可以选择以何种方式发送提醒与预警，竟而更精准有效的通知到他们，如邮件、系统消息、短信、微信等。' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_channel
-- ----------------------------
INSERT INTO `alarm_channel` VALUES (1, NULL, 'weixin', NULL, '企业微信', 'http://fp.yonghuivip.com/message/push', NULL, 'b80f85a58f1b7992d91111fa18225209', '', '', NULL, '2021-05-12 20:34:19.692441', '2021-05-12 20:34:19.692441', NULL);
INSERT INTO `alarm_channel` VALUES (2, NULL, 'email', NULL, '邮箱', 'smtp.163.com', NULL, '', 'lifetaster@163.com', 'ZWEGGKKSBLDDNTQS', NULL, '2021-05-12 20:34:19.707982', '2021-05-12 20:34:19.707982', NULL);
INSERT INTO `alarm_channel` VALUES (3, NULL, 'sms', NULL, '短信', '', NULL, '', NULL, NULL, NULL, '2021-05-12 20:34:19.726031', '2021-05-12 20:34:19.726031', NULL);

-- ----------------------------
-- Table structure for alarm_computer
-- ----------------------------
DROP TABLE IF EXISTS `alarm_computer`;
CREATE TABLE `alarm_computer`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `computer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计算器名称，对应一个规则计算bean，spring启动时注册进来（唯一）',
  `params_json` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计算器支持的参数列表，自定义时必须知道参数',
  `computer_descr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述描述',
  `ver` int(11) NULL DEFAULT NULL COMMENT '版本',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预警规则计算bean 预警规则计算bean，spring启动时会注册' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_computer
-- ----------------------------
INSERT INTO `alarm_computer` VALUES (1, 'expressionComputer', NULL, '表达式计算器', NULL, '2021-05-12 20:31:46.090556', '2021-05-12 20:31:46.090556', NULL);
INSERT INTO `alarm_computer` VALUES (2, 'finishedBeforePickingComputer', NULL, '捡货提前抛单规则计算器', NULL, '2021-05-12 20:31:48.925549', '2021-05-12 20:31:48.925549', NULL);
INSERT INTO `alarm_computer` VALUES (3, 'storeCheatComputer', NULL, '门店作弊规则计算器', NULL, '2021-05-12 20:31:48.965654', '2021-05-12 20:31:48.965654', NULL);
INSERT INTO `alarm_computer` VALUES (4, 'storeDispatchOrderTimeoutComputer', NULL, '门店派单超时计算器', NULL, '2021-05-12 20:31:48.998742', '2021-05-12 20:31:48.998742', NULL);
INSERT INTO `alarm_computer` VALUES (5, 'thirdTransportReceiveOrderTimeoutComputer', NULL, '三方运力接单超时计算器', NULL, '2021-05-12 20:31:49.036844', '2021-05-12 20:31:49.036844', NULL);

-- ----------------------------
-- Table structure for alarm_config
-- ----------------------------
DROP TABLE IF EXISTS `alarm_config`;
CREATE TABLE `alarm_config`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` bigint(19) NULL DEFAULT NULL COMMENT '主键',
  `namespace` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '命名空间',
  `tnaid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'table name and id',
  `fkey` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性建名 properties key',
  `fval` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性建名对应的属性值可以是一个 json string propertie',
  `ftyp` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性的字段类型',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预警配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_config
-- ----------------------------

-- ----------------------------
-- Table structure for alarm_dic
-- ----------------------------
DROP TABLE IF EXISTS `alarm_dic`;
CREATE TABLE `alarm_dic`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_database` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源库',
  `source_table` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源表',
  `filed_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段码',
  `filed_typ` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `filed_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警规则字典纬度 预警规则字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_dic
-- ----------------------------
INSERT INTO `alarm_dic` VALUES (1, 'ofs_shop_center', 'shop_config_info', 'name', 'varchar', 'name=Delivery 配置key的名称', '2021-05-12 20:34:19.389360', '2021-05-12 20:34:19.389360', NULL);
INSERT INTO `alarm_dic` VALUES (2, 'ofs_shop_center', 'shop_config_info', 'value', 'varchar', 'name=Delivery&&value=1|3|4为系统派单', '2021-05-12 20:34:19.461827', '2021-05-12 20:34:19.461827', NULL);
INSERT INTO `alarm_dic` VALUES (3, 'picklist_db', 't_trade_picklist_order', 'start_pick_time', 'datetime', '拣货开始时间', '2021-05-12 20:34:19.476366', '2021-05-12 20:34:19.476366', NULL);
INSERT INTO `alarm_dic` VALUES (4, 'picklist_db', 't_trade_picklist_order', 'finish_pick_time', 'datetime', '拣货结束时间', '2021-05-12 20:34:19.488900', '2021-05-12 20:34:19.488900', NULL);
INSERT INTO `alarm_dic` VALUES (5, 'ofs_delivery_center', 't_delivery_order', 'id', 'bigint', '配送单主键id', '2021-05-12 20:34:19.503940', '2021-05-12 20:34:19.503940', NULL);
INSERT INTO `alarm_dic` VALUES (6, 'ofs_delivery_center', 't_delivery_order', 'status', 'int', '状态(0:待准备,10:待派单,20:待接单,30:待提货,40:待出发,50:待到达,60:待妥投,70:待确认,80:已完成,90:已关闭,100:已取消)', '2021-05-12 20:34:19.517476', '2021-05-12 20:34:19.517476', NULL);
INSERT INTO `alarm_dic` VALUES (7, 'ofs_delivery_center', 't_delivery_order', 'end_time', 'datetime', '结单时间', '2021-05-12 20:34:19.533016', '2021-05-12 20:34:19.533016', NULL);
INSERT INTO `alarm_dic` VALUES (8, 'ofs_delivery_center', 't_delivery_order', 'dispatch_time', 'datetime', '派单时间', '2021-05-12 20:34:19.551566', '2021-05-12 20:34:19.551566', NULL);
INSERT INTO `alarm_dic` VALUES (9, 'ofs_delivery_center', 't_delivery_order', 'pickup_time', 'datetime', '提货时间', '2021-05-12 20:34:19.569112', '2021-05-12 20:34:19.569112', NULL);
INSERT INTO `alarm_dic` VALUES (10, 'ofs_delivery_center', 't_delivery_order', 'delivery_releasable_time', 'datetime', '配送可释放时间', '2021-05-12 20:34:19.586159', '2021-05-12 20:34:19.586159', NULL);
INSERT INTO `alarm_dic` VALUES (11, 'ofs_delivery_center', 't_delivery_order', 'delivery_deadline_time', 'datetime', '配送截止时间', '2021-05-12 20:34:19.602702', '2021-05-12 20:34:19.602702', NULL);
INSERT INTO `alarm_dic` VALUES (12, 'ofs_delivery_center', 't_delivery_exception_order', 'delivery_order_id', 'bigint', '配送单号', '2021-05-12 20:34:19.621253', '2021-05-12 20:34:19.621253', NULL);
INSERT INTO `alarm_dic` VALUES (13, 'ofs_delivery_center', 't_delivery_exception_order', 'delivery', 'int', '配送状态(0:未配送,1:已配送)', '2021-05-12 20:34:19.639801', '2021-05-12 20:34:19.639801', NULL);

-- ----------------------------
-- Table structure for alarm_job
-- ----------------------------
DROP TABLE IF EXISTS `alarm_job`;
CREATE TABLE `alarm_job`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键|消息id',
  `cron` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定时表达式',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警job维度， 定义执行job产生相应维度的数据，' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_job
-- ----------------------------

-- ----------------------------
-- Table structure for alarm_msg
-- ----------------------------
DROP TABLE IF EXISTS `alarm_msg`;
CREATE TABLE `alarm_msg`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键|消息id',
  `channel_id` bigint(19) NULL DEFAULT NULL COMMENT '通道id',
  `user_id` bigint(19) NULL DEFAULT NULL COMMENT '用户id货账号id',
  `state` int(11) NULL DEFAULT NULL COMMENT '状态 0：成功，1：失败',
  `content` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息内容',
  `send_time` datetime(6) NULL DEFAULT NULL COMMENT '发送时间',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警消息维度，执行预警发送的消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_msg
-- ----------------------------

-- ----------------------------
-- Table structure for alarm_notice
-- ----------------------------
DROP TABLE IF EXISTS `alarm_notice`;
CREATE TABLE `alarm_notice`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_id` bigint(19) NULL DEFAULT NULL COMMENT '通知模板id',
  `channel_id` bigint(19) NULL DEFAULT NULL COMMENT '通道id',
  `reg_id` bigint(19) NULL DEFAULT NULL COMMENT '规则id',
  `store_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓店号',
  `user_id` bigint(19) NULL DEFAULT NULL COMMENT '用户id货账号id',
  `user_typ` int(2) NULL DEFAULT NULL COMMENT '用户类型{1:用户，2：用户组userGroup}',
  `job_id` bigint(19) NULL DEFAULT NULL COMMENT 'job定时器id，结合job 可做定时发送，为空则立刻发送',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约告警规则维度 确定哪些仓店使用那些通知规则，并确定接受预警通知的用户配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_notice
-- ----------------------------
INSERT INTO `alarm_notice` VALUES (1, 1, 2, 1, NULL, 1, 0, NULL, '2021-05-12 21:55:44.632397', '2021-05-12 21:55:44.632397', NULL);
INSERT INTO `alarm_notice` VALUES (2, 2, 2, 2, NULL, 1, 0, NULL, '2021-05-12 21:55:44.632397', '2021-05-12 21:55:44.632397', NULL);
INSERT INTO `alarm_notice` VALUES (3, 3, 2, 3, NULL, 1, 0, NULL, '2021-05-12 21:55:44.632397', '2021-05-12 21:55:44.632397', NULL);
INSERT INTO `alarm_notice` VALUES (4, 4, 2, 4, NULL, 1, 0, NULL, '2021-05-12 21:55:44.632397', '2021-05-12 21:55:44.632397', NULL);
INSERT INTO `alarm_notice` VALUES (5, 5, 2, 5, NULL, 1, 1, NULL, '2021-05-12 21:55:44.632397', '2021-05-12 21:55:44.632397', NULL);

-- ----------------------------
-- Table structure for alarm_operation
-- ----------------------------
DROP TABLE IF EXISTS `alarm_operation`;
CREATE TABLE `alarm_operation`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓店号',
  `order_no` bigint(19) NULL DEFAULT NULL COMMENT '订单号，订单号具有业务意义',
  `order_typ` int(11) NULL DEFAULT NULL COMMENT '订单类型',
  `order_channel` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单渠道，履约|wms|其他....',
  `operator_id` bigint(19) NULL DEFAULT NULL COMMENT '用户id或账号id,来自用户领域或用户中心',
  `operator_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作业人名称',
  `work_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作业名称，来自数据字典，打包，拣货，配送......可以涵盖整个履约链路中的作业',
  `work_val` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作业值，与workName一一对应，来自数据字典',
  `ctime` datetime(6) NULL DEFAULT NULL COMMENT '作业时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警作业维度 基于作业维度，围绕着仓店和订单，便于知道和定位那个仓店的， 哪个订单，哪个作业人员，做如何的操作等， 这里支持一个订单可以有多个人去作业' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_operation
-- ----------------------------

-- ----------------------------
-- Table structure for alarm_order
-- ----------------------------
DROP TABLE IF EXISTS `alarm_order`;
CREATE TABLE `alarm_order`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓店号',
  `order_no` bigint(19) NULL DEFAULT NULL COMMENT '订单号',
  `order_type` int(11) NULL DEFAULT NULL COMMENT '订单类型',
  `order_channel` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单渠道，履约|wms|其他....',
  `reg_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警健',
  `reg_val` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警值',
  `reg_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警名称',
  `cur_val` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前值，实际业务得到的值',
  `reg_id` bigint(19) NULL DEFAULT NULL COMMENT '规则id',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警订单维度 基于订单维度' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_order
-- ----------------------------

-- ----------------------------
-- Table structure for alarm_params
-- ----------------------------
DROP TABLE IF EXISTS `alarm_params`;
CREATE TABLE `alarm_params`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `reg_id` bigint(19) NULL DEFAULT NULL COMMENT '规则id',
  `reg_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余规则名称，展示用',
  `dic_id` bigint(19) NULL DEFAULT NULL COMMENT '字典id冗余处理，查找用',
  `dbtb` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库表,值如db.tb 确定唯一',
  `filed_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段码',
  `filed_typ` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `filed_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名，展示用',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预警规则param 预警规则计算参数' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_params
-- ----------------------------
INSERT INTO `alarm_params` VALUES (1, 1, '提前抛单', 3, 'picklist_db_t_trade_picklist_order', 'start_pick_time', 'datetime', '拣货开始时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (2, 1, '提前抛单', 4, 'picklist_db_t_trade_picklist_order', 'finish_pick_time', 'datetime', '拣货结束时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (3, 2, '门店作弊', 5, 'ofs_delivery_center_t_delivery_order', 'id', 'bigint', '配送单主键', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (4, 2, '门店作弊', 6, 'ofs_delivery_center_t_delivery_order', 'status', 'int', '状态(0:待准备,10:待派单,20:待接单,30:待提货,40:待出发,50:待到达,60:待妥投,70:待确认,80:已完成,90:已关闭,100:已取消)', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (5, 2, '门店作弊', 7, 'ofs_delivery_center_t_delivery_order', 'end_time', 'datetime', '结单时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (6, 2, '门店作弊', 8, 'ofs_delivery_center_t_delivery_order', 'dispatch_time', 'datetime', '派单时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (7, 2, '门店作弊', 9, 'ofs_delivery_center_t_delivery_order', 'pickup_time', 'datetime', '提货时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (8, 2, '门店作弊', 12, 'ofs_delivery_center_t_delivery_exception_order', 'delivery_order_id', 'bigint', '配送单号', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (9, 2, '门店作弊', 13, 'ofs_delivery_center_t_delivery_exception_order', 'delivery', 'varchar', '配送状态(0:未配送,1:已配送)', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (10, 3, '派单超时', 1, 'ofs_shop_center_shop_config_info', 'name', 'varchar', '配置建key名称(这里的值为name=Delivery)', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (11, 3, '派单超时', 2, 'ofs_shop_center_shop_config_info', 'value', 'varchar', 'value=1|3|4为系统派单', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (12, 3, '派单超时', 10, 'ofs_delivery_center_t_delivery_order', 'delivery_releasable_time', 'datetime', '配送可释放时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (13, 3, '派单超时', 11, 'ofs_delivery_center_t_delivery_order', 'delivery_deadline_time', 'datetime', '配送截止时间', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);
INSERT INTO `alarm_params` VALUES (14, 3, '派单超时', 6, 'ofs_delivery_center_t_delivery_order', 'status', 'int', '订单状态为10', '2021-05-12 21:47:40.171558', '2021-05-12 21:47:40.171558', NULL);

-- ----------------------------
-- Table structure for alarm_reg
-- ----------------------------
DROP TABLE IF EXISTS `alarm_reg`;
CREATE TABLE `alarm_reg`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `catid` bigint(19) NULL DEFAULT NULL COMMENT '类目id',
  `reg_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则名称',
  `reg_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则健，key唯一',
  `reg_val` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则默认值，参考值',
  `reg_datasource` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则参考值来源，可以是一个api接口，一条sql语句 如： http://xxx/getDeliveryTime…… sql://192.168.0.1@db@user:pwd/select * from tb……',
  `descr` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则对描述',
  `expression` varchar(1204) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条件或条件表达式，如果没有按照coputer的逻辑计算，如果有computer按照该表达式计算',
  `computer_id` bigint(19) NULL DEFAULT NULL COMMENT '对应ofs_alarm_computer表中单一个计算器',
  `computer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计算器名称',
  `param_count` int(4) NULL DEFAULT NULL COMMENT '参数计数器，用于分布式计算，收集参数计数',
  `placeholder_json` varchar(1204) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则模板通知内容中的占位符',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警规则维度 预警规则，如： 规则1：运单状态为委托接受，超过X时间未发货的运单，给出提醒。 规则2：运单状态为提货，未到货的运单，如果当前时间减去接单时间，超过标准时效，则给出提醒，将延误。 规则3：运单状态为发货确认或提货，当前时间减去入场时间，超过X时间未出场的，给出提醒。 规则4：运单状态提货，超过X时间，车辆/骑手的LBS地址没有变化，则给出预警提示，送货异常' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_reg
-- ----------------------------
INSERT INTO `alarm_reg` VALUES (1, 1, '提前抛单', 'tiqianpaodan', '20', NULL, '(拣货完成时间-[接单时间/派单时]) < 20秒', 'picklist_db_t_trade_picklist_order_finish_pick_time - picklist_db_t_trade_picklist_order_start_pick_time < 20', 1, 'expressionComputer', 2, '{\"拣货结束时间\":\"finksh_pick_time\",\"拣货开始时间\":\"start_pick_time\"}', '2021-05-12 21:53:35.463066', '2021-05-12 21:53:35.463066', NULL);
INSERT INTO `alarm_reg` VALUES (2, 2, '门店作弊', 'mendianzuobi', NULL, NULL, '订单是关闭状态;并且(关单时间 - [派单时间/提货时间]) < 2分钟;并且异常单审核为已产生配送', 'ofs_delivery_center_t_delivery_order_status == 90 && (ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_dispatch_time < 2 || ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_pickup_time < 2) && ofs_delivery_center_t_delivery_exception_order_delivery_order_id ==  ofs_delivery_center_t_delivery_order_delivery_order_id && delivery == 1', 1, 'expressionComputer', 6, NULL, '2021-05-12 21:53:35.463066', '2021-05-12 21:53:35.463066', NULL);
INSERT INTO `alarm_reg` VALUES (3, 2, '派单超时', 'paidanchaoshi', NULL, NULL, ' 门店是系统派单模式;并且(当前时间 - 配送任务释放时间) > 10分钟;并且(考核时间-当前时间) < 15分钟 ;并且订单状态为 10', 'ofs_shop_center_shop_config_info_name == \'delivery\' && (ofs_shop_center_shop_config_info_value == 1 || ofs_shop_center_shop_config_info_value == 3 ||ofs_shop_center_shop_config_info_value == 4)  && currentTime - ofs_delivery_center_t_delivery_order_delivery_releasable_time > 10 && ofs_delivery_center_t_delivery_order_delivery_deadline_time - currentTime < 15 && ofs_delivery_center_t_delivery_order_status == 10', 1, 'expressionComputer', 6, NULL, '2021-05-12 21:53:35.463066', '2021-05-12 21:53:35.463066', NULL);
INSERT INTO `alarm_reg` VALUES (4, 3, '发三方运力失败', 'fasanfangyunlishibai', NULL, NULL, '监控系统提供告警接口，业务系统返单异常是调用接口发告警', NULL, 1, 'expressionComputer', 3, NULL, '2021-05-12 21:53:35.463066', '2021-05-12 21:53:35.463066', NULL);
INSERT INTO `alarm_reg` VALUES (5, 3, '三方运力接单超时', 'sanfangyunlijiedanchaoshi', NULL, NULL, ' 配送单状态为待派单&&订单发三方成功&&（当前时间-发三方时间）>5分钟', NULL, 1, 'expressionComputer', 4, NULL, '2021-05-12 21:53:35.463066', '2021-05-12 21:53:35.463066', NULL);

-- ----------------------------
-- Table structure for alarm_store
-- ----------------------------
DROP TABLE IF EXISTS `alarm_store`;
CREATE TABLE `alarm_store`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `store_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓店编号',
  `cat_id` bigint(19) NULL DEFAULT NULL COMMENT '预警类目id',
  `reg_json` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警规则json对象，由ofs_alarm_reg对象配置相应的键值对，这里可以用另一张表来记录',
  `biz_line` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务线，用来筛选系统如履约|wms|其他',
  `ver` int(11) NULL DEFAULT NULL COMMENT '版本号',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约告警仓店实体 通过仓店配置，将storeNo 和catId建立关联关系，开启对仓店的监控 同时可选择开启监控那些指标regJson' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_store
-- ----------------------------

-- ----------------------------
-- Table structure for alarm_template
-- ----------------------------
DROP TABLE IF EXISTS `alarm_template`;
CREATE TABLE `alarm_template`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `reg_id` bigint(19) NULL DEFAULT NULL COMMENT '规则id',
  `reg_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预警名称',
  `mode_val` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模式值{1:点对点，2：广播，3:订阅}',
  `mode_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模式名',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知内容，可取自模板或自定义， 用regKey，regVal，regName，currVal 填充',
  `ver` int(11) NULL DEFAULT NULL COMMENT '版本',
  `ctime` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `last_updated_at` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '最后更新时间',
  `tenant_id` bigint(19) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '履约预警通知模板 定义不同场景，不同的通知模式（direct，fanout，topic等），不同的消息内容，不同的通知通道。如需要针对骑手提货异常发送微信消息提醒，选择微信通知，选择通知模板，编辑或使用规则模板内容或使用自定义通知内容完成配置，当到达业务预值时，触发该通知，不仅可以同时通知到骑手，还可以通知主管，通知仓店等.....' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of alarm_template
-- ----------------------------
INSERT INTO `alarm_template` VALUES (1, 1, '提前抛单', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 21:47:40.250769', '2021-05-12 21:47:40.250769', NULL);
INSERT INTO `alarm_template` VALUES (2, 2, '门店作弊', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 21:47:40.250769', '2021-05-12 21:47:40.250769', NULL);
INSERT INTO `alarm_template` VALUES (3, 3, '派单超时', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 21:47:40.250769', '2021-05-12 21:47:40.250769', NULL);
INSERT INTO `alarm_template` VALUES (4, 4, '发三方运力失败', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 21:47:40.250769', '2021-05-12 21:47:40.250769', NULL);
INSERT INTO `alarm_template` VALUES (5, 5, '三方运力接单超时', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 21:47:40.250769', '2021-05-12 21:47:40.250769', NULL);

SET FOREIGN_KEY_CHECKS = 1;
