/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.26 : Database - config_center
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`config_center` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `config_center`;

/*Table structure for table `application` */

DROP TABLE IF EXISTS `application`;

CREATE TABLE `application` (
  `name` varchar(45) NOT NULL COMMENT '应用程序名称，对应spring.appliction.name',
  `description` varchar(45) NOT NULL COMMENT '应用程序说明',
  PRIMARY KEY (`name`) USING BTREE,
  UNIQUE KEY `application_name_UNIQUE` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='应用表';

/*Table structure for table `application_config` */

DROP TABLE IF EXISTS `application_config`;

CREATE TABLE `application_config` (
  `profile` varchar(10) NOT NULL COMMENT 'spring.profiles.active,环境名称。关联profile.profile',
  `application_name` varchar(45) NOT NULL COMMENT 'spring.application.name',
  `property_source` text NOT NULL COMMENT '配置内容',
  `source_type` varchar(10) NOT NULL COMMENT '配置类型：PROPERTIES 或 YAML',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本，每修改一次版本号加1，并增加一条记录。',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`profile`,`application_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `application_config_log` */

DROP TABLE IF EXISTS `application_config_log`;

CREATE TABLE `application_config_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `profile` varchar(10) NOT NULL COMMENT 'spring.profiles.active环境名称。关联profile.profile',
  `application_name` varchar(45) NOT NULL COMMENT 'spring.application.name',
  `property_source` text NOT NULL COMMENT '配置内容',
  `source_type` varchar(10) NOT NULL COMMENT '配置类型：PROPERTIES 或 YAML',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本，每修改一次版本号加1，并增加一条记录。',
  `memo` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `application_name_profile` (`application_name`,`profile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `application_instance` */

DROP TABLE IF EXISTS `application_instance`;

CREATE TABLE `application_instance` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `application_name` varchar(45) NOT NULL COMMENT '应用名',
  `profile` varchar(10) NOT NULL COMMENT '环境',
  `ip` varchar(15) NOT NULL COMMENT '实例ip地址',
  `port` int(11) NOT NULL COMMENT '端口号',
  `manager_path` varchar(200) NOT NULL COMMENT 'manager端点路径',
  `username` varchar(45) DEFAULT NULL COMMENT '端点用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '端点密码',
  `created_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT NULL COMMENT '上次修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `application_profile_ip_port` (`application_name`,`profile`,`ip`,`port`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='应用实例信息表';

/*Table structure for table `global_config` */

DROP TABLE IF EXISTS `global_config`;

CREATE TABLE `global_config` (
  `profile` varchar(10) NOT NULL COMMENT '环境名称。关联profile.profile',
  `source_type` varchar(10) NOT NULL COMMENT '配置类型：PROPERTIES 或 YAML',
  `property_source` text NOT NULL COMMENT '配置信息',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本，每修改一次版本号加1，并添加新的一条记录',
  `memo` varchar(450) DEFAULT NULL COMMENT '备注说明',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `user_id` int(11) NOT NULL COMMENT '修改用户',
  PRIMARY KEY (`profile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='全局配置';

/*Table structure for table `global_config_log` */

DROP TABLE IF EXISTS `global_config_log`;

CREATE TABLE `global_config_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `profile` varchar(10) NOT NULL COMMENT '环境名称。关联profile.profile',
  `property_source` text NOT NULL COMMENT '配置信息',
  `source_type` varchar(10) NOT NULL COMMENT '配置类型：PROPERTIES 或 YAML',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本，每修改一次版本号加1，并添加新的一条记录',
  `memo` varchar(450) DEFAULT NULL COMMENT '备注说明',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据修改时间',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `profile` (`profile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='全局配置日志表';

/*Table structure for table `profile` */

DROP TABLE IF EXISTS `profile`;

CREATE TABLE `profile` (
  `profile` varchar(10) NOT NULL COMMENT '环境名称',
  `name` varchar(45) NOT NULL COMMENT '环境显示说明',
  PRIMARY KEY (`profile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='环境表';

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(45) NOT NULL COMMENT '登录名称',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `administrator` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否超级管理员，YES是超级管理员，NO不是超级管理员',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '状态VALID：有效；INVALID；无效',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_UNIQUE` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户';

/*Table structure for table `user_application_config_role` */

DROP TABLE IF EXISTS `user_application_config_role`;

CREATE TABLE `user_application_config_role` (
  `user_id` int(11) NOT NULL,
  `application_name` varchar(45) NOT NULL,
  `profile` varchar(10) NOT NULL COMMENT '环境名称。关联profile.profile',
  PRIMARY KEY (`user_id`,`application_name`,`profile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='应用配置权限';

/*Table structure for table `user_global_config_role` */

DROP TABLE IF EXISTS `user_global_config_role`;

CREATE TABLE `user_global_config_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `profile` varchar(10) NOT NULL COMMENT '环境名称。关联profile.profile',
  PRIMARY KEY (`user_id`,`profile`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='全局配置权限';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
