`unichain_pay`SET GLOBAL time_zone = '+8:00';

FLUSH PRIVILEGES;

SELECT NOW();


SHOW VARIABLES LIKE "%time_zone%";


USE unichain_app;
SELECT * FROM app LIMIT 10;

USE unichain_devops;
SELECT t1.`id`,t1.`name`,CONCAT("https://pkgs.source.tastelifer.com/share/",t1.`id`,".jpg") AS link FROM t1 JOIN t2 ON t1.`id`=t2.`id`;

SHOW CREATE TABLE promotion_statistics;



TABLE	CREATE TABLE
CREATE TABLE `flow_statistics_testsuperset` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `account_id` BIGINT(11) DEFAULT NULL COMMENT '账号编号',
  `account_phone` VARCHAR(12) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '账号电话',
  `ctime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` BIT(1) NOT NULL,
  `device_id` VARCHAR(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '设备ID',
  `device_typ` VARCHAR(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '设备类型',
  `ip` VARCHAR(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'Ip地址',
  `price` DECIMAL(11,2) DEFAULT NULL COMMENT '价格',
  `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品编号',
  `source` VARCHAR(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '渠道来源',
  `state` INT(3) DEFAULT NULL COMMENT '状态,0：显示(不扣量)，状态1:不显示（扣量）',
  `typ` INT(3) DEFAULT NULL COMMENT '统计类型1：点击，2：注册，3：下载，4:登陆',
  `utime` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `promotion_typ` VARCHAR(5) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '结算方式',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SELECT id,app_jar_path,app_prod_version FROM unichain_devops.`ops_apps` WHERE id = 5;
UPDATE unichain_devops.`ops_apps` SET app_jar_path = app_prod_version WHERE id = 5;