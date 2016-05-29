<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>logo上传</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript">


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

function change() {
  var pic = document.getElementById("preview");
  var file = document.getElementById("f");
  var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
  // gif在IE浏览器暂时无法显示
  if(ext!='png'){
      alert("文件必须为图片png格式!"); return;
  }
  // IE浏览器
  if (document.all) {

      file.select();
      var reallocalpath = document.selection.createRange().text;
      var ie6 = /msie 6/i.test(navigator.userAgent);
      // IE6浏览器设置img的src为本地路径可以直接显示图片
      if (ie6) pic.src = reallocalpath; 
      else { 
          // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
          pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
          // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
          pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
      }
  }else{
      html5Reader(file);
  }
  //调整外部iframe高度
  ajustDms();
}

function html5Reader(file){ 
  var file = file.files[0]; 
  var reader = new FileReader(); 
  reader.readAsDataURL(file); 
  reader.onload = function(e){ 
      var pic = document.getElementById("preview");
      pic.src=this.result;
  } 
}

function beforeSubmit(){
	var file = document.getElementById("f");
	if(isEmptyValue(file.value)){
		alert("请上传文件，先。");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0"  class="table-form">
  <tr class="title">
    <td valign="middle" colspan="2">系统LOGO管理&nbsp;
      <#list groupList as group>
	  	  <input type="button" value="${group.name}" onclick="search('${group.id}');" class="button-tj"/>
	  	</#list>
      <span class="on" onclick="openSearch(this, 'searchForm')">+ 展开</span>
    </td>
  </tr>
  <tr>
  	<td class="showTitle" width="120px">当前LOGO：</td>
  	<td style="height:46px;"><img alt="已存在的LOGO" src="${ctx}/permit/admin/home/logofile.do" height="40px"></td>
  </tr>
  <tr>
  	<td class="showTitle">上传新LOGO：</td>
  	<td>
  		<form action="${ctx}/permit/admin/home/logoupload.do" 
  			enctype="multipart/form-data" name="myform1" method="post" 
  			onsubmit="return beforeSubmit();">
  		  <input id="f" type="file" name="f" onchange="change()"/>
  		  <input type="submit" value="上传文件" class="button-tj"/>
  		</form>
  	</td>
  </tr>
  <tr>
  	<td class="showTitle">预览新LOGO：</td>
  	<td><img id="preview" alt="" name="pic" height="40px"/></td>
  </tr>
  <tr>
  	<td class="showTitle">备注：</td>
  	<td>系统LOGO高度为40px；宽度不限，最好不要超过500px。</td>
  </tr>
</table>
 
</body>
<#include "/common/footer.ftl">
</html>