
	<table border="0" cellpadding="0" cellspacing="0" width="630px" class="table-form">
  	<tr class="title">
  		<td colspan="3">选择任务类型</td>
  	</tr>
  	<tr>
  		<td width="50px">
  			<input type="radio" value="singleTask" id="singleTask" name="taskType" checked="checked"/>
  		</td>
  		<td width="240px">
  			<img alt="任务类型图标" src="">&nbsp;单任务 [singleTask]
  		</td>
  		<td width="340px">单任务只有一个输入节点和输出节点，是最普通的任务。</td>
  	</tr>
  	<tr>
  		<td>
  			<input type="radio" value="splitTask" id="splitTask" name="taskType"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;分拆任务 [splitTask]
  		</td>
  		<td>分拆任务有一个输入节点和多个输出节点，适合下一步任务有多重选择的情况。</td>
  	</tr>
  	<#if (!start)>
  	<tr>
  		<td>
  			<input type="radio" value="affluenceTask" id="affluenceTask" name="taskType"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;汇聚任务 [affluenceTask]
  		</td>
  		<td>汇聚任务有多个输入节点和一个输出节点，适合上一步的多个任务流转到同一个节点。</td>
  	</tr>
  	<tr>
  		<td>
  			<input type="radio" value="splitAndAffluenceTask" id="splitAndAffluenceTask" name="taskType"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;分拆+汇聚任务 [splitAndAffluenceTask]
  		</td>
  		<td>分拆+汇聚任务有多个输入节点和多个输出节点，作为中转节点起到承上启下的作用。</td>
  	</tr>
  	</#if>
  	<tr>
  		<td>
  			<input type="radio" value="subProcessTask" id="subProcessTask" name="taskType"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;子流程任务 [subProcessTask]
  		</td>
  		<td>子流程任务可以在内部嵌套一个其他流程，在某些大型流程中会出现拆分很多小流程的情况。</td>
  	</tr>
  	<#if (!start)>
  	<tr>
  		<td>
  			<input type="radio" value="edgeTask" id="edgeTask" name="taskType"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;边界任务 [edgeTask]
  		</td>
  		<td>边界任务是一种特殊任务类型，它不会影响流程状态，也就是说即使用户不执行此任务也不影响。</td>
  	</tr>
  	<tr>
  		<td>
  			<input type="radio" value="auxiliaryTask" id="auxiliaryTask" name="taskType"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;辅助任务 [auxiliaryTask]
  		</td>
  		<td>辅助任务是一种特殊任务类型。</td>
  	</tr>
  	<tr>
  		<td>
  			<input type="radio" value="autoProgram" id="autoProgram" name="taskType" disabled="disabled"/>
  		</td>
  		<td>
  			<img alt="任务类型图标" src="">&nbsp;自动执行任务 [autoProgram]
  		</td>
  		<td>自动执行任务无需人工干预，只需要指定一个回调接口或事件，任务到达便会自动触发并转入下一步。</td>
  	</tr>
  	</#if>
 </table>
