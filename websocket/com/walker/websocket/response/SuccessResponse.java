package com.walker.websocket.response;

import com.walker.infrastructure.core.filter.ConvertDataException;
import com.walker.infrastructure.core.filter.Convertable;
import com.walker.infrastructure.utils.StringUtils;

public class SuccessResponse extends WrappedResponse {

	private Convertable responseConvertor;
	
	private String result;
	
	public SuccessResponse(Convertable responseConvertor){
		this.setCode(CODE_SUCCESS);
		this.setSummary(SUCCESS);
		this.responseConvertor = responseConvertor;
	}
	
	@Override
	public String toString(){
		if(result == null){
			try {
				result = (String)responseConvertor.convertTo(this);
			} catch (ConvertDataException e) {}
			return StringUtils.EMPTY_STRING;
		}
		return result;
	}
}
