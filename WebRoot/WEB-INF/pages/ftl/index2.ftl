<!DOCTYPE html>
<html>
<head>
<title>My 管理平台V1.0</title>
<meta http-equiv="cache-control" content="max-age=7200">
<!-- 
<link href="${ctx}/style/reset.css" type="text/css" rel="stylesheet"/>
 -->
<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/css.css">
<link rel="stylesheet" type="text/css" href="${ctx}/script/lib/modal/jquery_dialog.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/${style}/tabslet.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/script/lib/layer/layer.css">

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript" src="${ctx}/script/modernizr.custom.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/waypoints.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/waypoints-sticky.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/jquery.tabslet.min.js"></script>
<script type="text/javascript" src="${ctx}/script/tabslet/rainbow-custom.min.js"></script>

<script type="text/javascript" src="${ctx}/script/lib/modal/jquery_dialog.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/layer/layer.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//改变窗口自动刷新页面
	//window.onresize=function(){ location=location }; 	

	//获得浏览器宽高
	//$('.menu').height($(window).height()-118);
	//$('.sidebar').height($(window).height()-51-27-26-5);
	//$('.page').height($(window).height()-51-27-26-5);
	//$('.page iframe').width($(window).width()-10-168);
	
  //新菜单
  $("#firstpane p.menu_head").click(function(){
       //$(this).next().next("div.menu_body").slideToggle(0).siblings("div.menu_body").slideUp(0);
       $(this).next().next("div.menu_body").toggle();
   });
  /*
   $("#firstpane p.menu_head").mouseover(function(){
      $(this).css({background:"#2286c6"});
   });
   $("#firstpane p.menu_head").mouseout(function(){
      $(this).css({background:"#93cdff"});
   });
   */
});
	
function smenuOpen(url){
	smenuOpen(url, null, null, null);
}
function smenuOpen(url, menuId, groupName, menuName){
	selectMenu(menuId);
	//document.getElementById("sampleframe").style.height = "500";
	$("#sampleframe").attr("src", url);
}

function openWindow(title,url,width,height,callBack) {
	JqueryDialog.Open(title, url, width, height);
	JqueryDialog.callBack = callBack;
}

function closeWindow() {
	JqueryDialog.callBack.apply(this,arguments);
	JqueryDialog.Close();
}

/*修正主界面右边内容区域动态加载数据后，框架iframe高度不更新的情况
 *此方法在某些功能中调用，非常重要。
*/
function setTableHeight(_height){
	if(_height != null && _height != ""){
		document.getElementById("sampleframe").style.height = _height;
		//$("#ctable").height(_height);
		//alert("传入高度=" + _height +  ",ctable高=" + $("#ctable").height());
	}
}

function clickTab(name, sysId){
	if(sysId == "home"){
		$('#_menuContainer').hide();
		$("#sampleframe").attr("src", "${ctx}/permit/portlet/default.do");
		return;
	}
	$('#_menuContainer').show();
	$("#position_toolbar").html("<a href='#'>" + name + "</a>");
	// 重新加载二三级菜单
	loadContent("firstpane", "${ctx}/permit/menu.do", {"sysId": sysId}, function(){
		// 让菜单可以折叠
		$("#firstpane p.menu_head").click(function(){
	      //$(this).next().next("div.menu_body").slideToggle(0).siblings("div.menu_body").slideUp(0);
	      $(this).next().next("div.menu_body").toggle();
	  });
		ajustMenuHeight();
	});
}


var selectedMenu = null;

function selectMenu(menuId){
	if(selectedMenu != null){
		// 如果不是第一次选择菜单
		//$("#"+selectedMenu).removeClass("menu_active");
		$("#"+selectedMenu).css("color","#222222");
		$("#"+selectedMenu).css("background-color","#F4FEFF");
	}
	$("#"+menuId).css("color","#222222");
	$("#"+menuId).css("background-color","#DAEEF2");
	//$("#"+menuId).attr("class","on");
	//$("#"+menuId).toggleClass("menu_active");
	selectedMenu = menuId;
}

function toggleLeftMenu(){
	$('#_menuContainer').toggle();	 
	if($("#_menuContainer").is(":hidden")){
		//$('.page iframe').width($(window).width());
		//$('.btn').text("+展开菜单");
	}else{
		//$('.page iframe').width($(window).width()-10-168);
		//$('.btn').text("- 收起菜单");
	}
}

// 弹出样式选择窗口
function popStyleWindow(){
	popModalDialog("选择你的风格", "${ctx}/permit/style.do", "800px", "460px");
}

function openBrowserNew(){
	
}
</script>
</head>
  
<body style="overflow-x:hidden;">
 
<!--#页眉开始 -->
<div class="logoDiv">
<table class="logoTable" border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td valign="top">
		<!-- 
			<div class="top">
			<ul>
        <li>你好！<a href="javascript:;">${username}</a>&nbsp;所属单位：<a href="#">${orgname}</a><span>|</span></li>
        <#if (!isSupervisor)>
        <li><span></span><a href="javascript:;" onclick="popStyleWindow();">风格</a><span>|</span></li>
        </#if>
        <li><a href="javascript:;" onclick="smenuOpen('${ctx}' + '/permit/person/index.do')">个人设置</a><span>|</span></li>
        <li><a href="javascript:;">锁屏</a><span>|</span></li>
        <li><a href="javascript:;" onclick="smenuOpen('${ctx}/permit/help/index.do')">帮助</a><span>|</span></li>
        <li><a href="javascript:;" onclick="smenuOpen('${ctx}/permit/about.do')">关于</a><span>|</span></li>
        <li><a href="${ctx}/j_spring_security_logout">注销</a><span>|</span></li>
      </ul>
      </div>
		 -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      	<tr>
      		<td>&nbsp;</td>
      		<td width="470px" height="32px" align="center" valign="middle">
      			<table border="0" cellspacing="0" cellpadding="0" width="98%" class="logoTitle">
      				<tr>
      					<td>
      						你好！<a href="javascript:;">${username}</a>&nbsp;所属单位：
      						<#if (orgname??)>
      							<#if (orgname?length > 6)>
      							<a href="javascript:;" title="${orgname}">${orgname?substring(0,6)}</a>
      							<#else>
	      						<a href="javascript:;" title="${orgname}">${orgname}</a>
      							</#if>
      						</#if>
      						&nbsp;|&nbsp;
      						<#if (!isSupervisor)>
					        <a href="javascript:;" onclick="popStyleWindow();">风格</a>&nbsp;|&nbsp;
					        </#if>
      						<a href="javascript:;" onclick="smenuOpen('${ctx}' + '/permit/person/index.do')">设置</a>&nbsp;|&nbsp;
      						<a href="javascript:;">锁屏</a>&nbsp;|&nbsp;
        					<a href="javascript:;" onclick="smenuOpen('${ctx}/permit/help/index.do')">帮助</a>&nbsp;|&nbsp;
        					<a href="${ctx}/j_spring_security_logout">注销</a>
      					</td>
      				</tr>
      			</table>
      		</td>
      	</tr>
      </table>
		</td>
	</tr>
	<tr>
		<td style="padding-left:12px;" valign="bottom">
			<div class="tabs" data-toggle="tabslet" data-animation="true">
				<ul class="horizontal">
					<#assign sysCount = 1>
          <#assign sysId = "">
          <#assign firstSysId = "">
          <#assign lastSysId = "">
          <#list sysList as sys>
            <#if (sysCount == 1)>
              <#assign sysId = sys.id>
            <#else>
            </#if>
            <#if (sys.openStyle == 9 && sys.url??)>
            	<#if (sys.url?contains("http://"))>
            <li id="${sys.id}"><a href="${sys.url}" target="_blank">${sys.name}</a></li>
            	<#else>
            <li id="${sys.id}"><a href="${ctx}/${sys.url}" target="_blank">${sys.name}</a></li>
            	</#if>
            <#else>
            <li onclick="clickTab('${sys.name}','${sys.id}')" id="${sys.id}"><a href="#tab2">${sys.name}</a></li>
            </#if>
            <#assign sysCount = sysCount+1>
            <#assign lastSysId = sys.id>
          </#list>
					<#if (isSupervisor)>
					<#assign lastSysId = "deployTab">
					<li onclick="clickTab('开发部署','supervisor')" id="deployTab"><a href="#tab1">部署</a></li>
					</#if>
					<!-- 
					<div class="end">-</div>
					 -->
					<li class="end">-</li>
					
					<!-- 判断第一个tab标签是谁，添加圆角 -->
					<#if (sysCount >= 1)>
					<#assign firstSysId = sysId>
					<#else>
					<#assign firstSysId = "deployTab">
					</#if>
				</ul>
				<script type="text/javascript">
					<#if (firstSysId != "")>
						$("#${firstSysId}").addClass("leftRoundCorner");
					</#if>
					<#if (lastSysId != "")>
						$("#${lastSysId}").addClass("rightRoundCorner");
					</#if>
				</script>
			</div>
		</td>
	</tr>
</table>
</div>
<!-- 下方菜单和iframe -->
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top" width="197px" id="_menuContainer" class="menu_container">
      <div id="firstpane" class="menu_list">
      	<#list funcGroupLst as mGroup>
      	<p class="menu_head">
	      	<img alt="" src="${ctx}/images/menu_plus.png"/>&nbsp;${mGroup.name}
      	</p>
      	<div class="menu_line"></div>
      	<div class="menu_body">
      		<#list mGroup.itemList as mItem>
      		<div class="menu_content">
						<a id="${mItem.id}" href="####" onclick="smenuOpen('${ctx}' + '${mItem.url}' + '?fid=' + '${mItem.id}', '${mItem.id}', '${mGroup.name}', '${mItem.name}')"><label class="menu_tip">▪</label>${mItem.name}</a>
      		</div>
      		</#list>
      	</div>
      	</#list>
      </div>
    </td>
    <td width="6px" class="menu2"></td>
    <td valign="top" style="padding:4px;">
      <!-- 
      onload="Javascript:SetSkyWinHeight(this)"
      ${ctx}/permit/help/index.do?pageId=1
       -->
      <iframe width="100%" scrolling="no" frameborder="false" allowtransparency="true" style="border: medium none;" 
      	src="" id="sampleframe" name="sampleframe" 
      	></iframe>
    </td>
	</tr>
</table>

<script>
<#if (sysId != "")>
	//$("#${sysId}").click();
</#if>
	setTimeout(function(){
		$("#sampleframe").attr("src", "${ctx}/permit/portlet/default.do");
	},1000);
</script>
</body>
</html>