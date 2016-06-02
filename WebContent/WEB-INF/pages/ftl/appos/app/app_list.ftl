<table cellspacing="0" cellpadding="0" class="table-box" style="table-layout:fixed;" >
	  <tr class="table-tit">
	    <td width="50px">
	    	<input type="checkbox"  onclick="checkAllOrNone(this)" class="check_box"/>
	    </td>
	    <td width="120px" rows＝"3">appid</td>
	    <td width="120px" rows＝"3">appcode</td>
	    <td width="50px" rows＝"3">应用名</td>
	    <td width="120px" rows＝"3">包名</td>
	    <td width="120px" rows＝"3">微信appid</td>
	    <td width="120px" rows＝"3">微信paternerKey</td>
	    <td width="80px" rows＝"3">微信商户号</td>
	    <td width="200px" style="word-wrap:break-word;">通知地址</td>
	    <td width="70px" rows＝"3">创建时间</td>
	    <td width="70px" rows＝"3">修改时间</td>
	  </tr>
	  
	 	<#list pagerView.datas as app>
	 	<tr class="table-date">
	    <td>
	    	<input name="ids" type="checkbox" value="${app.id}" class="check_box" />
	    </td>
	    <td>${app.appid}</td>
	    <td>${app.appcode}</td>
	    <td>${app.appname}</td>
	    <td>${app.package_name}</td>
	    <td>${app.wx_appid}</td>
	    <td>${app.wx_parternerKey}</td>
	    <td>${app.wx_mch_id}</td>
	    <td>${app.notify_url}</td>
	    <td>${app.create_time}</td>
	    <td>${app.last_time}</td>
	    
	  </tr>
		</#list>
	</table>
	<#include "/common/page_view.ftl">