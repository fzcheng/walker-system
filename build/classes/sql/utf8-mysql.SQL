--   -------------------------------------------------- 
--   Generated by Enterprise Architect Version 8.0.858
--   Created On : 星期一, 02 十二月, 2013 
--   DBMS       : MySql 
--   -------------------------------------------------- 


SET FOREIGN_KEY_CHECKS=0;


--  Drop Tables, Stored Procedures and Views 

/***/
DROP TABLE IF EXISTS S_CODE
;
DROP TABLE IF EXISTS S_DEPARTMENT_ADMIN
;
DROP TABLE IF EXISTS S_DEPARTMENT
;
DROP TABLE IF EXISTS S_POINTER
;
DROP TABLE IF EXISTS S_FUNCTION
;
DROP TABLE IF EXISTS S_LOG
;
DROP TABLE IF EXISTS S_ROLE_FUNC
;
DROP TABLE IF EXISTS S_ROLE_USER
;
DROP TABLE IF EXISTS S_ROLE
;
DROP TABLE IF EXISTS S_USER_CORE
;
DROP TABLE IF EXISTS S_FILE
;
DROP TABLE IF EXISTS S_DB
;
DROP TABLE IF EXISTS S_FLOW_BIND
;

--  Create Tables 
CREATE TABLE S_CODE
(
	ID VARCHAR(36) NOT NULL,
	SXH INTEGER NOT NULL COMMENT '顺序号',
	NAME VARCHAR(50) NOT NULL COMMENT '代码名称',
	CODE_ID VARCHAR(100) COMMENT '对应国家代码ID',
	CODE_TYPE TINYINT NOT NULL COMMENT '代码类型：0_代码表，1_代码项',
	PARENT_ID VARCHAR(36) NOT NULL COMMENT '上级ID',
	CODE_SEC TINYINT NOT NULL COMMENT '代码管理权限：0_系统代码，1_用户代码',
	CHILD_SUM INTEGER NOT NULL DEFAULT 0 COMMENT '子代码数量',
	PRIMARY KEY (ID)
)  COMMENT='系统代码表'
;


CREATE TABLE S_DEPARTMENT
(
	ID VARCHAR(36) NOT NULL COMMENT '主键',
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间,如：20131023132301',
	ORDER_NUM INTEGER NOT NULL COMMENT '顺序号',
	NAME VARCHAR(200) NOT NULL COMMENT '机构全称',
	SIMPLE_NAME VARCHAR(200) COMMENT '简称',
	TYPE TINYINT NOT NULL COMMENT '机构类型：0_独立单位，1_部门',
	IS_SYSTEM TINYINT NOT NULL DEFAULT 0 COMMENT '是否系统机构：0_否，1_是,系统机构不允许普通管理员修改',
	PARENT_ID VARCHAR(36) NOT NULL COMMENT '上级机构ID',
	ATTRIBUTE VARCHAR(255) COMMENT '机构属性，由业务定义并扩展',
	CHILD_SUM INTEGER NOT NULL DEFAULT 0 COMMENT '下级子机构数量',
	ADMINISTRATE TINYINT NOT NULL DEFAULT 0 COMMENT '是否启用单位管理员：0_否,1_是，只有类型为单位才能使用',
	CHARGE_MAN VARCHAR(36) COMMENT '机构主管，用户ID，只能存在一个',
	MANAGER VARCHAR(36) COMMENT '部门经理，用户ID，只能存在一个',
	SUMMARY VARCHAR(255) COMMENT '简介',
	STATUS TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0_正常，1_已删除',
	PRIMARY KEY (ID)
)  COMMENT='机构信息表'
;


CREATE TABLE S_DEPARTMENT_ADMIN
(
	ID VARCHAR(36) NOT NULL COMMENT '主键',
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间',
	DEPART_ID VARCHAR(36) NOT NULL COMMENT '机构ID',
	USER_ID VARCHAR(36) NOT NULL COMMENT '用户ID',
	USER_NAME VARCHAR(100) NOT NULL COMMENT '用户名称（冗余）',
	PRIMARY KEY (ID),
	KEY (DEPART_ID)
)  COMMENT='单位管理员表'
;


CREATE TABLE S_FUNCTION
(
	ID VARCHAR(36) NOT NULL,
	ORDER_NUM INTEGER NOT NULL,
	F_TYPE INTEGER NOT NULL COMMENT '功能类型：0_功能组，1_功能项',
	NAME VARCHAR(200) NOT NULL COMMENT '功能名称',
	URL VARCHAR(200) COMMENT '功能地址',
	POINTER VARCHAR(255) COMMENT '功能点：id,id,..',
	IS_RUN TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用：0_禁用，1_启用',
	PARENT_ID VARCHAR(36) COMMENT '上级菜单组id',
	OPEN_STYLE TINYINT NOT NULL DEFAULT 0 COMMENT '菜单打开方式:0_iframe,1_弹出对话框,2_覆盖主窗口,9_新浏览器窗口',
	PRIMARY KEY (ID)
)  COMMENT='功能菜单表'
;


CREATE TABLE S_LOG
(
	ID VARCHAR(36) NOT NULL,
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间，毫秒数',
	LOGIN_USER VARCHAR(36) NOT NULL COMMENT '用户登录ID',
	TYPE TINYINT NOT NULL COMMENT '日志类型:0_一般操作,1_登录,2_注销,3_删除数据',
	CONTENT VARCHAR(255) NOT NULL COMMENT '日志内容',
	SUMMARY VARCHAR(255) COMMENT '备用字段',
	PRIMARY KEY (ID)
)  COMMENT='日志表'
;


CREATE TABLE S_POINTER
(
	ID VARCHAR(36) NOT NULL COMMENT '主键',
	NAME VARCHAR(100) NOT NULL COMMENT '功能点名称',
	ORDER_NUM INTEGER NOT NULL COMMENT '顺序号',
	SELECTED NUMERIC(2) NOT NULL DEFAULT 0 COMMENT '默认选择：0_否1_是',
	FUNCTION_ID VARCHAR(36) NOT NULL COMMENT '对应功能ID',
	URL VARCHAR(200) COMMENT '功能点对应的URL',
	PRIMARY KEY (ID),
	KEY (FUNCTION_ID)
)  COMMENT='功能点表'
;


CREATE TABLE S_ROLE
(
	ID VARCHAR(36) NOT NULL,
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间',
	R_TYPE TINYINT NOT NULL DEFAULT 1 COMMENT '角色类型：0_系统，1_用户类型',
	NAME VARCHAR(200) NOT NULL COMMENT '角色名称',
	SUMMARY VARCHAR(200) COMMENT '描述',
	PRIMARY KEY (ID)
)  COMMENT='角色表'
;


CREATE TABLE S_ROLE_FUNC
(
	ID VARCHAR(36) NOT NULL,
	ROLE_ID VARCHAR(36) NOT NULL COMMENT '角色ID',
	FUNC_ITEM_ID VARCHAR(36) NOT NULL COMMENT '功能项ID',
	POINTER VARCHAR(255) COMMENT '功能点IDS，用户英文逗号分隔',
	PRIMARY KEY (ID),
	KEY (ROLE_ID)
)  COMMENT='角色对应功能表'
;


CREATE TABLE S_ROLE_USER
(
	ID VARCHAR(36) NOT NULL,
	ROLE_ID VARCHAR(36) NOT NULL COMMENT '角色ID',
	USER_ID VARCHAR(36) NOT NULL COMMENT '用户ID',
	USER_NAME VARCHAR(50) COMMENT '用户名',
	PRIMARY KEY (ID),
	KEY (ROLE_ID)
)  COMMENT='角色用户表'
;


CREATE TABLE S_USER_CORE
(
	ID VARCHAR(36) NOT NULL COMMENT '主键',
	CREATE_TIME BIGINT NOT NULL,
	ORDER_NUM INTEGER NOT NULL,
	LOGIN_ID VARCHAR(50) NOT NULL COMMENT '登录ID',
	NAME VARCHAR(100) NOT NULL COMMENT '用户姓名',
	PINYIN VARCHAR(100) COMMENT '用户名拼音',
	TYPE TINYINT NOT NULL DEFAULT 2 COMMENT '用户类型：0_超级管理员，1_单位管理员，2_普通用户',
	ENABLE TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用：0_否，1_是',
	STATUS TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0_正常，1_删除',
	ORG_ID VARCHAR(36) NOT NULL COMMENT '用户所属单位ID',
	DEPARTMENT_ID VARCHAR(36) COMMENT '用户所在部门ID',
	SUMMARY VARCHAR(200),
	PASSWORD VARCHAR(50) NOT NULL COMMENT '密码',
	STYLE VARCHAR(20) NOT NULL DEFAULT 'simple' COMMENT '界面风格',
	FROM_TYPE TINYINT NOT NULL DEFAULT 0 COMMENT '用户来源：0_系统内部，1_外部（如：供应商）目前不细化',
	FROM_TYPE_ID VARCHAR(36) COMMENT '来源细分，通过代码引用',
	ORG_NAME VARCHAR(100) COMMENT '外部用户，需要用此冗余字段显示单位名',
	SEX TINYINT NOT NULL DEFAULT 1 COMMENT '性别：0_女,1_男',
	TEL VARCHAR(20) COMMENT '电话号码',
	MOBILE VARCHAR(15) COMMENT '手机号码',
	EMAIL VARCHAR(50) COMMENT '邮件地址',
	PRIMARY KEY (ID)
)  COMMENT='用户核心表'
;

CREATE TABLE S_FILE
(
	ID VARCHAR(36) NOT NULL,
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间',
	CREATOR VARCHAR(30) COMMENT '创建人',
	CONTENT_TYPE VARCHAR(120) NOT NULL COMMENT '文件mime类型',
	FILE_NAME VARCHAR(255) NOT NULL COMMENT '文件原始名称',
	PATH VARCHAR(255) COMMENT '文件在服务器保存的相对路径',
	EXT VARCHAR(6) COMMENT '扩展名',
	CONTENT BLOB COMMENT '文件二进制内容',
	STORE_TYPE SMALLINT NOT NULL COMMENT '存储类型：0_文件，1_二进制',
	SUMMARY VARCHAR(100) COMMENT '备注',
	GROUP_ID VARCHAR(36) COMMENT '组编号，业务可以把多个文件归类一个组',
	PRIMARY KEY (ID)
)  COMMENT='应用文件信息表'
;

CREATE TABLE S_DB
(
	ID VARCHAR(36) NOT NULL COMMENT '主键',
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间，毫秒数',
	CREATOR_NAME VARCHAR(50) NOT NULL COMMENT '创建用户名',
	TYPE TINYINT NOT NULL COMMENT '类型：0_导入，1_导出',
	NAME VARCHAR(100) NOT NULL COMMENT '导出、导入名字',
	FILE_ID VARCHAR(36) NOT NULL COMMENT '导出、导入文件的ID，关联S_FIL表主键',
	EXPORT_TYPE TINYINT NOT NULL DEFAULT 0 COMMENT '导出类型：0_全表，1_选择表',
	SUMMARY VARCHAR(255) COMMENT '备注',
	PRIMARY KEY (ID)
) 
;

CREATE TABLE S_FLOW_BIND
(
	ID BIGINT NOT NULL,
	CREATE_TIME BIGINT NOT NULL COMMENT '创建时间，毫秒值',
	TYPE TINYINT NOT NULL DEFAULT 0 COMMENT '绑定类型：0_单位，1_部门，2_角色，3_岗位，9_个人',
	BIND_ID VARCHAR(36) NOT NULL COMMENT '绑定的ID，如：单位ID、部门ID、用户ID等',
	BIND_NAME VARCHAR(50) NOT NULL COMMENT '绑定的名字，冗余，如：单位名称、部门名称等',
	PROCESS_DEFINE_ID VARCHAR(36) NOT NULL COMMENT '绑定的流程定义ID',
	PROCESS_DEFINE_NAME VARCHAR(100) NOT NULL COMMENT '绑定流程定义名称，冗余',
	SUMMARY VARCHAR(100) COMMENT '备注',
	BUSINESS_TYPE VARCHAR(12) COMMENT '业务类别ID',
	PRIMARY KEY (ID)
)  COMMENT='流程绑定表'
;

SET FOREIGN_KEY_CHECKS=1;


--  Create Foreign Key Constraints 
ALTER TABLE S_DEPARTMENT_ADMIN ADD CONSTRAINT FK_S_DEPART_ADMIN 
	FOREIGN KEY (DEPART_ID) REFERENCES S_DEPARTMENT (ID)
	ON DELETE CASCADE
;

ALTER TABLE S_POINTER ADD CONSTRAINT FK_S_POINTER_S_FUNCTION 
	FOREIGN KEY (FUNCTION_ID) REFERENCES S_FUNCTION (ID)
	ON DELETE CASCADE
;

ALTER TABLE S_ROLE_FUNC ADD CONSTRAINT FK_S_ROLE_FUNC_S_ROLE 
	FOREIGN KEY (ROLE_ID) REFERENCES S_ROLE (ID)
	ON DELETE CASCADE
;

ALTER TABLE S_ROLE_USER ADD CONSTRAINT FK_S_ROLE_USER_S_ROLE 
	FOREIGN KEY (ROLE_ID) REFERENCES S_ROLE (ID)
	ON DELETE CASCADE
;

-- ----------------------------
-- Records of s_code
-- ----------------------------
/**
INSERT INTO s_code VALUES ('RD_TYPE', '26', '研发类型', 0, '0', 'ROOT', '0', '2');
INSERT INTO s_code VALUES ('RD_TYPE_NEW', '1', '研发项目', 0, '1', 'RD_TYPE', '1', '0');
INSERT INTO s_code VALUES ('RD_TYPE_PRD', '2', '推广项目', 0, '1', 'RD_TYPE', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA', '1', '所属区域', 0, '0', 'ROOT', '0', '32');
INSERT INTO s_code VALUES ('SYS_AREA_23', '23', '江苏省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_24', '24', '上海', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_25', '25', '内蒙古', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_27', '27', '深圳', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_34', '34', '宁夏', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_AHS', '5', '安徽省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_BEIJING', '19', '北京', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_CHONGQING', '22', '重庆', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_FUJIAN', '12', '福建省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_GDS', '8', '广东省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_GXS', '9', '广西省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_HAINAN', '11', '海南省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_HEBEI', '18', '河北省', 0, '1', 'SYS_AREA', '1', '0');
INSERT INTO s_code VALUES ('SYS_AREA_HNS', '6', '河南省', 0, '1', 'SYS_AREA', '1', '0');

-- ----------------------------
-- Records of s_function
-- ----------------------------
INSERT INTO s_function VALUES ('0', '99', '-1', '运行维护', null, null, '1', '-1', 0);
INSERT INTO s_function VALUES ('1', '1', '0', '系统管理', null, null, '1', '0', 0);
INSERT INTO s_function VALUES ('10', '10', '1', '功能菜单', '/admin/function/index.do', 'FUNC_ADD,FUNC_EDIT,FUNC_DEL', '1', '1', 0);
INSERT INTO s_function VALUES ('12', '12', '1', '代码管理', '/admin/code/index.do', 'CODE_ADD,CODE_EDIT,CODE_DEL', '1', '1', 0);
INSERT INTO s_function VALUES ('13', '13', '1', '组织机构', '/admin/department/index.do', 'DEPT_ADD,DEPT_ADD_POST,DEPT_VIEW,DEPT_DEL,DEPT_EASE,DEPT_EDIT', '1', '1', 0);
INSERT INTO s_function VALUES ('14', '14', '1', '系统用户', '/admin/user/index.do', 'USER_ADD,USER_DEL,USER_EASE,USER_EDIT,PASS_RESET', '1', '1', 0);
INSERT INTO s_function VALUES ('15', '15', '1', '角色权限', '/admin/role/index.do', 'ROLE_ADD,ROLE_FUNC,ROLE_USER,ROLE_DEL', '1', '1', 0);
INSERT INTO s_function VALUES ('16', '16', '1', '活动用户', '/admin/activeuser/index.do', null, '1', '1', 0);
INSERT INTO s_function VALUES ('17', '17', '1', '日志检查', '/admin/log/index.do', null, '1', '1', 0);
INSERT INTO s_function VALUES ('18', '18', '1', '上传附件', '/admin/file/index.do', null, '1', '1', 0);
INSERT INTO s_function VALUES ('9', '9', '1', '参数选项', '/admin/args/index.do', 'ARGS_SAVE', '1', '1', 0);

INSERT INTO s_function VALUES ('3', '3', '0', '首页设置', null, null, '1', '0', 0);
INSERT INTO s_function VALUES ('31', '31', '1', '上传LOGO', '/admin/home/logo.do', null, '1', '3', 0);

insert into s_function   values ('20','2','0','流程管理',null,null,'1','0', 0);
insert into s_function   values ('202','202','1','流程定义管理','/flow/define/index.do',null,'1','20', 0);
insert into s_function   values ('203','203','1','流程运行监控','/flow/instance/index.do',null,'1','20', 0);
insert into s_function   values ('204','204','1','正在办理任务','/flow/instance/await_task.do',null,'1','20', 0);
insert into s_function   values ('205','205','1','流程绑定设置','/flow/bind/index.do',null,'1','20', 0);
insert into s_function   values ('209','209','1','流程测试','/flow/test/index.do',null,'1','20', 0);

-- ----------------------------
-- Records of s_pointer
-- ----------------------------
INSERT INTO s_pointer VALUES ('ARGS_SAVE', '更新选项', '1', '0', '9', '/admin/args/save.do');
INSERT INTO s_pointer VALUES ('CODE_ADD', '添加代码项', '1', '0', '12', '/admin/code/saveCode.do');
INSERT INTO s_pointer VALUES ('CODE_DEL', '删除代码项', '3', '0', '12', '/admin/code/deleteCodeInfo.do');
INSERT INTO s_pointer VALUES ('CODE_EDIT', '修改代码项', '2', '0', '12', '/admin/code/updateCode.do');
INSERT INTO s_pointer VALUES ('DEPT_ADD', '添加机构', '1', '0', '13', '/admin/department/save.do');
INSERT INTO s_pointer VALUES ('DEPT_ADD_POST', '添加岗位', '2', '0', '13', '/admin/department/show_add_post.do');
INSERT INTO s_pointer VALUES ('DEPT_DEL', '删除机构', '4', '0', '13', '/admin/department/deleteOrg.do');
INSERT INTO s_pointer VALUES ('DEPT_EASE', '彻底删除', '5', '0', '13', '/admin/department/easeOrg.do');
INSERT INTO s_pointer VALUES ('DEPT_EDIT', '编辑机构', '6', '0', '13', '/admin/department/edit.do');
INSERT INTO s_pointer VALUES ('DEPT_VIEW', '查看机构', '3', '0', '13', '/admin/department/show.do');
INSERT INTO s_pointer VALUES ('ROLE_ADD', '创建角色', '1', '0', '15', '/admin/role/createRole.do');
INSERT INTO s_pointer VALUES ('ROLE_FUNC', '设置功能', '2', '0', '15', '/admin/role/saveRoleFunc.do');
INSERT INTO s_pointer VALUES ('ROLE_USER', '设置用户', '3', '0', '15', '/admin/role/saveRoleUser.do');
INSERT INTO s_pointer VALUES ('ROLE_DEL', '删除角色', '4', '0', '15', '/admin/role/remove.do');
INSERT INTO s_pointer VALUES ('USER_ADD', '添加用户', '1', '0', '14', '/admin/user/save.do');
INSERT INTO s_pointer VALUES ('USER_DEL', '删除用户', '2', '0', '14', '/admin/user/delete.do');
INSERT INTO s_pointer VALUES ('USER_EASE', '彻底删除用户', '3', '0', '14', '/admin/user/ease.do');
INSERT INTO s_pointer VALUES ('USER_EDIT', '编辑用户', '4', '0', '14', '/admin/user/edit.do');
INSERT INTO s_pointer VALUES ('PASS_RESET', '重置密码', '5', '0', '14', '/admin/user/pass_reset.do');
INSERT INTO s_pointer VALUES ('FUNC_ADD', '添加新功能', '1', '0', '10', '/admin/function/show_add.do');
INSERT INTO s_pointer VALUES ('FUNC_EDIT', '编辑功能', '2', '0', '10', '/admin/function/show_edit.do');
INSERT INTO s_pointer VALUES ('FUNC_DEL', '删除功能', '3', '0', '10', '/admin/function/remove.do');
*/

