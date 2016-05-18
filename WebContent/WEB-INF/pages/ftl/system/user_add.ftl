<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加系统用户</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	showTipInput("orderNum");
	showTipInput("loginId");
	showTipInput("name");
	showTipInput("type");
	showTipInput("enable");
	showTipInput("summary");
});

function Ok(){
	//相关逻辑代码
	//alert(parent.frames.sampleframe.mygrid);
	//window.frames.sampleframe.mygrid.reload();
	if(!f_check()){
		return false;
	} else {
		return postData();
	}
}

function postData(){
	var result = false;
	//var roleIdsVal = getCheckboxVal("roleIdsDiv");
	$("#roleIds").val(getCheckboxVal("roleIdsDiv"));
	
	$('#form').ajaxSubmit({
      //target: '#content',
      async:false,
      success: function(data){
    	  if(data != ""){
    		  alert(data);
    		  result = false;
    	  } else {
	    	  parent.frames.sampleframe.reload(1);
	      	result = true;
    	  }
      }
  });
	return result;
	
/*
	$.ajax({
		async:false,
		type:"post",
		url:"${ctx}/admin/user/save.do",
		data:"parentId=" + $("#parentId").val() + "&codeName=" + $("#codeName").val() + "&codeStand=" + $("#codeStand").val(),
		success:function(msg){
			if(msg == "0"){
				alert("代码项保存成功！");
				parent.frames.sampleframe.refreshCodeTree();
				result = true;
			} else {
				alert("保存代码项出现错误！");
			}
		}
	});
	return result;
	*/
}

function f_check(){
	//if($("#orderNum").val() == ""){		
		//alert("请输入'顺序号'");
		//$("#orderNum").focus();
		//return false;
	//}
	if($("#loginId").val() == ""){		
		alert("请输入'登陆ID'");
		$("#loginId").focus();
		return false;
	}
	if($("#name").val() == ""){		
		alert("请输入'用户名称'");
		$("#name").focus();
		return false;
	}
	return true;
}	

function save(){
	$('#form').ajaxSubmit({
      target: '#content',
      success: function(){
      	refreshCodeTree();
      }
  });
  return false; // <-- important!
	/**
	$("#form").ajaxForm({
		action: "${ctx}/admin/department/save.do",
		method: "post",
		success: function(data){
			alert(data);
		}
	});*/
}
</script>
</head>
<body>

 	<form action="${ctx}/admin/user/save.do" method="post" id="form" target="_self">
		<input type="hidden" id="parentId" name="parentId" value="${parentId}"/>
		<input type="hidden" id="roleIds" name="roleIds"/>
 	
 	<table border="0" cellpadding="0" cellspacing="0" width="600px" class="table-form">
   	<!-- 
   	<tr>
   		<td class="title" width="150px">顺序号：</td>
   		<td width="450px">
   			<input type="text" id="orderNum" name="orderNum" class="textbox_req" 
   			maxlength="6" title="只能是整数，在展示用户列表时使用其来排序"/>
   		</td>
   	</tr>
    -->
   	<tr>
   		<td class="showTitle" width="150px">(<span class="required">*</span>)登录ID：</td>
   		<td width="450px">
   			<input type="text" id="loginId" name="loginId" class="textbox_req" 
   			maxlength="30" title="只能输入英文、数字、下划线或者其组合，不超过30个字符"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">(<span class="required">*</span>)用户名称：</td>
   		<td >
   			<input type="text" id="name" name="name" class="textbox_req" 
   			maxlength="50"	title="用户姓名或者中文名称"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">(<span class="required">*</span>)用户类型：</td>
   		<td width="260">
   			<select id="type" name="type"
   			title="<li>定义为单位管理员，那么该用户具有管理本单位机构和用户权限。</li><li>其可以看到本单位的部门和用户信息，这还要依赖权限是否给定。</li>">
   				<option value="2" selected="selected">普通用户</option>
   				<option value="1">单位管理员</option>
   			</select>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">(<span class="required">*</span>)启用状态：</td>
   		<td width="260">
   			<select id="enable" name="enable"
   			title="如果禁用，那么该用户将无法登陆并使用系统">
   				<option value="1" selected="selected">启用</option>
   				<option value="0">禁用</option>
   			</select>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">所属机构：</td>
   		<td>${parentName}</td>
   	</tr>
   	<tr>
   		<td class="showTitle">备注：</td>
   		<td >
   			<input type="text" id="summary" name="summary" class="textbox_req" maxlength="120"
   				title="用户描述信息，不超过100字，可不填"/>
   		</td>
   	</tr>
   	<tr>
   		<td class="showTitle">所属角色：</td>
   		<td>
   		<div id="roleIdsDiv">
   			<#list roleList as role>
   				<input type="checkbox" name="checkId" value="${role.id}"/>
   				${role.name}【${role.summary}】<br>
   			</#list>
   		</div>
   		&nbsp;
   		</td>
   	</tr>
   </table>
 	</form>
</body>
</html>