<table border="0" cellspacing="0" cellpadding="0" class="table-box">
  <tr class="table-tit">
    <td width="50px">
    	<input type="checkbox" onclick="selectAll(this)" class="check_box" disabled="disabled"/>
    </td>
    <td width="140px">代码表ID</td>
    <td width="140px">代码表名称</td>
    <td width="80px">代码权限</td>
  </tr>
	  
	 	<#list pagerView.datas as code>
	 	<tr class="table-date" onclick="doOnRowSelected('${code.id}')">
	    <td>
	    	<input name="ids" type="checkbox" value="${code.id}" class="check_box" disabled="disabled"/>
	    </td>
	    <td>${code.id}</td>
	    <td>
	    	${code.name}
	    </td>
	    <td>
	    	<#if (code.codeSec == 0)>
	    	系统代码
	    	<#else>
	    	用户代码
	    	</#if>
	    </td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">