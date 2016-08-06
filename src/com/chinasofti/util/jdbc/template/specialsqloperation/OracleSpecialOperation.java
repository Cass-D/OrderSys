/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.template.specialsqloperation;

import java.sql.PreparedStatement;

/**
 * <p>
 * Title: OracleSpecialOperation
 * </p>
 * <p>
 * Description:Oracle���ݿ��ض���������
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
public class OracleSpecialOperation extends SpecialSQLOperation {
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
		// �Ƴ���ʼ��SQL���ǰ������ÿհ�
		initialSQL = initialSQL.trim();
		// ��ʼ��SQL������Ƿ���for update�Ӿ�
		boolean isForUpdate = false;
		// �ж���ʼ��SQL������Ƿ���for update�Ӿ�
		if (initialSQL.toLowerCase().endsWith(" for update")) {
			// ɾ��for update�Ӿ�
			initialSQL = initialSQL.substring(0, initialSQL.length() - 11);
			// ����for update�Ӿ�״̬
			isForUpdate = true;
		}
		// ����Ŀ��SQL����ַ���������
		StringBuffer pagingSelect = new StringBuffer(initialSQL.length() + 200);
		// �����offSet��Ϣ
		if (hasOffset) {
			// ���rownumα����Ϣ
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
			// ���û��offset��Ϣ
		} else {
			// ֱ�Ӳ�ѯĿ����
			pagingSelect.append("select * from ( ");
		}
		// ����ʼ����ѯ��Ϊ��ʱ��ͼ��ѯĿ��
		pagingSelect.append(initialSQL);
		// �����offset
		if (hasOffset) {
			// ���offset����
			pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
			// ���û��offset
		} else {
			// ֻ�涨��ѯ�������Ŀ��
			pagingSelect.append(" ) where rownum <= ?");
		}
		// �������for update�Ӿ�
		if (isForUpdate) {
			// ��ȫfor update�Ӿ�
			pagingSelect.append(" for update");
		}
		// ���ؽ��SQL���
		return pagingSelect.toString();
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
		// TODO Auto-generated method stub
		// �������ò���
		try {
			// ��������ֵ
			for (int i = 0; i < args.length; i++) {
				// Ϊ��Ӧ��ռλ�����ö�Ӧ��ֵ
				topNStatement.setObject(i + 1, args[i]);
			}
			// ����ÿҳ�����Ŀ������
			topNStatement.setObject(args.length + 1, size + size);
			// ����offset����
			topNStatement.setObject(args.length + 2, offset);
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
