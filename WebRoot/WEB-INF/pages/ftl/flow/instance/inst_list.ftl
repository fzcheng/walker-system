<table cellspacing="0" cellpadding="0" class="table-box" id="tbl">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="150px">流程名称</td>
	    <td width="140px">创建时间</td>
	    <td width="70px">创建人</td>
	    <td width="80px">流程状态</td>
	    <td width="100px">当前任务</td>
	    <td width="70px">当前执行人</td>
	    <td>流程模板</td>
	  </tr>
	  
	 	<#list pagerView.datas as process>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${process.id}" class="check_box" />
	    </td>
	    <td>
	    	<a href="javascript:void(0);" onclick="javascript:showProcessInfo('${process.id}', '${process.processDefineId}');return false;">${process.name}</a>
	    </td>
	    <td>${process.showCreateTime}</td>
	    <td>${process.creatorName}</td>
	    <td>
	    	<#if (process.end == 1)>
	    	<label>流程结束</label>
	    	<#elseif (process.pause == 1)>
	    	<label style="font-weight:bold;color:red;">流程暂停</label>
	    	<#elseif (process.terminate == 1)>
	    	<label style="font-weight:bold;color:red;">终止</label>
	    	<#else>
	    	<label style="color:green;">正常运行</label>
	    	</#if>
	    </td>
	    <td>${process.taskName}</td>
	    <td>${process.userName}</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">