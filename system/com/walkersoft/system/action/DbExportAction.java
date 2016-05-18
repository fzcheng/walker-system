package com.walkersoft.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.db.export.DataExportor;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.infrastructure.utils.LongCalendar;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.manager.SystemBaseManagerImpl;

/**
 * 数据库导入、导出功能。
 * @author shikeying
 * @date 2014-8-15
 *
 */
@Controller
@RequestMapping("supervisor")
public class DbExportAction extends SystemAction{

	private static final String URL_EXPORT = "system/db/export_index";
	private static final String URL_EXPORT_RELOAD = "system/db/export_list";
	private static final String URL_IMPORT = "system/db/import_index";
	private static final String URL_IMPORT_RELOAD = "system/db/import_list";
	private static final String URL_IMPORT_SUCCESS= "system/db/import_success";
	
	private static final String ATTR_DATE = "time";
	private static final String ATTR_TABLES = "tables";
	
	private static final String MSG_EXPORT_RUNNING = "导出任务正在执行，不能重复执行";
	
	@Autowired
	private DataExportor defaultDatabaseExportor;
	
	@Autowired
	private SystemBaseManagerImpl systemBaseManager;
	
	@RequestMapping("db/export")
	public String exportIndex(Model model){
		String currentTime = LongCalendar.getCurrentDateForpage();
		model.addAttribute(ATTR_DATE, currentTime);
		model.addAttribute(ATTR_TABLES, defaultDatabaseExportor.getExportTables());
		loadList(model);
		return URL_EXPORT;
	}
	
	@RequestMapping(value = "db/export/reload")
	public String reloadPage(Model model){
		loadList(model);
		return URL_EXPORT_RELOAD;
	}
	
	private void loadList(Model model){
		this.loadList(model, systemBaseManager.queryExportList());
	}
	
	/**
	 * 保存导出SQL结果
	 * @param summary
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("db/save_export")
	public void saveExport(String summary, HttpServletResponse response) throws IOException{
		if(defaultDatabaseExportor == null 
				|| !defaultDatabaseExportor.isDone()){
			logger.warn(MSG_EXPORT_RUNNING);
			this.ajaxOutPutText(MSG_EXPORT_RUNNING);
			return;
		}
		int exportType = 0; // 默认全表导出
		String loginName = this.getCurrentUser().getName();
		// 保存导出记录
		FileMeta fm = systemBaseManager.execSaveExport(exportType, summary, loginName);
		
		// 排除的表
		List<String> excludeTables = new ArrayList<String>(1);
		excludeTables.add("s_db");
		
		// 开始执行导出任务，异步任务。
		defaultDatabaseExportor.export(fm.getAbsoluteFileName(), null, excludeTables);
		
		boolean failed = false;
		
		while(!defaultDatabaseExportor.isDone()){
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				logger.warn("等待导出，被打断，退出等待...");
				failed = true;
				this.ajaxOutPutText(MESSAGE_ERROR);
				break;
			}
		}
		if(!failed)
			this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// 数据库导入功能
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@RequestMapping("db/import")
	public String importIndex(Model model){
		model.addAttribute(ATTR_DATE, LongCalendar.getCurrentDateForpage());
		this.loadList(model, systemBaseManager.queryImportList());
		return URL_IMPORT;
	}
	
	@RequestMapping("db/save_import")
	public void saveImport(String summary, HttpServletResponse response) throws IOException{
		List<FileMeta> uploadFiles = this.getUploadFiles("sqlFile", StoreType.FileSystem);
		if(uploadFiles == null || uploadFiles.size() == 0){
			this.ajaxOutPutText("没有发现上传文件。");
			return;
		}
		String fileExt = null;
		for(FileMeta fm : uploadFiles){
			fileExt = fm.getFileExt();
			if(StringUtils.isEmpty(fileExt) || 
					(!fileExt.equalsIgnoreCase("sql") && !fileExt.equalsIgnoreCase("txt"))){
				this.ajaxOutPutText("导入文件仅支持后缀名：txt/sql两种。");
				return;
			}
		}
		systemBaseManager.execSaveImport(summary, this.getCurrentUser().getName(), uploadFiles);
		
		String filename = uploadFiles.get(0).getAbsoluteFileName();
		
		// 在新的线程中执行导入操作，数据量可能会有些大。
		defaultDatabaseExportor.importData(filename);
		
		boolean failed = false;
		while(!defaultDatabaseExportor.isImportDone()){
			logger.debug("+++++++++++++ 获得导入的返回状态一次：未完成，准备等待5秒...");
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				logger.warn("等待导入，被打断，退出等待...", e);
				failed = true;
				this.ajaxOutPutText(MESSAGE_ERROR);
				break;
			}
		}
		if(!failed){
			logger.info("导入成功!");
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} else {
			logger.error("导入出现未成功!");
		}
		
//		boolean result = defaultDatabaseExportor.isImportDone();
//		if(result){
//			this.ajaxOutPutText(MESSAGE_SUCCESS);
//		} else {
//			this.ajaxOutPutText("数据导入失败!");
//		}
	}
	
	@RequestMapping(value = "db/import/reload")
	public String reloadImportPage(Model model){
		this.loadList(model, systemBaseManager.queryImportList());
		return URL_IMPORT_RELOAD;
	}
	
	@RequestMapping(value = "db/import/success")
	public String showImportSuccess(){
		return URL_IMPORT_SUCCESS;
	}
}
