package com.walker.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.walkersoft.application.security.MyUserDetails;


public class AutoMatchTableInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3105697349649905109L;
	
	private transient final Log logger = LogFactory.getLog(getClass());

	@Override
	public String onPrepareStatement(String sql) { 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null){
			logger.debug("未登录");
			return sql;
		} else {
			if(sql.indexOf("wiki_doc") > 0){
				int i = 0;
				MyUserDetails userDetails = (MyUserDetails)auth.getDetails();
				String uid = userDetails.getUsername();
				if(uid.equals("sunwukong")){
					i = 1;
				} else if(uid.equals("zhubajie")){
					i = 2;
				}
				logger.debug("++++++++++ 原始SQL: " + sql);
				
				if(i != 0){
					sql = sql.replaceAll("wiki_doc", "wiki_doc_"+i);
				}
			}
			logger.debug("--------- 替换后sql:" + sql);
		}
		return sql; 
	}
}
