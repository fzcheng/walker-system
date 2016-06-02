 // this is the paint style for the connecting lines..
/*
	var connectorPaintStyle = {
		lineWidth:2,
		strokeStyle:"#61B7CF",
		joinstyle:"round",
		outlineColor:"white",
		outlineWidth:1
	},
	// .. and this is the hover style. 
	connectorHoverStyle = {
		lineWidth:2,
		strokeStyle:"#216477",
		outlineWidth:1,
		outlineColor:"white"
	},
	endpointHoverStyle = {
		fillStyle:"#216477",
		strokeStyle:"#216477"
	},
	// the definition of source endpoints (the small blue ones)
	// label: "Drag" --> 删除了拖拽说明
	sourceEndpoint = {
		endpoint:"Dot",
		paintStyle:{ 
			strokeStyle:"#7AB02C",
			fillStyle:"transparent",
			radius:5,
			lineWidth:2
		},				
		isSource:true,
		connector:[ "Flowchart", { stub:[40, 60], gap:10, cornerRadius:5, alwaysRespectStubs:true } ],								                
		connectorStyle:connectorPaintStyle,
		hoverPaintStyle:endpointHoverStyle,
		connectorHoverStyle:connectorHoverStyle,
        dragOptions:{},
        overlays:[
        	[ "Label", { 
            	location:[0.5, 1.5], 
            	label:"",
            	cssClass:"endpointSourceLabel" 
            } ]
        ]
	},		
	// the definition of target endpoints (will appear when the user drags a connection) 
	// label: "Drop" --> 删除了拖拽说明
	targetEndpoint = {
		endpoint:"Dot",					
		paintStyle:{ fillStyle:"#7AB02C",radius:5 },
		hoverPaintStyle:endpointHoverStyle,
		maxConnections:-1,
		dropOptions:{ hoverClass:"hover", activeClass:"active" },
		isTarget:true,			
        overlays:[
        	[ "Label", { location:[0.5, -0.5], label:"", cssClass:"endpointTargetLabel" } ]
        ]
	},			
	init = function(connection) {			
		connection.getOverlay("label").setLabel(connection.sourceId.substring(15) + "-" + connection.targetId.substring(15));
		connection.bind("editCompleted", function(o) {
			if (typeof console != "undefined")
				console.log("connection edited. path is now ", o.path);
		});
	};			

	var _addEndpoints = function(toId, sourceAnchors, targetAnchors) {
		for (var i = 0; i < sourceAnchors.length; i++) {
			var sourceUUID = toId + sourceAnchors[i];
			instance.addEndpoint("flowchart" + toId, sourceEndpoint, { anchor:sourceAnchors[i], uuid:sourceUUID });						
		}
		for (var j = 0; j < targetAnchors.length; j++) {
			var targetUUID = toId + targetAnchors[j];
			instance.addEndpoint("flowchart" + toId, targetEndpoint, { anchor:targetAnchors[j], uuid:targetUUID });						
		}
	};
	*/
	
	// 把js中任务名字转换成数字
	var _translateTaskName = function(taskName){
		if(isEmptyValue(taskName)){
			alert("任务名称不存在。");
			return null;
		}
		//_temp = taskName.split("");
		//return _temp[_temp.length-1];
		_temp = taskName.replace("flowchartWindow", "");
		return _temp;
	};
	
	var _positionDefine = ["TopLeft","TopCenter","TopRight","BottomLeft","BottomCenter","BottomRight","LeftMiddle","RightMiddle"];
	
	/**
	 * 把UI中window节点名字，提取出节点序号。</br>
	 * 如：Window1TopLeft/Window22BottomCenter等。
	 */
	var _translatePostionName = function(position){
		if(isEmptyValue(position)){
			alert("位置名称不存在。");
			return null;
		}
		console.log("转换位置字符串，原始: " + position);
		for(var i=0; i<_positionDefine.length; i++){
			if(position.indexOf(_positionDefine[i]) > 0){
				return _positionDefine[i];
			}
		}
		alert("转换位置出现错误,未匹配到名字: " + position);
		return "";
	};
	
	// 清除掉上次操作保存的临时变量
	var _clearHoldPosition = function(){
		changedSourceId = null;
		changedSourcePosition = null;
		changedTargetId = null;
		changedTargetPosition = null;
		createLinkMode = 0;
	};
	
	// 保存画布：节点在页面中的坐标
	function saveDraft(url){
		var windowDivs = $(".window");
		if(windowDivs == null || windowDivs.length == 0){
			return;
		}
		// 保存最终提交结果
		var result = "";
		$.each(windowDivs, function(n, value){
			//console.log("++++ n = " + n);
			console.log("-------> " + value.id + ", x:y = " + value.offsetTop + "," + value.offsetLeft);
			result = result + value.offsetTop + "&" + value.offsetLeft + "&" + _translateTaskName(value.id);
			if((n+1) < windowDivs.length){
				result = result + ",";
			}
		});
		console.log(result);
		if(result != ""){
			_params = {"positions":result};
			requestAjax(url, _params, function(msg){
				if(msg == "success"){
					alert("画布中任务节点坐标保存成功!");
				} else {
					alert(msg);
				}
			});
		}
	}
	
	// 弹出菜单
	$(function() {
		$('.window').contextPopup({
		  title: '流程设计菜单',
		  items: [
			{label:'设置任务属性',     icon:contextPath + '/images/flow/shopping-basket.png',             action:function(obj) { 
				var tid = obj.currentTarget.id;
				console.log("------->" + tid);
				popDefaultDialog("设置任务属性", contextPath+"/permit/flow/design/show_edit_task.do?id=" + _translateTaskName(tid));
			}},
			{label:'调整节点位置',     icon:contextPath + '/images/flow/application-monitor.png',              action:function(obj) {
				var tid = obj.currentTarget.id;
				console.log("进入设置节点位置界面: " + tid);
				popModalDialog("调整节点位置", contextPath+"/permit/flow/design/show_ajust_pos.do?id=" + _translateTaskName(tid), "590px", "360px");
			}},
			{label:'设置参与者', icon:contextPath + '/images/flow/bin-metal.png',                action:function(obj) {
				var tid = obj.currentTarget.id;
				console.log("进入参与者界面: " + tid);
				popModalDialog("设置参与者模型", contextPath+"/permit/flow/design/show_actor.do?id=" + _translateTaskName(tid), "850px", "500px");
			}},
			null, // divider
			{label:'删除任务',         icon:contextPath + '/images/flow/application-table.png',         action:function(obj) {
				if(confirm("确定要删除此任务么？如果删除，节点所有连接关系将一同删除!")){
					var tid = obj.currentTarget.id;
					//var windowDiv = obj.target;
					var _params = {"id":_translateTaskName(tid)};
					requestAjax(contextPath + "/permit/flow/design/remove_task.do", _params, function(msg){
						if(msg == "success"){
							//alert("任务成功删除。");
							instance.remove(windowDiv);
						} else {
							alert(msg);
						}
					});
				}
			}},
			null, // divider
			{label:'取消结束任务',       icon:contextPath + '/images/flow/magnifier-zoom-actual-equal.png',           action:function(obj) {
				var tid = obj.currentTarget.id;
				if(!window.confirm("确定要取消此任务为'结束任务'么？")){
					return;
				}
				console.log("........ id = " + tid + ", pid = " + processDefineId);
				var _params = {"id":_translateTaskName(tid), "processDefineId":processDefineId};
				requestAjax(contextPath + "/permit/flow/design/cancel_end.do", _params, function(msg){
					if(msg == "success"){
						reload();
					} else {
						alert(msg);
					}
				});
			}},
			{label:'设置为结束任务',      icon:contextPath + '/images/flow/cassette.png',                    action:function(obj) {
				var tid = obj.currentTarget.id;
				if(!window.confirm("确定要设置此任务为结束任务么？")){
					return;
				}
				console.log("........ id = " + tid + ", pid = " + processDefineId);
				var _params = {"id":_translateTaskName(tid), "processDefineId":processDefineId};
				requestAjax(contextPath + "/permit/flow/design/set_end.do", _params, function(msg){
					if(msg == "success"){
						reload();
					} else {
						alert(msg);
					}
				});
			}}
		  ]
		});
	  });