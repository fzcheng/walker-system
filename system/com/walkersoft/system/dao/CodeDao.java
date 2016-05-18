package com.walkersoft.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.SQLDaoSupport;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.entity.CodeEntity;

@Repository("codeDao")
public class CodeDao extends SQLDaoSupport<CodeEntity> {

	public static final String SQL_UPDATE_CODE_SUM = 
			"update s_code c set c.child_sum = c.child_sum + 1 Where c.Id = ?";
	
	public static final String SQL_DECREASE_CODE_SUM = 
			"update s_code c set c.child_sum = c.child_sum - 1 Where c.Id = ?";
	
	private static Sort sxhSort = Sorts.ASC().setField("sxh");
	
	/**
	 * 更新代码“下级代码数量值”
	 * @param codeId
	 * @throws Exception
	 */
	public void updateParentCodeChildSum(String codeId){
		assert (StringUtils.isNotEmpty(codeId));
		this.update(SQL_UPDATE_CODE_SUM, new Object[]{codeId});
	}
	
	/**
	 * 更新减少代码“下级代码数量值”
	 * @param codeId
	 * @throws Exception
	 */
	public void decreaseParentCodeChildSum(String codeId){
		assert (StringUtils.isNotEmpty(codeId));
		this.update(SQL_DECREASE_CODE_SUM, new Object[]{codeId});
	}
	
	/**
	 * 返回代码表集合
	 * @return
	 */
	public List<CodeEntity> getRootCodeList(){
		return this.findBy(PropertyEntry.createEQ("codeType", 0), sxhSort);
	}
	
	/**
	 * 返回所有代码项集合
	 * @return
	 */
	public List<CodeEntity> getAllCodeItemList(){
		return this.findBy(PropertyEntry.createEQ("codeType", 1), sxhSort);
	}
}
