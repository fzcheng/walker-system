<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>创建新测试项目</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	//showTipInput("name");
	//showTipInput("process");
 });

function save(){
	var name = $.trim($("#name").val());
	if(name == ""){
		alert("请输入'项目名称'");
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
        		alert(data);
        	}
        },
        error:function(data){
        	try{
	        	console.log("dsfsdf " + data);
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

function changeFlowSelect(){
	var pname = $("#processId").find("option:selected").text();
	if($("#processId").val() != ""){
		$("#processName").val(pname);
		console.log("pname: " + pname);
	}
}
function saveTest(){
	parent.frames.sampleframe.test();
	closeWindow();
}
</script>
</head>
<body>

<form action="${ctx}/permit/flow/test/save.do" method="post" id="form" target="_self">
	<input type="hidden" id="processName" name="processName"/>
	<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
  	<tr class="title">
  		<td colspan="2">创建新测试项目</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)项目名称：</td>
  		<td width="300px">
  			<input type="text" id="name" name="name" class="text" 
  			maxlength="50" title="不超过50个字符，用来唯一定义每种流程，不能重复"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">项目描述：</td>
  		<td width="300px">
  			<input type="text" id="summary" name="summary" class="text" 
  			maxlength="50" title="不超过100字"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)选择流程：</td>
  		<td width="260">
  			<select id="processId" name="processId" style="width:200px;" onchange="changeFlowSelect();"
  			title="<li>如果是子流程，那么它必须属于一个主流程中的子任务。</li><li>一般流程都是独立流程。</li>">
  				<option value="" selected="selected">===== 选择流程 =====</option>
  				<#list processes as process>
  				<option value="${process.processId}">${process.info}</option>
  				</#list>
  			</select>
  		</td>
  	</tr>
 </table>
 <div style="width:450px;" align="center">
 	<input type="button" value=" 保 存 " class="button" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>
</html>