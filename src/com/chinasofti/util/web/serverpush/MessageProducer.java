/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

/**
 * <p>
 * Title: MessageProducer
 * </p>
 * <p>
 * Description: ������������Ϣ����Ϣ������
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
public class MessageProducer {

	/**
	 * ������Ϣ�ķ���
	 * 
	 * @param sessionID
	 *            ������������Ϣ��ԵĿͻ�sessionid
	 * @param msg
	 *            ��������Ϣ����
	 * */
	public void sendMessage(String sessionID, String messageTitle, String msg) {
		// ���ø�����ϢĿ��ͻ�sessionid����Ϣ���ⴴ����Ϣ��
		ServerPushKey key = new ServerPushKey(sessionID, messageTitle);
		// �����Ϣ�ȴ������д��ڱ�����ִ����Ϣ����������
		if (ServerPushMQ.waitQueue.get(key) != null) {
			// ��ȡ����Ϣ�ȴ������е���Ϣ����
			Message message = ServerPushMQ.waitQueue.get(key);
			// ����Ϣ�����������Ϣ����
			message.setMsg(msg);
			// ��ȡ����Ϣ�����ͬ����
			synchronized (message) {
				// ��������Ϣ���������������̣߳�������Ϣ��������������http�������̣߳���Ϣ�������߽�ֱ�ӵ�����Ϣ�������Ĵ�����
				message.notifyAll();
			}
		}
	}
}
