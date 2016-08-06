/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: InitSysServlet
 * </p>
 * <p>
 * Description: ��ʼ��ϵͳ��Ϣ��Servlet
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
public class InitSysServlet extends HttpServlet {

	/**
	 * ����GET��ʽ����Servletʱ��service�����ص�
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * ����POST��ʽ����Servletʱ��service�����ص�
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * ��ʼ����Servlet�ķ����������������ڻ�ȡ��Ŀ������
	 */
	public void init() throws ServletException {
		// Put your code here
		// ���屾��Ŀ����
		String sysName = "�������-����������ϵͳ";
		// ���Դ������ļ��ж�ȡ�Զ�����Ŀ����
		try {
			// ��ȡ�Զ�����Ŀ����
			sysName = getServletConfig().getInitParameter("sysname") != null ? getServletConfig()
					.getInitParameter("sysname") : "�������-����������ϵͳ";
			// �����쳣��Ϣ
		} catch (Exception ex) {

		}
		// ����Ŀ����Ϣ���õ�ServletContext��������
		getServletContext().setAttribute("ORDER_SYS_NAME", sysName);
	}

}
