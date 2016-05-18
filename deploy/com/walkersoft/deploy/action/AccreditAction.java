package com.walkersoft.deploy.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.deploy.entity.AccreditEntity;
import com.walkersoft.deploy.manager.AccreditManagerImpl;
import com.walkersoft.system.SystemAction;

/**
 * 程序授权管理
 * @author shikeying
 * @date 2014-11-3
 *
 */
@Controller
public class AccreditAction extends SystemAction {

	private static final String URL_INDEX = "deploy/accredit/accredit_index";
	private static final String URL_RELOAD = "deploy/accredit/accredit_list";
	private static final String URL_ADD = "deploy/accredit/accredit_add";
	
	@Autowired
	private AccreditManagerImpl accreditManager;
	
	@RequestMapping("supervisor/accredit/index")
	public String index(Model model){
		loadList(model, accreditManager.queryAccreditList());
		return URL_INDEX;
	}
	
	@RequestMapping(value = "supervisor/accredit/reload")
	public String reloadPage(Model model){
		loadList(model, accreditManager.queryAccreditList());
		return URL_RELOAD;
	}
	
	@RequestMapping(value = "supervisor/accredit/show_add")
	public String showAddPage(){
		return URL_ADD;
	}
	
	@RequestMapping(value = "supervisor/accredit/save")
	public void save(@ModelAttribute("entity") AccreditEntity entity
			, HttpServletResponse response) throws IOException{
		Assert.notNull(entity);
		if(entity.getAuthType() == 0 && StringUtils.isEmpty(entity.getMacAddress())){
			this.ajaxOutPutText("必须输入mac地址");
			return;
		}
		if(entity.getAuthType() == 1 && (entity.getStartTime() == 0 || entity.getEndTime() == 0)){
			this.ajaxOutPutText("必须输入时间段");
			return;
		}
		try {
			accreditManager.execSave(entity);
			this.ajaxOutPutText(MESSAGE_SUCCESS);
		} catch (Exception e) {
			this.ajaxOutPutText(MESSAGE_ERROR);
		}
	}
	
	/**
	 * 生成授权lib: walker-security.jar，把授权文件写进去，供用户使用。<br>
	 * 暂不实现。
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	public void generateLisenceLib(long id
			, HttpServletResponse response) throws IOException{
		// 找到是否存在walker-security.jar包
		
	}
	
//	private void doFindSecurityLib(){
//		File appLibFolder = new File(JarDeployer.webappLibAbsolute);
//		File[] jarList = appLibFolder.listFiles();
//		File securityJar = null;
//		if(jarList != null){
//			for(File f : jarList){
//				// 因为都是jar文件，不会存在其他
//				if(f.getName().startsWith("walker-security") && f.getName().indexOf("-admin") < 0){
//					securityJar = f;
//					break;
//				}
//			}
//		}
//		if(securityJar == null){
//			throw new IllegalStateException("not found jar lib: walker-security.jar!");
//		}
//		JarFile jar = null;
//		JarOutputStream jaroutput = null;
//	}
	
}
