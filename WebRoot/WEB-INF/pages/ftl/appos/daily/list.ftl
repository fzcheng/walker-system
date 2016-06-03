<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    
	    <td width="100px">应用名</td>
	    <td width="100px">日期</td>
	    <td width="100px">订单数</td>
	    <td width="100px">订单总额(分)</td>
	    <td width="100px">创建时间</td>
	    <td width="100px">修改时间</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    
	    <td>${app.appname}</td>
	    <td>${app.date}</td>
	    <td>${app.order_count}</td>
	    <td>${app.order_total_fee}</td>
	    <td>${app.create_time}</td>
	    <td>${app.last_time}</td>
	    
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">