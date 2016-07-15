<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="120px">应用名称</td>
	    <td width="180px">游戏侧订单号</td>
	    <td width="180px">支付侧订单号</td>
	    <td width="50px">市场渠道</td>
	    <td width="50px">订单状态</td>
	    <td width="50px">通知状态</td>
	    <td width="180px">创建时间</td>
	    <td width="180px">修改时间</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.id}" class="check_box" />
	    </td>
	    <td>${app.appname}</td>
	    <td>${app.cpOrderId}</td>
	    <td>${app.orderid}</td>
	    <td>${app.market}</td>
	    <td>${app.status}</td>
	    <td>${app.transfer_status}</td>
	    <td>${app.create_time}</td>
	    <td>${app.last_time}</td>
	    
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">