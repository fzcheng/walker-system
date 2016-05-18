package com.walkersoft.system.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.db.page.support.GenericPager;
import com.walker.file.FileEngine;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.web.view.WebContextAction;
import com.walkersoft.application.log.MyLogDetail;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.dao.FileDao;
import com.walkersoft.system.dao.SystemBaseDao;
import com.walkersoft.system.pojo.ExportObj;

@Service("systemBaseManager")
public class SystemBaseManagerImpl {

	@Autowired
	private SystemBaseDao systemBaseDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private FileEngine fileEngine;
	
	/**
	 * 分页查询日志记录
	 * @param loginId 查询的用户'登录ID'
	 * @param logType 日志类型
	 * @param start 开始时间（毫秒值）
	 * @param end   结束时间（毫秒值）
	 * @return
	 */
	public GenericPager<MyLogDetail> queryLogs(String loginId, LogType logType, long start, long end){
		return systemBaseDao.searchLog(loginId, logType, start, end);
	}
	
	public GenericPager<MyLogDetail> queryLogs(int startIndex, int pageSize){
		return systemBaseDao.searchLog(startIndex, pageSize);
	}
	
	/**
	 * 返回上传文件记录集合
	 * @param storeType 文件存储类型
	 * @param filename 文件名
	 * @param ext 后缀
	 * @param start 开始日期(毫秒)
	 * @param end 结束日期
	 * @return
	 */
	public GenericPager<FileMeta> queryFiles(StoreType storeType, String filename
			, String ext, long start, long end){
		return fileDao.searchFile(storeType, filename, ext, start, end);
	}
	
	/**
	 * 删除一个上传文件资源
	 * @param id
	 * @throws FileOperateException
	 */
	public void execRemoveFile(String id)throws FileOperateException{
		fileEngine.removeFile(id);
	}
	
	/**
	 * 保存导出数据库SQL记录
	 * @param exportType 导出类型：0_全表，1_选择表
	 * @param summary
	 * @param creatorName
	 */
	public FileMeta execSaveExport(int exportType, String summary, String creatorName){
		String showFileName = "export_" + NumberGenerator.getSequenceNumber() + ".SQL";
		FileMeta fm = fileEngine.createEmptyFile(showFileName, WebContextAction.TEXT_PLAIN);
		systemBaseDao.insertExport(exportType, summary, fm.getId(), creatorName);
		return fm;
	}
	
	/**
	 * 保存导入的记录，仅是记录，不包括：导入的SQL数据!
	 * @param summary
	 * @param creatorName
	 * @param files
	 */
	public void execSaveImport(String summary, String creatorName, List<FileMeta> files){
		List<String> fileIds = null;
		try {
			// 保存上传文件
			fileIds = fileEngine.writeFiles(files);
		} catch (FileOperateException e) {
			throw new ApplicationRuntimeException("file write failed in save department!", e);
		}
		// 只会存在一个文件
		systemBaseDao.insertImport(summary, fileIds.get(0), creatorName);
	}
	
	/**
	 * 分页返回数据库导出列表
	 * @return
	 */
	public GenericPager<ExportObj> queryExportList(){
		return systemBaseDao.getExportList();
	}
	
	public GenericPager<ExportObj> queryImportList(){
		return systemBaseDao.getImportList();
	}
}
