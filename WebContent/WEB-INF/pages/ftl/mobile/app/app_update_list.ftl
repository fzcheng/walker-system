<table cellspacing="0" cellpadding="0" class="table-box" id="tbl">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <!-- 
	    <td width="60px">序号</td>
	     -->
	    <td width="140px">创建</td>
	    <td width="220px">应用名称</td>
	    <td width="80px">升级版本</td>
	    <td width="70px">强制升级</td>
	    <td width="70px">升级文件</td>
	    <td width="*">升级描述</td>
	  </tr>
	  
	 	<#list pagerView.datas as q>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${q.id}" class="check_box" />
	    </td>
	    <td>${q.createTime?number_to_datetime}</td>
	    <td>${q.appName}</td>
	    <td>${q.versionCode}</td>
	    <td>
	    	<#if (q.updateForce == 1)>
	    	强制
	    	<#else>
	    	--
	    	</#if>
	    </td>
	    <td>
			<a href="${ctx}/permit/admin/file/down.do?id=${q.fileId}" target="_blank">下载</a>
	    </td>
	    <td>${q.summary}</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">