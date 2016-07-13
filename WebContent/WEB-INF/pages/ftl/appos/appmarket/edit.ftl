<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
    
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
    <script type="text/javascript">
		
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
	if($("#market").val() == ""){		
		alert("请输入market！");
		return false;
	}
	if($("#app.appid").val() == ""){		
		alert("请选择应用！");
		return false;
	}
	if($("#strategyGroup.group_id").val() == ""){		
		alert("请选择策略组！");
		$("#strategyGroup.group_id").focus();
		return false;
	}
	return true;
}
		//-->
		</script>
  </head>
  
<body>
	<form action="${ctx}/appos/appmarket/updateAppmarket.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	
  	<tr>
  		<td width="160" class="showTitle">id：</td>
  		<td><input type="text" id="id" name="id" value="${app.id}" class="text" readonly="readonly"/></td>
  	</tr>
  	<tr>
  		<td width="160" class="showTitle">appid：</td>
  		<!-- <td><input type="text" id="appid" name="appid" class="text" value="${appid}" style="width:200px;"/></td> -->
  		<td> <select id="app.appid" name="app.appid" value="${app.app.appid}">
			<option value="0">请选择</option>
			<#list userapps as userapp>
			<option value="${userapp.appid}" <#if (userapp.appid == app.app.appid)>selected="selected"</#if>>
			${userapp.appname}
			</option>
			</#list>
		 </select> </td>
  	</tr>
  	<tr>
  		<td class="showTitle">market：</td>
  		<td><input type="text" id="market" name="market" value="${app.market}" class="text"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">策略组名称：</td>
  		<!-- <td><input type="text" id="strategyGroup.strategy_groupid" name="strategyGroup.strategy_groupid" class="text"/></td> -->
  		<td> <select id="strategyGroup.group_id" name="strategyGroup.group_id" value="${app.strategyGroup.group_id}">
			<option value="0">请选择</option>
			<#list strategygroups as group>
			<option value="${group.group_id}" <#if (group.group_id == app.strategyGroup.group_id)>selected="selected"</#if>>
			${group.group_name}
			</option>
			</#list>
		 </select> </td>
  	</tr>
  	<tr>
  		<td class="showTitle">序号：</td>
  		<td><input type="text" id="seq" name="seq" value="${app.seq}" class="text"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">备注：</td>
  		<td><input type="text" id="remark" name="remark" value="${app.remark}" class="text"/></td>
  	</tr>
  </table>
  <div style="width:99%;" align="center">
	 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
	 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
	</div>
  </form>
</body>
</html>
