/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.ordersys.service.DomainProtectedService;
import com.chinasofti.ordersys.service.login.LoginService;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.sec.Passport;
import com.chinasofti.util.web.upload.MultipartRequestParser;

/**
 * <p>
 * Title:UserLoginServlet
 * </p>
 * <p>
 * Description: �û���¼��Servlet
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
public class UserLoginServlet extends HttpServlet {

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
		// �����������Զ�����������
		MultipartRequestParser parser = new MultipartRequestParser();
		// �������������ȡUserInfo�û���Ϣ����
		UserInfo info = (UserInfo) parser.parse(request, UserInfo.class);
		// ��ȡ���ݼ��ܹ��߶���
		Passport passport = new Passport();
		// ���û������������md5�뷽ʽ����
		info.setUserPass(passport.md5(info.getUserPass()));
		// ������վ�Ƿ������ж��������
		DomainProtectedService domainService = new DomainProtectedService();
		// ����Ǳ�վ�Ϸ�����
		if (domainService.isFromSameDomain()) {
			// �����û���¼�������
			LoginService loginService = new LoginService();
			// ִ�е�¼�ж�
			switch (loginService.login(info)) {
			// ����û�������
			case LoginService.WRONG_USERNAME:
				// ���������б����û��������ڵĴ�����ʾ
				request.setAttribute("ERROR_MSG", "�û��������ڣ�");
				// ���������б����û���д����Ϣ
				request.setAttribute("USER_INFO", info);
				// ��ת�ص�¼����
				request.getRequestDispatcher("/pages/login.jsp").forward(
						request, response);
				break;
			// ����������
			case LoginService.WRONG_PASSWORD:
				// ���������б����������Ĵ�����ʾ
				request.setAttribute("ERROR_MSG", "�û����벻ƥ�䣡");
				// ���������б����û���д����Ϣ
				request.setAttribute("USER_INFO", info);
				// ��ת�ص�¼����
				request.getRequestDispatcher("/pages/login.jsp").forward(
						request, response);
				break;
			// �����½�ɹ�
			case LoginService.LOGIN_OK:
				// �ڻỰ��Ϣ�б����û�����ϸ��Ϣ
				request.getSession().setAttribute("USER_INFO",
						loginService.getLoginUser());
				// �ж��û����
				switch (loginService.getLoginUser().getRoleId()) {
				// ����ǲ�������Ա
				case 1:
					// ��ת������Ա������
					response.sendRedirect("/OrderSys/toadminmain.order");
					break;
				// ����Ǻ����Ա
				case 2:
					// ��ת�������Ա������
					response.sendRedirect("/OrderSys/tokitchenmain.order");
					break;
				// ����ǲ�������Ա
				case 3:
					// ��ת������Ա������
					response.sendRedirect("/OrderSys/towaitermain.order");
					break;

				}

				break;
			// ����û��Ѿ�������
			case LoginService.WRONG_LOCKED:
				// ���������б����û��������Ĵ�����ʾ
				request.setAttribute("ERROR_MSG", "���û��Ѿ���������");
				// ���������б����û���д����Ϣ
				request.setAttribute("USER_INFO", loginService.getLoginUser());
				// ��ת�ص�¼����
				request.getRequestDispatcher("/pages/login.jsp").forward(
						request, response);
				break;
			// ����û��Ѿ�����
			case LoginService.USER_ALREADY_ONLINE:
				// ���������б����û��Ѿ����ߵĴ�����ʾ
				request.setAttribute("ERROR_MSG", "���û��Ѿ����ߣ������ظ���¼��");
				// ���������б����û���д����Ϣ
				request.setAttribute("USER_INFO", info);
				// ��ת�ص�¼����
				request.getRequestDispatcher("/pages/login.jsp").forward(
						request, response);
				break;
			}
			// �������վ�Ƿ�����
		} else {
			// �ڻỰ�б����û���д����Ϣ
			request.getSession().setAttribute("USER_INFO", info);
			// ��ת���Ƿ�������ʾ����
			response.sendRedirect("todomainerror.order");

		}

	}



}
