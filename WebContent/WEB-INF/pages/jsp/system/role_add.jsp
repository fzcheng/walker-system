<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../taglibs.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/public.css">
    
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
					url:"${ctx}/admin/role/createRole.do",
					data:"roleName=" + $("#roleName").val() + "&summary=" + $("#summary").val(),
					success:function(msg){
						if(msg == "0"){
							alert("角色创建成功！");
							//parent.frames.sampleframe.mygrid.enablePaging(true,12,5,"pagingArea",true);
							parent.frames.sampleframe.reloadGrid();
							//虽然按照组建说明文档，返回了TRUE，但窗口却不会关闭。应该是AJAX重新提交后，返回了新的值!
							//parent.JqueryDialog.Close();
							result = true;
						} else if(msg == "1"){
							alert("创建的角色名称已经存在，角色名称不能重复！请重新输入“角色名称”。");
							$("#roleName").focus();
						} else {
							alert("创建数据出现错误！");
						}
					}
				});
				return result;
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
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="formtable">
  	<tr>
  		<td width="120px" class="title">角色名称：(<span class="required">*</span>)</td>
  		<td><input type="text" id="roleName" name="roleName" style="width:260px;" class="textbox_req"/></td>
  	</tr>
  	<tr>
  		<td valign="top" class="title">角色描述：</td>
  		<td><textarea id="summary" name="summary" style="width:260px;" rows="6" class="textarea"></textarea></td>
  	</tr>
  </table>
</body>
</html>
