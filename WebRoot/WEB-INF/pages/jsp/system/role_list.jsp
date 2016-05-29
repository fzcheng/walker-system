<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <title>角色设置</title>
  <link rel="stylesheet" type="text/css" href="${ctx}/style/public.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/script/dhtml/grid/dhtmlxgrid.css"/>
  
  <script type="text/javascript" src="${ctx}/script/public.js"></script>
  <script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
  <script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
  <script type="text/javascript" src="${ctx}/script/dhtml/tree/dhtmlxcommon.js"></script>
	
  <script type="text/javascript" src="${ctx}/script/dhtml/grid/dhtmlxgrid.js"></script>		
  <script type="text/javascript" src="${ctx}/script/dhtml/grid/dhtmlxgridcell.js"></script>
  <script type="text/javascript" src="${ctx}/script/dhtml/grid/dhtmlxgrid_pgn.js"></script>
<script>
	$(document).ready(function(){
		var mygrid;
		//$("#btn2").click(function(){
			//parent.$("#modalWin").click(function(){
				//parent.JqueryDialog.Open('模态DIV1', '${ctx}/system/role/role_add.jsp', 400, 300);
			//});
		//});
		//$("a[id]=editLink").bind("click", {foo: $("a[id]=editLink").attr("data")}, showEditPop);
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
	
	/*初始化列表*/
	function initGrid(){
		
		showSkyLoading("数据加载中，请稍候......");
	
		mygrid = new dhtmlXGridObject("gridbox");
		//mygrid.setImagePath("../../script/dhtml/grid/imgs/");
		mygrid.setHeader("序号, 角色名称, 功能, 用户");
		mygrid.setInitWidths("50,150,50,*");
		mygrid.setColAlign("center,left,center,center");
		mygrid.setColTypes("ro,ro,ro,ro");
		mygrid.enableAutoHeight(true, "400");
		mygrid.init();
		mygrid.setSkin("gray");
		mygrid.enablePaging(true,12,5,"pagingArea",true);
		mygrid.loadXML("${ctx}/permit/admin/role/loadRoleList.do",ajustDms,"xml");
	}
	
	function ajustDms(){
		hideSkyLoading();
		ajustFrameDms();
	}
	/*刷新角色列表，其他页面调用*/
	function reloadGrid(){
		mygrid.clearAll();
		mygrid.loadXML("${ctx}/permit/admin/role/loadRoleList.do",ajustDms,"xml");
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
				ajustFrameDms();
			}
		});
	}
	/*提交保存角色功能*/
	function submitForm(){
		//formSerialize()
		var sQuery = $("#myform1 *").fieldValue(true);
		if(sQuery == null || sQuery == "" || sQuery == $("#roleId").val()){
			alert("请选择该角色拥有的功能。");
			return;
		}
		$.ajax({
			async:false,
			url:"${ctx}/admin/role/saveRoleFunc.do",
			type:"post",
			data:"roleId=" + $("#roleId").val() + "&funcIds=" + sQuery,
			success:function(data){
				$("#content").empty();
				$("#content").html(data);
				$("#submitBtn").hide();
				$("#submitUsrBtn").hide();
			},
			error:function(data){
				$("#content").empty();
				$("#content").html(data);
			}
		});
	}
	/*设置角色用户*/
	function showUser(roleId){
		$.ajax({
			async:false,
			url:"${ctx}/permit/admin/role/getRoleUserHtml.do",
			type:"post",
			data:"roleId=" + roleId,
			success:function(data){
				$("#content").empty();
				$("#content").html(data);
				$("#submitBtn").hide();
				$("#submitUsrBtn").show();
				ajustFrameDms();
			}
		});
	}
	/*提交保存角色用户*/
	function submitUser(){
		var sQuery = $("#myform1 *").fieldValue(true);
		if(sQuery == null || sQuery == "" || sQuery == $("#roleId").val()){
			alert("请选择该角色包含的用户。");
			return;
		}
		$.ajax({
			async:false,
			url:"${ctx}/admin/role/saveRoleUser.do",
			type:"post",
			data:"roleId=" + $("#roleId").val() + "&userIds=" + sQuery,
			success:function(data){
				$("#content").empty();
				$("#content").html(data);
				$("#submitBtn").hide();
				$("#submitUsrBtn").hide();
				ajustFrameDms();
			},
			error:function(data){
				$("#content").empty();
				$("#content").html(data);
			}
		});
	}
</script>
</head>
  
<body onload="initGrid()">
<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin:0 0 6 0;">
	<tr>
		<td class="toolbar">
			<span>角色权限设置</span>
			<c:if test="${! empty pointers['ROLE_ADD']}">
				<input type="button" value="创建新角色" onclick="showAddRoleWnd()" class="btn2"/>
			</c:if>
		</td>
	</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="306px" valign="top">
			<table width="300px" height="100%" cellpadding="0" cellspacing="0" border="0">
				<tr><td><div id="loading"></div></td></tr>
				<tr>
					<td width="300px">
						<div id="gridbox" style="background-color:white;overflow:hidden"></div>
					</td>
				</tr>
				<tr>
					<td valign="middle">
						<div id="pagingArea" class="paging"></div>
					</td>
				</tr>
			</table>
		</td>
		<td valign="top" width="*">
			<form id="myform1" name="myform1">
				<div id="content">
					<label><input type="checkbox" id="a" name="a" value="aaa">苹果</label>
					<label><input type="checkbox" id="b" name="b" value="bbb">西瓜</label>
				</div>
				<c:choose>
					<c:when test="${! empty pointers['ROLE_FUNC']}">
						<input type="button" value="保存角色功能" id="submitBtn" name="submitBtn" onclick="submitForm()" class="btn2"/>
					</c:when>
					<c:otherwise>
						<input type="button" value="N/A" id="submitBtn" name="submitBtn" class="btn2"/>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${! empty pointers['ROLE_USER']}">
						<input type="button" value="保存角色用户" id="submitUsrBtn" name="submitUsrBtn" onclick="submitUser()" class="btn2"/>
					</c:when>
					<c:otherwise>
						<input type="button" value="N/A" id="submitUsrBtn" name="submitUsrBtn" class="btn2"/>
					</c:otherwise>
				</c:choose>
			</form>
		</td>
	</tr>
</table>

</body>
</html>
