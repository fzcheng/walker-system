<script type="text/javascript">
<!--
function clickRadio(obj){
	v = obj.value;
	$("#tr_fix").hide();
	$("#tr_scope_tree").hide();
	$("#tr_scope_list").hide();
	if(v == "SingleGivenActor"){
		$("#tr_1").show();
		$("#tr_2").hide();
		$("#tr_3").hide();
		$("#tr_4").hide();
		$("#tr_5").hide();
	} else if(v == "BusinessExpendActor"){
		$("#tr_1").hide();
		$("#tr_2").show();
		$("#tr_3").hide();
		$("#tr_4").hide();
		$("#tr_5").hide();
	} else if(v == "SelectScopeAndFixActor"){
		$("#tr_1").hide();
		$("#tr_2").hide();
		$("#tr_3").show();
		$("#tr_4").hide();
		$("#tr_5").hide();
		$("#tr_fix").show();
	} else if(v == "SelectScopeTreeActor"){
		$("#tr_1").hide();
		$("#tr_2").hide();
		$("#tr_3").hide();
		$("#tr_4").show();
		$("#tr_5").hide();
		$("#tr_scope_tree").show();
	} else if(v == "SelectScopeListActor"){
		$("#tr_1").hide();
		$("#tr_2").hide();
		$("#tr_3").hide();
		$("#tr_4").hide();
		$("#tr_5").show();
		$("#tr_scope_list").show();
	}
}


/* 以下为参与者列表选择、树操作方法 */
var actorScopeTree = null;
var actorFixTree   = null;

/**
 * 选择参与者树形范围列表
 */
function changeScopeTree(){
	var beanId = $("#SelectScopeTreeActor").val();
	if(beanId == ""){
		$("#tr_scope_tree").hide();
		return;
	}
	$("#tr_scope_tree").show();
	
	if(actorScopeTree != null){
		console.log("销毁之前的树对象");
		actorScopeTree.destroy();
	}
	_params = {"beanId":beanId};
	requestAjax("${ctx}/permit/flow/actor/get_scope_tree.do", _params, function(msg){
		if(msg != "failed"){
			alert(msg);
			actorScopeTree = initSimpleZtree("actorScopeTree", msg);
		} else {
			alert(msg);
		}
	});
}

function changeScopeList(){
	var beanId = $("#SelectScopeListActor").val();
	if(beanId == ""){
		$("#tr_scope_list").hide();
		return;
	}
	$("#tr_scope_list").show();
	_params = {"beanId":beanId};
	requestAjax("${ctx}/permit/flow/actor/get_scope_list.do", _params, function(msg){
		if(isEmptyValue(msg)){
			$("#scopeListAvaliableUser").empty();
			return;
		}
		if(msg != "failed"){
			users = msg.split(",");
			for(var i=0; i<users.length; i++){
				user = users[i].split("&");
				$("#scopeListAvaliableUser").append("<option value='" + user[0] + "'>" + user[1] + "</option>");
			}
		} else {
			alert(msg);
		}
	});
}

/* 点击固定人员树节点回调方法 */
var clickFixTreeCall = function clickFixTreeNode(e, treeId, treeNode){
	id = treeNode.id;
	if(isEmptyValue(id)){
		alert("机构ID不存在");
		return;
	}
	beanId = $("#SelectScopeAndFixActor").val();
	_params = {"deptId":id, "beanId":beanId};
	requestAjax("${ctx}/permit/flow/actor/get_fix_list.do", _params, function(msg){
		if(isEmptyValue(msg)){
			$("#fixAvaliableUser").empty();
			return;
		}
		// 清空旧数据
		$("#fixAvaliableUser").empty();
		if(msg != "failed"){
			console.log("......." + msg);
			users = msg.split(",");
			for(var i=0; i<users.length; i++){
				user = users[i].split("&");
				$("#fixAvaliableUser").append("<option value='" + user[0] + "'>" + user[1] + "</option>");
			}
		} else {
			alert(msg);
		}
	});
};

function changeFixTree(){
	var beanId = $("#SelectScopeAndFixActor").val();
	if(beanId == ""){
		$("#tr_fix").hide();
		return;
	}
	$("#tr_fix").show();
	
	if(actorFixTree != null){
		console.log("销毁之前的树对象");
		actorFixTree.destroy();
	}
	_params = {"beanId":beanId};
	requestAjax("${ctx}/permit/flow/actor/get_fix_tree.do", _params, function(msg){
		if(msg != "failed"){
			//alert(msg);
			actorFixTree = doCreateFixTree("treeDemo", msg, clickFixTreeCall);
		} else {
			alert(msg);
		}
	});
}

function doCreateFixTree(id, zNodes, _callback){
	var setting = {
		view: {
				dblClickExpand: true
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "pid",
					rootPId: "root"
				}
			},
			callback: {
				onClick: _callback//单击节点回调方法
			}
		};
	//var zNodes = '${departSet}';
	zNodes = eval("(" + zNodes + ")");
	tree = $.fn.zTree.init($("#"+id), setting, zNodes);
	return tree;
}

/* 从左边选到到右边 */
function fixSelectToRight(){
	selectVal = $("#fixAvaliableUser").val();
	selectText= $("#fixAvaliableUser").find("option:selected").text();
	if(isEmptyValue(selectVal)){
		//alert("请选择一个记录");
		return;
	}
	// 如果重复，不再添加
	flag = false;
	$("#fixSelectedUser option").each(function(){
		if($(this).val() == selectVal){
			flag = true;
			return;
		}
	});
	if(!flag){
		$("#fixSelectedUser").append("<option value='" + selectVal + "'>" + selectText + "</option>");
	}
}
/* 从右边删除 */
function fixSelectToLeft(){
	selectVal = $("#fixSelectedUser").val();
	if(isEmptyValue(selectVal)){
		return;
	}
	//index = $("#fixSelectedUser").get(0).selectedIndex;
	//console.log("------" + index);
	$("#fixSelectedUser option[value='" + selectVal + "']").remove();
}
//-->
</script>
<table border="0" cellpadding="0" cellspacing="0" width="450px" class="table-form">
 	 <tr>
  	 <td class="showTitle">模型类型：</td>
  	 <td>
  	 	 <#if (start == "1")>
  	 	   <input type="radio" name="actoryType" onclick="clickRadio(this);" value="SingleGivenActor" checked="checked"/>单人参与者：特定的一个人<br>
	 		   <input type="radio" name="actoryType" onclick="clickRadio(this);" value="BusinessExpendActor"/>从业务扩展中选择&nbsp;&nbsp;<br>
	 		   <input type="radio" name="actoryType" onclick="clickRadio(this);" value="SelectScopeAndFixActor"/>指定系统中特定用户&nbsp;&nbsp;<br>
  	 	 <#else>
		 		 <input type="radio" name="actoryType" onclick="clickRadio(this);" value="SingleGivenActor" checked="checked"/>单人参与者：特定的一个人<br>
		 		 <input type="radio" name="actoryType" onclick="clickRadio(this);" value="BusinessExpendActor"/>从业务扩展中选择&nbsp;&nbsp;<br>
		 		 <input type="radio" name="actoryType" onclick="clickRadio(this);" value="SelectScopeAndFixActor"/>指定系统中特定用户&nbsp;&nbsp;<br>
		 		 <input type="radio" name="actoryType" onclick="clickRadio(this);" value="SelectScopeTreeActor" disabled="disabled"/>从树节点中选择范围&nbsp;&nbsp;<br>
		 		 <input type="radio" name="actoryType" onclick="clickRadio(this);" value="SelectScopeListActor"/>从列表中选择范围&nbsp;&nbsp;<br>
  	 	 </#if>
  	 </td>
   </tr>
 	 <tr id="tr_1">
  		<td class="showTitle">可配置的参与者：</td>
  		<td>
  			<select id="SingleGivenActor" name="SingleGivenActor" style="width:300px;" 
  				title="这些都是自定义参与者实现，直接配置在spring环境中即可。">
	 				<option value="" selected="selected">== 请选择 ==</option>
  				<#list SingleGivenActor?keys as k>
  				<#if (k == actor_bean)>
	 				<option value="${k}" selected="selected">${SingleGivenActor[k]}-${k}</option>
  				<#else>
	 				<option value="${k}">${SingleGivenActor[k]}-${k}</option>
  				</#if>
  				</#list>
	 			</select>
  		</td>
  	</tr>
  	<tr id="tr_2" style="display:none;">
  		<td class="showTitle">可配置的参与者：</td>
  		<td>
  			<select id="BusinessExpendActor" name="BusinessExpendActor" style="width:300px;" 
  				title="这些都是自定义参与者实现，直接配置在spring环境中即可。">
  				<option value="" selected="selected">== 请选择 ==</option>
  				<#list BusinessExpendActor?keys as k>
  				<#if (k == actor_bean)>
	 				<option value="${k}" selected="selected">${BusinessExpendActor[k]}-${k}</option>
  				<#else>
	 				<option value="${k}">${BusinessExpendActor[k]}-${k}</option>
	 				</#if>
  				</#list>
	 			</select>
  		</td>
  	</tr>
  	<tr id="tr_3" style="display:none;">
  		<td class="showTitle">可配置的参与者：</td>
  		<td>
  			<select id="SelectScopeAndFixActor" name="SelectScopeAndFixActor" style="width:300px;" 
  				title="这些都是自定义参与者实现，直接配置在spring环境中即可。" onchange="changeFixTree();">
	 				<option value="" selected="selected">== 请选择 ==</option>
  				<#list SelectScopeAndFixActor?keys as k>
  				<#if (k == actor_bean)>
	 				<option value="${k}" selected="selected">${SelectScopeAndFixActor[k]}-${k}</option>
  				<#else>
	 				<option value="${k}">${SelectScopeAndFixActor[k]}-${k}</option>
	 				</#if>
  				</#list>
	 			</select>
  		</td>
  	</tr>
  	<tr id="tr_4" style="display:none;">
  		<td class="showTitle">可配置的参与者：</td>
  		<td>
  			<select id="SelectScopeTreeActor" name="SelectScopeTreeActor" style="width:300px;" 
  				title="这些都是自定义参与者实现，直接配置在spring环境中即可。" onchange="">
	 				<option value="" selected="selected">== 请选择 ==</option>
  				<#list SelectScopeTreeActor?keys as k>
  				<#if (k == actor_bean)>
	 				<option value="${k}" selected="selected">${SelectScopeTreeActor[k]}-${k}</option>
  				<#else>
	 				<option value="${k}">${SelectScopeTreeActor[k]}-${k}</option>
	 				</#if>
  				</#list>
	 			</select>
  		</td>
  	</tr>
  	<tr id="tr_5" style="display:none;">
  		<td class="showTitle">可配置的参与者：</td>
  		<td>
  			<select id="SelectScopeListActor" name="SelectScopeListActor" style="width:300px;" 
  				title="这些都是自定义参与者实现，直接配置在spring环境中即可。" onchange="changeScopeList();">
	 				<option value="" selected="selected">== 请选择 ==</option>
  				<#list SelectScopeListActor?keys as k>
  				<#if (k == actor_bean)>
	 				<option value="${k}" selected="selected">${SelectScopeListActor[k]}-${k}</option>
  				<#else>
	 				<option value="${k}">${SelectScopeListActor[k]}-${k}</option>
	 				</#if>
  				</#list>
	 			</select>
  		</td>
  	</tr>
   <tr id="tr_fix" style="display:none;">
  	 <td class="showTitle" width="150px">选择固定人员：</td>
  	 <td width="300px">
  	 	 <table border="0" cellpadding="0" cellspacing="0" width="100%">
  	 	 	 <tr>
  	 	 	 	 <td>参与者数据来源</td>
  	 	 	 	 <td>可选择用户：</td>
  	 	 	 	 <td>&nbsp;</td>
  	 	 	 	 <td>已选择用户：</td>
  	 	 	 </tr>
  	 	 	 <tr>
  	 	 	   <td width="160px" valign="top">
			  		 <div id="treeDemo" class="ztree" style="width:160px; height:200px; overflow-x:hidden; overflow-y:scroll"></div>
  	 	 	   </td>
  	 	 	   <td width="90px" valign="top">
  	 	 	   	 <select multiple="multiple" style="width:100px;height:200px;" id="fixAvaliableUser" name="fixAvaliableUser">
			  		 </select>
  	 	 	   </td>
  	 	 	   <td width="60px" height="200px" valign="middle" align="center" style="padding-top:6px;">
  	 	 	   	 <div>
	  	 	 	   	 <input type="button" value=" >> " class="button" onclick="fixSelectToRight();"/><br><br>
	  	 	 	   	 <input type="button" value=" << " class="button" onclick="fixSelectToLeft();"/>
  	 	 	   	 </div>
  	 	 	   </td>
  	 	 	   <td valign="top">
	  	 	 	   <select multiple="multiple" style="width:100px;height:200px;" id="fixSelectedUser" name="fixSelectedUser">
	  	 	 	   	 <#if (actor_type == "SelectScopeAndFixActor")>
	  	 	 	   	 <#list actor_value?keys as k>
	  	 	 	   	 <option value="${k}">${actor_value[k]}</option>
	  	 	 	   	 </#list>
	  	 	 	   	 </#if>
			  		 </select>
  	 	 	   </td>
  	 	 	 </tr>
  	 	 </table>
  	 </td>
   </tr>
   <tr id="tr_scope_tree" style="display:none;">
  	 <td class="showTitle" width="150px">从树节点中选择一个范围：</td>
  	 <td width="300px">
  		 <div id="actorScopeTree" class="ztree" style="width:160px; height:200px; overflow-x:hidden; overflow-y:scroll"></div>
  		 <div><input type="checkbox" name="man" value=""/>张三<br></div>
  		 <select multiple="multiple">
  			 <option value="0">李四</option>
  		 </select>
  	 </td>
   </tr>
   <tr id="tr_scope_list" style="display:none;">
   	<td class="showTitle" width="150px">从列表中选择一个范围：</td>
   	<td width="300px">
   	  <table border="0" cellpadding="0" cellspacing="0" width="100%">
  	 	 	 <tr>
  	 	 	 	 <td>可选择对象：</td>
  	 	 	 	 <td>&nbsp;</td>
  	 	 	 	 <td>已选择对象：</td>
  	 	 	 </tr>
  	 	 	 <tr>
  	 	 	   <td width="90px" valign="top">
  	 	 	   	 <select multiple="multiple" style="width:160px;height:200px;" id="scopeListAvaliableUser" name="scopeListAvaliableUser">
			  		 </select>
  	 	 	   </td>
  	 	 	   <td width="60px" height="200px" valign="middle" align="center" style="padding-top:6px;">
  	 	 	   	 <div>
	  	 	 	   	 <input type="button" value=" >> " class="button" onclick="listSelectToRight();"/><br><br>
	  	 	 	   	 <input type="button" value=" << " class="button" onclick="listSelectToLeft();"/>
  	 	 	   	 </div>
  	 	 	   </td>
  	 	 	   <td valign="top">
	  	 	 	   <select multiple="multiple" style="width:160px;height:200px;" id="scopeListSelectedUser" name="scopeListSelectedUser">
			  		   <#if (actor_type == "SelectScopeListActor")>
	  	 	 	   	 <#list actor_value?keys as k>
	  	 	 	   	 <option value="${k}">${actor_value[k]}</option>
	  	 	 	   	 </#list>
	  	 	 	   	 </#if>
			  		 </select>
  	 	 	   </td>
  	 	 	 </tr>
  	 	 </table>
   	</td>
   </tr>
 </table>
 <script>
function listSelectToRight(){
	selectVal = $("#scopeListAvaliableUser").val();
	selectText= $("#scopeListAvaliableUser").find("option:selected").text();
	if(isEmptyValue(selectVal)){
		//alert("请选择一个记录");
		return;
	}
	// 如果已经存在一个，不再添加，仅支持添加一个数据
	flag = false;
	$("#scopeListSelectedUser option").each(function(){
		if($(this).val() != ""){
			flag = true;
			return;
		}
	});
	if(!flag){
		$("#scopeListSelectedUser").append("<option value='" + selectVal + "'>" + selectText + "</option>");
	}
}
function listSelectToLeft(){
	selectVal = $("#scopeListSelectedUser").val();
	if(isEmptyValue(selectVal)){
		return;
	}
	//index = $("#fixSelectedUser").get(0).selectedIndex;
	//console.log("------" + index);
	$("#scopeListSelectedUser option[value='" + selectVal + "']").remove();
}
 </script>