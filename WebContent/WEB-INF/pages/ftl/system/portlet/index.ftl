<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>默认Portlet容器显示</title>
<!-- 
<link href="${ctx}/style/reset.css" type="text/css" rel="stylesheet"/>
 -->
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

function reloadPortlet(portletId){
	_params = {"portletId":portletId};
	loadContent(portletId, "${ctx}/permit/portlet/reload.do", _params, function(){
		refreshFrame();
	});
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="50%" id="awaitTaskPortlet" valign="top">
			<#if (awaitTaskPortlet??)>
			<#include awaitTaskPortlet.includePage>
			</#if>
		</td>
		<td width="1%"></td>
		<td width="49%" id="sysInfoPortlet" valign="top">
			<#if (sysInfoPortlet??)>
			<#include sysInfoPortlet.includePage>
			</#if>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>

</body>
<#include "/common/footer.ftl">
</html>