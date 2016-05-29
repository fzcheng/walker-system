<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width">

<title>WALKER-WEB 流程查看</title>

<!-- 
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
 -->
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
  
</script>
<script type="text/javascript" src="${ctx}/script/lib/flow/view.js"></script>
</head>
<body data-demo-id="flowchart" data-library="jquery">

<input type="hidden" id="processDefineId" name="processDefineId" value="${processDefine.id}"/>

<h3>${processDefine.name}</h3>
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
    
		// 测试绑定其他事件
		/*
		instance.bind("dblclick", function(conn, originalEvent){
			console.log("+++++++++");
		});*/
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