package com.walkersoft.report.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.system.SystemAction;

/**
 * BIRT报表展示首页
 * @author shikeying
 * @date 2014-8-9
 *
 */
@Controller
public class BirtReportAction extends SystemAction {

	private static final String URL_INDEX = "report/birt/index";
			
	@RequestMapping("report/birt/index")
	public String index(){
		return URL_INDEX;
	}
}
