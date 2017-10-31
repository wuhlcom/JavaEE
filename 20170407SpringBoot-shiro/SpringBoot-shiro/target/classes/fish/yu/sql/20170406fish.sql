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

insert  into `t_group`(`ID`,`NAME`,`TYPE`) values ('1','insert组角色','insert'),('2','delete组角色','delete'),('3','update组角色','update'),('4','select组角色','select'),('a33e2ec45ab4478cb4729ffe7f64dec4','g1name','g1type'),('6efcff8c5f6c4cbdac720af4116095fe','edit组角色 ','edit');

/*Table structure for table `t_group_role` */

DROP TABLE IF EXISTS `t_group_role`;

CREATE TABLE `t_group_role` (
  `id` varchar(36) NOT NULL,
  `group_id` varchar(36) DEFAULT NULL,
  `role_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_group_role` */

insert  into `t_group_role`(`id`,`group_id`,`role_id`) values ('1','1','7'),('2','2','8'),('25cf5687c3d54e33b8581194d50b8992','a33e2ec45ab4478cb4729ffe7f64dec4','9'),('2f820db5dcc84cc19b575dc9f50b1077','a33e2ec45ab4478cb4729ffe7f64dec4','8'),('3','3','9'),('3b9eb95924d248f3a4ae6e4057207532','6efcff8c5f6c4cbdac720af4116095fe','4bead02397d7453ab1fff444400d49f2'),('4','4','10'),('85dfddedd7b645018b30273d0537e4a7','a33e2ec45ab4478cb4729ffe7f64dec4','7');

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `ID` varchar(36) NOT NULL COMMENT '权限主键',
  `NAME` varchar(256) DEFAULT NULL COMMENT '请求URL',
  `URL` varchar(64) DEFAULT NULL COMMENT '请求描述',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_permission` */

insert  into `t_permission`(`ID`,`NAME`,`URL`) values ('1','用户列表页面','/user/queryAllUserPage'),('10','初始化权限列表页面数据','/initPermission/getInitPermissionListWithPager'),('11','用户组列表页面','/group/groupPage'),('12','用户组列表数据','/group/getGroupListWithPager'),('13','查询操作权限标识','query'),('14','添加操作权限标识','add'),('15','更新操作权限标识','update'),('16','删除操作权限标识','delete'),('2','用户列表数据','/user/queryAllUsers'),('3','角色列表页面','/role/rolePage'),('3337dabb7b15425c9ea85a23cfe3ca40','编辑操作权限标识','edit'),('4','角色列表数据','/role/getRoleListWithPager'),('5','权限列表页面','/permission/permissionPage'),('6','权限列表数据','/permission/getPermissionListWithPager'),('7','在线用户列表页面','/onlineUser/onlineUserPage'),('8','在线用户列表数据','/onlineUser/onlineUsers'),('9','初始化权限列表页面','/initPermission/initPermissionPage'),('e2dd34d661794a1d8fcd75ee4922e30d','test1','testqq');

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
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NAME` (`NAME`,`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`ID`,`NAME`,`TYPE`) values ('8','delete组角色','delete'),('4bead02397d7453ab1fff444400d49f2','edit组角色','edit'),('7','insert组角色','insert'),('10','select组角色','select'),('38d5f2c208d44e0ba007b502e8d16caf','test','tupe'),('9','update组角色','update'),('4','初始化权限管理员','initPermission'),('5','在线用户管理员','online'),('3','权限管理员','permission'),('1','用户管理员','user'),('2','角色管理员','role');

/*Table structure for table `t_role_permission` */

DROP TABLE IF EXISTS `t_role_permission`;

CREATE TABLE `t_role_permission` (
  `ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `PERMISSION_ID` varchar(36) NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role_permission` */

insert  into `t_role_permission`(`ID`,`ROLE_ID`,`PERMISSION_ID`) values ('1','1','1'),('2','1','2'),('3','2','3'),('4','2','4'),('5','3','5'),('6','3','6'),('7','4','9'),('8','4','10'),('9','5','7'),('10','5','8'),('11','7','14'),('12','8','16'),('13','9','15'),('14','10','13'),('8f3e377f884b40a0b5c27a21d7a93e82','38d5f2c208d44e0ba007b502e8d16caf','16'),('cc15b0e6c99a4f75905394272ad2f2e6','4bead02397d7453ab1fff444400d49f2','3337dabb7b15425c9ea85a23cfe3ca40');

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

insert  into `t_user`(`ID`,`NICKNAME`,`EMAIL`,`PSWD`,`SALT`,`CREATE_TIME`,`LAST_LOGIN_TIME`,`STATUS`) values ('1','super','136214289@qq.com','d830470fea6cfc2d1e1d633486b68249','PDU7','2017-03-24 19:20:06','2017-04-06 18:17:03',1),('2','userManager','136214289@qq.com','de4db1723e0b9942c3ff15bd5ff31e18','Y9I2','2017-03-27 12:12:54','2017-04-06 17:10:25',1),('3','roleManager','136214289@qq.com','1a19b6299045af1493887d602ecd9df2','NQKI','2017-03-27 20:02:28','2017-04-06 15:11:39',1),('4','permissionManager','136214289@qq.com','3a53598f57c884c2da72b582ecde6c71','7P94','2017-03-27 20:02:33','2017-04-06 14:40:49',1),('41f0adf2e3584e9ca0627e2d62323ca0','test','123456@qq.com','7cc6a50dcc625831324c9c44392cbd29','YPGL',NULL,NULL,1),('5','initPermissionManager','136214289@qq.com','70b8bc25fb4d1ff5bbcf0a8c6c6066c9','CEDS','2017-03-27 20:04:30','2017-03-28 19:49:16',1),('6','onlineManager','136214289@qq.com','b2428915b345b8ce59d1f9c162c50507','CEDS','2017-03-27 20:04:30','2017-03-28 19:49:50',1);

/*Table structure for table `t_user_group` */

DROP TABLE IF EXISTS `t_user_group`;

CREATE TABLE `t_user_group` (
  `id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  `group_id` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_group` */

insert  into `t_user_group`(`id`,`user_id`,`group_id`) values ('8','5','4'),('9','6','4'),('e97374985d5f47988ade21d875c5923d','41f0adf2e3584e9ca0627e2d62323ca0','4'),('943661f7418145bc91fd6d84aefb7f2f','41f0adf2e3584e9ca0627e2d62323ca0','6efcff8c5f6c4cbdac720af4116095fe'),('ba5e2c7bfe2d442f93b82995a141937f','1','3'),('4df4ef5dd32a4fc5a8dac23275cc8586','1','6efcff8c5f6c4cbdac720af4116095fe'),('266aca27d2724e4296a16b5cbf6f3125','1','2'),('13aaf816a6db46f59a4b337cc1e724b4','1','1'),('10','1','4'),('dd908ad19fcb4974a8a05740ff80030c','2','4'),('bc3b3c36b0ad40ecb75a0ff922d0badd','2','6efcff8c5f6c4cbdac720af4116095fe'),('727995d8072c40b18c77914477ec3ab2','4','4'),('abfb737530424d568d23cbfabe746fe3','4','6efcff8c5f6c4cbdac720af4116095fe'),('c7c8f5d0fde345318fa7c21b47203c72','3','6efcff8c5f6c4cbdac720af4116095fe');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`ID`,`USER_ID`,`ROLE_ID`) values ('9','5','4'),('10','6','5'),('597ccbaff04e4815add4c66341d3bf70','41f0adf2e3584e9ca0627e2d62323ca0','1'),('24988cb03bbc44a1b1429d59302607ff','1','2'),('7ea4bcbc9e9342f9ac2f986e7e5e2031','1','3'),('262ffdb5b888423eb8a917db5cedea11','1','4'),('0a049978394649d28d9aa099240e4b45','1','1'),('96d4f33c4f894713a655cc33a7d9edfb','1','5'),('11','1','10'),('77fbb07de0e44c15bcee84b7a97e2946','2','2'),('57f5d5dc0036489d8336c7572a57fbe0','2','1'),('a75930f9807a4b59b71ca4286871c3a1','4','2'),('473edda5af114fff87f397a0b643a258','3','2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
