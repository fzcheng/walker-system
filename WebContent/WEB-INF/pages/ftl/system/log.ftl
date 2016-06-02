<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统日志检查</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/lhgcalendar.min.js"></script>
<script type="text/javascript">

$(function(){
	$("#start").calendar();
	$("#end").calendar();
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//拼json参数
	var startVal = $.trim($("#start").val());
	var endVal   = $.trim($("#end").val());
	var logType = $.trim($("#logType").val());
	var loginId = $.trim($("#loginId").val());
	var params = {start:startVal, end:endVal, logType:logType, loginId:loginId};
	doReloadPage(offset, "${ctx}/permit/admin/log/reload.do", params);
}

function doSearch(url){
	var startVal = $.trim($("#start").val());
	var endVal   = $.trim($("#end").val());
	if(startVal == ""){
		alert("请输入'开始日期'，格式如：2013-11-21");
		return;
	}
	if(endVal == ""){
		alert("请输入'结束日期'，格式如：2013-11-21");
		return;
	}
	var logType = $.trim($("#logType").val());
	var loginId = $.trim($("#loginId").val());
	var params = {start:startVal, end:endVal, logType:logType, loginId:loginId};
	doReloadPage(1, url, params);
	
}

function search(){
	doSearch("${ctx}/permit/admin/log/reload.do");
}

function flush(){
	doSearch("${ctx}/permit/admin/log/flush.do");
}


</script>
</head>
<body>

<table border="0" cellspacing="0" cellpadding="0" class="table-box">
  <tr class="table-tit">
    <td>系统日志检查&nbsp;
      <span class="on" onclick="openSearch(this, 'searchForm')">- 收起</span>
    </td>
  </tr>
  <tr id="searchForm">
    <td><table width="100%" border="0" cellspacing="5" cellpadding="0">
        <tr>
          <td align="right" width="80">时间范围：</td>
          <td width="500">
            <input type="text" id="start" name="start" maxlength="10" value="${firstDayOfMonth}" style="width:80px;" class="text" readonly="readonly"/>&nbsp;到&nbsp;
					  <input type="text" id="end" name="end" maxlength="10" value="${today}" style="width:80px;" class="text" readonly="readonly"/>&nbsp;
					  日志类型：
					 <select id="logType" name="logType">
							<option value="" selected="selected">全部</option>
							<option value="0">一般操作</option>
							<option value="1">登录</option>
							<option value="2">注销</option>
							<option value="3">删除</option>
							<option value="4">创建</option>
							<option value="5">编辑</option>
						</select>
						记录用户：
						<input type="text" id="loginId" name="loginId" maxlength="30" style="width:90px;" class="text"/>
          </td>
          <td width="170">
            <input type="button" value=" 查 询 " onclick="search();" class="button"/>
            <input type="button" value=" 强制刷新 " onclick="flush();" class="button" title="使缓存队列中还没有触发写入的日志，立即持久化。"/>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
<div id="pageInfo">
<#include "/system/log_list.ftl" encoding="UTF-8">
</div>
</body>
<#include "/common/footer.ftl">
</html>