/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

/**
 * <p>
 * Title: Message
 * </p>
 * <p>
 * Description: ���ڱ��������������Ϣ���ݵ���
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
public class Message {

	/**
	 * ���������͵���Ϣ�����ַ���
	 * */
	String msg;

	/**
	 * �ṩ����Ϣ�������߻�ȡ��ǰ��Ϣ���ݵķ���
	 * 
	 * @return ������Ϣ��������������Ϣ����
	 * */
	public String getMsg() {
		return msg;
	}

	/**
	 * �ṩ����Ϣ�����������õ�ǰ��Ϣ���ݵķ���
	 * 
	 * @param msg
	 *            ������������ϣ�����͸������ߵ���Ϣ����
	 * */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * ������ת��Ϊ�ַ�����ʽ�ķ��������������ַ�����ʽ����Ϣ����
	 * 
	 * @return ��ǰ����Ϣ����
	 * */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return msg;
	}

}
