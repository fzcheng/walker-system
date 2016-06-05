<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>角色设置</title>
  <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
  
  <script type="text/javascript" src="${ctx}/script/public.js"></script>
  <script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
	
<script>
	$(document).ready(function(){
		$("#submitBtn").hide();
		$("#submitUsrBtn").hide();
	});
	
	/*弹出添加角色窗口*/
	function showAddRoleWnd(){
		parent.JqueryDialog.Open('创建新角色', '${ctx}/permit/admin/role/add.do', 430, 260);
	}
	/*弹出编辑角色窗口*/
	function showEditRoleWnd(id){
		parent.JqueryDialog.Open('编辑角色', '${ctx}/permit/admin/role/edit.do?rid=' + id, 400, 300);
	}

	//重新加载业务分页列表，即：点击分页条的方法
	function reload(offset){
		var params = {};
		doReloadPage(offset, "${ctx}/permit/admin/role/reload.do", params);
		ajustDms();
	}
	
	/*设置角色功能*/
	function showFunction(roleId){
		$.ajax({
			async:false,
			cache:false,
			url:"${ctx}/permit/admin/role/getRoleFuncHtml.do",
			type:"get",
			data:"roleId=" + roleId,
			success:function(data){
				$("#content").empty();
				$("#content").html(data);
				$("#submitBtn").show();
				$("#submitUsrBtn").hide();
				ajustDms();
			}
		});
	}
	/*显示用户应用*/
	function showApps(userId){
		$.ajax({
			async:false,
			url:"${ctx}/permit/appos/userapp/getUserAppsHtml.do",
			type:"post",
			data:"userId=" + userId,
			success:function(data){
				$("#content").empty();
				$("#content").html(data);
				$("#submitBtn").hide();
				$("#submitUsrBtn").show();
				ajustDms();
			},
			error:function(data){
				alert(0);
			}
		});
	}
	/*提交保存用户应用*/
	function submitUser(){
		var sQuery = $("#myform1 *").fieldValue(true);
		if(sQuery == null || sQuery == "" || sQuery == $("#userId").val()){
			alert("请选择该角色包含的用户。");
			return;
		}
		$.ajax({
			async:false,
			url:"${ctx}/appos/userapp/saveUserApp.do",
			type:"post",
			data:"userId=" + $("#userId").val() + "&appIds=" + sQuery,
			success:function(data){
				$("#content").empty();
				$("#content").html(data);
				$("#submitBtn").hide();
				$("#submitUsrBtn").hide();
				ajustDms();
			},
			error:function(data){
				$("#content").empty();
				$("#content").html(data);
			}
		});
	}

function removeRole(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一条数据。");
		return;
	}
	if(window.confirm("确定要删除角色么")){
		requestAjax("${ctx}/admin/role/remove.do", {"roleId":selected[0]}, function(data){
			reload(0);
		});
	}
}
</script>
</head>
  
<body>
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>用户应用权限</span>&nbsp;|&nbsp;
			
		</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="410px" valign="top">
			<div id="pageInfo">
				<#include "/appos/userapp/user_list.ftl">
			</div>
		</td>
		<td width="10px">&nbsp;</td>
		<td valign="top">
			<form id="myform1" name="myform1">
				<div id="content" style="margin-top:0;">
					请先选择一个用户的"关联应用"
				</div>
				
				<#if (pointers['USER_APP'])>
					<input type="button" value="保存用户应用" id="submitUsrBtn" name="submitUsrBtn" onclick="submitUser()" class="button-tj"/>
				<#else>
					<input type="button" value="N/A" id="submitUsrBtn" name="submitUsrBtn" class="button"/>
				</#if>
			</form>
		</td>
	</tr>
</table>

</body>
<#include "/common/footer.ftl">
</html>
