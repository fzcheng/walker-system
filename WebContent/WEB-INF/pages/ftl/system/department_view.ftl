<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>显示组织机构</title>
</head>
<body>

<#if (entity.type == 1)>
	<#assign orgPrefix = "部门">
<#elseif (entity.type == 0)>
	<#assign orgPrefix = "单位">
<#elseif (entity.type == 9)>
	<#assign orgPrefix = "岗位">
<#else>
	<#assign orgPrefix = "N/A">
</#if>

<input type="hidden" value="" id="1">
	<form action="" method="post" id="form">
	<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
  	<tr class="title">
  		<td colspan="2">显示详细${orgPrefix}信息</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)${orgPrefix}名称：</td>
  		<td width="300px">${entity.name}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">${orgPrefix}简称：</td>
  		<td>${entity.simpleName}&nbsp;</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)机构类型：</td>
  		<td width="260">
  			<#if (entity.type == 0)>
  				独立单位
  			<#elseif (entity.type == 1)>
  				下级部门
  			<#elseif (entity.type == 9)>
  				岗位
  			</#if>
  		</td>
  	</tr>
  	<#if (entity.type == 1)>
  	<tr>
  		<td class="showTitle">上级机构：</td>
  		<td >
  			${parentName}
  		</td>
  	</tr>
  	</#if>
  	<tr>
  		<td class="showTitle">是否系统机构：</td>
  		<td >
  			<#if (entity.system == 0)>
  			否
  			<#else>
  			是
  			</#if>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">${orgPrefix}属性：</td>
  		<td>${entity.attribute}&nbsp;</td>
  	</tr>
  	<#if (!isDepartment)>
  	<tr>
  		<td class="showTitle">启用单位管理员：</td>
  		<td>
  			<#if (entity.administrate == 0)>
  			否
  			<#else>
  			是
  			</#if>
  		</td>
  	</tr>
  	</#if>
  	<tr>
  		<td class="showTitle">${orgPrefix}主管：</td>
  		<td >
  			${entity.chargeMan}&nbsp;
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">部门经理：</td>
  		<td >
  			${entity.manager}&nbsp;
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">备注：</td>
  		<td >
  			${entity.summary}&nbsp;
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">状态：</td>
  		<td >
  			<#if (entity.status == 1)>
  			<font style="color:red;">已删除</font>
 			<#else>
 			正常
 			</#if>
 		</td>
 	</tr>
 </table>
 <div>
 	<#if (canEdit)>
 	<input type="button" value="编辑${orgPrefix}" onclick="showEditPage('${entity.id}')" class="button"/>&nbsp;
 	</#if>
 	<#if (canDel && entity.childSum == 0 && entity.status == 0)>
 	<input type="button" value="删 除" onclick="deleteOrg('${entity.id}')" class="button"/>
 	<input type="button" value="彻底删 除" onclick="easeOrg('${entity.id}')" class="button"/>
 	</#if>
 </div>
</form>
</body>
</html>