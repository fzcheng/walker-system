<script type="text/javascript">
<!--
function selectSplitType(){
	var d = $("#splitType").val();
	if(d == "1"){
		$("#userSelect").show();
		$("#conditionOption").hide();
		$("#conditionDesc").hide();
	} else if(d == "9"){
		$("#userSelect").hide();
		$("#conditionOption").hide();
		$("#conditionDesc").hide();
	} else if(d == "2"){
		// 条件分拆
		$("#userSelect").hide();
		$("#conditionOption").show();
		$("#conditionDesc").show();
	} else {
		$("#userSelect").hide();
		$("#conditionOption").hide();
		$("#conditionDesc").hide();
	}
}
//-->
</script>
<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
 	 <tr class="title">
 		 <td colspan="2">分拆任务选项</td>
 	 </tr>
 	 <tr>
  		<td class="showTitle">分拆类型：</td>
  		<td>
  			<select id="splitType" name="splitType" style="width:200px;" onchange="selectSplitType();"
  				title="分拆环节允许下一步任务存在多个选择，选择方式不同。">
  				<option value="" selected="selected">=== 请选择 ===</option>
 					<#if (task.splitType == 2)>
 					<option value="2" selected="selected">按给定条件分拆</option>
	 				<option value="1">用户自主选择</option>
	 				<option value="9">全分拆[同时执行]</option>
 					<#elseif (task.splitType == 1)>
 					<option value="2">按给定条件分拆</option>
	 				<option value="1" selected="selected">用户自主选择</option>
	 				<option value="9">全分拆[同时执行]</option>
 					<#elseif (task.splitType == 9)>
 					<option value="2">按给定条件分拆</option>
	 				<option value="1">用户自主选择</option>
	 				<option value="9" selected="selected">全分拆[同时执行]</option>
	 				<#else>
	 				<option value="2">按给定条件分拆</option>
	 				<option value="1">用户自主选择</option>
	 				<option value="9">全分拆[同时执行]</option>
 					</#if>
	 			</select>
  		</td>
  	</tr>
   <tr id="conditionOption">
  		<td class="showTitle" width="150px">条件分拆选项：</td>
  		<td width="300px">
  			<table border="0" width="100%">
  			<#list taskOutList as to>
  				<tr>
  					<td width="220px" align="right">
  			执行[<font color="red">${to.nextTaskName}</font>]的条件表达式：
  					</td>
  					<td>
  			<input type="hidden" name="nextTasks" value="${to.nextTask}"/>
  			<input type="text" name="conditionValues" class="text" maxlength="50" value="${to.condition}"/>
  					</td>
  				</tr>
  			</#list>
  			</table>
  		</td>
  </tr>	
  <tr id="conditionDesc">
  		<td class="showTitle" width="150px">条件分叉说明：</td>
  		<td width="300px">
  			如果下一步任务是按照条件进行选择走向，那么用户必须给每个分支配置一个条件表达式。<br>
  			该表达式是一种简单二元表达式，就像普通的数值比较一样。例如：<br>
  			(day >= 0) and (p1 == member)，其中'day'和'p1'为参数名称。<br>
  			当在任务离开事件接口实现中，提供了'day'和'p1'参数值后，引擎便可以知道需要选择哪个任务来执行了。
  		</td>
  </tr>
  <tr id="userSelect" style="display:none;">
  		<td class="showTitle" width="150px">默认选择的任务：</td>
  		<td width="300px">
  			<select id="defaultTask" name="defaultTask" style="width:200px;"
  				title="在界面中会默认选中这里设置的任务。">
  				<#list taskOutList as to>
  				<#if (to.defaultTask == 1)>
  				<option value="${to.nextTask}" selected="selected">${to.nextTaskName}</option>
  				<#else>
  				<option value="${to.nextTask}">${to.nextTaskName}</option>
  				</#if>
  				</#list>
	 			</select>
  		</td>
  </tr>	
 </table>
<script type="text/javascript">
<!--
<#if (task.splitType == 2)>
$("#userSelect").hide();
$("#conditionOption").show();
$("#conditionDesc").show();
<#elseif (task.splitType == 1)>
$("#userSelect").show();
$("#conditionOption").hide();
$("#conditionDesc").hide();
<#elseif (task.splitType == 9)>
$("#userSelect").hide();
$("#conditionOption").hide();
$("#conditionDesc").hide();
<#else>
$("#userSelect").hide();
$("#conditionOption").hide();
$("#conditionDesc").hide();
</#if>
//-->
</script>