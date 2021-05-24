
INSERT INTO `alarm_cat` VALUES (1, 1, 0, NULL, NULL, NULL, '仓内抛单预警', NULL, '2021-05-12 22:05:03.079637', '2021-05-12 22:05:03.079637', NULL);
INSERT INTO `alarm_cat` VALUES (2, 1, 0, NULL, NULL, NULL, '门店作弊预警', NULL, '2021-05-12 22:05:03.079637', '2021-05-12 22:05:03.079637', NULL);
INSERT INTO `alarm_cat` VALUES (3, 1, 0, NULL, NULL, NULL, '三方运力预警', NULL, '2021-05-12 22:05:03.079637', '2021-05-12 22:05:03.079637', NULL);


INSERT INTO `alarm_channel` VALUES (1, NULL, 'weixin', NULL, '企业微信', 'http://fp.yonghuivip.com/message/push', NULL, 'b80f85a58f1b7992d91111fa18225209', NULL, NULL, NULL, '1900-01-20 20:34:19.692441', '1900-01-20 20:34:19.692441', NULL);
INSERT INTO `alarm_channel` VALUES (2, NULL, 'email', NULL, '邮箱', 'smtp.163.com', NULL, NULL, 'lifetaster@163.com', 'ZWEGGKKSBLDDNTQS', NULL, '1900-01-20 20:34:19.707982', '1900-01-20 20:34:19.707982', NULL);
INSERT INTO `alarm_channel` VALUES (3, NULL, 'sms', NULL, '短信', NULL, NULL, NULL, NULL, NULL, NULL, '1900-01-20 20:34:19.726031', '1900-01-20 20:34:19.726031', NULL);
INSERT INTO `alarm_channel` VALUES (4, NULL, 'weixin', NULL, '企业微信', 'http://fp.yonghuivip.com/message/push', NULL, 'b80f85a58f1b7992d91111fa18225209', '', '', NULL, '2021-05-12 22:10:03.519951', '2021-05-12 22:10:03.519951', NULL);
INSERT INTO `alarm_channel` VALUES (5, NULL, 'email', NULL, '邮箱', 'smtp.163.com', NULL, '', 'lifetaster@163.com', 'ZWEGGKKSBLDDNTQS', NULL, '2021-05-12 22:10:03.537967', '2021-05-12 22:10:03.537967', NULL);
INSERT INTO `alarm_channel` VALUES (6, NULL, 'sms', NULL, '短信', '', NULL, '', NULL, NULL, NULL, '2021-05-12 22:10:03.552006', '2021-05-12 22:10:03.552006', NULL);


INSERT INTO `alarm_dic` VALUES (1, 'ofs_shop_center', 'shop_config_info', 'name', 'varchar', 'name=Delivery 配置key的名称', '1900-01-20 20:34:19.389360', '1900-01-20 20:34:19.389360', NULL);
INSERT INTO `alarm_dic` VALUES (2, 'ofs_shop_center', 'shop_config_info', 'value', 'varchar', 'name=Delivery&&value=1|3|4为系统派单', '1900-01-20 20:34:19.461827', '1900-01-20 20:34:19.461827', NULL);
INSERT INTO `alarm_dic` VALUES (3, 'picklist_db', 't_trade_picklist_order', 'start_pick_time', 'datetime', '拣货开始时间', '1900-01-20 20:34:19.476366', '1900-01-20 20:34:19.476366', NULL);
INSERT INTO `alarm_dic` VALUES (4, 'picklist_db', 't_trade_picklist_order', 'finish_pick_time', 'datetime', '拣货结束时间', '1900-01-20 20:34:19.488900', '1900-01-20 20:34:19.488900', NULL);
INSERT INTO `alarm_dic` VALUES (5, 'ofs_delivery_center', 't_delivery_order', 'id', 'bigint', '配送单主键id', '1900-01-20 20:34:19.503940', '1900-01-20 20:34:19.503940', NULL);
INSERT INTO `alarm_dic` VALUES (6, 'ofs_delivery_center', 't_delivery_order', 'status', 'int', '状态(0:待准备,10:待派单,20:待接单,30:待提货,40:待出发,50:待到达,60:待妥投,70:待确认,80:已完成,90:已关闭,100:已取消)', '1900-01-20 20:34:19.517476', '1900-01-20 20:34:19.517476', NULL);
INSERT INTO `alarm_dic` VALUES (7, 'ofs_delivery_center', 't_delivery_order', 'end_time', 'datetime', '结单时间', '1900-01-20 20:34:19.533016', '1900-01-20 20:34:19.533016', NULL);
INSERT INTO `alarm_dic` VALUES (8, 'ofs_delivery_center', 't_delivery_order', 'dispatch_time', 'datetime', '派单时间', '1900-01-20 20:34:19.551566', '1900-01-20 20:34:19.551566', NULL);
INSERT INTO `alarm_dic` VALUES (9, 'ofs_delivery_center', 't_delivery_order', 'pickup_time', 'datetime', '提货时间', '1900-01-20 20:34:19.569112', '1900-01-20 20:34:19.569112', NULL);
INSERT INTO `alarm_dic` VALUES (10, 'ofs_delivery_center', 't_delivery_order', 'delivery_releasable_time', 'datetime', '配送可释放时间', '1900-01-20 20:34:19.586159', '1900-01-20 20:34:19.586159', NULL);
INSERT INTO `alarm_dic` VALUES (11, 'ofs_delivery_center', 't_delivery_order', 'delivery_deadline_time', 'datetime', '配送截止时间', '1900-01-20 20:34:19.602702', '1900-01-20 20:34:19.602702', NULL);
INSERT INTO `alarm_dic` VALUES (12, 'ofs_delivery_center', 't_delivery_exception_order', 'delivery_order_id', 'bigint', '配送单号', '1900-01-20 20:34:19.621253', '1900-01-20 20:34:19.621253', NULL);
INSERT INTO `alarm_dic` VALUES (13, 'ofs_delivery_center', 't_delivery_exception_order', 'delivery', 'int', '配送状态(0:未配送,1:已配送)', '1900-01-20 20:34:19.639801', '1900-01-20 20:34:19.639801', NULL);


INSERT INTO `alarm_notice` VALUES (1, 1, 2, 1, NULL, 1, 0, NULL, '2021-05-12 22:05:03.315534', '2021-05-12 22:05:03.315534', NULL);
INSERT INTO `alarm_notice` VALUES (2, 2, 2, 2, NULL, 1, 0, NULL, '2021-05-12 22:05:03.315534', '2021-05-12 22:05:03.315534', NULL);
INSERT INTO `alarm_notice` VALUES (3, 3, 2, 3, NULL, 1, 0, NULL, '2021-05-12 22:05:03.315534', '2021-05-12 22:05:03.315534', NULL);
INSERT INTO `alarm_notice` VALUES (4, 4, 2, 4, NULL, 1, 0, NULL, '2021-05-12 22:05:03.315534', '2021-05-12 22:05:03.315534', NULL);
INSERT INTO `alarm_notice` VALUES (5, 5, 2, 5, NULL, 1, 1, NULL, '2021-05-12 22:05:03.315534', '2021-05-12 22:05:03.315534', NULL);


INSERT INTO `alarm_params` VALUES (1, 1, '提前抛单', 3, 'picklist_db_t_trade_picklist_order', 'start_pick_time', 'datetime', '拣货开始时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (2, 1, '提前抛单', 4, 'picklist_db_t_trade_picklist_order', 'finish_pick_time', 'datetime', '拣货结束时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (3, 2, '门店作弊', 5, 'ofs_delivery_center_t_delivery_order', 'id', 'bigint', '配送单主键', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (4, 2, '门店作弊', 6, 'ofs_delivery_center_t_delivery_order', 'status', 'int', '状态(0:待准备,10:待派单,20:待接单,30:待提货,40:待出发,50:待到达,60:待妥投,70:待确认,80:已完成,90:已关闭,100:已取消)', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (5, 2, '门店作弊', 7, 'ofs_delivery_center_t_delivery_order', 'end_time', 'datetime', '结单时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (6, 2, '门店作弊', 8, 'ofs_delivery_center_t_delivery_order', 'dispatch_time', 'datetime', '派单时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (7, 2, '门店作弊', 9, 'ofs_delivery_center_t_delivery_order', 'pickup_time', 'datetime', '提货时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (8, 2, '门店作弊', 12, 'ofs_delivery_center_t_delivery_exception_order', 'delivery_order_id', 'bigint', '配送单号', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (9, 2, '门店作弊', 13, 'ofs_delivery_center_t_delivery_exception_order', 'delivery', 'varchar', '配送状态(0:未配送,1:已配送)', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (10, 3, '派单超时', 1, 'ofs_shop_center_shop_config_info', 'name', 'varchar', '配置建key名称(这里的值为name=Delivery)', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (11, 3, '派单超时', 2, 'ofs_shop_center_shop_config_info', 'value', 'varchar', 'value=1|3|4为系统派单', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (12, 3, '派单超时', 10, 'ofs_delivery_center_t_delivery_order', 'delivery_releasable_time', 'datetime', '配送可释放时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (13, 3, '派单超时', 11, 'ofs_delivery_center_t_delivery_order', 'delivery_deadline_time', 'datetime', '配送截止时间', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);
INSERT INTO `alarm_params` VALUES (14, 3, '派单超时', 6, 'ofs_delivery_center_t_delivery_order', 'status', 'int', '订单状态为10', '2021-05-12 22:05:03.394244', '2021-05-12 22:05:03.394244', NULL);


INSERT INTO `alarm_reg` VALUES (1, 1, '提前抛单', 'tiqianpaodan', '20', NULL, '(拣货完成时间-[接单时间/派单时]) < 20秒', 'picklist_db_t_trade_picklist_order_finish_pick_time - picklist_db_t_trade_picklist_order_start_pick_time < 20', 1, 'expressionComputer', 2, '{\"拣货结束时间\":\"finksh_pick_time\",\"拣货开始时间\":\"start_pick_time\"}', '2021-05-12 22:05:03.423821', '2021-05-12 22:05:03.423821', NULL);
INSERT INTO `alarm_reg` VALUES (2, 2, '门店作弊', 'mendianzuobi', NULL, NULL, '订单是关闭状态;并且(关单时间 - [派单时间/提货时间]) < 2分钟;并且异常单审核为已产生配送', 'ofs_delivery_center_t_delivery_order_status == 90 && (ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_dispatch_time < 2 || ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_pickup_time < 2) && ofs_delivery_center_t_delivery_exception_order_delivery_order_id ==  ofs_delivery_center_t_delivery_order_delivery_order_id && delivery == 1', 1, 'expressionComputer', 6, NULL, '2021-05-12 22:05:03.423821', '2021-05-12 22:05:03.423821', NULL);
INSERT INTO `alarm_reg` VALUES (3, 2, '派单超时', 'paidanchaoshi', NULL, NULL, ' 门店是系统派单模式;并且(当前时间 - 配送任务释放时间) > 10分钟;并且(考核时间-当前时间) < 15分钟 ;并且订单状态为 10', 'ofs_shop_center_shop_config_info_name == \'delivery\' && (ofs_shop_center_shop_config_info_value == 1 || ofs_shop_center_shop_config_info_value == 3 ||ofs_shop_center_shop_config_info_value == 4)  && currentTime - ofs_delivery_center_t_delivery_order_delivery_releasable_time > 10 && ofs_delivery_center_t_delivery_order_delivery_deadline_time - currentTime < 15 && ofs_delivery_center_t_delivery_order_status == 10', 1, 'expressionComputer', 6, NULL, '2021-05-12 22:05:03.423821', '2021-05-12 22:05:03.423821', NULL);
INSERT INTO `alarm_reg` VALUES (4, 3, '发三方运力失败', 'fasanfangyunlishibai', NULL, NULL, '监控系统提供告警接口，业务系统返单异常是调用接口发告警', NULL, 1, 'expressionComputer', 3, NULL, '2021-05-12 22:05:03.423821', '2021-05-12 22:05:03.423821', NULL);
INSERT INTO `alarm_reg` VALUES (5, 3, '三方运力接单超时', 'sanfangyunlijiedanchaoshi', NULL, NULL, ' 配送单状态为待派单&&订单发三方成功&&（当前时间-发三方时间）>5分钟', NULL, 1, 'expressionComputer', 4, NULL, '2021-05-12 22:05:03.423821', '2021-05-12 22:05:03.423821', NULL);


INSERT INTO `alarm_template` VALUES (1, 1, '提前抛单', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 22:05:03.476462', '2021-05-12 22:05:03.476462', NULL);
INSERT INTO `alarm_template` VALUES (2, 2, '门店作弊', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 22:05:03.476462', '2021-05-12 22:05:03.476462', NULL);
INSERT INTO `alarm_template` VALUES (3, 3, '派单超时', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 22:05:03.476462', '2021-05-12 22:05:03.476462', NULL);
INSERT INTO `alarm_template` VALUES (4, 4, '发三方运力失败', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 22:05:03.476462', '2021-05-12 22:05:03.476462', NULL);
INSERT INTO `alarm_template` VALUES (5, 5, '三方运力接单超时', 'fanout', '广播', '履约告警:\n业态：${bizName}\n省份：${province}\n城市：${city}\n仓店：${storeNo}\n单号：${orderId}\n预警分类：${alarmCatName}\n规则名称：${regName}\n触发原因：${descr}\n操作人：${operatorName}\n操作人电话：${operatorMobile}', NULL, '2021-05-12 22:05:03.476462', '2021-05-12 22:05:03.476462', NULL);

