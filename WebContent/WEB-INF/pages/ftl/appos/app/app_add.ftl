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
	$("#appname").focus();
});
		
function save(){
	if(!f_check()){
		return false;
	}
	$('#form').ajaxSubmit({
		//dataType: 'text',
		//target: '#content',
		success: function(data){
			if(data == "success"){
				//alert("数据保存成功");
		    	parent.frames.sampleframe.reload(1);
		    	closeWindow();
			} else {
				showErrorTip(data);
			}
		},
		error:function(data){
			try{
				//alert(1);
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
		//注：每个嵌入页必须定义该方法，供父窗口调用，并且返回true或false来告之父窗口是否关闭
			function Ok(){
				//相关逻辑代码
				alert(parent.frames.sampleframe.mygrid);
				//window.frames.sampleframe.mygrid.reload();
				if(!f_check()){
					return false;
				} else {
				 return postData();
				}
			}
			
			function postData(){
				var result = false;
				$.ajax({
					async:false,
					type:"post",
					url:"${ctx}/appos/app/saveApp.do",
					data:"parentId=" + $("#parentId").val() + "&codeName=" + $("#codeName").val() + "&codeStand=" + $("#codeStand").val(),
					success:function(msg){
						if(msg == "0"){
							alert("应用保存成功！");
							parent.frames.sampleframe.refreshCodeTree();
							result = true;
						} else {
							alert("应用保存出现错误！");
						}
					}
				});
				return result;
			}
			
function f_check(){
	if($("#appcode").val() == ""){		
		alert("错误：appcode为空！");
		return false;
	}
	if($("#appname").val() == ""){		
		alert("请输入应用名称！");
		$("#appname").focus();
		return false;
	}
	if($("#package_name").val() == ""){		
		alert("请输入应用包名！");
		$("#package_name").focus();
		return false;
	}
	return true;
}
		//-->
</script>
  </head>
  
<body>
	<form action="${ctx}/appos/app/saveApp.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td width="160" class="showTitle">appid：</td>
  		<td><input type="text" id="appid" name="appid" style="width:200px;" disabled/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">appcode：</td>
  		<td><input type="text" id="appcode" name="appcode" class="text" readonly="readonly"
  			value="${appcode}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">应用名称：</td>
  		<td><input type="text" id="appname" name="appname" class="text"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">应用包名：</td>
  		<td><input type="text" id="package_name" name="package_name" class="text"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">所属公司id：</td>
  		<td><input type="text" id="companyid" name="companyid" class="text"/></td>
  	</tr>
  </table>
   <div style="width:99%;" align="center">
	 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
	 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
	 </div>
  </form>
</body>
</html>
