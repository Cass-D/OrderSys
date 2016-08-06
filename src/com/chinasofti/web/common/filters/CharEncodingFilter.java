/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.web.common.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: CharEncodingFilter
 * </p>
 * <p>
 * Description: ������Ŀ�������ַ����Ĺ�����
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
public class CharEncodingFilter implements Filter {
	/**
	 * �ַ������Ʊ���
	 * */
	String encoding = "utf-8";

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
		// ���������ַ���
		((HttpServletRequest) request).setCharacterEncoding(encoding);
		// ����ִ������
		chain.doFilter(request, response);

	}

	/**
	 * ��������ʼ��ʱִ�еĻص�����
	 * 
	 * @param arg0
	 *            ���������ö��󣬿��Զ�ȡ�����ļ��е�����������Ϣ
	 * */
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		// ���Ի�ȡ�Զ����ַ�����������
		try {
			// �������ļ���ȡ�ַ�����������
			encoding = filterConfig.getInitParameter("encoding") != null ? filterConfig
					.getInitParameter("encoding") : "utf-8";
			// �����쳣
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();

		}
	}
}
