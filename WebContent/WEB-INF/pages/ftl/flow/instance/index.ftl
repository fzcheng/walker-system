<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程运行监控</title>
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
	doReloadPage(offset, "${ctx}/permit/flow/instance/reload.do", null);
}

// 查看流程实例
function showProcessInfo(processInstId, processDefineId){
	window.location.href = "${ctx}/permit/flow/instance/view.do?processInstId="+processInstId+"&processDefineId="+processDefineId;
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>流程运行监控</span>&nbsp;|&nbsp;
	  	<input type="button" value="打开流程处理窗口" onclick="popTest();" class="button-tj"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/flow/instance/inst_list.ftl">
</div>

</body>
<#include "/common/footer.ftl">
</html>