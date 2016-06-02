<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加新岗位</title>
<script type="text/javascript">

/*	
$(function(){ // wait for document to load 
	showTipInput("name");
	showTipInput("summary");
});
*/

function save(){
	// 供应商名称
	if(!validateRequired("name")){
		return false;
	}
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	refreshCodeTree();
        	$("#content").html(data);
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

</script>
</head>
<body>

<form action="${ctx}/permit/admin/department/save_post.do" method="post" id="form" target="_self">
<input type="hidden" id="parentId" name="parentId" value="${parentId}" />
<input type="hidden" id="type" name="type" value="9" />
<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-form">
  	<tr>
  		<td class="showTitle" width="25%"><span class="required">*</span>岗位名称：</td>
  		<td width="75%">
  			<input type="text" id="name" name="name" style="width:260px;" class="text" maxlength="60" title="不超过60汉字"/>
  		</td>
  	</tr>
  	<tr>
  		<td class="showTitle">备注：</td>
  		<td valign="top">
  			<textarea id="summary" name="summary" style="width:260px;" rows="3" class="textarea"></textarea>
  		</td>
  	</tr>
 </table>
 </div>
 <div style="width:100%;" align="center">
 	<input type="button" value="确 定" class="button-tj" onclick="save();"/>
 </div>
</form>
</body>
</html>