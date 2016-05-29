package com.walkersoft.appmanager.dao;

import org.springframework.stereotype.Repository;

import com.walker.db.hibernate.SQLDaoSupport;
import com.walkersoft.appmanager.entity.TenpayCallbackEntity;

@Repository("tenpaycallbackDao")
public class TenpayCallbackDao extends SQLDaoSupport<TenpayCallbackEntity> {
}
