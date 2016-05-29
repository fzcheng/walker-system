<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/css.css">
    
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript">
		<!--
		$(document).ready(function(){
			$("#roleName").focus();
		});
		//注：每个嵌入页必须定义该方法，供父窗口调用，并且返回true或false来告之父窗口是否关闭
			function Ok(){
				//相关逻辑代码
				//alert(parent.frames.sampleframe.mygrid);
				//window.frames.sampleframe.mygrid.reload();
				return false;
				
			}
			
			function f_check(){
				if($("#roleName").val() == ""){		
					alert("请输入角色名称！");
					$("#roleName").focus();
					return false;
				}
				return true;
			}
		//-->
		</script>
  </head>
  
<body>
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
  	<tr>
  		<td width="100" align="center">角色ID：</td>
  		<td>${rid}</td>
  	</tr>
  	<tr>
  		<td width="100" align="center">角色名称：</td>
  		<td><input type="text" id="roleName" name="roleName" style="width:260px;"/></td>
  	</tr>
  	<tr>
  		<td align="center">角色描述：</td>
  		<td><textarea id="summary" name="summary" style="width:260px;" rows="6"></textarea></td>
  	</tr>
  </table>
</body>
</html>
