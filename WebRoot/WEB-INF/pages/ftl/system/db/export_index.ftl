<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统数据导出</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript">

//var loadi = layer.load('加载中…'); //需关闭加载层时，执行layer.close(loadi)即可
var loadi;

$(function(){
	
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	var params = {"id":""};
	doReloadPage(offset, "${ctx}/supervisor/db/export/reload.do", params);
}

function clickScope(obj){
	var scope = obj.value;
	if(scope == "1"){
		$("#tableContainer").show();
	} else if(scope == "0"){
		$("#tableContainer").hide();
	}
	ajustDms();
}

function saveExport(){
	if(!window.confirm("确定要创建备份文件么？")){
		return;
	}
	loadi = layer.load('数据库备份SQL文件正在创建…');
	$('#form').ajaxSubmit({
		//dataType: 'text',
      //target: '#content',
      success: function(data){
    	  layer.close(loadi);
      	if(data == "success"){
      		// 刷新列表
      		reload(1);
      		//alert("数据备份创建成功!");
      	} else {
      		alert(data);
      	}
      },
      error:function(data){
      	try{
       	console.log("dsfsdf " + data);
       } catch(e){
       	alert(e.message + ", " + e.name);
       }
      }
  });
  return false; // <-- important!
}
</script>
</head>
<body>

<form action="${ctx}/supervisor/db/save_export.do" method="post" id="form" target="_self">
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td colspan="2">创建数据备份</td>
	</tr>
	<tr>
		<td width="120px" class="showTitle">导出时间：</td>
		<td>${time}</td>
	</tr>
	<tr>
		<td class="showTitle">导出范围：</td>
		<td>
			<input type="radio" name="scope" value="0" onclick="clickScope(this);" checked="checked"/>全部表&nbsp;
			<input type="radio" name="scope" value="1" onclick="clickScope(this);"/>选择表&nbsp;
		</td>
	</tr>
	<tr id="tableContainer" style="display:none;">
		<td colspan="2">
			<table border="0" cellpadding="0" cellspacing="0">
				<#assign row_inx=0>
				
				<#list tables?keys as k>
					<#assign _r = row_inx % 2>
					<#if (_r == 0)>
					<tr>
						<td width="50%">
						<input type="checkbox" name="tables" value="${k}"/>
						${k}: ${tables[k]}
						</td>
					<#else>
						<td>
						<input type="checkbox" name="tables" value="${k}"/>
						${k}: ${tables[k]}
						</td>
					</tr>
					</#if>
					<#assign row_inx = row_inx + 1>
				</#list>
			</table>
		  共 ${row_inx} 个表
		</td>
	</tr>
	<tr>
		<td class="showTitle">文件是否压缩：</td>
		<td>
			<input type="radio" name="zip" value="0" disabled="disabled" checked="checked"/>不压缩&nbsp;
			<input type="radio" name="zip" value="1" disabled="disabled"/>GZIP压缩&nbsp;
		</td>
	</tr>
	<tr>
		<td class="showTitle">导出备注：</td>
		<td height="50px">
			<textarea rows="3" cols="80" name="summary"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="46px">
		  <input type="button" value=" 创建SQL文件备份 " onclick="saveExport();" class="button-tj"/>
		</td>
	</tr>
</table>
</form>
<div id="pageInfo">
	<#include "/system/db/export_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>