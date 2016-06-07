<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="180px">策略组id</td>
	    <td width="180px">策略组名</td>
	    <td width="180px">备注</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.group_id}" class="check_box" />
	    </td>
	    <td>${app.group_id}</td>
	    <td>${app.group_name}</td>
	    <td>${app.remark}</td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">