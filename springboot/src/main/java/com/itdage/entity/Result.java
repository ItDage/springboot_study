package com.itdage.entity;


import org.springframework.stereotype.Component;

/**
 * 
 * @ClassName: Result 
 * @Description: 返回的json的格式
 * @author: scy
 * @date: 2018年9月8日 下午4:02:39
 */
@Component
public class Result {
	
	/**
	 * 状态码
	 */
	private int code;
	
	/**
	 * 消息
	 */
	private String msg;
	
	/**
	 * 通用体
	 */
	private Object obj;
	
	/**
	 * 发送消息的时间
	 */
	private String date;
	
	/**
	 * 消息将要发送给谁
	 */
	private String user_to;
	
	/**
	 * 消息由谁发送
	 */
	private String user_from;
	
	public String getUser_to() {
		return user_to;
	}

	public void setUser_to(String user_to) {
		this.user_to = user_to;
	}

	public String getUser_from() {
		return user_from;
	}

	public void setUser_from(String user_from) {
		this.user_from = user_from;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "{code:" + code + ", msg:" + msg + ", obj:" + obj + "}";
	}
	
}
