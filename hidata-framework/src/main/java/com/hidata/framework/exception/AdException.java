package com.hidata.framework.exception;


/**
 * 
* @Description: TODO(自定义异常) 
* @author chenjinzhao
* @date 2014年1月6日 上午11:37:32 
*
 */
public class AdException extends Exception {

	private int errorCode;
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private String errorMsg;

	public AdException(int errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;

	}

	public AdException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	
	  public AdException(String errorMsg, Throwable cause) {
	        super(errorMsg, cause);
	    }
	    
	public AdException(Throwable cause) {
	        super(cause);
	    }

}
