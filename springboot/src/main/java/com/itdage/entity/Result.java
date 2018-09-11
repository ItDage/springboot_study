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
	
	private int code;
	
	private String msg;
	
	private Object obj;

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
