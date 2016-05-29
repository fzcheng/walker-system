<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设置用户所属岗位</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<link type="text/css" rel="stylesheet" href="${ctx}/script/lib/tip/tip-yellowsimple.css"/>
<link href="${ctx}/script/lib/ztree/zTreeStyle.css" type="text/css" rel="stylesheet"/>

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
 });

function save(){
	// 供应商名称
	if(!validateRequired("name")){
		return false;
	}
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	if(data == "success"){
	        	parent.frames.sampleframe.reload(1);
	        	setTimeout(function(){closeWindow();},0);
        	} else {
        		showErrorTip(data);
        	}
        },
        error:function(data){
        	try{
	        	console.log("error:" + data);
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
</script>
</head>
<body>

<form action="${ctx}/permit/exam/info/save.do" method="post" id="form" target="_self">
<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle" width="25%"><span class="required">*</span>用户名称：</td>
  		<td width="75%">${userName}</td>
  	</tr>
  	<tr>
  		<td class="showTitle"><span class="required"></span>所属部门：</td>
  		<td>
  			<input id="departId" name="departId" type="hidden" value=""/>
    		<input id="departName" name="departName" type="text" size="15" readonly="readonly" style="width:150px;" class="text"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle"><span class="required">*</span>岗位选择：</td>
  		<td>
  			<select id="limitTime" name="limitTime">
		  		<option value="0" selected="selected">不限制</option>
		  		<option value="1">限制时间段</option>
		  	</select>
  		</td>
  	</tr>
 </table>
 </div>
 <div style="width:100%;" align="center">
 	<input type="button" value="确 定" class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value="关 闭" class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>

<script type="text/javascript">
var zNodes = '${departSet}';
zNodes = stringToJson(zNodes);
//var zNodes = [{id:1,name:"新开普电子科技有限公司",superId:0},{departId:2,name:"产品研发中心",superId:1},{departId:3,name:"软件技术研究部",superId:2}];
$("#departName").initDownTree({
	 idInputName:"departId",
	 parentSelLimit:true, //父节点不能被选择
   zNodes:zNodes
});
</script>

</html>