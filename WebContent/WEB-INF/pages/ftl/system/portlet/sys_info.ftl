<script type="text/javascript">
<!--

//-->
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="table-box">
 	<tr class="table-tit">
 		<td colspan="2" class="portal-logo"><img src="${ctx}/images/window-logo.png">&nbsp;&nbsp;${sysInfoPortlet.title}</td>
 	</tr>
 	<tr class="table-date">
 		<td width="120px" align="right" class="portal-td">操作系统：</td>
 		<td>${sysInfo.osName}&nbsp;${sysInfo.osVersion}</td>
 	</tr>
 	<tr class="table-date">
 		<td align="right" class="portal-td">JDK版本：</td>
 		<td>${sysInfo.jdkVersion}</td>
 	</tr>
 	<tr class="table-date">
 		<td align="right" class="portal-td">内存：</td>
 		<td>(可用)${sysInfo.physicalMemoryFree}&nbsp;/&nbsp;(总)${sysInfo.physicalMemoryTotal}</td>
 	</tr>
 	<tr class="table-date">
 		<td align="right" class="portal-td">临时文件夹：</td>
 		<td>${sysInfo.tempDir}</td>
 	</tr>
 	<tr class="table-date">
 		<td align="right" class="portal-td">可用磁盘空间：</td>
 		<td>
 			<#list sysInfo.discSpaces?keys as k>
 			磁盘${k}：${sysInfo.discSpaces[k]}<br>
 			</#list>
 		</td>
 	</tr>
</table>
