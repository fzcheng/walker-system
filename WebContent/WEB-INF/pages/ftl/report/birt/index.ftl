<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BIRT报表</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript">

$(function(){
	
});


</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>报表测试</span>&nbsp;|&nbsp;
			选择报表：
			<select id="storeType" name="storeType">
				<option value="1" selected="selected">折线报表一</option>
				<option value="2">圆饼报表一</option>
			</select>
	  	<input type="button" value=" 查 询 " onclick="search();" class="button"/>
		</td>
	</tr>
</table>
<a href="${ctx}/frameset?__report=201.rptdesign" target="_blank">演示报表-打开新报表窗口</a><br>
 
</body>
<#include "/common/footer.ftl">
</html>