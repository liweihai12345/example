package com.example.demo.exception;

/**
 * 业务逻辑异常
 * @author liweihai
 *
 */
public class ViewException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int ERROR_CODE_DEFAULT = 3001;

	private int errCode = 3001;
	private String errMsg;
	private Object obj;
	
	// 显示抛出错误
	public ViewException(Throwable cause) {
		super(cause);
	}
	
	public static ViewException instance(Throwable cause){
		return new ViewException(cause);
	}
	//
	public ViewException(String errMsg) {
		super(errMsg);
		this.errMsg= errMsg;
	}
	public static ViewException instance(String errMsg){
		return new ViewException(errMsg);
	}
	public ViewException(String errMsg,Object obj){
		super(errMsg);
		this.errMsg =errMsg;
		this.obj=obj;
	}
	public static ViewException instance(String errMsg,Object obj){
		return new ViewException(errMsg,obj);
	}
	public ViewException(Integer errCode,String errMsg){
		super(errMsg);
		this.errCode=errCode;
		this.errMsg=errMsg;
	}
	public static ViewException instance(Integer errCode,String errMsg){
		return new ViewException(errCode,errMsg);
	}
	public ViewException(Integer errCode, String errMsg, Object obj) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.obj = obj;
	}
	public static ViewException instance(Integer errCode,String errMsg,Object obj){
		return new ViewException(errCode,errMsg,obj);
	}

	public ViewException(BizExceptionEnum bizExeptionEnum){
		super(bizExeptionEnum.getErrMsg());
		this.errMsg = bizExeptionEnum.getErrMsg();
	}
	public ViewException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public static ViewException instance(BizExceptionEnum bizExceptionEnum){
		return new ViewException(bizExceptionEnum);
	}





}
