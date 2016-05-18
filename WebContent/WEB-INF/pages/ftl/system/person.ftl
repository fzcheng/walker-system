<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人设置</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/sha1.js"></script>
<script type="text/javascript">

var selectedMenu = null;

function selectMenu(menuId){
	if(selectedMenu != null){
		// 如果不是第一次选择菜单
		$("#"+selectedMenu).css("color","#0F7B99");
		$("#"+selectedMenu).css("background-color","");
	}
	$("#"+menuId).css("color","#FFFFFF");
	$("#"+menuId).css("background-color","#0F7B99");
	selectedMenu = menuId;
}

function showEditPass(menuId){
	selectMenu(menuId);
	//$("#content").load("${ctx}/permit/person/editPass.do", null, null);
	loadContent("content", "${ctx}/permit/person/editPass.do", null, function(){ajustDms();});	
}
function showInfo(menuId){
	selectMenu(menuId);
	loadContent("content", "${ctx}/permit/person/show_info.do", null, function(){ajustDms();});
}
function showAbout(menuId){
	selectMenu(menuId);
	loadContent("content", "${ctx}/permit/about.do", null, function(){ajustDms();});
}

function search(){
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
	doReloadPage(1, "${ctx}/permit/admin/log/reload.do", params);
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>个人设置</span>&nbsp;|&nbsp;
			<a id="userInfo" href="javascript:;" onclick="showInfo('userInfo');">用户信息</a>
			<a id="editPass" href="javascript:;" onclick="showEditPass('editPass');">修改密码</a>
			&nbsp;|&nbsp;
			<a id="about" href="javascript:;" onclick="showAbout('about');">关于</a>
		</td>
	</tr>
</table>
<div id="content">
	<#include "/system/person_info.ftl">
</div>
</body>
<#include "/common/footer.ftl">
</html>