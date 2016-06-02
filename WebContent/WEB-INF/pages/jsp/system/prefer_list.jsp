<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title>参数配置</title>
  <link rel="stylesheet" type="text/css" href="${ctx}/style/public.css">
  <link rel="stylesheet" type="text/css" href="${ctx}/script/dhtml/grid/dhtmlxgrid.css"/>
  
  <script type="text/javascript" src="${ctx}/script/public.js"></script>
  <script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="${ctx}/script/dhtml/tree/dhtmlxcommon.js"></script>

<script type="text/javascript" src="${ctx}/script/dhtml/grid/dhtmlxgrid.js"></script>		
<script type="text/javascript" src="${ctx}/script/dhtml/grid/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="${ctx}/script/dhtml/grid/dhtmlxgrid_pgn.js"></script>
  <script type="text/javascript">
  	//
  	var oldPreferValue;
  	window.dhx_globalImgPath="${ctx}/script/dhtml/imgs/";
  
  	/*初始化列表*/
	function initGrid(){
		
		//showSkyLoading("数据加载中，请稍候......");
	
		mygrid = new dhtmlXGridObject("gridbox");
		mygrid.setImagePath("../../script/dhtml/grid/imgs/");
		mygrid.setHeader("配置ID, 参数描述, 参数类型, 参数值, 默认值");
		mygrid.setInitWidths("150,280,80,150,*");
		mygrid.setColAlign("left,left,center,left,left");
		mygrid.setColTypes("ro,ro,ro,ed,ro");
		mygrid.enableAutoHeight(true, "350");
		
		mygrid.attachEvent("onEditCell",doOnCellEdit);
		mygrid.init();
		mygrid.setSkin("gray");
		mygrid.enablePaging(true,12,5,"pagingArea",true,"recinfoArea");
		mygrid.loadXML("${ctx}/permit/admin/preference/loadAllPrefer.do",ajustDms,"xml");
	}
	
	function ajustDms(){
		//hideSkyLoading();
		ajustFrameDms();
	}
	
	function doOnCellEdit(stage,rowId,cellInd){
		if(stage == 1){
			oldPreferValue = mygrid.cells(rowId, cellInd).getValue();
		}
		if(stage == 2){
			//从rowId中取出“数据类型”
			var _s = rowId.split(",");
			var preferId = _s[0];
			var dataType = _s[1];
			var modText = mygrid.cells(rowId, cellInd).getValue();
			//alert("rowId=" + _s[1] + ",用户确定了修改内容:" + modText);
			
			if(modText == null || modText == ""){
				alert("请输入要修改的参数内容！");
				return false;
			}
			if(dataType == 0){
				//字符类型
				
			} else if(dataType == 1){
				// /^[1-9]+[0-9]*]*$/ 整数
				if(rowId == "SMS_FEEDBACK,1"){
					if(modText != "0" && modText != "1"){
						alert("请输入“0”或“1”");
						return false;
					}
				}else{
					if(!/^[1-9]+[0-9]*]*$/.test(modText)){
						alert("请输入正整数");
						return false;
					}
				}
				
			} else if(dataType == 2){
				//浮点型
				if(!/^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$/.test(modText)){
					alert("请输入浮点类型的小数!");
					return false;
				}
			} else {
				//布尔型
			}
			if(oldPreferValue == modText){
				//没有修改数据，不保存
				oldPreferValue = null;
				return false;
			}
			//提交更新数据
			$.ajax({
				url:"${ctx}/admin/preference/updatePrefer.do",
				type:"post",
				data:"preferId=" + preferId + "&modText=" + modText,
				success:function(msg){
					alert(msg);
				}
			});
		}
		return true;
	}
  </script>
  </head>
  
  <body onload="initGrid()">
<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin:0 0 6 0;">
	<tr>
		<td class="toolbar">
			<span>系统参数配置</span>
			【双击“参数值”单元格可编辑】
		</td>
	</tr>
</table>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<!--<tr><td><div id="loading"></div></td></tr>
	-->
	<tr>
		<td width="100%" valign="top">
			<div id="gridbox" style="background-color:white;overflow:hidden"></div>
		</td>
	</tr>
	<tr>
		<td valign="middle">
			<table>
				<tr>
					<td id="pagingArea" class="paging"></td>
					<td id="recinfoArea" class="recinfo"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

  </body>
</html>
