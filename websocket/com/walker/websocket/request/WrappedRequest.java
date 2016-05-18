package com.walker.websocket.request;

import com.walker.websocket.Request;

public class WrappedRequest implements Request {

	private String url;
	private String security;
	private int type = TYPE_BROADCAST;
	private String from;
	private String to;
	private String content;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getSecurity() {
		return this.security;
	}

	@Override
	public int getType() {
		return this.type;
	}

	@Override
	public String getFrom() {
		return this.from;
	}

	@Override
	public String getTo() {
		return this.to;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public String toString(){
		return new StringBuilder().append("[url=").append(url)
				.append(", type=").append(type)
				.append(", from=").append(from)
				.append(", to=").append(to)
				.append(", content=").append(content)
				.append("]").toString();
	}
}
