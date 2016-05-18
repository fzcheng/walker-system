<#assign pageNum = pagerView.currentPage>
<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="selectAll(this)" class="check_box"/>
	    </td>
	    <td width="150px">创建时间</td>
	    <td width="260px">文件名</td>
	    <td width="70px">后缀名</td>
	    <td width="90px">存储类型</td>
	    <td>存储路径</td>
	    <td width="60px">删除</td>
	  </tr>
	  
	 	<#list pagerView.datas as log>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="1" class="check_box" />
	    </td>
	    <td>${log.createTime?number_to_date}</td>
	    <td>
	      <a href="${ctx}/permit/admin/file/down.do?id=${log.id}" onclick="" target="_blank">${log.filename}</a>
	    </td>
	    <td>${log.fileExt}</td>
	    <td>${log.storeType}</td>
	  	<td>${log.path}</td>
	  	<td>
	  	  <a href="#" onclick="removeFile('${log.id}', ${pageNum})">删除</a>
	  	</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">