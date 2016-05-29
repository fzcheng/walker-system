//<!--
var JqueryDialog = {
	//配置项
	//模态窗口背景色
	"cBackgroundColor"			:	"#ffffff",
	//边框尺寸(像素)
	"cBorderSize"				:	1,
	//边框颜色
	"cBorderColor"				:	"#666666",
	//Header背景色
	"cHeaderBackgroundColor"	:	"#e0f0ff",
	//右上角关闭显示文本
	"cCloseText"				:	"X",
	//鼠标移上去时的提示文字
	"cCloseTitle"				:	"close",
	//Bottom背景色
	"cBottomBackgroundColor"	:	"#dff5f6",
	//提交按钮文本
	"cSubmitText"				:	"确 定",
	//取消按钮文本
	"cCancelText"				:	"关 闭",
	//拖拽效果
	"cDragTime"					:	"100",
	Open:function(dialogTitle, iframeSrc, iframeWidth, iframeHeight){
		JqueryDialog.init(dialogTitle, iframeSrc, iframeWidth, iframeHeight, true, true, true);
	},
	
	Open1:function(dialogTitle, iframeSrc, iframeWidth, iframeHeight, isSubmitButton, isCancelButton, isDrag){
		JqueryDialog.init(dialogTitle, iframeSrc, iframeWidth, iframeHeight, isSubmitButton, isCancelButton, isDrag);
	},
	
	init:function(dialogTitle, iframeSrc, iframeWidth, iframeHeight, isSubmitButton, isCancelButton, isDrag){
		
		//获取客户端页面宽高
		//（时克英：原来的宽、高定义，在用户改变浏览器窗口大小后，蒙版就不对了，所以自己修改了，取scroll宽、高）
		//var _client_width = document.body.clientWidth;
		//var _client_height = document.documentElement.scrollHeight;
		var _client_width = document.body.scrollWidth;
		var _client_height = document.body.scrollHeight;
		//create shadow
		if(typeof($("#jd_shadow")[0]) == "undefined"){
			//前置
			$("body").prepend("<div id='jd_shadow'>&nbsp;</div>");
			var _jd_shadow = $("#jd_shadow");
			_jd_shadow.css("width", _client_width + "px");
			_jd_shadow.css("height", _client_height + "px");
		}
		//create dialog
		if(typeof($("#jd_dialog")[0]) != "undefined"){
			$("#jd_dialog").remove();
		}
		$("body").prepend("<div id='jd_dialog'></div>");
		//dialog location
		//left 边框*2 阴影5
		//top 边框*2 阴影5 header30 bottom50
		var _jd_dialog = $("#jd_dialog");
		var _left = (_client_width - (iframeWidth + JqueryDialog.cBorderSize * 2 + 0)) / 2;
		_jd_dialog.css("left", (_left < 0 ? 0 : _left) + document.documentElement.scrollLeft + "px");
		//var _top = (document.documentElement.clientHeight - (iframeHeight + JqueryDialog.cBorderSize * 2 + 30 + 50 + 5)) / 2;
		var _top = (document.body.scrollHeight - (iframeHeight + JqueryDialog.cBorderSize * 2 + 30 + 50 + 0)) / 2;
		_jd_dialog.css("top", (_top < 0 ? 0 : _top) + document.documentElement.scrollTop + "px");

		//create dialog shadow
		_jd_dialog.append("<div id='jd_dialog_s'>&nbsp;</div>");
		var _jd_dialog_s = $("#jd_dialog_s");
		//iframeWidth + double border
		_jd_dialog_s.css("width", iframeWidth + JqueryDialog.cBorderSize * 2 + "px");
		//iframeWidth + double border + header + bottom
		_jd_dialog_s.css("height", iframeHeight + JqueryDialog.cBorderSize * 2 + 30 + 50 + "px");

		//create dialog main
		_jd_dialog.append("<div id='jd_dialog_m'></div>");
		var _jd_dialog_m = $("#jd_dialog_m");
		_jd_dialog_m.css("border", JqueryDialog.cBorderColor + " " + JqueryDialog.cBorderSize + "px solid");
		_jd_dialog_m.css("width", iframeWidth + "px");
		_jd_dialog_m.css("background-color", JqueryDialog.cBackgroundColor);
	
		//header
		_jd_dialog_m.append("<div id='jd_dialog_m_h'></div>");
		var _jd_dialog_m_h = $("#jd_dialog_m_h");
		_jd_dialog_m_h.css("width", iframeWidth);
		_jd_dialog_m_h.css("background-color", JqueryDialog.cHeaderBackgroundColor);
		
		//header left
		_jd_dialog_m_h.append("<span id='jd_dialog_m_h_l'>" + dialogTitle + "</span>");
		_jd_dialog_m_h.append("<span id='jd_dialog_m_h_r' title='" + JqueryDialog.cCloseTitle + "' onclick='JqueryDialog.Close();'>" + JqueryDialog.cCloseText + "</span>");

		//body
		_jd_dialog_m.append("<div id='jd_dialog_m_b'></div>");
		var _jd_dialog_m_b = $("#jd_dialog_m_b");
		_jd_dialog_m_b.css("width", iframeWidth + "px");
		_jd_dialog_m_b.css("height", iframeHeight + "px");		
		
		//iframe 遮罩层
		_jd_dialog_m_b.append("<div id='jd_dialog_m_b_1'>&nbsp;</div>");
		var _jd_dialog_m_b_1 = $("#jd_dialog_m_b_1");
		_jd_dialog_m_b_1.css("top", "30px");
		_jd_dialog_m_b_1.css("width", iframeWidth + "px");
		_jd_dialog_m_b_1.css("height", iframeHeight + "px");
		_jd_dialog_m_b_1.css("display", "none");
		_jd_dialog_m_b.append("<div id='jd_dialog_m_b_2'></div>");
		$("#jd_dialog_m_b_2").append("<iframe id='jd_iframe' src='"+iframeSrc+"' scrolling='no' frameborder='0' width='"+iframeWidth+"' height='"+iframeHeight+"' />");
		_jd_dialog_m.append("<div id='jd_dialog_m_t' style='background-color:"+JqueryDialog.cBottomBackgroundColor+";'></div>");
		var _jd_dialog_m_t = $("#jd_dialog_m_t");
		if(isSubmitButton){
			_jd_dialog_m_t.append("<span><input id='jd_submit' value='"+JqueryDialog.cSubmitText+"' type='button' onclick='JqueryDialog.Ok();' class='button-tj'/></span>");
		}
		if(isCancelButton){
			_jd_dialog_m_t.append("<span class='jd_dialog_m_t_s'><input id='jd_cancel' value='"+JqueryDialog.cCancelText+"' type='button' onclick='JqueryDialog.Close();' class='button-tj'/></span>");
		}
		if(isDrag){
			DragAndDrop.Register(_jd_dialog[0], _jd_dialog_m_h[0]);
		}
	},
	Close:function(){
		$("#jd_shadow").remove();
		$("#jd_dialog").remove();
	},
	Ok:function(){
		var frm = $("#jd_iframe");	
		if (frm[0].contentWindow.Ok()){
			JqueryDialog.Close() ;
		}
		else{
			frm[0].focus() ;
		}
	},
	SubmitCompleted:function(alertMsg, isCloseDialog, isRefreshPage){
		if($.trim(alertMsg).length > 0 ){
			alert(alertMsg);
		}
    	if(isCloseDialog){
			JqueryDialog.Close();
			if(isRefreshPage){
				window.location.href = window.location.href;
			}
		}
	}
};

var DragAndDrop = function(){
	var _clientWidth;
	var _clientHeight;
	var _controlObj;
	var _dragObj;
	var _flag = false;
	var _dragObjCurrentLocation;
	var _mouseLastLocation;
	var getElementDocument = function(element){
		return element.ownerDocument || element.document;
	};
	var dragMouseDownHandler = function(evt){
		if(_dragObj){
			evt = evt || window.event;
			_clientWidth = document.body.clientWidth;
			_clientHeight = document.documentElement.scrollHeight;
			$("#jd_dialog_m_b_1").css("display", "");
			_flag = true;
			_dragObjCurrentLocation = {
				x : $(_dragObj).offset().left,
				y : $(_dragObj).offset().top
			};
			_mouseLastLocation = {
				x : evt.screenX,
				y : evt.screenY
			};
			$(document).bind("mousemove", dragMouseMoveHandler);
			$(document).bind("mouseup", dragMouseUpHandler);
			if(evt.preventDefault)
				evt.preventDefault();
			else
				evt.returnValue = false;
		}
	};
	var dragMouseMoveHandler = function(evt){
		if(_flag){
			evt = evt || window.event;
			var _mouseCurrentLocation = {
				x : evt.screenX,
				y : evt.screenY
			};
			_dragObjCurrentLocation.x = _dragObjCurrentLocation.x + (_mouseCurrentLocation.x - _mouseLastLocation.x);
			_dragObjCurrentLocation.y = _dragObjCurrentLocation.y + (_mouseCurrentLocation.y - _mouseLastLocation.y);
			_mouseLastLocation = _mouseCurrentLocation;
			$(_dragObj).css("left", _dragObjCurrentLocation.x + "px");
			$(_dragObj).css("top", _dragObjCurrentLocation.y + "px");
			if(evt.preventDefault)
				evt.preventDefault();
			else
				evt.returnValue = false;
		}
	};
	var dragMouseUpHandler = function(evt){
		if(_flag){
			evt = evt || window.event;
			$("#jd_dialog_m_b_1").css("display", "none");
			cleanMouseHandlers();
			_flag = false;
		}
	};
	
	var cleanMouseHandlers = function(){
		if(_controlObj){
			$(_controlObj.document).unbind("mousemove");
			$(_controlObj.document).unbind("mouseup");
		}
	};
	return {
		Register : function(dragObj, controlObj){
			_dragObj = dragObj;
			_controlObj = controlObj;
			$(_controlObj).bind("mousedown", dragMouseDownHandler);			
		}
	}
}();
//-->