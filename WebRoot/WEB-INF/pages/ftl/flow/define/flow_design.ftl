<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width">

<title>WALKER-WEB 流程设计器</title>

<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" href="${ctx}/style/${style}/flow/jsplumb.css">
<link rel="stylesheet" href="${ctx}/style/${style}/flow/demo.css">
<link rel="stylesheet" href="${ctx}/style/jquery.contextmenu.css">
		
<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script src="${ctx}/script/lib/flow/jquery-1.11.1.min.js"></script>
<script src="${ctx}/script/lib/flow/jquery-ui-1.10.3.js"></script>
<script src="${ctx}/script/lib/flow/jquery.ui.touch-punch.min.js"></script>
<script src="${ctx}/script/lib/flow/jquery.jsPlumb-1.6.2-min.js"></script>
<script src="${ctx}/script/lib/flow/jquery.contextmenu.js"></script>

<script type="text/javascript">
  var instance = null;
  var sourceEndpoint = null;
  
  var contextPath = '${ctx}';
  var processDefineId = '${processDefine.id}';

//定义改变节点签，源节点的ID以及位置ID
  var changedSourceId;
  var changedSourcePosition;
  var changedTargetId;
  var changedTargetPosition;
  
  // 是否创建连接模式，1_true, 0_false;
  var createLinkMode = 0;
  
function next(id){
	window.location.href = "${ctx}/permit/help/index.do?pageId="+id;
}

function goBack(){
	window.location.href = "${ctx}/flow/define/index.do";
}

// 刷新当前页，由弹出窗口调用
function reload(){
	window.location.href = "${ctx}/permit/flow/define/design.do?id=${processDefine.id}";
}

function createNewTask(){
	popDefaultDialog("创建新任务节点", "${ctx}/permit/flow/design/create.do?id=" + $("#processDefineId").val());
}

function validateProcess(){
	var _params = {"processDefineId":$("#processDefineId").val()};
	requestAjax(contextPath + "/permit/flow/design/validate_process.do", _params, function(msg){
		if(msg == "success"){
			alert("流程校验通过：OK");
		} else {
			alert(msg);
		}
	});
}

function publishProcess(){
	var _params = {"processDefineId":$("#processDefineId").val()};
	requestAjax(contextPath + "/permit/flow/design/publish_process.do", _params, function(msg){
		if(msg == "success"){
			alert("流程发布成功");
		} else {
			alert(msg);
		}
	});
}
</script>
<script type="text/javascript" src="${ctx}/script/lib/flow/view.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/flow/base.js"></script>
</head>
<body data-demo-id="flowchart" data-library="jquery">

<input type="hidden" id="processDefineId" name="processDefineId" value="${processDefine.id}"/>
<table border="0" cellpadding="0" cellspacing="0" class="table-form">
	<tr class="title">
		<td>
			<span>${processDefine.name}</span>&nbsp;|&nbsp;
	  	<input type="button" value="返 回" onclick="goBack();" class="button"/>&nbsp;|&nbsp;
	  	<input type="button" value="创建任务节点" onclick="createNewTask();" class="button"/>&nbsp;
	  	<input type="button" value="保存画布" onclick="saveDraft('${ctx}/permit/flow/design/save_draft.do');" class="button-tj"/>&nbsp;
	  	<input type="button" value="检查流程" onclick="validateProcess();" class="button-tj"/>&nbsp;|&nbsp;
	  	<input type="button" value="发布流程" onclick="publishProcess();" class="button"/>&nbsp;
		</td>
	</tr>
</table>

<div id="main">
	<!-- demo -->
	<div class="demo flowchart-demo" id="flowchart-demo">
		<#list taskList as task>
			<#if (task.start)>
			<div class="window" id="flowchartWindow${task.id}" style="background:#FC9D3F;"><strong>${task.name} [开始]</strong></div>
			<#elseif (task.end)>
			<div class="window" id="flowchartWindow${task.id}" style="background:#4ABDE5;"><strong>${task.name} [结束]</strong></div>
			<#else>
			<div class="window" id="flowchartWindow${task.id}"><strong>${task.name}</strong></div>
			</#if>
		</#list>
	</div>
	<!-- /demo -->
</div>

<script type="text/javascript">

jsPlumb.ready(function() {

	instance = jsPlumb.getInstance({
		// default drag options
		DragOptions : { cursor: 'pointer', zIndex:2000 },
		// the overlays to decorate each connection with.  note that the label overlay uses a function to generate the label text; in this
		// case it returns the 'labelText' member that we set on each connection in the 'init' method below.
		ConnectionOverlays : [
			[ "Arrow", { location:1 } ],
			[ "Label", { 
				location:0.1,
				id:"label",
				cssClass:"aLabel"
			}]
		],
		Container:"flowchart-demo"
	});

	// suspend drawing and initialise.
	instance.doWhileSuspended(function() {
	
		var inPositions  = null;
		var outPositions = null;
		<#list taskNodeList as taskNode>
			inPositions  = '${taskNode.inPositions}'.split(",");
			outPositions = '${taskNode.outPositions}'.split(",");
			console.log("...... in: " + inPositions);
			_addEndpoints("Window${taskNode.id}", outPositions, inPositions);
		</#list>
		/*
		_addEndpoints("Window4", ["TopCenter", "BottomCenter"], ["LeftMiddle", "RightMiddle"]);			
		_addEndpoints("Window2", ["LeftMiddle", "BottomCenter"], ["TopCenter", "RightMiddle"]);
		_addEndpoints("Window3", ["RightMiddle", "BottomCenter"], ["LeftMiddle", "TopCenter"]);
		_addEndpoints("Window1", ["LeftMiddle", "RightMiddle"], ["TopCenter", "BottomCenter"]);
		*/
					
		// listen for new connections; initialise them the same way we initialise the connections at startup.
		instance.bind("connection", function(connInfo, originalEvent) { 
			init(connInfo.connection);
		});			
					
		// make all the window divs draggable						
		instance.draggable(jsPlumb.getSelector(".flowchart-demo .window"), { grid: [2, 2] });		
		// THIS DEMO ONLY USES getSelector FOR CONVENIENCE. Use your library's appropriate selector 
		// method, or document.querySelectorAll:
		//jsPlumb.draggable(document.querySelectorAll(".window"), { grid: [20, 20] });
        
		// connect a few up
		var taskConn = null;
		<#list taskConnection as conn>
			taskConn = '${conn}'.split(",");
			instance.connect({uuids:taskConn, editable:true});
		</#list>
		/*
		instance.connect({uuids:["Window2BottomCenter", "Window3TopCenter"], editable:true});
		instance.connect({uuids:["Window2LeftMiddle", "Window4LeftMiddle"], editable:true});
		instance.connect({uuids:["Window4TopCenter", "Window4RightMiddle"], editable:true});
		instance.connect({uuids:["Window3RightMiddle", "Window2RightMiddle"], editable:true});
		instance.connect({uuids:["Window4BottomCenter", "Window1TopCenter"], editable:true});
		instance.connect({uuids:["Window3BottomCenter", "Window1BottomCenter"], editable:true});
    */
    
		//
		// listen for clicks on connections, and offer to delete connections on click.
		//
		/**/
		instance.bind("click", function(connection, originalEvent) {
			if(confirm("确定要删除该连接么？")){
				var endpoints = connection.endpoints;
				var srcTaskId = _translateTaskName(connection.sourceId);
				var destTaskId= _translateTaskName(connection.targetId);
				var srcOutPos = _translatePostionName(endpoints[0].getUuid());
				var destInPos = _translatePostionName(endpoints[1].getUuid());
				_params = {"processDefineId":"${processDefine.id}", "srcTask":srcTaskId, "destTask":destTaskId, "srcOutPosition":srcOutPos, "destInPosition":destInPos};
				requestAjax("${ctx}/permit/flow/design/del_connection.do", _params, function(msg){
					if(msg == "success"){
						jsPlumb.detach(connection);
					} else {
						alert(msg);
					}
				});
			}
		});
	
		
		// 测试绑定其他事件
		/*
		instance.bind("dblclick", function(conn, originalEvent){
			console.log("+++++++++");
		});*/
		
		instance.bind("connectionDrag", function(connection) {
			console.log("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			var endpoints = connection.endpoints;
			var size = endpoints.length;
			// 即使只有一个节点，size也为2，jsplumb会自动生成对象。
			console.log("endpoints size: " + size);
			
			console.log("++++1:" + endpoints[0].getUuid());
			console.log("++++2:" + endpoints[1].getUuid());
			
			changedSourceId = connection.sourceId;
			changedSourcePosition = endpoints[0].getUuid();
			
			if(typeof endpoints[1].getUuid() == "undefined"){
				createLinkMode = 1;
				console.log("开始拖拽了一个新节点，说明要创建连接（不是修改已有连线）src = " + changedSourceId);
			} else {
				// 说明是编辑节点，可能是起始点，也可能是目的点
				createLinkMode = 0;
				changedTargetId = connection.targetId;
				changedTargetPosition = endpoints[1].getUuid();
			}

		connection.getOverlay("label").setLabel("大于5天");
			console.log("connection " + connection.id + " is being dragged. suspendedElement is ", connection.suspendedElement, " of type ", connection.suspendedElementType);
		});	
		
		instance.bind("connectionDragStop", function(connection) {
			console.log("==============================");
			var endpoints = connection.endpoints;
			if(endpoints == null){
				alert("请确保连接到任务节点上。");
				_clearHoldPosition();
				reload();
				return;
			}
			console.log(".....endpoints[0]: " + endpoints[0].getUuid());
			console.log(".....endpoints[1]: " + endpoints[1].getUuid());
			
			// 没有任何节点、位置改变，用户仅仅移动节点又重新放在原位，不处理
			if(changedSourceId != null && changedSourcePosition != null 
				&& changedSourceId == connection.sourceId && changedSourcePosition == endpoints[0].getUuid() 
				&& changedTargetId != null && changedTargetPosition != null 
				&& changedTargetId == connection.targetId && changedTargetPosition == endpoints[1].getUuid()){
				console.log("--------> 没有改变任何节点信息，不处理");
				_clearHoldPosition();
				return;
			}
			
			// 新创建的连接线
			if(createLinkMode){
				var targetId = connection.targetId;
				var targetPosition = endpoints[1].getUuid();
				console.log("源节点: " + changedSourceId + ", position: " + changedSourcePosition);
				console.log("目的点: " + targetId + ", position: " + targetPosition);
				
				changedSourceId = _translateTaskName(changedSourceId);
				targetId = _translateTaskName(targetId);
				changedSourcePosition = _translatePostionName(changedSourcePosition);
				targetPosition = _translatePostionName(targetPosition);
				var _params = {"processDefineId":"${processDefine.id}", "srcTask":changedSourceId, "destTask":targetId, "srcOutPosition":changedSourcePosition, "destInPosition":targetPosition};
				requestAjax("${ctx}/permit/flow/design/new_connection.do", _params, function(msg){
					if(msg != "success"){
						alert(msg);
						reload();
					}
				});
				
			} else {
				// 更新已有的连接线，说明用户确实改变了连接线（节点或者位置）
				if(changedSourceId != null && changedSourceId != connection.sourceId){
					// 源节点变动，说明用户把原来任务节点调整到另一个任务节点
					console.log("您把(1)输出节点从 '" + changedSourceId + "' 调整到了 '" + connection.sourceId + "'");
					console.log("调整后的位置: " + endpoints[0].getUuid() + ", 之前：" + changedSourcePosition);
					
					oldSrcTask = _translateTaskName(changedSourceId);
					chnSrcTask = _translateTaskName(connection.sourceId);
					oldSrcPos  = _translatePostionName(changedSourcePosition);
					chnSrcPos  = _translatePostionName(endpoints[0].getUuid());
					targetTask = _translateTaskName(connection.targetId);
					targetPos  = _translatePostionName(endpoints[1].getUuid());
					
					var _params = {"processDefineId":"${processDefine.id}", "oldSrcTask":oldSrcTask, "chnSrcTask":chnSrcTask, "oldSrcPos":oldSrcPos, "chnSrcPos":chnSrcPos, "targetTask":targetTask, "targetPos":targetPos};
					requestAjax("${ctx}/permit/flow/design/change_src_ep.do", _params, function(msg){
						if(msg != "success"){
							alert(msg);
							reload();
						}
					});
				}
				// 目的连接点被移动到了其他任务节点
				if(changedTargetId != null && changedTargetId != connection.targetId){
					console.log("您把(2)输入节点从 '" + changedTargetId + "' 调整到了 '" + connection.targetId + "'");
					console.log("调整后的位置: " + endpoints[1].getUuid() + ", 之前: " + changedTargetPosition);
					
					oldTargetTask = _translateTaskName(changedTargetId);
					chnTargetTask = _translateTaskName(connection.targetId);
					oldTargetPos  = _translatePostionName(changedTargetPosition);
					chnTargetPos  = _translatePostionName(endpoints[1].getUuid());
					sourceTask    = _translateTaskName(connection.sourceId);
					sourcePos     = _translatePostionName(endpoints[0].getUuid());
					var _params = {"processDefineId":"${processDefine.id}", "oldTargetTask":oldSrcTask, "chnTargetTask":chnTargetTask, "oldTargetPos":oldTargetPos, "chnTargetPos":chnTargetPos, "sourceTask":sourceTask, "sourcePos":sourcePos};
					requestAjax("${ctx}/permit/flow/design/change_dest_ep.do", _params, function(msg){
						if(msg != "success"){
							alert(msg);
							reload();
						}
					});
				}
				// 源节点仅变动了位置
				if(changedSourceId != null && changedSourceId == connection.sourceId 
					&& changedSourcePosition != null && changedSourcePosition != endpoints[0].getUuid()){
					console.log("您把(1)输出节点调整了位置，从 '" + changedSourcePosition + "' 到 '" + endpoints[0].getUuid() + "'");
					
					sourceTask = _translateTaskName(connection.sourceId);
					oldSrcPos  = _translatePostionName(changedSourcePosition);
					chnSrcPos  = _translatePostionName(endpoints[0].getUuid());
					targetTask = _translateTaskName(connection.targetId);
					targetPos  = _translatePostionName(endpoints[1].getUuid());
					var _params = {"processDefineId":"${processDefine.id}", "sourceTask":sourceTask, "oldSrcPos":oldSrcPos, "chnSrcPos":chnSrcPos, "targetTask":targetTask, "targetPos":targetPos};
					requestAjax("${ctx}/permit/flow/design/change_src_pos.do", _params, function(msg){
						if(msg != "success"){
							alert(msg);
						}
					});
				}
				// 目的节点仅变动了位置
				if(changedTargetId != null && changedTargetId == connection.targetId 
					&& changedTargetPosition != null && changedTargetPosition != endpoints[1].getUuid()){
					console.log("您把(2)输入节点调整了位置，从 '" + changedTargetPosition + "' 到 '" + endpoints[1].getUuid() + "'");
					
					targetTask = _translateTaskName(connection.targetId);
					sourceTask = _translateTaskName(connection.sourceId);
					sourcePos  = _translatePostionName(endpoints[0].getUuid());
					oldTargetPos = _translatePostionName(changedTargetPosition);
					chnTargetPos = _translatePostionName(endpoints[1].getUuid());
					var _params = {"processDefineId":"${processDefine.id}", "targetTask":targetTask, "sourceTask":sourceTask, "sourcePos":sourcePos, "oldTargetPos":oldTargetPos, "chnTargetPos":chnTargetPos};
					requestAjax("${ctx}/permit/flow/design/change_dest_pos.do", _params, function(msg){
						if(msg != "success"){
							alert(msg);
						}
					});
				}
				
			}
			_clearHoldPosition();
		});

		instance.bind("connectionMoved", function(params) {
			console.log("connection " + params.connection.id + " was moved");
		});
		
		instance.bind("contextmenu", function(component, originalEvent){
			console.log("++++++++++++ right click component: ");
		});
	});

	// 通过css设置节点的坐标，初始化
	<#list taskNodeList as task>
		//console.log("+++++++++++++++= ${task.x}, ${task.id}, ${task.inPositions}");
		//$("#flowchartWindow${task.id}").attr("offsetTop","${task.x}");
		//$("#flowchartWindow${task.id}").attr("offsetLeft","${task.y}");
		$("#flowchartWindow${task.id}").css("top", "${task.x}px");
		$("#flowchartWindow${task.id}").css("left", "${task.y}px");
	</#list>
	// 一定要加上这句，否则jsplumb不会刷新连接点位置
	instance.setSuspendDrawing(false, true);

	jsPlumb.fire("jsPlumbDemoLoaded", instance);
});

</script>
</body>
<#include "/common/footer.ftl">
</html>