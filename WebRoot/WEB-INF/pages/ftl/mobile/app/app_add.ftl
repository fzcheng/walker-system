<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加新应用</title>
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
	   accept: 'apk|rar',
    STRING: {
     remove: 'Remover',
     selected:'已选择文件: $file',
     denied:'无效的文件类型: $ext!'
    }
   });
});

function save(){
	if(!validateRequired("id")){
		return false;
	}
	
	if(!validateRequired("name")){
		return false;
	}
	
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

<form action="${ctx}/permit/mobile/app/save.do" method="post" id="form" target="_self" enctype="multipart/form-data">
<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>应用ID：</td>
  		<td>
  			<input type="text" id="id" name="id" style="width:260px;" class="text" maxlength="50" title="不超过50字符"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>应用名称：</td>
  		<td>
  			<input type="text" id="name" name="name" style="width:260px;" class="text" maxlength="30" title="不超过30汉子"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>发布版本：</td>
  		<td>
  			<input type="text" id="releaseVersion" name="releaseVersion" style="width:260px;" class="text" maxlength="5" title="只能输入数字" value="1" readonly="readonly"/>
  		</td>
  	</tr>
  	  	<tr>
  		<td class="showTitle" width="15%"><span class="required">*</span>上传安卓APP：</td>
  		<td>
  			<input type="file" class="multi" maxlength="2" id="file" name="file"/><br>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">备注：</td>
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