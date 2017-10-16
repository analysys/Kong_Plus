/*
Navicat MySQL Data Transfer

Source Server         : 192.168.220.167
Source Server Version : 50635
Source Host           : 192.168.220.167:3306
Source Database       : api_gateway

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-10-16 13:57:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for api_acls
-- ----------------------------
DROP TABLE IF EXISTS `api_acls`;
CREATE TABLE `api_acls` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `api_id` varchar(100) DEFAULT NULL COMMENT 'API id',
  `consumer_id` varchar(100) DEFAULT NULL COMMENT '消费者ID',
  `remarks` varchar(200) DEFAULT NULL COMMENT '描述',
  `sys_type` tinyint(1) DEFAULT NULL,
  `max_callcount` varchar(200) DEFAULT ',,,,,' COMMENT '最大调用次数',
  `max_callcount_ok` varchar(200) DEFAULT ',,,,,' COMMENT '最大调用次数(成功)',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `extend` varchar(100) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_define
-- ----------------------------
DROP TABLE IF EXISTS `api_define`;
CREATE TABLE `api_define` (
  `id` varchar(100) NOT NULL COMMENT 'api id',
  `name` varchar(100) DEFAULT NULL COMMENT 'api名称',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `visit_url` varchar(300) DEFAULT NULL COMMENT '访问地址',
  `req_host` varchar(100) DEFAULT NULL COMMENT 'DNS标识',
  `req_path` varchar(100) DEFAULT NULL COMMENT '路径访问标识',
  `auth_type` varchar(30) DEFAULT NULL COMMENT '认证类型',
  `remarks` varchar(300) DEFAULT NULL COMMENT '描述',
  `acl_whitelist` varchar(100) DEFAULT NULL COMMENT 'ACL白名单',
  `ip_whitelist` varchar(100) DEFAULT NULL COMMENT 'IP地址黑名单',
  `log_file` varchar(100) DEFAULT NULL COMMENT '日志路径',
  `call_total` int(11) DEFAULT '0' COMMENT '调用次数',
  `call_ok` int(11) DEFAULT '0' COMMENT '调用次数(成功)',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `extend` varchar(100) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_logs
-- ----------------------------
DROP TABLE IF EXISTS `api_logs`;
CREATE TABLE `api_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'api id',
  `started_at` varchar(100) DEFAULT NULL COMMENT 'api名称',
  `response_size` varchar(8) DEFAULT NULL COMMENT '用户id',
  `response_status` varchar(8) DEFAULT NULL COMMENT '访问地址',
  `request_method` varchar(8) DEFAULT NULL COMMENT 'DNS标识',
  `request_uri` varchar(200) DEFAULT NULL COMMENT '路径访问标识',
  `request_size` varchar(8) DEFAULT NULL COMMENT '认证类型',
  `request_real_uri` varchar(300) DEFAULT NULL COMMENT '描述',
  `x_consumer_id` varchar(100) DEFAULT NULL COMMENT 'ACL白名单',
  `x_forwarded_for` varchar(100) DEFAULT NULL COMMENT 'IP地址黑名单',
  `x_real_ip` varchar(20) DEFAULT NULL COMMENT '日志路径',
  `x_consumer_username` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `client_ip` varchar(100) DEFAULT NULL COMMENT '扩展字段',
  `api_id` varchar(100) DEFAULT NULL,
  `api_name` varchar(50) DEFAULT NULL,
  `latencies_kong` varchar(6) DEFAULT NULL,
  `latencies_request` varchar(6) DEFAULT NULL,
  `latencies_proxy` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81617 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_oauth2
-- ----------------------------
DROP TABLE IF EXISTS `api_oauth2`;
CREATE TABLE `api_oauth2` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `api_id` varchar(100) DEFAULT NULL COMMENT 'API id',
  `plugin_id` varchar(100) DEFAULT NULL COMMENT 'OAuth2插件ID',
  `scope` varchar(100) DEFAULT NULL COMMENT '作用域',
  `scope_need` tinyint(1) DEFAULT '0' COMMENT '作用域是否必须: 0-非必须  1-必须',
  `expiration` int(8) DEFAULT NULL COMMENT 'Access_Token有效期',
  `grant_type` varchar(50) DEFAULT 'code' COMMENT '授权方式',
  `provision_key` varchar(100) DEFAULT NULL COMMENT 'provision_key',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `extend` varchar(800) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for api_users
-- ----------------------------
DROP TABLE IF EXISTS `api_users`;
CREATE TABLE `api_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `pwd` varchar(70) DEFAULT NULL COMMENT '用户密码(MD5)',
  `status` tinyint(1) DEFAULT '1' COMMENT '0:无效  1:有效',
  `sys_type` tinyint(1) DEFAULT '1' COMMENT '系统类型 0:Super 1:方舟',
  `user_role` tinyint(1) DEFAULT '1' COMMENT '角色类型 0:管理员',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `extend` varchar(100) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for consumers_define
-- ----------------------------
DROP TABLE IF EXISTS `consumers_define`;
CREATE TABLE `consumers_define` (
  `id` varchar(100) NOT NULL COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '消费者名称',
  `custom_id` varchar(100) DEFAULT NULL COMMENT '消费者ID',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `email` varchar(80) DEFAULT NULL COMMENT 'email地址',
  `remarks` varchar(200) DEFAULT NULL COMMENT '描述',
  `auth_type` varchar(30) DEFAULT NULL COMMENT '认证类型',
  `api_key` varchar(100) DEFAULT NULL COMMENT '访问Key值',
  `client_id` varchar(100) DEFAULT NULL COMMENT '客户端标识',
  `client_secret` varchar(100) DEFAULT NULL COMMENT '客户端口令',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `extend` varchar(100) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
