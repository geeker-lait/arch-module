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

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`password`,`email`,`administrator`,`status`,`created_date`) values (2,'admin','0a758ecb99c12e3019b31828f5b25967','admin@xxx.com','YES','VALID','2019-01-05 14:20:49'),(3,'zhangpeng','da911bdfd7a5f959f6b47b178d541745','1058110228@qq.com','YES','VALID','2019-01-05 18:08:40'),(4,'zhujunjie','0f7cc5b6cbf07d9f80fe22e0d7390eeb','18051578267@163.com','YES','VALID','2019-01-05 18:08:36'),(5,'gongshaojie','0f7cc5b6cbf07d9f80fe22e0d7390eeb','gongshaojie@qq.com','YES','VALID','2019-01-05 18:08:32');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
