<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="150px">创建时间</td>
	    <td width="80px">授权识别号</td>
	    <td width="100px">单位</td>
	    <td width="150px">项目</td>
	    <td width="80px">授权类型</td>
	    <td>JAR</td>
	    <td width="70px">授权文件</td>
	    <td width="60px">授权库</td>
	  </tr>
	  
	 	<#list pagerView.datas as accredit>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="1" class="check_box" />
	    </td>
	    <td>${accredit.createTime?number_to_datetime}</td>
	    <td>${accredit.authId}</td>
	    <td>${accredit.orgName}</td>
	    <td>${accredit.projectName}</td>
	  	<td>${accredit.authType}
	  		<#if (accredit.authType == 0)>
	  		MAC绑定
	  		<#elseif (accredit.authType == 1)>
	  		时间限制
	  		<#else>
	  		全部
	  		</#if>
	  	</td>
	  	<td>${accredit.jarName}</td>
	  	<td>
	      <a href="${ctx}/permit/admin/file/down.do?id=${accredit.fileId}" target="_blank">下载</a>
	  	</td>
	  	<td>
	      <a href="" target="_blank">生成</a>
	  	</td>
	  </tr>
		</#list>

	</table>
	<#include "/common/page_view.ftl">