/**
 * 代码选择相关的操作定义。
 * @author shikeying
 * @date 2014-8-26
 */

/* 定义点击代码树的回调函数 */
var clickIndustryTree = function(e, treeId, treeNode){
	if(treeNode.isParent){
		// 如果是父节点，不能点击
		return;
	}
	_selectId = treeId.substring(0, treeId.length-4);
	//console.log("+++++++++ " + _selectId);
	
	// 如果重复，不再添加
	flag = false;
	$("#" + _selectId + " option").each(function(){
		if($(this).val() == treeNode.id){
			flag = true;
			return;
		}
	});
	if(!flag){
		$("#"+_selectId).append("<option selected='selected' value='" + treeNode.id + "'>" + treeNode.name + "</option>");
	}
};

/**
 * 创建一个代码(多选)树对象，支持点击叶子节点后，把选择的项放到左边对应的select组件中。</p>
 * 注意：这里面有个隐含规则，select的ID和tree的ID是对应的，即：treeId = selectId + "Tree"
 * @param treeId
 * @param datas
 * @returns
 */
function createMultiSelectCodeTree(treeId, datas){
	return doInitSimpleZtree0(treeId, datas, clickIndustryTree);
}

/**
 * 从select组件中移除选择的项，双击鼠标。
 * @param select
 */
function _removeSelect(select){
	id = select.id;
	if(isEmptyValue(id)){
		throw new Exception("id is required!");
	}
	_selectVal = $("#"+id).val();
	if(isEmptyValue(_selectVal)){
		return;
	}
	$("#" + id + " option[value='" + _selectVal + "']").remove();
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// 下拉单选树
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
var setting = {
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	view: {
		dblClickExpand: true
	},
	data: {
		key: {
			name: "name"
		},
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: "0"
		}
	},
	callback: {
		onClick: _singleCodeClick,
		onCheck: _singleCodeCheck
	}
};
function _singleCodeClick(e, treeId, treeNode) {
	if(treeNode == null || treeNode.isParent){
		return false;
	}
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	_hideCodePopTree(treeId);
	return false;
}

/**
 * 点击代码树的某个节点，在文本框中显示选择内容。
 * @param e
 * @param treeId
 * @param treeNode
 */
function _singleCodeCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	nodes = zTree.getCheckedNodes(true);
	v = "";
	s = "";
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		s += nodes[i].id + ",";
	}
	if (v.length > 0 ) v = v.substring(0, v.length-1);
	if (s.length > 0 ) s = s.substring(0, s.length-1);
	// 动态判断选择输入框名字
	_id = treeId.substring(0, treeId.length-4);
	_name = _id + "Name";
	// 设置显示名称
	var cityObj = $("#"+_name);
	cityObj.attr("value", v);
	
	// 设置隐藏域的值
	var hideObj = $("#"+_id);
	hideObj.attr("value", s);
}

function showSingleCodeTree(obj) {
	_name = obj.id; // locationName
	_id = _name.substring(0, _name.length-4); // location
	
	_popName = "#" + _id + "MenuContent";
	if($(_popName).css("display") != "none"){
		// 如果再次点击选择框，就隐藏弹出菜单
		_hideCodePopTree(_id+"Tree");
		return;
	}
	
	var cityObj = $("#"+_name);
	//var cityOffset = $("#locationName").offset();
	var cityPosition = $("#"+_name).position();
	//console.log("========" + cityOffset.top + ", " + (cityOffset.top + cityObj.outerHeight()));
	//$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + "px"}).slideDown("fast");
	$("#"+_id+"MenuContent").css({left:cityPosition.left + "px", top:cityPosition.top + cityObj.outerHeight() + "px"}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
/**
 * 隐藏弹出树窗口
 * @param id 树的ID
 */
function _hideCodePopTree(id) {
	if(!isEmptyValue(id)){
		_inx = id.indexOf("Tree");
		if(_inx > 0){
			_id = id.substring(0, _inx);
			console.log("hide id = " + _id);
			$("#"+_id+"MenuContent").fadeOut("fast");
		} else {
			console.log("没有点击tree对象");
		}
	} else {
		// 点击界面，但没有点击任何可用组件
		$(":contains('MenuContent')").each(function(index, obj){
			_id2 = obj.id;
			if(!isEmptyValue(_id2) && _id2.indexOf("MenuContext") > 0){
				$(obj).fadeOut("fast");
			}
		});
	}
	//$("#locationMenuContent").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	console.log("......." + event.target.id + ", " + ($(event.target).parents("#locationMenuContent").length));
	_id = event.target.id;
	if(!isEmptyValue(_id)){
		_inx = _id.indexOf("Tree");
		if(_inx > 0){
			// 如果点击了树的父节点，不隐藏窗口
			//hideMenu(_id);
			return;
		}
	} else {
		_hideCodePopTree("");
	}
	/*
	if (!(event.target.id == "menuBtn" || event.target.id == "locationName" || event.target.id == "locationMenuContent" || $(event.target).parents("#locationMenuContent").length>0)) {
		hideMenu(event.target.id);
	}*/
}

/**
 * 创建代码选择树，单选
 * @param treeId
 * @param datas
 */
function createSingleCodeTree(treeId, datas){
	$.fn.zTree.init($("#"+treeId), setting, stringToJson(datas));
}
