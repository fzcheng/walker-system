<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设置参与者模型</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link href="${ctx}/script/lib/ztree/zTreeStyle.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/downTree.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function(){
	// 初始化显示tip
	//showTip("add_org_btn");
	var actor_type = "${actor_type}";
	$("input[name='actoryType']").each(function(){
		if($(this).val() == actor_type){
			$(this).click();
			return false;
		} else
			return true;
	});
});

/*
function zTreeOnClick(e, treeId, treeNode){
	console.log("click tree node: " + treeNode);
}
*/

function doAquireActorBeanOnly(actorType){
	beanId = $("#"+actorType).val();
	if(isEmptyValue(beanId)){
		alert("请从列表中选择一个参与者实现");
		return false;
	} else {
		$("#actor_type").val(actorType);
		$("#actor_bean").val(beanId);
		console.log("1-------: " + beanId);
		return true;
	}
}
function doAquireSelectedResult(selectId, msg){
	selected = "";
	$("#" + selectId + " option").each(function(){
		if(isEmptyValue(selected)){
			selected = $(this).val() + "&" + $(this).text() + ",";
		} else {
			selected = selected + $(this).val() + "&" + $(this).text() + ",";
		}
	});
	if(isEmptyValue(selected)){
		alert(msg);
		return false;
	} else {
		selected = selected.substring(0, selected.length-1);
		console.log("selected: " + selected);
		$("#actor_value").val(selected);
	}
	return true;
}

function save(){
	val = $("input[name='actoryType']:checked").val();
	if(val == ""){
		alert("请选择一个参与者模型");
		return false;
	}
	var beanId = null;
	if(val == "SingleGivenActor"){
		if(!doAquireActorBeanOnly("SingleGivenActor"))
			return false;
	} else if(val == "BusinessExpendActor"){
		if(!doAquireActorBeanOnly("BusinessExpendActor"))
			return false;
	} else if(val == "SelectScopeAndFixActor"){
		if(!doAquireActorBeanOnly("SelectScopeAndFixActor"))
			return false;
		if(!doAquireSelectedResult("fixSelectedUser", "请选择至少一个固定参与者")){
			return false;
		}
	} else if(val == "SelectScopeTreeActor"){
		// 暂未实现
		alert("该模型暂未实现，请先使用其他模型");
		return false;
	} else if(val == "SelectScopeListActor"){
		if(!doAquireActorBeanOnly("SelectScopeListActor"))
			return false;
		if(!doAquireSelectedResult("scopeListSelectedUser", "请选择一个参与者")){
			return false;
		}
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
}

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}
</script>
</head>
<body>

<form action="${ctx}/permit/flow/actor/edit_actor.do" method="post" id="form" target="_self">
	<input type="hidden" id="taskId" name="taskId" value="${task.id}"/>
	<input type="hidden" id="startTask" name="startTask" value="${start}"/>
	
	<!-- 提交表单时的数据区域 -->
	<input type="hidden" id="actor_type" name="actor_type" value=""/>
	<input type="hidden" id="actor_bean" name="actor_bean" value=""/>
	<input type="hidden" id="actor_value" name="actor_value" value=""/>
	
<#if (task.nodeTypeIndex != "subProcessTask")>
<#include "/flow/actor/inc_actor.ftl">
 <div style="width:800px;" align="center">
 	<input type="button" value=" 保 存 " class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 </div>
<#else>
当前任务不支持参与者选择
</#if>

</form>
</body>
</html>