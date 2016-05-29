package com.walkersoft.appmanager.manager;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walkersoft.appmanager.dao.AliCallbackDao;
import com.walkersoft.appmanager.dao.TenpayCallbackDao;
import com.walkersoft.appmanager.entity.AliCallbackEntity;
import com.walkersoft.appmanager.entity.TenpayCallbackEntity;


/**
 * 回调记录
 * @author a
 *
 */
@Service("callbackManager")
public class CallbackRecordImpl {

	@Autowired
	AliCallbackDao alicallbackDao;
	
	@Autowired
	TenpayCallbackDao tenpaycallbackDao;
	
	public AliCallbackEntity addAliRecord(HttpServletRequest req) {
		AliCallbackEntity entity = new AliCallbackEntity(req); 
		
		alicallbackDao.save(entity);
		return entity;
	}

	public TenpayCallbackEntity addTenpayRecord(Map<String, String> map, String body) {
		TenpayCallbackEntity entity = new TenpayCallbackEntity(map, body); 
		
		tenpaycallbackDao.save(entity);
		return entity;
	}

}
