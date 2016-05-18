<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>代码管理</title>
  <link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
  <link href="${ctx}/script/lib/ztree/zTreeStyle.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

	<script type="text/javascript" src="${ctx}/script/public.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>

<script type="text/javascript">
var tree = null;
var selectedTableId = null;

$(document).ready(function(){
});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	doReloadPage(offset, "${ctx}/permit/admin/code/reload.do", null);
	ajustDms();
}

//编辑代码名称
function doOnCellEdit(stage,rowId,cellInd){
	if(stage == 1){
		oldPreferValue = mygrid.cells(rowId, cellInd).getValue();
	}
	if(stage == 2){
		var modText = mygrid.cells(rowId, cellInd).getValue();
		//alert("要修改的id=" + rowId + ",用户确定了修改内容:" + modText+"需要修改的第几项内容="+cellInd);
		if(cellInd == 1||cellInd == 2){
			if(modText == null || modText == ""){
				alert("请输入要修改的参数内容！");
				return false;
			}
		} 
		if(oldPreferValue == modText){
			//没有修改数据，不保存
			oldPreferValue = null;
			return false;
		}
		//提交更新数据
		$.ajax({
		    url:"${ctx}/admin/code/updateCodeTable.do",
			type:"post",
			data:"codeTableId=" + rowId + "&modText=" + modText + "&cellInd=" +cellInd,
			success:function(msg){
				alert(msg);
			},
			error:function(msg){
				alert(msg);
			}
		});
	}
	return true;
}

function doOnRowSelected(id){
	//$("#treeDemo").empty();
	initTree(id);
	selectedTableId = id;
}
function initTree(codeTableId){
	tree = initAsyncZtree("treeDemo", "${ctx}/permit/admin/code/loadOneLvlItem.do?codeTableId=" + codeTableId);
}

function zTreeOnClick(e, treeId, treeNode){
	
}

function showAddCodeWnd(){
	/*
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请从左边列表中选择一个代码表记录。");
		return;
	}
	*/
	//检查是否选择了树的节点
	//var slcId = tree.getSelectedItemId();
	if(tree == null){
		alert("请先从左边列表选择一个“代码表数据”");
		return;
	}
	var slcId = getSelectTreeNodeId();
	if(!slcId){
		alert("请先选择一个树节点");
		return;
	}
	popDefaultDialog('添加新代码项', '${ctx}/permit/admin/code/showAddCodeItem.do?parentId=' + slcId);
}

//修改代码项
function showUpdateCodeWnd(){
	if(tree == null){
		alert("请先从左边列表中选择一个代码表记录！");
		return;
	}
	//检查是否选择了树的节点
	var node = getZtreeSelectNode(getZtreeObject("treeDemo"));
	var slcId = node.id;
	var slcName = node.name;
	//var parentId = tree.getParentId(slcId);
	if(slcId == null ||slcId == "" || slcId == "null"){
		alert("请从树节点中选择一个代码项记录！");
		return;
	}
	//parent.JqueryDialog.Open('修改代码项', '${ctx}/admin/code/showEditCodeItem.do?parentId=' + slcId + "&codeName=" + encodeURIComponent(slcName), 430, 300);
	popDefaultDialog('修改代码项', '${ctx}/permit/admin/code/showEditCodeItem.do?id=' + slcId + "&codeName=" + slcName);
}

//删除代码项
function deleteCodeWnd(){
	var flag = window.confirm("确定删除该代码项？");
	if(tree == null){
		alert("请先从左边列表中选择一个代码表记录！");
		return;
	}
	//检查是否选择了树的节点
	var node = getZtreeSelectNode(getZtreeObject("treeDemo"));
	var slcId = node.id;
	//var slcName = node.name;
	//var parentId = tree.getParentId(slcId);
	var parentId = node.pid;
	
	if(slcId == null ||slcId == "" || slcId == "null"){
		alert("请先从树节点中选择一个代码项记录！");
		return;
	}
	if(node.isParent){
		alert("该节点包含子节点，请先删除子节点！");
		return;
	}

	//删除数据
	if(flag == true){
		$.ajax({
		    url:"${ctx}/admin/code/deleteCodeInfo.do",
			type:"post",
			data:"codeId=" + slcId + "&parentId=" + parentId,
			success:function(msg){
				alert(msg);
				refreshCodeTree();
			},
			error:function(msg){
				showErrorTip(msg);
			}
		});
	}
}

/*弹出窗口回调：刷新树对象*/
function refreshCodeTree(){
	//$("#treeboxbox_tree").empty();
	initTree(selectedTableId);
}
</script>
</head>
  
<body>
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>系统代码管理</span>&nbsp;|&nbsp;
			<#if (pointers['CODE_ADD'])>
			<input type="button" value="添加新代码项" onclick="showAddCodeWnd()" class="button-tj"/>
			</#if>
			<#if (pointers['CODE_EDIT'])>
			<input type="button" value="修改代码项" onclick="showUpdateCodeWnd()" class="button"/>
			</#if>
			<#if (pointers['CODE_DEL'])>
			<input type="button" value="删除代码项" onclick="deleteCodeWnd()" class="button"/>
			</#if>
		</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="410px" valign="top">
			<div id="pageInfo">
				<#include "/system/code_list.ftl">
			</div>
		</td>
		<td width="10px">&nbsp;</td>
		<td valign="top">
			<div id="treeDemo" class="ztree" style="width:350px; height:432px; overflow-x:hidden; overflow-y:scroll"></div>
		</td>
	</tr>
</table>
</body>
<#include "/common/footer.ftl">
</html>