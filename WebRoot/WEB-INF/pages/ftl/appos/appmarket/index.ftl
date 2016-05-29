<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用渠道管理</title>
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
	doReloadPage(offset, "${ctx}/permit/appos/appmarket/reload.do", params);
}

//创建授权页面
function showAddAppMarketWnd(){
	popDefaultDialog('添加应用', '${ctx}/permit/appos/appmarket/showAddAppMarketItem.do');
}

//创建授权页面
function showUpdateAppMarketWnd(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	
	popDefaultDialog('编辑应用', '${ctx}/permit/appos/appmarket/showUpdateAppMarketItem.do?id='+selected[0]);
}

function deleteAppMarketWnd()
{
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("确定要删除应用么")){
		requestAjax("${ctx}/appos/appmarket/delAppmarket.do", {"id":selected[0]}, function(data){
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
			
			<#if (pointers['APP_MARKET_ADD'])>
			<input type="button" value="添加配置" onclick="showAddAppMarketWnd()" class="button"/>
			</#if>
			<#if (pointers['APP_MARKET_EDIT'])>
			<input type="button" value="修改配置" onclick="showUpdateAppMarketWnd()" class="button"/>
			</#if>
			<#if (pointers['APP_MARKET_DEL'])>
			<input type="button" value="删除配置" onclick="deleteAppMarketWnd()" class="button"/>
			</#if>

		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/appos/appmarket/list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>