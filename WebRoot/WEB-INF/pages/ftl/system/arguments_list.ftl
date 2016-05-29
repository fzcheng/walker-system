<form id="myform1" name="myform1">
<input type="hidden" id="groupId" name="groupId" value="${groupId}"/>
<table border="0" cellspacing="0" cellpadding="0" class="table-form"> 
  <tr class="title">  
    <td width="460px">系统参数描述</td>  
    <td width="80px">数据类型</td>  
    <td width="220px">当前值</td>  
 		<td>默认值</td>  
  </tr>
  <#assign _index = 0>
  <#list varList as var> 
  <#if (_index % 2 == 0)>
  	<#assign _color2 = "table-date select">
  <#else>
  	<#assign _color2 = "table-date">
  </#if>
  <tr>  
    <td>${var.description}&nbsp;[${var.id}]</td>  
    <td>
    	<#if (var.type == "String")>
    		字符串
    	<#elseif (var.type == "Boolean")>
    		布尔类型
    	<#elseif (var.type == "Integer")>
    		整数
    	<#elseif (var.type == "Float")>
    		浮点数
    	<#else>
    		unknown:${var.type}
    	</#if>
    </td>
    <td width="180px">
    	<#if (var.type == "String")>
    		<#assign _value = var.stringValue>
    	<#elseif (var.type == "Boolean")>
    		<#assign _value = var.booleanValue>
    	<#elseif (var.type == "Integer")>
    		<#assign _value = var.integerValue>
    	<#elseif (var.type == "Float")>
    		<#assign _value = var.floatValue>
    	<#else>
    		<#assign _value = "N/A">
    	</#if>
    	
    	<#if (var.type == "Boolean")>
    		<select style="width:80px;" id="${var.id}" name="${var.id}">
    			<#if (var.booleanValue)>
    				<option value="true" selected="selected">是</option>
    				<option value="false">否</option>
    			<#else>
	    			<option value="true">是</option>
	    			<option value="false" selected="selected">否</option>
    			</#if>
    		</select>
    	<#else>
	    	<input type="text" id="${var.id}" name="${var.id}" maxlength="200" class="text" value="${_value}"/>&nbsp;
    	</#if>
    </td>  
    <td>${var.defaultValue}</td>
  </tr>
  <#assign _index = _index+1>
  </#list> 
</table>
<input type="button" value=" 更新配置选项 " onclick="submitForm();" class="button-tj" style="margin-top:2px;"/>
&nbsp;系统缓存和配置文件同时更新。
</form>