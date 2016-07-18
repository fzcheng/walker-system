<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="180px">订单时间</td>
	    <td width="120px">应用名称</td>
	    <td width="180px"><span>游戏订单号\平台订单号</span></td>
	    <td width="180px">第三方订单号</td>
	    <td width="50px">市场渠道</td>
	    <td width="50px">订单状态</td>
	    <td width="50px">通知状态</td>
	    <td width="50px">操作</td>
	  </tr>
	  
	  <#assign statusmap = statuss>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.id}" class="check_box" />
	    </td>
	    <td>${app.create_time}</td>
	    <td>${app.appname}</td>
	    <td>${app.cpOrderId}<br>${app.orderid}</td>
	    <td>${app.payOrderid}</td>
	    <td>${app.market}</td>
	    <td>${statusmap["${app.status}"].name}</td>
	    <td>${app.transfer_status}</td>
	    <td>
	    	<input type="button" value="重发通知" onclick="retransfer(${app.id})" class="button"/>
	    	<input type="button" value="查看详情" onclick="detail(${app.id})" class="button"/>
	    </td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">