<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="100px">id</td>
	    <td width="180px">appid</td>
	    <td width="180px">cpOrderId</td>
	    <td width="180px">orderid</td>
	    <td width="20px">market</td>
	    <td width="20px">status</td>
	    <td width="150px">transfer_status</td>
	    <td width="70px">创建时间</td>
	    <td width="70px">修改时间</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.id}" class="check_box" />
	    </td>
	    <td>${app.appid}</td>
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