<#macro paging pageCount currentPage totalRows pageSize jsMethod>
<#assign ipageCount = pageCount?number>
<#assign icurrentPage = currentPage?number>
<#assign itotalRows = totalRows?number>
<#assign ipageSize = pageSize?number>

<!-- 每页显示多少个分页数字，默认10个 -->
<#assign idigitsize = 10>
 
<#if (itotalRows>0)>

<!-- 定义变量：最后翻页的页码最大值 -->
<#assign maxPageVal = ((ipageCount/idigitsize)?int) * idigitsize>
<#if (ipageCount <= idigitsize)>
	<#assign _count = ipageCount>
<#elseif (icurrentPage > maxPageVal)>
	<#assign _count = ipageCount % idigitsize>
<#else>
	<#assign _count = idigitsize>
</#if>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="26px" valign="middle" align="left">
<ul id="pagination-digg">
	<#if (icurrentPage <= idigitsize)>
  <li class="previous-off">&laquo; 上一页</li>
  <#else>
  <li><a href="javascript:${jsMethod}(${icurrentPage-(icurrentPage % idigitsize)})">&laquo; 上一页</a></li>
  </#if>
  
  <!-- 判断是否要转到下一轮分页 -->
  <#if (icurrentPage <= idigitsize)>
  	<#assign currentDraw = 1>
  <#else>
  	<#assign _left = icurrentPage % idigitsize>
  	<#if (_left == 0)>
  		<#assign currentDraw = icurrentPage>
  	<#else>
  		<#assign currentDraw = (icurrentPage - _left + 1)>
  	</#if>
  </#if>

  <#list currentDraw..(currentDraw+_count-1) as _c>
  	<#if (_c == icurrentPage)>
  		<li><a class="active" href="javascript:${jsMethod}(${icurrentPage})">${_c}</a></li>
  	<#else>
	  	<li><a href="javascript:${jsMethod}(${_c})">${_c}</a></li>
  	</#if>
  </#list>
  
  <#if (icurrentPage <= maxPageVal)>
  	<li class="next"><a href="javascript:${jsMethod}(${_count} + 1)">下一页 &raquo;</a></li>
  <#else>
  	<li class="next-off">下一页 &raquo;</li>
  </#if>
  <li style="margin-right:3px;">
		<input type='text' name='offset' id='offset' size='2' value='${icurrentPage}' class="textbox_page"/>
  </li>
  <li>
		<a href="####" onclick="${jsMethod}(0)">GO</a>
  </li>
  <li>
		<input type="hidden" id="totalpage" name="totalpage" value="${ipageCount}"/>
  </li>
</ul>
</td>
<td valign="middle" width="200px" class="page_bar_desc">
|&nbsp;每页显示<font class="page_bar_key">${ipageSize}</font>&nbsp;|&nbsp;共<font class="page_bar_key">${ipageCount}</font>页&nbsp;|&nbsp;<font class="page_bar_key">${itotalRows}</font>记录
</td>
<td width="*">&nbsp;</td>
</tr>
</table>

<#else>
<div style="font-size: 13px;font-weight:500;line-height: 150px;text-align: center;">[暂无数据]</div>
</#if>
</div>

</#macro>