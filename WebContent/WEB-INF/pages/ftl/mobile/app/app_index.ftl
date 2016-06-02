<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APP应用管理</title>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>

<script type="text/javascript">
var loadi;

$(function(){
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	_params = {};
	doReloadPage(offset, "${ctx}/permit/mobile/app/reload.do", _params);
}

// 查看流程实例
function showAddPage(){
	popDefaultDialog("添加新应用", "${ctx}/permit/mobile/app/show_add.do");
}

function removeApp(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("您确定要删除么？")){
		var _params = {"appId":selected[0]};
		requestAjax("${ctx}/permit/mobile/app/remove.do", _params, function(data){
			if(data == "success"){
				reload(0);
			} else {
				showErrorTip(data);
			}
		});
	}
}

function showUpdate(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	window.location.href = "${ctx}/permit/mobile/app/update_index.do?appId="+selected[0];
}

function publish(id){
	if(window.confirm("您确定要执行操作么？")){
		loadi = layer.load('正在加载数据，请稍后…');
		// questionType = 题库分类，对应q_type字段
		var _params = {"paperId":id};
		requestAjax("${ctx}/permit/exam/paper/publish.do", _params, function(data){
			if(data == "success"){
				reload(0);
			} else {
				showErrorTip(data);
			}
			layer.close(loadi);
		});
	}
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>APP应用管理</span>&nbsp;|&nbsp;
	  	<input type="button" value="添加应用" onclick="showAddPage();" class="button-tj"/>&nbsp;
	  	<input type="button" value="删除应用" onclick="removeApp();" class="button"/>&nbsp;|&nbsp;
	  	<input type="button" value="升级管理" onclick="showUpdate();" class="button"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/mobile/app/app_list.ftl">
</div>

</body>
<#include "/common/footer.ftl">
</html>