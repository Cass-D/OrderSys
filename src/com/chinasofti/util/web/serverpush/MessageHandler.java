/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

import java.util.Hashtable;

/**
 * <p>
 * Title: MessageHandler
 * </p>
 * <p>
 * Description: 消息处理器回调接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: ChinaSoft International Ltd.
 * </p>
 * 
 * @author etc
 * @version 1.0
 */
public interface MessageHandler {
	/**
	 * 处理消息的回调方法
	 * 
	 * @param messageQueue
	 *            本消息所在的消息等待序列
	 * @param key
	 *            本消息的键，包含消息针对的客户sessionid和消息的标题
	 * @param msg
	 *            消息对象
	 * */
	public void handle(Hashtable<ServerPushKey, Message> messageQueue,
			ServerPushKey key, Message msg);

}
