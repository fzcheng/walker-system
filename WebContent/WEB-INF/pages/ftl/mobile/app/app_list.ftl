<table cellspacing="0" cellpadding="0" class="table-box" id="tbl">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <!-- 
	    <td width="60px">序号</td>
	     -->
	    <td width="140px">创建</td>
	    <td width="150px">应用ID</td>
	    <td width="220px">应用名称</td>
	    <td width="80px">发布版本</td>
	    <td width="70px">发布文件</td>
	    <td width="*">备注</td>
	  </tr>
	  
	 	<#list pagerView.datas as q>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${q.id}" class="check_box" />
	    </td>
	    <td>${q.createTime?number_to_datetime}</td>
	    <td>${q.id}</td>
	    <td>${q.name}</td>
	    <td>${q.releaseVersion}</td>
	    <td>
			<a href="${ctx}/permit/admin/file/down.do?id=${q.releaseFileId}" target="_blank">下载</a>
	    </td>
	    <td>${q.summary}</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">