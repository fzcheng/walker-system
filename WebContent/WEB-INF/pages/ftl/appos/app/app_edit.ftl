<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
    
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
    <script type="text/javascript">
		<!--
		
var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function(){
	$("#codeName").focus();
});

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

function save(){
	if(!f_check()){
		return false;
	}
	$('#form').ajaxSubmit({
		//dataType: 'text',
		//target: '#content',
		success: function(data){
			if(data == "success"){
		    	parent.frames.sampleframe.reload(1);
		    	closeWindow();
			} else {
				showErrorTip(data);
			}
		},
		error:function(data){
			try{
		    	console.log("error:" + data);
		    } catch(e){
		    	alert(e.message + ", " + e.name);
		    }
		}
	});
	return false; // <-- important!
}
		
function f_check(){
	if($("#appcode").val() == ""){		
		alert("错误：appcode为空！");
		return false;
	}
	if($("#appid").val() == ""){		
		alert("错误：appid为空！");
		return false;
	}
	if($("#appname").val() == ""){		
		alert("请输入应用名称！");
		$("#appname").focus();
		return false;
	}
	if($("#package_name").val() == ""){		
		alert("请输入应用包名！");
		$("#package_name").focus();
		return false;
	}
	return true;
}
		//-->
		</script>
  </head>
  
<body>
	<form action="${ctx}/appos/app/updateApp.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<td>
	    <input name="id" type="text" value="${id}" class="text" readonly="readonly"/>
    </td>
  	<tr>
  		<td width="160" class="showTitle">appid：</td>
  		<td><input type="text" id="appid" name="appid" class="text" value="${appid}" style="width:200px;" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">appcode：</td>
  		<td><input type="text" id="appcode" name="appcode" class="text" readonly="readonly"
  			value="${appcode}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">应用名称：</td>
  		<td><input type="text" id="appname" name="appname" class="text" value="${appname}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">应用包名：</td>
  		<td><input type="text" id="package_name" name="package_name" class="text" value="${package_name}"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">所属公司id：</td>
  		<td><input type="text" id="companyid" name="companyid" class="text" value="${companyid}"/></td>
  	</tr>
  </table>
  <div style="width:99%;" align="center">
	 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
	 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
	</div>
  </form>
</body>
</html>
