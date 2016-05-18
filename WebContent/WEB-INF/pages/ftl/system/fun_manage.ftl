<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统菜单管理</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/select/jquery.cxselect.min.js"></script>
<script type="text/javascript">
//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//拼json参数
	var params = {name:""};
	doReloadPage(offset, "${ctx}/permit/admin/function/reload.do", params);
}

function search(){
	var params = null;
	var v = $("#p_grp").val();
	//console.log("+++++ v = " + v);
	if(isNotEmptyValue(v)){
		params = {pid:v};
		doReloadPage(0, "${ctx}/permit/admin/function/reload.do", params);
		return;
	}
	
	v = $("#p_sys").val();
	if(isEmptyValue(v)){
		alert("请选择查询条件");
		return;
	}
	params = {pid:v};
	doReloadPage(0, "${ctx}/permit/admin/function/reload.do", params);
}

function showAddPage(){
	popModalDialog("添加新功能", "${ctx}/admin/function/show_add.do", "600px", "400px");
}
function showEditPage(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	popModalDialog("编辑功能", "${ctx}/admin/function/show_edit.do?id="+selected[0], "600px", "400px");
}

function removeFunc(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("您确定要删除该功能么？")){
		var _params = {"id":selected[0]};
		requestAjax("${ctx}/admin/function/remove.do", _params, function(data){
			if(data == "success"){
				reload(0);
			} else {
				alert(data);
			}
		});
	}
}

function showPointerPage(id){
	/*
	currentPage = $("#offset").val();
	if(isEmptyValue(currentPage)){
		currentPage = "";
	}
	*/
	window.location.href = "${ctx}/permit/admin/function/list_button.do?id="+id + "&_page="+getCurrentPage();
}

</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form" >
	<tr class="title">
		<td>
			<div id="groups">
			<span>系统功能管理</span>&nbsp;|&nbsp;
				<select class="system" id="p_sys" name="p_sys"></select>
   			<select class="group" id="p_grp" name="p_grp"></select>
				<input type="button" value="查 询" class="button" onclick="search();"/>&nbsp;|&nbsp;
				<#if (pointers['FUNC_ADD'])>
	  		<input type="button" value="添加新功能" onclick="showAddPage();" class="button-tj"/>&nbsp;
	  		</#if>
	  		<#if (pointers['FUNC_EDIT'])>
	  		<input type="button" value="编辑功能" onclick="showEditPage();" class="button"/>&nbsp;
	  		</#if>
	  		<#if (pointers['FUNC_DEL'])>
	  		<input type="button" value="删除功能" onclick="removeFunc();" class="button"/>&nbsp;
	  		</#if>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
$.cxSelect.defaults.url = "js/cityData.min.json";
$.cxSelect.defaults.nodata = "none";

var data = stringToJson("${sysGroupJson}"); // 包含两级数据

$("#groups").cxSelect({
	selects : ["system", "group"],
	required : true,
	url: data
});
</script>

<div id="pageInfo">
	<#include "/system/fun_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>