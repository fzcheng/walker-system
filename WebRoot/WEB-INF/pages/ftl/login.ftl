<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>My 管理平台</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" type="text/css" href="${ctx}/style/login-2.css"/>

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript">
$(function(){
	//	切换登陆
	$('.loginFuncNormal').click(function(){
		$('#loginBlock').removeClass("tab-2");
		$('#loginBlock').addClass("tab-1");
		});
	$('.loginFuncMobile').click(function(){
		$('#loginBlock').removeClass("tab-1");
		$('#loginBlock').addClass("tab-2");
		});
	//输入时样式
	$('.luru').mouseenter(function(){
		$('.loginFormIpt-focus').removeClass("loginFormIpt-focus");
		$(this).addClass("loginFormIpt-focus");
	});
  //登陆按钮
	 $('#loginBtn').hover(function(){
         $(this).removeClass("btn-login");
		 $(this).addClass("btn-login-hover");
                      },function(){
         $(this).removeClass("btn-login-hover");
		 $(this).addClass("btn-login");
   });
})

$(document).ready(function(){
	
});

function login(){
	var user = $.trim($("#j_username").val());
	var pass = $.trim($("#j_password").val());
	if(user == ""){
		return tip_user("请输入用户名!", "j_username");
	}
	if(!validPassword(user)){
		return tip_user("用户名只能是‘字母、数字、下划线或者他们的组合’。", "j_username");
	}
	if(pass == ""){
		return tip_user("请输入密码!", "j_password");
	}
	if(!validPassword(pass)){
		return tip_user("密码只能是‘字母、数字、下划线或者他们的组合’。", "j_password");
	}
	
	<#if (useValidateCode == true)>
	var validCode = $("#j_captcha");
	var codeVal = $.trim(validCode.val());
	if(codeVal == ""){
		return tip_user("请输入验证码!", "j_captcha");
	}
	</#if>
	
	return true;
}

function tip_user(msg, compid){
	var sysTip = $("#sys_tip");
	sysTip.html(msg);
	$("#"+compid).focus();
	return false;
}

/**
 * 重置按钮动作
 */
function clearInput(){
	$("#j_username").val("");
	$("#j_password").val("");
	$("#j_captcha").val("");
}

function getCode(){
	document.getElementById("checkcode").src = "${ctx}/jcaptcha";
}
</script>
</head>
  
<body style="padding-top: 0px;">

<header class="header">
	<h1 class="headerLogo"><a href="#" title="系统名称"><img src="${ctx}/images/login/163logo.gif" alt="系统名称"/></a></h1>
	<a class="headerIntro" href="#" title="My 管理平台V1.0"><span class="unvisi">My 管理平台V1.0</span></a>
	<nav class="headerNav"> <a href="#" target="_blank">免费试用</a><a href="#" target="_blank">使用帮助</a><a href="#" target="_blank">插件下载</a><a href="#" target="_blank">免责声明</a>&nbsp;|&nbsp;<a href="#" target="_blank">捐助</a> </nav>
</header>
<section class="main" id="mainBg" style="background-color: rgb(225, 239, 227)">
	<div class="main-inner" id="mainCnt" style="background-image: url(${ctx}/images/login/140315_yixin_cnt.jpg); background-position: 50% 0%; background-repeat: no-repeat no-repeat;">
		<div class="themeCtrl"> <a id="prevTheme" href="javascript:void(0);" onclick="indexLogin.showThemes(indexLogin.oTheme.getPrev())" title="上一张"> </a> <a id="nextTheme" href="javascript:void(0);" onclick="indexLogin.showThemes(indexLogin.oTheme.getNext())" title="下一张"></a> </div>
	</div>
	
	<!--登录框-->
	<div id="loginBlock" class="login tab-1">
		<div class="loginFunc">
			<div id="lbNormal" class="loginFuncNormal"> 用户名登录 </div>
		</div>
		<div class="loginForm">
		<table width="245" border="0" cellspacing="0" cellpadding="0" align="center" style="margin-top:-20px; margin-bottom:10px;">
	<tr>
		<td class="tishi">
		<span id="sys_tip">
		<#if (msg == "expired")>
		用户登陆超时
		<#else>
		${msg}
		</#if>
		</span>
		</td>
	</tr>
</table>
<form method="post" action="${ctx}/j_spring_security_check" onsubmit="return login();">
	<div id="idInputLine" class="loginFormIpt luru"> <b class="ico ico-uid"></b>
		<input class="loginFormTdIpt loginFormTdIpt-focus" tabindex="1" title="请输入帐号" id="j_username" name="j_username" type="text" maxlength="50" value="" autocomplete="off" placeholder="用户名" />
		
		<div id="mobtips"></div>
		<label for="idInput" class="placeholder" id="idPlaceholder">邮箱帐号或手机号</label>
		<div id="idInputTest"></div>
	</div>
<table width="245" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td class="password luru" ><input class="inptu_password" title="password" id="j_password" name="j_password" type="password" placeholder="密码" tabindex="2"/></td>
	</tr>
</table>
		
<#if (useValidateCode == true)>		
<table width="245" border="0" cellspacing="0" cellpadding="0" align="center" style=" margin-top:15px;">
	<tr>
		<td class="yzm luru">
			<input class="inptu_yzm"title="验证码" id="j_captcha" name="j_captcha" type="text" placeholder="验证码" tabindex="3"/>
		</td>
		<td width="10"></td>
		<td><img id="checkcode" src="${ctx}/jcaptcha" width="114" height="42" style="float:right;" onclick="getCode();"/></td>
	</tr>
</table>
</#if>
	<div class="loginFormCheck">
		<div id="lfAutoLogin" class="loginFormCheckInner"> <b class="ico ico-checkbox"></b>
			<label id="remAutoLoginTxt" for="remAutoLogin">
				<input title="十天内免登录" class="loginFormCbx" type="checkbox" id="remAutoLogin"/>
				十天内免登录</label>
			<div id="whatAutologinTip">为了您的信息安全，请不要在网吧或公用电脑上使用此功能！ </div>
		</div>
		<div class="forgetPwdLine"> <a class="forgetPwd" href="#" target="_blank" title="找回密码">忘记密码了?</a> </div>
	</div>
	<div class="loginFormBtn">
		<button id="loginBtn" class="btn btn-login" tabindex="4" type="submit">登&nbsp;&nbsp;录</button>
		<a id="lfBtnReg" class="btn btn-reg" href="#" tabindex="5" onclick="clearInput();">重&nbsp;&nbsp;置</a> </div>
	<div class="loginFormConf">
		<div class="loginFormVer"> 版本: <a id="styleConf" href="javascript:void(0);">默认版本 <b class="ico ico-arr ico-arr-d"></b></a> </div>
		<div class="loginFormService" id="loginSSL" style="display: block;">正使用SSL登录</div>
		<div class="loginFormService" id="AllSSL" style="display: none;">
			<input title="全程SSL" class="loginFormCbx" type="checkbox" id="AllSSLCkb"/>
			&nbsp;
			<label style="vertical-align:baseline;" for="AllSSLCkb">全程SSL</label>
		</div>
		<div class="loginFormCheckInner" style="display:none">
			<input class="loginFormCbx loginFormSslCbx" type="checkbox" title="SSL安全登录" id="SslLogin" checked="checked"/>
			<label class="loginFormSslText" for="SslLogin">&nbsp;<span style="font-family:verdana;">SSL</span>安全登录</label>
		</div>
			
	</div>
</form>
			<div class="ext" id="loginExt">
			<ul id="extText"><li class="ext-1"> <a href="#" target="_blank" style="">使用超级管理员supervisor登录</a></li><li class="ext-2"> <a href="#" target="_blank" style="">supervisor默认密码123456</a></li></ul>
				<div id="extMobLogin">
					<h3 class="ext-tt"><a href="#" target="_blank">有手机号码邮箱，就可以直接登录</a></h3>
					<p> <a href="#" target="_blank"> <span>无需注册</span><var>|</var>邮件到达免费提醒<var>|</var>送2G网盘 </a> </p>
				</div>
				<div id="extMobLogin2">
					<button class="btn btn-moblogin2" id="extBtnMoblogin" type="button">还没设置手机号登录？</button>
					<div class="setMobLoginInfo">设置后，可用手机号直接登录（完全免费）</div>
				</div>
			</div>
		</div>
	</div>
</section>

</body>
</html>