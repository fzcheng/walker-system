<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程定义管理</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript">

$(function(){

});

//重新加载业务分页列表，即：点击分页条的方法
function reload(offset){
	//var params = {start:startVal, end:endVal, storeType:storeType, filename:filename, ext:ext};
	doReloadPage(offset, "${ctx}/permit/flow/define/reload.do", null);
}

//弹出样式选择窗口
function popAddWindow(){
	parent.$.layer({
	    type: 2,
	    //shade: [0],
	    fix: false,
	    title : ['创建新流程',true],
	    // 如果加上该属性，那么鼠标在阴影中单击也可关闭窗口
	    //shadeClose: true,
	    maxmin: true,
	    zIndex: parent.layer.zIndex,
	    iframe : {src : '${ctx}/permit/flow/define/add.do'},
	    area : ['800px' , '500px'],
	    offset : ['100px', ''],
	    close : function(index){
	        //layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
	    }
	});
}

function popEditWindow(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个流程。");
		return;
	}
	parent.$.layer({
	    type: 2,
	    //shade: [0],
	    fix: false,
	    title : ['修改流程概要',true],
	    maxmin: true,
	    zIndex: parent.layer.zIndex,
	    iframe : {src : '${ctx}/permit/flow/define/edit.do?id='+selected[0]},
	    area : ['800px' , '500px'],
	    offset : ['100px', ''],
	    close : function(index){
	        //layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
	    }
	});
}

// 请求ajax的回调函数实现
var _callback = function ajaxCallback(data){
	if(data == "success"){
		reload(1);
	} else
		showErrorTip(data);
};

// 废弃流程
function deprecatedProcess(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个流程。");
		return;
	}
	if(window.confirm("您确定要废弃该流程么？废弃后只能被删除，不能再使用。")){
		var _params = {"id":selected[0]};
		requestAjax("${ctx}/permit/flow/define/deprecate.do", _params, _callback);
	}
}

// 删除一个流程定义
function deleteProcess(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个流程。");
		return;
	}
	if(window.confirm("确定要删除该流程么？这个操作不可恢复，通常只有在测试阶段才会真正删除不需要的数据。")){
		var _params = {"id":selected[0]};
		requestAjax("${ctx}/permit/flow/define/delete.do", _params, _callback);
	}
}

function showDesign(){
	var selected = getCheckValue("ids");
	if(selected == null || selected.length > 1){
		alert("请选择一个流程。");
		return;
	}
	window.location.href = "${ctx}/permit/flow/define/design.do?id=" + selected[0];
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>流程定义管理</span>&nbsp;|&nbsp;
	  	<input type="button" value=" 创建新流程 " onclick="popAddWindow();" class="button"/>&nbsp;
	  	<input type="button" value=" 修改流程概要 " onclick="popEditWindow();" class="button"/>&nbsp;|&nbsp;
	  	<input type="button" value=" 设计流程任务 " onclick="showDesign();" class="button-tj"/>&nbsp;
	  	<input type="button" value=" 查看历史版本 " onclick="history();" class="button"/>&nbsp;
	  	<input type="button" value=" 废弃流程 " onclick="deprecatedProcess();" class="button"/>&nbsp;|&nbsp;
	  	<input type="button" value=" 删除流程 " onclick="deleteProcess();" class="button-tj"/>&nbsp;
		</td>
	</tr>
</table>
<div id="pageInfo">
	<#include "/flow/define/flow_list.ftl" encoding="UTF-8">
</div>
 
</body>
<#include "/common/footer.ftl">
</html>