package com.walkersoft.flow.actor;

import com.walker.flow.core.actor.AbstractBaseActor;
import com.walker.flow.core.actor.SingleGivenActor;
import com.walker.flow.core.actor.node.AbstractActorNode;
import com.walker.flow.core.actor.node.DefaultItemNode;
import com.walker.flow.core.context.ProcessContext;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 当前登录用户参与者实现
 * @author shikeying
 * @date 2014-8-21
 *
 */
public class LoginUserActor extends AbstractBaseActor implements
		SingleGivenActor {

	@Override
	public AbstractActorNode getExcuteActor(ProcessContext processcontext) {
		UserCoreEntity user = MyApplicationConfig.getCurrentUser();
		return new DefaultItemNode(user.getId(), user.getName());
	}

}
