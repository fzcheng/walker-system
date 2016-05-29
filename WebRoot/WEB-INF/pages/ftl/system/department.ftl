<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>组织机构管理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>
  <link href="${ctx}/script/lib/ztree/zTreeStyle.css" type="text/css" rel="stylesheet"/>
  
  <script type="text/javascript" src="${ctx}/script/public.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
	
	<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.ztree.all-3.5.min.js"></script>
	
	<!-- 上传组件 -->
	<script type="text/javascript" src="${ctx}/script/lib/jquery.MetaData.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.MultiFile.pack.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.MultiFile.js"></script>
	
<script type="text/javascript">
$(document).ready(function(){
	var mygrid;
	var tree;
	// 初始化显示tip
	showTip("add_org_btn");
	
	initTree();
});
function ajustDms(){
	hideSkyLoading();
	ajustFrameDms();
}

function initTree(){
	tree = initAsyncZtree("treeDemo", "${ctx}/permit/admin/department/loadTree.do");
}

function zTreeOnClick(e, treeId, treeNode){
	if(treeNode.id == "root") return;
	<#if (pointers['DEPT_VIEW'] != "")>
	  var params = {"id" : treeNode.id, "canEdit":"${pointers['DEPT_EDIT']}", "canDel":"${pointers['DEPT_DEL']}"};
		$("#content").load("${ctx}/admin/department/show.do", params, null);
	</#if>
}

function showAddOrg(){
	var node = getZtreeSelectNode(getZtreeObject("treeDemo"));
	var orgId = "0"; // 单位的parentId默认0表示根单位。
	if(node != null && node.id != "root"){
		orgId = node.id;
	}
	var params = {parentId : orgId};
	$("#content").load("${ctx}/permit/admin/department/showAddOrg.do", params, null);
}

function showAddDepartment(){
	//检查是否选择了树的节点
	//tree = $.fn.zTree.getZTreeObj("treeDemo");
	var node = getZtreeSelectNode(getZtreeObject("treeDemo"));
	if(node == null || node.id == "root"){
		alert("请选择[单位]后再点击[增加部门]按钮。");
		return;
	}
	var orgId = node.id;
	var slcName = node.name;
	//var parentId = tree.getParentId(slcId);
	var params = {parentId : orgId};
	$("#content").load("${ctx}/permit/admin/department/showAddDepartment.do", params, null);
}

function showAddPost(){
	var node = getZtreeSelectNode(getZtreeObject("treeDemo"));
	if(node == null || node.id == "root"){
		alert("请选择一个部门。");
		return;
	}
	var orgId = node.id;
	var slcName = node.name;
	//var parentId = tree.getParentId(slcId);
	var params = {parentId : orgId};
	$("#content").load("${ctx}/permit/admin/department/show_add_post.do", params, null);
}

function refreshCodeTree(){
	$("#treeDemo").empty();
	initTree();
}

function deleteOrg(id){
	if(confirm("确定要删除机构么？删除后记录仍然保留，可以由超级管理员恢复。")){
		var params = {"id" : id};
		$("#content").load("${ctx}/admin/department/deleteOrg.do", params, null);
	}
}

function easeOrg(id){
	if(confirm("确定要彻底删除机构么？删除后不可恢复。")){
		if(confirm("彻底删除后，关联的业务数据可能缺乏完整性，通常仅在测试阶段使用该功能!")){
			var params = {"id" : id};
			$("#content").load("${ctx}/admin/department/easeOrg.do", params, null);
		}
	}
}

function showEditPage(id){
	var params = {"id" : id};
	$("#content").load("${ctx}/permit/admin/department/showEdit.do", params, null);
}

</script>
</head>
<body>

<table border="0" cellspacing="0" cellpadding="0"  class="table-form">
	<tr class="title">
		<td>
			<span>组织机构管理</span>&nbsp;|&nbsp;
			<#if (pointers['DEPT_ADD'] != "")>
	  		<input id="add_org_btn" type="button" value="添加新单位" onclick="showAddOrg();" class="button" 
	  			title="<li>单位是系统机构中的根节点，可以存在多个单位。</li><li>必须在单位下面才能存在[部门]数据，每个单位都可以指定多个[管理员]来维护本单位信息。</li>"/>
	  		<input type="button" value="添加新部门" onclick="showAddDepartment();" class="button"/>
			</#if>
			<#if (pointers['DEPT_ADD_POST'] != "")>
	  		<input type="button" value="添加岗位" onclick="showAddPost();" class="button"/>
			</#if>
		</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="220px" valign="top">
		<!-- 
			<table border="0" cellpadding="0" cellspacing="0" width="210" height="100%">
				<tr><td class="title">单位列表</td></tr>
			</table>
		 -->
			<div id="treeDemo" class="ztree" style="width:210px; height:460px; overflow-x:hidden; overflow-y:scroll"></div>
		</td>
		<td width="6">&nbsp;</td>
		<td valign="top" align="left">
			<div id="content"></div>
		</td>
	</tr>
</table>
</body>
<#include "/common/footer.ftl">
</html>