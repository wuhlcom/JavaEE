/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17 : Database - fish
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fish` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `fish`;

/*Table structure for table `t_beautiful_pictures` */

DROP TABLE IF EXISTS `t_beautiful_pictures`;

CREATE TABLE `t_beautiful_pictures` (
  `id` varchar(255) NOT NULL COMMENT '美女图片爬取',
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `pictureurls_num` int(11) DEFAULT NULL,
  `zan` int(11) DEFAULT NULL,
  `biaoqian` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `last_update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_beautiful_pictures` */

/*Table structure for table `t_group` */

DROP TABLE IF EXISTS `t_group`;

CREATE TABLE `t_group` (
  `ID` varchar(108) DEFAULT NULL,
  `NAME` varchar(150) DEFAULT NULL,
  `TYPE` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_group` */

insert  into `t_group`(`ID`,`NAME`,`TYPE`) values ('1','insert组角色','insert'),('2','delete组角色','delete'),('3','update组角色','update'),('4','select组角色','select');

/*Table structure for table `t_group_role` */

DROP TABLE IF EXISTS `t_group_role`;

CREATE TABLE `t_group_role` (
  `id` varchar(36) NOT NULL,
  `group_id` varchar(36) DEFAULT NULL,
  `role_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_group_role` */

insert  into `t_group_role`(`id`,`group_id`,`role_id`) values ('1','1','7'),('2','2','8'),('3','3','9'),('4','4','10');

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `ID` varchar(36) NOT NULL COMMENT '权限主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '请求URL',
  `URL` varchar(64) DEFAULT NULL COMMENT '请求描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_permission` */

insert  into `t_permission`(`ID`,`NAME`,`URL`) values ('1','用户列表页面','/user/queryAllUserPage'),('10','初始化权限列表页面数据','/initPermission/getInitPermissionListWithPager'),('11','用户组列表页面','/group/groupPage'),('12','用户组列表数据','/group/getGroupListWithPager'),('13','查询操作权限标识','query'),('14','添加操作权限标识','add'),('15','更新操作权限标识','update'),('16','删除操作权限标识','delete'),('2','用户列表数据','/user/queryAllUsers'),('3','角色列表页面','/role/rolePage'),('4','角色列表数据','/role/getRoleListWithPager'),('5','权限列表页面','/permission/permissionPage'),('6','权限列表数据','/permission/getPermissionListWithPager'),('7','在线用户列表页面','/onlineUser/onlineUserPage'),('8','在线用户列表数据','/onlineUser/onlineUsers'),('9','初始化权限列表页面','/initPermission/initPermissionPage');

/*Table structure for table `t_permission_init` */

DROP TABLE IF EXISTS `t_permission_init`;

CREATE TABLE `t_permission_init` (
  `id` varchar(36) NOT NULL,
  `url` varchar(250) DEFAULT NULL,
  `permission_init` varchar(500) DEFAULT NULL,
  `sort` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_permission_init` */

insert  into `t_permission_init`(`id`,`url`,`permission_init`,`sort`) values ('1','/static/**','anon,kickout',1),('10','/initPermission/**','perms[user:initPermission],kickout',9),('11','/permission/**','perms[user:permission],kickout',10),('12','/role/**','perms[user:role],kickout',11),('2','/ajaxLogin','anon,kickout',2),('3','/logout','logout,kickout',99),('4','/add','perms[权限添加:权限删除],roles[100002],kickout',6),('5','/**','user,kickout',100),('6','/getGifCode','anon,kickout',3),('7','/kickout','anon',4),('8','/user/**','perms[user:user],kickout',7),('9','/onlineUser/**','perms[user:onlineUser],kickout',8);

/*Table structure for table `t_picture` */

DROP TABLE IF EXISTS `t_picture`;

CREATE TABLE `t_picture` (
  `ID` varchar(36) NOT NULL,
  `pictures_id` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `last_update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_picture` */

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `ID` varchar(36) NOT NULL COMMENT '角色主键',
  `NAME` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`ID`,`NAME`,`TYPE`) values ('1','用户管理员','user'),('10','select组角色','select'),('2','角色管理员','role'),('3','权限管理员','permission'),('4','初始化权限管理员','initPermission'),('5','在线用户管理员','online'),('7','insert组角色','insert'),('8','delete组角色','delete'),('9','update组角色','update');

/*Table structure for table `t_role_permission` */

DROP TABLE IF EXISTS `t_role_permission`;

CREATE TABLE `t_role_permission` (
  `ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` varchar(36) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role_permission` */

insert  into `t_role_permission`(`ID`,`ROLE_ID`,`PERMISSION_ID`) values ('1','1','1'),('2','1','2'),('3','2','3'),('4','2','4'),('5','3','5'),('6','3','6'),('7','4','9'),('8','4','10'),('9','5','7'),('10','5','8'),('11','7','14'),('12','8','16'),('13','9','15'),('14','10','13');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `ID` varchar(36) NOT NULL COMMENT '主键ID',
  `NICKNAME` varchar(36) DEFAULT NULL COMMENT '用户昵称',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `PSWD` varchar(32) DEFAULT NULL COMMENT '用户密码',
  `SALT` varchar(6) DEFAULT NULL COMMENT '用户密码随机盐',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '用户创建时间',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '用户最后登录时间',
  `STATUS` bigint(1) DEFAULT NULL COMMENT '用户状态',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`ID`,`NICKNAME`,`EMAIL`,`PSWD`,`SALT`,`CREATE_TIME`,`LAST_LOGIN_TIME`,`STATUS`) values ('1','super','136214289@qq.com','e4573cce75edb68b7e1cded894a57e48','CDES','2017-03-24 19:20:06','2017-03-30 17:03:26',1),('2','userManager','136214289@qq.com','4907efde59641aa497c0b5714eea464e','CEDS','2017-03-27 12:12:54','2017-03-30 08:59:42',2),('3','roleManager','136214289@qq.com','b8e276e4ee91ed59f5255f9c727efc92','CEDS','2017-03-27 20:02:28','2017-03-28 19:52:38',1),('4','permissionManager','136214289@qq.com','089781d1d0dd2bc9e319da47e035e05b','CEDS','2017-03-27 20:02:33','2017-03-28 19:48:45',1),('5','initPermissionManager','136214289@qq.com','70b8bc25fb4d1ff5bbcf0a8c6c6066c9','CEDS','2017-03-27 20:04:30','2017-03-28 19:49:16',1),('6','onlineManager','136214289@qq.com','b2428915b345b8ce59d1f9c162c50507','CEDS','2017-03-27 20:04:30','2017-03-28 19:49:50',1);

/*Table structure for table `t_user_group` */

DROP TABLE IF EXISTS `t_user_group`;

CREATE TABLE `t_user_group` (
  `id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `group_id` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_group` */

insert  into `t_user_group`(`id`,`user_id`,`group_id`) values ('1','1','1'),('2','1','2'),('3','1','3'),('4','1','4'),('5','2','4'),('6','3','4'),('7','4','4'),('8','5','4'),('9','6','4');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`ID`,`USER_ID`,`ROLE_ID`) values ('1','1','1'),('2','1','2'),('3','1','3'),('4','1','4'),('5','1','5'),('6','2','1'),('7','3','2'),('8','4','3'),('9','5','4'),('10','6','5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
