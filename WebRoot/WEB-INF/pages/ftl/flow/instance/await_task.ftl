<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>等待办理任务</title>
<link href="${ctx}/style/reset.css" type="text/css" rel="stylesheet"/>
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
	doReloadPage(offset, "${ctx}/permit/flow/instance/await_reload.do", null);
}

//弹出流程处理窗口
function popFlowDialog(processInstId, processDefineId, taskInstId, taskDefineId, bizId, userId){
	popModalDialog("流程任务办理", "${ctx}/permit/flow/runtime/index.do?instId="+processInstId+"&defineId="+processDefineId+"&taskInstId="+taskInstId+"&taskDefineId="+taskDefineId+"&bizId="+bizId+"&userId="+userId, "900px", "600px");
}

// 请求ajax的回调函数实现
var _callback = function ajaxCallback(data){
	if(data == "success"){
		reload(1);
	} else
		alert(data);
};

</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>等待办理任务</span>&nbsp;|&nbsp;
	  	<input type="button" value="测试" onclick="" class="button-tj"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#if (pagerView??)>
	<#include "/flow/instance/await_list.ftl">
	<#else>
	<label>暂无数据</label>
	</#if>
</div>

</body>
<#include "/common/footer.ftl">
</html>