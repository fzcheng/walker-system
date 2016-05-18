package com.walkersoft.flow.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.flow.client.SpringWorkerClient;
import com.walker.flow.core.biz.BizDTO;
import com.walkersoft.flow.pojo.TestProject;
import com.walkersoft.system.dao.SystemBaseDao;

@Service("testManager")
public class TestManagerImpl{

	private static final String SQL_QUERY_PROJECT = "select p.*, i.PROCESS_DEFINE_ID, i.NAME PNAME, i.IS_END, i.IS_PAUSE, i.IS_TERMINATE "
			+ "from test_project p, sw_process_inst i where p.process = i.id";
	
	private final TestProjectRowMapper rowMapper = new TestProjectRowMapper();
	private final ProjectObjectMapper projectMapper = new ProjectObjectMapper();
	
	@Autowired
	private SystemBaseDao systemBaseDao;
	
	/**
	 * 分页返回测试项目列表集合
	 * @return
	 */
	public GenericPager<TestProject> queryProjectList(){
		return systemBaseDao.sqlGeneralQueryPager(SQL_QUERY_PROJECT, null, rowMapper);
	}
	
	public TestProject queryProjectInfo(String id){
		return systemBaseDao.getJdbcTemplate().queryForObject("select * from test_project where id=?"
				, new Object[]{id}, projectMapper);
	}
	
	private static final String SQL_INSERT_PRJ = "insert into test_project(id,name,create_time,"
			+ "summary,process) values(?,?,?,?,?)";
	
	/**
	 * 创建项目记录，同时创建流程信息
	 * @param project
	 * @param currentUserId
	 * @param currentUserName
	 */
	public void execSaveTestProject(TestProject project, String currentUserId, String currentUserName){
		BizDTO primaryBiz = new BizDTO(project.getId()
				, project.getName(), currentUserId, currentUserName);
		primaryBiz.addBizData("project_name", project.getName());
		String processInstId = SpringWorkerClient.createProcessAtFirst(primaryBiz, project.getProcessId(), null);
		Object[] params = new Object[5];
		params[0] = project.getId();
		params[1] = project.getName();
		params[2] = project.getCreateTime();
		params[3] = project.getSummary();
		params[4] = processInstId; // 存储流程实例ID
		systemBaseDao.update(SQL_INSERT_PRJ, params);
	}
	
	private class TestProjectRowMapper implements RowMapper<TestProject>{
		@Override
		public TestProject mapRow(ResultSet rs, int arg1) throws SQLException {
			TestProject tp = new TestProject();
			tp.setId(rs.getString("id"));
			tp.setName(rs.getString("name"));
			tp.setCreateTime(rs.getLong("create_time"));
			tp.setProcessId(rs.getString("process"));
			tp.setSummary(rs.getString("summary"));
			tp.setProcessDefineId(rs.getString("process_define_id"));
			tp.setProcessName(rs.getString("pname"));
			if(rs.getInt("is_terminate") == 1 
					|| rs.getInt("is_end") == 1){
				tp.setStatus(1);
			} else if(rs.getInt("is_pause") == 1){
				tp.setStatus(2); // 已暂停
			}
			return tp;
		}
	}
	
	private class ProjectObjectMapper implements RowMapper<TestProject>{
		@Override
		public TestProject mapRow(ResultSet rs, int arg1) throws SQLException {
			TestProject tp = new TestProject();
			tp.setId(rs.getString("id"));
			tp.setName(rs.getString("name"));
			tp.setCreateTime(rs.getLong("create_time"));
			tp.setSummary(rs.getString("summary"));
			return tp;
		}
	}
}
