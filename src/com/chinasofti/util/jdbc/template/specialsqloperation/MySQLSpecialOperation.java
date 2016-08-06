/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.template.specialsqloperation;

import java.sql.PreparedStatement;

/**
 * <p>
 * Title: MySQLSpecialOperation
 * </p>
 * <p>
 * Description: MySQL���ݿ��ض���������
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
public class MySQLSpecialOperation extends SpecialSQLOperation {
	/**
	 * ��ȡ�ض�TopN����SQL���ķ���
	 * 
	 * @param initialSQL
	 *            ��ʼ��SQL���
	 * @param hasOffset
	 *            �Ƿ�֧��offSet����
	 * @return ��ȡ�����ض�TopN�������
	 * */
	@Override
	public String getTopNSQL(String initialSQL, boolean hasOffset) {
		// TODO Auto-generated method stub
		// ����limit���������TopN��ѯ���
		return new StringBuffer(initialSQL.length() + 50).append(initialSQL)
				.append(hasOffset ? " limit ?, ?" : " limit ?").toString();
	}

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
	@Override
	public PreparedStatement setTopNQueryParameter(
			PreparedStatement topNStatement, Object[] args, int offset, int size) {
		// �������ò���
		try {
			// ��������ֵ
			for (int i = 0; i < args.length; i++) {
				// Ϊ��Ӧ��ռλ�����ö�Ӧ��ֵ
				topNStatement.setObject(i + 1, args[i]);
			}
			// ����offset����
			topNStatement.setObject(args.length + 1, offset);
			// ����ÿҳ�����Ŀ������
			topNStatement.setObject(args.length + 2, size);
			// TODO Auto-generated method stub
			// �������ò������Ԥ����������
			return topNStatement;
			// �����쳣
		} catch (Exception e) {
			// ����쳣��Ϣ
			e.printStackTrace();
			// ����null
			return null;
			// TODO: handle exception
		}

	}

}
