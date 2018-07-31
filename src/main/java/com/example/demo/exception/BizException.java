package com.example.demo.exception;

public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int ERROR_CODE_DEFAULT=2001;
	
	private Integer errCode;
	private String errMsg;
	private Object obj;
	
	public BizException(Throwable cause) {
		super(cause);
	}
	public BizException(String errMsg){
		super(errMsg);
		this.errMsg=errMsg;
	}
	public BizException(String errMsg,Object obj){
		super(errMsg);
		this.errMsg = errMsg;
		this.obj= obj;
	}
	public BizException(Integer errCode,String errMsg){
		super(errMsg);
		this.errCode=errCode;
		this.errMsg =errMsg;
	}
	public BizException(Integer errCode,String errMsg,Object obj){
		super(errMsg);
		this.errCode =errCode;
		this.errMsg = errMsg;
		this.obj =obj;
	}
	public BizException(BizExceptionEnum bizExceptionEnum){
		super(bizExceptionEnum.getErrMsg());
		this.errMsg= bizExceptionEnum.getErrMsg();
	}
	public Integer getErrCode() {
		return errCode;
	}
	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

	

}
