<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程运行监控查看</title>
<link href="${ctx}/style/reset.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/css.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/tabslet.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/style/${style}/layer.css" type="text/css" rel="stylesheet"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript" src="${ctx}/script/modernizr.custom.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/waypoints.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/waypoints-sticky.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/jquery.tabslet.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/rainbow-custom.min.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>

<script type="text/javascript">

var loadi;
//var index = parent.layer.getFrameIndex(window.name);

//定义tab标签的索引值，从1开始
var tabIndex = 1;

$(function(){
	// 初始化，加载第一个tab内容
	loadi = layer.load('正在加载数据...'); //需关闭加载层时，执行layer.close(loadi)即可
	loadContent("tab1", "${ctx}/permit/flow/instance/view_item.do", {"processInstId":"${processInstId}"}, function(){
		layer.close(loadi);
		refreshFrame(); //刷新iframe
	});
});

function closeWindow(){
	//parent.layer.msg('您将标记"' + $('#name').val() + '"成功传送给了父窗口' , 1, 1);
  parent.layer.close(index);
}

function showTabContent(index){
	// 触发tab标签点击
	tabIndex = parseInt(index);
	if($("#tab"+tabIndex).text() == ""){
		// 不存在就初始化加载
		// refreshFrame
		loadi = layer.load('正在加载数据...');
		if(tabIndex == 4){
			loadContent("tab"+tabIndex, "${ctx}/permit/flow/define/view.do", {"id":"${processDefineId}"}, function(){
				layer.close(loadi);
				refreshFrame();
			});
		} else if(tabIndex == 1){
			//不会到这里
		} else if(tabIndex == 2){
			loadContent("tab"+tabIndex, "${ctx}/permit/flow/instance/view_task_list.do", {"processInstId":"${processInstId}"}, function(){
				layer.close(loadi);
				refreshFrame();
			});
		} else if(tabIndex == 3){
			layer.close(loadi);
			refreshFrame();
		}
	} else {
		//console.log("-------------> 刷新一次iframe");
		// 如果不延时刷新，iframe高度就会刷新不了，还不知道为啥!
		setTimeout("refreshFrame()",300);
		//refreshFrame();
	}
}

function goBack(){
	window.location.href = "${ctx}/flow/instance/index.do";
	//parent.frames.sampleframe.src = "${ctx}/flow/instance/index.do";
}
</script>
</head>
<body>

<section>
<article>
<div class='tabs' data-toggle="tabslet" data-animation="true">
	<ul class='horizontal'>
		<li onclick="showTabContent('1');"><a href="#tab1">流程详细内容</a></li>
		<li onclick="showTabContent('2');"><a href="#tab2">任务执行过程</a></li>
		<li onclick="showTabContent('3');"><a href="#tab3">当前办理</a></li>
		<li onclick="showTabContent('4');"><a href="#tab4">流程模板</a></li>
		<li onclick="goBack();"><a href="#tab0">返 回</a></li>
	</ul>
	<div id='tab1'></div>
	<div id='tab2'></div>
	<div id='tab3'></div>
	<div id='tab4'></div>
</div>
</article>
</section>

</body>
<#include "/common/footer.ftl">
</html>