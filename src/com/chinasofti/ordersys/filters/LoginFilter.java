/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Title: LoginFilter
 * </p>
 * <p>
 * Description: ��¼Ȩ���ж����������ж���ǰ�û��Ƿ��¼
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
public class LoginFilter implements Filter {

	/**
	 * ����������ʱִ�еĻص�����
	 * */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * ���Ĺ��˷����������ж���ǰ�û��Ƿ��¼
	 * 
	 * @param arg0
	 *            �������
	 * @param arg1
	 *            ��Ӧ����
	 * @param arg2
	 *            ������������
	 * */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// ��ȡHttpServletReuqst����
		HttpServletRequest request = (HttpServletRequest) arg0;
		// ��ȡHttpServletResponse��Ӧ
		HttpServletResponse response = (HttpServletResponse) arg1;
		// ��ȡHttpSession�Ự���ٶ���
		HttpSession session = request.getSession();
		
		System.out.println(session.getAttribute("USER_INFO"));
		
		// �ж���ǰ�û��Ƿ��¼
		if (session.getAttribute("USER_INFO") != null) {
			// ����Ѿ���¼��������ִ������
			arg2.doFilter(arg0, arg1);
			// ���δ��¼
		} else {
			// ��ת����¼����
			response.sendRedirect("/OrderSys");
		}
	}

	/**
	 * ��������ʼ��ʱִ�еĻص�����
	 * 
	 * @param arg0
	 *            ���������ö��󣬿��Զ�ȡ�����ļ��е�����������Ϣ
	 * */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
