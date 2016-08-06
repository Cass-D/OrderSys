/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

/**
 * <p>
 * Title: ServerPushKey
 * </p>
 * <p>
 * Description: ��Ϣ�ȴ����м��������˳��Ի�ȡ��Ϣ�Ŀͻ�sessionid����ͼ��ȡ����Ϣ����
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
public class ServerPushKey {

	/**
	 * ��ͼ��ȡ����Ϣ�Ŀͻ�sessionid
	 * */
	String sessionID = "";
	/**
	 * ��ͼ��ȡ����Ϣ����
	 * */
	String messageTitle = "";

	/**
	 * ������Ϣ���Ĺ��췽��
	 * 
	 * @param sessionID
	 *            ���Ի�ȡ��Ϣ�Ŀͻ�sessionid
	 * @param messageTitle
	 *            �ͻ����Ի�ȡ����Ϣ����
	 * */
	public ServerPushKey(String sessionID, String messageTitle) {
		super();
		this.sessionID = sessionID;
		this.messageTitle = messageTitle;
	}

	/**
	 * �ж�������Ϣ���Ƿ���ͬ�ķ��������������Ϣ��������sessionid����Ϣ���ⶼ��ͬ����Ϊ��������ͬ
	 * 
	 * @param arg0
	 *            ���ڶԱȵ���һ����Ϣ����
	 * */
	@Override
	public boolean equals(Object arg0) {
		// ��������������Ϣ�������Ƿ���ͬ�ı���
		boolean isEquals = false;
		// ֻ�жԱȶ���Ҳ����Ϣ��ʱ�������Ƚ�
		if (arg0 instanceof ServerPushKey) {
			// ��ȡ�Ա���Ϣ������
			ServerPushKey key = (ServerPushKey) arg0;
			// �ж�������Ϣ���Ŀͻ�sessionid����Ϣ�����Ƿ���ͬ
			if (key.sessionID.equals(sessionID)
					&& key.messageTitle.equals(messageTitle)) {
				isEquals = true;
			}
		}
		// ���رȶԽ��
		return isEquals;
	}

	/**
	 * ��ȡ��Ϣ������Ĺ�ϣֵ
	 * 
	 * @return �����ϣֵ
	 * */
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return sessionID.hashCode() + messageTitle.hashCode();
	}

}
