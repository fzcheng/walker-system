<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="180px">appid</td>
	    <td width="150px">market</td>
	    <td width="150px">sdkid</td>
	    <td width="100px">备注</td>
	    <td width="70px">创建时间</td>
	    <td width="70px">修改时间</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.id}" class="check_box" />
	    </td>
	    <td>${app.appid}</td>
	    <td>${app.market}</td>
	    <td>${app.sdkid}</td>
	    <td>${app.remark}</td>
	    <td>${app.create_time}</td>
	    <td>${app.last_time}</td>
	    
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">