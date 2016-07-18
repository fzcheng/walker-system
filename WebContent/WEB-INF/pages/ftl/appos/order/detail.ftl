<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
    
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
    <script type="text/javascript">
		
var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function(){
	$("#codeName").focus();
});

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

function exit(){
	closeWindow();
}
		

		</script>
  </head>
  
<body>
	<form action="${ctx}/appos/app/updateApp.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  <#assign statusmap = statuss>
  <#assign trstatusmap = transferstatuss>
  	<tr>
  		<td width="160" class="showTitle">appid：</td>
  		<td width="160"><input type="text" id="appid" name="appid" class="text" value="${order.appid}"  readonly="readonly"/></td>
  		
  		<td width="160" class="showTitle">应用名称：</td>
  		<td width="160"><input type="text" id="appname" name="appname" class="text"  value="${order.appname}" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td width="160" class="showTitle">市场渠道market：</td>
  		<td width="160"><input type="text" id="market" name="market" class="text"   value="${order.market}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">付费渠道：</td>
  		<td width="160"><input type="text" id="paychannel" name="paychannel" class="text" value="${order.paychannel}" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td width="160" class="showTitle">玩家id：</td>
  		<td width="160"><input type="text" id="userId" name="userId" class="text" value="${order.userId}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">玩家昵称：</td>
  		<td width="160"><input type="text" id="nickname" name="nickname" class="text" value="${order.nickname}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">商品id：</td>
  		<td width="160"><input type="text" id="waresId" name="waresId" class="text" value="${order.waresId}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">商品描述：</td>
  		<td width="160"><input type="text" id="wares" name="wares" class="text" value="${order.wares}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">平台订单号：</td>
  		<td width="160"><input type="text" id="orderid" name="orderid" class="text"  style="width:240px;" value="${order.orderid}" readonly="readonly"/></td>
  
  		<td width="160" class="showTitle">应用订单号：</td>
  		<td width="160"><input type="text" id="cpOrderId" name="cpOrderId" class="text"  style="width:240px;" value="${order.cpOrderId}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">第三方订单号：</td>
  		<td width="160"><input type="text" id="payOrderid" name="payOrderid" class="text"  style="width:240px;" value="${order.payOrderid}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">透传参数：</td>
  		<td width="160"><input type="text" id="ext" name="ext" class="text" value="${order.ext}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">金额（分）：</td>
  		<td width="160"><input type="text" id="totalFee" name="totalFee" class="text" value="${order.totalFee}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">订单创建时间：</td>
  		<td width="160"><input type="text" id="create_time" name="create_time" class="text" value="${order.create_time}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">订单状态：</td>
  		<td width="160"><input type="text" id="status" name="status" class="text" value="${statusmap["${order.status}"].name}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">model：</td>
  		<td width="160"><input type="text" id="model" name="model" class="text" value="${order.model}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">retCode：</td>
  		<td width="160"><input type="text" id="retCode" name="retCode" class="text" value="${order.retCode}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">retMsg：</td>
  		<td width="160"><input type="text" id="retMsg" name="retMsg" class="text" value="${order.retMsg}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">通知状态：</td>
  		<td width="160"><input type="text" id="transfer_status" name="transfer_status" class="text" value="${trstatusmap["${order.transfer_status}"].name}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">总通知次数：</td>
  		<td width="160"><input type="text" id="transfer_count" name="transfer_count" class="text" value="${order.transfer_count}" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td width="160" class="showTitle">通知地址：</td>
  		<td width="160"><input type="text" id="transfer_url" name="transfer_url" class="text" style="width:280px;" value="${order.transfer_url}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">ip：</td>
  		<td width="160"><input type="text" id="ip" name="ip" class="text" value="${order.ip}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">host：</td>
  		<td width="160"><input type="text" id="host" name="host" class="text" value="${order.host}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">imei：</td>
  		<td width="160"><input type="text" id="imei" name="imei" class="text" value="${order.imei}" readonly="readonly"/></td>
  	</tr>
  	
  	<tr>
  		<td width="160" class="showTitle">mobile：</td>
  		<td width="160"><input type="text" id="mobile" name="mobile" class="text" value="${order.mobile}" readonly="readonly"/></td>
  	
  		<td width="160" class="showTitle">sdk版本号：</td>
  		<td width="160"><input type="text" id="version" name="version" class="text" value="${order.version}" readonly="readonly"/></td>
  	</tr>
  </table>
  <div style="width:99%;" align="center">
	 	<input type="button" value="关 闭" class="button" onclick="exit();"/>
	</div>
  </form>
</body>
</html>
