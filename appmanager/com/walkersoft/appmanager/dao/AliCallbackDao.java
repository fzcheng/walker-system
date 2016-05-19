package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walkersoft.appmanager.entity.AliCallbackEntity;

@Repository("alicallbackDao")
public class AliCallbackDao extends SQLDaoSupport<AliCallbackEntity> {
}
