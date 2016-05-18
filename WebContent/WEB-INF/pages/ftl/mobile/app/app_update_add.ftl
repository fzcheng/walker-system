<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加应用升级版本</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>

<!-- 上传组件 -->
<script type="text/javascript" src="${ctx}/script/lib/jquery.MetaData.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.MultiFile.pack.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.MultiFile.js"></script>
	
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	showTipInput("id");
	showTipInput("name");
	
 $('#file').MultiFile({ 
	   max:1,
	   accept: 'apk',
    STRING: {
     remove: 'Remover',
     selected:'已选择文件: $file',
     denied:'无效的文件类型: $ext!'
    }
   });
});

function save(){
	var file = document.getElementById("file");
	if(isEmptyValue(file.value)){
		alert("请上传文件，先。");
		return false;
	}
	
	$('#form').ajaxSubmit({
			//dataType: 'text',
       //target: '#content',
       success: function(data){
    	  result = htmlToString(data);
        parent.frames.sampleframe.reload(1);
       	if(result == "success"){
       	} else {
       		showErrorTip(result);
       	}
        setTimeout(function(){closeWindow();},0);
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

<form action="${ctx}/permit/mobile/app/save_update.do" method="post" id="form" target="_self" enctype="multipart/form-data">
<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>应用ID：</td>
  		<td>
  			<input type="text" id="appId" name="appId" style="width:260px;" class="text" maxlength="50" readonly="readonly" value="${entity.id}"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>应用名称：</td>
  		<td>
  			<input type="text" id="appName" name="appName" style="width:260px;" class="text" maxlength="30" readonly="readonly" value="${entity.name}"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>升级到版本：</td>
  		<td>
  			<input type="text" id="versionCode" name="versionCode" style="width:260px;" class="text" maxlength="5" title="只能输入正整数" readonly="readonly" value="${entity.releaseVersion+1}"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>是否强制升级：</td>
  		<td>
  			<select name="updateForce">
  				<option value="0" selected="selected">不强制，用户可忽略</option>
  				<option value="1">强制升级，否则无法使用</option>
  			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>上传升级APP：</td>
  		<td>
  			<input type="file" class="multi" maxlength="1" id="file" name="file"/><br>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">升级描述：</td>
  		<td valign="top">
  			<textarea id="summary" name="summary" style="width:260px;" rows="3" class="textarea"></textarea>
  		</td>
  	</tr>
 </table>
 </div>
 <div style="width:100%;" align="center">
 	<input type="button" value="确 定" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>

</html>