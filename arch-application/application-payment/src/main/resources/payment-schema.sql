-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `pay`;
USE `pay`;

CREATE TABLE IF NOT EXISTS `pay_merchant`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id` (255) COMMENT '系统账号ID',
    `merchant_no` (255) COMMENT '系统为其生成商户号',
    `merchant_name` (255) COMMENT '公司名称',
    `merchant_code` (255) COMMENT '公司营业执照编码',
    `concat_name` (255) COMMENT '联系人',
    `concat_phone` (255) COMMENT '联系人手机',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-商户';

CREATE TABLE IF NOT EXISTS `pay_merchant_app`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id` (255) COMMENT '系统账号ID',
    `merchant_no` (255) COMMENT '系统为其生成商户号',
    `app_id` (255) COMMENT '应用ID',
    `app_code` (255) COMMENT '应用码',
    `app_name` (255) COMMENT '应用名',
    `app_typ` (255) COMMENT '应用业务类型，适配不同支付通道，可从字典获取',
    `app_url` (255) COMMENT '应用下载链接',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-商户应用';

CREATE TABLE IF NOT EXISTS `pay_channel`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_name` (255) COMMENT '通道名称',
    `channel_code` (255) COMMENT '通道码',
    `channel_url` (255) COMMENT '通道连接',
    `descr` (255) COMMENT '描述说明',
    `sotrted` (255) COMMENT '排序',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-通道';

CREATE TABLE IF NOT EXISTS `pay_status_code`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_id` (255) COMMENT '通道ID',
    `channel_code` (255) COMMENT '通道码',
    `channel_state_code` (255) COMMENT '通道状态码',
    `channel_state_descr` (255) COMMENT '通过状态码描述',
    `state_code` (255) COMMENT '统一状态码',
    `state_descr` (255) COMMENT '统一状态码描述',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-状态码映射';

CREATE TABLE IF NOT EXISTS `pay_channel_bank`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `bank_code` (255) COMMENT '银行码',
    `bank_name` (255) COMMENT '银行名称',
    `channel_code` (255) COMMENT '通道码',
    `channel_id` (255) COMMENT '通道ID',
    `weight` (255) COMMENT '权重',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-应用通道支持银行表';

CREATE TABLE IF NOT EXISTS `pay_directive`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_id` (255) COMMENT '通道ID',
    `channel_code` (255) COMMENT '通道码',
    `directive_name` (255) COMMENT '指令名称',
    `directive_code` (255) COMMENT '指令码',
    `directive_uri` (255) COMMENT '指令URI',
    `redirect_url` (255) COMMENT '定向URL',
    `callback_url` (255) COMMENT '指令回调URL',
    `weight` (255) COMMENT '权重',
    `descr` (255) COMMENT '描述',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-指令集';

CREATE TABLE IF NOT EXISTS `pay_config`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id` (255) COMMENT '应用ID',
    `config_key` (255) COMMENT '配置key',
    `config_json` (255) COMMENT '配置key对应的json串',
    `descr` (255) COMMENT '描述',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-配置';

CREATE TABLE IF NOT EXISTS `pay_merchant_channel`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_id` (255) COMMENT '通道ID',
    `merchant_id` (255) COMMENT '商户ID',
    `merchant_no` (255) COMMENT '商户号',
    `private_key` (255) COMMENT '私钥',
    `public_key` (255) COMMENT '公钥',
    `secret_key` (255) COMMENT '密钥',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-商户通道';

CREATE TABLE IF NOT EXISTS `pay_strategy`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id` (255) COMMENT '应用ID',
    `app_code` (255) COMMENT '应用码',
    `channel_id` (255) COMMENT '通道ID',
    `merchant_id` (255) COMMENT '商户ID',
    `priority` (255) COMMENT '权重或优先级',
    `rule_id` (255) COMMENT '规则ID,这里后期借助规则引擎来实现，预留规则id,前期先简单静态，根据权重或优先级',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-应用商户通道策略';

CREATE TABLE IF NOT EXISTS `pay_request_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id` (255) COMMENT '应用ID',
    `user_id` (255) COMMENT '用户id',
    `amount` (255) COMMENT '金额',
    `bankcard` (255) COMMENT '银行卡',
    `bank_code` (255) COMMENT '银行码',
    `biz_code` (255) COMMENT '业务码',
    `biz_id` (255) COMMENT '业务id',
    `channel_code` (255) COMMENT '通道码',
    `channel_id` (255) COMMENT '通道id',
    `merchant_id` (255) COMMENT '商户id',
    `device_id` (255) COMMENT '设备ID',
    `directive_code` (255) COMMENT '指令码',
    `payment_id` (255) COMMENT '三方需要的支付流水号',
    `phone_num` (255) COMMENT '手机号',
    `protocol_id` (255) COMMENT '协议号',
    `return_code` (255) COMMENT '返回码',
    `return_msg` (255) COMMENT '返回信息',
    `uid` (255) COMMENT '用户id',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-请求记录';

CREATE TABLE IF NOT EXISTS `pay_binded_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id` (255) COMMENT '应用ID',
    `channel_id` (255) COMMENT '通道ID',
    `uid` (255) COMMENT '用户ID',
    `idcard` (255) COMMENT '身份证号',
    `user_name` (255) COMMENT '用户名',
    `bankcard` (255) COMMENT '银行卡',
    `bank_code` (255) COMMENT '卡码',
    `phone` (255) COMMENT '预留手机号',
    `protocol_id` (255) COMMENT '协议id',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-绑卡记录';

CREATE TABLE IF NOT EXISTS `pay_response_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id` (255) COMMENT '应用ID',
    `user_id` (255) COMMENT '用户id',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-响应记录';
