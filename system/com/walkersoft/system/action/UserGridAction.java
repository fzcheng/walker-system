package com.walkersoft.system.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.db.page.support.MapPager;
import com.walker.web.ui.jqgrid.JQGridColumn;
import com.walker.web.ui.jqgrid.JQGridColumnProvider;
import com.walkersoft.system.JQGridProviderAction;
import com.walkersoft.system.manager.UserManagerImpl;
import com.walkersoft.system.util.AbstractJQColumnProvider;

/**
 * 展示<code>JQGrid</code>组件使用的控制器，显示了用户列表，并控制列信息。
 * @author shikeying
 * @date 2014-8-6
 *
 */
@Controller
public class UserGridAction extends JQGridProviderAction {

	private static final String URL_INDEX = "system/jqgrid/user";
	
	@Autowired
	private UserManagerImpl userManager;
	
	@RequestMapping("admin/user/jqgrid_index")
	public String index(Model model){
		this.initJQGridPage(model);
		return URL_INDEX;
	}
	
	@RequestMapping("permit/admin/user/get_jqgrid_json")
	public void getUserData(HttpServletResponse response) throws IOException{
		this.doAquireJQGridData();
	}

	@Override
	protected MapPager queryMapPager(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return userManager.queryPagerUserList(pageIndex, pageSize);
	}

	@Override
	protected JQGridColumnProvider createJQGridProvider() {
		// TODO Auto-generated method stub
		return new UserColumnProvider();
	}
	
	private class UserColumnProvider extends AbstractJQColumnProvider {
		@Override
		protected void addColumns(Map<String, JQGridColumn> allColumns) {
			// TODO Auto-generated method stub
			JQGridColumn loginId = new JQGridColumn();
			loginId.setName("login_id").setIndex("login_id").setWidth(90).setAlias("用户ID");
			allColumns.put("login_id", loginId);
			
			JQGridColumn userName = new JQGridColumn();
			userName.setName("name").setIndex("name").setWidth(80).setAlias("姓名");
			allColumns.put("name", userName);
			
			JQGridColumn type = new JQGridColumn();
			type.setName("type").setIndex("type").setWidth(100).setAlias("类型");
			allColumns.put("type", type);
			
			JQGridColumn orgName = new JQGridColumn();
			orgName.setName("org_id").setIndex("org_id").setWidth(150).setAlias("所属单位");
			allColumns.put("org_id", orgName);
			
			JQGridColumn enable = new JQGridColumn();
			enable.setName("enable").setIndex("enable").setWidth(60).setAlias("是否启用");
			allColumns.put("enable", enable);
			
			JQGridColumn status = new JQGridColumn();
			status.setName("status").setIndex("status").setWidth(80).setAlias("删除状态");
			allColumns.put("status", status);
			
			JQGridColumn departId = new JQGridColumn();
			departId.setName("department_id").setIndex("department_id").setWidth(100).setAlias("所属部门");
			allColumns.put("department_id", departId);
			
			JQGridColumn style = new JQGridColumn();
			style.setName("style").setIndex("style").setWidth(80).setAlias("风格");
			allColumns.put("style", style);
		}
	}
}
