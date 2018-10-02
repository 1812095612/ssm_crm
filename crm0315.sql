/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.51 : Database - crm0315
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`crm0315` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `crm0315`;

/*Table structure for table `authorities` */

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permissions` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_authority_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sdu0y4lwsyd7j783aymbda6g4` (`parent_authority_id`),
  CONSTRAINT `FK_sdu0y4lwsyd7j783aymbda6g4` FOREIGN KEY (`parent_authority_id`) REFERENCES `authorities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `authorities` */

insert  into `authorities`(`id`,`display_name`,`name`,`permissions`,`url`,`parent_authority_id`) values (15,'营销管理',NULL,NULL,'/',NULL),(16,'营销机会管理','chance',NULL,'/chance/list',15),(17,'客户开发计划','plan',NULL,'/plan/list',15),(18,'客户管理',NULL,NULL,'/',NULL),(19,'客户信息管理','customer',NULL,'/customer/list',18),(20,'客户流失管理','drain',NULL,'/drain/list',18),(21,'服务管理',NULL,NULL,'/',NULL),(22,'服务创建','service-create',NULL,'/service/create',21),(23,'服务分配','service-allot',NULL,'/service/allot/list',21),(24,'服务处理','service-deal',NULL,'/service/deal/list',21),(25,'服务反馈','service-feedback',NULL,'/service/feedback/list',21),(26,'服务归档','service-archive',NULL,'/service/archive/list',21),(27,'统计报表',NULL,NULL,'/',NULL),(28,'客户贡献分析','report-pay',NULL,'/report/pay',27),(29,'客户构成分析','report-consist',NULL,'/report/consist?type=search_level',27),(30,'客户服务分析','report-service',NULL,'/report/service',27),(31,'客户流失分析','report-drain',NULL,'/report/drain',27),(32,'基础数据',NULL,NULL,'/',NULL),(33,'数据字典管理','dict',NULL,'/dict/list',32),(34,'查询产品信息','product',NULL,'/product/list',32),(35,'查询库存信息','storage',NULL,'/storage/list',32),(36,'系统权限管理',NULL,NULL,'/',NULL),(37,'系统用户管理','user',NULL,'/user/list',36),(39,'角色管理','role',NULL,'/role/list',36);

/*Table structure for table `contacts` */

DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `memo` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j0uturnru83j56ufv1cyq02xl` (`customer_id`),
  CONSTRAINT `FK_j0uturnru83j56ufv1cyq02xl` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `contacts` */

/*Table structure for table `customer_activities` */

DROP TABLE IF EXISTS `customer_activities`;

CREATE TABLE `customer_activities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bg3w9v3b53u7nav7nvfu79y4p` (`customer_id`),
  CONSTRAINT `FK_bg3w9v3b53u7nav7nvfu79y4p` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_activities` */

/*Table structure for table `customer_drains` */

DROP TABLE IF EXISTS `customer_drains`;

CREATE TABLE `customer_drains` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delay` varchar(255) DEFAULT NULL,
  `drain_date` datetime DEFAULT NULL,
  `last_order_date` datetime DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_jvieiqnrgrujknxtrv0rmr7ta` (`customer_id`),
  CONSTRAINT `FK_jvieiqnrgrujknxtrv0rmr7ta` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_drains` */

/*Table structure for table `customer_services` */

DROP TABLE IF EXISTS `customer_services`;

CREATE TABLE `customer_services` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `allot_date` date DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `deal_date` date DEFAULT NULL,
  `deal_result` varchar(255) DEFAULT NULL,
  `satisfy` varchar(255) DEFAULT NULL,
  `service_deal` varchar(255) DEFAULT NULL,
  `service_request` varchar(255) DEFAULT NULL,
  `service_state` varchar(255) DEFAULT NULL,
  `service_title` varchar(255) DEFAULT NULL,
  `service_type` varchar(255) DEFAULT NULL,
  `allot_id` bigint(20) DEFAULT NULL,
  `created_id` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i20kn2qk74hgiinv2e7mb30xf` (`allot_id`),
  KEY `FK_ajfs11oxygp0on3joqe8a1t08` (`created_id`),
  KEY `FK_gxsr8weei1a8a45jk0dsp38jf` (`customer_id`),
  CONSTRAINT `FK_ajfs11oxygp0on3joqe8a1t08` FOREIGN KEY (`created_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_gxsr8weei1a8a45jk0dsp38jf` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
  CONSTRAINT `FK_i20kn2qk74hgiinv2e7mb30xf` FOREIGN KEY (`allot_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer_services` */

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `bank_account` varchar(255) DEFAULT NULL,
  `bankroll` bigint(20) DEFAULT NULL,
  `chief` varchar(255) DEFAULT NULL,
  `credit` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `customer_level` varchar(255) DEFAULT NULL,
  `licence_no` varchar(255) DEFAULT NULL,
  `local_tax_no` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `national_tax_no` varchar(255) DEFAULT NULL,
  `no` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `satify` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `turnover` bigint(20) DEFAULT NULL,
  `websit` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `manager_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rgpem82qdqautsqfmwtwyjp06` (`manager_id`),
  CONSTRAINT `FK_rgpem82qdqautsqfmwtwyjp06` FOREIGN KEY (`manager_id`) REFERENCES `contacts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `customers` */

insert  into `customers`(`id`,`address`,`bank`,`bank_account`,`bankroll`,`chief`,`credit`,`fax`,`customer_level`,`licence_no`,`local_tax_no`,`name`,`national_tax_no`,`no`,`region`,`satify`,`state`,`tel`,`turnover`,`websit`,`zip`,`manager_id`) values (5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'北京培黎师范学院',NULL,'9a94dfcb-38b0-4e79-ae96-08f19da9f0f1',NULL,NULL,'删除',NULL,NULL,NULL,NULL,NULL),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'新浪',NULL,'ff704114-40cc-41c3-b30a-853f322eae9e',NULL,NULL,'删除',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `dicts` */

DROP TABLE IF EXISTS `dicts`;

CREATE TABLE `dicts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `editable` tinyint(1) NOT NULL,
  `item` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `dicts` */

insert  into `dicts`(`id`,`editable`,`item`,`type`,`value`) values (8,0,'☆','满意度',NULL),(9,0,'☆☆','满意度',NULL),(10,0,'☆☆☆','满意度',NULL),(11,0,'☆☆☆☆','满意度',NULL),(12,0,'☆☆☆☆☆','满意度',NULL),(13,0,'☆','信用度',NULL),(14,0,'☆☆','信用度',NULL),(15,0,'☆☆☆','信用度',NULL),(16,0,'☆☆☆☆','信用度',NULL),(17,0,'☆☆☆☆☆','信用度',NULL),(18,0,'北京','地区',NULL),(19,0,'上海','地区',NULL),(20,0,'广州','地区',NULL),(21,0,'深圳','地区',NULL),(22,0,'香港','地区',NULL),(23,0,'普通客户','客户等级',NULL),(24,0,'大客户','客户等级',NULL),(25,0,'重点开发客户','客户等级',NULL),(26,0,'合作伙伴','客户等级',NULL),(27,0,'战略合作伙伴','客户等级',NULL),(28,0,'辽宁','地区',NULL),(29,0,'投诉','服务类型',NULL),(30,0,'建议','服务类型',NULL),(31,0,'咨询','服务类型',NULL);

/*Table structure for table `order_items` */

DROP TABLE IF EXISTS `order_items`;

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_count` bigint(20) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9gap2fmw66v092ntb58rtohwh` (`order_id`),
  KEY `FK_3fea23hxar30bx7m7h8ed25n9` (`product_id`),
  CONSTRAINT `FK_3fea23hxar30bx7m7h8ed25n9` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FK_9gap2fmw66v092ntb58rtohwh` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_items` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `no` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_astys1dv61mdlp0n0wx0574r2` (`customer_id`),
  CONSTRAINT `FK_astys1dv61mdlp0n0wx0574r2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `products` */

insert  into `products`(`id`,`batch`,`memo`,`name`,`price`,`type`,`unit`) values (1,'国行','保证行货','ThinkPad T430 笔记本',8000,'T430','台'),(3,'水货','不保修','Nexus 手机',2000,'Nexus5','台'),(4,'二手',NULL,'ipad',1000,'2','台'),(5,'32G',NULL,'小米手机',1500,'2s','台'),(6,'金色',NULL,'iphone',5000,'5s','台'),(7,'32G',NULL,'三星NOTE',3000,'3','台');

/*Table structure for table `role_authority` */

DROP TABLE IF EXISTS `role_authority`;

CREATE TABLE `role_authority` (
  `role_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  KEY `FK_17v3u9qbwajrm5jsaj4uxujx8` (`authority_id`),
  KEY `FK_pd9dxrh00ojci21l1h9pj772` (`role_id`),
  CONSTRAINT `FK_17v3u9qbwajrm5jsaj4uxujx8` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`),
  CONSTRAINT `FK_pd9dxrh00ojci21l1h9pj772` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_authority` */

insert  into `role_authority`(`role_id`,`authority_id`) values (1,16),(1,17),(1,19),(1,20),(1,22),(1,23),(1,24),(1,25),(1,26),(1,28),(1,29),(1,30),(1,31),(1,33),(1,34),(1,35),(1,37),(1,39),(2,17),(3,16),(3,17),(3,19),(3,20),(3,22),(3,23),(3,24),(3,25),(3,26),(3,28),(3,29),(3,30),(3,31),(3,33),(3,34),(3,35),(3,37),(3,39);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`id`,`description`,`enabled`,`name`) values (1,NULL,1,'管理员'),(2,NULL,1,'测试'),(3,'测试时使用, 上线需删除',1,'测试管理员');

/*Table structure for table `sales_chances` */

DROP TABLE IF EXISTS `sales_chances`;

CREATE TABLE `sales_chances` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact` varchar(255) DEFAULT NULL,
  `contact_tel` varchar(255) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `cust_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `designee_date` date DEFAULT NULL,
  `rate` bigint(20) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `created_user_id` bigint(20) DEFAULT NULL,
  `designee_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ftkycv9d4nvmu2c2w2fw31kno` (`created_user_id`),
  KEY `FK_hqecyrmh65uwk6dc2nm2holcg` (`designee_id`),
  CONSTRAINT `FK_ftkycv9d4nvmu2c2w2fw31kno` FOREIGN KEY (`created_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_hqecyrmh65uwk6dc2nm2holcg` FOREIGN KEY (`designee_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sales_chances` */

/*Table structure for table `sales_plan` */

DROP TABLE IF EXISTS `sales_plan`;

CREATE TABLE `sales_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_date` date DEFAULT NULL,
  `plan_result` varchar(255) DEFAULT NULL,
  `todo` varchar(255) DEFAULT NULL,
  `chance_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fyp32ddrxhrwk4k6qlbgyb03k` (`chance_id`),
  CONSTRAINT `FK_fyp32ddrxhrwk4k6qlbgyb03k` FOREIGN KEY (`chance_id`) REFERENCES `sales_chances` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sales_plan` */

/*Table structure for table `storages` */

DROP TABLE IF EXISTS `storages`;

CREATE TABLE `storages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `memo` varchar(255) DEFAULT NULL,
  `stock_count` int(11) NOT NULL,
  `stock_ware` varchar(255) DEFAULT NULL,
  `ware_house` varchar(255) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ga5f9d7rvvwkjyah6p02e69n0` (`product_id`),
  CONSTRAINT `FK_ga5f9d7rvvwkjyah6p02e69n0` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `storages` */

insert  into `storages`(`id`,`memo`,`stock_count`,`stock_ware`,`ware_house`,`product_id`) values (1,'Nexus 手机比较抢手',100,'1','北京五棵松',3),(2,NULL,2000,'2','中关村海龙',1),(3,NULL,200,'3','京东一号',3),(4,NULL,100,'4','e世界',4),(5,NULL,200,'10','北京五棵松',1),(6,NULL,100,'11','北京五棵松',3),(7,NULL,100,'13','北京五棵松',4),(8,NULL,100,'14','北京五棵松',5),(9,NULL,100,'15','北京五棵松',6),(10,NULL,100,'16','北京五棵松',7);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enabled` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_krvotbtiqhudlkamvlpaqus0t` (`role_id`),
  CONSTRAINT `FK_krvotbtiqhudlkamvlpaqus0t` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`enabled`,`name`,`password`,`salt`,`role_id`) values (21,1,'bcde','4f6ed9e4ab25a6dac05933a8a0c5822ada8177e5','e2b87e6eced06509',1),(22,1,'abcd','f7e480709b119c14621301576eb572ee009a47ce','db314a8d91bd6f83',2),(24,1,'a','9bba13aaeb55b59ce72f9f6aad672e2c32544630','ceadfd47cdaa814c',3);

/* Procedure structure for procedure `check_drain` */

/*!50003 DROP PROCEDURE IF EXISTS  `check_drain` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `check_drain`()
BEGIN
DECLARE done BOOLEAN;
DECLARE str1 VARCHAR(50);
DECLARE str2 VARCHAR(50);
DECLARE drain_cursor CURSOR
FOR 
	SELECT c.id, o.order_date
	FROM customers c
	JOIN (SELECT customer_id, MAX(order_date) order_date
		FROM orders
		GROUP BY customer_id) o
	ON c.id = o.customer_id
	WHERE o.order_date < DATE_ADD(NOW(),INTERVAL -6 MONTH)
	AND c.state = '正常';
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;
OPEN drain_cursor;
REPEAT
FETCH drain_cursor INTO str1,str2;
	INSERT INTO customer_drains(id, customer_id, last_order_date, STATUS) 
	VALUES(NULL, str1, str2, '流失预警');
	UPDATE customers SET state = '流失预警' WHERE id = str1;
UNTIL done END REPEAT;
CLOSE drain_cursor;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
