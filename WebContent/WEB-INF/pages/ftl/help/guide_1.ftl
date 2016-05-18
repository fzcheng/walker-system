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
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>系统开发帮助指南--系统安全</span>
		</td>
	</tr>
</table>
<table id="mytable" cellspacing="0" cellpadding="0" class="table-form">  
  <tr class="title">  
    <td width="200px">帮助指南-标题</td>  
 		<td>说明与描述</td>  
  </tr>
  <tr>
  	<td>关于超级管理员</td>
  	<td>
  	1、系统内置了超级管理员用户，在配置文件app_config.properties中可以配置名称。<br>
  	2、超级管理员密码可以在“参数选项”功能中设置。<br>
  	3、超级管理员名称一旦确定，在正式运行后就不要修改了，否则容易出现用户名的混乱。<br>
  	4、超级管理员可以访问系统所有功能。
  	</td>
  </tr>
  <tr>
  	<td>系统框架使用</td>
  	<td>
  	1、使用了Spring-3.0.5 + SpringMVC + Hibernate-3 + SpringSecurity-3.1.0。<br>
  	2、通常你只需要修改classpatd下面的app_config.properties和app_jdbc.properties文件。<br>
  	3、系统封装了较多的类库，位于lib/walker-infrastructure-0.2.15.jar中。
  	</td>
  </tr>
  <tr>
  	<td>SpringSecurity3相关配置</td>
  	<td>
  	1、系统URL安全由SpringSecurity3拦截，配合菜单、功能点权限分配，能满足业务安全要求。<br>
  	2、但是，对于大多应用，我们不想细粒度管理，目前在“角色权限”功能中可以分配某个按钮功能，例如：编辑，这时系统仅仅是不允许用户“编辑保存”，用户仍然可以通过url方式手动请求看到编辑界面，当然你可以修改程序做到完全拦截。<br>
  	<font style="font-weight:bold;">3、基于2中的原因，系统开放了一个公开地址：permit/，所有登录用户都可以访问该地址，如果你不想那么麻烦的控制功能，就可以把URL放在permit下面，系统就不会拦截了。例如：所有用户都可以查看帮助页面。</font><br>
  	4、在application-security.xml中可以配置其详细参数，例如：默认用户能同时登陆个数等，当前设置为2，即：同一用户只能同时登陆两个。
  	</td>
  </tr>
  <tr>
  	<td>URL访问控制</td>
  	<td>
  	1、如果出现无权限访问资源，首先检查是否给用户分配了响应权限（角色权限）。<br>
  	2、如果是大家都能访问的URL，可以考虑放到permit/下面，避免不必要的麻烦。<br>
  	3、如果是无须经过SpringSecurity拦截器的URL，就在配置文件中，如：&lt;http pattern="/style/**" security="none"/&gt;
  	</td>
  </tr>
  <tr>
  	<td>系统登录</td>
  	<td>
  	在“参数选项”功能中，可以设置登录验证码、用户初始密码等信息。
  	</td>
  </tr>
  <tr>
  	<td>应用动态配置文件</td>
  	<td>
  	应用的配置信息存储在classpatd下面的app_variables.xml文件中。修改后缓存和配置文件实时更新生效，无需手动修改。
  	</td>
  </tr>
  <tr>
  	<td>数据库切换</td>
  	<td>
  	1、系统目前支持MySQL和ORACLE，可以在配置文件：app_config.properties中设置。这仅表示系统现有功能支持，业务是否支持需要自己开发。<br>
  	2、通常来说，只要不使用特定数据库特性，并不会影响切换。<br>
  	3、系统内含了这两个库的初始化脚本，并在系统启动时自动检测，如果不存在表结构会触发自动初始化，你也可以强制不检查，配置文件中myDatabaseDetector对象参数可以设置（有注释）。
  	</td>
  </tr>
</table>
<div style="margin-top:5px;">
</div>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td align="center">
			<input type="button" value=" &lt;上一页 " class="disabled" disabled="disabled"/>&nbsp;&nbsp;
			1/3&nbsp;&nbsp;
			<input type="button" value=" 下一页&gt; " onclick="next('2')" class="button"/>
		</td>
	</tr>
</table>
</body>
<#include "/common/footer.ftl">
</html>