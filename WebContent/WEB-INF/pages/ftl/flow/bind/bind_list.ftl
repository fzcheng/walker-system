<table cellspacing="0" cellpadding="0" class="table-box" id="tbl">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="70px">业务类别</td>
	    <td width="70px">绑定类型</td>
	    <td width="180px">名称</td>
	    <td width="220px">绑定流程</td>
	    <td width="140px">创建时间</td>
	    <td>备注</td>
	  </tr>
	  
	 	<#list pagerView.datas as process>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${process.id}" class="check_box" />
	    </td>
	    <td>${process.businessType}</td>
	    <td>
	    <#if (process.type == 0)>
			单位
	    <#elseif (process.type == 1)>
			部门
	    <#elseif (process.type == 2)>
			角色
	    <#elseif (process.type == 3)>
			岗位
	    <#else>
			个人
	    </#if>
	    </td>
	    <td>${process.bindName}</td>
	    <td>${process.processDefineName}</td>
	    <td>${process.createTime?number_to_datetime}</td>
	    <td>${process.summary}</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">