-- MySQL dump 10.13  Distrib 5.6.19, for osx10.7 (i386)
--
-- Host: 114.55.99.232    Database: yeelo_sdk
-- ------------------------------------------------------
-- Server version	5.6.30-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(200) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_STATEFUL` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `IS_STATEFUL` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_LISTENERS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_LISTENERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_JOB_LISTENERS` (
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `JOB_LISTENER` varchar(200) NOT NULL,
  PRIMARY KEY (`JOB_NAME`,`JOB_GROUP`,`JOB_LISTENER`),
  CONSTRAINT `QRTZ_JOB_LISTENERS_ibfk_1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_LISTENERS`
--

LOCK TABLES `QRTZ_JOB_LISTENERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_LISTENERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_JOB_LISTENERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_LOCKS` (
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
INSERT INTO `QRTZ_LOCKS` VALUES ('CALENDAR_ACCESS'),('JOB_ACCESS'),('MISFIRE_ACCESS'),('STATE_ACCESS'),('TRIGGER_ACCESS');
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `IS_VOLATILE` varchar(1) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `JOB_NAME` (`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGER_LISTENERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGER_LISTENERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QRTZ_TRIGGER_LISTENERS` (
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `TRIGGER_LISTENER` varchar(200) NOT NULL,
  PRIMARY KEY (`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_LISTENER`),
  CONSTRAINT `QRTZ_TRIGGER_LISTENERS_ibfk_1` FOREIGN KEY (`TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGER_LISTENERS`
--

LOCK TABLES `QRTZ_TRIGGER_LISTENERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGER_LISTENERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_TRIGGER_LISTENERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SW_ACTOR_ASSIGN`
--

DROP TABLE IF EXISTS `SW_ACTOR_ASSIGN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SW_ACTOR_ASSIGN` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `TASK_DEFINE_ID` varchar(36) NOT NULL COMMENT '任务定义ID',
  `ACTOR_ID` varchar(36) NOT NULL COMMENT '参与者ID(用户名、岗位、角色等)',
  `ACTOR_NAME` varchar(60) NOT NULL COMMENT '参与者名称',
  `ORDER_NUM` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '顺序号',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '流程定义ID',
  PRIMARY KEY (`ID`),
  KEY `TASK_DEFINE_ID` (`TASK_DEFINE_ID`),
  CONSTRAINT `FK_SW_ACTOR_ASSIGN_004` FOREIGN KEY (`TASK_DEFINE_ID`) REFERENCES `SW_TASK_DEFINE` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参与者指派';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SW_ACTOR_ASSIGN`
--

LOCK TABLES `SW_ACTOR_ASSIGN` WRITE;
/*!40000 ALTER TABLE `SW_ACTOR_ASSIGN` DISABLE KEYS */;
/*!40000 ALTER TABLE `SW_ACTOR_ASSIGN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SW_PROCESS_VERSION`
--

DROP TABLE IF EXISTS `SW_PROCESS_VERSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SW_PROCESS_VERSION` (
  `INTER_IDENTIFIER` varchar(36) NOT NULL COMMENT '流程内部标识号',
  `CREATE_TIME` decimal(14,0) NOT NULL COMMENT '创建时间',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '对应流程定义表[SW_PROCESS_DEFINE].ID',
  PRIMARY KEY (`INTER_IDENTIFIER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程定义的版本表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SW_PROCESS_VERSION`
--

LOCK TABLES `SW_PROCESS_VERSION` WRITE;
/*!40000 ALTER TABLE `SW_PROCESS_VERSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `SW_PROCESS_VERSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SW_TASK_DEFINE`
--

DROP TABLE IF EXISTS `SW_TASK_DEFINE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SW_TASK_DEFINE` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `NAME` varchar(200) NOT NULL COMMENT '任务名称',
  `CREATE_TIME` decimal(14,0) NOT NULL COMMENT '创建时间',
  `TASK_TYPE` varchar(50) NOT NULL COMMENT '任务类型',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '流程定义ID',
  `LISTENER_IN` varchar(200) DEFAULT NULL COMMENT '节点进入事件ID',
  `LISTENER_OUT` varchar(200) DEFAULT NULL COMMENT '节点离开事件ID',
  `IS_START` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '是否开始任务:0_否,1_是,默认0',
  `IS_END` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '是否结束任务:0_否,1_是,默认0',
  `AFFLUENCE_TYPE` decimal(1,0) DEFAULT NULL COMMENT '汇聚类型:1_单一汇聚,2_全部汇聚,,3_多数汇聚',
  `SPLIT_TYPE` decimal(1,0) DEFAULT NULL COMMENT '分拆类型：1_用户选择2_条件分拆9_全部分拆',
  `ACTOR_TYPE` varchar(200) NOT NULL COMMENT '参与者类型:0_无参与者',
  `SUB_PROCESS_ID` varchar(36) DEFAULT NULL COMMENT '子流程定义ID',
  `NEXT_NAME` varchar(50) DEFAULT NULL COMMENT '下一步按钮自定义名称',
  `PREVIOUS_NAME` varchar(50) DEFAULT NULL COMMENT '退回按钮自定义名称',
  `PAGE_URL` varchar(300) DEFAULT NULL COMMENT '业务集成页面地址',
  `ACCESS_ROLE` varchar(36) DEFAULT NULL COMMENT '任务可访问角色，不同角色可访问不同任务',
  `MULTI_USER` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '任务可多人执行:0_不允许,1_允许',
  `ACTOR_EXEC` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '如果存在多个参与者执行方式:0_任意一个人执行完,1_所有人执行完.2_多数人执行完',
  `MOD_TASK` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '修改任务:0_否;1_是;可以增加或删除某些任务',
  `AUTO_SPLIT_MERGE` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '任务自动分拆合并选项:0_不能拆分合并;1_只能拆分;2_只能合并;3_可拆分合并',
  PRIMARY KEY (`ID`),
  KEY `PROCESS_DEFINE_ID` (`PROCESS_DEFINE_ID`),
  CONSTRAINT `FK_SW_TASK_DEFINE_001` FOREIGN KEY (`PROCESS_DEFINE_ID`) REFERENCES `sw_process_define` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程任务定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SW_TASK_DEFINE`
--

LOCK TABLES `SW_TASK_DEFINE` WRITE;
/*!40000 ALTER TABLE `SW_TASK_DEFINE` DISABLE KEYS */;
/*!40000 ALTER TABLE `SW_TASK_DEFINE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SW_TASK_IN`
--

DROP TABLE IF EXISTS `SW_TASK_IN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SW_TASK_IN` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `TASK_DEFINE_ID` varchar(36) NOT NULL COMMENT '任务定义ID',
  `PREVIOUS_TASK` varchar(36) NOT NULL COMMENT '上一个任务ID',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '流程定义ID',
  PRIMARY KEY (`ID`),
  KEY `TASK_DEFINE_ID` (`TASK_DEFINE_ID`),
  CONSTRAINT `FK_SW_TASK_IN_003` FOREIGN KEY (`TASK_DEFINE_ID`) REFERENCES `SW_TASK_DEFINE` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务进入路由表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SW_TASK_IN`
--

LOCK TABLES `SW_TASK_IN` WRITE;
/*!40000 ALTER TABLE `SW_TASK_IN` DISABLE KEYS */;
/*!40000 ALTER TABLE `SW_TASK_IN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SW_TASK_OUT`
--

DROP TABLE IF EXISTS `SW_TASK_OUT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SW_TASK_OUT` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `TASK_DEFINE_ID` varchar(36) NOT NULL COMMENT '任务定义ID',
  `NEXT_TASK` varchar(36) NOT NULL COMMENT '下一个任务ID',
  `ORDER_NUM` decimal(2,0) NOT NULL COMMENT '顺序号',
  `CONDITIONS` varchar(100) DEFAULT NULL COMMENT '用户定义条件',
  `EXPRESSION` varchar(100) DEFAULT NULL COMMENT '解析后的表达式',
  `DEFAULT_TASK` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '默认选择任务:0_否,1_是,在用户选择下一步任务时使用',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '流程定义ID',
  PRIMARY KEY (`ID`),
  KEY `TASK_DEFINE_ID` (`TASK_DEFINE_ID`),
  CONSTRAINT `FK_SW_TASK_OUT_002` FOREIGN KEY (`TASK_DEFINE_ID`) REFERENCES `SW_TASK_DEFINE` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节点离开路由表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SW_TASK_OUT`
--

LOCK TABLES `SW_TASK_OUT` WRITE;
/*!40000 ALTER TABLE `SW_TASK_OUT` DISABLE KEYS */;
/*!40000 ALTER TABLE `SW_TASK_OUT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_code`
--

DROP TABLE IF EXISTS `s_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_code` (
  `ID` varchar(36) NOT NULL,
  `SXH` int(11) NOT NULL COMMENT '顺序号',
  `NAME` varchar(50) NOT NULL COMMENT '代码名称',
  `CODE_ID` varchar(100) DEFAULT NULL COMMENT '对应国家代码ID',
  `CODE_TYPE` tinyint(4) NOT NULL COMMENT '代码类型：0_代码表，1_代码项',
  `PARENT_ID` varchar(36) NOT NULL COMMENT '上级ID',
  `CODE_SEC` tinyint(4) NOT NULL COMMENT '代码管理权限：0_系统代码，1_用户代码',
  `CHILD_SUM` int(11) NOT NULL DEFAULT '0' COMMENT '子代码数量',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统代码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_code`
--

LOCK TABLES `s_code` WRITE;
/*!40000 ALTER TABLE `s_code` DISABLE KEYS */;
INSERT INTO `s_code` VALUES ('RD_TYPE',26,'研发类型','0',0,'ROOT',0,2),('RD_TYPE_NEW',1,'研发项目','0',1,'RD_TYPE',1,0),('RD_TYPE_PRD',2,'推广项目','0',1,'RD_TYPE',1,0),('SYS_AREA',1,'所属区域','0',0,'ROOT',0,32),('SYS_AREA_23',23,'江苏省','0',1,'SYS_AREA',1,0),('SYS_AREA_24',24,'上海','0',1,'SYS_AREA',1,0),('SYS_AREA_25',25,'内蒙古','0',1,'SYS_AREA',1,0),('SYS_AREA_27',27,'深圳','0',1,'SYS_AREA',1,0),('SYS_AREA_34',34,'宁夏','0',1,'SYS_AREA',1,0),('SYS_AREA_AHS',5,'安徽省','0',1,'SYS_AREA',1,0),('SYS_AREA_BEIJING',19,'北京','0',1,'SYS_AREA',1,0),('SYS_AREA_CHONGQING',22,'重庆','0',1,'SYS_AREA',1,0),('SYS_AREA_FUJIAN',12,'福建省','0',1,'SYS_AREA',1,0),('SYS_AREA_GDS',8,'广东省','0',1,'SYS_AREA',1,0),('SYS_AREA_GXS',9,'广西省','0',1,'SYS_AREA',1,0),('SYS_AREA_HAINAN',11,'海南省','0',1,'SYS_AREA',1,0),('SYS_AREA_HEBEI',18,'河北省','0',1,'SYS_AREA',1,0),('SYS_AREA_HNS',6,'河南省','0',1,'SYS_AREA',1,0);
/*!40000 ALTER TABLE `s_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_db`
--

DROP TABLE IF EXISTS `s_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_db` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间，毫秒数',
  `CREATOR_NAME` varchar(50) NOT NULL COMMENT '创建用户名',
  `TYPE` tinyint(4) NOT NULL COMMENT '类型：0_导入，1_导出',
  `NAME` varchar(100) NOT NULL COMMENT '导出、导入名字',
  `FILE_ID` varchar(36) NOT NULL COMMENT '导出、导入文件的ID，关联S_FIL表主键',
  `EXPORT_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '导出类型：0_全表，1_选择表',
  `SUMMARY` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_db`
--

LOCK TABLES `s_db` WRITE;
/*!40000 ALTER TABLE `s_db` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_db` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_department`
--

DROP TABLE IF EXISTS `s_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_department` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间,如：20131023132301',
  `ORDER_NUM` int(11) NOT NULL COMMENT '顺序号',
  `NAME` varchar(200) NOT NULL COMMENT '机构全称',
  `SIMPLE_NAME` varchar(200) DEFAULT NULL COMMENT '简称',
  `TYPE` tinyint(4) NOT NULL COMMENT '机构类型：0_独立单位，1_部门',
  `IS_SYSTEM` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否系统机构：0_否，1_是,系统机构不允许普通管理员修改',
  `PARENT_ID` varchar(36) NOT NULL COMMENT '上级机构ID',
  `ATTRIBUTE` varchar(255) DEFAULT NULL COMMENT '机构属性，由业务定义并扩展',
  `CHILD_SUM` int(11) NOT NULL DEFAULT '0' COMMENT '下级子机构数量',
  `ADMINISTRATE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否启用单位管理员：0_否,1_是，只有类型为单位才能使用',
  `CHARGE_MAN` varchar(36) DEFAULT NULL COMMENT '机构主管，用户ID，只能存在一个',
  `MANAGER` varchar(36) DEFAULT NULL COMMENT '部门经理，用户ID，只能存在一个',
  `SUMMARY` varchar(255) DEFAULT NULL COMMENT '简介',
  `STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0_正常，1_已删除',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_department`
--

LOCK TABLES `s_department` WRITE;
/*!40000 ALTER TABLE `s_department` DISABLE KEYS */;
INSERT INTO `s_department` VALUES ('402881e75495f141015495ffca7a0001',1462805449294,1,'admin','admin',0,1,'0','',0,1,'','','',0);
/*!40000 ALTER TABLE `s_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_department_admin`
--

DROP TABLE IF EXISTS `s_department_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_department_admin` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `DEPART_ID` varchar(36) NOT NULL COMMENT '机构ID',
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(100) NOT NULL COMMENT '用户名称（冗余）',
  PRIMARY KEY (`ID`),
  KEY `DEPART_ID` (`DEPART_ID`),
  CONSTRAINT `FK_S_DEPART_ADMIN` FOREIGN KEY (`DEPART_ID`) REFERENCES `s_department` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_department_admin`
--

LOCK TABLES `s_department_admin` WRITE;
/*!40000 ALTER TABLE `s_department_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_department_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_file`
--

DROP TABLE IF EXISTS `s_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_file` (
  `ID` varchar(36) NOT NULL,
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `CREATOR` varchar(30) DEFAULT NULL COMMENT '创建人',
  `CONTENT_TYPE` varchar(120) NOT NULL COMMENT '文件mime类型',
  `FILE_NAME` varchar(255) NOT NULL COMMENT '文件原始名称',
  `PATH` varchar(255) DEFAULT NULL COMMENT '文件在服务器保存的相对路径',
  `EXT` varchar(6) DEFAULT NULL COMMENT '扩展名',
  `CONTENT` blob COMMENT '文件二进制内容',
  `STORE_TYPE` smallint(6) NOT NULL COMMENT '存储类型：0_文件，1_二进制',
  `SUMMARY` varchar(100) DEFAULT NULL COMMENT '备注',
  `GROUP_ID` varchar(36) DEFAULT NULL COMMENT '组编号，业务可以把多个文件归类一个组',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_file`
--

LOCK TABLES `s_file` WRITE;
/*!40000 ALTER TABLE `s_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_flow_bind`
--

DROP TABLE IF EXISTS `s_flow_bind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_flow_bind` (
  `ID` bigint(20) NOT NULL,
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间，毫秒值',
  `TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '绑定类型：0_单位，1_部门，2_角色，3_岗位，9_个人',
  `BIND_ID` varchar(36) NOT NULL COMMENT '绑定的ID，如：单位ID、部门ID、用户ID等',
  `BIND_NAME` varchar(50) NOT NULL COMMENT '绑定的名字，冗余，如：单位名称、部门名称等',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '绑定的流程定义ID',
  `PROCESS_DEFINE_NAME` varchar(100) NOT NULL COMMENT '绑定流程定义名称，冗余',
  `SUMMARY` varchar(100) DEFAULT NULL COMMENT '备注',
  `BUSINESS_TYPE` varchar(12) DEFAULT NULL COMMENT '业务类别ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程绑定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_flow_bind`
--

LOCK TABLES `s_flow_bind` WRITE;
/*!40000 ALTER TABLE `s_flow_bind` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_flow_bind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_function`
--

DROP TABLE IF EXISTS `s_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_function` (
  `ID` varchar(36) NOT NULL,
  `ORDER_NUM` int(11) NOT NULL,
  `F_TYPE` int(11) NOT NULL COMMENT '功能类型：0_功能组，1_功能项',
  `NAME` varchar(200) NOT NULL COMMENT '功能名称',
  `URL` varchar(200) DEFAULT NULL COMMENT '功能地址',
  `POINTER` varchar(255) DEFAULT NULL COMMENT '功能点：id,id,..',
  `IS_RUN` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否启用：0_禁用，1_启用',
  `PARENT_ID` varchar(36) DEFAULT NULL COMMENT '上级菜单组id',
  `OPEN_STYLE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '菜单打开方式:0_iframe,1_弹出对话框,2_覆盖主窗口,9_新浏览器窗口',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_function`
--

LOCK TABLES `s_function` WRITE;
/*!40000 ALTER TABLE `s_function` DISABLE KEYS */;
INSERT INTO `s_function` VALUES ('0',99,-1,'运行维护',NULL,NULL,1,'-1',0),('1',1,0,'系统管理',NULL,NULL,1,'0',0),('10',10,1,'功能菜单','/admin/function/index.do','FUNC_ADD,FUNC_EDIT,FUNC_DEL',1,'1',0),('12',12,1,'代码管理','/admin/code/index.do','CODE_ADD,CODE_EDIT,CODE_DEL',1,'1',0),('13',13,1,'组织机构','/admin/department/index.do','DEPT_ADD,DEPT_VIEW,DEPT_DEL,DEPT_EASE,DEPT_EDIT',1,'1',0),('14',14,1,'系统用户','/admin/user/index.do','USER_ADD,USER_DEL,USER_EASE,USER_EDIT,PASS_RESET',1,'1',0),('15',15,1,'角色权限','/admin/role/index.do','ROLE_ADD,ROLE_FUNC,ROLE_USER,ROLE_DEL',1,'1',0),('16',16,1,'活动用户','/admin/activeuser/index.do',NULL,1,'1',0),('17',17,1,'日志检查','/admin/log/index.do',NULL,1,'1',0),('18',18,1,'上传附件','/admin/file/index.do',NULL,1,'1',0),('20',2,0,'流程管理',NULL,NULL,1,'0',0),('202',202,1,'流程定义管理','/flow/define/index.do',NULL,1,'20',0),('203',203,1,'流程运行监控','/flow/instance/index.do',NULL,1,'20',0),('204',204,1,'正在办理任务','/flow/instance/await_task.do',NULL,1,'20',0),('205',205,1,'流程绑定设置','/flow/bind/index.do',NULL,1,'20',0),('209',209,1,'流程测试','/flow/test/index.do',NULL,1,'20',0),('3',3,0,'首页设置',NULL,NULL,1,'0',0),('31',31,1,'上传LOGO','/admin/home/logo.do',NULL,1,'3',0),('4',40,0,'应用管理','','',1,'0',0),('41',41,1,'我的应用','/appos/app/index.do','APP_ADD,APP_EDIT,APP_DEL',1,'4',0),('9',9,1,'参数选项','/admin/args/index.do','ARGS_SAVE',1,'1',0);
/*!40000 ALTER TABLE `s_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_log`
--

DROP TABLE IF EXISTS `s_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_log` (
  `ID` varchar(36) NOT NULL,
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间，毫秒数',
  `LOGIN_USER` varchar(36) NOT NULL COMMENT '用户登录ID',
  `TYPE` tinyint(4) NOT NULL COMMENT '日志类型:0_一般操作,1_登录,2_注销,3_删除数据',
  `CONTENT` varchar(255) NOT NULL COMMENT '日志内容',
  `SUMMARY` varchar(255) DEFAULT NULL COMMENT '备用字段',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_log`
--

LOCK TABLES `s_log` WRITE;
/*!40000 ALTER TABLE `s_log` DISABLE KEYS */;
INSERT INTO `s_log` VALUES ('0bd88804-a4ef-4eee-a404-55a9dfd31455',1462806763214,'supervisor',0,'用户登录并进入首页',NULL),('0db05a21-063c-4350-9a65-a52ce242228f',1462805533639,'fzcheng',1,'用户登录',NULL),('0fa42b64-5f12-4f92-ba37-c9c17f789663',1462888835260,'fzcheng',0,'用户登录并进入首页',NULL),('100a4421-bc6a-4c7e-b08d-f5b5427fea53',1462840789774,'fzcheng',0,'用户登录并进入首页',NULL),('169a5065-d211-479c-b172-72156bf9be86',1462805176272,'supervisor',1,'用户登录',NULL),('17051cf7-902b-4b41-9d70-fc242bcbcdb6',1463067087820,'supervisor',1,'用户登录',NULL),('21609e9d-36aa-4d2e-b9ea-03c4d5ff762e',1462888835251,'fzcheng',1,'用户登录',NULL),('22dda077-159f-403f-a678-e73932ec003f',1463067167943,'supervisor',0,'用户试图访问未授权的页面',NULL),('26e62b71-d59e-404e-afe2-6f122cdf7ac1',1462805495436,'supervisor',4,'创建用户fzchengfzcheng',NULL),('30f58070-f712-4092-9da6-a488f3f10a43',1463065393137,'supervisor',1,'用户登录',NULL),('3a7a61e1-214d-44da-a338-45a16e3fd1ae',1463067128166,'supervisor',0,'用户试图访问未授权的页面',NULL),('3e980648-adbf-4ffc-b902-208d690cc32e',1462805604562,'supervisor',1,'用户登录',NULL),('52d6547f-842f-4b7b-92d2-07911a182bb1',1462840803633,'fzcheng',0,'用户登录并进入首页',NULL),('554d77c2-5405-43f8-8cb6-bdb808d276b6',1462805855172,'fzcheng',1,'用户登录',NULL),('5ae70943-b4fc-47cd-bf0e-57edc3316f80',1462839786393,'supervisor',1,'用户登录',NULL),('68795c49-bc9e-46ac-9b21-5a4b915f247c',1462805183856,'supervisor',0,'用户登录并进入首页',NULL),('70da8707-538b-42aa-927d-dfbfeeb47259',1462804640140,'supervisor',1,'用户登录',NULL),('719c7571-561b-4275-99bb-005da464d7ea',1462840178526,'fzcheng',1,'用户登录',NULL),('7dfc02c2-2367-4ee4-8bc1-d37c1c52522f',1462806763207,'supervisor',1,'用户登录',NULL),('7e93f1aa-7176-4267-aa57-d18123f4a52e',1462805032554,'supervisor',1,'用户登录',NULL),('846e8b83-4209-42a3-baf2-322053e4b4a1',1462840209910,'fzcheng',0,'用户试图访问未授权的页面',NULL),('860287c5-86d9-4d90-baaa-62c28a5ebc6f',1462805665937,'supervisor',4,'创建角色admin',NULL),('8db6a7ce-63d1-4079-a5e3-d3e480d79683',1462805861970,'fzcheng',0,'用户登录并进入首页',NULL),('984f4621-adc1-4f22-a5b8-e41d98da89fe',1462805533658,'fzcheng',0,'用户登录并进入首页',NULL),('9a34cc9c-d739-4ae9-bfd7-8c07f66da334',1462840794325,'fzcheng',0,'用户登录并进入首页',NULL),('a0d7ff37-da15-4d4c-a4e9-4351e1d09b58',1462805569929,'fzcheng',0,'用户登录并进入首页',NULL),('a822b743-dd97-4696-acaa-f2e1aac23d38',1462805807103,'supervisor',5,'改变角色功能{202=, 203=, 204=, 205=, 209=}',NULL),('a964b052-79f1-451b-be37-3e8a26720341',1463065393175,'supervisor',0,'用户登录并进入首页',NULL),('bf6c5b07-aa51-4a6d-b7ed-f03c376e708b',1462840227933,'fzcheng',0,'用户试图访问未授权的页面',NULL),('c499bcb0-4268-4f8f-be76-4509d6bb5fe4',1462805083438,'supervisor',1,'用户登录',NULL),('d0ce04a5-5356-45f3-a0cd-fa9dcf1e6994',1462805449484,'supervisor',4,'创建机构admin',NULL),('d7688da1-3820-4853-9197-f0500a972648',1462805682875,'supervisor',5,'改变角色用户[402881e75495f141015496007e880002]',NULL),('e29c4167-c027-4916-842a-756ff67471e9',1462805604569,'supervisor',0,'用户登录并进入首页',NULL),('e2e9fc0e-f775-4bee-9d15-16d38a41205c',1462840178535,'fzcheng',0,'用户登录并进入首页',NULL),('e47227bb-c9fb-4b5f-9755-e4b1787a5eac',1462839786401,'supervisor',0,'用户登录并进入首页',NULL),('e95a01cf-e4b5-4866-9195-77fd5d6f3fbd',1462805563520,'fzcheng',0,'用户登录并进入首页',NULL),('f26ec41e-e440-4181-84d4-2ac589fd00a1',1463067087881,'supervisor',0,'用户登录并进入首页',NULL),('f6668283-2c0c-41af-8b73-dd095dc74418',1462805855205,'fzcheng',0,'用户登录并进入首页',NULL);
/*!40000 ALTER TABLE `s_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_mobile_login`
--

DROP TABLE IF EXISTS `s_mobile_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_mobile_login` (
  `ID` varchar(50) NOT NULL COMMENT 'SESSION_ID：用户loginID的编码结果',
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间，毫秒',
  `UPDATE_TIME` bigint(20) NOT NULL COMMENT '更新时间，毫秒',
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `LOGIN_ID` varchar(50) DEFAULT NULL,
  `EQUIPMENT_NAME` varchar(50) DEFAULT NULL COMMENT '设备名称',
  `EQUIPMENT_OS` varchar(20) DEFAULT NULL COMMENT '设备操作系统',
  `LOGIN_COUNT` int(11) NOT NULL DEFAULT '1' COMMENT '登陆次数',
  `SUMMARY` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机登录记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_mobile_login`
--

LOCK TABLES `s_mobile_login` WRITE;
/*!40000 ALTER TABLE `s_mobile_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `s_mobile_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_pointer`
--

DROP TABLE IF EXISTS `s_pointer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_pointer` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `NAME` varchar(100) NOT NULL COMMENT '功能点名称',
  `ORDER_NUM` int(11) NOT NULL COMMENT '顺序号',
  `SELECTED` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '默认选择：0_否1_是',
  `FUNCTION_ID` varchar(36) NOT NULL COMMENT '对应功能ID',
  `URL` varchar(200) DEFAULT NULL COMMENT '功能点对应的URL',
  PRIMARY KEY (`ID`),
  KEY `FUNCTION_ID` (`FUNCTION_ID`),
  CONSTRAINT `FK_S_POINTER_S_FUNCTION` FOREIGN KEY (`FUNCTION_ID`) REFERENCES `s_function` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_pointer`
--

LOCK TABLES `s_pointer` WRITE;
/*!40000 ALTER TABLE `s_pointer` DISABLE KEYS */;
INSERT INTO `s_pointer` VALUES ('APP_ADD','添加应用',1,0,'16','/appos/app/saveApp.do'),('APP_DEL','删除应用',3,0,'16','/appos/app/delApp.do'),('APP_EDIT','编辑应用',2,0,'16','/appos/app/editApp.do'),('ARGS_SAVE','更新选项',1,0,'9','/admin/args/save.do'),('CODE_ADD','添加代码项',1,0,'12','/admin/code/saveCode.do'),('CODE_DEL','删除代码项',3,0,'12','/admin/code/deleteCodeInfo.do'),('CODE_EDIT','修改代码项',2,0,'12','/admin/code/updateCode.do'),('DEPT_ADD','添加机构',1,0,'13','/admin/department/save.do'),('DEPT_DEL','删除机构',4,0,'13','/admin/department/deleteOrg.do'),('DEPT_EASE','彻底删除',5,0,'13','/admin/department/easeOrg.do'),('DEPT_EDIT','编辑机构',6,0,'13','/admin/department/edit.do'),('DEPT_VIEW','查看机构',3,0,'13','/admin/department/show.do'),('FUNC_ADD','添加新功能',1,0,'10','/admin/function/show_add.do'),('FUNC_DEL','删除功能',3,0,'10','/admin/function/remove.do'),('FUNC_EDIT','编辑功能',2,0,'10','/admin/function/show_edit.do'),('PASS_RESET','重置密码',5,0,'14','/admin/user/pass_reset.do'),('ROLE_ADD','创建角色',1,0,'15','/admin/role/createRole.do'),('ROLE_DEL','删除角色',4,0,'15','/admin/role/remove.do'),('ROLE_FUNC','设置功能',2,0,'15','/admin/role/saveRoleFunc.do'),('ROLE_USER','设置用户',3,0,'15','/admin/role/saveRoleUser.do'),('USER_ADD','添加用户',1,0,'14','/admin/user/save.do'),('USER_DEL','删除用户',2,0,'14','/admin/user/delete.do'),('USER_EASE','彻底删除用户',3,0,'14','/admin/user/ease.do'),('USER_EDIT','编辑用户',4,0,'14','/admin/user/edit.do');
/*!40000 ALTER TABLE `s_pointer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_role`
--

DROP TABLE IF EXISTS `s_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_role` (
  `ID` varchar(36) NOT NULL,
  `CREATE_TIME` bigint(20) NOT NULL COMMENT '创建时间',
  `R_TYPE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色类型：0_系统，1_用户类型',
  `NAME` varchar(200) NOT NULL COMMENT '角色名称',
  `SUMMARY` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_role`
--

LOCK TABLES `s_role` WRITE;
/*!40000 ALTER TABLE `s_role` DISABLE KEYS */;
INSERT INTO `s_role` VALUES ('402881e75495f14101549603188e0003',1462805665934,1,'admin','admin');
/*!40000 ALTER TABLE `s_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_role_func`
--

DROP TABLE IF EXISTS `s_role_func`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_role_func` (
  `ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `FUNC_ITEM_ID` varchar(36) NOT NULL COMMENT '功能项ID',
  `POINTER` varchar(255) DEFAULT NULL COMMENT '功能点IDS，用户英文逗号分隔',
  PRIMARY KEY (`ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `FK_S_ROLE_FUNC_S_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `S_ROLE` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色对应功能表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_role_func`
--

LOCK TABLES `s_role_func` WRITE;
/*!40000 ALTER TABLE `s_role_func` DISABLE KEYS */;
INSERT INTO `s_role_func` VALUES ('2da3a0cf-57cf-4193-96ab-17966662b0f5','402881e75495f14101549603188e0003','204',''),('6af770b0-d427-4f56-9bc1-888a54cf8a3d','402881e75495f14101549603188e0003','209',''),('7d1656b6-31ba-4f55-bebf-d299d76a4d8b','402881e75495f14101549603188e0003','202',''),('c11e4ee0-c873-45c5-8824-9ef17b526bfc','402881e75495f14101549603188e0003','205',''),('dc2f5f39-e8f1-4c0d-9e37-4443d4d98813','402881e75495f14101549603188e0003','203',''),('df7604f0-eac0-4d6f-80e5-5258a9bc5cd2','402881e75495f14101549603188e0003','41','APP_EDIT');
/*!40000 ALTER TABLE `s_role_func` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_role_user`
--

DROP TABLE IF EXISTS `s_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_role_user` (
  `ID` varchar(36) NOT NULL,
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`ID`),
  KEY `ROLE_ID` (`ROLE_ID`),
  CONSTRAINT `FK_S_ROLE_USER_S_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `s_role` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_role_user`
--

LOCK TABLES `s_role_user` WRITE;
/*!40000 ALTER TABLE `s_role_user` DISABLE KEYS */;
INSERT INTO `s_role_user` VALUES ('a8390e39-399d-40ee-9e1d-097775f30198','402881e75495f14101549603188e0003','402881e75495f141015496007e880002',NULL);
/*!40000 ALTER TABLE `s_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_user_core`
--

DROP TABLE IF EXISTS `s_user_core`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_user_core` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `CREATE_TIME` bigint(20) NOT NULL,
  `ORDER_NUM` int(11) NOT NULL,
  `LOGIN_ID` varchar(50) NOT NULL COMMENT '登录ID',
  `NAME` varchar(100) NOT NULL COMMENT '用户姓名',
  `PINYIN` varchar(100) DEFAULT NULL COMMENT '用户名拼音',
  `TYPE` tinyint(4) NOT NULL DEFAULT '2' COMMENT '用户类型：0_超级管理员，1_单位管理员，2_普通用户',
  `ENABLE` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否启用：0_否，1_是',
  `STATUS` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态：0_正常，1_删除',
  `ORG_ID` varchar(36) NOT NULL COMMENT '用户所属单位ID',
  `DEPARTMENT_ID` varchar(36) DEFAULT NULL COMMENT '用户所在部门ID',
  `SUMMARY` varchar(200) DEFAULT NULL,
  `PASSWORD` varchar(50) NOT NULL COMMENT '密码',
  `STYLE` varchar(20) NOT NULL DEFAULT 'simple' COMMENT '界面风格',
  `FROM_TYPE` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户来源：0_系统内部，1_外部（如：供应商）目前不细化',
  `FROM_TYPE_ID` varchar(36) DEFAULT NULL COMMENT '来源细分，通过代码引用',
  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '外部用户，需要用此冗余字段显示单位名',
  `SEX` tinyint(4) NOT NULL DEFAULT '1' COMMENT '性别：0_女,1_男',
  `TEL` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `MOBILE` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '邮件地址',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户核心表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_user_core`
--

LOCK TABLES `s_user_core` WRITE;
/*!40000 ALTER TABLE `s_user_core` DISABLE KEYS */;
INSERT INTO `s_user_core` VALUES ('402881e75495f141015496007e880002',1462805495417,1,'fzcheng','fzcheng',NULL,1,1,0,'402881e75495f141015495ffca7a0001',NULL,'','388869d99993a5dc4dc814794c002c417fcb14ac','simple',0,NULL,'admin',1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `s_user_core` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_accredit`
--

DROP TABLE IF EXISTS `sm_accredit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_accredit` (
  `id` bigint(20) NOT NULL,
  `auth_id` varchar(20) DEFAULT NULL,
  `auth_type` int(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `end_time` bigint(20) DEFAULT NULL,
  `file_id` varchar(36) DEFAULT NULL,
  `jar_name` varchar(50) DEFAULT NULL,
  `mac_address` varchar(20) DEFAULT NULL,
  `org_name` varchar(50) DEFAULT NULL,
  `project_name` varchar(100) DEFAULT NULL,
  `start_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_accredit`
--

LOCK TABLES `sm_accredit` WRITE;
/*!40000 ALTER TABLE `sm_accredit` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_accredit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_biz_data`
--

DROP TABLE IF EXISTS `sw_biz_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_biz_data` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `CREATE_TIME` decimal(14,0) NOT NULL COMMENT '创建时间',
  `PROCESS_INST_ID` varchar(36) NOT NULL COMMENT '流程实例ID',
  `NAME` varchar(100) NOT NULL COMMENT '数据名称',
  `VALUE` varchar(300) NOT NULL COMMENT '数据值',
  `DATA_TYPE` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '数据类型:0_字符串,1_整形,2_浮点,3_双精度,4_布尔',
  PRIMARY KEY (`ID`),
  KEY `PROCESS_INST_ID` (`PROCESS_INST_ID`),
  CONSTRAINT `FK_SW_BIZ_DATA_007` FOREIGN KEY (`PROCESS_INST_ID`) REFERENCES `sw_process_inst` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_biz_data`
--

LOCK TABLES `sw_biz_data` WRITE;
/*!40000 ALTER TABLE `sw_biz_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_biz_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_config`
--

DROP TABLE IF EXISTS `sw_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_config` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `SXH` decimal(15,0) NOT NULL COMMENT '顺序号',
  `NAME` varchar(200) NOT NULL COMMENT '参数描述',
  `DATA_TYPE` decimal(2,0) NOT NULL COMMENT '数据类型：0_字符串，1_整数，2_浮点数，3_布尔',
  `DATA_VALUE` varchar(300) NOT NULL COMMENT '参数值',
  `DEFAULT_VALUE` varchar(300) NOT NULL COMMENT '默认值',
  `GROUP_NAME` varchar(50) DEFAULT NULL COMMENT '分组名称（可选）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_config`
--

LOCK TABLES `sw_config` WRITE;
/*!40000 ALTER TABLE `sw_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_design_anchor_in`
--

DROP TABLE IF EXISTS `sw_design_anchor_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_design_anchor_in` (
  `ID` varchar(36) NOT NULL,
  `NODE_ID` varchar(36) NOT NULL COMMENT '任务节点ID',
  `POSITION` varchar(20) NOT NULL COMMENT '进入本节点的位置，如：TopCenter',
  `FROM_NODE` varchar(36) NOT NULL COMMENT '上一个节点ID',
  `FROM_POSITION` varchar(20) NOT NULL COMMENT '上一个离开线的位置，如：BottomCenter',
  PRIMARY KEY (`ID`),
  KEY `NODE_ID` (`NODE_ID`),
  CONSTRAINT `FK_SW_DESIGN_02` FOREIGN KEY (`NODE_ID`) REFERENCES `sw_design_node` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计器-连接点进入数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_design_anchor_in`
--

LOCK TABLES `sw_design_anchor_in` WRITE;
/*!40000 ALTER TABLE `sw_design_anchor_in` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_design_anchor_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_design_anchor_out`
--

DROP TABLE IF EXISTS `sw_design_anchor_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_design_anchor_out` (
  `ID` varchar(36) NOT NULL,
  `NODE_ID` varchar(36) NOT NULL COMMENT '任务节点ID',
  `POSITION` varchar(20) NOT NULL COMMENT '离开锚点位置信息',
  `NEXT_NODE` varchar(36) NOT NULL COMMENT '下一个节点ID',
  `NEXT_POSITION` varchar(20) NOT NULL COMMENT '下一个进入线的位置，如：TopCenter',
  `SUMMARY` varchar(50) DEFAULT NULL COMMENT '输出连接线上标注文字',
  PRIMARY KEY (`ID`),
  KEY `NODE_ID` (`NODE_ID`),
  CONSTRAINT `FK_SW_DESIGN_01` FOREIGN KEY (`NODE_ID`) REFERENCES `sw_design_node` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计器-任务节点连接线出来信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_design_anchor_out`
--

LOCK TABLES `sw_design_anchor_out` WRITE;
/*!40000 ALTER TABLE `sw_design_anchor_out` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_design_anchor_out` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_design_node`
--

DROP TABLE IF EXISTS `sw_design_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_design_node` (
  `ID` varchar(36) NOT NULL COMMENT '任务节点ID，注意这个和任务定义中的ID保持一致。',
  `X` varchar(20) NOT NULL COMMENT 'X轴坐标，目前是像素，如：230px',
  `Y` varchar(20) NOT NULL COMMENT 'Y轴坐标',
  `IN_POSITIONS` varchar(100) DEFAULT NULL COMMENT '可使用进入的节点位置信息，用逗号分隔，如：LeftMiddle,TopCenter',
  `OUT_POSITIONS` varchar(100) DEFAULT NULL COMMENT '可使用离开的节点位置信息，用逗号分隔，如：LeftMiddle,TopCenter',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '流程定义ID',
  PRIMARY KEY (`ID`),
  KEY `PROCESS_DEFINE_ID` (`PROCESS_DEFINE_ID`),
  CONSTRAINT `FK_SW_DESIGN_NODE_009` FOREIGN KEY (`PROCESS_DEFINE_ID`) REFERENCES `sw_process_define` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计器-任务节点信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_design_node`
--

LOCK TABLES `sw_design_node` WRITE;
/*!40000 ALTER TABLE `sw_design_node` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_design_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_process_define`
--

DROP TABLE IF EXISTS `sw_process_define`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_process_define` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `NAME` varchar(200) NOT NULL COMMENT '流程定义名称',
  `CREATE_TIME` decimal(14,0) NOT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(36) DEFAULT NULL COMMENT '创建用户',
  `PUBLISH_STATUS` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '发布状态:0_未发布,1_已发布,默认0',
  `INTER_IDENTIFIER` varchar(36) NOT NULL COMMENT '流程内部标识号',
  `VERSION` decimal(3,0) NOT NULL DEFAULT '1' COMMENT '当前流程定义版本号',
  `SUB_PROCESS` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '是否子流程:0_否;1_是(子流程专用)',
  `LISTENER_CREATE` varchar(200) DEFAULT NULL COMMENT '流程创建监听器ID',
  `LISTENER_END` varchar(200) DEFAULT NULL COMMENT '流程结束监听器ID',
  `SUMMARY` varchar(300) DEFAULT NULL COMMENT '流程描述',
  `BUSINESS_TYPE` varchar(2) NOT NULL DEFAULT 'A' COMMENT '业务类型:使用26个大写英文字母表示,默认A',
  `DELETE_STATUS` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '删除状态：0_正常1,_已删除',
  `PAGE_URL_ID` varchar(36) DEFAULT NULL COMMENT '业务集成页面ID',
  `WORKDAY_LIMIT` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '工作日限制,默认0天',
  `PICTURE` varchar(36) DEFAULT NULL COMMENT '流程图路径',
  `REMIND_TEMPLATE` varchar(600) DEFAULT NULL COMMENT '提醒内容模板',
  `OLD_BASE_LABEL` varchar(36) DEFAULT NULL COMMENT '项目基本信息标签,此字段为了兼容老流程平台',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程定义表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_process_define`
--

LOCK TABLES `sw_process_define` WRITE;
/*!40000 ALTER TABLE `sw_process_define` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_process_define` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_process_inst`
--

DROP TABLE IF EXISTS `sw_process_inst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_process_inst` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `NAME` varchar(200) NOT NULL COMMENT '流程名称',
  `PROCESS_DEFINE_ID` varchar(36) NOT NULL COMMENT '流程定义ID',
  `CREATE_TIME` decimal(14,0) NOT NULL COMMENT '创建时间',
  `USER_ID` varchar(36) NOT NULL COMMENT '流程创建用户ID',
  `USER_NAME` varchar(50) NOT NULL COMMENT '流程创建用户名称',
  `IS_END` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '是否结束:0_运行中,1_已结束',
  `END_TIME` decimal(14,0) DEFAULT NULL COMMENT '结束时间',
  `IS_TERMINATE` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '是否被终止:0_否1_是',
  `TERMINATE_TIME` decimal(14,0) DEFAULT NULL COMMENT '终止时间',
  `BUSINESS_ID` varchar(36) DEFAULT NULL COMMENT '业务数据ID:子流程可以没有业务ID',
  `BUSINESS_TYPE` varchar(2) NOT NULL DEFAULT 'A' COMMENT '业务类型:使用26个大写英文字母表示,默认A',
  `SUB_PROCESS` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '子流程:0_否,1_是，查询时业务只需要主流程实例',
  `IS_PAUSE` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '是否暂停:0_否,1_是',
  `PAUSE_TIME` decimal(14,0) DEFAULT NULL COMMENT '暂停时间',
  `GLOBAL_PROCESS_INST` varchar(36) NOT NULL COMMENT '全局流程实例ID，对于子流程任务为主流程实例ID',
  PRIMARY KEY (`ID`),
  KEY `PROCESS_DEFINE_ID` (`PROCESS_DEFINE_ID`),
  CONSTRAINT `FK_SW_PROCESS_INST_005` FOREIGN KEY (`PROCESS_DEFINE_ID`) REFERENCES `sw_process_define` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_process_inst`
--

LOCK TABLES `sw_process_inst` WRITE;
/*!40000 ALTER TABLE `sw_process_inst` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_process_inst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_task_inst`
--

DROP TABLE IF EXISTS `sw_task_inst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_task_inst` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `NAME` varchar(200) NOT NULL COMMENT '任务名称',
  `CREATE_TIME` decimal(14,0) NOT NULL COMMENT '创建时间',
  `SUB_PROCESS` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '子流程任务:0_否,1_是,标识这个任务类型是否子流程任务,冗余字段',
  `TASK_STATUS` decimal(2,0) NOT NULL DEFAULT '0' COMMENT '任务状态:-1_初始化,0_未完成,1_已完成,9_系统自动完成',
  `UPDATE_TIME` decimal(14,0) DEFAULT NULL COMMENT '任务更新时间',
  `END_TIME` decimal(14,0) DEFAULT NULL COMMENT '结束时间',
  `USER_ID` varchar(36) NOT NULL COMMENT '执行人ID',
  `USER_NAME` varchar(50) NOT NULL COMMENT '执行人名称',
  `OPINION` varchar(200) DEFAULT NULL COMMENT '执行人意见',
  `PREVIOUS_TASK` varchar(36) NOT NULL COMMENT '上一任务实例ID',
  `NEXT_TASK` varchar(200) DEFAULT NULL COMMENT '下一任务实例ID,多个用逗号分开',
  `TASK_DEFINE_ID` varchar(36) NOT NULL COMMENT '任务定义ID',
  `PROCESS_INST_ID` varchar(36) NOT NULL COMMENT '当前任务所在流程实例ID',
  `PRIMARY_TASK_INST` varchar(36) DEFAULT NULL COMMENT '子任务实例所在的主任务实例ID',
  `GLOBAL_PROCESS_INST` varchar(36) NOT NULL COMMENT '全局流程实例ID，对于普通任务为流程实例ID，对于子流程任务为主流程实例ID',
  PRIMARY KEY (`ID`),
  KEY `PROCESS_INST_ID` (`PROCESS_INST_ID`),
  CONSTRAINT `FK_SW_TASK_INST_006` FOREIGN KEY (`PROCESS_INST_ID`) REFERENCES `sw_process_inst` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_task_inst`
--

LOCK TABLES `sw_task_inst` WRITE;
/*!40000 ALTER TABLE `sw_task_inst` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_task_inst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sw_task_plugin`
--

DROP TABLE IF EXISTS `sw_task_plugin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sw_task_plugin` (
  `ID` varchar(36) NOT NULL COMMENT '主键',
  `TASK_DEFINE_ID` varchar(36) NOT NULL COMMENT '任务定义ID',
  `MOD_DOCUMENT` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '修改文档权限:0_否,1_是',
  `UPLOAD_FILE` decimal(1,0) NOT NULL DEFAULT '0' COMMENT '必须上传附件:0_否,1_是',
  `EDIT_DOC_NAME` varchar(30) DEFAULT NULL COMMENT '编辑文档按钮名称',
  PRIMARY KEY (`ID`),
  KEY `TASK_DEFINE_ID` (`TASK_DEFINE_ID`),
  CONSTRAINT `FK_SW_TASK_PLUGIN_008` FOREIGN KEY (`TASK_DEFINE_ID`) REFERENCES `SW_TASK_DEFINE` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程任务定义业务插件表：此表为流程对业务支持的扩展，因此从逻辑上从流程核心表中分离';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sw_task_plugin`
--

LOCK TABLES `sw_task_plugin` WRITE;
/*!40000 ALTER TABLE `sw_task_plugin` DISABLE KEYS */;
/*!40000 ALTER TABLE `sw_task_plugin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yl_ali_callback_record`
--

DROP TABLE IF EXISTS `yl_ali_callback_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yl_ali_callback_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notify_time` varchar(45) DEFAULT NULL,
  `notify_type` varchar(45) DEFAULT NULL,
  `notify_id` varchar(45) DEFAULT NULL,
  `sign_type` varchar(45) DEFAULT NULL,
  `sign` varchar(256) DEFAULT NULL,
  `out_trade_no` varchar(64) DEFAULT NULL,
  `subject` varchar(128) DEFAULT NULL,
  `payment_type` varchar(4) DEFAULT NULL,
  `trade_no` varchar(64) DEFAULT NULL,
  `trade_status` varchar(45) DEFAULT NULL,
  `seller_id` varchar(45) DEFAULT NULL,
  `seller_email` varchar(100) DEFAULT NULL,
  `buyer_id` varchar(45) DEFAULT NULL,
  `buyer_email` varchar(100) DEFAULT NULL,
  `total_fee` float DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `body` varchar(512) DEFAULT NULL,
  `gmt_create` varchar(45) DEFAULT NULL,
  `gmt_payment` varchar(45) DEFAULT NULL,
  `is_total_fee_adjust` varchar(1) DEFAULT NULL,
  `use_coupon` varchar(1) DEFAULT NULL,
  `discount` varchar(45) DEFAULT NULL,
  `refund_status` varchar(45) DEFAULT NULL,
  `gmt_refund` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yl_ali_callback_record`
--

LOCK TABLES `yl_ali_callback_record` WRITE;
/*!40000 ALTER TABLE `yl_ali_callback_record` DISABLE KEYS */;
INSERT INTO `yl_ali_callback_record` VALUES (5,'2016-05-23 18:35:18','trade_status_sync','cc96f049c1bbdd2b9f066fafd942741i0a','RSA','A+24a1LcIdJ+gHnjCRu5Godxsvj/Jdh/2OZlImV5wpl1qMr1XBPYZN1c/HtK7/Ug10mmf1T/ZFRJ6+HfExKpVRwGe0aH/4TdoqeEoeBh/KmsOCK+n981n9yIqVEncM7yU21ufEaOTPZUfRzCiGc04LOhy98IWFFFxmjNEovh/Ts=','20160523183509716648','wares998','1','2016052321001004260289820029','WAIT_BUYER_PAY','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares998','2016-05-23 18:35:18',NULL,'Y','N','0.00',NULL,NULL),(6,'2016-05-23 18:35:20','trade_status_sync','2f49148ad4ec4b4ad63ad12b3c12af8i0a','RSA','PkhaQaikaKdmeQkq8geoSGYbgtuPAg5+hqj8W6UCico83RKgZyBby02KMDhc9j7lJKESZ6hZIUNblyYjavKK9vYW0r4tJsSFMpcYxFULhh/ZwyBubIAXuAM1ByfPQbzMco536ejrD3zjBz/uJoC+DdYI9i7LKrilv54VT+nyej4=','20160523183509716648','wares998','1','2016052321001004260289820029','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares998','2016-05-23 18:35:18','2016-05-23 18:35:19','N','N','0.00',NULL,NULL),(7,'2016-05-23 18:39:15','trade_status_sync','2f49148ad4ec4b4ad63ad12b3c12af8i0a','RSA','OSdkXiTQkW6t8tR714JMFBC4EG9CnGgG7jT9xpn78h0DNx7yLzRGQzDgifkN2YifvStrmtIXktoC47OwaDbW5mPQHfF0xkeibC1w1dtWNTWrdHcWxnbFJdGRdD6RWdnTxwj9MCZrrERJzw1XU+wfJJfI+g64xL5ib5iZckU4/Yg=','20160523183509716648','wares998','1','2016052321001004260289820029','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares998','2016-05-23 18:35:18','2016-05-23 18:35:19','N','N','0.00',NULL,NULL),(8,'2016-05-23 18:43:16','trade_status_sync','d844d7b4911e81b1323d3033d9f3a18i0a','RSA','o0gTBKHVg/soQg+1krxy8QeKXAU8MqCpkeqNtdL7Kj7h/6KnOzlYTY4t2+ldmIBANTtpYgdMve5ZyKs7mNope2iZ87m5ts4DBj6zT17MSa3k1M60VyCmEQ++qE25Y00D8XvUMX2g95L4fFf8NWVVcfZL+le9lex2HJwsVpjCS5Y=','20160523184307427205','wares6','1','2016052321001004260296033635','WAIT_BUYER_PAY','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares6','2016-05-23 18:43:16',NULL,'Y','N','0.00',NULL,NULL),(9,'2016-05-23 18:43:19','trade_status_sync','9e2894a7770a7c860338d1a16302da1i0a','RSA','NL3zPTRap2om9q50BH3Kp1eNAJUTrd6yjFjxZhld8P1IViAfzS8vrMWAfqXFcOS2GRBgDjMEljo63ceDy80S9Jz3KJgIbdUerfeqEx5w186e8dGyMK08m51QL4zYjbfG1mJx/vynchtBFk/IQkOZwVsWxT3l48JoXBFh/7gwi0c=','20160523184307427205','wares6','1','2016052321001004260296033635','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares6','2016-05-23 18:43:16','2016-05-23 18:43:18','N','N','0.00',NULL,NULL),(10,'2016-05-23 18:48:23','trade_status_sync','551beabc6e9d3220d59b90e4f888857i0a','RSA','CHFjIArdQ2mYijfwWotOmM/kitrvRiozfljCb7tynGiB8YJ/vN4g3kXINgnDfCjLqKd/Jj4J3jQE2UXlDcyCUKE74KUMyVevyY+S+0J8q9ZetKRnJrH6R99D9cbRj7NQWTlmxiFe/Oetnj29Tfr+LOEbXD2xOXdCPDVr+4wmGj0=','20160523184814989964','wares83','1','2016052321001004260281534724','WAIT_BUYER_PAY','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares83','2016-05-23 18:48:23',NULL,'Y','N','0.00',NULL,NULL),(11,'2016-05-23 18:48:25','trade_status_sync','8734992b9165811a335be2991fb347ai0a','RSA','cBdpjPygdTixZhmVpWrtIXDVbVDvZgICcUr8EF3h0E8V+a1ertiiD9MeLuPIxuvHHew8qv1xhpW8ujipWnjjcUo1naU6Jp/KYF/O0QCne3AIgu2PDtg76lUDvak/0SNnCDlHWVy5ta9tDnYZMitMUPewqxpShCLRYM3uQfBAG20=','20160523184814989964','wares83','1','2016052321001004260281534724','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares83','2016-05-23 18:48:23','2016-05-23 18:48:24','N','N','0.00',NULL,NULL),(12,'2016-05-23 18:50:42','trade_status_sync','2f49148ad4ec4b4ad63ad12b3c12af8i0a','RSA','HLm2e1iNFj5LqNMau8WniypNZt2qWCHKsZnjMCpPsUCX403ROV/6d3IUDrd1PagLgxKiuC+qKf3QqUFRr1GCU5B/BRpOAtKZ4HIhihFgcNLaL5lRdZYZxr1pNl09gAB04bFc9UyevmM1ccEsKB3HZlqSm5e+TUBzVgpv4HCbJYI=','20160523183509716648','wares998','1','2016052321001004260289820029','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares998','2016-05-23 18:35:18','2016-05-23 18:35:19','N','N','0.00',NULL,NULL),(13,'2016-05-23 18:55:52','trade_status_sync','1d9dfd59528cb3e494a3f98a6e90cb8i0a','RSA','i0hrqYZ/3pZbZkVMaG17Wp3wkKK3EpvtqM8Gf2W9zDzfg9RpLRBhHdJdAJuhKIMXja0t/m8MsEcXEoOCn57egFKHZwbc9cs7m+pg1r1GFnU3hjYEgkHfeOVSlpxPS83vjFFx7MliK690PxtYOoq386UjEIvsBEKqxHcV7dUuQo4=','20160523185542978724','wares373','1','2016052321001004260281534901','WAIT_BUYER_PAY','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares373','2016-05-23 18:55:51',NULL,'Y','N','0.00',NULL,NULL),(14,'2016-05-23 18:55:53','trade_status_sync','2b86c5d63e90e8ed28a81ea4625ee7di0a','RSA','Ijr1FGP0+HpPu48zjxrzgSqZFRpg/ao0g7HzprWnHgxMOqB+U0D80wjiq4ATNn+aJegRMsZR3Oe3Qc0hvPP7Q3sl9DooDBmXsy7Pst/6rnwsOPZhiPgWjvY6V0B5ZM9siXeb+NS7Ca/a+BpkboBzN3FAapNbvnPcTYgQer06e8Y=','20160523185542978724','wares373','1','2016052321001004260281534901','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares373','2016-05-23 18:55:51','2016-05-23 18:55:53','N','N','0.00',NULL,NULL),(15,'2016-05-23 19:01:23','trade_status_sync','a9a6bdbd2d21b930692b5fea5706f8di0a','RSA','Op6nkpdbAeU7FPBCrKSvXTueIyATkwcqBF2zWGPptbw4sjltdh2dbp+nty1sqkWSU5R078D+qUr/SI0psJel4wUuouqC5Y6FpJ873t3duaF+3KN0AFNIQZJJNqWSe5BEDBfqLjnwHW6TEEIO+/Gt0h6yAkBJy/ayW/JxC1Sz4WA=','20160523173706327824','wares102','1','2016052321001004260296700194','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares102','2016-05-23 17:37:15','2016-05-23 17:37:17','N','N','0.00',NULL,NULL),(16,'2016-05-23 19:04:50','trade_status_sync','389e91ed1348a0cc35f9213d5087486i0a','RSA','fJHh1P+BY3psQkVfM4JZtKT5fAtWHgYdK4/ENbvrJWMrLEjEEZLyf1fmetHArSNDY9ot7guhFpmZIDQcYyOlQfSRMaca7oLl+D8IFOLPuhh44G1BCwsc+R7oRyOQe3Ohm9GmWxf6uTux8y8OrRQKBXES5Zl/kFZT+WHHgFwPxEo=','20160523190232979499','wares146','1','2016052321001004260296235292','WAIT_BUYER_PAY','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares146','2016-05-23 19:04:50',NULL,'Y','N','0.00',NULL,NULL),(17,'2016-05-23 19:04:52','trade_status_sync','4c4a6444e257cbac3c158d12c751f98i0a','RSA','S1aYzpt0eddXykYxHnO4/T2okggvfkd5dznBprqfGeVPrDNLoeGbKQNQWMplrr0/taWRoVaerrN8qKqjxQhnslOc0WZMD4j+x4H8S/uFRusKK52yPsLnBuR5vJ0tJ+cCiUXQFoHqCOTaRkgB6fIqr5S17Avj1ocPp6CzJiRTluQ=','20160523190232979499','wares146','1','2016052321001004260296235292','TRADE_SUCCESS','2088221378971981','49151630@qq.com','2088202286656265','13818365949',0.01,1,0.01,'wares146','2016-05-23 19:04:50','2016-05-23 19:04:52','N','N','0.00',NULL,NULL);
/*!40000 ALTER TABLE `yl_ali_callback_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yl_app`
--

DROP TABLE IF EXISTS `yl_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yl_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(45) NOT NULL,
  `appcode` varchar(50) NOT NULL COMMENT 'app码',
  `appname` varchar(50) NOT NULL COMMENT '应用名称',
  `package_name` varchar(100) NOT NULL COMMENT '包名',
  `companyid` int(11) NOT NULL COMMENT '公司id',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `last_time` bigint(20) NOT NULL COMMENT '最后修改时间',
  `data` varchar(255) DEFAULT NULL,
  `orderid` varchar(255) DEFAULT NULL,
  `ordertype` int(11) DEFAULT NULL,
  `paychannel` int(11) DEFAULT NULL,
  `response` varchar(255) DEFAULT NULL,
  `tid` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `appid` (`appid`)
) ENGINE=InnoDB AUTO_INCREMENT=1032 DEFAULT CHARSET=utf8 COMMENT='应用app';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yl_app`
--

LOCK TABLES `yl_app` WRITE;
/*!40000 ALTER TABLE `yl_app` DISABLE KEYS */;
INSERT INTO `yl_app` VALUES (1017,'1001','1234567890','demo1','com.yeelo.demo1',1,160511121212,160511121212,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1018,'1002','1234567890','1','1',1,1463065540604,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1019,'1003','1234567890','1','1',1,1463065674015,1463065674015,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1020,'1004','1234567890','1','1',1,1463065710693,1463065710693,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1021,'1005','1234567890','1','1',1,1463065968298,1463065968298,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1022,'1006','1234567890','1','1',1,1463066097352,1463066097352,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1023,'1008','1234567890','1','1',1,1463066647249,1463066647249,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1024,'1009','1234567890','1','1',1,1463067097488,1463067097488,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1025,'1010','1234567890','1','1',1,1463067127805,1463067127805,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1026,'1011','1234567890','1','1',1,1463067167697,1463067167697,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1027,'1012','1234567890','1','1',1,1463067300278,1463067300278,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1028,'1013','1234567890','1','1',1,1463067437599,1463067437599,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1029,'1014','1234567890','1','1',1,1463067610456,1463067610456,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1030,'1015','1234567890','1','1',1,1463067787045,1463067787045,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(1031,'1016','1234567890','1','1',1,1463068350245,1463068350245,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `yl_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yl_app_market`
--

DROP TABLE IF EXISTS `yl_app_market`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yl_app_market` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(45) DEFAULT NULL,
  `market` int(11) DEFAULT NULL,
  `sdkid` int(11) DEFAULT NULL COMMENT '1-自有  2-内置',
  `remark` varchar(128) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `last_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_market` (`appid`,`market`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yl_app_market`
--

LOCK TABLES `yl_app_market` WRITE;
/*!40000 ALTER TABLE `yl_app_market` DISABLE KEYS */;
/*!40000 ALTER TABLE `yl_app_market` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yl_order`
--

DROP TABLE IF EXISTS `yl_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yl_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(45) DEFAULT NULL COMMENT '应用',
  `market` int(11) DEFAULT NULL COMMENT '渠道',
  `paychannel` int(11) DEFAULT NULL COMMENT '微信=801 支付宝=802 银联=803（暂未开通） 移动卡=804 电信卡=805 联通卡=806 其他=807',
  `userId` varchar(45) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `waresId` varchar(45) DEFAULT NULL,
  `wares` varchar(45) DEFAULT NULL,
  `orderid` varchar(45) DEFAULT NULL COMMENT '平台订单号',
  `cpOrderId` varchar(45) DEFAULT NULL,
  `payOrderid` varchar(45) DEFAULT NULL COMMENT '付费渠道订单号',
  `ext` varchar(512) DEFAULT NULL COMMENT '透传',
  `totalFee` int(11) DEFAULT NULL COMMENT '分',
  `create_time` datetime DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `isdeal` int(11) DEFAULT NULL,
  `retCode` varchar(45) DEFAULT NULL,
  `retMsg` varchar(45) DEFAULT NULL,
  `transfer_status` int(11) DEFAULT NULL,
  `transfer_count` int(11) DEFAULT NULL,
  `transfer_url` varchar(45) DEFAULT NULL COMMENT '通知地址',
  `ip` varchar(45) DEFAULT NULL,
  `host` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `payorder_paychannel` (`payOrderid`,`paychannel`),
  UNIQUE KEY `app_coprder` (`cpOrderId`,`appid`),
  UNIQUE KEY `orderid` (`orderid`),
  KEY `appid` (`appid`),
  KEY `cporderid` (`cpOrderId`),
  KEY `payorderid` (`payOrderid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yl_order`
--

LOCK TABLES `yl_order` WRITE;
/*!40000 ALTER TABLE `yl_order` DISABLE KEYS */;
INSERT INTO `yl_order` VALUES (6,'mq152823349e387837',401,1,'1463650448368','nickname395','waresId360','wares539','20160519173352414661','cpOrderId6363',NULL,'透传参数68',1,'2016-05-19 17:33:53','2016-05-19 17:33:53',NULL,0,0,NULL,NULL,0,0,NULL,NULL,NULL),(7,'mq152823349e387837',401,802,'1463995681756','nickname356','waresId203','wares669','20160523172747868187','cpOrderId2532',NULL,'透传参数45',1,'2016-05-23 17:27:48','2016-05-23 17:27:48',NULL,201,0,NULL,NULL,0,0,NULL,NULL,NULL),(8,'mq152823349e387837',401,802,'1463995776350','nickname31','waresId895','wares326','20160523172923102472','cpOrderId3277',NULL,'透传参数50',1,'2016-05-23 17:29:24','2016-05-23 17:29:24',NULL,201,0,NULL,NULL,0,0,NULL,NULL,NULL),(9,'mq152823349e387837',401,802,'1463996239838','nickname522','waresId112','wares102','20160523173706327824','cpOrderId3067','2016052321001004260296700194','透传参数50',1,'2016-05-23 17:37:06','2016-05-23 19:01:32',NULL,200,0,'TRADE_SUCCESS',NULL,2,5,NULL,NULL,NULL),(10,'mq152823349e387837',401,802,'1463998122673','nickname135','waresId959','wares390','20160523180827448798','cpOrderId1355',NULL,'透传参数98',1,'2016-05-23 18:08:27','2016-05-23 18:08:27',NULL,201,0,NULL,NULL,0,0,NULL,NULL,NULL),(11,'mq152823349e387837',401,802,'1463999724435','nickname165','waresId130','wares998','20160523183509716648','cpOrderId33','2016052321001004260289820029','透传参数6',1,'2016-05-23 18:35:10','2016-05-23 18:50:43',NULL,200,0,'TRADE_SUCCESS',NULL,2,5,NULL,NULL,NULL),(12,'mq152823349e387837',401,802,'1464000203606','nickname645','waresId32','wares6','20160523184307427205','cpOrderId8850','2016052321001004260296033635','透传参数87',1,'2016-05-23 18:43:07','2016-05-23 18:43:19',NULL,200,0,'TRADE_SUCCESS',NULL,2,5,NULL,NULL,NULL),(13,'mq152823349e387837',401,802,'1464000510692','nickname863','waresId687','wares83','20160523184814989964','cpOrderId7938','2016052321001004260281534724','透传参数79',1,'2016-05-23 18:48:15','2016-05-23 18:48:25',NULL,200,0,'TRADE_SUCCESS',NULL,2,5,NULL,NULL,NULL),(14,'mq152823349e387837',401,802,'1464000958647','nickname312','waresId540','wares373','20160523185542978724','cpOrderId5901','2016052321001004260281534901','透传参数21',1,'2016-05-23 18:55:43','2016-05-23 18:55:54',NULL,200,0,'TRADE_SUCCESS',NULL,2,5,NULL,NULL,NULL),(15,'mq152823349e387837',401,802,'1464001284131','nickname821','waresId786','wares364','20160523190107464037','cpOrderId3188',NULL,'透传参数66',1,'2016-05-23 19:01:08','2016-05-23 19:01:08',NULL,201,0,NULL,NULL,0,0,NULL,NULL,NULL),(16,'mq152823349e387837',401,802,'1464001365259','nickname615','waresId60','wares146','20160523190232979499','cpOrderId5992','2016052321001004260296235292','透传参数86',1,'2016-05-23 19:02:32','2016-05-23 19:04:52',NULL,200,0,'TRADE_SUCCESS',NULL,2,5,NULL,NULL,NULL);
/*!40000 ALTER TABLE `yl_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yl_transfer_record`
--

DROP TABLE IF EXISTS `yl_transfer_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yl_transfer_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(45) DEFAULT NULL COMMENT '0-ipos 1-szf',
  `orderid` varchar(45) DEFAULT NULL,
  `tid` varchar(45) DEFAULT NULL COMMENT 'cporderid',
  `paychannel` int(11) DEFAULT NULL COMMENT '微信=801 支付宝=802 银联=803（暂未开通） 移动卡=804 电信卡=805 联通卡=806 其他=807',
  `url` varchar(128) DEFAULT NULL,
  `data` varchar(512) DEFAULT NULL,
  `response` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `ordertype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orderid` (`orderid`),
  KEY `tid` (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='通知记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yl_transfer_record`
--

LOCK TABLES `yl_transfer_record` WRITE;
/*!40000 ALTER TABLE `yl_transfer_record` DISABLE KEYS */;
INSERT INTO `yl_transfer_record` VALUES (62,NULL,'20160523184814989964','cpOrderId7938',802,NULL,'cpOrderId=cpOrderId7938&nickname=nickname863&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A48%3A15.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B079&sign=a5b120e69f8aaae89c350dea1f704bf0&payMode=802&market=401&totalFee=1&waresId=waresId687&userId=1464000510692&orderId=20160523184814989964&wares=wares83',NULL,NULL,NULL,0),(63,NULL,'20160523184814989964','cpOrderId7938',802,NULL,'cpOrderId=cpOrderId7938&nickname=nickname863&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A48%3A15.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B079&sign=a5b120e69f8aaae89c350dea1f704bf0&payMode=802&market=401&totalFee=1&waresId=waresId687&userId=1464000510692&orderId=20160523184814989964&wares=wares83',NULL,NULL,NULL,0),(64,NULL,'20160523184814989964','cpOrderId7938',802,NULL,'cpOrderId=cpOrderId7938&nickname=nickname863&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A48%3A15.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B079&sign=a5b120e69f8aaae89c350dea1f704bf0&payMode=802&market=401&totalFee=1&waresId=waresId687&userId=1464000510692&orderId=20160523184814989964&wares=wares83',NULL,NULL,NULL,0),(65,NULL,'20160523184814989964','cpOrderId7938',802,NULL,'cpOrderId=cpOrderId7938&nickname=nickname863&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A48%3A15.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B079&sign=a5b120e69f8aaae89c350dea1f704bf0&payMode=802&market=401&totalFee=1&waresId=waresId687&userId=1464000510692&orderId=20160523184814989964&wares=wares83',NULL,NULL,NULL,0),(66,NULL,'20160523184814989964','cpOrderId7938',802,NULL,'cpOrderId=cpOrderId7938&nickname=nickname863&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A48%3A15.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B079&sign=a5b120e69f8aaae89c350dea1f704bf0&payMode=802&market=401&totalFee=1&waresId=waresId687&userId=1464000510692&orderId=20160523184814989964&wares=wares83',NULL,NULL,NULL,0),(67,NULL,'20160523183509716648','cpOrderId33',802,NULL,'cpOrderId=cpOrderId33&nickname=nickname165&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A35%3A10.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B06&sign=6f2107e224510b48279916887aea6159&payMode=802&market=401&totalFee=1&waresId=waresId130&userId=1463999724435&orderId=20160523183509716648&wares=wares998',NULL,NULL,NULL,0),(68,NULL,'20160523183509716648','cpOrderId33',802,NULL,'cpOrderId=cpOrderId33&nickname=nickname165&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A35%3A10.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B06&sign=6f2107e224510b48279916887aea6159&payMode=802&market=401&totalFee=1&waresId=waresId130&userId=1463999724435&orderId=20160523183509716648&wares=wares998',NULL,NULL,NULL,0),(69,NULL,'20160523183509716648','cpOrderId33',802,NULL,'cpOrderId=cpOrderId33&nickname=nickname165&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A35%3A10.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B06&sign=6f2107e224510b48279916887aea6159&payMode=802&market=401&totalFee=1&waresId=waresId130&userId=1463999724435&orderId=20160523183509716648&wares=wares998',NULL,NULL,NULL,0),(70,NULL,'20160523183509716648','cpOrderId33',802,NULL,'cpOrderId=cpOrderId33&nickname=nickname165&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A35%3A10.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B06&sign=6f2107e224510b48279916887aea6159&payMode=802&market=401&totalFee=1&waresId=waresId130&userId=1463999724435&orderId=20160523183509716648&wares=wares998',NULL,NULL,NULL,0),(71,NULL,'20160523183509716648','cpOrderId33',802,NULL,'cpOrderId=cpOrderId33&nickname=nickname165&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A35%3A10.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B06&sign=6f2107e224510b48279916887aea6159&payMode=802&market=401&totalFee=1&waresId=waresId130&userId=1463999724435&orderId=20160523183509716648&wares=wares998',NULL,NULL,NULL,0),(72,NULL,'20160523185542978724','cpOrderId5901',802,NULL,'cpOrderId=cpOrderId5901&nickname=nickname312&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A55%3A43.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B021&sign=41c75c0746dc3817cde9b90076c1afe5&payMode=802&market=401&totalFee=1&waresId=waresId540&userId=1464000958647&orderId=20160523185542978724&wares=wares373',NULL,NULL,NULL,0),(73,NULL,'20160523185542978724','cpOrderId5901',802,NULL,'cpOrderId=cpOrderId5901&nickname=nickname312&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A55%3A43.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B021&sign=41c75c0746dc3817cde9b90076c1afe5&payMode=802&market=401&totalFee=1&waresId=waresId540&userId=1464000958647&orderId=20160523185542978724&wares=wares373',NULL,NULL,NULL,0),(74,NULL,'20160523185542978724','cpOrderId5901',802,NULL,'cpOrderId=cpOrderId5901&nickname=nickname312&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A55%3A43.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B021&sign=41c75c0746dc3817cde9b90076c1afe5&payMode=802&market=401&totalFee=1&waresId=waresId540&userId=1464000958647&orderId=20160523185542978724&wares=wares373',NULL,NULL,NULL,0),(75,NULL,'20160523185542978724','cpOrderId5901',802,NULL,'cpOrderId=cpOrderId5901&nickname=nickname312&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A55%3A43.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B021&sign=41c75c0746dc3817cde9b90076c1afe5&payMode=802&market=401&totalFee=1&waresId=waresId540&userId=1464000958647&orderId=20160523185542978724&wares=wares373',NULL,NULL,NULL,0),(76,NULL,'20160523185542978724','cpOrderId5901',802,NULL,'cpOrderId=cpOrderId5901&nickname=nickname312&status=200&appid=mq152823349e387837&orderTime=2016-05-23+18%3A55%3A43.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B021&sign=41c75c0746dc3817cde9b90076c1afe5&payMode=802&market=401&totalFee=1&waresId=waresId540&userId=1464000958647&orderId=20160523185542978724&wares=wares373',NULL,NULL,NULL,0),(77,NULL,'20160523173706327824','cpOrderId3067',802,NULL,'cpOrderId=cpOrderId3067&nickname=nickname522&status=200&appid=mq152823349e387837&orderTime=2016-05-23+17%3A37%3A06.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B050&sign=6a63de61b901f865951a85e0f308f725&payMode=802&market=401&totalFee=1&waresId=waresId112&userId=1463996239838&orderId=20160523173706327824&wares=wares102',NULL,NULL,NULL,0),(78,NULL,'20160523173706327824','cpOrderId3067',802,NULL,'cpOrderId=cpOrderId3067&nickname=nickname522&status=200&appid=mq152823349e387837&orderTime=2016-05-23+17%3A37%3A06.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B050&sign=6a63de61b901f865951a85e0f308f725&payMode=802&market=401&totalFee=1&waresId=waresId112&userId=1463996239838&orderId=20160523173706327824&wares=wares102',NULL,NULL,NULL,0),(79,NULL,'20160523173706327824','cpOrderId3067',802,NULL,'cpOrderId=cpOrderId3067&nickname=nickname522&status=200&appid=mq152823349e387837&orderTime=2016-05-23+17%3A37%3A06.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B050&sign=6a63de61b901f865951a85e0f308f725&payMode=802&market=401&totalFee=1&waresId=waresId112&userId=1463996239838&orderId=20160523173706327824&wares=wares102',NULL,NULL,NULL,0),(80,NULL,'20160523173706327824','cpOrderId3067',802,NULL,'cpOrderId=cpOrderId3067&nickname=nickname522&status=200&appid=mq152823349e387837&orderTime=2016-05-23+17%3A37%3A06.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B050&sign=6a63de61b901f865951a85e0f308f725&payMode=802&market=401&totalFee=1&waresId=waresId112&userId=1463996239838&orderId=20160523173706327824&wares=wares102',NULL,NULL,NULL,0),(81,NULL,'20160523173706327824','cpOrderId3067',802,NULL,'cpOrderId=cpOrderId3067&nickname=nickname522&status=200&appid=mq152823349e387837&orderTime=2016-05-23+17%3A37%3A06.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B050&sign=6a63de61b901f865951a85e0f308f725&payMode=802&market=401&totalFee=1&waresId=waresId112&userId=1463996239838&orderId=20160523173706327824&wares=wares102',NULL,NULL,NULL,0),(82,NULL,'20160523190232979499','cpOrderId5992',802,NULL,'cpOrderId=cpOrderId5992&nickname=nickname615&status=200&appid=mq152823349e387837&orderTime=2016-05-23+19%3A02%3A32.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B086&sign=e5fc8283c463a4baf2f9b206dea3e657&payMode=802&market=401&totalFee=1&waresId=waresId60&userId=1464001365259&orderId=20160523190232979499&wares=wares146',NULL,NULL,NULL,0),(83,NULL,'20160523190232979499','cpOrderId5992',802,NULL,'cpOrderId=cpOrderId5992&nickname=nickname615&status=200&appid=mq152823349e387837&orderTime=2016-05-23+19%3A02%3A32.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B086&sign=e5fc8283c463a4baf2f9b206dea3e657&payMode=802&market=401&totalFee=1&waresId=waresId60&userId=1464001365259&orderId=20160523190232979499&wares=wares146',NULL,NULL,NULL,0),(84,NULL,'20160523190232979499','cpOrderId5992',802,NULL,'cpOrderId=cpOrderId5992&nickname=nickname615&status=200&appid=mq152823349e387837&orderTime=2016-05-23+19%3A02%3A32.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B086&sign=e5fc8283c463a4baf2f9b206dea3e657&payMode=802&market=401&totalFee=1&waresId=waresId60&userId=1464001365259&orderId=20160523190232979499&wares=wares146',NULL,NULL,NULL,0),(85,NULL,'20160523190232979499','cpOrderId5992',802,NULL,'cpOrderId=cpOrderId5992&nickname=nickname615&status=200&appid=mq152823349e387837&orderTime=2016-05-23+19%3A02%3A32.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B086&sign=e5fc8283c463a4baf2f9b206dea3e657&payMode=802&market=401&totalFee=1&waresId=waresId60&userId=1464001365259&orderId=20160523190232979499&wares=wares146',NULL,NULL,NULL,0),(86,NULL,'20160523190232979499','cpOrderId5992',802,NULL,'cpOrderId=cpOrderId5992&nickname=nickname615&status=200&appid=mq152823349e387837&orderTime=2016-05-23+19%3A02%3A32.0&payTime=null&ext=%E9%80%8F%E4%BC%A0%E5%8F%82%E6%95%B086&sign=e5fc8283c463a4baf2f9b206dea3e657&payMode=802&market=401&totalFee=1&waresId=waresId60&userId=1464001365259&orderId=20160523190232979499&wares=wares146',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `yl_transfer_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-23 19:11:37
