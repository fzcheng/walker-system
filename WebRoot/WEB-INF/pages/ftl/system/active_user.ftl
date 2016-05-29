<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线用户列表</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>

<!-- 
<script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
 -->
<script type="text/javascript" src="${ctx}/script/public.js"></script>

</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form" >
	<tr class="title">
		<td>
			<span>当前在线用户共【${userSize}】人</span>&nbsp;|&nbsp;
  		<input type="button" value="刷新查看" onclick="" class="button-tj"/>
		</td>
	</tr>
</table>
<div id="pageInfo">
	<table border="0" cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="70px" align="center">序号</td>
	    <td width="100px">用户名</td>
	    <td width="120px">登录ID</td>
	    <td width="100px">最近活动时间</td>
	    <td width="100px">操作</td>
	    <td>备注</td>
	  </tr>
	  
	 <#list userList as map>
	 	<#list map?keys as user>
	  <tr class="table-date">
	    <td>1</td>
	    <td>${user.name}</td>
	    <td>${user.loginId}</td>
	  	<td>&nbsp;${map['user']}</td>
	  	<td>N/A</td>
	  	<td>N/A</td>
	  </tr>
	  </#list>
	 </#list>
	</table>
</div>
 
</body>
<#include "/common/footer.ftl">
</html>