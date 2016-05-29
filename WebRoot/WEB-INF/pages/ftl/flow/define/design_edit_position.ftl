<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设计器-调整节点位置</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>
<style type="text/css">
.dataBg {background:#FFFCFC; font-color:red;}
</style>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.form.js"></script>
<script type="text/javascript">

var index = parent.layer.getFrameIndex(window.name);
	
$(function(){ // wait for document to load 

 });

function save(){
	
	$('#form').ajaxSubmit({
				//dataType: 'text',
        //target: '#content',
        success: function(data){
        	if(data == "success"){
	        	alert("成功调整了节点位置。");
	        	parent.frames.sampleframe.reload();
	        	closeWindow();
        	} else {
        		alert(data);
        	}
        },
        error:function(data){
        	try{
	        	console.log(data);
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
<body style="margin:0px;">
<p>
	<font color="red">说明1：可以通过选择节点位置来调整连接线的方向，注意一旦这样设置后，之前的连接信息将被删除，您需要重新设置连接的属性！</font><br>
	<font>说明2：'关闭'-代表该连接点不可用，'输入'-代表该连接点作为输入点，'输出'-代表该连接点作为输出点。</font>
</p>
<p/>
<form action="${ctx}/permit/flow/design/save_ajust_pos.do" method="post" id="form" target="_self">
<input type="hidden" id="taskDefineId" name="taskDefineId" value="${task.id}"/>
<table border="0" cellpadding="0" cellspacing="0" width="510px">
	<tr>
		<td width="150px" height="35px" align="right" valign="bottom">
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="topLeft" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['TopLeft'])>
				<input type="radio" id="2" name="topLeft" value="-1" checked="checked"/><font class="dataBg">输入</font>&nbsp;
				<#else>
				<input type="radio" id="2" name="topLeft" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['TopLeft'])>
				<input type="radio" id="3" name="topLeft" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="topLeft" value="1"/>输出
				</#if>
			</span>
		</td>
		<td width="210px" height="35px" align="center" valign="bottom">
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="topCenter" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['TopCenter'])>
				<input type="radio" id="2" name="topCenter" value="-1" checked="checked"/><font class="dataBg" style="background:#FFFCFC;">输入</font>&nbsp;
				<#else>
				<input type="radio" id="2" name="topCenter" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['TopCenter'])>
				<input type="radio" id="3" name="topCenter" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="topCenter" value="1"/>输出
				</#if>
			</span>
		</td>
		<td width="150px" height="35px" valign="bottom">
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="topRight" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['TopRight'])>
				<input type="radio" id="2" name="topRight" value="-1" checked="checked"/>输入&nbsp;
				<#else>
				<input type="radio" id="2" name="topRight" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['TopRight'])>
				<input type="radio" id="3" name="topRight" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="topRight" value="1"/>输出
				</#if>
			</span>
		</td>
	</tr>
	<tr>
		<td align="right">
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="middleLeft" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['LeftMiddle'])>
				<input type="radio" id="2" name="middleLeft" value="-1" checked="checked"/>输入&nbsp;
				<#else>
				<input type="radio" id="2" name="middleLeft" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['LeftMiddle'])>
				<input type="radio" id="3" name="middleLeft" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="middleLeft" value="1"/>输出
				</#if>
			</span>
		</td>
		<td height="93px" class="flow_node_bg" align="center">${task.name}</td>
		<td>
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="middleRight" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['RightMiddle'])>
				<input type="radio" id="2" name="middleRight" value="-1" checked="checked"/>输入&nbsp;
				<#else>
				<input type="radio" id="2" name="middleRight" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['RightMiddle'])>
				<input type="radio" id="3" name="middleRight" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="middleRight" value="1"/>输出
				</#if>
			</span>
		</td>
	</tr>
	<tr>
		<td align="right">
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="bottomLeft" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['BottomLeft'])>
				<input type="radio" id="2" name="bottomLeft" value="-1" checked="checked"/>输入&nbsp;
				<#else>
				<input type="radio" id="2" name="bottomLeft" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['BottomLeft'])>
				<input type="radio" id="3" name="bottomLeft" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="bottomLeft" value="1"/>输出
				</#if>
			</span>
		</td>
		<td align="center">
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="bottomCenter" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['BottomCenter'])>
				<input type="radio" id="2" name="bottomCenter" value="-1" checked="checked"/>输入&nbsp;
				<#else>
				<input type="radio" id="2" name="bottomCenter" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['BottomCenter'])>
				<input type="radio" id="3" name="bottomCenter" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="bottomCenter" value="1"/>输出
				</#if>
			</span>
		</td>
		<td>
			<span style="border:1px solid #666666; padding:3px;">
				<input type="radio" id="1" name="bottomRight" value="0" checked="checked"/>关闭&nbsp;
				<#if (inPositions['BottomRight'])>
				<input type="radio" id="2" name="bottomRight" value="-1" checked="checked"/>输入&nbsp;
				<#else>
				<input type="radio" id="2" name="bottomRight" value="-1"/>输入&nbsp;
				</#if>
				<#if (outPositions['BottomRight'])>
				<input type="radio" id="3" name="bottomRight" value="1" checked="checked"/>输出
				<#else>
				<input type="radio" id="3" name="bottomRight" value="1"/>输出
				</#if>
			</span>
		</td>
	</tr>
</table>
<br>
 <div style="width:570px;" align="center">
 	<input type="button" value=" 保 存 " class="button-tj" onclick="save();"/>&nbsp;&nbsp;
 	<input type="button" value=" 关 闭 " class="button" onclick="closeWindow();"/>
 </div>
</form>
</body>
</html>