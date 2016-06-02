<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>数据导入成功</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	
 });

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

function login(){
	window.top.location.href = "${ctx}/login.do";
}
</script>
</head>
<body>

	<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
  	<tr class="title">
  		<td colspan="2">数据导入成功，但请注意以下事项：</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">关于缓存：</td>
  		<td width="300px">
  			<ul>
  				<li>由于导入的数据可能有一部分会作为缓存来使用，因此系统将重新加载缓存数据。</li>
  			</ul>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">springMVC注解：</td>
  		<td width="300px">
  			<ul>
  				<li>因为springMVC的注解机制，无法让其自动重新启动，因此对于涉及用户权限菜单出现的问题需要重新启动服务。</li>
  				<li>系统通过对比springMVC注解中的Controller来对比菜单URL，避免出现非法菜单，如果不重新启动服务很可能出现这种情况。</li>
  				<li>当然，不重启服务也是可以使用的。</li>
  			</ul>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">当前用户权限：</td>
  		<td>
  			<ul>
  				<li>当前登录用户菜单缓存没有重新加载，因此您现在需要重新登录来加载新的个人权限。</li>
  			</ul>
  		</td>
  	</tr>
 </table>
 <div style="width:800px;" align="center">
 	<input type="button" value=" 重新登录 " class="button" onclick="login();"/>&nbsp;
 	<!-- 
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 	 -->
 </div>
</body>
</html>