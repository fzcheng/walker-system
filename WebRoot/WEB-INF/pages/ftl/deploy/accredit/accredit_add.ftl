<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>创建新授权文件</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/lhgcalendar.min.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	showTipInput("orgName");
	showTipInput("projectName");
	showTipInput("authId");
	showTipInput("macAddress");
	$("#startTimeShow").calendar();
	$("#endTimeShow").calendar();
 });

function save(){
	if(!validateRequired("orgName")){
		return false;
	}
	if(!validateRequired("projectName")){
		return false;
	}
	if(!validateRequired("authId")){
		return false;
	}
	
	_type = getRadioSelectValue("authType");
	//console.log(_type);
	if(_type == "0" && !validateRequired("macAddress")){
		return false;
	} else if(_type == "1" && (!validateRequired("startTimeShow") || !validateRequired("endTimeShow"))){
		return false;
	} else if(_type == "2"){
		if(!validateRequired("macAddress") || !validateRequired("startTimeShow") || !validateRequired("endTimeShow")){
			return false;
		}
	}
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	if(data == "success"){
	        	parent.frames.sampleframe.reload(1);
	        	closeWindow();
        	} else {
        		showErrorTip(data);
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

function clickRadio(obj){
	v = obj.value;
	if(v == "0"){
		$("#macTr").show();
		$("#dateTr").hide();
	} else if(v == "1"){
		$("#macTr").hide();
		$("#dateTr").show();
	} else if(v == "2"){
		$("#macTr").show();
		$("#dateTr").show();
	}
}
</script>
</head>
<body>

<form action="${ctx}/supervisor/accredit/save.do" method="post" id="form" target="_self">
	<input type="hidden" id="processName" name="processName"/>
	<table border="0" cellpadding="0" cellspacing="0" width="99%" class="table-form">
  	<tr>
  		<td class="showTitle" width="20%">(<span class="required">*</span>)所属单位：</td>
  		<td width="80%">
  			<input type="text" id="orgName" name="orgName" class="text" 
  			maxlength="50" title="不超过20个字"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)项目(工程)名称：</td>
  		<td>
  			<input type="text" id="projectName" name="projectName" class="text" 
  			maxlength="50" title="不超过50字"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)(单位)唯一标识：</td>
  		<td>
  			<input type="text" id="authId" name="authId" class="text" 
  			maxlength="18" title="不超过20个字符，并确保唯一"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">当前使用jar库：</td>
  		<td>df</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)授权类型：</td>
  		<td>
  			<input type="radio" id="" name="authType" value="0" onclick="clickRadio(this);"/>MAC地址授权&nbsp;
  			<input type="radio" id="" name="authType" value="1" onclick="clickRadio(this);"/>时间段授权&nbsp;
  			<input type="radio" id="" name="authType" value="2" onclick="clickRadio(this);" checked="checked"/>两种都有&nbsp;
  		</td>
  	</tr>
  	<tr id="macTr">
  		<td class="showTitle">(<span class="required">*</span>)Mac地址：</td>
  		<td>
  			<input type="text" id="macAddress" name="macAddress" class="text" 
  			maxlength="18" title="输入授权绑定网卡的mac地址"/>
  		</td>
  	</tr>
  	<tr id="dateTr">
  		<td class="showTitle">(<span class="required">*</span>)时间段：</td>
  		<td>
  			<input type="text" id="startTimeShow" name="startTimeShow" maxlength="10" value="" style="width:80px;" class="text" readonly="readonly"/>&nbsp;到&nbsp;
				<input type="text" id="endTimeShow" name="endTimeShow" maxlength="10" value="" style="width:80px;" class="text" readonly="readonly"/>
  		</td>
  	</tr>
 </table>
 <div style="width:99%" align="center">
 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>
</html>