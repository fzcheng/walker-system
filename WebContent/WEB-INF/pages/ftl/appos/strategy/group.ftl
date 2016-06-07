<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>策略组管理</title>
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
	doReloadPage(offset, "${ctx}/permit/appos/strategy/reloadGroup.do", params);
}

//创建授权页面
function showAddGroupWnd(){
	popDefaultDialog('添加策略组', '${ctx}/permit/appos/strategy/showAddGroupItem.do');
}

//创建授权页面
function showUpdateGroupWnd(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	
	popDefaultDialog('编辑策略组', '${ctx}/permit/appos/strategy/showUpdateGroupItem.do?id='+selected[0]);
}

function deleteGroupWnd()
{
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("确定要删除策略组么")){
		requestAjax("${ctx}/appos/strategy/delGroup.do", {"id":selected[0]}, function(data){
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
			<span>策略组列表</span>&nbsp;|&nbsp;
			
			<#if (pointers['STRATEGY_GROUP_ADD'])>
			<input type="button" value="添加策略组" onclick="showAddGroupWnd()" class="button"/>
			</#if>
			<#if (pointers['STRATEGY_GROUP_EDIT'])>
			<input type="button" value="修改策略组" onclick="showUpdateGroupWnd()" class="button"/>
			</#if>
			<#if (pointers['STRATEGY_GROUP_DEL'])>
			<input type="button" value="删除策略组" onclick="deleteGroupWnd()" class="button"/>
			</#if>

		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/appos/strategy/group_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>