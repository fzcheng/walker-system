package com.walkersoft.flow.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walkersoft.flow.FlowAction;
import com.walkersoft.flow.manager.TestManagerImpl;
import com.walkersoft.flow.pojo.TestProject;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 测试业务流程演示
 * @author shikeying
 * @date 2014-8-21
 *
 */
@Controller
public class TestAction extends FlowAction {

	private static final String URL_INDEX = "flow/test/index";
	private static final String URL_RELOAD = "flow/test/prj_list";
	private static final String URL_ADD = "flow/test/prj_add";
	private static final String URL_EDIT = "flow/test/prj_edit";
	
	@Autowired
	private TestManagerImpl testManager;
	
	@RequestMapping("flow/test/index")
	public String index(Model model){
		loadList(model, testManager.queryProjectList());
		return URL_INDEX;
	}
	
	@RequestMapping(value = "permit/flow/test/reload")
	public String reloadPage(Model model){
		loadList(model, testManager.queryProjectList());
		return URL_RELOAD;
	}
	
	@RequestMapping(value = "permit/flow/test/show_add")
	public String showAdd(Model model){
		model.addAttribute("processes", this.getAvailablePrimaryProcessList());
		return URL_ADD;
	}
	
	@RequestMapping(value = "permit/flow/test/save")
	public void save(TestProject project
			, HttpServletResponse response) throws IOException{
		assert (project != null);
		project.setCreateTime(NumberGenerator.getSequenceNumber());
		project.setId(NumberGenerator.generatorHexUUID());
		UserCoreEntity user = this.getCurrentUser();
		testManager.execSaveTestProject(project, user.getId(), user.getName());
		this.ajaxOutPutText(MESSAGE_SUCCESS);
	}
	
	@RequestMapping(value = "permit/flow/test/show_edit")
	public String showEditPage(Model model, String id){
		Assert.hasText(id);
		model.addAttribute("project", testManager.queryProjectInfo(id));
		return URL_EDIT;
	}
}
