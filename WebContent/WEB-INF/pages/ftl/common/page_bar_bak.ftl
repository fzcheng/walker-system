<#macro paging pageCount currentPage totalRows pageSize jsMethod>
<#assign ipageCount = pageCount?number>
<#assign icurrentPage = currentPage?number>
<#assign itotalRows = totalRows?number>
<#assign ipageSize = pageSize?number>

	<div class="pages" style="font-size:13px;">
		<#if (itotalRows>0)>
			<font color="#000000">
				| 共${itotalRows}条 | 每页显示${ipageSize}条 | ${icurrentPage}/${ipageCount}页&nbsp;&nbsp;</font>
				<#if (icurrentPage > 1)>
					<a href="javascript:${jsMethod}(1)" title="首页" class="nav"><span>首页</span></a> 			 
	        <a href="javascript:${jsMethod}(${icurrentPage} - 1)" title="上一页" class="nav"><span class="nav">上一页</span></a>
				<#else>
					<span>首页</span>  
	        <span>上一页</span>
				</#if>
				
				<#if (icurrentPage < ipageCount && ipageCount > 0)>
					<a href="javascript:${jsMethod}(${icurrentPage} + 1)" title="下一页" class="nav"><span>下一页</span></a>  
	        <a href="javascript:${jsMethod}(${ipageCount})" class="nav"><span>尾页</span></a>  
				<#else>
					<span>下一页</span>
	        <span>尾页</span>
				</#if>
			
		  <input type='text' name='offset' id='offset' size='2' value='${icurrentPage}' class="textbox_page"/>
		  <input type='button' value='GO' onclick="${jsMethod}(0)" class="btn_page"/>
		  <input type="hidden" id="totalpage" name="totalpage" value="${ipageCount}"/>
		<#else>
			<div style="font-size: 13px;font-weight:500;line-height: 150px;text-align: center;">[暂无数据]</div>
		</#if>
	</div>
  
</#macro>