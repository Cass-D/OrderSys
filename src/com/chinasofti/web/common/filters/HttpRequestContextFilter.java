/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.web.common.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.web.common.httpequest.HttpRequestContext;

/**
 * <p>
 * Title: HttpRequestContextFilter
 * </p>
 * <p>
 * Description: �ڴ���ÿ������ǰ����������Ӧ����Ĺ�����
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
public class HttpRequestContextFilter implements Filter {
	/**
	 * ServletContext����
	 * */
	private ServletContext context;

	/**
	 * ����������ʱִ�еĻص�����
	 * */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * ���Ĺ��˷����������ж���ǰ�Ƿ��Բ�������Ա��ݵ�¼
	 * 
	 * @param arg0
	 *            �������
	 * @param arg1
	 *            ��Ӧ����
	 * @param arg2
	 *            ������������
	 * */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// ��ThreadLocal�й�����������Ӧ����
		HttpRequestContext.setHttpRequestContext((HttpServletRequest) request,
				(HttpServletResponse) response, context);
		// ����ִ������
		chain.doFilter(request, response);

	}

	/**
	 * ��������ʼ��ʱִ�еĻص�����
	 * 
	 * @param arg0
	 *            ���������ö��󣬿��Զ�ȡ�����ļ��е�����������Ϣ
	 * */
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		// ��ȡServletContext����
		context = config.getServletContext();

	}

}
