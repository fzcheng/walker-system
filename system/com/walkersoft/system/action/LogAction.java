package com.walkersoft.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.manager.SystemBaseManagerImpl;

@Controller
public class LogAction extends SystemAction {
	
	@Autowired
	private SystemBaseManagerImpl systemBaseManager;
	
	private static final String PARAMETER_LOGIN_ID = "loginId";
	private static final String PARAMETER_LOG_TYPE = "logType";
	private static final String PARAMETER_START_TIME = "start";
	private static final String PARAMETER_END_TIME = "end";
	
	@RequestMapping(value = "admin/log/index")
	public String index(Model model){
		setDefaultSearchCondition(model);
		loadList(model);
		return BASE_URL + "log";
	}
	
	@RequestMapping(value = "permit/admin/log/reload")
	public String reloadPage(Model model){
		loadList(model);
		return BASE_URL + "log_list";
	}
	
	private void loadList(Model model){
		// 查询参数：登录ID、日志类型
		String loginId = this.getParameter(PARAMETER_LOGIN_ID);
		String logTypeStr = this.getParameter(PARAMETER_LOG_TYPE);
		LogType logType = null;
		if(StringUtils.isNotEmpty(logTypeStr)){
			logType = MyLogDetail.getLogType(Integer.parseInt(logTypeStr));
		}
		// 查询参数：开始、结束时间
		String startStr = this.getParameter(PARAMETER_START_TIME);
		String endStr   = this.getParameter(PARAMETER_END_TIME);
		long start = 0L;
		long end   = 0L;
		if(StringUtils.isNotEmpty(startStr)){
			start = DateUtils.getDateLongEarly(startStr);
		} else
			start = DateUtils.getCurrentMonthFirstDayEarly();
		if(StringUtils.isNotEmpty(endStr)){
			end = DateUtils.getDateLongLate(endStr);
		} else
			end = DateUtils.getTodayLongLate();
		
		GenericPager<MyLogDetail> pager = systemBaseManager.queryLogs(loginId, logType, start, end);
		PagerView<MyLogDetail> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
		model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
	}
	
	@RequestMapping(value = "permit/admin/log/flush")
	public String flush(Model model){
		this.flushSystemLog();
		loadList(model);
		return BASE_URL + "log_list";
	}
}
