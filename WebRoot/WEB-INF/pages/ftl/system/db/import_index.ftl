<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统数据导入</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/powerFloat.css" type="text/css" rel="stylesheet"/>
<!-- 
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
 -->
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-powerFloat-min.js"></script>

<!-- 上传组件 -->
<script type="text/javascript" src="${ctx}/script/lib/jquery.MetaData.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.MultiFile.pack.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.MultiFile.js"></script>
	
<script type="text/javascript">

//var loadi = layer.load('加载中…'); //需关闭加载层时，执行layer.close(loadi)即可
var loadi;

$(function(){
	$('#sqlFile').MultiFile({ 
   max:1,
   accept: 'SQL|txt',
   STRING: {
    remove: 'Remover',
    selected:'已选择文件: $file',
    denied:'无效的文件类型: $ext!'
   }
  });
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	var params = {"id":""};
	doReloadPage(offset, "${ctx}/supervisor/db/import/reload.do", params);
}

function saveImport(){
	if(!window.confirm("确定要导入数据么？")){
		return;
	}
	var file = $("#sqlFile").val();
	if(isEmptyValue(file)){
		alert("请选择导入的文件");
		return;
	}
	//loadi = layer.load('正在导入数据…');
	
	//创建半透明遮罩层
  if (!$("#overLay").size()) {
      $('<div id="overLay"></div>').prependTo($("body"));
      $("#overLay").css({
          width: "100%",
          backgroundColor: "#000",
          opacity: 0.2,
          position: "absolute",
          left: 0,
          top: 0,
          zIndex: 99
      }).height($(document).height());
  }
 $("#targetFixed").powerFloat({
     eventType: null,
     targetMode: "doing",	
     target: "正在导入数据...",
     position: "1-2"
 });
	 
	$('#form').ajaxSubmit({
		//dataType: 'text',
      //target: '#content',
      success: function(data){
    	  //layer.close(loadi);
    	  // 使用这个等待窗口，国产的一个简单的popup插件，功能还比较多，就是封装的不好。
    	  $("#overLay").remove();
        $.powerFloat.hide();
        str = htmlToString(data);
        console.log(str);
      	if(str != "" && str.indexOf("success") >= 0){
      		// 刷新列表
      		reload(1);
      		//alert("数据备份创建成功!");
      		// 弹出成功处理窗口
      		importSuccess();
      	} else {
      		alert(data);
      	}
      },
      error:function(data){
    	  //layer.close(loadi);
    	  $("#overLay").remove();
        $.powerFloat.hide();
      	try{
       		alert("应用程序遇到了异常错误：" + data);
       } catch(e){
       	alert(e.message + ", " + e.name);
       }
      }
  });
  return false; // <-- important!
}

function importSuccess(){
	popDefaultDialog("数据导入完成", "${ctx}/supervisor/db/import/success.do");
}

</script>
</head>
<body>
<!--  进度框容器 -->
<span id="targetFixed" class="target_fixed"></span>

<form action="${ctx}/supervisor/db/save_import.do" method="post" id="form" target="_self" enctype="multipart/form-data">
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td colspan="2">导入新的备份数据</td>
	</tr>
	<tr>
		<td width="120px" class="showTitle">导入时间：</td>
		<td>${time}</td>
	</tr>
	<tr>
		<td class="showTitle">(<span class="required">*</span>)选择导入文件：</td>
		<td>
			<input type="file" class="multi" maxlength="2" id="sqlFile" name="sqlFile"/>&nbsp;
			当前导入文件仅支持后缀名：sql、txt两种。
		</td>
	</tr>
	
	<tr>
		<td class="showTitle">导入备注：</td>
		<td height="50px">
			<textarea rows="3" cols="80" name="summary"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" height="46px">
		  <input type="button" value=" 提交导入 " onclick="saveImport();" class="button-tj"/>
		</td>
	</tr>
</table>
</form>
<div id="pageInfo">
	<#include "/system/db/import_list.ftl">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>