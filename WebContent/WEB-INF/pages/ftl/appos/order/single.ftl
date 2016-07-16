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
	var cpOrderid=$("#cpOrderid").val();
	var orderid=$("#orderid").val();
	var payOrderid=$("#payOrderid").val();
	
	var params = {cpOrderid:cpOrderid, orderid:orderid, payOrderid:payOrderid};
	doReloadPage(1, "${ctx}/permit/appos/order/singleReload.do", params);
}

</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>订单流水查询</span>&nbsp;|&nbsp;&nbsp;&nbsp;
			
			<span>游戏订单号:</span>&nbsp;&nbsp;
			<input type="text" id="cpOrderid" name="cpOrderid" class="text"/>
			&nbsp;&nbsp;&nbsp;
			<span>平台订单号:</span>&nbsp;&nbsp;
			<input type="text" id="orderid" name="orderid" class="text"/>
			&nbsp;&nbsp;&nbsp;
			<span>第三方订单号:</span>&nbsp;&nbsp;
			<input type="text" id="payOrderid" name="payOrderid" class="text"/>
			&nbsp;&nbsp;&nbsp;
			<input type="button" value="查询" onclick="query()" class="button"/>
			
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/appos/order/list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>