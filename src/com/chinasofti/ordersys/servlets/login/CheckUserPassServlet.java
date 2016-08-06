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

import com.chinasofti.ordersys.service.login.CheckUserPassService;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.web.upload.MultipartRequestParser;

/**
 * <p>
 * Title:CheckUserPassServlet
 * </p>
 * <p>
 * Description: ����Ajax����û������Ƿ���ȷ��Servlet
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
public class CheckUserPassServlet extends HttpServlet {

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
		// �������������ݽ������߶���
		MultipartRequestParser parser = new MultipartRequestParser();
		// ��������ȡUserInfo����
		UserInfo info = (UserInfo) parser.parse(request, UserInfo.class);
		// �����ж��û������Ƿ���ȷ�ķ������
		CheckUserPassService service = new CheckUserPassService();
		// ��ȡ��Կͻ��˵��ı������
		PrintWriter pw = response.getWriter();
		// ���������ȷ
		if (service.checkPass(info)) {
			// ���������ȷ�ı�ʶ
			pw.print("OK");
			// �������
		} else {
			// ����������ı�ʶ
			pw.print("FAIL");
		}

	}

}
