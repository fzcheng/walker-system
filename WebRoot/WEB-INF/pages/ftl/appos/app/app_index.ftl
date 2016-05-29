<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用管理</title>
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
	doReloadPage(offset, "${ctx}/permit/appos/app/reload.do", params);
}

//创建授权页面
function showAddAppWnd(){
	popDefaultDialog('添加应用', '${ctx}/permit/appos/app/showAddAppItem.do');
}

//创建授权页面
function showUpdateAppWnd(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	
	popDefaultDialog('编辑应用', '${ctx}/permit/appos/app/showUpdateAppItem.do?id='+selected[0]);
}

function deleteAppWnd()
{
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("确定要删除应用么")){
		requestAjax("${ctx}/appos/app/delApp.do", {"id":selected[0]}, function(data){
			reload(0);
		});
	}

}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>应用列表</span>&nbsp;|&nbsp;
			
			<#if (pointers['APP_ADD'])>
			<input type="button" value="添加应用" onclick="showAddAppWnd()" class="button"/>
			</#if>
			<#if (pointers['APP_EDIT'])>
			<input type="button" value="修改应用" onclick="showUpdateAppWnd()" class="button"/>
			</#if>
			<#if (pointers['APP_DEL'])>
			<input type="button" value="删除应用" onclick="deleteAppWnd()" class="button"/>
			</#if>

		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/appos/app/app_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>