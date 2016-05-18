<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统功能点（按钮）管理</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript">
//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//拼json参数
	//var params = {name:""};
	loadDiv("${ctx}/permit/admin/function/list_button.do?id="+$("#functionId").val(), null, null);
}

function showAddPointer(){
	fid = $("#functionId").val();
	if(isEmptyValue(fid)){
		alert("参数错误：未找到功能ID");
		return;
	}
	popDefaultDialog("添加新按钮", "${ctx}/permit/admin/function/show_pointer.do?functionId="+fid);
}

function goBack(){
	fid = $("#functionId").val();
	window.location.href = "${ctx}/admin/function/index.do?fid="+fid+"&_page="+$("#_page").val();
}

function removePointer(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("您确定要删除该按钮么？")){
		var _params = {"pointerId":selected[0], "functionId":$("#functionId").val()};
		requestAjax("${ctx}/permit/admin/function/del_pointer.do", _params, function(data){
			if(data == "success"){
				reload(0);
			} else {
				showErrorTip(data);
			}
		});
	}
}

</script>
</head>
<body>
<input type="hidden" id="functionId" name="functionId" value="${functionId}"/>
<input type="hidden" id="_page" name="_page" value="${_page}"/>

<div id="pageInfo">
<table border="0" cellpadding="0" cellspacing="0" class="table-form" >
	<tr class="title">
		<td>
			<div id="groups">
			<span>功能按钮管理</span>&nbsp;|&nbsp;
	  		<input type="button" value="添加按钮" onclick="showAddPointer();" class="button"/>&nbsp;
	  		<input type="button" value="删除按钮" onclick="removePointer();" class="button"/>&nbsp;|&nbsp;
	  		<input type="button" value="返 回" onclick="goBack();" class="button"/>
			</div>
		</td>
	</tr>
</table>

<#include "/system/pointer_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>