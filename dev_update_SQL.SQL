-- 2014/08/06 增加JQGrid的展示
INSERT INTO `s_function` VALUES ('99', '99', '1', '用户浏览-JQGRID', '/admin/user/jqgrid_index.do', '', '1', '1');

-- 2014/08/08 增加JasperReport报表的展示
INSERT INTO `s_function` VALUES ('4', '4', '0', '报表管理', '', '', '1', '0');
INSERT INTO `s_function` VALUES ('41', '41', '1', 'BIRT报表-demo', '/report/birt/index.do', '', '1', '4');

--2014/08/11 数据库管理
INSERT INTO `s_function` VALUES ('5', '5', '0', '数据库管理', null, null, '1', '0');
INSERT INTO `s_function` VALUES ('51', '51', '1', '数据备份', '/admin/db/export.do', null, '1', '5');
INSERT INTO `s_function` VALUES ('52', '52', '1', '数据导入', '/admin/db/import.do', null, '1', '5');
