/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.26 : Database - unichain_app
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`unichain_app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;

USE `unichain_app`;

/*Table structure for table `app_agreement` */

DROP TABLE IF EXISTS `app_agreement`;

CREATE TABLE `app_agreement` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '协议键',
  `url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '协议地址',
  `app_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'app标识',
  `descr` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述文档',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Table structure for table `app_config` */

DROP TABLE IF EXISTS `app_config`;

CREATE TABLE `app_config` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `key` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置的键',
  `val` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '值',
  `desc` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置描述',
  `app_code` varchar(30) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'app标识',
  `version` tinyint(2) DEFAULT NULL COMMENT '版本号',
  `sort` tinyint(3) DEFAULT '1' COMMENT '排序',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Table structure for table `app_opinion` */

DROP TABLE IF EXISTS `app_opinion`;

CREATE TABLE `app_opinion` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) DEFAULT NULL COMMENT '账号编号',
  `phone` varchar(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
  `content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '反馈内容',
  `imgs` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '反馈图片集合  逗号隔开',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Table structure for table `app_question` */

DROP TABLE IF EXISTS `app_question`;

CREATE TABLE `app_question` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '问题',
  `val` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '答案',
  `app_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'app标识',
  `sort` tinyint(3) DEFAULT '1' COMMENT '排序',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*Table structure for table `app_update` */

DROP TABLE IF EXISTS `app_update`;

CREATE TABLE `app_update` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `is_force` tinyint(1) DEFAULT NULL COMMENT '是否强更   1 强更  0否',
  `update_text` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改内容',
  `down_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '下载url',
  `version` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '版本号',
  `size` decimal(10,0) DEFAULT NULL COMMENT '安装包大小',
  `app_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '应用标识',
  `app_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'app类型',
  `disable` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
