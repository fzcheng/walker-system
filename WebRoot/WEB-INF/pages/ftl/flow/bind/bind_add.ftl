<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设置流程绑定对象</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/ztree/zTreeStyle.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/tip/jquery.poshytip.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/downTree.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 
	//showTipInput("name");
	//showTipInput("process");
 });

function save(){
	var _type = getRadioSelectValue("type");
	if(isEmptyValue(_type)){
		alert("未选择绑定类型。");
		return false;
	}
	if(_type == "0"){
		if(!validateRequired("orgName")){
			return false;
		} else {
			$("#bindId").val($("#orgId").val());
			$("#bindName").val($("#orgName").val());
		}
	} else if(_type == "1"){
		if(!validateRequired("deptName")){
			return false;
		} else {
			$("#bindId").val($("#deptId").val());
			$("#bindName").val($("#deptName").val());
		}
	}
	if(!validateRequired("processDefineId")){
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
        		showErrorTip(data);
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
	var pname = $("#processDefineId").find("option:selected").text();
	if($("#processDefineId").val() != ""){
		$("#processDefineName").val(pname);
		console.log("pname: " + pname);
	}
}

function clickRadio(obj){
	v = obj.value;
	if(v == "0"){
		$("#orgTr").show();
		$("#deptTr").hide();
	} else if(v == "1"){
		$("#orgTr").hide();
		$("#deptTr").show();
	} else if(v == "2"){
		
	}
}
</script>
</head>
<body>

<form action="${ctx}/permit/flow/bind/save.do" method="post" id="form" target="_self">
	<input type="hidden" id="bindId" name="bindId"/>
	<input type="hidden" id="bindName" name="bindName"/>
	<input type="hidden" id="processDefineName" name="processDefineName"/>
	<table border="0" cellpadding="0" cellspacing="0" width="99%" class="table-form">
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)绑定类型：</td>
  		<td>
  			<input type="radio" id="" name="type" value="0" onclick="clickRadio(this);" checked="checked"/>单位&nbsp;
  			<input type="radio" id="" name="type" value="1" onclick="clickRadio(this);"/>部门&nbsp;
  			<input type="radio" id="" name="type" value="2" onclick="clickRadio(this);" disabled="disabled"/>角色&nbsp;
  			<input type="radio" id="" name="type" value="3" onclick="clickRadio(this);" disabled="disabled"/>岗位&nbsp;
  			<input type="radio" id="" name="type" value="9" onclick="clickRadio(this);" disabled="disabled"/>个人&nbsp;
  		</td>
  	</tr>
  	<tr id="orgTr">
  		<td class="showTitle">(<span class="required">*</span>)选择单位：</td>
  		<td>
  			<input id="orgId" name="orgId" type="hidden"/>
    		<input id="orgName" name="orgName" type="text" readonly="readonly" style="width:280px;" class="text"/>
  		</td>
  	</tr>
  	<tr id="deptTr" style="display:none;">
  		<td class="showTitle">(<span class="required">*</span>)选择部门：</td>
  		<td>
  			<input id="deptId" name="deptId" type="hidden"/>
    		<input id="deptName" name="deptName" type="text" readonly="readonly" style="width:280px;" class="text"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)业务类别：</td>
  		<td>
  			<select id="businessType" name="businessType" style="width:280px;">
  				<option value="0" selected="selected">无类别</option>
  				<#list businessType?keys as k>
  				<option value="${k}">${businessType[k]}</option>
  				</#list>
  			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">(<span class="required">*</span>)关联流程：</td>
  		<td>
  			<select id="processDefineId" name="processDefineId" style="width:280px;" onchange="changeFlowSelect();">
  				<option value="" selected="selected">----- 选择流程 -----</option>
  				<#list processes as process>
  				<option value="${process.processId}">${process.info}</option>
  				</#list>
  			</select>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="150px">备注：</td>
  		<td>
  			<input type="text" id="summary" name="summary" class="text" 
  			maxlength="50" title="不超过100字"/>
  		</td>
  	</tr>
 </table>
 <div style="width:99%; height:230px;"></div>
 <div style="width:99%;" align="center">
 	<input type="button" value="保 存" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>

<script type="text/javascript">
var zNodes = '${orgSet}';
zNodes = stringToJson(zNodes);
//var zNodes = [{id:1,name:"新开普电子科技有限公司",superId:0},{departId:2,name:"产品研发中心",superId:1},{departId:3,name:"软件技术研究部",superId:2}];
$("#orgName").initDownTree({
	 idInputName:"orgId",
   zNodes:zNodes
});
</script>

</html>