/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasofti.web.servlets.common.SaveCodeServlet;

/**
 * <p>
 * Title:CheckSaveCodeServlet
 * </p>
 * <p>
 * Description: ����Ajax�����֤���Ƿ�������ȷ��Servlet
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
public class CheckSaveCodeServlet extends HttpServlet {

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
		// ��ȡ�û��������֤��
		String inputCode = request.getParameter("code");
		// ��ȡ�Ự����
		HttpSession session = request.getSession();
		// ��ȡ�Ự�б������֤��
		String sessionCode = session.getAttribute(
				SaveCodeServlet.CODE_SESSION_ATTR_NAME).toString();

		// System.out.println(inputCode + "                   " + sessionCode);
		// ���÷���MIME����
		response.setContentType("text/html");
		// ��ȡ��Կͻ��˵��ı������
		PrintWriter out = response.getWriter();
		// ����ں��Դ�Сд��������û��������֤����Ự��Ϣ�б������֤���ܹ�ƥ��
		if (sessionCode.equalsIgnoreCase(inputCode)) {
			// �����֤����ȷ�ı�ʶ
			out.print("OK");
			// �������ƥ��
		} else {
			// �����֤�����ı�ʶ
			out.print("FAIL");
		}

		// ˢ�������
		out.flush();
		// �ر������
		out.close();
	}

	

}
