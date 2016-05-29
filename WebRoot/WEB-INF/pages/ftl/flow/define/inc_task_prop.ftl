<script type="text/javascript">
<!--
function changeMutiUser(){
	var v = $("#multiUser").val();
	if(v == "0"){
		$("#actorExec").attr("disabled","disabled");
	} else {
		$("#actorExec").removeAttr("disabled");
	}
}
//-->
</script>
<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
  	<tr class="title">
  		<td colspan="2">任务基本属性</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)任务名称：</td>
  		<td width="300px">
  			<input type="text" id="name" name="name" class="text" 
  			maxlength="50" title="不超过100个字符"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">进入任务节点事件：</td>
  		<td>
  			<select id="taskInListenerName" name="taskInListenerName" style="width:200px;"
  			title="<li>在进入任务时可以配置自定义的事件监听器来与流程引擎交互。</li><li>需要在spring配置文件中设置，编写请参考手册。</li>">
  				<#list taskInListener?keys as k>
  				<option value="${k}">${taskInListener[k]}</option>
  				</#list>
  			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">离开任务节点事件：</td>
  		<td>
  			<select id="taskOutListenerName" name="taskOutListenerName" style="width:200px;"
  			title="<li>在离开任务时可以配置自定义的事件监听器来与流程引擎交互。</li><li>需要在spring配置文件中设置，编写请参考手册。</li>">
  				<#list taskOutListener?keys as k>
  				<option value="${k}">${taskOutListener[k]}</option>
  				</#list>
  			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">任务参与者：</td>
  		<td>
  			<select id="actorType" name="actorType" style="width:200px;" title="参与者类型需要用户自定义编写，配置到环境中可以选择。">
	 				<#list actorType?keys as k>
	 				<option value="${k}">${actorType[k]}</option>
	 				</#list>
	 			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">参与者选择：</td>
  		<td>&nbsp;</td>
  	</tr>
  	<tr>
  		<td class="showTitle">任务可多人执行：</td>
  		<td>
  			<select id="multiUser" name="multiUser" style="width:200px;" onchange="changeMutiUser();"
  				title="对于有些如:会签，可以同时多个人参与，<br>系统会创建多个任务。">
	 				<option value="0" selected="selected">否</option>
	 				<#if (taskType == "singleTask")>
	 				<option value="1">是</option>
	 				</#if>
	 			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">
  			<span>有多执行人时<br>完成任务的标准</span>：
  		</td>
  		<td>
  			<select id="actorExec" name="actorExec" style="width:200px;" disabled="disabled"
  				title="任意一人完成表示只要一个人完成任务就向下继续流转">
	 				<option value="0" selected="selected">任意一个人执行完</option>
	 				<option value="1">所有人执行完</option>
	 				<!-- 
	 				<option value="2">大部分人执行完</option>
	 				 -->
	 			</select>
  		</td>
  	</tr>
 </table>