/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.web.common.httpequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: HttpRequestContext
 * </p>
 * <p>
 * Description: ������Ӧ��ServletContext����İ�װ����
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
public class HttpRequestContext {

	/**
	 * �������
	 * */
	private HttpServletRequest request;
	/**
	 * ��Ӧ����
	 * */
	private HttpServletResponse response;
	/**
	 * ServletContext����
	 * */
	private ServletContext servletContext;

	/**
	 * ��������������װ����
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 * @param servletContext
	 *            ServletContext����
	 * */
	private HttpRequestContext(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext) {
		// ��ʼ���������
		this.request = request;
		// ��ʼ����Ӧ����
		this.response = response;
		// ��ʼ��ServletContext����
		this.servletContext = servletContext;
	}

	/**
	 * ThreadLocal���������ڵ�һ�߳��й�������
	 * */
	private static ThreadLocal<HttpRequestContext> currentContext = new ThreadLocal<HttpRequestContext>();

	/**
	 * ���ù������ݵķ���
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 * @param servletContext
	 *            ServletContext����
	 * */
	public static void setHttpRequestContext(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext) {
		// ������װ����
		HttpRequestContext context = new HttpRequestContext(request, response,
				servletContext);
		// ��ThreadLocal�д�Ű�װ����
		currentContext.set(context);
	}

	/**
	 * ��ȡ�������ķ���
	 * 
	 * @return �������
	 * */
	public static HttpServletRequest getRequest() {
		// �����������
		return currentContext.get() == null ? null
				: currentContext.get().request;
	}

	/**
	 * ��ȡ��Ӧ����ķ���
	 * 
	 * @return ��Ӧ����
	 * */
	public static HttpServletResponse getResponse() {
		// ������Ӧ����
		return currentContext.get() == null ? null
				: currentContext.get().response;
	}

	/**
	 * ��ȡServletContext����ķ���
	 * 
	 * @return ServletContext����
	 * */
	public static ServletContext getServletContext() {
		// ����servletContext����
		return currentContext.get() == null ? null
				: currentContext.get().servletContext;
	}

}
