/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.listeners;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.chinasofti.ordersys.vo.UserInfo;

/**
 * <p>
 * Title: OrderSysListener
 * </p>
 * <p>
 * Description: ���ϵͳ����������Ҫ�����Ự�Ĵ��������١���¼��Ϣ�Ự���������ã�����ʵ�������û��������ߺ����Ա�����߷���Ա�б���
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 * <p>
 * Company: ChinaSoft International Ltd.
 * </p>
 * 
 * @author etc
 * @version 1.0
 */
public class OrderSysListener implements HttpSessionListener,
		HttpSessionAttributeListener {

	/**
	 * ��������Ѿ���½�û���Ϣ��Hashtable,��Ϊ�û�sessionID��ֵΪ�û���ϸ��Ϣ
	 * */
	public static Hashtable<String, UserInfo> sessions = new Hashtable<String, UserInfo>();
	/**
	 * �������߻Ự���ı���
	 * */
	public static int onlineSessions = 0;

	/**
	 * ��ȡ���߷���Ա�б�ķ���
	 * 
	 * @return ��ǰ���ߵ����в�������Ա�б���Ϣ
	 * */
	public static ArrayList<UserInfo> getOnlineWaiters() {
		// ���ص�ǰ���ߵ����з���Ա�б�
		return getOnlineUsersByRoleId(3);
	}

	/**
	 * ��ȡ���ߺ����Ա�б�ķ���
	 * 
	 * @return ��ǰ���ߵ����в��������Ա�б���Ϣ
	 * */
	public static ArrayList<UserInfo> getOnlineKitchens() {
		// ���ص�ǰ���ߵ����к����Ա�б�
		return getOnlineUsersByRoleId(2);
	}

	/**
	 * ��ȡ�ض���ɫ������Ա�б�ķ���
	 * 
	 * @param roleId
	 *            Ҫ��ȡ����Ա��ɫID��1-��������Ա��2-�����Ա��3-��������Ա
	 * @return ��ǰ���ߵ����з��Ͻ�ɫ����Ա�б���Ϣ
	 * */
	private static ArrayList<UserInfo> getOnlineUsersByRoleId(int roleId) {
		// ��ȡ���������û���sessionID
		Set<String> sessionIds = sessions.keySet();
		// ��ȡ�����û�ID�ĵ�����
		Iterator<String> sessionIdIt = sessionIds.iterator();
		// �����������
		ArrayList<UserInfo> list = new ArrayList<UserInfo>();
		// ���������û�ID
		while (sessionIdIt.hasNext()) {
			// ��ȡ��sessionID��Ӧ���û���Ϣ
			UserInfo info = sessions.get(sessionIdIt.next());
			// �ж���ɫ��Ϣ�Ƿ����Ҫ��
			if (info.getRoleId() == roleId) {
				// �����ɫ��Ϣ�͸�����Ϣһ�£��򽫸��û��������б�
				list.add(info);
			}
		}
		// ���ؽ���б�
		return list;

	}

	/**
	 * ��session���������ʱ�ļ������ص�����
	 * 
	 * @param arg0
	 *            �������¼���Ϣ�����Ի�ȡ������Ե�session\��ӵ�������\��ӵ�����ֵ����Ϣ
	 * */
	@Override
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		// �����ӵ���������USER_INFO����˵����һ���û���¼��
		if (arg0.getName().equals("USER_INFO")) {
			// �����û���Ϣ��ӵ������û��б���
			sessions.put(arg0.getSession().getId(), (UserInfo) arg0.getValue());
		}
	}

	/**
	 * ��session���Ƴ�����ʱ�ļ������ص�����
	 * 
	 * @param arg0
	 *            �������¼���Ϣ�����Ի�ȡ�Ƴ����Ե�session\�Ƴ���������\�Ƴ�������ֵ����Ϣ
	 * */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		// ����Ƴ�����������USER_INFO����˵����һ���û�ע����
		if (arg0.getName().equals("USER_INFO")) {
			// �������û��б��н����û���Ϣ�Ƴ�
			sessions.remove(arg0.getSession().getId());
		}

	}

	/**
	 * ��session������ֵ���滻ʱ�ļ������ص�����
	 * 
	 * @param arg0
	 *            �������¼���Ϣ�����Ի�ȡ�滻���Ե�session\�滻��������\�滻������ֵ����Ϣ
	 * */
	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		// ����滻����������USER_INFO����˵����һ���û������¼�����
		if (arg0.getName().equals("USER_INFO")) {
			// ���µ�ǰ�û��ĵ�¼��Ϣ
			sessions.put(arg0.getSession().getId(), (UserInfo) arg0.getValue());
		}

	}

	/**
	 * ������Sessionʱ�ļ������ص�����
	 * 
	 * @param arg0
	 *            �������¼���Ϣ�����Ի�ȡ������session��Ϣ
	 * */
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		// ���߻Ự������
		onlineSessions++;
	}

	/**
	 * ������Sessionʱ�ļ������ص�����
	 * 
	 * @param arg0
	 *            �������¼���Ϣ�����Ի�ȡ���ٵ�session��Ϣ
	 * */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		// ���߻Ự������
		onlineSessions--;
		// ��������ٵ�ʱ���Ѿ���¼���û�
		if (arg0.getSession().getAttribute("USER_INFO") != null) {
			// �������û��б��н����û���Ϣ�Ƴ�
			sessions.remove(arg0.getSession().getId());
		}
	}

}
