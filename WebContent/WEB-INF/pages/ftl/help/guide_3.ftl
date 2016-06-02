<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统帮助-开发指南</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript">
function next(id){
	window.location.href = "${ctx}/permit/help/index.do?pageId="+id;
}
</script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
	<tr class="title">
		<td>
			<span>系统开发帮助指南--系统功能简介</span>
		</td>
	</tr>
</table>
<table id="mytable" cellspacing="0" cellpadding="0" class="table-form">  
  <tr class="title">  
    <td width="200px">帮助指南-标题</td>  
 		<td>说明与描述</td>  
  </tr>
  <tr>
  	<td>多单位管理</td>
  	<td>
  	系统支持多个单位存在，每个单位可以设置自己的管理员。<br>
  	单位管理员可以管理本单位的机构和用户。<br>
  	超级管理员可以管理所有单位和所有用户。
  	</td>
  </tr>
  <tr>
  	<td>版本升级0.2.3-beta</td>
  	<td>
  	0.2.3-beta更新内容：<br>
  	1、修复了登录根路径时报错（http://localhost/walker-web/）。<br>
  	2、修复了分页组件ftl模板。
  	3、增加了https安全登录，在springsecurity配置文件中，去掉相应注释就可以了，同时程序根目录'设计文档'中有默认证书需要您导入JDK库中。<br>
  	4、增加了系统logo上传功能，这样不用重新发布程序就可以修改logo。<br>
  	5、把原来使用的DHTML商业组件替换为开源产品，如：ztree等，但仍保留了“代码管理”没有替换可以供个人参考学习DHTMLX组件使用方法。<br>
  	6、系统界面重新进行了调整和美化，但并不能支持IE的兼容模式，请优先使用GOOGLE浏览器。<br>
  	
  	</td>
  </tr>
</table>
<div style="margin-top:5px;">
</div>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td align="center">
			<input type="button" value=" &lt;上一页 " onclick="next('2');" class="button"/>&nbsp;&nbsp;
			3/3&nbsp;&nbsp;
			<input type="button" value=" 下一页&gt; " onclick="" class="disabled" disabled="disabled"/>
		</td>
	</tr>
</table>
</body>
<#include "/common/footer.ftl">
</html>