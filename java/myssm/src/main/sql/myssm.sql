
CREATE DATABASE IF NOT EXISTS myssm DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE myssm;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for days_user
-- ----------------------------
DROP TABLE IF EXISTS `days_user`;
CREATE TABLE `days_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` int(11) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of days_user
-- ----------------------------
