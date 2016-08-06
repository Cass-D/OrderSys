/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.service.login;

import java.util.ArrayList;

import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

/**
 * <p>
 * Title: CheckUserPassService
 * </p>
 * <p>
 * Description: �ж��û������Ƿ���ȷ�ķ������
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
public class CheckUserPassService {

	/**
	 * ��֤�û��û��������Ƿ���ȷ�ķ���
	 * 
	 * @param info
	 *            �����ж��û�����������û�����
	 * @return �û����������Ƿ���֤ͨ����true��ʾ�û���������ȷ��false��ʾ�û������������
	 * */
	public boolean checkPass(UserInfo info) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS dbHelper = JDBCTemplateWithDS.getJDBCHelper();
		// ���ݸ������û�����ѯ�û���Ϣ
		ArrayList<UserInfo> userList = dbHelper
				.preparedQueryForList(
						"select userId,userAccount,userPass,locked,roleId,roleName from userinfo,roleinfo where userinfo.role=roleinfo.roleId and userinfo.userId=?",
						new Object[] { new Integer(info.getUserId()) },
						UserInfo.class);
		// �ж���ѯ�������
		switch (userList.size()) {
		// ���û�в�ѯ���κ�����
		case 0:
			// ������֤ʧ��
			return false;
			// �����ѯ��һ����¼���ж������Ƿ�һ��
		case 1:
			// �������ܶ���
			Passport passport = new Passport();
			// �ж��û���������������ݿ��е������Ƿ�һ��
			if (userList.get(0).getUserPass()
					.equals(passport.md5(info.getUserPass()))) {
				// ���һ�£��򷵻�true
				return true;
				// �����һ��
			} else {
				// �����û��������벻ƥ��
				return false;
			}

		}
		// ��������·�����֤ʧ��
		return false;
	}

}
