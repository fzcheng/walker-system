<script type="text/javascript">
<!--
//弹出流程处理窗口
function popFlowDialog(processInstId, processDefineId, taskInstId, taskDefineId, bizId, userId){
	popModalDialog("流程任务办理", "${ctx}/permit/flow/runtime/index.do?instId="+processInstId+"&defineId="+processDefineId+"&taskInstId="+taskInstId+"&taskDefineId="+taskDefineId+"&bizId="+bizId+"&userId="+userId+"&callbackJs=reloadPortlet('awaitTaskPortlet')", "900px", "600px");
}
//-->
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-box">
 	<tr class="table-tit">
 		<td colspan="2" class="portal-logo"><img src="${ctx}/images/window-logo.png">&nbsp;&nbsp;${awaitTaskPortlet.title}</td>
 	</tr>
 	<#list awaitTaskList as process>
 	<tr class="table-date">
 		<td class="portal-td">
 		<a href="javascript:;" onclick="popFlowDialog('${process.processInstanceId}', '${process.processDefineId}', '${process.taskInstanceId}', '${process.taskDefineId}', '${process.businessId}', '${process.userId}');">${process.taskName} 【${process.processName}】</a>
 		</td>
    <td width="36%">${process.showTaskArrivedTime}</td>
 	</tr>
 	</#list>
</table>
