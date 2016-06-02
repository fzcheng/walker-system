
function reinitIframe(){
  var iframe = document.getElementById("frame1");
  try{
  var bHeight = iframe.contentWindow.document.body.scrollHeight;
  var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
  var height = Math.max(bHeight, dHeight);
  iframe.height =  height;
  }catch (ex){}
}

/**
 * 修改应用整体布局中最外层ifame的宽度
 * @param obj
 */
function setMainFrameWidth(obj){
	
}

function SetSkyWinHeight(obj){
	var win=obj;
	if (document.getElementById){
	  if (win && !window.opera){
	    if (win.contentDocument && win.contentDocument.body.offsetHeight){
	      win.height = win.contentDocument.body.offsetHeight; 
	    } else if (win.Document && win.Document.body.scrollHeight)
	      win.height = win.Document.body.scrollHeight;
	        
	    //if(win.contentDocument && win.contentDocument.body.offsetWidth){
	    	//win.width = win.contentDocument.body.offsetWidth;
	    //}else if(win.Document && win.Document.body.scrollWidth)
	      //win.width = win.Document.body.scrollWidth;
	    
	    if(win.height <= 360){
	    	win.height = 360;
	    } else {
//	    	alert(win.height);
	    }
	    document.getElementById("sampleframe").style.height = win.height;
	  }
	}
}

function getSkyPageSize(){
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
//		alert("...... window.innerHeight, yscroll = " + yScroll);
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac 
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
//		alert("...... document.body.scrollHeight, yscroll = " + yScroll);
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari 
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
//		alert("...... document.body.offsetWidth, yscroll = " + yScroll);
	} 
	var windowWidth = null;
	var windowHeight = null;
	if (window.innerHeight) { // all except Explorer 
		windowWidth = self.innerWidth;
		windowHeight = self.innerHeight;
//		alert("...... self.innerHeight, windowHeight = " + windowHeight);
	} 
//	else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode 
//		windowWidth = document.documentElement.clientWidth;
//		windowHeight = document.documentElement.clientHeight;
//		alert("...... document.documentElement, windowHeight = " + windowHeight);
//	} 
	else if ((document.body) && (document.body.clientHeight)) { // other Explorers 
		windowHeight = document.body.clientHeight;
		//通过深入Document内部对body进行检测，获取窗口大小
		if (document.documentElement && document.documentElement.clientHeight 
				&& document.documentElement.clientWidth){
			windowHeight = document.documentElement.clientHeight; 
			windowWidth = document.documentElement.clientWidth;
		}
//		alert("...... document.body, windowHeight = " + windowHeight);
	} 
	// for small pages with total height less then height of the viewport 
	if(yScroll < windowHeight){ 
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}
//	alert("yScroll = " + yScroll + ", windowHeight = " + windowHeight);
	// for small pages with total width less then width of the viewport 
	if(xScroll < windowWidth){ 
		pageWidth = windowWidth;
	} else { 
		pageWidth = xScroll;
	} 
	//alert("xScroll = " + xScroll + ", windowWidth = " + windowWidth);
	
	arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight);
	return arrayPageSize;
}

/**
 * 调整最外层系统iframe高度和宽度</p>
 * 该方法供子页面加载完成后调用，通常放在body之后，</br>
 * 如果是ajax异步刷新，在刷新后也可在回调中手工调用。
 */
function ajustDms(){
	//hideSkyLoading();
	ajustFrameDms();
}
/**
 * 调整主界面中左侧菜单高度
 */
function ajustMenuHeight(){
	//var pgDms = getSkyPageSize();
	//alert("页面高度: " + pgDms[1] + ", " + pgDms[3]);
	//$("#_menuContainer").height(pgDms[1]-84-10);
}

function hideSkyLoading(){
	var div = document.getElementById("loading");
	if(div != null){
		div.innerHTML = "";
	}
}

function ajustFrameDms(){
	var iframeObject = parent.document.getElementById("sampleframe");
    if(iframeObject != null){
    	iframeObject.height = "300";
    	//iframeObject.width = "600px";
    	var pgDms = getSkyPageSize();
    	
//    	alert("======= " + pgDms[1] + ", $(document).height() = " + $(document).height());
    	//alert("======= " + pgDms[0] + ", $(document).width() = " + $(document).width());
//      iframeObject.style.height = pgDms[1];
//    	iframeObject.style.width = pgDms[0];
    	if(pgDms[1] > 300){
    		iframeObject.height = pgDms[1]+20;
    	}
        //不能设置iframe宽度为固定值，否则在调整浏览器窗口时，内容宽度会固定而不会自动适应。
        //iframeObject.width = pgDms[0];
    	parent.ajustMenuHeight();
    }
}

function ajustFrameWidthAndHeight(){
	var iframeObject = parent.document.getElementById("sampleframe");
    if(iframeObject != null){
    	iframeObject.height = "300";
    	//iframeObject.width = "600px";
    	var pgDms = getSkyPageSize();
    	
//    	alert("======= " + pgDms[1] + ", $(document).height() = " + $(document).height());
    	//alert("======= " + pgDms[0] + ", $(document).width() = " + $(document).width());
//      iframeObject.style.height = pgDms[1];
//    	iframeObject.style.width = pgDms[0];
    	if(pgDms[1] > 300){
    		iframeObject.height = pgDms[1];
    	}
        //不能设置iframe宽度为固定值，否则在调整浏览器窗口时，内容宽度会固定而不会自动适应。
        iframeObject.width = pgDms[0];
    }
}

/*设置子页面中iframe高度，同时也要更新最外层框架Iframe高度*/
function setSubFrameHgt(obj){
	var win=obj;
	if (document.getElementById){
	  if (win && !window.opera){
	    if (win.contentDocument && win.contentDocument.body.offsetHeight){
	      win.height = win.contentDocument.body.offsetHeight; 
	    } else if (win.Document && win.Document.body.scrollHeight)
	      win.height = win.Document.body.scrollHeight;
	        
	  if(win.contentDocument && win.contentDocument.body.offsetWidth){
    	win.width = win.contentDocument.body.offsetWidth;
	  }else if(win.Document && win.Document.body.scrollWidth)
		  win.width = win.Document.body.scrollWidth;
	    
	    if(win.height <= 400){
	    	win.height = 400;
	    }
	    document.getElementById("monthFrame").style.height = win.height;
	    document.getElementById("monthFrame").style.width = win.width;
	    //更新外部主IFrame宽度
	    ajustFrameDms();
	  }
	}
}

//字符串的替换方法
String.prototype.replaceAll  = function(s1,s2){    
	return this.replace(new RegExp(s1,"gm"),s2);    
}
/*根据class得到元素集合*/
function getElementsByClassName(className,tagName){  
 var ele=[],all=document.getElementsByTagName(tagName||"*");  
 for(var i=0;i<all.length;i++){  
	 if(all[i].className==className){  
	 	ele[ele.length]=all[i];  
	 }  
 }  
 return ele;  
}
//limit money Integer's length
//i.为整数位数
function checkMoneyLength(money,i){
	var mint = 8;//默认整数为8位
	if(i != null && i != "" && i != undefined)
		mint = i;
	if(money.indexOf(".")!=-1){
		var m = money.substring(0,money.indexOf("."))
		if(m.length > mint){
			 alert("金额的整数部分不可大于'"+mint+"'位数");
			 return false;
		}
	}else{
		if(money.length > mint){
			 alert("金额的整数部分不可大于'"+mint+"'位数");
			 return false;
		}
	}
		return true;
}

/**
 * ====================================================================
 * 时克英 2013-10-9
 * ====================================================================
 */
// 重新加载业务分页列表，即：点击分页条的方法
//function reload(offset){
	//拼json参数
	//var params = {name:""};
	//doReloadPage(offset, "reload.do", params);
//}

/**
 * 分页加载列表
 * @param offset 当前页
 * @param url 要加载的地址
 * @param params 业务参数，json格式，如：{name:test, age:30}
 */
function doReloadPage(offset, url, params){
	//总分页数目
	var totalpage = 0;
	if($("#totalpage").length > 0){
		// 因为在其他地方调用时，可能会不存在分页对象
		totalpage = $("#totalpage").val();
	}
	
	//查询条件
	//var rolename = $("#rolename").val()
	if(offset == 0 && $("#offset").length > 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	var processUrl = null;
	if(url.indexOf("?") >= 0){
		// 已经包含参数
		processUrl = url + "&walker_page_index=" + offset;
	} else {
		processUrl = url + "?walker_page_index=" + offset;
	}
	loadDiv(processUrl,params,function(){ajustDms();});
}
function loadDiv(url,params, callback){
	$.ajaxSetup({cache:false});
	$("#pageInfo").load(url, params, callback);
}

/* 定义一个刷新iframe的方法 */
var refreshFrame = function(){
	ajustDms();
};

/**
 * 加载内容到div中
 * @param div 页面元素ID
 * @param url 页面路径
 * @param params 数组参数
 */
function loadContent(div, url, params){
	$.ajaxSetup({cache:false});
	$("#"+div).load(url, params, refreshFrame);
}
function loadContent(div, url, params, callback){
	$.ajaxSetup({cache:false});
	$("#"+div).load(url, params, callback);
}

/**
 * 全选或者取消选择，<code>selectAll</code>方法存在兼容性问题。
 * @param obj
 */
function checkAllOrNone(obj){
	/*
	if (obj.checked == true) {
		var _boxs = document.getElementsByName("ids");
		for ( var index = 0; index < _boxs.length; index++) {
			_boxs[index].checked = true;
		}
	} else {
		var _boxs = document.getElementsByName("ids");
		for ( var index = 0; index < _boxs.length; index++) {
			_boxs[index].checked = false;
		}
	}
	*/
	checkAllOrNoneByName(obj, "ids");
}
/**
 * 根据名称，全选或者取消选择，在多个列表中需要此方法
 * @param obj,name
 */
function checkAllOrNoneByName(obj,name){
	if (obj.checked == true) {
		var _boxs = document.getElementsByName(name);
		for ( var index = 0; index < _boxs.length; index++) {
			_boxs[index].checked = true;
		}
	} else {
		var _boxs = document.getElementsByName(name);
		for ( var index = 0; index < _boxs.length; index++) {
			_boxs[index].checked = false;
		}
	}
} 

// 全选
// 该方法已废弃
function selectAll(obj){
	/*
	var str = "";
	if($(obj).attr("checked")){
		$("input[type='checkbox']").attr("checked", true);
		$(".selectFocus").each(function(){
			if($(this).val()){
				str += "'"+ $(this).val() +"',";
			}
		});
		str = str.substr(0,str.length-1);
		$("#ids").val(str);
	}else{
		$("input[type='checkbox']").attr("checked", false);
	}*/
	checkAllOrNoneByName(obj, "ids");
}
//选择其中一行
function selectFocus(obj){
	var b = true;
	$(".selectFocus").each(function(){
		if(!$(this).attr("checked")){
			b = false;
			return false;
		}
	});
	if(b){
		$("input[type='checkbox']").attr("checked", true);
	}else{
		$("input[type='checkbox']:first").attr("checked", false);
		$("input[type='checkbox']:last").attr("checked", false);
	}
}

function getCheckValue(name){    
  var obj=document.getElementsByName(name);  //选择所有name="'test'"的对象，返回数组    
  //取到对象数组后，我们来循环检测它是不是被选中    
  var s = new Array();
  var _c = 0;
  for(var i=0; i<obj.length; i++){    
    //if(obj[i].checked) s+=obj[i].value+",";  //如果选中，将value添加到变量s中   
	if(obj[i].checked){
		s[_c] = obj[i].value;
		_c++;
	}
  }
  if(s.length == 0) return null;
  return s;
}

function isEmptyValue(val){
	if(val == null || val == "" || val == "null"){
		return true;
	}
	return false;
}

function isNotEmptyValue(val){
	if(val == null || val == "" || val == "null"){
		return false;
	}
	return true;
}

function showTip(id){
	$('#'+id).poshytip();
}
function showTipInput(id){
	showTipInput(id, null);
}
function showTipInput(id, showOn){
	$('#'+id).poshytip({
		//className: 'tip-yellowsimple',
		showOn: isEmptyValue(showOn) ? 'focus' : showOn,
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 5
		//showTimeout: 100
	});
}

/**
 * 获取多个checkbox的值
 * @param parentId 父容器的ID，如：在chechbox外层使用一个DIV。
 * @returns {String} id,id...
 */
function getCheckboxVal(parentId){
	var _str = "";
	$("#" + parentId + " input:checkbox:checked").each(function(){
		if (_str == ""){
		   _str += $(this).val();	
		}else {
		   _str += ","+$(this).val();
		}
	});
	return _str;
}

var isNumber = function isNumber0(String){  
	var   Letters   =   "1234567890-";   //可以自己增加可輸入值
	var   i;
	var   c;
	if(String.charAt(   0   )=='-')
	return   false;
	if(   String.charAt(   String.length   -   1   )   ==   '-'   )
	return   false;
	for(   i   =   0;   i   <   String.length;   i   ++   )
	{  
	c   =   String.charAt(   i   );
	if   (Letters.indexOf(   c   )   <   0)
	return   false;
	}
	return   true;
};

var isFloat = function isFloat0(oNum){
  if(!oNum) return false;
  var strP=/^\d+(\.\d+)?$/;
  if(!strP.test(oNum)) return false;
  try{
  	if(parseFloat(oNum)!=oNum) return false;
  }catch(ex){
    return false;
  }
  return true;
};

var isEmail = function isEmail0(str){
	re= /\w@\w*\.\w/;
	if(re.test(str)){
		return true;
	} else
		return false;
};

/**
 * 定义校验手机号方法，在页面中直接调用：isMobile(value)即可;
 */
var isMobile = function isMobile0(str){
	re=/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	if(re.test(str)) return true;
	return false;
};

//----------------------------------------
// ztree共用代码
//----------------------------------------

/**
 * 返回给定树选择节点的ID属性值
 * @param tree
 * @returns
 */
function getZtreeSelectNodeId(tree){
	var node = getZtreeSelectNode(tree);
	if(node == null) return null;
	return node.id;
}

/**
 * 返回给定树对象的选择节点（单个）
 * @param tree
 * @returns
 */
function getZtreeSelectNode(tree){
	var nodes = tree.getSelectedNodes();
	if(nodes == null) return null;
	return nodes[0];
}

function getZtreeObject(id){
	var tree = $.fn.zTree.getZTreeObj(id);
	if(tree == null)
		throw new Error("指定的树没有找到: " + id);
	return tree;
}

function getSelectTreeNodeId(){
	var node = getZtreeSelectNode(getZtreeObject("treeDemo"));
	if(node == null) return null;
	return node.id;
}

/**
 * 初始化一个简单的Ztree同步树
 * @param id 树容器ID
 * @param zNodes 加载的节点json数据
 * @param callback 回调函数
 * @returns
 */
function doInitSimpleZtree0(id, zNodes, _callback){
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

function initSimpleZtree(id, zNodes, _callback){
	return doInitSimpleZtree0(id, zNodes, _callback);
}

/**
 * 初始化一个异步调用的Ztree对象
 */
function initAsyncZtree(id, _url){
  var setting = {
   	view: {
			dblClickExpand: true
		},
		async: {    
	        enable: true,
	        type:'post',
	        url:_url
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
			onClick: zTreeOnClick
		}
	};
	/**
	var zNodes = '${departSet}';
	zNodes = eval("(" + zNodes + ")");
	*/
  tree = $.fn.zTree.init($("#"+id), setting);
  return tree;
}

/**
 * 打开、折叠搜索条件区域
 * @param object 展开按钮对象(this)
 * @param searchForm 搜索条件容器ID，如：tr
 */
function openSearch(object, searchForm){
	$("#"+searchForm).toggle();	 
	if($("#"+searchForm).is(":hidden")){
		$(object).text("+ 展开");
	}else{
		$(object).text("- 收起");
	}
}

/**
 * 发起ajax请求，
 * @param _url 请求URL
 * @param _params 参数，字符串，试试json看是否可行
 * @param _callback 成功回调函数
 */
function requestAjax(_url, _params, _callback){
$.ajax({
  url:_url,
	type:"post",
	data:_params,
	success:function(msg){
		_callback(msg);
	},
	error:function(msg){
		//alert(msg);
		showErrorTip(msg);
	}
});
}

/**
 * 弹出模式窗口对话框，注意该方法是用在iframe中，即：嵌套在iframe中的子功能页面中。</br>
 * 主界面中不能使用该方法。
 * @param title
 * @param url
 * @param width
 * @param height
 */
function popModalDialog(title, url, width, height){
	parent.$.layer({
	    type: 2,
	    //shade: [0],
	    fix: false,
	    title : [title,true],
	    // 如果加上该属性，那么鼠标在阴影中单击也可关闭窗口
	    //shadeClose: true,
	    maxmin: true,
	    zIndex: parent.layer.zIndex,
	    iframe : {src : url},
	    area : [width , height],
	    offset : ['100px', ''],
	    close : function(index){
	        //layer.msg('您获得了子窗口标记：' + layer.getChildFrame('#name', index).val(),3,1);
	    }
	});
}

function popDefaultDialog(title, url){
	popModalDialog(title, url, "800px", "500px");
}

/**
 * 把json字符串转换成JSON对象
 * @param data
 * @returns
 */
function stringToJson(data){
	return eval("(" + data + ")");
}

/**
 * 检查给定的数据是否为空
 * @param v 数据
 * @param tip 提示信息
 * @returns {Boolean}
 */
function checkValue(v, tip){
	if(isEmptyValue(v)){
		alert(tip);
		return false;
	}
	return true;
}

/**
 * 返回checkbox选择的值，拼接字符串，以逗号分隔。
 * @param cname checkbox名字
 * @returns {String}
 */
function getCheckboxValues(cname){
	var str = "";
	$("input[name='" + cname + "']:checked").each(function(){
		str += $(this).val() + ",";
	});
	if(str.length > 0){
		str = str.substring(0, str.length-1);
	}
	return str;
}
/**
 * 把HTML内容转换成JS字符串
 * @param html
 * @returns {String}
 */
function htmlToString(html){
	var reg=new RegExp("\r\n","g");
	html = html.replace(/\'/g,"\\'").replace(/\"/g,'\\"');
	html=html.replace(/[\r\n]/g,'\"\+\r\"');
	html=html.replace(/\"\s*\"\+/g,'');
	html=html.replace(/\"\s+</g,'\"<');
	return "\""+html+"\"";
}

/**
 * 返回选择的radio值
 * @param name radio名字
 * @returns
 */
function getRadioSelectValue(name){
	return $("input[name='" + name + "']:checked").val();
}
/* 返回选择的select选项的文本 */
function getSelectOptionText(id){
	return $("#"+id).find("option:selected").text();
}

/**
 * 选中select的所有options
 * @param id
 */
function selectAllOptions(id){
	$("#"+id + " option").each(function(){
		$(this).attr("selected","selected");
	});
}

/**
 * 正则表达式验证密码，必须是字母、数字、下划线或者组合。
 * @param str
 * @returns {Boolean}
 */
function validPassword(str){
	var regex = /^\w+$/;
	if(!regex.test(str)){
		return false;
	}
	return true;
}

/**
 * 验证给定的字段是否存在值，必填项。
 * @param id
 * @returns {Boolean}
 */
function validateRequired(id){
	v = $.trim($("#"+id).val());
	if(isEmptyValue(v)){
		$("#"+id).focus();
		return false;
	}
	return true;
}

/**
 * 表单中，对于可选项的校验，存在值就校验；不存在，不校验。
 * @param id 输入框ID
 * @param callback 回调函数，如:isMobile，可以校验手机号，这个在本文件中都有定义。
 * @returns {Boolean}
 */
function validateOption(id, callback){
	v = $.trim($("#"+id).val());
	if(isEmptyValue(v)){
		return true;
	}
	res = callback(v);
	if(!res){
		//alert("校验失败");
		$("#"+id).focus();
		return false;
	}
	return true;
}

/**
 * 初始化分页列表，实现能选择某一行变颜色，并且选中checkbox<br>
 * 目前表格ID是固定的:tbl。
 */
function initTable(){
	/*
  if(document.getElementById("tbl")){
	  var tbl = document.getElementById("tbl");  
	  for(var i = 0; i < tbl.rows.length; i++){  
		  tbl.rows[i].onclick =function(){ 
			  if(window.cur) window.cur.style.background = "#FFF";
			  this.style.background = "#EEF7FC";  
			  window.cur = this;
			  // 先清除掉其他行的选择，在设置本行checkbox。
			  if(this.cells.length > 0){
				  //chkbox = this.cells[0].firstChild; // firstElementChild
				  chkbox = this.cells[0].children[0];
				  if(chkbox != null && chkbox.type == "checkbox"){
					  if(!chkbox.checked){
						  // 没选中，直接选中
						  chkbox.checked = true;
					  }
					  for(var i = 0; i < tbl.rows.length; i++){
						  var _tempCheckbox;
						  if(tbl.rows[i].cells.length > 0){
							  _tempCheckbox = tbl.rows[i].cells[0].children[0];
							  if(_tempCheckbox != null && _tempCheckbox.type == "checkbox"){
								  if(_tempCheckbox.value != chkbox.value){
									  _tempCheckbox.checked = false;
								  }
							  }
						  }
					  }
				  }
			  }
		  }; 
	  }
  }
  */
}

/**
 * 返回分页列表当前页
 * @returns
 */
function getCurrentPage(){
	currentPage = $("#offset").val();
	if(isEmptyValue(currentPage)){
		currentPage = "";
	}
	return currentPage;
}

/**
 * 显示应用程序错误提示。</p>
 * 1、在业务中输入错误时，后台会返回提示信息；<br>
 * 2、如果返回系统异常，会覆盖掉当前页面信息。
 * @param data
 */
function showErrorTip(data){
	_res = htmlToString(data);
	if(_res.indexOf("ApplicationException") >= 0){
		// 如果返回的是错误页面，就覆盖掉当前页面
		$("body").empty();
		$("body").html(data);
	} else {
		// 如果是业务提示
		alert(data);
	}
}
