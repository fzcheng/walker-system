<#if (task.nodeTypeIndex == 'affluenceTask' || task.nodeTypeIndex == 'splitAndAffluenceTask' || (task.nodeTypeIndex == 'subProcessTask' && !task.start))>
<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
 	 <tr class="title">
 		 <td colspan="2">汇聚任务选项</td>
 	 </tr>
 	 <tr>
  		<td class="showTitle">(<font color="red">*</font>)汇聚类型：</td>
  		<td>
  			<select id="affluenceType" name="affluenceType" style="width:200px;"
  				title="分拆环节允许下一步任务存在多个选择，选择方式不同。">
  				<#if (task.affluenceType == 1)>
  				<option value="1" selected="selected">单一汇聚</option>
	 				<option value="2">全汇聚</option>
	 				<option value="3">多数汇聚 [暂未支持]</option>
  				<#elseif (task.affluenceType == 2)>
  				<option value="1">单一汇聚</option>
	 				<option value="2" selected="selected">全汇聚</option>
	 				<option value="3">多数汇聚 [暂未支持]</option>
  				<#else>
  				<option value="1">单一汇聚</option>
	 				<option value="2">全汇聚</option>
	 				<option value="3" selected="selected">多数汇聚 [暂未支持]</option>
  				</#if>
	 				
	 			</select>
  		</td>
  	</tr>
   <tr id="conditionOption">
  		<td class="showTitle" width="150px">说明：</td>
  		<td width="300px">
  			单一汇聚：只要有一个任务到达就可以触发该任务。<br>
  			全汇聚：必须输入的全部任务都执行完才能触发该任务。<br>
  			多数汇聚：输入任务只要达到指定数量执行完成就可以触发该任务。
  		</td>
  </tr>		
 </table>
 </#if>