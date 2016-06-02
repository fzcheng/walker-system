<table border="0" cellspacing="0" cellpadding="0"  class="table-box">
  <tr class="table-tit">
    <td width="6%">
    	<input type="checkbox" onclick="selectAll(this)" class="check_box"/>
    </td>
    <td width="16%">创建时间</td>
    <td width="8%">记录用户</td>
    <td width="8%">日志类型</td>
    <td width="62%">内容</td>
  </tr>
	  
	 	<#list pagerView.datas as log>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="1" class="check_box" />
	    </td>
	    <td>${log.showTime}</td>
	    <td>${log.loginUser}</td>
	    <td>${log.typeName}</td>
	  	<td>${log.content}</td>
	  </tr>
		</#list>
		<!-- 
		<tr>
	    <td colspan="5" class="page_td">
			</td>
		</tr>
		 -->
	</table>
	<#include "/common/page_view.ftl">