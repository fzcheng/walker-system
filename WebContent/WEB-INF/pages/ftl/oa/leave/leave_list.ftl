<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="130px">请假时间</td>
	    <td width="70px">天数</td>
	    <td width="100px">所在单位</td>
	    <td width="60px">请假人</td>
	    <td width="70px">是否销假</td>
	    <td width="120px">销假时间</td>
	    <td>备注</td>
	    <td width="60px">状态</td>
	  </tr>
	  
	 	<#list pagerView.datas as leave>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="1" class="check_box" />
	    </td>
	    <td>${leave.dateFromShow}</td>
	    <td>${leave.resultDays}</td>
	    <td>${leave.userDeptName}</td>
	    <td>${leave.userName}</td>
	    <td>
	    	<#if (leave.after == 0)>
	    	否
	    	<#else>
	    	是
	    	</#if>
	    </td>
	    <td>
	    <#if (leave.after == 1)>
	    ${leave.afterTime?number_to_datetime}
	    </#if>
	    </td>
	    <td>${leave.summary}</td>
	    <td>
	    <#if (leave.done == 1)>
	    完成查看
	    <#else>
	    办理
	    </#if>
	    </td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">