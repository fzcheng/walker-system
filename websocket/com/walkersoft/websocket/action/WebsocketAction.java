package com.walkersoft.websocket.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walkersoft.system.SystemAction;

/**
 * 测试websocket页面
 * @author shikeying
 * @date 2014-12-12
 *
 */
@Controller
public class WebsocketAction extends SystemAction {

	private static final String URL_INDEX = "websocket/demo_1";
	private static final String URL_INDEX_2 = "websocket/demo_2";
	
	@RequestMapping("test/websocket/demo_1")
	public String index(Model model){
		return URL_INDEX;
	}
	
	@RequestMapping("test/websocket/demo_2")
	public String index2(Model model){
		model.addAttribute("userId", this.getCurrentUserId());
		return URL_INDEX_2;
	}
}
