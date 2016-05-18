package com.walkersoft.application.log;

import com.walker.fastlog.LogDetail;
import com.walker.fastlog.LogDetailAdapter;
import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationContext;

/**
 * 系统日志对象
 * @author shikeying
 *
 */
public class MyLogDetail implements LogDetailAdapter {

	private String id;
	private long createTime = 0;
	private String loginUser;
	private LogType type = LogType.Operation;
	private String content;
	
	public String getLoginUser() {
		return loginUser;
	}

	public LogType getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public long getCreateTime() {
		return createTime;
	}
	
	public String getShowTime(){
		return DateUtils.getDateTimeForHuman(createTime);
	}

	public MyLogDetail setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getTypeName(){
		return this.type.getName();
	}
	
	public MyLogDetail(){
		this(MyApplicationContext.getUserLoginId());
	}
	
	/**
	 * 此构造方法是在用户认证过程中记录日志需要调用的，因为那时候登陆用户信息还没有写入session
	 * @param loginId
	 */
	public MyLogDetail(String loginId){
		id = NumberGenerator.generatorHexUUID();
		createTime = NumberGenerator.getSequenceNumber();
		loginUser = loginId;
	}
	
//	public MyLogDetail setId(String id) {
//		this.id = id;
//		return this;
//	}
//
//	public MyLogDetail setCreateTime(long createTime) {
//		this.createTime = createTime;
//		return this;
//	}
//
	public MyLogDetail setLoginUser(String loginUser) {
		this.loginUser = loginUser;
		return this;
	}

	public MyLogDetail setType(LogType type) {
		this.type = type;
		return this;
	}

	public MyLogDetail setContent(String content) {
		assert (StringUtils.isNotEmpty(content));
		if(content.length() > 120)
			this.content = content.substring(0, 120);
		else
			this.content = content;
		return this;
	}

	private static final String NAME_ID = "id";
	private static final String NAME_CREATE_TIME = "create_time";
	private static final String NAME_LOGIN_ID = "login_user";
	private static final String NAME_LOG_TYPE = "type";
	private static final String NAME_CONTENT = "content";
	
	/**
	 * 实现了格式转换，告诉系统业务日志字段如何与SQL中的字段对应。
	 */
	@Override
	public void transferLog(LogDetail logDetail) {
		// TODO Auto-generated method stub
		logDetail.addStringProperty(NAME_ID, id);
		logDetail.addLongProperty(NAME_CREATE_TIME, createTime);
		logDetail.addStringProperty(NAME_LOGIN_ID, loginUser);
		logDetail.addIntegerProperty(NAME_LOG_TYPE, type.getValue());
		logDetail.addStringProperty(NAME_CONTENT, content);
	}

	public enum LogType {
		Operation{
			public int getValue(){
				return 0;
			}
			public String getName(){
				return NAME_OPERATION;
			}
		},
		Login{
			public int getValue(){
				return 1;
			}
			public String getName(){
				return NAME_LOGIN;
			}
		},
		Logout{
			public int getValue(){
				return 2;
			}
			public String getName(){
				return NAME_LOGOUT;
			}
		},
		Delete{
			public int getValue(){
				return 3;
			}
			public String getName(){
				return NAME_DELETE;
			}
		},
		Add{
			public int getValue(){
				return 4;
			}
			public String getName(){
				return NAME_ADD;
			}
		},
		Edit{
			public int getValue(){
				return 5;
			}
			public String getName(){
				return NAME_EDIT;
			}
		};
		public int getValue(){
			throw new AbstractMethodError();
		}
		public String getName(){
			throw new AbstractMethodError();
		}
		
		static final String NAME_LOGIN     = "用户登录";
		static final String NAME_LOGOUT    = "用户注销";
		static final String NAME_DELETE    = "删除";
		static final String NAME_ADD       = "创建";
		static final String NAME_EDIT      = "修改";
		static final String NAME_OPERATION = "一般操作";
	}
	
	public static final LogType getLogType(int typeValue){
		if(typeValue == 0){
			return LogType.Operation;
		} else if(typeValue == 1){
			return LogType.Login;
		} else if(typeValue == 2){
			return LogType.Logout;
		} else if(typeValue == 3){
			return LogType.Delete;
		} else if(typeValue == 4){
			return LogType.Add;
		} else if(typeValue == 5){
			return LogType.Edit;
		} else 
			throw new UnsupportedOperationException("unknown type value of log: " + typeValue);
	}
}
