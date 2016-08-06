/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.datasource;

import java.sql.Connection;
/**
 * <p>Title: DSConnectionContext</p>
 * <p>Description: �������ݿ����ӳ����Ӷ���������</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: ChinaSoft International Ltd.</p>
 * @author etc
 * @version 1.0
 */
public class DSConnectionContext {

	/**
	 * ��ʵ��JDBC���Ӷ���
	 * */
	Connection dbConnection;
	/**
	 * �����Ժ��JDBC���Ӷ���
	 * */
	Connection proxyConnection;
	/**
	 * ȷ����ǰ�����Ƿ�ռ�õı�־λ��true��ʾ��ǰ�������ڱ�ռ�ã�false��ʾ��ǰ����Ϊ����״̬
	 * */
	boolean busyFlag;

	/**
	 * �������е�JDBC���Ӷ��󴴽������ĵĹ��췽��
	 * @param dbConnection �����õ���ͨJDBC���Ӷ���
	 * */
	DSConnectionContext(Connection dbConnection) {
		//����ǰ��������Ϊ����״̬
		busyFlag = false;
		//��ʼ�����Ӷ���
		this.dbConnection = dbConnection;
	}

}
