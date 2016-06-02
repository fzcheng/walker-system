<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>系统用户管理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css"/>
  <link rel="stylesheet" type="text/css" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>
  <link href="${ctx}/script/lib/ztree/zTreeStyle.css" type="text/css" rel="stylesheet"/>
  
  <script type="text/javascript" src="${ctx}/script/public.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
	
<script type="text/javascript">
$(document).ready(function(){
	var mygrid;
	var tree;
	// 初始化显示tip
	showTip("add_org_btn");
	
	tree = initTree();
});
function ajustDms(){
	hideSkyLoading();
	ajustFrameDms();
}

function initTree(){
	return doInitSimpleZtree0("treeDemo", '${departSet}', callback);
}

var callback = function zTreeOnClick(e, treeId, treeNode){
	if(treeNode.id == "" || treeNode.id == "root"){
		return;
	}
	reload(1);
};

function showAddUser(){
	//检查是否选择了树的节点
	var orgId = getSelectTreeNodeId();
	//var orgName = tree.getSelectedItemText();
	if(isEmptyValue(orgId)){
		alert("请选择左侧[组织机构]后再点击[增加用户]按钮。");
		return;
	}
	if(orgId == "root"){
		alert("根节点下无法添加用户，请先添加一个单位，选择单位后创建用户。");
		return;
	}
	parent.JqueryDialog.Open('添加新用户', '${ctx}/permit/admin/user/add.do?parentId=' + orgId, 580, 300);
}

function showEdit(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个用户。");
		return;
	}
	parent.JqueryDialog.Open('编辑用户', '${ctx}/permit/admin/user/showEdit.do?id=' + selected[0], 580, 300);
}

function showPost(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个用户。");
		return;
	}
	popDefaultDialog('设置岗位', '${ctx}/permit/admin/user/show_post.do?id=' + selected[0]);
}

function deleteUser(){
	var selected = getCheckValue("ids");
	if(selected == null){
		alert("请选择要删除的用户");
		return;
	}
	if(confirm("确定要删除选择的用户么？")){
		//var params = {"ids" : selected};
		//$("#content").load("${ctx}/admin/user/delete.do", params, null);
		$.ajax({
		  url:"${ctx}/admin/user/delete.do",
			type:"post",
			data:"ids="+selected,
			success:function(msg){
				reload(1);
				alert(msg);
			},
			error:function(msg){
				alert(msg);
			}
		});
	}
}

function easeUser(){
	var selected = getCheckValue("ids");
	if(selected == null){
		alert("请选择要删除的用户");
		return;
	}
	if(confirm("确定要彻底删除选择的用户么？彻底删除后，系统中将不保留任何该用户相关内容!")){
		if(confirm("确认业务已经不再关联该用户，通常只有超级管理员可以做此项操作。")){
			$.ajax({
			  url:"${ctx}/admin/user/ease.do",
				type:"post",
				data:"ids="+selected,
				success:function(msg){
					reload(1);
					alert(msg);
				},
				error:function(msg){
					alert(msg);
				}
			});
		}
	}
}

function resetPass(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个用户");
		return;
	}
	if(!window.confirm("确定要重置用户密码么？")){
		return;
	}
	requestAjax("${ctx}/admin/user/pass_reset.do", {id: selected[0]}, function(data){
		reload(1);
	});
}

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//拼json参数
	//var id = tree.getSelectedItemId();
	var id = getSelectTreeNodeId();
	
	if(id != "" && id == "null")
		id = "";
	var params = {"orgId":id};
	doReloadPage(offset, "${ctx}/permit/admin/user/reload.do", params);
}

</script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>系统用户管理</span>&nbsp;|&nbsp;
			<#if (pointers['USER_ADD'])>
	  		<input id="add_org_btn" type="button" value="添加用户" onclick="showAddUser();" class="button-tj" 
	  			title="<li>添加新的用户。</li><li>系统用户必须存在机构中，需要选择机构才能添加。</li>"/>
			</#if>
			<#if (pointers['USER_EDIT'])>
				<input type="button" id="editBtn" value="编辑用户" class="button" onclick="showEdit();"/>
			</#if>
			<#if (pointers['USER_DEL'])>
	  		<input type="button" value="删除用户" onclick="deleteUser();" class="button"/>
			</#if>
			<#if (pointers['USER_EASE'])>
	  		<input type="button" value="彻底删除" onclick="easeUser();" class="button"/>
			</#if>
			<#if (pointers['PASS_RESET'])>
	  		<input type="button" value="重置密码" onclick="resetPass();" class="button"/>
			</#if>
  		<input type="button" value="设置岗位" onclick="showPost();" class="button"/>
		</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="230px" valign="top">
		<!-- 
			<table border="0" cellpadding="0" cellspacing="0" width="230" height="100%">
				<tr><td class="title">单位列表</td></tr>
			</table>
		 -->
			<div id="treeDemo" class="ztree" style="width:230px; height:460px; overflow-x:hidden; overflow-y:scroll"></div>
			<!-- 
			<div id="treeboxbox_tree" style="width:230; height:432; background:#FFFFFF;border-color:#c1dad7;border-width:0 1 1 1;border-style:solid;"></div>
			 -->
		</td>
	  <td width="6">&nbsp;</td>
		<td valign="top" align="left">
			<div id="pageInfo">
				<#include "/system/user_list.ftl">
			</div>
		</td>
	</tr>
</table>

</body>
<#include "/common/footer.ftl">
</html>