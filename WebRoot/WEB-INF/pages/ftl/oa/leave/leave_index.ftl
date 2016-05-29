<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假管理</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript">

$(function(){
	
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//拼json参数
	params = {};
	doReloadPage(offset, "${ctx}/supervisor/accredit/reload.do", params);
}

//创建授权页面
function showAdd(){
	popDefaultDialog("创建新授权文件", "${ctx}/supervisor/accredit/show_add.do");
}
function remove(){
	alert("暂不支持删除操作");
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>我的请假记录</span>&nbsp;|&nbsp;
			<input type="button" value="创建请假单" onclick="showAdd();" class="button-tj"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/oa/leave/leave_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>