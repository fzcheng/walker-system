<table border="0" cellspacing="0" cellpadding="0" class="table-box">
  <tr class="table-tit">
    <td width="50px">
    	<input type="checkbox" onclick="selectAll(this)" class="check_box"/>
    </td>
    <td width="50px">序号</td>
    <td width="150px">角色名称</td>
    <td width="60px">描述</td>
    <td width="50px">功能</td>
    <td width="50px">用户</td>
  </tr>
	  
	 	<#list pagerView.datas as role>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${role.id}" class="check_box" />
	    </td>
	    <td>1</td>
	    <td>${role.name}</td>
	    <td><a href="#" title="${role.summary}">描述</a></td>
	    <td>
	      <img src="${ctx}/images/public/func.gif" alt="设置功能" style="cursor:pointer;" 
	        onclick="showFunction('${role.id}')"/>
	    </td>
	  	<td>
	      <img src="${ctx}/images/public/user_g.gif" alt="设置用户" style="cursor:pointer;" 
	        onclick="showUser('${role.id}')"/>
	  	</td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">