<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程绑定管理</title>
<link href="${ctx}/style/reset.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>

<script type="text/javascript">

var typeValue = "";

$(function(){

});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	_params = {"type":typeValue};
	doReloadPage(offset, "${ctx}/permit/flow/bind/reload.do", null);
}

// 查看流程实例
function showAddBind(){
	popDefaultDialog("添加绑定", "${ctx}/permit/flow/bind/show_add.do");
}
function removeBind(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("您确定要删除绑定么？")){
		var _params = {"id":selected[0]};
		requestAjax("${ctx}/permit/flow/bind/remove.do", _params, function(data){
			if(data == "success"){
				reload(0);
			} else {
				showErrorTip(data);
			}
		});
	}
}

function search(val){
	if(isEmptyValue(val)){
		alert("缺少查询类型");
		return;
	}
	typeValue = val;
	_params = {"type":val};
	doReloadPage(0, "${ctx}/permit/flow/bind/reload.do", _params);
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>流程绑定管理</span>&nbsp;|&nbsp;
	  	<input type="button" value="单位" onclick="search('0');" class="button"/>&nbsp;
	  	<input type="button" value="部门" onclick="search('1');" class="button"/>&nbsp;
	  	&nbsp;&nbsp;|&nbsp;
	  	<input type="button" value="添加绑定" onclick="showAddBind();" class="button-tj"/>&nbsp;
	  	<input type="button" value="删除绑定" onclick="removeBind();" class="button"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/flow/bind/bind_list.ftl">
</div>

</body>
<#include "/common/footer.ftl">
</html>