SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

use studio;

-- 学校学院表
DROP TABLE IF EXISTS `school_academy`;
CREATE TABLE `school_academy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学院主键id',
  `academy_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 学校年级表
DROP TABLE IF EXISTS `school_grade`;
CREATE TABLE `school_grade` (
    `grade_id` bigint NOT NULL AUTO_INCREMENT COMMENT '年级主键id',
    `grade_num` int NOT NULL COMMENT '年级',
    PRIMARY KEY (`grade_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 学校专业表
DROP TABLE IF EXISTS `school_major`;
CREATE TABLE `school_major` (
    `major_id` bigint NOT NULL AUTO_INCREMENT COMMENT '专业主键id',
    `major_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '专业名称',
    PRIMARY KEY (`major_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 证书表
DROP TABLE IF EXISTS `studio_ccie`;
CREATE TABLE `studio_ccie` (
   `ccie_id` bigint NOT NULL AUTO_INCREMENT COMMENT '获奖证书主键id',
   `ccie_name` varchar(100)  NOT NULL DEFAULT '' COMMENT '证书名称',
   `ccie_img` varchar(255) DEFAULT '' COMMENT '获奖证书图片',
   `ccie_get_time` datetime DEFAULT NULL COMMENT '获奖时间',
   `ccie_think` text COMMENT '经验总结',
   `user_id` bigint DEFAULT NULL COMMENT '用户id',
   `inclusion_flag` TINYINT DEFAULT 0 COMMENT '收录标志，0表示未收录，1为申请收录，2为收录打回，3为收录通过（可对外展示）',
   `type` TINYINT DEFAULT 0 COMMENT '类别字段码，对应sys_dict_type中的dict_type=CCIE_TYPE，0表示其他证书可自定义',
   `type_name` varchar(100) DEFAULT '' COMMENT '自定义证书类别名称（自定义名称type=0的名称也放置其中）',
   PRIMARY KEY (`ccie_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 竞赛表（个人、团队）
DROP TABLE IF EXISTS `studio_race`;
CREATE TABLE `studio_race` (
   `race_id` bigint NOT NULL AUTO_INCREMENT COMMENT '竞赛主键id',
   `race_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '竞赛名称',
   `race_members` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '竞赛成员（个人就填写一个id，存储参与成员的id，使用,分割）',
   `race_begin_time` datetime DEFAULT NULL COMMENT '竞赛开始时间',
   `race_end_time` datetime DEFAULT NULL COMMENT '竞赛结束时间',
   `race_ccie` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '获奖证书图片',
   `race_flag` tinyint(1) DEFAULT '1' COMMENT '标识个人还是团队(1是个人,2是团队)',
   `race_summarize` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci COMMENT '经验总结',
   `inclusion_flag` TINYINT DEFAULT 0 COMMENT '收录标志，0表示未收录，1为申请收录，2为收录打回，3为收录通过（可对外展示）',
   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` datetime DEFAULT NULL COMMENT '修改时间',
   PRIMARY KEY (`race_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 团队资源表
DROP TABLE IF EXISTS `studio_resource`;
CREATE TABLE `studio_resource` (
   `res_id` bigint NOT NULL AUTO_INCREMENT COMMENT '资源主键id',
   `res_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '资源url地址',
   `res_flag` tinyint(1) DEFAULT '1' COMMENT '资源标识(1表示竞赛团队)',
   `res_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '' COMMENT '图片上传名称',
   `create_time` datetime DEFAULT NULL COMMENT '上传时间',
   `table_id` bigint DEFAULT NULL COMMENT '关联表id(其他表主键id)',
   PRIMARY KEY (`res_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 团队个人思考表
DROP TABLE IF EXISTS `studio_think`;
CREATE TABLE `studio_think` (
    `think_id` bigint NOT NULL AUTO_INCREMENT COMMENT '个人心得主键id',
    `think_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '思考标题',
    `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '感悟思考',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `user_id` bigint DEFAULT NULL COMMENT '用户主键id',
    PRIMARY KEY (`think_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 系统配置表
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
      `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
      `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数名称',
      `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键名',
      `config_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '参数键值',
      `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
      `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
      PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参数配置表';

-- 系统字典值表
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
     `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
     `dict_sort` int DEFAULT '0' COMMENT '字典排序',
     `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典标签',
     `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典键值',
     `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
     `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
     `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表格回显样式',
     `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
     `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
     `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
     `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`dict_code`) USING BTREE,
     KEY `fk_sys_dict_data_sys_dict_data_1` (`dict_type`) USING BTREE,
     CONSTRAINT `fk_sys_dict_data_sys_dict_data_1` FOREIGN KEY (`dict_type`) REFERENCES `sys_dict_type` (`dict_type`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';

-- 系统字典类型表
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
     `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
     `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典名称',
     `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '字典类型',
     `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '状态（0正常 1停用）',
     `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
     `create_time` datetime DEFAULT NULL COMMENT '创建时间',
     `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
     `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
     PRIMARY KEY (`dict_id`) USING BTREE,
     UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';

-- 系统菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
    `order_num` int DEFAULT '0' COMMENT '显示顺序',
    `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
    `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
    `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
    `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
    `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
    `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';

-- 系统角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
    `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort` int NOT NULL COMMENT '显示顺序',
    `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
    `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
    `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

-- 系统角色菜单中间表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
     `role_id` bigint NOT NULL COMMENT '角色ID',
     `menu_id` bigint NOT NULL COMMENT '菜单ID',
     PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';

-- 系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
    `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
    `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户邮箱',
    `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '手机号码',
    `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '头像地址',
    `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '密码',
    `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '最后登录IP',
    `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `real_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '真实姓名',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '个人描述(用于官网展示)',
    `per_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '个人照片',
    `major_id` bigint DEFAULT NULL COMMENT '专业id',
    `grade_id` bigint DEFAULT NULL COMMENT '年级id',
    `academy_id` bigint DEFAULT NULL COMMENT '学院id',
    `extra` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '额外字段，详情可见模型',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

-- 系统用户角色表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
     `user_id` bigint NOT NULL COMMENT '用户ID',
     `role_id` bigint NOT NULL COMMENT '角色ID',
     PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';

-- 成果表
DROP TABLE IF EXISTS `studio_achievement`;
CREATE TABLE `studio_achievement` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` VARCHAR(255) NOT NULL COMMENT '标题',
  `preview_img` VARCHAR(255) COMMENT '预览图',
  `pocs_id` BIGINT COMMENT '成果分类id【studio_pocs】',
  `create_user_id` BIGINT COMMENT '创建用户id【sys_user】',
  `part_user_ids` VARCHAR(255) DEFAULT '' COMMENT '参与者，包含有多个用户id【sys_user】，使用,分隔',
  `start_time` DATETIME COMMENT '过程开始时间',
  `end_time` DATETIME COMMENT '过程结束时间',
  `description` TEXT COMMENT '成果描述及总结材料（可以markdown形式）',
  `del_flag` TINYINT DEFAULT 0 COMMENT '删除标志，0表示未删除，1表示删除',
  `inclusion_flag` TINYINT DEFAULT 0 COMMENT '收录标志，0表示未收录，1为申请收录，2为收录打回，3为收录通过（可对外展示）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='成果表';

-- 成果分类表
DROP TABLE IF EXISTS `studio_pocs`;
CREATE TABLE `studio_pocs` (
   `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键id',
   `pocs_name` VARCHAR(255) NOT NULL COMMENT '成果名称',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='成果分类表';

SET FOREIGN_KEY_CHECKS = 1;

