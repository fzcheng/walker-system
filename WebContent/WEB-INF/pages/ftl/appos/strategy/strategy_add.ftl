<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
    
    <script type="text/javascript" src="${ctx}/script/public.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
    <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/lib/WdatePicker.js">  </script>
    <script type="text/javascript">
		<!--
		
var index = parent.layer.getFrameIndex(window.name);

$(document).ready(function(){
	$("#appname").focus();
});
		
function save(){
	if(!f_check()){
		return false;
	}

	$('#form').ajaxSubmit({
		//dataType: 'text',
		//target: '#content',
		success: function(data){
			if(data == "success"){
				//alert("数据保存成功");
		    	parent.frames.sampleframe.reload(1);
		    	closeWindow();
			} else {
				showErrorTip(data);
			}
		},
		error:function(data){
			try{
				//alert(1);
		    	console.log("error:" + data);
		    } catch(e){
		    	alert(e.message + ", " + e.name);
		    }
		}
	});
	return false; // <-- important!
}

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}
		//注：每个嵌入页必须定义该方法，供父窗口调用，并且返回true或false来告之父窗口是否关闭
			function Ok(){
				//相关逻辑代码
				alert(parent.frames.sampleframe.mygrid);
				//window.frames.sampleframe.mygrid.reload();
				if(!f_check()){
					return false;
				} else {
				 return postData();
				}
			}
			
			function postData(){
				var result = false;
				$.ajax({
					async:false,
					type:"post",
					url:"${ctx}/appos/app/saveApp.do",
					data:"parentId=" + $("#parentId").val() + "&codeName=" + $("#codeName").val() + "&codeStand=" + $("#codeStand").val(),
					success:function(msg){
						if(msg == "0"){
							alert("应用保存成功！");
							parent.frames.sampleframe.refreshCodeTree();
							result = true;
						} else {
							alert("应用保存出现错误！");
						}
					}
				});
				return result;
			}
			
function f_check(){
	if($("#group_name").val() == ""){		
		alert("错误：策略组名为空！");
		return false;
	}
	return true;
}
		//-->
		
function changeConditionType(id, type)
{
	//alert(id + type);
	var s = ["valueNone","valueTime","valueLocation","valueVip","valuePer"];
	
	for(var i = 0; i < s.length; i ++)
	{
		var para = s[i] + id;
		var ui =document.getElementById(para);
		ui.style.display="none";
	}
	var para = s[type] + id;
	var ui =document.getElementById(para);
	ui.style.display="table-row";
}

</script>
  </head>
  
<body>
	<form action="${ctx}/appos/strategy/addStrategy.do" method="post" id="form" target="_self">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle">策略名：</td>
  		<td><input type="text" id="group_name" name="group_name" class="text"/></td>
  	</tr>
  	<tr>
  		<td class="showTitle">条件1 类型：</td>
  		<td>
				<INPUT type="radio" name="R1" value="0" checked onclick="changeConditionType(1, 0)">无
				<INPUT type="radio" name="R1" value="1" onclick="changeConditionType(1, 1)">时间段条件
				<INPUT type="radio" name="R1" value="2" onclick="changeConditionType(1, 2)">区域条件
				<INPUT type="radio" name="R1" value="3" onclick="changeConditionType(1, 3)">vip等级
				<INPUT type="radio" name="R1" value="4" onclick="changeConditionType(1, 4)">比例
		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" style="display: inline">条件1 数值：</td>
  		<td id="valueNone1" style="display: inline">无</td>
  		<tr id="valueTime1" style="display: none">
  			<td>时间段条件</td>
  			<input type="text"  id="temp" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
  		<tr>
  		<tr id="valueLocation1" style="display: none">
  			<td>区域条件</td>
  			<td>
	  			<#list provinces as p>
		  			<label><input type='checkbox' id='provinceCheckbox1' name='provinceCheckbox1' value='${p[0]}' class='check_box' check='false' />${p[1]}</label>
		  			<#if (p[0] % 6 == 0)>
		  				<br>
		  			</#if>
	  			</#list>
  			</td>
  		</tr>
  		<tr id="valueVip1" style="display: none">
  			<td>vip等级</td>
  			<td>
  				区间1：
				<INPUT type="text" name="vip1_b1" value="0">－
				<INPUT type="text" name="vip1_e1" value="0">
				<br>
				区间2：
				<INPUT type="text" name="vip1_b2" value="0">－
				<INPUT type="text" name="vip1_e2" value="0">
				<br>
				区间3：
				<INPUT type="text" name="vip1_b3" value="0">－
				<INPUT type="text" name="vip1_e3" value="0">
				<br>
				区间4：
				<INPUT type="text" name="vip1_b4" value="0">－
				<INPUT type="text" name="vip1_e4" value="0">
  			</td>	
  		</tr>
  		<tr id="valuePer1" style="display: none">
  			<td>比例</td>
  			<td>
  				<INPUT type="text" name="per1" value="4">
  			</td>	
  		</tr>
  	</tr>
  	
  	<tr>
  		<td class="showTitle">条件2 类型：</td>
  		<td>
				<INPUT type="radio" name="R2" value="0" checked onclick="changeConditionType(2, 0)">无
				<INPUT type="radio" name="R2" value="1" onclick="changeConditionType(2, 1)">时间段条件
				<INPUT type="radio" name="R2" value="2" onclick="changeConditionType(2, 2)">区域条件
				<INPUT type="radio" name="R2" value="3" onclick="changeConditionType(2, 3)">vip等级
				<INPUT type="radio" name="R2" value="4" onclick="changeConditionType(2, 4)">比例
		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" style="display: inline">条件2 数值：</td>
  		<td id="valueNone2" style="display: inline">无</td>
  		<td id="valueTime2" style="display: none">时间段条件</td>
  		<td id="valueLocation2" style="display: none">区域条件</td>
  		<td id="valueVip2" style="display: none">vip等级</td>
  		<td id="valuePer2" style="display: none">比例</td>
  	</tr>
  </table>
   <div style="width:99%;" align="center">
	 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
	 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
	 </div>
  </form>
</body>
</html>
