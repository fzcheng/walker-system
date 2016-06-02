<table cellspacing="0" cellpadding="0" class="table-box" id="tbl" width="99%">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="90px">任务名称</td>
	    <td width="150px">流程名称</td>
	    <td width="140px">到达时间</td>
	    <td width="70px">任务执行人</td>
	    <td width="80px">流程状态</td>
	    <td>使用流程模板</td>
	    <td width="60px">操作</td>
	  </tr>
	  
	 	<#list pagerView.datas as process>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${process.taskInstanceId}" class="check_box" />
	    </td>
	    <td>${process.taskName}</td>
	    <td>${process.processName}</td>
	    <td>${process.showTaskArrivedTime}</td>
	    <td>${process.userName}</td>
	    <td>
	    	<#if (process.end == 1)>
	    	<label>流程结束</label>
	    	<#elseif (process.pause == 1)>
	    	<label style="font-weight:bold;">流程暂停</label>
	    	<#else>
	    	<label>正常运行</label>
	    	</#if>
	    </td>
	    <td>&nbsp;</td>
	  	<td>
	    	<#if (process.end == 0 && process.pause == 0)>
		      <a href="javascript:;" onclick="popFlowDialog('${process.processInstanceId}', '${process.processDefineId}', '${process.taskInstanceId}', '${process.taskDefineId}', '${process.businessId}', '${process.userId}');">进入办理</a>
	    	<#else>
	    	</#if>
	    </td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">