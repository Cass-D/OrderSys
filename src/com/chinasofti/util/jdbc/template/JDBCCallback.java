/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */

package com.chinasofti.util.jdbc.template;

import java.sql.Statement;
/**
 * <p>Title: JDBCCallback</p>
 * <p>Description: JDBCģ��ص��ӿ�</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: ChinaSoft International Ltd.</p>
 * @author etc
 * @version 1.0
 */
public interface JDBCCallback {
	
	/**
	 * ʵ�ʻص�����
	 * @param statement ģ��������ȡ���������û�����ֱ�����øö���ִ�����ݲ���
	 * @return ����ҵ���ʵ�ַ��ز�ͬ����Ϣ�������ݸ��²����漰������
	 * */
	public Object doWithStatement(Statement statement);

}
