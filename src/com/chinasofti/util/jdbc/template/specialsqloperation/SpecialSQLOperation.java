/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.template.specialsqloperation;

import java.sql.PreparedStatement;

/**
 * <p>
 * Title: SpecialSQLOperation
 * </p>
 * <p>
 * Description: �������ݿ��ض���������
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
public abstract class SpecialSQLOperation {

	/**
	 * ��ȡ�ض�TopN����SQL���ķ���
	 * 
	 * @param initialSQL
	 *            ��ʼ��SQL���
	 * @param hasOffset
	 *            �Ƿ�֧��offSet����
	 * @return ��ȡ�����ض�TopN�������
	 * */
	public abstract String getTopNSQL(String initialSQL, boolean hasOffset);

	/**
	 * ����TopN��ѯ�������������Ϣ
	 * 
	 * @param topNStatement
	 *            Ԥ����������
	 * @param args
	 *            ��ѯ����Ӧ�Ĳ���
	 * @param offset
	 *            ��ѯ��offsetֵ
	 * @param size
	 *            ���β�ѯ���ص������Ŀֵ
	 * @return ���ò������Ԥ����������
	 * */
	public abstract PreparedStatement setTopNQueryParameter(
			PreparedStatement topNStatement, Object[] args, int offset, int size);
}
