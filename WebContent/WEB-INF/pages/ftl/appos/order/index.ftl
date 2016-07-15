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
	doReloadPage(offset, "${ctx}/permit/appos/order/reload.do", params);
}

function query(){
	var sel=document.getElementsByName("curappid")[0];
	var selvalue= sel.options[sel.options.selectedIndex].value;//你要的值
	
	var stat=document.getElementsByName("status")[0];
	var statvalue= stat.options[stat.options.selectedIndex].value;//你要的值
	
	var params = {appid:selvalue, status:statvalue};
	doReloadPage(1, "${ctx}/permit/appos/order/reload.do", params);
}

</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>订单流水查询</span>&nbsp;|&nbsp;&nbsp;&nbsp;
			
			<span>应用</span>&nbsp;&nbsp;
			<select id="curappid" name="curappid">
			<option value="0">全部</option>
			<#list userapps as app>
			<option value="${app.appid}">
			${app.appname}
			</option>
			</#list>
			</select>
			
			<span>状态</span>&nbsp;&nbsp;
			<select id="status" name="status">
			<option value="0">全部</option>
			<#list statuss as stat>
			<option value="${stat.value}">
			${stat.name}
			</option>
			</#list>
			</select>
			
			<#if (pointers['ORDER_SINGLE_QUERY'])>
			<input type="button" value="查询" onclick="query()" class="button"/>
			</#if>

		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/appos/order/list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>