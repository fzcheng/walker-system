package com.walkersoft.websocket.test;

import com.walker.websocket.Action;
import com.walker.websocket.ActionInvokeException;
import com.walker.websocket.Request;
import com.walker.websocket.Response;

public class TestSocketAction implements Action {

	@Override
	public void handleAction(Request request, Response response)
			throws ActionInvokeException {
		response.setContent("业务结果1");
	}

	@Override
	public String matchUrl() {
		return "test";
	}

}
