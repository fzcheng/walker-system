<table cellspacing="0" cellpadding="0" class="table-box" id="tbl">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="60px">序号</td>
	    <td width="120px">任务名称</td>
	    <td width="140px">到达时间</td>
	    <td width="70px">执行人</td>
	    <td width="80px">当前状态</td>
	    <td width="140px">完成时间</td>
	    <td>意见/备注</td>
	  </tr>
	  <#assign inx = 1>
	 	<#list taskList as task>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${task.id}" class="check_box" />
	    </td>
	    <td>${inx}</td>
	    <td>${task.name}</td>
	    <td>${task.showCreateTime}</td>
	    <td>${task.userName}</td>
	    <td>
	    	<#if (task.taskStatus == -1)>
	    	<label style="color:#666666;">初始化</label>
	    	<#elseif (task.taskStatus == 0)>
	    	<label style="color:red;">执行中...</label>
	    	<#else>
	    	<label style="color:green;">已完成</label>
	    	</#if>
	    </td>
	    <td>${task.showEndTime}</td>
	    <td>${task.opinion}</td>
	  </tr>
	  <#assign inx = (inx + 1)>
		</#list>
	</table>
