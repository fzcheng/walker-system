/*����iframe�߶ȣ����Է���û��ʹ��*/
function reinitIframe(){
  var iframe = document.getElementById("frame1");
  try{
  var bHeight = iframe.contentWindow.document.body.scrollHeight;
  var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
  var height = Math.max(bHeight, dHeight);
  iframe.height =  height;
  }catch (ex){}
}
/*��ʼ��IFRAMEʱ��������߶ȡ���ȸ���������ã�����IFRAME��ȿ�����Ӧ����˲������ÿ��*/
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
	    
	    //alert("---" + win.height);
	    if(win.height <= 550){
	    	win.height = 550;
	    }
	    document.getElementById("sampleframe").style.height = win.height;
	  }
	}
}
/*����ҳ���Window�ĸ߶ȺͿ��*/
function getSkyPageSize(){ 
	var xScroll, yScroll;
	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac 
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari 
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	} 
	var windowWidth, windowHeight;
	if (self.innerHeight) { // all except Explorer 
		windowWidth = self.innerWidth;
		windowHeight = self.innerHeight;
	} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode 
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) { // other Explorers 
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	} 
	// for small pages with total height less then height of the viewport 
	if(yScroll < windowHeight){ 
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	} 
	// for small pages with total width less then width of the viewport 
	if(xScroll < windowWidth){ 
		pageWidth = windowWidth;
	} else { 
		pageWidth = xScroll;
	} 
	
	arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight) 
	return arrayPageSize;
}
/*������������ʾ������ʾ*/
function showSkyLoading(message){
	//var div = document.createElement("DIV");
	var div = document.getElementById("loading");
	div.align="center";
	div.style.fontSize="12px";
	div.style.margin="30px";   
	var messageNode = document.createTextNode(message);
	div.appendChild(messageNode);
	//document.body.innerHTML="";
	document.body.appendChild(div);
}
/*�������������ؼ�����ʾ*/
function hideSkyLoading(){
	var div = document.getElementById("loading");
	if(div != null){
		div.innerHTML = "";
	}
}
/*�������Iframe�Ŀ�Ⱥ͸߶�*/
function ajustFrameDms(){
	var pgDms = getSkyPageSize();
    var iframeObject = parent.document.getElementById("sampleframe");
    if(iframeObject != null){
        iframeObject.style.height = pgDms[1];
        iframeObject.style.width = pgDms[0];
    }
	//parent.document.getElementById("container").style.height = pgDms[1];
	//parent.document.getElementById("ctable").style.height = pgDms[1];
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
	var totalpage = $("#totalpage").val();
	//查询条件
	//var rolename = $("#rolename").val()
	if(offset == 0){  //点击go触发
		offset = $("#offset").val();
	}
	if(offset > parseInt(totalpage) && parseInt(totalpage) != 0){
		offset = totalpage;
	}
	loadDiv(url + "?walker_page_index=" + offset,params,null);
}
function loadDiv(url,params, callback){
	$("#pageInfo").load(url, params, function(){});
}

//全选
function selectAll(obj){
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
	}
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
  //if(s != ""){
	  //s = s.substr(0,s.length-1);
  //}
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
