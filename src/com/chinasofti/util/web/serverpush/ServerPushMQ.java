/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>
 * Title: ServerPushMQ
 * </p>
 * <p>
 * Description: ��������Ϣ���ͻ��Ƶ���Ϣ�ȴ�����
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
public class ServerPushMQ {
	/**
	 * ���ڴ����Ϣ�ȴ����е�ɢ�б����а�������Ҫ��ȡ���ݵĿͻ���sessionid�Լ�������ȡ����Ϣ���ƣ�
	 * ֵ����Ϊ�����Ϣ�ַ�������������Ϊ�����ͻ��˵����������
	 * */
	static Hashtable<ServerPushKey, Message> waitQueue = new Hashtable<ServerPushKey, Message>();


}
