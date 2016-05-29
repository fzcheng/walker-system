<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加组织机构</title>

<script type="text/javascript">
	showTipInput("name");
	showTipInput("simpleName");
	showTipInput("type");
	showTipInput("system");
	showTipInput("attribute");
	showTipInput("summary");
	
function save(){
	var name = $.trim($("#name").val());
	var simpleName = $.trim($("#simpleName").val());
	if(name == ""){
		alert("请输入'机构名称'");
		return false;
	}
	//if(simpleName == ""){
		//alert("请输入'机构简称'");
		//return false;
	//}
	$('#form').ajaxSubmit({
        target: '#content',
        success: function(){
        	refreshCodeTree();
        }
    });
    return false; // <-- important!
	/**
	$("#form").ajaxForm({
		action: "${ctx}/admin/department/save.do",
		method: "post",
		success: function(data){
			alert(data);
		}
	});*/
}
</script>
</head>
<body>

<#if (isDepartment)>
	<#assign orgPrefix = "部门">
<#else>
	<#assign orgPrefix = "单位">
</#if>

	<form action="${ctx}/admin/department/edit.do" method="post" id="form" target="_self">
	<input type="hidden" id="id" name="id" value="${entity.id}">
 	<#if (isDepartment)>
		<input type="hidden" id="parentId" name="parentId" value="${parentId}">
	<#else>
		<input type="hidden" id="parentId" name="parentId" value="0">
	</#if>
 	<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
   	<tr class="title">
   		<td colspan="2">编辑${orgPrefix}</td>
   	</tr>
   	<tr>
   		<td class="showTitle" width="150px">(<span class="required">*</span>)${orgPrefix}名称：</td>
   		<td width="300px">
   			<input type="text" id="name" name="name" class="text" 
   			maxlength="10" title="机构的全称，不超过100字" value="${entity.name}"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">${orgPrefix}简称：</td>
   		<td >
   			<input type="text" id="simpleName" name="simpleName" class="text" 
   			maxlength="100"	title="机构简称，通常是缩写，可以不填" value="${entity.simpleName}"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">(<span class="required">*</span>)机构类型：</td>
   		<td width="260">
   			<select id="type" name="type"
   			title="<li>系统定义了[单位]，是最高级机构，下面可以从属多个部门。</li><li>支持多单位，每个单位可以由指定用户来管理。</li>">
   			<#if (isDepartment)>
   				<option value="1" selected="selected">下级部门</option>
   			<#else>
   				<option value="0" selected="selected">独立单位</option>
   			</#if>
   			</select>
   		</td>
   	</tr>
   	<#if (isDepartment)>
   	<tr>
   		<td class="showTitle">上级机构：</td>
   		<td >
   			${parentName}
   		</td>
   	</tr>
   	</#if>
   	<tr>
   		<td class="showTitle">是否系统机构：</td>
   		<td>
   			<select id="system" name="system" style="width:80px;" 
   				title="系统机构不允许普通管理员修改，只能超级管理员可以创建">
   				<#if (entity.system == 0)>
   				<option value="0" selected="selected">否</option>
   				<option value="1">是</option>
   				<#else>
   				<option value="1" selected="selected">是</option>
   				<option value="0">否</option>
   				</#if>
   			</select>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">${orgPrefix}属性：</td>
   		<td >
   			<input type="text" id="attribute" name="attribute" class="text" value="${entity.attribute}"
   			maxlength="100" title="<li>如果业务对机构有特别要求，可以自由使用此字段。</li><li>如：某部门可以标记为[中心]或者[二级单位]等。</li>"/>
   		</td>
   	</tr>
   	<#if (!isDepartment)>
   	<tr>
   		<td class="showTitle">启用单位管理员：</td>
   		<td>
   			<select id="administrate" name="administrate" style="width:80px;">
   				<#if (entity.administrate == 0)>
   				<option value="0" selected="selected">否</option>
   				<option value="1">是</option>
   				<#else>
   				<option value="1" selected="selected">是</option>
   				<option value="0">否</option>
   				</#if>
   			</select>
   		</td>
   	</tr>
   	</#if>
   	<tr>
   		<td class="showTitle">${orgPrefix}主管：</td>
   		<td >
   			<input type="text" id="chargeMan" name="chargeMan" class="text" value="${entity.chargeMan}"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">部门经理：</td>
   		<td >
   			<input type="text" id="manager" name="manager" class="text" value="${entity.manager}"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">备注：</td>
   		<td >
   			<input type="text" id="summary" name="summary" class="text" maxlength="120"
   				title="机构描述信息，不超过120字，可不填" value="${entity.summary}"/>
   		</td>
   	</tr>
   </table>
   <div>
   	<input type="button" value="保存${orgPrefix}" class="button" onclick="save();"/>
   </div>
 	</form>
</body>
</html>