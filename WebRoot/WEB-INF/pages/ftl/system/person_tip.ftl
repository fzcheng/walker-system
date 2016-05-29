<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
function goBack(){
	$("#content").load("${ctx}/permit/person/editPass.do", null, null);
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="center" valign="top">
		 	<table border="0" cellpadding="0" cellspacing="0" width="420px" class="table-form">
		   	<tr class="title">
		   		<td>个人设置提示：</td>
		   	</tr>
		   	<tr>
		   		<td width="300px" align="left">
		   			${msg}
		   		</td>
		   	</tr>
		   	<tr>
		   		<td>
						<input type="button" class="button" value=" 返 回 " onclick="goBack();"/>
					</td>
		   	</tr>
		   </table>
		</td>
	</tr>
</table>
 	
</body>
</html>