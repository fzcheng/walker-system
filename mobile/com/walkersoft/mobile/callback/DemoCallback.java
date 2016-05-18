package com.walkersoft.mobile.callback;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail;
import com.walkersoft.mobile.support.SimpleCallback;
import com.walkersoft.system.manager.SystemBaseManagerImpl;

/**
 * 演示使用的接口调用实现。
 * @author shikeying
 * @date 2015-3-24
 *
 */
@Component
public class DemoCallback extends SimpleCallback {

	@Autowired
	private SystemBaseManagerImpl systemBaseManager;
	
	@Override
	protected List<String> getMethodList() {
		List<String> list = new ArrayList<String>();
		list.add("loadPageList");
		return list;
	}

	@Override
	protected JSONObject doInvokeData(String method, String userId)
			throws Exception {
		JSONObject data = null;
		if(method.equals("loadPageList")){
			data = this.loadPageList(userId);
		}
		return data;
	}

	@Override
	protected JSONObject doInvokeData(String method, String userId,
			JSONObject parameters) throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * 分页加载并返回日志数据集合，手机端测试分页列表使用。
	 * @param userId
	 * @return
	 */
	private JSONObject loadPageList(String userId){
		String pIndex = this.getParameter("startIndex");
		String pSize  = this.getParameter("pageSize");
		if(StringUtils.isEmpty(pIndex) || StringUtils.isEmpty(pSize)){
			throw new IllegalArgumentException();
		}
		JSONArray datas = new JSONArray();
		GenericPager<MyLogDetail> pager = systemBaseManager.queryLogs(Integer.parseInt(pIndex)
				, Integer.parseInt(pSize));
		List<MyLogDetail> logs = pager.getDatas();
		JSONObject d = null;
		int i = 1;
		for(MyLogDetail log : logs){
			d = new JSONObject();
			d.put("content", log.getContent());
			d.put("title", log.getTypeName());
			d.put("createTime", log.getCreateTime());
			d.put("icon", i + ".png");
			i++;
			datas.add(d);
		}
		return getDatasForJson(datas);
	}
}
