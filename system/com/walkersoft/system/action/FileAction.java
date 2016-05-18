package com.walkersoft.system.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.ListPageContext;
import com.walker.db.page.PagerView;
import com.walker.db.page.support.GenericPager;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.manager.SystemBaseManagerImpl;

@Controller
public class FileAction extends SystemAction {

	@Autowired
	private SystemBaseManagerImpl systemBaseManager;
	
	private static final String URL_FILE = BASE_URL + "file";
	private static final String URL_FILE_LIST = BASE_URL + "file_list";
	
	private static final String PARAMETER_STORE_TYPE = "storeType";
	private static final String PARAMETER_START_TIME = "start";
	private static final String PARAMETER_END_TIME = "end";
	private static final String PARAMETER_FILE_NAME = "filename";
	private static final String PARAMETER_FILE_EXT = "ext";
	
	@RequestMapping(value = "admin/file/index")
	public String index(Model model){
		setDefaultSearchCondition(model);
		loadList(model);
		return URL_FILE;
	}
	
	@RequestMapping(value = "permit/admin/file/reload")
	public String reloadPage(Model model){
		loadList(model);
		return URL_FILE_LIST;
	}
	
	/**
	 * 向浏览器客户端写出文件数据(下载)
	 * @param response
	 */
	@RequestMapping(value = "permit/admin/file/down")
	public void writeFile(HttpServletResponse response){
		this.writeFileToBrownser(response);
	}
	
	/**
	 * 请求服务端返回图片流信息。</p>
	 * 请求参数中必须包含ID，即：文件ID，如：permit/admin/image/view.do?id=abcdeewe222
	 * @param response
	 */
	@RequestMapping(value = "permit/admin/image/view")
	public void loadImageFileStream(HttpServletResponse response){
		this.ajaxOutputImage();
	}
	
	private static final String LOG_MSG_1 = "用户删除上传文件:";
	private static final String LOG_MSG_2 = "用户删除上传文件失败";
	
	@RequestMapping(value = "permit/admin/file/remove")
	public String removeFile(Model model){
		try {
			String id = getParameterIdValue();
			systemLog(LOG_MSG_1 + id, LogType.Delete);
			systemBaseManager.execRemoveFile(id);
		} catch (FileOperateException e) {
			// TODO Auto-generated catch block
			logger.error(LOG_MSG_2, e);
			systemLog(LOG_MSG_2);
			throw new ApplicationRuntimeException("remove upload file failed!", e);
		}
		loadList(model);
		return URL_FILE_LIST;
	}
	
	private void loadList(Model model){
		// 查询参数：文件存储类型
		/** 测试，每页只显示3条记录
		ListPageContext.setCurrentPageSize(3);
*/
		String storeTypeStr = this.getParameter(PARAMETER_STORE_TYPE);
		StoreType storeType = null;
		if(StringUtils.isNotEmpty(storeTypeStr)){
			storeType = StoreType.getStoreType(Integer.parseInt(storeTypeStr));
		}
		
		String filename = this.getParameter(PARAMETER_FILE_NAME);
		if(StringUtils.isEmpty(filename)){
			filename = null;
		}
		String ext = this.getParameter(PARAMETER_FILE_EXT);
		if(StringUtils.isEmpty(ext)){
			ext = null;
		}
		
		// 查询参数：开始、结束时间
		String startStr = this.getParameter(PARAMETER_START_TIME);
		String endStr   = this.getParameter(PARAMETER_END_TIME);
		long start = 0L;
		long end   = 0L;
		if(StringUtils.isNotEmpty(startStr)){
			start = DateUtils.getDateLongEarly(startStr);
		} 
//		else
//			start = DateUtils.getTodayLongEarly();
		if(StringUtils.isNotEmpty(endStr)){
			end = DateUtils.getDateLongLate(endStr);
		} 
//		else
//			end = DateUtils.getTodayLongLate();
		
		GenericPager<FileMeta> pager = systemBaseManager.queryFiles(storeType, filename, ext, start, end);
		PagerView<FileMeta> pagerView = ListPageContext.createPagerView(pager, DEFAULT_JS_NAME);
		model.addAttribute(DEFAULT_PAGER_VIEW_NAME, pagerView);
	}
}
