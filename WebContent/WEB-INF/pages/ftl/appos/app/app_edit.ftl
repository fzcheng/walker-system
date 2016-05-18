<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
    
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
    <script type="text/javascript">
		<!--
		
var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function(){
	$("#codeName").focus();
});

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

function save(){
	if(!f_check()){
		return false;
	}
	$('#form').ajaxSubmit({
		//dataType: 'text',
		//target: '#content',
		success: function(data){
			if(data == "success"){
		    	parent.frames.sampleframe.refreshCodeTree();
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
		
function f_check(){
	if($("#parentId").val() == ""){		
		alert("保存错误：代码ID不存在！");
		return false;
	}
	if($("#codeName").val() == ""){		
		alert("请输入代码项名称！");
		$("#codeName").focus();
		return false;
	}
	return true;
}
		//-->
		</script>
  </head>
  
<body>
	<form action="${ctx}/admin/code/updateCode.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle">代码ID：</td>
  		<td><input type="text" id="parentId" name="parentId" class="text" readonly="readonly"
  			value="${id}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">代码名称(*)：</td>
  		<td><input type="text" id="codeName" name="codeName" class="text"
  			value="${codeName}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">标准代码编号：</td>
  		<td><input type="text" id="codeStand" name="codeStand" class="text"/></td>
  	</tr>
  </table>
  <div style="width:99%;" align="center">
	 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
	 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
	</div>
  </form>
</body>
</html>
