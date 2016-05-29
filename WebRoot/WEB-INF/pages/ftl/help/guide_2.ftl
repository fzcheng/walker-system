<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统帮助-开发指南</title>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/script/lib/ztree/zTreeStyle.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/downTree.js"></script>
<script type="text/javascript">
function next(id){
	window.location.href = "${ctx}/permit/help/index.do?pageId="+id;
}

</script>
</head>
<body>
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>系统开发帮助指南--功能调用API</span>
		</td>
	</tr>
</table>
<table id="mytable" cellspacing="0" cellpadding="0" class="table-form">  
  <tr class="title">  
    <td width="200px">帮助指南-标题</td>  
 		<td>说明与描述</td>  
  </tr>
  <tr>
  	<td>编写业务ACTION</td>
  	<td>
  	业务ACTION如果继承系统SystemAction可以调用基类的诸多方法。<br>
  	</td>
  </tr>
  <tr>
  	<td>获取登录用户</td>
  	<td>
  	this.getCurrentUser()或者this.getCurrentUserDetails()
  	</td>
  </tr>
  <tr>
  	<td>获得登录用户类型</td>
  	<td>
  	this.getCurrentUserType(),返回用户类型枚举值：UserType
  	</td>
  </tr>
  <tr>
  	<td>写入系统日志</td>
  	<td>
  	this.systemLog("日志内容")或者this.systemLog("内容", LogType.XXX);
  	</td>
  </tr>
  <tr>
  	<td>设置功能点权限</td>
  	<td>
  	this.setUserPointers(model)，调用此方法后，系统会把当前用户点击的菜单所具有的功能点信息返回给模板，模板可以检查该显示那些按钮。
  	</td>
  </tr>
  <tr>
  	<td>获得机构类型</td>
  	<td>
  	this.getOrgType(String orgId),根据机构ID返回其机构类型枚举值：OrgType
  	</td>
  </tr>
  <tr>
  	<td>获得配置的系统选项值</td>
  	<td>
  	this.getArgumentsValue(MyArgumentsNames name);<br>
  	参数名是枚举值，参数的KEY需要定义在MyArgumentsNames中，返回的是Object对象，业务根据参数本身的类型来强制转换。
  	</td>
  </tr>
  <tr>
  	<td>获得用户、机构缓存对象</td>
  	<td>
  	SystemAction中配置有用户、机构缓存对象，可以直接调用，不过建议在基类中编写更多的调用方法，让业务调用更加方便。
  	</td>
  </tr>
  <tr>
  	<td>获取整个组织机构下拉树</td>
  	<td>选择机构：
  	<input id="departId" name="departId" type="hidden" value=""/>
    <input id="departName" name="departName" type="text" size="15" readonly="readonly" style="width:150px;" class="text"/>
  	</td>
  </tr>
  <tr>
  	<td>获得一个代码表下拉树</td>
  	<td>
  	选择区域：
  	<input id="codeId" name="codeId" type="hidden" value="" />
    <input id="codeName" name="codeName" type="text" size="15" readonly="readonly" style="width:150px;" class="text"/>
  	</td>
  </tr>
</table>
<div style="margin-top:5px;">
</div>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
		<td align="center">
			<input type="button" value=" &lt;上一页 " onclick="next('1');" class="button"/>&nbsp;&nbsp;
			2/3&nbsp;&nbsp;
			<input type="button" value=" 下一页&gt; " onclick="next('3');" class="button"/>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
var zNodes = '${departSet}';
console.log(zNodes);
zNodes = stringToJson(zNodes);
//var zNodes = [{id:1,name:"新开普电子科技有限公司",superId:0},{departId:2,name:"产品研发中心",superId:1},{departId:3,name:"软件技术研究部",superId:2}];
$("#departName").initDownTree({
	 idInputName:"departId",
	 parentSelLimit:true, //父节点不能被选择
   zNodes:zNodes
});

//----------代码下拉树
var codeNodes = '${codeSet}';
codeNodes = eval("(" + codeNodes + ")");
$("#codeName").initDownTree({
	 idInputName:"codeId",
   parentSelLimit:true, //父节点不能被选择
   zNodes:codeNodes
});
</script>
<#include "/common/footer.ftl">
</html>