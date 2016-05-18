package com.walkersoft.system.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.SystemAction;
import com.walkersoft.system.entity.UserCoreEntity;

@Controller
public class PersonAction extends SystemAction {

//	@Autowired
//	private UserManagerImpl userManager;
	
	private static final String PARAMETER_OLD_PASS = "p_op";
	private static final String PARAMETER_NEW_PASS = "p_np";
	private static final String PARAMETER_CON_PASS = "p_cp";
	
	private static final String MSG_TIP_1 = "输入的'老密码'或'新密码'不正确。";
	private static final String MSG_TIP_2 = "输入的'老密码'不正确。";
	private static final String MSG_TIP_3 = "输入的'确认密码'不正确。";
	private static final String MSG_TIP_OK = "密码修改成功!";
	
	private static final String LOG_MSG_EDIT = "用户修改了密码为：";
	
	private static final String ATTRIBUTE_TIP_NAME = "msg";
	
	@RequestMapping(value = "permit/person/index")
	public String index(Model model){
		model.addAttribute("userInfo", this.getCurrentUser());
		return BASE_URL + "person";
	}
	
	@RequestMapping(value = "permit/person/show_info")
	public String showInfo(Model model){
		model.addAttribute("userInfo", this.getCurrentUser());
		return BASE_URL + "person_info";
	}
	
	@RequestMapping(value = "permit/person/editPass")
	public String editPass(){
		return BASE_URL + "person_pass";
	}
	
	@RequestMapping(value = "permit/person/savePass")
	public String savePass(Model model){
		String oldPass = this.getParameter(PARAMETER_OLD_PASS);
		String newPass = this.getParameter(PARAMETER_NEW_PASS);
		String confirmPass = this.getParameter(PARAMETER_CON_PASS);
		
		if(StringUtils.isEmpty(oldPass) || StringUtils.isEmpty(newPass)){
			model.addAttribute(ATTRIBUTE_TIP_NAME, MSG_TIP_1);
			return BASE_URL + "person_tip";
		}
		
		UserCoreEntity currentUser = getCurrentUser();
		if(!MyApplicationConfig.encodePassword(oldPass, currentUser.getLoginId()).equals(currentUser.getPassword())){
			model.addAttribute(ATTRIBUTE_TIP_NAME, MSG_TIP_2);
			return BASE_URL + "person_tip";
		}
		
		if(!newPass.equals(confirmPass)){
			model.addAttribute(ATTRIBUTE_TIP_NAME, MSG_TIP_3);
			return BASE_URL + "person_tip";
		}
		
		/* 保存修改密码 */
		String encodedPass = this.setUserPassword(currentUser.getId(), newPass);
//		String encodedPass = MyApplicationConfig.encodePassword(newPass, currentUser.getLoginId());
//		userManager.execEditPassword(currentUser.getId(), encodedPass);
//		/* 更新缓存 */
//		userCacheProvider.getCacheData(currentUser.getId()).setPassword(encodedPass);
		
		systemLog(new StringBuilder().append(LOG_MSG_EDIT)
				.append(encodedPass).append(":")
				.append(newPass).toString(), LogType.Edit);
		
		model.addAttribute(ATTRIBUTE_TIP_NAME, MSG_TIP_OK);
		return BASE_URL + "person_tip";
	}
}
