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
	/**
	 * 主页消息通知
	 */
	
	public static final int MESSAGE_INDEX_NOTICE = 205;
	/**
	 * chat页面消息通知--消息推送 --推送到对方chat.html
	 */
	public static final int MESSAGE_CHAT_NOTICE = 206;
	/**
	 * chat页面消息通知--消息推送 --推送到自己chat.html
	 */
	public static final int MESSAGE_CHAT_NOTICE_ME = 207;
	/**
	 * 主页消息取消
	 */
	public static final int MESSAGE_INDEX_CANCEL = 208;
	
}
