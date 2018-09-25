package com.itdage.constant;

import org.springframework.stereotype.Component;

@Component
public class StatusConstant {
	/**
	 * 请求成功
	 */
	public static final int SUCCESS = 200;
	/**
	 * 请求失败
	 */
	public static final int ERROR = 400;
	/**
	 * 用户列表
	 */
	public static final int USERLIST = 202;
	/**
	 * 上线通知
	 */
	public static final int ONLINE_NOTICE = 203;
	/**
	 * 下线通知
	 */
	public static final int OUTLINE_NOTICE = 204;
}
