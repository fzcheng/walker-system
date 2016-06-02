<html>
<head>
<title>demo系统</title>
<meta http-equiv="cache-control" content="max-age=7200">

<link rel="stylesheet" type="text/css" href="${ctx}/style/public.css">

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript" src="${ctx}/script/lib/modal/jquery_dialog.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/script/lib/modal/jquery_dialog.css">
    
<script type="text/javascript">
	$(document).ready(function(){
		
		var sltMenu;
		
		$(".menu_item").mouseover(function(){
			if($(this).css("background") != "#7fcfd0"){
				$(this).css("background","#d0f3f2");
			}
		});
		$(".menu_item").mouseout(function(){
			if($(this).attr("class") == "selected_Menu_item" 
					&& $(this).css("background") == ""){
				$(this).css("background","#7fcfd0");
				return;
			}
			if($(this).css("background") != "#7fcfd0"){
				$(this).css("background","none");
				//alert($(this).css("background"));
			}
		});
		
	  $(".menu_item").click(function(){
	  	sltMenu = $(".selected_Menu_item");
	  	if(sltMenu != null){
		  	sltMenu.removeClass("selected_Menu_item");
		  	sltMenu.addClass("menu_item");
		  	sltMenu.css("background","none");
	  	}
	  	$(this).removeClass("menu_item");
	  	$(this).addClass("selected_Menu_item");
	  	$(this).css("background","#7fcfd0");
	  	sltMenu = $(this);
	  });
	  
	  $("selected_Menu_item").mouseout(function(){
		  alert("select mouse out!");
	  });
	});
	
	function smenuOpen(url){
		document.getElementById("sampleframe").style.height = "500";
		$("#sampleframe").attr("src", url);
		//window.frames.sampleframe.location.href = url;
	}
	
	/*点击菜单组隐藏、显示菜单项*/
	function showMenu(groupId){
		var menuList = getElementsByClassName(groupId,"tr");
		for(var j=0;j<menuList.length;j++){
			if(menuList[j].style.display == "block"){
				menuList[j].style.display = "none";
			} else {
				menuList[j].style.display = "block";
			}
		}
		var menuImg = document.getElementById("img_" + groupId);
		var imgSrc = menuImg.src;
		if(imgSrc.indexOf("menu_tip_add") >= 0){
			menuImg.src = "${ctx}/images/public/menu_tip.gif";
		} else {
			menuImg.src = "${ctx}/images/public/menu_tip_add.gif";
		}
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
	
	/*返回首页*/
	function goHome(){
		document.getElementById("sampleframe").src = "${ctx}/index.do";
	}

</script>
</head>
  
<body style="padding: 0; margin: 0; height:100%;">
    
<table id="mainTable" width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td height="50" width="100%">
			<table width="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td width="191" height="50" valign="bottom" style="background:url('images/public/logo_notshow.gif')">
						<table width="100%" cellpadding="0" cellspacing="0" border="0">
							<tr><td height="*">&nbsp;</td></tr>
							<tr><td height="11" style="border-right:1px solid #7fcfd0;font-size:3px;background-color:#dff5f6;">&nbsp;</td></tr>
						</table>
					</td>
					<td valign="top" style="background-color:#ffffff;">
						<table width="100%" height="40" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td style="border-bottom:1px solid #65abac;background-color:#dff5f6;">
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td width="280">欢迎！&nbsp;&lt;
												<span style="font-weight:bold;">${username}</span>&gt;
												<span>&nbsp;[&nbsp;<a href="###" onclick="goHome();">首页</a>&nbsp;]</span>
											</td>
											<td align="right">
												<span><a href="#" onclick="smenuOpen('${ctx}' + '/permit/about.do')">关于</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
												<span><a href="#" onclick="smenuOpen('${ctx}' + '/permit/person/index.do')">个人设置</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
												<span><a href="#" onclick="smenuOpen('${ctx}/permit/help/index.do')">帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
												<a href="${ctx}/j_spring_security_logout">退出</a>
											</td>
											<td width="10">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="100%">
			<table id="ctable" width="100%" height="100%" cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td width="191" class="menu_bg" valign="top">
						<table width="191" cellpadding="0" cellspacing="0" class="menuTable" style="table-layout:fixed;">
						  <#if (funcGroupLst??)>
						  <#assign mgcount = 0>
						  <#list funcGroupLst as mGroup>
						  	<#if mGroup.id != "HOME">
									<tr>
										<td class="menu_group" valign="middle" onclick="showMenu('${mGroup.id}')" style="cursor:pointer;">
											<table width="190px" cellpadding="0" cellspacing="0" border="0">
												<tr>
													<td width="*" height="28px">
													<#if (mgcount == 0)>
														<img id="img_${mGroup.id}" src="${ctx}/images/public/menu_tip.gif"/>&nbsp;${mGroup.name}
													<#else>
														<img id="img_${mGroup.id}" src="${ctx}/images/public/menu_tip_add.gif"/>&nbsp;${mGroup.name}
													</#if>
													</td>
													<td width="60px" align="right">
													<img src="${ctx}/images/public/m_folder.gif" style="margin-right:10px;"/>
													</td>
												</tr>
											</table>
											<!-- 
											<div id="g1" style="width:190px;">
											</div>
											 -->
										</td>
									</tr>
									<#list mGroup.itemList as mItem>
										<#if (mgcount == 0)>
											<tr class="${mGroup.id}" style="display:block;">
										<#else>
											<tr class="${mGroup.id}" style="display:none;">
										</#if>
										<td class="menu_item" onclick="smenuOpen('${ctx}' + '${mItem.url}' + '?fid=' + '${mItem.id}')">
										${mItem.name}
										</td></tr>
									</#list>
									<#assign mgcount = mgcount+1>
								</#if>
							</#list>
							</#if>
							<!-- -->
							<tr class="22" style="display:block;">
								<td class="menu_item" onclick="smenuOpen('${ctx}' + '/permit/doc/index.do')">
									知识列表
								</td>
							</tr>
							 
						</table>
					</td>
					<td width="6">
						<div></div>
					</td>
					<td valign="top" id="container"><iframe id="sampleframe" name="sampleframe" width="100%" height="500px"
							SCROLLING="NO" frameborder="0" src="${ctx}/permit/help/index.do?pageId=1" style="border: 0px solid #cecece;"
							onload="Javascript:SetSkyWinHeight(this)"></iframe>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>