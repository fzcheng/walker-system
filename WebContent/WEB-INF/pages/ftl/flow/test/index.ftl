<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程测试</title>
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
	//var params = {start:startVal, end:endVal, storeType:storeType, filename:filename, ext:ext};
	doReloadPage(offset, "${ctx}/permit/flow/test/reload.do", null);
}

//弹出样式选择窗口
function showAdd(){
	popModalDialog("创建项目信息", "${ctx}/permit/flow/test/show_add.do", "450px", "360px");
}

// 请求ajax的回调函数实现
var _callback = function ajaxCallback(data){
	if(data == "success"){
		reload(1);
	} else
		alert(data);
};

//弹出样式选择窗口
function popFlowDialog(instId, defineId){
	popModalDialog("流程处理界面测试", "${ctx}/permit/flow/runtime/index.do?instId="+instId+"&defineId="+defineId, "900px", "600px");
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>流程测试-项目审批</span>&nbsp;|&nbsp;
	  	<input type="button" value="创建新项目" onclick="showAdd();" class="button-tj"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/flow/test/prj_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>