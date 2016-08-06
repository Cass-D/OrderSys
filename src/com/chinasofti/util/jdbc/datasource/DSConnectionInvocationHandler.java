/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * <p>Title: DSConnectionInvocationHandler</p>
 * <p>Description: ���Ӷ���Ĵ�������</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: ChinaSoft International Ltd.</p>
 * @author etc
 * @version 1.0
 */
public class DSConnectionInvocationHandler implements InvocationHandler {
	/**
	 * ���ݿ�����������
	 * */
	DSConnectionContext conn;

	/**
	 * �������ݿ����������Ĺ����������Ĺ��췽��
	 * @param conn ���ݿ�����������
	 * */
	public DSConnectionInvocationHandler(DSConnectionContext conn) {
		//��ʼ������������
		this.conn = conn;
	}

	/**
	 * �����������ķ������Զ�����Connection�ӿڵ�close���������ر����Ӷ��ǽ�����״̬��Ϊ���У��������û�ʹ��
	 * @param proxy �����Ժ�����Ӷ���
	 * @param method �ͻ�����ͼ���õ�ҵ�񷽷�
	 * @param args �ͻ��˵���ҵ�񷽷�ʱ���ݵ�ʵ�ʲ���
	 * @return ���ظ��ͻ��˵�ʵ�ʷ���ֵ
	 * @see InvocationHandler#invoke(Object, Method, Object[])
	 * */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		//���巵��ֵ����
		Object resultObject = null;
		//�ж��Ƿ���ùر����ӵ�close����
		if ("close".equals(method.getName())) {
			//�����close������ִ����ԭʼ���ݶ���������״̬����Ϊ����
			conn.busyFlag = false;
		} else {
			//�������close����������ԭʼ�����Ӷ���ִ�ж�Ӧ�ķ�������ȡ����ֵ
			resultObject = method.invoke(conn.dbConnection, args);
		}
		//����ʵ�ʵķ���ֵ����
		return resultObject;
	}

}
