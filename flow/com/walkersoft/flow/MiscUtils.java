package com.walkersoft.flow;

import java.util.ArrayList;
import java.util.List;

import com.walker.flow.core.actor.ActorUser;
import com.walker.flow.meta.NextTaskOptionDTO;
import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.StringUtils;

public class MiscUtils {

	/**
	 * 返回时间毫秒作为字符串的ID
	 * @return
	 */
	public static String generateSortId(){
		return String.valueOf(System.currentTimeMillis());
	}
	
	/**
	 * 传递选择审批人时，值的分隔符，格式如：id,name
	 */
	public static final String ACTOR_VALUE_SEPARATOR = "|";
	
	/**
	 * 生成流程下一步选项对象，这个方法仅在抛出下一步多参与者异常时被调用
	 * 
	 * @param selectedActor 已经存在的选择过的用户信息，可以多个
	 * @param opinion 填写意见
	 * @return
	 */
	public static final NextTaskOptionDTO obtainNextTaskOptionInMultiActorException(String selectedActor, String opinion) {
		NextTaskOptionDTO nto = null;
		if (StringUtils.isNotEmpty(selectedActor)) {
			String[] idName = selectedActor.split("\\" + ACTOR_VALUE_SEPARATOR);
			nto = new NextTaskOptionDTO();
			nto.setCurrentTaskOpinion(opinion);
			nto.setUserSelectInException(true);
			List<ActorUser> auList = new ArrayList<ActorUser>(1);
			ActorUser au = new ActorUser(idName[0], idName[1]);
			auList.add(au);
			nto.setNextSelectedActors(auList);
		}
		return nto;
	}
	
	/**
	 * 返回日期字符串，展示用。如：2014-10-23 13:10:01
	 * @param datetime 输入日期数字，如：20141023131001
	 * @return
	 */
	public static String getDatetimeShow(long datetime){
		if(datetime > 0){
			return DateUtils.toShowDate(datetime);
		}
		return StringUtils.EMPTY_STRING;
	}
}
