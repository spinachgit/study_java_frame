package com.spinach.utils;

import java.util.List;

/**
 * 在返回消息的场合下抛出例外类
 * 
 * @author zhao niannian
 */
public class LogicException extends RuntimeException {
	/** serialVersionUID */
	private static final long serialVersionUID = -2627969827540064396L;

	/** 输入错误list */
	private List<String> messageList;

	/**
	 * 构造函数
	 * 
	 * @param messageList
	 */
	public LogicException(List<String> msgList) {
		messageList = msgList;
	}

	/**
	 * 取得messageList
	 * 
	 * @return messageList
	 */
	public List<String> getMessageList() {
		return messageList;
	}
}