<#assign pageNum = pagerView.currentPage>
<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="150px">项目名称</td>
	    <td width="140px">创建时间</td>
	    <td width="120px">流程模板</td>
	    <td width="80px">运行状态</td>
	    <td>备注</td>
	    <td width="60px">操作</td>
	  </tr>
	  
	 	<#list pagerView.datas as process>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${process.id}" class="check_box" />
	    </td>
	    <td>${process.name}</td>
	    <td>${process.createTime?number_to_date}</td>
	    <td>${process.processName}</td>
	    <td>
	    	<#if (process.status == 0)>
	    	运行中
	    	<#elseif (process.status == 1)>
	    	<font style="font-weight:bold;">已停止</font>
	    	<#else>
	    	暂停中
	    	</#if>
	    </td>
	    <td>${process.summary}</td>
	  	<td>
	    	<#if (process.status == 0)>
		      <a href="####" onclick="popFlowDialog('${process.processId}', '${process.processDefineId}');">进入办理</a>
	    	<#else>
	    	</#if>
	    </td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">