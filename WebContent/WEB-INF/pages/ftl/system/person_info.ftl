<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">

</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
 	<tr>
 		<td class="showTitle">登陆ID：</td>
 		<td align="left">${userInfo.loginId}</td>
 		<td class="showTitle">用户姓名：</td>
 		<td align="left">${userInfo.name}</td>
 	</tr>
 	<tr>
 		<td class="showTitle" width="12%">创建时间：</td>
 		<td width="38%" align="left">${userInfo.createTime?number_to_datetime}</td>
 		<td class="showTitle" width="12%">顺序号：</td>
 		<td width="38%" align="left">${userInfo.orderNum}</td>
 	</tr>
 	<tr>
 		<td class="showTitle">姓名拼音：</td>
 		<td align="left">N/A</td>
 		<td class="showTitle">用户类型：</td>
 		<td align="left">
 		<#if (userInfo.type == 0)>
 		超级管理员
 		<#elseif (userInfo.type == 1)>
 		单位管理员
 		<#else>
 		用户
 		</#if>
 		</td>
 	</tr>
 	<tr>
 		<td class="showTitle">启用状态：</td>
 		<td align="left">
 		<#if (userInfo.enable == 1)>
 		正在使用
 		<#else>
 		<font style="color:red;">禁止使用</font>
 		</#if>
 		</td>
 		<td class="showTitle">删除状态：</td>
 		<td align="left">
 		<#if (userInfo.status == 0)>
 		正在使用
 		<#else>
 		<font style="color:red;">已被删除</font>
 		</#if>
 		</td>
 	</tr>
 	<tr>
 		<td class="showTitle">所属单位（公司）：</td>
 		<td align="left">${userInfo.orgName}</td>
 		<td class="showTitle">所属部门：</td>
 		<td align="left">${userInfo.departmentName}</td>
 	</tr>
 	<tr>
 		<td class="showTitle">用户来源：</td>
 		<td align="left">
 		<#if (userInfo.fromType == 0)>
 		内部机构用户，即：从系统创建的用户
 		<#else>
 		<font style="color:red;">外部用户，如：通过其他模块（供应商/CMS）等同步的</font>
 		</#if>
 		</td>
 		<td class="showTitle">来源细分：</td>
 		<td align="left">外部来源用户细分描述，暂无</td>
 	</tr>
 	<tr>
 		<td class="showTitle">联系电话：</td>
 		<td align="left">${userInfo.tel}</td>
 		<td class="showTitle">手机：</td>
 		<td align="left">${userInfo.mobile}</td>
 	</tr>
 	<tr>
 		<td class="showTitle">Email：</td>
 		<td align="left">${userInfo.email}</td>
 		<td class="showTitle">选择风格：</td>
 		<td align="left">${userInfo.style}</td>
 	</tr>
 	<tr>
 		<td class="showTitle">性别：</td>
 		<td align="left">
 		<#if (userInfo.sex == 1)>
 		男
 		<#else>
 		女
 		</#if>
 		</td>
 		<td class="showTitle">备注：</td>
 		<td align="left">${userInfo.summary}</td>
 	</tr>
 </table>
 	
</body>
</html>