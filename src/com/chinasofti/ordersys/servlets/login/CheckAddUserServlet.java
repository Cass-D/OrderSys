/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;

/**
 * <p>
 * Title:CheckAddUserServlet
 * </p>
 * <p>
 * Description: ����Ajax����û��Ƿ��Ѿ����ڵ�Servlet
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
public class CheckAddUserServlet extends HttpServlet {

	/**
	 * ����GET��ʽ����Servletʱ��service�����ص�,��Servlet��ͬ������Ҫ��ͬ����Ӧ������ڱ�Servlet��ֱ�ӵ���doPost
	 * ���Ա�֤��Ӧ��һ����
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * ����Post��ʽ����Servletʱ��service�����ص�
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ��ȡ��������е��û�����Ϣ
		String userAccount = request.getParameter("name");
		// ������ajax���������Ҫת��
		userAccount = new String(userAccount.getBytes("iso8859-1"), "utf-8");
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ѯ��Ӧ�û������û���Ϣ
		ArrayList<UserInfo> list = helper.preparedQueryForList(
				"select userAccount from userinfo where userAccount=?",
				new Object[] { userAccount }, UserInfo.class);
		// ��ȡ��Կͻ��˵����������
		PrintWriter pw = response.getWriter();
		// ������ݿ���������
		if (list.size() == 0) {
			// ���������ӱ�ʶ
			pw.print("OK");
			// ������ݿ���������
		} else {
			// ���������ӱ�ʶ
			pw.print("FAIL");
		}
	}



}
