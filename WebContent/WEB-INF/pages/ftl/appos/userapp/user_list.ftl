<table border="0" cellspacing="0" cellpadding="0" class="table-box">
  <tr class="table-tit">
    <td width="50px">
    	<input type="checkbox" onclick="selectAll(this)" class="check_box"/>
    </td>
    <td width="50px">序号</td>
    <td width="150px">用户名称</td>
    <td width="60px">描述</td>
    <td width="50px">应用</td>
  </tr>
	  
	 	<#list pagerView.datas as user>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${user.id}" class="check_box" />
	    </td>
	    <td>${user_index}</td>
	    <td>${user.name}</td>
	    <td><a href="#" title="${user.name}">描述</a></td>
	  	<td>
	      <img src="${ctx}/images/public/user_g.gif" alt="设置应用" style="cursor:pointer;" 
	        onclick="showApps('${user.id}')"/>
	  	</td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">