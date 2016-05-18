<table  cellspacing="0" cellpadding="0" class="table-box">
  <tr class="table-tit">
    <td width="50px">
    	<input type="checkbox" id="fetchAll" name="fetchAll" onclick="selectAll(this)" class="check_box"/>
    </td>
    <td width="60px">序号</td>
    <td width="130px">登录ID</td>
    <td width="120px">名称</td>
    <td width="80px">类别</td>
    <td width="150px">单位</td>
    <td>启用</td>
  </tr>
  
 <#list pagerView.datas as obj>
  <tr class="table-date">
    <td>
    	<input name="ids" type="checkbox" value="${obj.id}" class="check_box" />
    </td>
    <td>${obj.orderNum}</td>
    <td>${obj.loginId}</td>
    <td>${obj.name}</td>
  	<td>
  		<#if (obj.type == 0)>
  			超级管理员
  		<#elseif (obj.type == 1)>
  			<font style="font-weight:bold; color:black;">单位管理员</font>
  		<#else>
  			普通用户
  		</#if>
  	</td>
  	<td>${obj.orgName}</td>
  	<td>
  		<#if (obj.enable == 1)>
  		正常
  		<#else>
  		<font style="color:red;">停用</font>
  		</#if>
  	</td>
  </tr>
	</#list>
</table> 
<#include "/common/page_view.ftl">