<#assign pageNum = pagerView.currentPage>
<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox" onclick="selectAll(this)" class="check_box"/>
	    </td>
	    <td width="150px">创建时间</td>
	    <td width="90px">创建人</td>
	    <td width="260px">导出名称</td>
	    <td width="70px">导出类型</td>
	    <td>备注</td>
	    <td width="80px">下载文件</td>
	  </tr>
	  
	 	<#list pagerView.datas as export>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="1" class="check_box" />
	    </td>
	    <td>${export.createTime?number_to_datetime}</td>
	    <td>${export.creatorName}</td>
	    <td>${export.name}</td>
	    <td>
	    	<#if (export.exportType == 0)>
	    	全部表
	    	<#else>
	    	指定表
	    	</#if>
	    </td>
	  	<td>${export.summary}</td>
	  	<td>
	      <a href="${ctx}/permit/admin/file/down.do?id=${export.fileId}" onclick="" target="_blank">下载文件</a>
	  	</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">