package com.walkersoft.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.app.ApplicationRuntimeException;
import com.walker.infrastructure.cache.AbstractCacheProvider;
import com.walker.infrastructure.cache.support.Cachable;
import com.walker.infrastructure.cache.support.Cache;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.flow.dao.DesignDao;
import com.walkersoft.flow.pojo.AnchorIn;
import com.walkersoft.flow.pojo.AnchorOut;
import com.walkersoft.flow.pojo.TaskNode;

/**
 * 任务节点位置以及连接线对象缓存定义
 * @author shikeying
 * @date 2014-7-9
 *
 */
public class TaskPositionCache extends AbstractCacheProvider<TaskNode> {

	@Autowired
	private DesignDao designDao;
	
	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return "flow.position.cache";
	}

	@Override
	public Class<TaskNode> getProviderType() {
		// TODO Auto-generated method stub
		return TaskNode.class;
	}

	@Override
	protected int loadDataToCache(Cache cache) {
		// TODO Auto-generated method stub
		List<TaskNode> taskNodes = designDao.getAllTaskNodeList();
		if(taskNodes.size() > 0){
			for(TaskNode tn : taskNodes){
				cache.put(tn.getId(), tn);
			}
			
			List<AnchorIn> anchorIns = designDao.getAllAnchorInList();
			for(AnchorIn ai : anchorIns){
				((TaskNode)cache.get(ai.getTaskNodeId())).addAnchorIn(ai);
			}
			
			List<AnchorOut> anchorOuts = designDao.getAllAnchorOutList();
			for(AnchorOut ao : anchorOuts){
				((TaskNode)cache.get(ao.getTaskNodeId())).addAnchorOut(ao);
			}
			return taskNodes.size();
		}
		return 0;
	}

	/**
	 * 返回任务节点对象集合
	 * @param taskIds 任务ID集合
	 * @return
	 */
	public List<TaskNode> getTaskAvailablePositions(List<String> taskIds){
		if(taskIds == null || taskIds.size() == 0) return null;
		List<TaskNode> resultList = new ArrayList<TaskNode>();
		TaskNode taskNode = null;
		for(Iterator<Cachable> it = getCache().getIterator(); it.hasNext();){
			taskNode = (TaskNode)(it.next().getValue());
			if(taskIds.contains(taskNode.getId())){
				resultList.add(taskNode);
			}
		}
		return resultList;
	}
	
	public void putAnchorOut(AnchorOut anchorOut){
		assert (anchorOut != null);
		TaskNode taskNode = getCacheData(anchorOut.getTaskNodeId());
		if(taskNode == null) throw new ApplicationRuntimeException("taskNode not found: " + anchorOut.getTaskNodeId());
		taskNode.addAnchorOut(anchorOut);
	}
	
	public void putAnchorIn(AnchorIn anchorIn){
		assert (anchorIn != null);
		TaskNode taskNode = getCacheData(anchorIn.getTaskNodeId());
		if(taskNode == null) throw new ApplicationRuntimeException("taskNode not found: " + anchorIn.getTaskNodeId());
		taskNode.addAnchorIn(anchorIn);
	}
	
	public void removeAnchorOut(String taskDefineId, String outPosition){
		assert (StringUtils.isNotEmpty(taskDefineId));
		assert (StringUtils.isNotEmpty(outPosition));
		TaskNode taskNode = getCacheData(taskDefineId);
		taskNode.removeAnchorOut(outPosition);
	}
	
	/**
	 * 从缓存中删除一个任务的输入点位置信息。<br>
	 * 因为输入点存在共用现象，因此需要加上fromTaskNode来匹配是那个连接线
	 * @param taskDefineId 输入点的任务ID
	 * @param inPosition 输入点位置
	 * @param fromTaskNode 连接上一步的任务ID
	 */
	public void removeAnchorIn(String taskDefineId
			, String inPosition, String fromTaskNode){
		assert (StringUtils.isNotEmpty(taskDefineId));
		assert (StringUtils.isNotEmpty(inPosition));
		assert (StringUtils.isNotEmpty(fromTaskNode));
		TaskNode taskNode = getCacheData(taskDefineId);
		taskNode.removeAnchorIn(inPosition, fromTaskNode);
	}
	
	public void updateAnchorIn(String changedSrcTaskId, String changedSrcPosition
			, String targetTaskId, String targetPosition, String oldSrcTaskId){
		TaskNode taskNode = getCacheData(targetTaskId);
		taskNode.updateAnchorIn(targetPosition, oldSrcTaskId, changedSrcTaskId, changedSrcPosition);
	}
	
	public void updateAnchorOut(String nextNode, String nextPosition, String sourceTaskId, String outPosition){
		TaskNode taskNode = getCacheData(sourceTaskId);
		taskNode.updateAnchorOut(outPosition, nextNode, nextPosition);
	}
}
