<table border="0" cellspacing="0" cellpadding="0"  class="table-box">
  <tr class="table-tit">
    <td width="50px">
    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
    </td>
    <td width="15%">ID</td>
    <td width="25%">按钮名称</td>
    <td width="10%">排序</td>
    <td width="35%">URL</td>
    <td width="15%">上级功能</td>
  </tr>
  
 <#list pointers as obj>
 	<tr class="table-date">
    <td>
    	<input name="ids" type="checkbox" value="${obj.id}" class="check_box" />
    </td>
    <td>${obj.id}</td>
    <td>${obj.name}</td>
    <td>${obj.orderNum}</td>
  	<td>${obj.url}</td>
  	<td>${obj.functionId}</td>
  </tr>
	</#list>
</table> 
