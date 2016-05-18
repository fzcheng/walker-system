<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>创建分拆任务</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	showTipInput("name");
	showTipInput("taskInrouteListener");
	showTipInput("taskOutrouteListener");
	showTipInput("actorType");
	showTipInput("multiUser");
	showTipInput("actorExec");
 });

function save(){
	var identifier = $.trim($("#name").val());
	if(identifier == ""){
		alert("请输入'任务名称'");
		return false;
	}
	var splitType = $("#splitType").val();
	if(isEmptyValue(splitType)){
		alert("请选择拆分类型");
		return false;
	}
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	if(data == "success"){
	        	parent.frames.sampleframe.reload();
	        	alert("任务添加成功");
	        	closeWindow();
        	} else {
        		alert(data);
        	}
        },
        error:function(data){
        	try{
	        	console.log("dsfsdf " + data);
	        } catch(e){
	        	alert(e.message + ", " + e.name);
	        }
        }
    });
    return false; // <-- important!
	/**
	$("#form").ajaxForm({
		action: "${ctx}/admin/department/save.do",
		method: "post",
		success: function(data){
			alert(data);
		}
	});*/
}

function edit(){
	var identifier = $.trim($("#name").val());
	if(identifier == ""){
		alert("请输入'任务名称'");
		return false;
	}
	var splitType = $("#splitType").val();
	console.log("+++++++++ " + splitType);
	if(isEmptyValue(splitType)){
		alert("请选择拆分类型");
		return false;
	}
	if(splitType == "2"){
		// 条件
		nextTasks = $("input[name='nextTasks']");
		if(nextTasks != null && nextTasks.length > 0){
			count = nextTasks.length;
			nextTaskValues = new Array();
			for(var i=0; i<count; i++){
				nextTaskValues[i] = nextTasks[i].value;
			}
			nextTaskValues = nextTaskValues.join(",");
			
			conditions = $("input[name='conditionValues']");
			conditionValues = new Array();
			for(var i=0; i<conditions.length; i++){
				conditionValues[i] = conditions[i].value;
				if(isEmptyValue(conditionValues[i])){
					alert("所有分拆任务都必须输入执行条件!");
					return false;
				}
			}
			
			conditionValues = conditionValues.join(",");
			$("#p_nextIds").val(nextTaskValues);
			$("#p_conditions").val(conditionValues);
			//alert(nextTaskValues + "," + conditionValues);
		}
		
	} else if(splitType == "1"){/*用户选择*/}
	
	$('#form').ajaxSubmit({
		//dataType: 'text',
	//target: '#content',
	success: function(data){
		if(data == "success"){
	    	//parent.frames.sampleframe.reload();
	    	alert("任务编辑成功");
	    	closeWindow();
		} else {
			alert(data);
		}
	},
	error:function(data){
		try{
	    	console.log("dsfsdf " + data);
	    } catch(e){
	    	alert(e.message + ", " + e.name);
	    }
	}
	});
	return false; // <-- important!
}

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}
</script>
</head>
<body>

<#if (isEdit)>
<form action="${ctx}/permit/flow/design/edit_split.do" method="post" id="form" target="_self">
	<input type="hidden" id="p_nextIds" name="p_nextIds" value=""/>
	<input type="hidden" id="p_conditions" name="p_conditions" value=""/>
	<input type="hidden" id="id" name="id" value="${task.id}"/>
	<input type="hidden" id="processDefineId" name="processDefineId" value="${task.processDefineId}"/>
		<input type="hidden" id="startTask" name="startTask" value="${task.startTask}"/>
	<input type="hidden" id="endTask" name="endTask" value="${task.endTask}"/>
<#else>
<form action="${ctx}/permit/flow/design/save_split.do" method="post" id="form" target="_self">
	<input type="hidden" id="taskType" name="taskType" value="singleTask"/>
	<input type="hidden" id="processDefineId" name="processDefineId" value="${processDefineId}"/>
	<input type="hidden" id="startTask" name="startTask" value="${start}"/>
</#if>
	
<#if (isEdit)>
<#include "/flow/define/inc_task_prop_edit.ftl">
<#include "/flow/define/inc_task_split_edit.ftl">
<#include "/flow/define/inc_task_option_edit.ftl">
<#else>
<#include "/flow/define/inc_task_prop.ftl">
<#include "/flow/define/inc_task_split.ftl">
<#include "/flow/define/inc_task_option.ftl">
</#if>

 <div style="width:800px;" align="center">
  <#if (isEdit)>
 	<input type="button" value=" 保 存 " class="button-tj" onclick="edit();"/>&nbsp;&nbsp;
 	<#else>
 	<input type="button" value=" 保 存 " class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	</#if>
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>
</html>