<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加新按钮</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	showTipInput("id");
	showTipInput("name");
	showTipInput("orderNum");
	showTipInput("url");
 });

function save(){
	// 供应商名称
	if(!validateRequired("id")){
		return false;
	}
	// 用户名称
	if(!validateRequired("name")){
		return false;
	}
	if(!validateRequired("url")){
		return false;
	}
	if(!validateOption("orderNum", isNumber)){
		return false;
	}
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	if(data == "success"){
	        	parent.frames.sampleframe.reload(1);
	        	setTimeout(function(){closeWindow();},0);
        	} else {
        		showErrorTip(data);
        		/*
        		_res = htmlToString(data);
        		if(_res.indexOf("ApplicationException") >= 0){
        			// 如果返回的是错误页面，就覆盖掉当前页面
	        		$("body").empty();
	        		$("body").html(data);
        		} else {
        			// 如果是业务提示
        			alert(data);
        		}
        		*/
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

<form action="${ctx}/permit/admin/function/save_pointer.do" method="post" id="form" target="_self">
<input type="hidden" id="functionId" name="functionId" value="${functionId}"/>
<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle" width="25%">(<span class="required">*</span>)功能名称：</td>
  		<td width="75%">${functionName}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)按钮ID：</td>
  		<td>
  			<input type="text" id="id" name="id" class="text" 
  			maxlength="50" title="必填项，不超过50字符"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)按钮名称：</td>
  		<td>
  			<input type="text" id="name" name="name" class="text" 
  			maxlength="10" title="不超过10个字"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)排序号：</td>
  		<td>
  			<input type="text" id="orderNum" name="orderNum" class="text" 
  				maxlength="8" title="必填项，只能输入数字，越大越靠后"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)URL：</td>
  		<td>
  			<input type="text" id="url" name="url" class="text" 
  				maxlength="100" title="必填项，按钮对应的请求地址，100字符以内"/>
  		</td>
  	</tr>
 </table>
 </div>
 <div style="width:100%;" align="center">
 	<input type="button" value="创 建" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>
</html>