<html>
<head>
<title>应用程序部署初始化</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link href="${ctx}/style/simple/css.css" type="text/css" rel="stylesheet">
<link href="${ctx}/style/simple/layer.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>
<script type="text/javascript">

var loadi;

function updateDeploy(){
	loadi = layer.load('正在部署程序模块包，请稍后...');
	requestAjax("${ctx}/update_deploy.do", null, function(data){
		layer.close(loadi);
		if(data == "success"){
			//stringToJson(flowCallback);
			window.top.location.href = "${ctx}/login.do";
		} else {
			showErrorTip(data);
		}
	});
}
</script>
</head>
  
<body>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td align="center">
			
<table border="0" cellpadding="0" cellspacing="0" width="500px" class="table-box" style="width:500px;margin-top:30px;">
 	<tr class="table-tit">
 		<td class="portal-logo" colspan="3" align="left"><img src="${ctx}/images/window-logo.png">&nbsp;&nbsp;应用程序部署初始化：</td>
 	</tr>
 	<tr class="table-date">
 		<td class="portal-td" height="60px" valign="top" colspan="3">
 		以下为系统检测到的应用程序功能包：<br>
 		1、如果是首次部署，需要点击“部署应用程序模块”按钮。<br>
 		2、如果您已经成功部署完毕，也可以“重新部署”单个压缩文件。
 		</td>
 	</tr>
 	<#if (deployedMap?size > 0)>
 	<tr class="table-date">
 		<td class="portal-td" height="30px" valign="top" colspan="3">
 			已经部署成功的功能包：
 		</td>
 	</tr>
 	<#list deployedMap?keys as k>
 	<tr class="table-date">
		<td width="50px" class="portal-td">#</td>
		<td>${k}</td>
		<td width="150px">${deployedMap[k]?number_to_datetime}</td>
 	</tr>
 	</#list>
 	</#if>
 	
 	<#if (waitList?size > 0)>
 	<tr class="table-date">
 		<td class="portal-td" height="30px" valign="top" colspan="3">
 			尚未部署的功能包：
 		</td>
 	</tr>
 	<#list waitList as file>
 	<tr class="table-date">
		<td width="50px" class="portal-td">#</td>
		<td>${file.name}</td>
		<td width="150px">未部署</td>
 	</tr>
 	</#list>
 	</#if>
</table>
<#if (editable)>
<div style="width:500px; align:center; margin-top:10px;">
	<input type="button" class="button-tj" value="部署应用程序模块" style="width:160px;" onclick="updateDeploy();"/>
</div>
</#if>
		</td>
	</tr>
</table>

</body>
</html>