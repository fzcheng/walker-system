<table cellspacing="0" cellpadding="0" class="table-box">
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="180px">策略id</td>
	    <td width="180px">策略名</td>
	    <td width="180px">条件1</td>
	    <td width="180px">条件2</td>
	    <td width="180px">条件3</td>
	    <td width="180px">使用sdk</td>
	    <td width="180px">备注</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.id}" class="check_box" />
	    </td>
	    <td>${app.id}</td>
	    <td>${app.strategy_name}</td>

	    <td>
		    <#if (app.type1 == 0)>
			  	无
		  	<#elseif (app.type1 == 1)>
			  	时间段条件:
			  	<#list app.timeDetail as t>  
					<li>${t}</li>
				</#list>
			  	
		  	<#elseif (app.type1 == 2)>
			  	区域条件:
			  	<#list app.provinceDetail as t>  
					<li>${t}</li>
				</#list>
			<#elseif (app.type1 == 3)>
			  	比例条件:
		  	</#if>
		</td>
		
		<td>
		    <#if (app.type2 == 0)>
			  	无
		  	<#elseif (app.type2 == 1)>
			  	时间段条件
		  	<#elseif (app.type2 == 2)>
			  	区域条件
			<#elseif (app.type2 == 3)>
			  	比例条件
		  	</#if>
		</td>
		
		<td>
		    <#if (app.type3 == 0)>
			  	无
		  	<#elseif (app.type3 == 1)>
			  	时间段条件
		  	<#elseif (app.type3 == 2)>
			  	区域条件
			<#elseif (app.type3 == 3)>
			  	比例条件
		  	</#if>
		</td>
		<td>
			<#if (app.sdkid == 0)>
			  	无
		  	<#elseif (app.sdkid == 1)>
			  	yeesdk
		  	<#elseif (app.sdkid == 2)>
			  	内置的其他sdk
		  	</#if>
		</td>
	    <td>${app.remark}</td>
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">