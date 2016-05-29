<#assign pageNum = pagerView.currentPage>
<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="70px">标识</td>
	    <td width="140px">创建时间</td>
	    <td width="170px">流程名称</td>
	    <td width="60px">当前版本</td>
	    <td width="80px">发布状态</td>
	    <td width="70px">子流程</td>
	    <td>备注</td>
	    <td width="60px">废弃</td>
	  </tr>
	  
	 	<#list pagerView.datas as process>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${process.processDefine.id}" class="check_box" />
	    </td>
	    <td>${process.identifier}</td>
	    <td>${process.createTime?number_to_date}</td>
	    <td>
	    	<#if (process.processDefine.deleteStatus == 1)>
	    		<font style="font-color:#F2F2F2;">${process.processDefine.name}</font>
	    	<#else>
		      <a href="####" onclick="" target="_blank">${process.processDefine.name}</a>
	    	</#if>
	    </td>
	    <td>${process.processDefine.version}</td>
	    <td>
	    	<#if (process.processDefine.publishStatus == 1)>
	    	已发布
	    	<#else>
	    	<font style="font-weight:bold;">未发布</font>
	    	</#if>
	    </td>
	  	<td>
	  		<#if (process.processDefine.subProcess == 1)>
	  		是
	  		<#else>
	  		否
	  		</#if>
	  	</td>
	  	<td>${process.processDefine.summary}</td>
	  	<td>
	  		<#if (process.processDefine.deleteStatus == 1)>
	  		已废弃
	  		<#else>
	  		在用
	  		</#if>
	  	</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">