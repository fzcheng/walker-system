<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>应用系统提示</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript">

$(function(){ // wait for document to load 
	
 });

function login(){
	window.top.location.href = "${ctx}/login.do";
}
</script>
</head>
<body>

	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form" align="center">
  	<tr class="title">
  		<td>${_title}：</td>
  	</tr>
  	<tr>
  		<td>
  			${_message}
  		</td>
  	</tr>
 </table>
 <div style="width:100%;" align="center">
 	<input type="button" value="返 回" class="button-tj" onclick=""/>&nbsp;
 	<!-- 
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 	 -->
 </div>
</body>
</html>