<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>创建新任务</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 

 });


function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

</script>
</head>
<body>

<form action="${ctx}/permit/flow/design/showCreateTask.do" method="post" id="form" target="_self">
<input type="hidden" id="processDefineId" name="processDefineId" value="${processDefineId}"/>
<input type="hidden" id="isStart" name="isStart" value="${start}"/>
<#include "/flow/define/design_task_select.ftl">
 <div style="width:800px;" align="center">
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 	<input type="submit" value=" 下一步 " class="button-tj"/>&nbsp;&nbsp;
 </div>
</form>
</body>
</html>