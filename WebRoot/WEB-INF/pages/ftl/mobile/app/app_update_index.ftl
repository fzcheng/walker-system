<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APP应用版本升级</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>

<script type="text/javascript">
var loadi;

$(function(){
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	_params = {"appId":"${appId}"};
	doReloadPage(offset, "${ctx}/permit/mobile/app/reload_update.do", _params);
}

// 查看流程实例
function showAddPage(){
	popDefaultDialog("添加应用升级信息", "${ctx}/permit/mobile/app/show_add_update.do?appId=${appId}");
}

function removeAppUpdate(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("您确定要删除么？")){
		var _params = {"appId":selected[0]};
		requestAjax("${ctx}/permit/mobile/app/remove_update.do", _params, function(data){
			if(data == "success"){
				reload(0);
			} else {
				showErrorTip(data);
			}
		});
	}
}

function goBack(){
	window.location.href = "${ctx}/mobile/app/index.do";
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>APP应用版本升级维护</span>&nbsp;|&nbsp;
	  	<input type="button" value="添加升级版本" onclick="showAddPage();" class="button-tj"/>&nbsp;
	  	<input type="button" value="删除升级版本" onclick="removeAppUpdate();" class="button"/>&nbsp;|&nbsp;
	  	<input type="button" value="返回应用列表" onclick="goBack();" class="button"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/mobile/app/app_update_list.ftl">
</div>

</body>
<#include "/common/footer.ftl">
</html>