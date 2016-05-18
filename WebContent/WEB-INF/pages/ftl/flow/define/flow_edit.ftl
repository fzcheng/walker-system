<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑流程信息</title>
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
	showTipInput("subProcess");
	showTipInput("businessType");
	showTipInput("workflowCreateListener");
	showTipInput("workflowEndListener");
	showTipInput("pageUrlId");
	showTipInput("workDayLimit");
	showTipInput("remindTemplate");
 });

function save(){
	var name = $.trim($("#name").val());
	if(name == ""){
		alert("请输入'流程名称'");
		return false;
	}
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	if(data == "success"){
	        	parent.frames.sampleframe.reload(1);
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

<form action="${ctx}/permit/flow/define/saveEdit.do" method="post" id="form" target="_self">
	<input type="hidden" id="id" name="id" value="${processDefine.id}"/>
	<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
  	<tr class="title">
  		<td colspan="2">编辑流程</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)流程标识：</td>
  		<td width="300px">${processDefine.interIdentifier}</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)流程名称：</td>
  		<td width="300px">
  			<input type="text" id="name" name="name" class="text" 
  			maxlength="50" title="不超过100字" value="${processDefine.name}"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">当前版本：</td>
  		<td>${processDefine.version}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)是否子流程：</td>
  		<td width="260">
  			<select id="subProcess" name="subProcess" style="width:200px;"
  			title="<li>如果是子流程，那么它必须属于一个主流程中的子任务。</li><li>一般流程都是独立流程。</li>">
  				<#if (processDefine.subProcess == 0)>
	  				<option value="0" selected="selected">独立流程</option>
	  				<option value="1">子流程(属于某个独立流程)</option>
  				<#else>
	  				<option value="1" selected="selected">子流程(属于某个独立流程)</option>
  					<option value="0">独立流程</option>
  				</#if>>
  			</select>
  		</td>
  	</tr>
  	
  	<tr>
  		<td class="showTitle">流程所属业务类型：</td>
  		<td>
  			<select id="businessType" name="businessType" style="width:200px;" title="业务类型为系统可用的与业务有关的分类，系统可配置">
	 				<#list businessType?keys as k>
	 					<#if (processDefine.businessType == k)>
	 						<option value="${k}" selected="selected">${businessType[k]}</option>
	 					<#else>
			 				<option value="${k}">${businessType[k]}</option>
	 					</#if>
	 				</#list>
	 			</select>
  		</td>
  	</tr>
  		
  	<tr>
  		<td class="showTitle">流程创建事件监听：</td>
  		<td>
  			<select id="createListenerName" name="createListenerName" style="width:360px;" 
 				title="用户可配置监听器，来自定义创建的用户回调方法，可用在系统中配置对应类。">
 					<option value="0">N/A</option>
 					<#list createListener?keys as key>
 						<#if (processDefine.createListenerName == key)>
	 						<option value="${key}" selected="selected">${createListener[key]}</option>
	 					<#else>
			 				<option value="${key}">${createListener[key]}</option>
	 					</#if>
 					</#list>
 			</select>
 		</td>
 	</tr>
 	<tr>
  		<td class="showTitle">流程结束事件监听：</td>
  		<td>
  			<select id="endListenerName" name="endListenerName" style="width:360px;" 
 				title="用户可配置监听器，来自定义流程结束时的用户回调方法，可用在系统中配置对应类。">
 					<option value="0">N/A</option>
 					<#list endListener?keys as key>
 						<#if (processDefine.endListenerName == key)>
	 						<option value="${key}" selected="selected">${endListener[key]}</option>
	 					<#else>
			 				<option value="${key}">${endListener[key]}</option>
	 					</#if>
 					</#list>
 			</select>
 		</td>
 	</tr>
 	<tr>
 		<td class="showTitle">流程使用的业务页面：</td>
 		<td >
 			<input type="text" id="pageUrlId" name="pageUrlId" class="text" 
 			maxlength="100" 
 			title="<li>如果业务流程简单，仅需要唯一的页面可以配置此项。</li><li>但通常可能每个任务都需要引用不同页面，如果这样，此项可不用配置。</li>" 
 			value="${processDefine.pageUrlId}"/>
 		</td>
 	</tr>
 	<tr>
 		<td class="showTitle">(<span class="required">*</span>)任务办理时间限制：</td>
 		<td >
 			<input type="text" id="workDayLimit" name="workDayLimit" class="text" 
 			value="${processDefine.workDayLimit}" maxlength="100" title="<li>设置流程中每个任务处理时限，超过时系统会催办，默认0不催办</li>"/>
 		</td>
 	</tr>
 	<tr>
 		<td class="showTitle">任务提醒模板：</td>
 		<td>
 			<textarea rows="5" cols="60" id="remindTemplate" name="remindTemplate">${processDefine.remindTemplate}</textarea>
 		</td>
 	</tr>
 	<tr>
 		<td class="showTitle">备注：</td>
 		<td >
 			<input type="text" id="summary" name="summary" class="text" maxlength="120"
 				title="备注描述，不超过120字，可不填" value="${processDefine.summary}"/>
 		</td>
 	</tr>
 </table>
 <div style="width:800px;" align="center">
 <#if (processDefine.deleteStatus == 0)>
 	<input type="button" value=" 保 存 " class="button" onclick="save();"/>&nbsp;&nbsp;
 <#else>
 	已废弃，无法更新
 </#if>
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>
</html>