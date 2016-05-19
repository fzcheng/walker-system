package com.walkersoft.appmanager.manager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walkersoft.appmanager.dao.AliCallbackDao;
import com.walkersoft.appmanager.entity.AliCallbackEntity;


/**
 * 回调记录
 * @author a
 *
 */
@Service("alicallbackManager")
public class AliCallbackRecordImpl {

	@Autowired
	AliCallbackDao alicallbackDao;
	
	public AliCallbackEntity addRecord(HttpServletRequest req) {
		AliCallbackEntity entity = new AliCallbackEntity(req); 
		
		alicallbackDao.save(entity);
		return entity;
	}

}
