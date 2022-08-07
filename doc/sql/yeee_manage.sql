/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云68
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 127.0.0.1:3306
 Source Schema         : yeee_crowdfunding

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 11/08/2022 17:51:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
    `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_validity` int(0) NULL DEFAULT NULL,
    `refresh_token_validity` int(0) NULL DEFAULT NULL,
    `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '终端信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('yeee-crowdfunding', NULL, '96e79218965eb72c92a549dd5a330112', 'all', 'password,refresh_token,authorization_code,client_credentials', '', NULL, 864000, 6048000, NULL, 'true');

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------
-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------
-- ----------------------------
-- Table structure for sys_cat
-- ----------------------------
DROP TABLE IF EXISTS `sys_cat`;
CREATE TABLE `sys_cat`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '名称',
  `tid` int(0) NULL DEFAULT -1 COMMENT '顶层ID',
  `pid` int(0) NULL DEFAULT -1 COMMENT '父id',
  `status` int(0) NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `sort` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code`(`code`) USING BTREE,
  INDEX `idx_parent_id`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_cat
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门代码',
  `pid` int(0) NULL DEFAULT -1 COMMENT '父部门id',
  `ancestors` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '祖级列表',
  `status` int(0) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
  `sort` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code`(`code`) USING BTREE,
  INDEX `idx_parent_id`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 433 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (2, '客服组', 'KF', 1, '-1,1', 0, 2, NULL, '1', '2021-11-09 14:36:03', '1', '2021-12-14 11:20:14');
INSERT INTO `sys_dept` VALUES (4, '常规组', 'CG', 1, '-1,1', 0, 1, NULL, '1', '2021-12-03 10:17:25', '1', '2021-12-03 10:17:25');
INSERT INTO `sys_dept` VALUES (5, '核心组', 'HX', 1, '-1,1', 0, 2, NULL, '1', '2021-12-03 10:17:41', '1', '2021-12-03 10:17:41');
INSERT INTO `sys_dept` VALUES (6, '战略组', 'ZL', 1, '-1,1', 0, 3, NULL, '1', '2021-12-03 10:17:54', '1', '2021-12-03 10:17:54');
INSERT INTO `sys_dept` VALUES (7, '渠道组', 'QD', 1, '-1,1', 0, 4, NULL, '1', '2021-12-03 10:18:15', '1', '2021-12-03 10:18:15');
INSERT INTO `sys_dept` VALUES (430, '测试1', '11', 8, '-1,8', 0, 11, NULL, '1', '2021-12-30 21:51:01', '1', '2021-12-30 21:51:01');
INSERT INTO `sys_dept` VALUES (431, '技术部', 'JS', -1, '-1', 0, 1, NULL, '1', '2022-01-05 17:01:56', '1', '2022-01-05 17:01:56');
INSERT INTO `sys_dept` VALUES (432, '客户满意部', 'KHMYB', -1, '-1', 0, 5, NULL, '10280', '2022-02-15 11:46:46', '10280', '2022-02-15 11:46:46');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父ID',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源路径',
  `perm` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `seq` smallint(0) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '类型 (0：菜单， 1：功能， 2：伪菜单)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4353221119792138 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统权限资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059879121720320, NULL, '系统工具', '', NULL, 'icon-tubiao', 300, '', 0, NULL, NULL, '2019-02-13 23:26:39', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059881300399104, 4059879121720320, '任务调度', 'sched/qrtz/qrtz', '', 'icon-job', 10, 'view/sched/qrtz/qrtz.html', 0, NULL, NULL, '2019-02-11 15:45:49', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059891056399360, 4059889694397440, '全部任务', 'act/task/taskAll', NULL, 'icon-zhedie', 15, 'view/act/task/taskAll.html', 0, NULL, NULL, '2019-02-11 15:49:50', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059891515298816, 4059889694397440, '用户任务', 'act/task/task', NULL, 'icon-menu', 20, 'view/act/task/task.html', 0, NULL, NULL, '2019-02-11 15:49:59', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059916341400576, NULL, '系统管理', '', NULL, 'icon-system', 100, '', 0, NULL, NULL, '2019-02-13 23:24:22', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059916791092224, 4059916341400576, '用户管理', 'sys/user/user', NULL, 'icon-admin', 5, 'view/sys/user/user.html', 0, NULL, NULL, '2019-02-11 15:16:48', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059917154079744, 4059916341400576, '角色管理', 'sys/role/role', NULL, 'icon-geren', 10, 'view/sys/role/role.html', 0, NULL, NULL, '2019-02-11 15:42:24', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059917584143360, 4059916341400576, '资源管理', 'sys/rsco/rsco', NULL, 'icon-editor', 15, 'view/sys/rsco/rsco.html', 0, NULL, NULL, '2019-02-11 15:42:45', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059918640845824, 4323654351082496, '接口日志', 'sys/log/log', NULL, 'icon-log', 10, 'view/sys/log/log.html', 0, NULL, NULL, '2019-04-06 19:55:29', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059918640845825, 4323654351082499, '会员管理', 'cf/user/user', NULL, 'icon-log', 20, 'view/sys/log/log.html', 0, NULL, NULL, '2019-04-06 19:55:29', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059918640845826, 4323654351082499, '订单管理', 'cf/order/order', NULL, 'icon-log', 30, 'view/sys/log/log.html', 0, NULL, NULL, '2019-04-06 19:55:29', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4059919174194176, 4059916341400576, '数据字典', 'sys/cat/cat', NULL, 'icon-sousuo', 20, 'view/sys/cat/cat.html', 0, NULL, NULL, '2019-02-11 15:43:16', NULL);
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714113, 4059919174194176, '查看', NULL, 'sys:cat:page, sys:cat:list, sys:cat:info', NULL, 10, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714114, 4059919174194176, '新增', NULL, 'sys:cat:add', NULL, 20, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714115, 4059919174194176, '修改', NULL, 'sys:cat:upd', NULL, 30, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714116, 4059919174194176, '删除', NULL, 'sys:cat:del', NULL, 40, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714117, 4059919174194176, '导出', NULL, 'sys:cat:expt', NULL, 50, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714118, 4059919174194176, '导入', NULL, 'sys:cat:impt', NULL, 60, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179754714119, 4059919174194176, '审核', NULL, 'sys:cat:audit', NULL, 70, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179755844609, 4059918640845824, '查看', NULL, 'sys:log:page, sys:log:list, sys:log:info', NULL, 10, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179755844612, 4059918640845824, '删除', NULL, 'sys:log:del', NULL, 40, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179755844613, 4059918640845824, '导出', NULL, 'sys:log:expt', NULL, 50, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297179758302211, 4059919704003584, '修改', NULL, 'sys:param:upd', NULL, 30, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448577, 4059917154079744, '查看', NULL, 'sys:role:page, sys:role:list, sys:role:info', NULL, 10, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448578, 4059917154079744, '新增', NULL, 'sys:role:add', NULL, 20, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448579, 4059917154079744, '修改', NULL, 'sys:role:upd', NULL, 30, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448580, 4059917154079744, '删除', NULL, 'sys:role:del', NULL, 40, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448581, 4059917154079744, '导出', NULL, 'sys:role:expt', NULL, 50, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448582, 4059917154079744, '导入', NULL, 'sys:role:impt', NULL, 60, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735991448583, 4059917154079744, '审核', NULL, 'sys:role:audit', NULL, 70, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218625, 4059917584143360, '查看', NULL, 'sys:rsco:page, sys:rsco:list, sys:rsco:info', NULL, 10, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218626, 4059917584143360, '新增', NULL, 'sys:rsco:add', NULL, 20, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218627, 4059917584143360, '修改', NULL, 'sys:rsco:upd', NULL, 30, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218628, 4059917584143360, '删除', NULL, 'sys:rsco:del', NULL, 40, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218629, 4059917584143360, '导出', NULL, 'sys:rsco:expt', NULL, 50, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218630, 4059917584143360, '导入', NULL, 'sys:rsco:impt', NULL, 60, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735992218631, 4059917584143360, '审核', NULL, 'sys:rsco:audit', NULL, 70, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577345, 4059916791092224, '查看', NULL, 'sys:user:page, sys:user:list, sys:user:info', NULL, 10, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577346, 4059916791092224, '新增', NULL, 'sys:user:add', NULL, 20, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577347, 4059916791092224, '修改', NULL, 'sys:user:upd', NULL, 30, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577348, 4059916791092224, '删除', NULL, 'sys:user:del', NULL, 40, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577349, 4059916791092224, '导出', NULL, 'sys:user:expt', NULL, 50, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577350, 4059916791092224, '导入', NULL, 'sys:user:impt', NULL, 60, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4297735995577351, 4059916791092224, '审核', NULL, 'sys:user:audit', NULL, 70, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298067236262912, 4059916791092224, '修改密码', '', 'sys:user:updPwd', '', 80, '', 1, '2019-02-26 16:03:00', 'admin', '2019-02-26 16:03:00', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298102159430656, 4059881300399104, '查询', '', 'sched:qrtz:list, sched:qrtz:info', '', 10, '', 1, '2019-02-26 16:38:32', 'admin', '2019-02-26 16:44:05', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298102588576768, 4059881300399104, '新增', '', 'sched:qrtz:add', '', 20, '', 1, '2019-02-26 16:38:58', 'admin', '2019-02-26 16:38:58', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298102912996352, 4059881300399104, '修改', '', 'sched:qrtz:upd', '', 30, '', 1, '2019-02-26 16:39:18', 'admin', '2019-02-26 16:39:18', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298103224865792, 4059881300399104, '删除', '', 'sched:qrtz:del', '', 40, '', 1, '2019-02-26 16:39:37', 'admin', '2019-02-26 16:39:37', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298103662826496, 4059881300399104, '执行', '', 'sched:qrtz:exec', '', 50, '', 1, '2019-02-26 16:40:03', 'admin', '2019-02-26 16:40:03', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298104030680064, 4059881300399104, '暂停', '', 'sched:qrtz:pause', '', 60, '', 1, '2019-02-26 16:40:26', 'admin', '2019-02-26 16:40:26', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4298104367584256, 4059881300399104, '恢复', '', 'sched:qrtz:resume', '', 70, '', 1, '2019-02-26 16:40:46', 'admin', '2019-02-26 16:40:46', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4323654351082496, NULL, '日志管理', '', '', 'icon-filesearch', 350, '', 0, '2019-03-16 17:51:34', 'admin', '2019-03-16 17:51:34', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4323654351082499, NULL, '众筹管理', '', '', 'icon-filesearch', 1, '', 0, '2019-03-16 17:51:34', 'admin', '2019-03-16 17:51:34', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4336080847524864, NULL, '伪菜单那', '', '', '', 1000, '伪菜单在后台不显示', 2, '2019-03-25 12:32:27', 'admin', '2019-04-16 16:19:18', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4347326007383040, 4059916341400576, '组织机构', 'sys/org/org', NULL, 'icon-cluster', 30, NULL, 0, NULL, 'auto', '2019-04-02 12:34:22', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792119, 4323654351082499, '项目管理', 'cf/project/project', NULL, 'icon-file-text', 10, NULL, 0, NULL, 'auto', '2019-04-06 19:54:39', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792128, 4323654351082496, '调度日志', 'sys/qrtzLog/qrtzLog', NULL, 'icon-file-text', 50, NULL, 0, NULL, 'auto', '2019-04-06 19:54:39', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792129, 4353221119792128, '查看', NULL, 'sys:qrtzLog:page, sys:qrtzLog:list, sys:qrtzLog:info', NULL, 10, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792132, 4353221119792128, '删除', NULL, 'sys:qrtzLog:del', NULL, 40, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792133, 4353221119792128, '导出', NULL, 'sys:qrtzLog:expt', NULL, 50, NULL, 1, NULL, 'auto', NULL, 'auto');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792134, 4347326007383040, '详情', '', 'sys:dept:info', '', 1, NULL, 1, '2022-05-29 14:52:26', 'admin', '2022-05-29 14:52:26', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792135, 4347326007383040, '删除', '', 'sys:dept:del', '', 1, NULL, 1, '2022-05-29 14:53:00', 'admin', '2022-05-29 14:53:00', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792136, 4347326007383040, '编辑', '', 'sys:dept:upd', '', 1, NULL, 1, '2022-05-29 14:53:18', 'admin', '2022-05-29 14:53:18', 'admin');
INSERT INTO sys_menu
(id, pid, name, url, perm, icon, seq, remark, `type`, create_time, create_by, update_time, update_by)
VALUES(4353221119792137, 4347326007383040, '新增', '', 'sys:dept:add', '', 1, NULL, 1, '2022-05-29 14:53:53', 'admin', '2022-05-29 14:53:53', 'admin');

INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792142, 4059879121720320, 'OSS资源', 'sys/oss/oss', '', '', 1, NULL, 0, '2023-06-12 17:22:41', 'admin', '2023-06-12 17:22:41', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792143, 4353221119792142, '上传', '', 'sys:oss:upload', '', 1, NULL, 1, '2023-06-12 17:51:22', 'admin', '2023-06-12 17:51:22', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792144, NULL, '一页の博客', '', '', 'icon-meh', 2, NULL, 0, '2023-06-17 14:29:00', 'admin', '2023-06-17 14:29:00', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792145, 4353221119792144, '博客分类', 'blog/classify/classify', '', '', 1, NULL, 0, '2023-06-17 13:53:44', 'admin', '2023-06-17 13:53:44', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792146, 4353221119792145, '添加', '', 'blog:classify:add', '', 1, NULL, 1, '2023-06-17 13:53:44', 'admin', '2023-06-17 13:53:44', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792147, 4353221119792145, '修改', '', 'blog:classify:upd', '', 1, NULL, 1, '2023-06-17 13:53:44', 'admin', '2023-06-17 13:53:44', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792148, 4353221119792145, '查看', '', 'blog:classify:info', '', 1, NULL, 1, '2023-06-17 13:53:44', 'admin', '2023-06-17 13:53:44', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792149, 4353221119792145, '删除', '', 'blog:classify:del', '', 1, NULL, 1, '2023-06-17 13:53:44', 'admin', '2023-06-17 13:53:44', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792150, 4353221119792144, '博客标签', 'blog/label/label', '', '', 1, NULL, 0, '2023-06-17 14:05:12', 'admin', '2023-06-17 14:05:12', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792151, 4353221119792150, '添加', '', 'blog:label:add', '', 1, NULL, 1, '2023-06-17 14:05:12', 'admin', '2023-06-17 14:05:12', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792152, 4353221119792150, '修改', '', 'blog:label:upd', '', 1, NULL, 1, '2023-06-17 14:05:12', 'admin', '2023-06-17 14:05:12', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792153, 4353221119792150, '查看', '', 'blog:label:info', '', 1, NULL, 1, '2023-06-17 14:05:12', 'admin', '2023-06-17 14:05:12', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792155, 4353221119792150, '删除', '', 'blog:label:del', '', 1, NULL, 1, '2023-06-17 14:05:12', 'admin', '2023-06-17 14:05:12', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792156, 4353221119792144, '博客专题', 'blog/topic/topic', '', '', 1, NULL, 0, '2023-06-17 14:06:10', 'admin', '2023-06-17 14:06:10', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792157, 4353221119792156, '添加', '', 'blog:topic:add', '', 1, NULL, 1, '2023-06-17 14:06:10', 'admin', '2023-06-17 14:06:10', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792158, 4353221119792156, '修改', '', 'blog:topic:upd', '', 1, NULL, 1, '2023-06-17 14:06:10', 'admin', '2023-06-17 14:06:10', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792159, 4353221119792156, '查看', '', 'blog:topic:info', '', 1, NULL, 1, '2023-06-17 14:06:10', 'admin', '2023-06-17 14:06:10', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792160, 4353221119792156, '删除', '', 'blog:topic:del', '', 1, NULL, 1, '2023-06-17 14:06:10', 'admin', '2023-06-17 14:06:10', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792161, 4353221119792144, '博客文章', 'blog/article/article', '', '', 1, NULL, 0, '2023-06-17 14:06:56', 'admin', '2023-06-17 14:06:56', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792162, 4353221119792161, '添加', '', 'blog:article:add', '', 1, NULL, 1, '2023-06-17 14:06:56', 'admin', '2023-06-17 14:06:56', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792163, 4353221119792161, '修改', '', 'blog:article:upd', '', 1, NULL, 1, '2023-06-17 14:06:56', 'admin', '2023-06-17 14:06:56', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792164, 4353221119792161, '查看', '', 'blog:article:info', '', 1, NULL, 1, '2023-06-17 14:06:56', 'admin', '2023-06-17 14:06:56', 'admin');
INSERT INTO `sys_menu` (`id`, `pid`, `name`, `url`, `perm`, `icon`, `seq`, `remark`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES (4353221119792165, 4353221119792161, '删除', '', 'blog:article:del', '', 1, NULL, 1, '2023-06-17 14:06:56', 'admin', '2023-06-17 14:06:56', 'admin');
-- ----------------------------

-- ----------------------------

-- Table structure for sys_qrtz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_qrtz_log`;
CREATE TABLE `sys_qrtz_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url',
  `job_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务标识',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `spend_time` int(0) NULL DEFAULT NULL COMMENT '运行时长',
  `next_fire_tm` datetime(0) NULL DEFAULT NULL COMMENT '下次执行时间',
  `pre_fire_tm` datetime(0) NULL DEFAULT NULL COMMENT '这次执行时间',
  `status` tinyint(0) NULL DEFAULT 0 COMMENT '状态 (0：成功， 1：失败， 2: 异常)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `edit_flag` tinyint(0) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统调度日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_qrtz_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色code',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `status` int(0) NULL DEFAULT 0 COMMENT '状态 （0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '管理员', 0, '管理员', '1', '2021-11-24 11:33:10', 'admin', NULL);
INSERT INTO `sys_role` VALUES (2, 'SURVEYER', '调查员', 0, '调查员', '1', '2021-11-16 10:14:41', '10144', '2022-03-31 17:26:25');
INSERT INTO `sys_role` VALUES (3, 'CUSTAFF', '客服', 0, '客服', '1', '2021-11-16 10:14:41', '10144', '2022-04-01 15:47:06');
INSERT INTO `sys_role` VALUES (4, 'QUALITY_INSPECTOR', '质检员', 0, '质检员', '1', '2021-11-17 13:58:02', '10144', '2022-04-01 16:04:33');
INSERT INTO `sys_role` VALUES (5, 'SURVEYER_HEAD', '调查主管', 0, '调查主管', '1', '2021-11-17 13:58:02', '10229', '2022-02-18 17:20:18');
INSERT INTO `sys_role` VALUES (6, 'CUSTAFF_HEAD', '客服主管', 0, '客服主管', '1', '2021-11-16 10:14:41', '10229', '2022-02-18 17:20:43');
INSERT INTO `sys_role` VALUES (7, 'KF_YS', '预审客服', 0, '预审客服', '1', '2021-12-03 10:21:32', '1', '2022-01-18 17:52:46');
INSERT INTO `sys_role` VALUES (8, 'KF_GD', '工单客服', 0, '工单客服', '1', '2021-12-03 10:23:32', '10229', '2022-02-18 17:02:12');
INSERT INTO `sys_role` VALUES (9, 'QUALITY_INSPECTOR_HEAD', '质检主管', 0, '质检主管', '1', '2021-12-03 10:25:09', '10144', '2022-04-01 15:49:03');
INSERT INTO `sys_role` VALUES (10, 'DATA', '数据支持人员', 0, '数据支持', '1', '2021-12-14 11:36:03', '10229', '2022-02-18 17:22:22');
INSERT INTO `sys_role` VALUES (11, 'OBJECTIVE_USER', '客观售后角色', 0, '客观售后角色', '1', '2021-12-14 11:36:03', '1', '2022-01-18 17:53:25');
INSERT INTO `sys_role` VALUES (63, 'CSM_MANAGER', '客满经理', 0, '客满经理', '10280', '2022-02-15 11:47:32', '10215', '2022-02-18 13:46:26');
INSERT INTO `sys_role` VALUES (64, 'CSM', 'CSM专员', 0, 'CSM专员', '10280', '2022-02-15 11:48:11', '10215', '2022-02-18 13:54:52');
INSERT INTO `sys_role` VALUES (65, 'CSM_DATA', 'CSM数据专员', 0, 'CSM数据专员', '10280', '2022-02-15 11:48:32', '10215', '2022-02-18 13:57:07');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL COMMENT 'ID',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '被授权对象ID',
  `menu_id` bigint(0) NULL DEFAULT NULL COMMENT '授权资源表ID',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统授权' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059880098206720, 1, 4059879121720320, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059896069346304, 1, 4059881300399104, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059896070181888, 1, 4059891056399360, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059896070231040, 1, 4059891515298816, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059922616374272, 1, 4059916341400576, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059922616488960, 1, 4059916791092224, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059922616538112, 1, 4059917154079744, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059922616570880, 1, 4059917584143360, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059922616636416, 1, 4059919174194176, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4059922616849408, 1, 4059918640845824, NULL, NULL, NULL, NULL, NULL);
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008073216, 1, 4297735995577345, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008073217, 1, 4297735995577346, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008105984, 1, 4297735995577347, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008122368, 1, 4297735995577348, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008122369, 1, 4297735995577349, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008122370, 1, 4297735995577350, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008155136, 1, 4297735995577351, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008155137, 1, 4297735991448577, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008187904, 1, 4297735991448578, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008253440, 1, 4297735991448579, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008286208, 1, 4297735991448580, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008286209, 1, 4297735991448581, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008286210, 1, 4297735991448582, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008318976, 1, 4297735991448583, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008318977, 1, 4297735992218625, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008351744, 1, 4297735992218626, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008351745, 1, 4297735992218627, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008384512, 1, 4297735992218628, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008384513, 1, 4297735992218629, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008384514, 1, 4297735992218630, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008417280, 1, 4297735992218631, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008417281, 1, 4297179754714113, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008450048, 1, 4297179754714114, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008450049, 1, 4297179754714115, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008450050, 1, 4297179754714116, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008482816, 1, 4297179754714117, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008482817, 1, 4297179754714118, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008482818, 1, 4297179754714119, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008548352, 1, 4297179758302211, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008777728, 1, 4297179755844609, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008810497, 1, 4297179755844612, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4297962008826880, 1, 4297179755844613, NULL, '2019-02-26 14:15:57', 'admin', '2019-02-26 14:15:57', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298067504092160, 1, 4298067236262912, NULL, '2019-02-26 16:03:16', 'admin', '2019-02-26 16:03:16', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939090944, 1, 4298102159430656, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939090945, 1, 4298102588576768, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939123712, 1, 4298102912996352, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939123713, 1, 4298103224865792, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939172864, 1, 4298103662826496, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939172865, 1, 4298104030680064, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4298104939205632, 1, 4298104367584256, NULL, '2019-02-26 16:41:21', 'admin', '2019-02-26 16:41:21', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4301058363200512, 1, 4297735995577345, NULL, '2019-02-28 18:45:44', 'admin', '2019-02-28 18:45:44', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4323660713317376, 1, 4323654351082496, NULL, '2019-03-16 17:58:02', 'admin', '2019-03-16 17:58:02', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4326496941560832, 1, 4297735995577346, NULL, '2019-03-18 18:03:12', 'admin', '2019-03-18 18:03:12', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4336082061579264, 1, 4336080847524864, NULL, '2019-03-25 12:33:41', 'admin', '2019-03-25 12:33:41', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4347405895521280, 1, 4347326007383040, NULL, '2019-04-02 12:32:53', 'admin', '2019-04-02 12:32:53', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4353231277986816, 1, 4353221119792128, NULL, '2019-04-06 15:18:46', 'admin', '2019-04-06 15:18:46', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4353231278035968, 1, 4353221119792129, NULL, '2019-04-06 15:18:46', 'admin', '2019-04-06 15:18:46', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4353231278068736, 1, 4353221119792132, NULL, '2019-04-06 15:18:46', 'admin', '2019-04-06 15:18:46', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4353231278101504, 1, 4353221119792133, NULL, '2019-04-06 15:18:46', 'admin', '2019-04-06 15:18:46', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4398262101201920, 1, 4297735995577349, NULL, '2019-05-08 10:46:29', 'admin', '2019-05-08 10:46:29', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4398262101332992, 1, 4297735995577350, NULL, '2019-05-08 10:46:29', 'admin', '2019-05-08 10:46:29', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4398263498183680, 1, 4297735995577347, NULL, '2019-05-08 10:47:54', 'admin', '2019-05-08 10:47:54', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4861403364758330, 1, 4353221119792119, NULL, '2020-03-30 14:58:09', 'admin', '2020-03-30 14:58:09', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4861403364758331, 1, 4059918640845825, NULL, '2020-03-30 14:58:09', 'admin', '2020-03-30 14:58:09', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4861403364758332, 1, 4059918640845826, NULL, '2020-03-30 14:58:09', 'admin', '2020-03-30 14:58:09', 'admin');
INSERT INTO sys_role_menu
(id, role_id, menu_id, remark, create_time, create_by, update_time, update_by)
VALUES(4861403364758529, 1, 4323654351082499, NULL, '2020-03-30 14:58:09', 'admin', '2020-03-30 14:58:09', 'admin');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '花名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `dept_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '部门名称',
  `job_id` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '岗位名称',
  `state` int(0) NULL DEFAULT 0 COMMENT '状态：（0正常 1停用）',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `super_admin` tinyint(0) NULL DEFAULT NULL COMMENT '超级管理员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_idx`(`username`) USING BTREE,
  INDEX `dept_idx`(`dept_id`) USING BTREE,
  INDEX `job_idx`(`job_id`) USING BTREE,
  INDEX `create_time_idx`(`create_time`) USING BTREE,
  INDEX `update_time_idx`(`update_time`) USING BTREE,
  INDEX `email_idx`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', '超级管理员', NULL, '13888888888', NULL, NULL, 8, NULL, 0, NULL, '2021-07-12 11:24:02', '2022-01-05 17:04:43', '1', '1', '管理员', 1);
INSERT INTO `sys_user` VALUES (2, 'shenhe', '96e79218965eb72c92a549dd5a330112', '审核员', NULL, '13888888888', NULL, NULL, 131, NULL, 0, NULL, '2021-07-12 11:24:02', '2021-12-16 16:59:44', '1', '1', '管理员', 0);

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `user_id` int(0) NULL DEFAULT -1 COMMENT '用户ID',
  `dept_id` int(0) NULL DEFAULT NULL COMMENT '部门ID',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_opr_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_opr_log`;
CREATE TABLE `sys_user_opr_log`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `route_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由地址',
  `browser` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '浏览器',
  `ip_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'IP地址',
  `ip_location` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'IP位置',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ' 请求方法',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求URL',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `access_params` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '请求参数',
  `access_time` bigint(0) NULL DEFAULT NULL COMMENT '执行时间',
  `access_result` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '返回数据',
  `error_message` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  INDEX `idx_url`(`url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_opr_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(0) UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` int(0) UNSIGNED NOT NULL COMMENT '角色ID',
  `create_by` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_user_role`(`role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 367 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (33, 10054, 3, '1', '2021-12-04 19:12:13', '1', '2021-12-04 19:12:13');
INSERT INTO `sys_user_role` VALUES (36, 10142, 4, '1', '2021-12-04 19:20:22', '1', '2021-12-04 19:20:22');
INSERT INTO `sys_user_role` VALUES (45, 10193, 10098, '1', '2021-12-04 20:41:04', '1', '2021-12-04 20:41:04');
INSERT INTO `sys_user_role` VALUES (46, 10193, 10098, '1', '2021-12-04 20:47:10', '1', '2021-12-04 20:47:10');
INSERT INTO `sys_user_role` VALUES (47, 10193, 10098, '1', '2021-12-04 20:58:07', '1', '2021-12-04 20:58:07');
INSERT INTO `sys_user_role` VALUES (48, 10193, 10098, '1', '2021-12-04 21:04:57', '1', '2021-12-04 21:04:57');
INSERT INTO `sys_user_role` VALUES (50, 10011, 3, '1', '2021-12-06 11:27:25', '1', '2021-12-06 11:27:25');
INSERT INTO `sys_user_role` VALUES (152, 10272, 4, '1', '2021-12-16 13:45:51', '1', '2021-12-16 13:45:51');
INSERT INTO `sys_user_role` VALUES (156, 2, 1, '1', '2021-12-16 16:59:44', '1', '2021-12-16 16:59:44');
INSERT INTO `sys_user_role` VALUES (162, 10101, 3, '1', '2021-12-20 12:42:49', '1', '2021-12-20 12:42:49');
INSERT INTO `sys_user_role` VALUES (163, 10026, 4, '1', '2021-12-20 13:30:58', '1', '2021-12-20 13:30:58');
INSERT INTO `sys_user_role` VALUES (191, 12356, 4, '1', '2021-12-22 14:08:52', '1', '2021-12-22 14:08:52');
INSERT INTO `sys_user_role` VALUES (214, 10215, 1, '10215', '2021-12-24 10:55:30', '10215', '2021-12-24 10:55:30');
INSERT INTO `sys_user_role` VALUES (267, 10124, 4, '1', '2021-12-29 16:09:48', '1', '2021-12-29 16:09:48');
INSERT INTO `sys_user_role` VALUES (268, 10239, 4, '1', '2021-12-29 16:09:56', '1', '2021-12-29 16:09:56');
INSERT INTO `sys_user_role` VALUES (269, 10098, 2, '1', '2021-12-29 16:10:30', '1', '2021-12-29 16:10:30');
INSERT INTO `sys_user_role` VALUES (274, 10097, 2, '1', '2021-12-30 10:30:20', '1', '2021-12-30 10:30:20');
INSERT INTO `sys_user_role` VALUES (275, 10024, 6, '1', '2021-12-30 10:31:05', '1', '2021-12-30 10:31:05');
INSERT INTO `sys_user_role` VALUES (276, 10065, 9, '1', '2021-12-30 10:31:20', '1', '2021-12-30 10:31:20');
INSERT INTO `sys_user_role` VALUES (282, 10030, 62, '1', '2021-12-30 14:13:23', '1', '2021-12-30 14:13:23');
INSERT INTO `sys_user_role` VALUES (288, 10126, 2, '1', '2021-12-30 15:09:10', '1', '2021-12-30 15:09:10');
INSERT INTO `sys_user_role` VALUES (290, 10061, 2, '1', '2021-12-30 15:09:19', '1', '2021-12-30 15:09:19');
INSERT INTO `sys_user_role` VALUES (294, 12354, 10, '1', '2021-12-30 16:23:33', '1', '2021-12-30 16:23:33');
INSERT INTO `sys_user_role` VALUES (296, 10227, 3, '1', '2021-12-31 03:00:35', '1', '2021-12-31 03:00:35');
INSERT INTO `sys_user_role` VALUES (298, 12358, 10, '1', '2022-01-05 17:02:06', '1', '2022-01-05 17:02:06');
INSERT INTO `sys_user_role` VALUES (302, 1, 1, '1', '2022-01-05 17:04:43', '1', '2022-01-05 17:04:43');
INSERT INTO `sys_user_role` VALUES (306, 10007, 1, '10215', '2022-01-07 17:39:05', '10215', '2022-01-07 17:39:05');
INSERT INTO `sys_user_role` VALUES (316, 10280, 1, '1', '2022-01-13 15:33:52', '1', '2022-01-13 15:33:52');
INSERT INTO `sys_user_role` VALUES (317, 10280, 3, '1', '2022-01-13 15:33:52', '1', '2022-01-13 15:33:52');
INSERT INTO `sys_user_role` VALUES (318, 10280, 5, '1', '2022-01-13 15:33:52', '1', '2022-01-13 15:33:52');
INSERT INTO `sys_user_role` VALUES (319, 10255, 1, '10215', '2022-01-13 15:39:05', '10215', '2022-01-13 15:39:05');
INSERT INTO `sys_user_role` VALUES (324, 12357, 2, '10280', '2022-02-08 15:41:19', '10280', '2022-02-08 15:41:19');
INSERT INTO `sys_user_role` VALUES (325, 12357, 11, '10280', '2022-02-08 15:41:19', '10280', '2022-02-08 15:41:19');
INSERT INTO `sys_user_role` VALUES (326, 10224, 1, '10280', '2022-02-08 15:41:34', '10280', '2022-02-08 15:41:34');
INSERT INTO `sys_user_role` VALUES (327, 10224, 11, '10280', '2022-02-08 15:41:34', '10280', '2022-02-08 15:41:34');
INSERT INTO `sys_user_role` VALUES (338, 10228, 3, '1', '2022-02-18 13:49:10', '1', '2022-02-18 13:49:10');
INSERT INTO `sys_user_role` VALUES (340, 10131, 63, '1', '2022-02-18 14:39:57', '1', '2022-02-18 14:39:57');
INSERT INTO `sys_user_role` VALUES (346, 10130, 8, '1', '2022-02-18 15:21:12', '1', '2022-02-18 15:21:12');
INSERT INTO `sys_user_role` VALUES (348, 10029, 3, '1', '2022-02-18 16:16:38', '1', '2022-02-18 16:16:38');
INSERT INTO `sys_user_role` VALUES (349, 10029, 11, '1', '2022-02-18 16:16:38', '1', '2022-02-18 16:16:38');
INSERT INTO `sys_user_role` VALUES (350, 10013, 3, '1', '2022-02-18 16:16:47', '1', '2022-02-18 16:16:47');
INSERT INTO `sys_user_role` VALUES (353, 10135, 7, '10229', '2022-02-18 17:02:33', '10229', '2022-02-18 17:02:33');
INSERT INTO `sys_user_role` VALUES (354, 10049, 5, '10229', '2022-02-18 17:15:14', '10229', '2022-02-18 17:15:14');
INSERT INTO `sys_user_role` VALUES (355, 10132, 3, '10229', '2022-02-18 18:08:28', '10229', '2022-02-18 18:08:28');
INSERT INTO `sys_user_role` VALUES (356, 10144, 1, '10229', '2022-02-23 11:30:47', '10229', '2022-02-23 11:30:47');
INSERT INTO `sys_user_role` VALUES (357, 10144, 2, '10229', '2022-02-23 11:30:47', '10229', '2022-02-23 11:30:47');
INSERT INTO `sys_user_role` VALUES (358, 10144, 4, '10229', '2022-02-23 11:30:47', '10229', '2022-02-23 11:30:47');
INSERT INTO `sys_user_role` VALUES (362, 10099, 2, '10144', '2022-02-27 23:28:40', '10144', '2022-02-27 23:28:40');
INSERT INTO `sys_user_role` VALUES (363, 10229, 1, '10144', '2022-02-28 14:31:10', '10144', '2022-02-28 14:31:10');
INSERT INTO `sys_user_role` VALUES (364, 10229, 2, '10144', '2022-02-28 14:31:10', '10144', '2022-02-28 14:31:10');
INSERT INTO `sys_user_role` VALUES (365, 10229, 3, '10144', '2022-02-28 14:31:10', '10144', '2022-02-28 14:31:10');
INSERT INTO `sys_user_role` VALUES (366, 10219, 1, '1', '2022-03-02 15:19:53', '1', '2022-03-02 15:19:53');
-- ----------------------------
-- oss

CREATE TABLE `sys_oss_log` (
   `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `source_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '源文件名称',
   `oss_url` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '存储地址',
   `size` decimal(8,2) DEFAULT '0.00' COMMENT '大小',
   `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
   `create_time` datetime DEFAULT NULL,
   `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
   `update_time` datetime DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='OSS上传日志';


SET FOREIGN_KEY_CHECKS = 1;
