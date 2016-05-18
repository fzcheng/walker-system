<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>demo系统</title>

<link rel="stylesheet" type="text/css" href="${ctx}/style/login.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/layout.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/login-theme.css">

<script type="text/javascript" src="${ctx}/script/public.js"></script>
<script type="text/javascript" src="${ctx}/script/lib/jquery-core.js"></script>

<script type="text/javascript">
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
</script>
</head>
  
<body>

	<!-- 
	在使用firefox的firebug调试时，浏览器会自动请求验证码以让开发人员看到验证码内容，
	所以导致总是请求两次，这时输入的和后台得到的是不一样的。太智能真害人。
	 -->
	 
<!-- 
<span>${msg}</span>
<form action="${ctx}/j_spring_security_check" method="post">
	用户名：<input type="text" id="j_username" name="j_username"/><br>
	密码：<input type="password" id="j_password" name="j_password"/><br>
<#if (useValidateCode == true)>
	验证码：<input type="text" id="j_captcha" name="j_captcha"/>
	<img id="checkcode" alt="验证码" width="100px" height="32px" src="${ctx}/jcaptcha"/>
</#if>
	<input type="submit" value="登 录"/>
</form>
 -->
 
<form action="${ctx}/j_spring_security_check" method="post" onsubmit="return login();">
<div id="logincontainer">
	<div id="loginbox">
    	<div id="loginheader">
        <img src="${ctx}/img/cp_logo_login.png" alt="Control Panel Login" />
      </div>
        <div id="innerlogin">
        	<p id="sys_tip">${msg}</p>
            	<p align="left">输入用户：</p>
            	<input type="text" class="logininput" id="j_username" name="j_username"/>
                <p align="left">输入密码：</p>
            	<input type="password" class="logininput" id="j_password" name="j_password"/>
                
              <#if (useValidateCode == true)>
                <p align="left">输入验证码：</p>
                <p align="left">
                <table border="0" cellpadding="0" cellspacing="0">
                	<tr>
                		<td width="130px" style="padding:0;" valign="middle">
				            	<input type="text" class="logininput" id="j_captcha" name="j_captcha" style="width:138px; margin: 0px 0;"/>
                		</td>
                		<td width="90px" valign="middle">
			                <img class="codeinput" id="checkcode" alt="验证码" height="28px" src="${ctx}/jcaptcha"/>
                		</td>
                	</tr>
                </table>
                </p>
              </#if>
               	<input type="submit" class="loginbtn" value="提交，进入首页" /><br/>
                <p><a href="#" title="Forgoteen Password?">忘 记 密 码 了? 找 回!</a></p>
        </div>
    </div>
    <img src="${ctx}/img/login_fade.png" alt="Fade" />
</div>
</form>

</body>
</html>