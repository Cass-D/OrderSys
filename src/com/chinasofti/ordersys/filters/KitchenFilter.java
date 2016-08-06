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

import com.chinasofti.ordersys.vo.UserInfo;

/**
 * <p>
 * Title: KitchenFilter
 * </p>
 * <p>
 * Description: ���Ȩ���ж����������ж���ǰ�Ƿ��Ժ����Ա��¼
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
public class KitchenFilter implements Filter {
	/**
	 * ����������ʱִ�еĻص�����
	 * */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * ���Ĺ��˷����������ж���ǰ�Ƿ��Ժ����Ա��ݵ�¼
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
		// �ж���ǰ�û��Ƿ��¼���û�����Ƿ�Ϊ�����Ա
		if (session.getAttribute("USER_INFO") != null
				&& ((UserInfo) session.getAttribute("USER_INFO")).getRoleId() == 2) {
			// �����¼��ݷ���Ҫ��������ִ������
			arg2.doFilter(arg0, arg1);
			// ���δ��¼���¼��ݲ�����Ҫ��
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
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
