<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
function savePass(){
	//提交更新数据
	/**
	$.ajax({
		url:"${ctx}/admin/preference/updatePrefer.do",
		type:"post",
		data:"preferId=" + preferId + "&modText=" + modText,
		success:function(msg){
			alert(msg);
		}
	});
	*/
	
	var oldPass = $.trim($("#p_op").val());
	var newPass = $.trim($("#p_np").val());
	var confirmPass = $.trim($("#p_cp").val());
	if(oldPass == "" || newPass == "" || confirmPass == ""){
		alert("请输入完整要修改的密码信息: 老密码、新密码和确认密码。");
		return;
	}
	if(!validPassword(newPass)){
		alert("输入的密码必须是：字母、数字、下划线或者他们的组合。");
		$("#p_np").focus();
		return;
	}
	if(newPass != confirmPass){
		alert("两次密码输入的不一致，请确认后重新输入。");
		$("#p_cp").focus();
		return;
	}
	var params = {"p_op":oldPass, "p_np":newPass, "p_cp":confirmPass};
	$("#content").load("${ctx}/permit/person/savePass.do", params, null);
}
</script>
</head>
<body>

<form action="" method="post" id="form">
	<table border="0" cellpadding="0" cellspacing="0" class="table-form" style="width:420px;">
  	<tr class="title">
  		<td colspan="2">修改个人密码</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="120px">(<span class="required">*</span>)老密码：</td>
  		<td width="300px" align="left">
  			<input type="password" id="p_op" class="textbox_req" maxlength="30"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)新密码：</td>
  		<td align="left">
  			<input type="password" id="p_np" class="textbox_req" 
			maxlength="30" title="只能输入英文、数字、下划线或者其组合，不超过30个字符"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)确认密码：</td>
  		<td align="left">
  			<input type="password" id="p_cp" class="textbox_req" 
			maxlength="30" title="只能输入英文、数字、下划线或者其组合，不超过30个字符"/>
		</td>
  	</tr>
  </table>
  <div>
  	<input type="button" value="保存密码" onclick="savePass();" class="button"/>&nbsp;
  </div>
</form>
 	
</body>
</html>