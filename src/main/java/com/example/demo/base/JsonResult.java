package com.example.demo.base;

import java.io.Serializable;

import com.example.demo.exception.BizException;
import com.example.demo.exception.ViewException;

public class JsonResult<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 返回状态true/false
	private Boolean success= false;
	// 返回信息成功或失败
	private String msg= "";
	// 返回结果对象
	private T obj=null;
	// 返回详细信息
	private String detailMsg ="";
	// 无参构造
	public JsonResult(){}
	
	// 状态或信息构造
	public JsonResult(Boolean success,String msg){
		this.success= success;
		this.msg =msg;
	}
	// 状态+信息+结果 构造
	public JsonResult(Boolean success,String msg,T obj){
		this.success = success;
		this.msg = msg;
		this.obj=obj;
	}
	// 状态+信息+详细信息 构造
	public JsonResult(Boolean success,String msg,String detailMsg){
		this.success=success;
		this.msg= msg;
		this.detailMsg= detailMsg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String getDetailMsg() {
		return detailMsg;
	}

	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	// 返回错误+信息：操作失败
	public static <T> JsonResult<T> resultError(){return resultError("操作失败");}
	// 返回错误+信息参数
	public static <T> JsonResult<T> resultError(String message) {
		
		return new JsonResult<T>(false,message);
	}
	// 创建错误+信息参数+详细信息参数
	public static <T> JsonResult<T> createErrorMsg(String message,String detailMsg){
		
		return new JsonResult<T>(false,message,detailMsg);
	}
	// 返回非业务或非逻辑错误以外的其他异常
	public static <T>JsonResult<T> resultBizExceptionError(Exception e,String defaultErrorMessage){
		return !(e instanceof BizException)&& !(e instanceof ViewException)? new JsonResult<T>(false,defaultErrorMessage):new JsonResult<T>(false,e.getMessage());
	}
	// 返回成功信息
	public static <T> JsonResult<T> resultSuccess(){
		return resultSuccess("操作成功",null);
	}
	// 返回成功信息+结果
	public static <T> JsonResult <T> resultSuccess(T obj){
		return resultSuccess("操作成功",obj);
	}
	//最终返回成功
	public static <T> JsonResult<T> resultSuccess(String message, T object) {
		
		return new JsonResult<T>(true,message,object);
	}
	
}
