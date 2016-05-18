<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>JQGrid列表展示</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/jqueryui/jquery-ui.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/ui.jqgrid.css"/>
  
  <script type="text/javascript" src="${ctx}/script/public.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery-ui.js"></script>
	<script type="text/javascript" src="${ctx}/script/jqgrid/grid.locale-cn.js"></script>
	<!-- jquery-migrate-1.1.1.js插件能避免1.9之前jquery中已经删除的方法仍能够使用 
	<script type="text/javascript" src="${ctx}/script/lib/jquery-migrate-1.1.1.js"></script>
	-->
	<script type="text/javascript" src="${ctx}/script/jqgrid/jquery.jqGrid.min.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.tablednd.js"></script>
	<script type="text/javascript" src="${ctx}/script/lib/jquery.contextmenu.js"></script>
	
	
<script type="text/javascript">
$(document).ready(function(){
	initGrid();
	
	/* 窗口大小改变时，自动设置grid宽度和高度。
	$(window).resize(function () {
  	$("#List").setGridWidth($(window).width());
    $("#List").setGridHeight($(window).height() - 121);
  });
	*/
});
function ajustDms(){
	hideSkyLoading();
	ajustFrameDms();
}

function initGrid(){
	//$("#List").jqGrid('GridUnload'); //可能需要重构
  //$("#List").jqGrid('GridDestroy'); //销毁
  var h = $(window).height() - 221;
  //var w = $(window).width();
  var w = 700;
console.log("---------- h: " + h + ", w: " + w);
	colDatas = stringToJson('${columnDatas}');
	colNames = "${columnNames}";
	colNames = colNames.split(",");
	$("#list").jqGrid({ 
		sortable: true,
		url:"${ctx}/permit/admin/user/get_jqgrid_json.do",//url地址
    datatype: "json",       //数据类型
    width:w,             //grid宽
    height: h,            //grid高
    colNames:colNames, //列标题设置
    colModel:colDatas, 
    multiselect: false, //多选设置
    rowNum:10,         //每页行数
    rowList:[15,20,30],  //允许动态设置的行数
    pager: '#pager',     //分页关联的div的id
    sortname: 'id', //数据显示时，按哪列排序
    shrinkToFit: false,//列是否随grid大小改变
    autowidth: false,
     viewrecords: true, //分页工具栏右下角显示记录总行数
    //caption: "用户信息浏览" ,//grid标题
    multiselectWidth: 50,
     pagerpos: 'center',
    jsonReader : {  
        root:"data",  //后台返回的json数据名称 
        page: "page", //当前页 
        total: "total", //总页数   
        records: "records",//总行数
        rows:"rows"  //每页行数
  	 }
  });
	//设置列冻结
	$("#list").jqGrid('setFrozenColumns');
	$("#list").jqGrid('navGrid','#pager',{add:false,edit:false,del:false});
}

function rebuildGrid(){
	var visibles = getCheckboxValues("visibleColumns");
	var frozens  = getCheckboxValues("frozenColumns");
	window.location.href = "${ctx}/admin/user/jqgrid_index.do?visibles="+ visibles + "&frozens=" + frozens;
}
</script>
</head>
<body style="height:700px;">
<table border="0" cellspacing="0" cellpadding="0" class="table-form"> 
  <tr class="title">  
    <td width="460px">用户数据浏览-JQGrid展示
    &nbsp;|&nbsp;<input type="button" value=" 确 定 " class="button-tj" onclick="rebuildGrid();"/>
    </td>  
  </tr>
  <tr>
  	<td>显示列：
  		<#list allColumns as col>
		    <#if (col.visible)>
				  <input type="checkbox" name="visibleColumns" value="${col.name}" checked="checked"/>${col.alias}&nbsp;
		    <#else>
				  <input type="checkbox" name="visibleColumns" value="${col.name}"/>${col.alias}&nbsp;
		    </#if>
		  </#list>
  	</td>
  </tr>
  <tr>
  	<td>冻结列：
  		<#list allColumns as col>
		    <#if (col.frozen)>
				  <input type="checkbox" name="frozenColumns" value="${col.name}" checked="checked"/>${col.alias}&nbsp;
		    <#else>
				  <input type="checkbox" name="frozenColumns" value="${col.name}"/>${col.alias}&nbsp;
		    </#if>
		  </#list>
  	</td>
  </tr>
</table>
<table id="list"></table>
<div id="pager"></div>

</body>
<#include "/common/footer.ftl">
</html>