/*
 Navicat Premium Data Transfer

 Source Server         : local_dbs
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : flow_db

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 06/11/2019 10:59:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bus_apply_order
-- ----------------------------
DROP TABLE IF EXISTS `bus_apply_order`;
CREATE TABLE `bus_apply_order` (
  `apply_order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `apply_person_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '申请人名称',
  `money` int(10) DEFAULT NULL COMMENT '报销金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `process_instance_id` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '流程实例ID',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`apply_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='报销流程表';

-- ----------------------------
-- Records of bus_apply_order
-- ----------------------------
BEGIN;
INSERT INTO `bus_apply_order` VALUES (1, 'liqi', 111, '2019-11-06 10:42:41', '1a64e252-003f-11ea-98b4-9e902035f60e', NULL);
INSERT INTO `bus_apply_order` VALUES (2, 'chenfeifei', 111, '2019-11-06 10:50:31', '32195800-0040-11ea-b8b5-9e902035f60e', NULL);
INSERT INTO `bus_apply_order` VALUES (3, 'chenfeifei', 999, '2019-11-06 10:51:10', '49cf9878-0040-11ea-b8b5-9e902035f60e', NULL);
COMMIT;

-- ----------------------------
-- Table structure for bus_department
-- ----------------------------
DROP TABLE IF EXISTS `bus_department`;
CREATE TABLE `bus_department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(30) DEFAULT NULL COMMENT '部门名称',
  `department_code` varchar(30) DEFAULT NULL COMMENT '部门code',
  `remark` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`department_id`) USING BTREE,
  UNIQUE KEY `parcode_index` (`department_code`) USING BTREE COMMENT '部门code索引'
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';

-- ----------------------------
-- Records of bus_department
-- ----------------------------
BEGIN;
INSERT INTO `bus_department` VALUES (1, '总裁办', 'boss', '总裁团队', '2019-11-06 10:35:14');
INSERT INTO `bus_department` VALUES (2, 'odc业务处', 'odc', '内部研发团队', '2019-11-06 10:37:15');
COMMIT;

-- ----------------------------
-- Table structure for bus_person
-- ----------------------------
DROP TABLE IF EXISTS `bus_person`;
CREATE TABLE `bus_person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `person_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `role` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `department_id` int(11) DEFAULT NULL COMMENT '部门Id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`person_id`) USING BTREE,
  UNIQUE KEY `personNameIndex` (`person_name`) USING BTREE COMMENT '人员名称索引'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='人员表';

-- ----------------------------
-- Records of bus_person
-- ----------------------------
BEGIN;
INSERT INTO `bus_person` VALUES (1, 'liqi', 'slave', 30, 2, '2019-11-06 10:37:55', 'java开发人员');
INSERT INTO `bus_person` VALUES (2, 'gaoshang', 'slave', 33, 2, '2019-11-06 10:39:53', 'java开发人员');
INSERT INTO `bus_person` VALUES (3, 'chenfeifei', 'slave', 33, 2, '2019-11-06 10:39:59', 'java开发人员');
INSERT INTO `bus_person` VALUES (4, 'weixinyongs', 'master', 33, 2, '2019-11-06 10:40:35', '项目经理');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
