package com.walkersoft.system.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.app.ApplicationRuntimeException;
import com.walker.file.FileEngine;
import com.walker.file.FileMeta;
import com.walker.file.FileOperateException;
import com.walker.file.FileUtils;
import com.walker.infrastructure.utils.FileCopyUtils;
import com.walker.infrastructure.utils.NumberGenerator;

/**
 * 首页设置服务
 * @author shikeying
 *
 */
@Service("homeSettingManager")
public class HomeSettingManagerImpl {

	@Autowired
	private FileEngine fileEngine;
	
	/**
	 * 上传logo图片
	 * @param files 上传的文件集合，目前只有一个
	 * @param filePath 要保存的图片路径
	 * @param logoName 要保存的文件名，当前为logo.png
	 */
	public void execUploadLogo(List<FileMeta> files, String filePath, String logoName){
		if(files != null){
			try {
				fileEngine.writeFiles(files);
			} catch (FileOperateException e) {
				throw new ApplicationRuntimeException("file write failed in save department!", e);
			}
			
			File oldFile = new File(filePath + logoName);
			
			try{
				// 如果存在同名文件，先备份并改名
				if(oldFile.exists()){
					StringBuilder destName = new StringBuilder(filePath);
					destName.append("logo_");
					destName.append(NumberGenerator.getSequenceNumber());
					destName.append(logoName.substring(logoName.indexOf(".")));
					
					File destFile = new File(destName.toString());
					FileCopyUtils.copy(oldFile, destFile);
					
					//删除原文件
					FileUtils.deleteFile(oldFile);
				}
				
				// 把新文件写到logo目录中，同时命名为logo.png
				FileCopyUtils.copy(files.get(0).getContent(), oldFile);
			} catch(IOException ioe){
				throw new ApplicationRuntimeException();
			}
		}
	}
}
