<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构管理</title>
	
<script type="text/javascript">

</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" width="420px">
	<tr>
		<td width="420px" valign="top">
			<table border="0" cellpadding="0" cellspacing="0" width="420px" class="table-form">
				<tr class="title"><td>机构删除成功</td></tr>
				<tr>
					<td>
						<#if (msg == "")>
							<li>
							机构删除后，缓存中仍然存在记录，因此可以在业务数据中继续关联使用
							</li>
							<li>可以通过超级管理员权限恢复删除的机构</li>
							<li>如果是彻底删除，那么缓存信息将同步更新。</li>
						
						<#else>
							${msg}
						</#if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>