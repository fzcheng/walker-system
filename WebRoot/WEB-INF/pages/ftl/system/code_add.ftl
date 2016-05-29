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

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}
		//注：每个嵌入页必须定义该方法，供父窗口调用，并且返回true或false来告之父窗口是否关闭
			function Ok(){
				//相关逻辑代码
				//alert(parent.frames.sampleframe.mygrid);
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
					url:"${ctx}/admin/code/saveCode.do",
					data:"parentId=" + $("#parentId").val() + "&codeName=" + $("#codeName").val() + "&codeStand=" + $("#codeStand").val(),
					success:function(msg){
						if(msg == "0"){
							alert("代码项保存成功！");
							parent.frames.sampleframe.refreshCodeTree();
							result = true;
						} else {
							alert("保存代码项出现错误！");
						}
					}
				});
				return result;
			}
			
function f_check(){
	if($("#parentId").val() == ""){		
		alert("保存错误：上级代码ID不存在！");
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
	<form action="${ctx}/admin/code/saveCode.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td width="160" class="showTitle">代码ID：</td>
  		<td><input type="text" id="codeId" name="codeId" style="width:200px;" disabled/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">上级代码：</td>
  		<td><input type="text" id="parentId" name="parentId" class="text" readonly="readonly"
  			value="${parentCodeId}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">代码名称(*)：</td>
  		<td><input type="text" id="codeName" name="codeName" class="text"/></td>
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
