package com.example.demo.exception;

/**
 * 普通枚举类（状态）
 * @author liweihai
 *
 */
public enum BizExceptionEnum {

	TOKEN_EXPIRED(700,"token过期"),
	TOKEN_ERROR(700,"token验证失败"),
	PERMISSION_ERROR(800,"没有访问该资源的权限"),
	SIGN_ERROR(700,"签名验证失败"),
	WRITE_ERROR(500,"渲染页面错误"),
	FILE_READING_ERROR(400,"文件读取错误"),
	FILE_NOT_FOUND(400,"没有发现文件"),
	REQUEST_NULL(400,"请求有错误"),
	BIZ_EXCEPTION(500,"业务处理失败"),
	SERVER_ERROR(500,"服务器出现异常");
	
	private int errCode;
	private String errMsg;
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	private BizExceptionEnum(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
}
