<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用可变参数配置</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript">
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

function search(groupId){
	var params = {"groupId":groupId};
	$("#content").load("${ctx}/permit/admin/args/load.do", params, null);
}

function submitForm(){
	var sQuery = $("#myform1 *").fieldValue(true);
	if(sQuery == null || sQuery == "" || sQuery == $("#roleId").val()){
		alert("请输入需要修改的配置选项。");
		return;
	}
	alert(sQuery);
	$.ajax({
		async:false,
		url:"${ctx}/admin/args/save.do",
		type:"post",
		data:"datas=" + sQuery,
		success:function(data){
			alert(data);
		},
		error:function(data){
			alert("更新配置选项失败: " + data);
		}
	});
}
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0"  class="table-form">
  <tr class="title">
    <td valign="middle">应用可变参数配置&nbsp;
      <#list groupList as group>
	  	  <input type="button" value="${group.name}" onclick="search('${group.id}');" class="button"/>
	  	</#list>
      <span class="on" onclick="openSearch(this, 'searchForm')">+ 展开</span>
    </td>
  </tr>
</table>
<div class="fen_geRg" id="content">
	<#include "/system/arguments_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>