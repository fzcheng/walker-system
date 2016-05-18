package com.walkersoft.portlet.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.portlet.Portlet;

/**
 * <code>Portlet</code>数据库事务控制层，它并没有自己特有的数据，</br>
 * 仅仅是从各个Portlet中加载数据，这样做避免了多次数据库的事务链接。
 * @author shikeying
 *
 */
@Service("portletManager")
public class PortletManagerImpl {

	private final transient Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 加载portlet集合的数据，通过创建一个事务，避免多次的远程连接。
	 * @param portletList
	 * @param model
	 */
	public void queryLoadPortletDatas(List<Portlet> portletList, Model model){
		if(!StringUtils.isEmptyList(portletList)){
			for(Portlet p : portletList){
				try {
					p.reload(model);
				} catch (Exception e) {
					logger.error("加载portlet数据错误:"+p.getTitle(), e);
				}
			}
		}
	}
}
