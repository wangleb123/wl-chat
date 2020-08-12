# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: exam.nb01.xyz (MySQL 5.6.44)
# Database: netty_chat
# Generation Time: 2019-07-17 02:36:54 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table groups
# ------------------------------------------------------------

DROP TABLE IF EXISTS `groups`;

CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(64) DEFAULT NULL COMMENT '群创建人',
  `group_name` varchar(64) DEFAULT NULL COMMENT '群名称',
  `group_note` varchar(64) DEFAULT NULL COMMENT '群公告',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `state` int(4) DEFAULT NULL COMMENT '删除标志位',
  `group_avatar_url` varchar(64) DEFAULT NULL COMMENT '群头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;

INSERT INTO `groups` (`id`, `create_date`, `create_user`, `group_name`, `group_note`, `modify_date`, `state`, `group_avatar_url`)
VALUES
	(1,'2019-07-13 15:53:09','2019-07-13 15:53:09','尚德netty交流群','欢迎各位同学加入','2019-07-13 15:53:09',10,'https://www.sunlands.com/img/rqCode_app.png');

/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `gender` int(4) DEFAULT NULL COMMENT '用户性别',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `user_name` varchar(45) DEFAULT NULL COMMENT '用户昵称',
  `pass_word` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `phone_number` varchar(64) DEFAULT NULL COMMENT '用户手机号',
  `state` int(5) DEFAULT NULL COMMENT '删除标志位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `create_date`, `gender`, `avatar_url`, `modify_date`, `user_name`, `pass_word`, `phone_number`, `state`)
VALUES
	(2,'2019-07-13 15:53:09',1,'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIyGhWqGJqgacwx7kuox5KicHUYWn07q7tZBgibxL3C4yUOovmUrDMAXdc2Je4aib9uc37sXgAGyBHQg/132','2019-07-13 15:53:09','冉野','123','18987673673',10),
	(3,'2019-07-13 15:53:09',1,'https://r.exam.sunlands.site/resources/head_images/exam/195.jpg','2019-07-13 15:53:09','汀落','123','17521531993',10);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_chat_relation
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_chat_relation`;

CREATE TABLE `-` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `friends_id` bigint(19) DEFAULT NULL COMMENT '好友id',
  `group_id` bigint(19) DEFAULT NULL COMMENT '群组id',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `remarks` varchar(45) DEFAULT NULL COMMENT '群组或者好友备注',
  `state` int(4) DEFAULT NULL COMMENT '删除标志位',
  `user_id` bigint(19) DEFAULT NULL COMMENT '个人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `user_chat_relation` WRITE;
/*!40000 ALTER TABLE `user_chat_relation` DISABLE KEYS */;

INSERT INTO `user_chat_relation` (`id`, `create_date`, `friends_id`, `group_id`, `modify_date`, `remarks`, `state`, `user_id`)
VALUES
	(1,'2019-07-13 15:53:09',3,NULL,'2019-07-13 15:53:09','汀落师兄',10,2),
	(2,'2019-07-13 15:53:09',2,NULL,'2019-07-13 15:53:09','冉野师弟',10,3),
	(3,'2019-07-13 15:53:09',NULL,1,'2019-07-13 15:53:09','netty交流群',10,2),
	(4,'2019-07-13 15:53:09',NULL,1,'2019-07-13 15:53:09','netty交流群',10,3);

/*!40000 ALTER TABLE `user_chat_relation` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
