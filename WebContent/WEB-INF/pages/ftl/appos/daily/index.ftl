<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用渠道管理</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/select/jquery.cxselect.min.js"></script>
<script type="text/javascript">

$(function(){
	
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//拼json参数
	var sel=document.getElementsByName("queryappid")[0];
	var selvalue= sel.options[sel.options.selectedIndex].value;//你要的值

	//alert(selvalue);
	var params = {appid:selvalue};
	doReloadPage(offset, "${ctx}/permit/appos/daily/reload.do", params);
}


function query(){
	var sel=document.getElementsByName("queryappid")[0];
	var selvalue= sel.options[sel.options.selectedIndex].value;//你要的值
	
	var params = {appid:selvalue};
	doReloadPage(1, "${ctx}/permit/appos/daily/reload.do", params);
}


</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>订单列表</span>&nbsp;|&nbsp;
			
			<select id="queryappid" name="queryappid">
			<option value="0">全部</option>
			<#list userapps as app>
			<option value="${app.appid}">
			${app.appname}
			</option>
			</#list>
			</select>

			
			<input type="button" value="查询" onclick="query()" class="button"/>
			

		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/appos/daily/list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>