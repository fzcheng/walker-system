<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/select/jquery.cxselect.min.js"></script>
<script type="text/javascript">
		
var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function(){
	var value = "${type}";
	if(value == "-1"){
		// 子系统
		$("#tr_parent").hide();
		$("#tr_url").show();
		$("#tr_openStyle").show();
		$("#tr_pointer").hide();
	} else if(value == "0"){
		// 功能组
		$("#tr_parent").show();
		$("#tr_url").hide();
		$("#tr_openStyle").hide();
		$("#tr_pointer").hide();
		$("#parentDiv").hide();
		$("#parentDiv2").show();
	} else {
		// 功能项
		$("#tr_parent").show();
		$("#tr_url").show();
		$("#tr_openStyle").show();
		$("#tr_pointer").show();
		$("#parentDiv").show();
		//$("#parentDiv2").hide();
	}
});

function save(){
	var f_type = $("input[name='type']:checked").val();
	// 初始化type选择radio，因为用户可能不会点击此按钮
	/*
	if(typeof(f_type) == 'undefined'){
		f_type = "1";
	}*/
	console.log("type = " + f_type);
	if(!checkValue($.trim($("#id").val()), "请输入功能ID")){
		return false;
	}
	if(!checkValue($.trim($("#name").val()), "请输入功能名称")){
		return false;
	}
	if(!checkValue($.trim($("#orderNum").val()), "请输入顺序号")){
		return false;
	}
	if(f_type == "1" && !checkValue($.trim($("#url").val()), "请输入功能对应的URL地址")){
		return false;
	}
	
	var parentId = "";
	if(f_type == "0"){
		// 模块
		parentId = $("#p_sys").val();
	} else if(f_type == "1"){
		// 功能项
		parentId = $("#p_grp").val();
	}
	console.log("parentId = " + parentId);
	if(f_type != "-1" && isEmptyValue(parentId)){
		alert("请选择上级功能");
		return false;
	}
	$("#parentId").val(parentId);
	
	$('#form').ajaxSubmit({
		//dataType: 'text',
    //target: '#content',
    success: function(data){
    	if(data == "success"){
	     	parent.frames.sampleframe.reload(1);
	     	//alert("数据保存成功");
	     	closeWindow();
    	} else {
    		showErrorTip(data);
    	}
    },
    error:function(data){
    	try{
     	console.log("error:" + data);
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
<form action="${ctx}/permit/admin/function/edit.do" method="post" id="form" target="_self">
	<input type="hidden" id="parentId" name="parentId" value=""/>
  <table border="0" cellpadding="0" cellspacing="0" class="table-form">
  	<tr>
  		<td width="120px" class="showTitle">(<span class="required">*</span>)功能分类：</td>
  		<td>
  		<#if (type == -1)>
  			<input type="radio" name="type" value="-1" readonly="readonly" checked="checked"/>子系统&nbsp;
  		<#elseif (type == 0)>
  			<input type="radio" name="type" value="0" readonly="readonly" checked="checked"/>模块[功能组]&nbsp;
  		<#else>
  			<input type="radio" name="type" value="1" readonly="readonly" checked="checked"/>功能项&nbsp;
  		</#if>
  		</td>
  	</tr>
  	<tr>
  		<td width="120px" class="showTitle">(<span class="required">*</span>)功能ID：</td>
  		<td><input type="text" id="id" name="id" style="width:260px;" class="text" maxlength="36" value="${functionObj.id}" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td width="120px" class="showTitle">(<span class="required">*</span>)功能名称：</td>
  		<td><input type="text" id="name" name="name" style="width:260px;" class="text" maxlength="100" value="${functionObj.name}"/></td>
  	</tr>
  	<tr>
  		<td width="120px" class="showTitle">(<span class="required">*</span>)顺序号：</td>
  		<td><input type="text" id="orderNum" name="orderNum" style="width:260px;" class="text" maxlength="8" value="${functionObj.orderNum}"/></td>
  	</tr>
  	<tr id="tr_parent">
  		<td width="120px" class="showTitle">(<span class="required">*</span>)上级功能：</td>
  		<td>
  			<#if (type == 1)>
  			<div id="parentDiv">
  				<select class="system" data-value="${parentSystem}"></select>
   				<select class="group" id="p_grp" name="p_grp" data-value="${parentGroup}"></select>
  			</div>
  			<#elseif (type == 0)>
  			<div id="parentDiv2" style="display:none;">
  				<select class="system" id="p_sys" name="p_sys" disabled="disabled" data-value="${parentSystem}"></select>
  			</div>
  			</#if>
  		</td>
  	</tr>
  	<tr id="tr_openStyle">
  		<td width="120px" class="showTitle">(<span class="required">*</span>)打开方式：</td>
  		<td>
  			<#if (functionObj.openStyle == 0)>
  			<input type="radio" name="openStyle" value="0" checked="checked"/>内嵌-iframe&nbsp;
  			<input type="radio" name="openStyle" value="1"/>内嵌-弹出窗口&nbsp;
  			<input type="radio" name="openStyle" value="2"/>内嵌-覆盖主界面&nbsp;
  			<input type="radio" name="openStyle" value="9"/>外部-浏览器打开新窗口&nbsp;
  			<#elseif (functionObj.openStyle == 1)>
  			<input type="radio" name="openStyle" value="0"/>内嵌-iframe&nbsp;
  			<input type="radio" name="openStyle" value="1" checked="checked"/>内嵌-弹出窗口&nbsp;
  			<input type="radio" name="openStyle" value="2"/>内嵌-覆盖主界面&nbsp;
  			<input type="radio" name="openStyle" value="9"/>外部-浏览器打开新窗口&nbsp;
  			<#elseif (functionObj.openStyle == 2)>
  			<input type="radio" name="openStyle" value="0"/>内嵌-iframe&nbsp;
  			<input type="radio" name="openStyle" value="1"/>内嵌-弹出窗口&nbsp;
  			<input type="radio" name="openStyle" value="2" checked="checked"/>内嵌-覆盖主界面&nbsp;
  			<input type="radio" name="openStyle" value="9"/>外部-浏览器打开新窗口&nbsp;
  			<#else>
  			<input type="radio" name="openStyle" value="0"/>内嵌-iframe&nbsp;
  			<input type="radio" name="openStyle" value="1"/>内嵌-弹出窗口&nbsp;
  			<input type="radio" name="openStyle" value="2"/>内嵌-覆盖主界面&nbsp;
  			<input type="radio" name="openStyle" value="9" checked="checked"/>外部-浏览器打开新窗口&nbsp;
  			</#if>
  		</td>
  	</tr>
  	<tr id="tr_url">
  		<td width="120px" class="showTitle">(<span class="required">*</span>)功能地址：</td>
  		<td><input type="text" id="url" name="url" style="width:260px;" class="text" maxlength="100" value="${functionObj.url}"/>功能组不必填</td>
  	</tr>
  	<tr id="tr_pointer">
  		<td width="120px" class="showTitle">功能点：</td>
  		<td><input type="text" id="pointer" name="pointer" style="width:260px;" class="text" maxlength="120" value="${functionObj.pointer}" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td width="120px" class="showTitle">(<span class="required">*</span>)是否启用：</td>
  		<td>
  			<#if (functionObj.run == 1)>
  			<input type="radio" name="run" value="1" checked="checked"/>启用&nbsp;
  			<input type="radio" name="run" value="0"/>停用&nbsp;
  			<#else>
  			<input type="radio" name="run" value="1"/>启用&nbsp;
  			<input type="radio" name="run" value="0" checked="checked"/>停用&nbsp;
  			</#if>
  		</td>
  	</tr>
  </table>
</form>
<div style="width:100%;" align="center">
<input type="button" id="saveBtn" value=" 保 存 " onclick="save();" class="button-tj"/>&nbsp;
<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
</div>
</body>
<script type="text/javascript">
$.cxSelect.defaults.url = "js/cityData.min.json";
$.cxSelect.defaults.nodata = "none";

var data = stringToJson("${sysGroupJson}"); // 包含两级数据
//console.log(data);

<#if (type == 1)>
$("#parentDiv").cxSelect({
	selects : ["system", "group"],
	required : true,
	url: data
});
</#if>

<#if (type == 0)>
$("#parentDiv2").cxSelect({
	selects : ["system"],
	required : true,
	url: data
});
</#if>
</script>
</html>