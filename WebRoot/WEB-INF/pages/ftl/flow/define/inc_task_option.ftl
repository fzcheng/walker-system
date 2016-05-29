<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
 	 <tr class="title">
 		 <td colspan="2">可选属性</td>
 	 </tr>
 	 <#if (taskType != 'subProcessTask')>
 	 <tr>
  		<td class="showTitle" width="150px">任务集成的业务页面：</td>
  		<td width="300px">
  			<input type="text" id="pageUrl" name="pageUrl" class="text" 
  			maxlength="50" title="每个任务可能对应不同的业务界面，可以配置业务的调用地址，是一个URL。不超过150个字符"/>
  		</td>
   </tr>
   </#if>
 	 <tr>
  		<td class="showTitle">是否允许修改流程：</td>
  		<td>
  			<select id="modTask" name="modTask" style="width:200px;" title="可在任务过程中修改后续任务，如：添加或删除环节。">
	 				<option value="0" selected="selected">否</option>
	 				<option value="1">是</option>
	 			</select>
  		</td>
  	</tr>
   <tr>
  		<td class="showTitle" width="150px">下一步按钮名称：</td>
  		<td width="300px">
  			<input type="text" id="nextName" name="nextName" class="text" 
  			maxlength="50" title="可以在每个任务中自定义'下一步'按钮名称，如：发送。不超过50个字符"/>
  		</td>
  </tr>
  <#if (taskType != 'subProcessTask' && taskType != 'edgeTask')>
  <tr>
  		<td class="showTitle" width="150px">退回按钮名称：</td>
  		<td width="300px">
  			<input type="text" id="previousName" name="previousName" class="text" 
  			maxlength="50" title="可以在每个任务中自定义'退回'按钮名称，如：打回。不超过50个字符"/>
  		</td>
  </tr>
  </#if>
 </table>