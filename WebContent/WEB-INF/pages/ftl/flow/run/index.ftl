<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务流程办理</title>
<link href="${ctx}/style/reset.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/tabslet.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/powerFloat.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript" src="${ctx}/script/modernizr.custom.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/waypoints.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/waypoints-sticky.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/jquery.tabslet.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/rainbow-custom.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>

<script type="text/javascript" src="${ctx}/script/lib/jquery-powerFloat-min.js"></script>
<script type="text/javascript">

var loadi;
var index = parent.layer.getFrameIndex(window.name);

// 流程执行后回调方法
var flowCallback = "parent.frames.sampleframe.${transmitCallback}";

//定义tab标签的索引值，从1开始
var tabIndex = 1;

$(function(){
	$("#usefulExp").powerFloat({
		width:160,
		eventType: "click",
		//target:[{href:"##", text:"同意审核"},{href:"##", text:"不同意，资料不完整"}],
		target:["唐丽霞", "徐栋梁", "朱小丽", "束方娟", "吉回秀dssdfsdfsfd地方", "陈阳", "<a href='####' onclick='selectOpinion(\"更多\");'>更多 >></a>"],
		targetMode:"list"
	});
	
	// 初始化，加载第一个tab内容
	loadi = layer.load('正在加载数据...'); //需关闭加载层时，执行layer.close(loadi)即可
	loadContent("tab1", "${ctx+bizUrl}", null, function(){
		layer.close(loadi);
	});
});

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

function selectOpinion(msg){
	$("#opinion").val(msg);
	// 再次自动点击链接，隐藏常用语弹出界面
	$("#usefulExp").click();
}

function showTabContent(index){
	// 触发tab标签点击
	tabIndex = parseInt(index);
	if($("#tab"+tabIndex).text() == ""){
		// 不存在就初始化加载
		// refreshFrame
		loadi = layer.load('正在加载数据...');
		if(tabIndex == 3){
			loadContent("tab"+tabIndex, "${ctx}/permit/flow/define/view.do", {"id":"${processDefineId}"}, function(){layer.close(loadi);});
		} else if(tabIndex == 1){
			loadContent("tab"+tabIndex, "${ctx+bizUrl}", null, function(){layer.close(loadi);});
		} else if(tabIndex == 2){
			loadContent("tab"+tabIndex, "${ctx}/permit/flow/instance/view_task_list.do", {"processInstId":"${processId}"}, function(){
				layer.close(loadi);
			});
		}
	}
}

function transback(){
	if(window.confirm("确定要执行退回操作么?")){
		_params = {"taskInstId":"${taskInstId}", "opinion":$("#opinion").val()};
		requestAjax("${ctx}/permit/flow/runtime/trans_back.do", _params, function(data){
			if(data == "success"){
				stringToJson(flowCallback);
				closeWindow();
			} else {
				showErrorTip(data);
			}
		});
	}
}

function transnext(){
	if(window.confirm("确定要执行流程转发操作么?")){
		_mustSelectActor = $("#mustSelectActor").val();
		if(_mustSelectActor == "1" && isEmptyValue($("#actors").val())){
			alert("必须选择下一步参与人员");
			$("#actors").focus();
			return;
		}
		_params = null;
		if(_mustSelectActor == "1"){
			// 选择的参与者
			_selectActors = $("#actors").val() + "|" + getSelectOptionText("actors");
			_params = {"taskInstId":"${taskInstId}", "opinion":$("#opinion").val(), "mustSelectActor":"1", "selectedActor":_selectActors};
		} else {
			_params = {"taskInstId":"${taskInstId}", "opinion":$("#opinion").val(), "mustSelectActor":"0"};
		}
		console.log(_params);
		requestAjax("${ctx}/permit/flow/runtime/trans_next.do", _params, function(data){
			if(data == "success"){
				stringToJson(flowCallback);
				closeWindow();
			} else if(data.indexOf("multiActorNext") >= 0){
				// 存在多参与者，需要选择
				// 需要选择的参与者html
				actorsHtml = "";
				resArray = data.split(",");
				for(i=1; i<resArray.length; i++){
					actorInfo = resArray[i].split("|");
					actorsHtml += "<option value='" + actorInfo[0] + "'>" + actorInfo[1] + "</option>";
				}
				
				// 目前仅支持选择一个人员，前端组件后续要替换，支持选择多个参与者
				$("#actors").html(actorsHtml);
				$("#nextActorSelect").show();
				$("#mustSelectActor").val("1"); //设置标志位，必须选择一个参与者
			} else {
				showErrorTip(data);
			}
		});
	}
}
</script>
</head>
<body>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td>

<section>
<article>
<div class='tabs' data-toggle="tabslet" data-animation="true">
	<ul class='horizontal'>
		<li onclick="showTabContent('1');"><a href="#tab1">业务信息</a></li>
		<li onclick="showTabContent('2');"><a href="#tab2">任务执行过程</a></li>
		<li onclick="showTabContent('3');"><a href="#tab3">使用流程模板</a></li>
	</ul>
	<div id='tab1' style="height:460px; overflow-x:hidden; overflow-y:scroll;"></div>
	<div id='tab2' style="height:460px; overflow-x:hidden; overflow-y:scroll;"></div>
	<div id='tab3' style="height:460px; overflow-x:hidden; overflow-y:scroll;"></div>
</div>
</article>
</section>

		</td>
	</tr>
	<tr>
		<td>
		 
<table border="3" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="390px" style="padding-left:3px;">
			意见或备注：
			<input type="text" class="text" id="opinion"/>
			<a href="####" id="usefulExp">常用语</a>&nbsp;|&nbsp;
			<a href="####">管理</a>
		</td>
		<#if (allowedBack)>
		<td width="80px" align="center">
			<input type="button" class="button" value="退回上一步" onclick="transback();"/>
		</td>
		</#if>
		<td width="60px" align="center">
			<input type="button" class="button" value="测试"/>
		</td>
		<td width="150px">
			<input type="button" class="button-tj" value="发送到下一步" onclick="transnext();"/>&nbsp;
			<#if (!multiNextActor)>
				<#list nextActorList as actor>
					${actor.nodeName}
				</#list>
			</#if>
		</td>
		<#if (multiNextActor)>
		<td id="nextActorSelect" style="display:block;" width="180px">选择执行人：
			<input type="hidden" id="mustSelectActor" name="mustSelectActor" value="1"/>
			<select id="actors" name="actors">
				<#list nextActorList as actor>
				<option value="${actor.nodeId}">${actor.nodeName}</option>
				</#list>
			</select>
		</td>
		<#else>
		<td id="nextActorSelect" style="display:none;" width="180px">选择执行人：
			<input type="hidden" id="mustSelectActor" name="mustSelectActor" value="0"/>
			<select id="actors" name="actors"></select>
		</td>
		</#if>
		<td>&nbsp;</td>
	</tr>
</table>
		 
		</td>
	</tr>
</table>
 
</body>
<#include "/common/footer.ftl">
</html>