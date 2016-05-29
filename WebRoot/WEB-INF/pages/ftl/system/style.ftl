<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的风格</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<style>
.img_border {
	border: 1px solid #666666;
	width:300px; height:200px;
	cursor:pointer;
}
</style>

<!-- 
<script type="text/javascript" src="${ctx}/script/lib/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
 -->

<script type="text/javascript">
function switchStyle(styleName){
	window.top.location = "${ctx}/permit/saveStyle.do?style=" + styleName;
}
</script>
</head>
<body>
<table width="90%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="300px" style="padding:10px;">
			<img alt="简约" src="${ctx}/images/style/simple.jpg" class="img_border" onclick="switchStyle('simple');">
		</td>
		<td width="300px" style="padding:10px;">
			<img alt="绿色" src="${ctx}/images/style/green.jpg" class="img_border" onclick="switchStyle('green');">
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="center">简约</td>
		<td align="center">绿色</td>
		<td>&nbsp;</td>
	</tr>
</table>

</body>
<#include "/common/footer.ftl">
</html>