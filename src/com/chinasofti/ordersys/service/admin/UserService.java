/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.service.admin;

import java.util.ArrayList;

import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;
import com.chinasofti.util.sec.Passport;

/**
 * <p>
 * Title: UserService
 * </p>
 * <p>
 * Description: �û�����������
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
public class UserService {

	/**
	 * ��ҳ��ȡ�û����ݵķ���
	 * 
	 * @param page
	 *            Ҫ��ȡ���ݵ�ҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @return ��ǰҳ���û������б�
	 * */
	public ArrayList<UserInfo> getByPage(int page, int pageSize) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ͨ����ѯ����ȡ��Ӧҳ������
		ArrayList<UserInfo> list = helper
				.preparedForPageList(
						"select userId,userAccount,userPass,locked,roleId,roleName,faceimg from userinfo,roleinfo where userinfo.role=roleinfo.roleId order by userId",
						new Object[] {}, page, pageSize, UserInfo.class);
		// ���ؽ��
		return list;
	}

	/**
	 * ��ȡ�û���Ϣ�����ҳ��
	 * 
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @return ��ǰ���ݿ������ݵ����ҳ��
	 * */
	public int getMaxPage(int pageSize) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ȡ���ҳ����Ϣ
		Long rows = (Long) helper.preparedQueryForObject(
				"select count(*) from userinfo", new Object[] {});
		// �������ҳ��
		return (int) ((rows.longValue() - 1) / pageSize + 1);
	}

	/**
	 * ����û��ķ���
	 * 
	 * @param info
	 *            ��Ҫ��ӵ��û���Ϣ
	 * */
	public void addUser(UserInfo info) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// �������ܹ���
		Passport passport = new Passport();
		// ִ���û���Ϣ�������
		helper.executePreparedUpdate(
				"insert into userinfo(userAccount,userPass,role,faceImg) values(?,?,?,?)",
				new Object[] { info.getUserAccount(),
						passport.md5(info.getUserPass()),
						new Integer(info.getRoleId()), info.getFaceimg() });
	}

	/**
	 * ɾ���û��ķ���
	 * 
	 * @param userId
	 *            ��ɾ���û���Id
	 * */
	public void deleteUser(Integer userId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ɾ������ID���û���Ϣ
		helper.executePreparedUpdate("delete from userinfo where userId=?",
				new Object[] { userId });
	}

	/**
	 * �޸��û�������Ϣ�ķ���
	 * 
	 * @param info
	 *            ��Ҫ�޸ĵ��û���Ϣ������userId����ָ����Ҫ�޸ĵ��û�ID��������ϢΪĿ��ֵ�������޸���Ϣֻ���޸������ͷ��
	 * */
	public void modify(UserInfo info) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// �������ܹ���
		Passport passport = new Passport();
		// �޸ı�����Ϣ
		helper.executePreparedUpdate(
				"update userinfo set userPass=?,faceimg=? where userId=?",
				new Object[] { passport.md5(info.getUserPass()),
						info.getFaceimg(), new Integer(info.getUserId()) });

	}

	/**
	 * ����Ա�޸��û���Ϣ�ķ���
	 * 
	 * @param info
	 *            ��Ҫ�޸ĵ��û���Ϣ������userId����ָ����Ҫ�޸ĵ��û�ID��������ϢΪĿ��ֵ
	 * */
	public void adminModify(UserInfo info) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// �������ܹ���
		Passport passport = new Passport();
		// �޸ı�����Ϣ
		helper.executePreparedUpdate(
				"update userinfo set userPass=?,faceimg=?,role=? where userId=?",
				new Object[] { passport.md5(info.getUserPass()),
						info.getFaceimg(), new Integer(info.getRoleId()),
						new Integer(info.getUserId()) });

	}

	/**
	 * ����ID��ȡ�û���ϸ��Ϣ�ķ���
	 * 
	 * @param userId
	 *            ��Ҫ��ȡ��ϸ��Ϣ���û�ID
	 * @return ���ز�ѯ�����û���ϸ��Ϣ
	 * */
	public UserInfo getUserById(Integer userId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// �����û�ID�������в�ѯ����
		ArrayList<UserInfo> list = helper
				.preparedQueryForList(
						"select userId,userAccount,userPass,locked,roleId,roleName,faceimg from userinfo,roleinfo where userinfo.role=roleinfo.roleId and userId=?",
						new Object[] { userId }, UserInfo.class);
		// ���ظ���ID��Ӧ���û���Ϣ
		return list.get(0);
	}

}
