package com.walker.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping(value = "jsp/welcome.do")
	public String getJspPage(){
		return "welcome_jsp";
	}
	
	@RequestMapping(value = "template/welcome.do")
	public String getTemplatePage(){
		return "welcome_ftl";
	}
}
