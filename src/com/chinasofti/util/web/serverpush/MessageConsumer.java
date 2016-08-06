/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

/**
 * <p>
 * Title: MessageConsumer
 * </p>
 * <p>
 * Description: ��Ϣ��������
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
public class MessageConsumer {

	/**
	 * �����߳��Ի�ȡ��������Ϣ�ķ������������ᵼ���߳�������ֱ����Ļ�ȡ����Ҫ����Ϣ��ִ����Ϣ�������Ĵ�������Ż����ִ�к�������
	 * 
	 * @param sessionID
	 *            ��Ҫ��ȡ��Ϣ�ĻỰsessionid
	 * @param messageTitle
	 *            ��Ҫ��ȡ����Ϣ�ı���
	 * @param handler
	 *            ��ʵ�ʻ�ȡ����Ϣ��Ĵ���������
	 * */
	public void searchMessage(String sessionID, String messageTitle,
			MessageHandler handler) {
		// ������Ϣ����
		Message msg = new Message();
		// ���ø����Ŀͻ�sessionid����Ϣ���ⴴ����Ϣ�ȴ����еļ�����
		ServerPushKey key = new ServerPushKey(sessionID, messageTitle);
		// �����Ϣ�����д��ڵ�ǰ��key��˵��֮ǰͬһ���ͻ�����ͼ��ȡͬһ����Ϣ�����󲻳ɹ�
		if (ServerPushMQ.waitQueue.get(key) != null) {
			// ��ȡ��ʷ��Ϣ����
			Message old = ServerPushMQ.waitQueue.get(key);
			// ����Ϣ��������Ϊ����ʱ
			old.setMsg(BuildinMessage.MESSAGE_WAITE_TIMEOUT);
			// ��ԭ��Ϣ�������Ϣ�ȴ�������ɾ��
			ServerPushMQ.waitQueue.remove(key);
			// ��ȡԭ����Ϣ�����ͬ����
			synchronized (old) {
				// ����ԭ����Ϣ�����������̣߳����̴߳������http�����������
				old.notifyAll();
			}
		}
		// ��ȡ�½���Ϣ�����ͬ����
		synchronized (msg) {
			try {
				// ����Ϣ���м�������µ���Ϣ������뵽��Ϣ�ȴ�����
				ServerPushMQ.waitQueue.put(key, msg);
				// ����msg���󽫵�ǰ��ͼ��ȡ��Ϣ���̣߳�����Http������̣߳�����������
				msg.wait();
				// ����ǰ�̱߳����Ѻ������Ϣ�������������Ϣ����ص�����
				handler.handle(ServerPushMQ.waitQueue, key, msg);
				// �����ǰ��Ϣ���ݲ��ǳ�ʱ��Ϣ�򽫱���Ϣ�������Ϣ�ȴ��������Ƴ�
				if (!msg.getMsg().equals(BuildinMessage.MESSAGE_WAITE_TIMEOUT)) {
					ServerPushMQ.waitQueue.remove(key);
				}
			} catch (InterruptedException e) {
				// ��������쳣��������쳣��Ϣ
				e.printStackTrace();
			}
		}
	}
}
