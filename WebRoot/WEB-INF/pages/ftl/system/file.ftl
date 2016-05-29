<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传文件监控管理</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/lhgcalendar.min.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
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
	var storeType = $.trim($("#storeType").val());
	var filename = $.trim($("#filename").val());
	var ext = $.trim($("#ext").val());
	var params = {start:startVal, end:endVal, storeType:storeType, filename:filename, ext:ext};
	doReloadPage(offset, "${ctx}/permit/admin/file/reload.do", params);
}

function doSearch(url, pageNum){
	var startVal = $.trim($("#start").val());
	var endVal   = $.trim($("#end").val());
	
	/**
	if(startVal == ""){
		alert("请输入'开始日期'，格式如：2013-11-21");
		return;
	}
	if(endVal == ""){
		alert("请输入'结束日期'，格式如：2013-11-21");
		return;
	}
	*/
	
	var storeType = $.trim($("#storeType").val());
	var filename = $.trim($("#filename").val());
	var ext = $.trim($("#ext").val());
	var params = {start:startVal, end:endVal, storeType:storeType, filename:filename, ext:ext};
	doReloadPage(pageNum, url, params);
}

function search(){
	doSearch("${ctx}/permit/admin/file/reload.do", 1);
}

function removeFile(id, pageNum){
	if(confirm("确定要删除已上传的文件么？")){
		doSearch("${ctx}/permit/admin/file/remove.do?id=" + id, pageNum);
	}
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>系统上传文件管理</span>&nbsp;|&nbsp;
			时间范围：
			<input type="text" id="start" name="start" maxlength="10" value="" style="width:80px;" class="text" readonly="readonly"/>&nbsp;到&nbsp;
			<input type="text" id="end" name="end" maxlength="10" value="" style="width:80px;" class="text" readonly="readonly"/>
			存储类型：
			<select id="storeType" name="storeType">
				<option value="" selected="selected">全部</option>
				<option value="0">磁盘文件</option>
				<option value="1">数据库二进制</option>
			</select>
			文件名称：
			<input type="text" id="filename" name="filename" maxlength="30" style="width:90px;" class="text"/>
			后缀名：
			<input type="text" id="ext" name="ext" maxlength="30" style="width:40px;" class="text"/>
	  	<input type="button" value=" 查 询 " onclick="search();" class="button"/>
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/system/file_list.ftl" encoding="UTF-8">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>