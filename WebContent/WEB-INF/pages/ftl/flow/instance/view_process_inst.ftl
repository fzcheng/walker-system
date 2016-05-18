<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>查看流程实例信息</title>
<link type="text/css" rel="stylesheet" href="${ctx}/style/${style}/css.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
</head>
<body>

	<table border="0" cellpadding="0" cellspacing="0" width="99%" class="table-form">
		<tr class="title">
  		<td colspan="4">流程实例信息</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="120px">(<span class="required">*</span>)流程实例ID：</td>
  		<td width="300px">${process.id}</td>
  		<td class="showTitle" width="120px">(<span class="required">*</span>)流程名称：</td>
  		<td>${process.name}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)使用流程模板：</td>
  		<td>${process.processTemplate}</td>
  		<td class="showTitle">(<span class="required">*</span>)创建时间：</td>
  		<td>${process.showCreateTime}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)创建用户ID：</td>
  		<td>${process.creatorId}</td>
  		<td class="showTitle">(<span class="required">*</span>)创建用户名字：</td>
  		<td>${process.creatorName}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)是否结束：</td>
  		<td>
  			<#if (process.end == 1)>
  			已结束
  			<#else>
  			<label style="color:green;">运行中...</label>
  			</#if>
  		</td>
  		<td class="showTitle">结束时间：</td>
  		<td>${process.showEndTime}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)是否终止：</td>
  		<td>
  			<#if (process.terminate == 1)>
  			<label style="color:red;">流程被用户终止</label>
  			<#else>
  			未被终止
  			</#if>
  		</td>
  		<td class="showTitle">终止时间：</td>
  		<td>${process.showTerminateTime}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)是否暂停：</td>
  		<td>
  			<#if (process.pause == 1)>
  			<label style="color:red;">流程被用户暂停</label>
  			<#else>
  			未被暂停
  			</#if>
  		</td>
  		<td class="showTitle">暂停时间：</td>
  		<td>${process.showPauseTime}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)关联业务ID：</td>
  		<td>${process.businessId}</td>
  		<td class="showTitle">业务类别：</td>
  		<td>${process.businessType}</td>
  	</tr>
  	<tr>
  		<td class="showTitle">(<span class="required">*</span>)是否子流程：</td>
  		<td>
  			<#if (process.subProcess == 1)>
  			子流程
  			<#else>
  			主流程
  			</#if>
  		</td>
  		<td class="showTitle">对应的主流程：</td>
  		<td>${process.globalProcessInst}</td>
  	</tr>
 </table>
 
 <table cellspacing="0" cellpadding="0" class="table-box" id="tbl" width="99%">
 		<tr class="table-tit">
 			<td colspan="5">业务关联参数(创建流程时提交的参数)</td>
 		</tr>
	  <tr class="table-tit">
	    <td width="50px">序号</td>
	    <td width="140px">创建时间</td>
	    <td width="130px">参数名称</td>
	    <td>参数值</td>
	    <td width="100px">参数类型</td>
	  </tr>
	  <#assign inx = 1>
	  <#list bizDataList as bizData>
	  <tr class="table-date">
	  	<td>${inx}</td>
	  	<td>${bizData.createTime}</td>
	  	<td>${bizData.name}</td>
	  	<td>${bizData.value}</td>
	  	<td>${bizData.dataType}</td>
	  </tr>
	  <#assign inx = inx+1>
	  </#list>
</table>

<!-- 
 <table border="0" cellpadding="0" cellspacing="0" width="99%" class="table-form">
		<tr class="title">
  		<td colspan="4">业务关联参数(创建流程时提交的参数)</td>
  	</tr>
  	<tr>
  		<td class="showTitle" width="120px">(<span class="required">*</span>)流程实例ID：</td>
  		<td width="300px">${process.id}</td>
  		<td class="showTitle" width="120px">(<span class="required">*</span>)流程名称：</td>
  		<td>${process.name}</td>
  	</tr>
 </table>
 -->
</body>
</html>