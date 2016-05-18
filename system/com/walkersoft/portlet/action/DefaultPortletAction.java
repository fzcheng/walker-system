package com.walkersoft.portlet.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.portlet.Portlet;
import com.walkersoft.portlet.manager.PortletManagerImpl;
import com.walkersoft.system.SystemAction;

@Controller
public class DefaultPortletAction extends SystemAction {

	private static final String URL_INDEX = "system/portlet/index";
	
	private Map<String, Portlet> portletMap = null;
	
	private final Object lock = new Object();
	
	@Autowired
	private PortletManagerImpl portletManager;
	
	/**
	 * 进入portlet容器主页
	 * @param model
	 * @return
	 */
	@RequestMapping("permit/portlet/default")
	public String index(Model model){
		List<Portlet> portletList = getPortletList();
		if(portletList != null){
			portletManager.queryLoadPortletDatas(portletList, model);
			for(Portlet p : portletList){
				model.addAttribute(p.getId(), p);
			}
		}
		return URL_INDEX;
	}
	
	/**
	 * 局部刷新portlet
	 * @param model
	 * @param portletId portletId，在对象中定义
	 * @return
	 */
	@RequestMapping("permit/portlet/reload")
	public String reloadPortlet(Model model, String portletId){
		Assert.hasText(portletId);
		Portlet portlet = portletMap.get(portletId);
		if(portlet == null){
			throw new ApplicationRuntimeException("未找到配置的portlet: " + portletId);
		}
		try {
			portlet.reload(model);
		} catch (Exception e) {
			logger.error("reload portlet failed: " + portlet.getTitle(), e);
			this.setErrorMessage(model, null, "加载portlet失败");
			return URL_ERROR;
		}
		String url = portlet.getIncludePage();
		if(url.startsWith("/")){
			url = url.substring(1, url.length()-4);
			logger.debug("............ url = " + url);
		}
		model.addAttribute(portletId, portlet);
		return url;
	}
	
	private List<Portlet> getPortletList(){
		List<Portlet> portlets = null;
		if(portletMap == null){
			synchronized (lock) {
				portlets = MyApplicationConfig.getBeanObjectList(Portlet.class);
				if(!StringUtils.isEmptyList(portlets)){
					portletMap = new HashMap<String, Portlet>(portlets.size());
					for(Portlet p : portlets){
						if(portletMap.containsKey(p.getId())){
							throw new ApplicationRuntimeException("存在相同ID的portlet: " + p.getId());
						}
						portletMap.put(p.getId(), p);
					}
				}
			}
		} else {
			portlets = new ArrayList<Portlet>(portletMap.values());
		}
		return portlets;
	}
}
