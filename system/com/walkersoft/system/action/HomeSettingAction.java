package com.walkersoft.system.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.file.FileEngine.StoreType;
import com.walker.file.FileMeta;
import com.walker.file.FileUtils;
import com.walker.infrastructure.utils.JarDeployer;
import com.walkersoft.application.MyArgumentsNames;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.manager.HomeSettingManagerImpl;

/**
 * 首页设置管理
 * @author shikeying
 * @date 2014-3-19
 *
 */
@Controller
public class HomeSettingAction extends SystemAction {

	private static final String LOGO_INDEX = "home_logo";
	private static final String LOGO_NAME = "logo.png";
	
	@Autowired
	private HomeSettingManagerImpl homeSettingManager;
	
	@RequestMapping(value = "admin/home/logo")
	public String logoIndex(Model model){
		return BASE_URL + LOGO_INDEX;
	}
	
	/**
	 * 返回当前logo图片的字节内容
	 * @param response
	 */
	@RequestMapping(value = "permit/admin/home/logofile")
	public void getCurrentLogo(HttpServletResponse response){
		this.ajaxOutputFileStream(ResponseFormat.ImagePng.getValue(), new File(getLogoImagesAbsolutePath()+LOGO_NAME));
	}
	
	/**
	 * 返回系统logo图片存放绝对路径，如：d:/webapp/walker-web/images/
	 * @return
	 */
	protected String getLogoImagesAbsolutePath(){
		String imagesPath = (String)this.getArgumentsValue(MyArgumentsNames.LogoPath);
		StringBuilder logoPath = new StringBuilder(JarDeployer.webappRootAbsolute);
		if(imagesPath.endsWith(FileUtils.FILE_SEPARATOR)){
			logoPath.append(imagesPath);
		} else {
			logoPath.append(imagesPath);
			logoPath.append(FileUtils.FILE_SEPARATOR);
		}
		return logoPath.toString();
	}
	
	@RequestMapping(value = "permit/admin/home/logoupload")
	public String saveUploadLogo() throws IOException{
		List<FileMeta> uploadFiles = this.getUploadFiles("f", StoreType.FileSystem);
		if(uploadFiles == null || uploadFiles.size() == 0){
			throw new ApplicationRuntimeException("未上传任何文件。");
		}
		homeSettingManager.execUploadLogo(uploadFiles
				, getLogoImagesAbsolutePath(), LOGO_NAME);
		return BASE_URL + LOGO_INDEX;
	}
}
