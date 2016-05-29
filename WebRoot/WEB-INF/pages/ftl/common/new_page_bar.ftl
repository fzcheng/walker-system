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

<#elseif (icurrentPage >= maxPageVal)>
	<#assign _count = ipageCount % idigitsize>

<#else>

	<#assign _count = idigitsize>
</#if>
<#if (icurrentPage > 0 && icurrentPage <= ipageCount)>
	<#assign _nextPage = icurrentPage + 1>
	<#assign _prePage = icurrentPage - 1>
<#else>
	<#assign _nextPage = icurrentPage>
	<#assign _prePage = icurrentPage>
</#if>

<div class="fenye">
  <#if (icurrentPage <= idigitsize)>
    <span class="disabled">上一页</span>
  <#else>
    <a href="javascript:${jsMethod}(${_prePage?c})">上一页</a>
  </#if>
  
  
 	<#assign _left = icurrentPage % idigitsize>
 	<#if (_left == 0)>
 		<#assign currentDraw = icurrentPage - idigitsize + 1>
 		<#assign endDraw = icurrentPage>
 	<#else>
 		<#assign currentDraw = (icurrentPage - _left + 1)>
 		<#assign endDraw = currentDraw + _count - 1>
 	</#if>
  
  <#list currentDraw..endDraw as _c>
  	<#if (_c == icurrentPage)>
  	  <span class="current">${_c}</span>
  	<#else>
	    <a href="javascript:${jsMethod}(${_c?c})">${_c}</a>
  	</#if>
  </#list>
  
  <#if (icurrentPage <= maxPageVal)>
  	<a href="javascript:${jsMethod}(${_nextPage?c})">下一页 &raquo;</a>
  <#else>
  	<span class="disabled">下一页</span>
  </#if>
  <span class="disabled">共${ipageCount}页 | ${itotalRows}条记录 </span>
  <input type="hidden" id="totalpage" name="totalpage" value="${ipageCount?c}"/>
  <input class="text" name="offset" id="offset" value="${icurrentPage}" type="text" style="width:30px;margin-right: 5px;"/>
  <a href="#" onclick="${jsMethod}(0)">转</a>
  
</div>

<#else>
  <div style="font-size: 13px;font-weight:200;line-height: 100px;text-align: center;">【暂无数据】</div>
</#if>

</#macro>