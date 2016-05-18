<table border="0" cellspacing="0" cellpadding="0"  class="table-box">
  <tr class="table-tit">
    <td width="5%">
    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
    </td>
    <td width="10%">ID</td>
    <td width="15%">功能名称</td>
    <td width="10%">序号</td>
    <td width="10%">类别</td>
    <td width="10%">上级ID</td>
    <td width="10%">设置</td>
    <td width="30%">功能URL</td>
  </tr>
  
 <#list pagerView.datas as obj>
 	<#if obj.type == 1>
 	<tr class="table-date">
 	<#elseif (obj.type == 0)>
  <tr class="table-date" style="background:#F2F2F2;">
  <#else>
  <tr class="table-date" style="background:#EEF7FC;">
 	</#if>
    <td>
    	<input name="ids" type="checkbox" value="${obj.id}" class="check_box" />
    </td>
    <td>${obj.id}</td>
    <td>
    	<#if (obj.type == 1)>
    	<a href="#" onclick="showPointerPage('${obj.id}');">${obj.name}</a>
    	<#else>
    	${obj.name}
    	</#if>
    </td>
    <td>${obj.orderNum}</td>
  	<td>
  		<#if obj.type == 1>
  		功能
  		<#elseif (obj.type == 0)>
  		<font style="font-weight:bold;">分组</font>
  		<#else>
  		<font style="font-weight:bold;">子系统</font>
  		</#if>
  	</td>
  	<td>${obj.parentName}</td>
  	<td>
  		<#if obj.type == 1>
  		功能点
  		<#else>
  		N/A
  		</#if>
  	</td>
  	<td>${obj.url}&nbsp;</td>
  </tr>
	</#list>
</table> 
<#include "/common/page_view.ftl">