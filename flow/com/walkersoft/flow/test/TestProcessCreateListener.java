package com.walkersoft.flow.test;

import com.walker.flow.core.biz.BizDTO;
import com.walker.flow.core.listener.ProcessCreateListener;

public class TestProcessCreateListener implements ProcessCreateListener {

	@Override
	public void onWorkflowCreate(BizDTO arg0, BizDTO arg1, Object arg2) {
		// TODO Auto-generated method stub
		System.out.println("bizDTO = " + arg0);
		
	}

}
