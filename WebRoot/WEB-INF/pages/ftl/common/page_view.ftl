<#include "/common/new_page_bar.ftl">
		 <@paging pageCount="${(pagerView.pageCount)?c}" currentPage="${pagerView.currentPage?c}"
			totalRows="${pagerView.totalRows?c}" pageSize="${pagerView.pageSize?c}" 
			jsMethod="${pagerView.jsMethod}"/>
			
<script>
/* 初始化分页表格，实现选中行变色，并选择复选框 */
initTable();
</script>