package com.walker.websocket.response;

import com.walker.websocket.Response;

/**
 * 响应对象定义
 * @author shikeying
 *
 */
public class WrappedResponse implements Response {

	private int code = CODE_UNKNOWN;
	
	private String summary;
	
	private String content;
	
	public void setCode(int code) {
		this.code = code;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getSummary() {
		return this.summary;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public String toString(){
		return new StringBuilder().append("[code=").append(code)
				.append(", summary=").append(summary)
				.append(", content=").append(content)
				.append("]").toString();
	}
}
