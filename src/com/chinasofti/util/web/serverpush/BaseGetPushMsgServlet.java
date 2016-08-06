/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Title: BaseGetPushMsgServlet
 * </p>
 * <p>
 * Description: ʵ�ֻ�ȡ������Ϣ�Ļ���Servlet
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: ChinaSoft International Ltd.
 * </p>
 * 
 * @author etc
 * @version 1.0
 */
public abstract class BaseGetPushMsgServlet extends HttpServlet {

	/**
	 * ϣ����ȡ������Ϣʱ���ṩ��Servlet����Ϣ���ƵĲ�����
	 * */
	String messageTitleParameterName = "messageTitle";

	/**
	 * Constructor of the object.
	 */
	public BaseGetPushMsgServlet() {
		super();
	}

	/**
	 * ����������Ϣ�������ĳ���ص����������øûص�ȷ����ȡ������Ϣ��Ĵ���ʽ
	 * 
	 * @param request
	 *            Servlet��ȡ����Http�������
	 * @param response
	 *            Servlet��ȡ����Http��Ӧ����
	 * @return �����õ���Ϣ������󣬹�Servletʹ��
	 * */
	public abstract MessageHandler getHandler(final HttpServletRequest request,
			final HttpServletResponse response);

	public void initService(final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session) {

	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ������ȷ�������ַ����Է�ֹ��������
		request.setCharacterEncoding("utf-8");
		// ���õ��ͻ����������������ݵ��ַ���
		response.setCharacterEncoding("utf-8");
		// ��ȡ�û���session�Ự����

		HttpSession session = request.getSession(true);
		initService(request, response, session);

		// ��ȡ�û�ϣ��ץȡ���ݵ�����
		String messageTitle = request.getParameter(messageTitleParameterName);
		// �����������������͵���Ϣ������
		MessageConsumer mconsumer = new MessageConsumer();

		// ������Ҫ�������ڲ��������ʹ����Ӧ������˶���һ��final�汾
		final HttpServletResponse rsp = response;

		// ����������ʵ�ֵ�setHandler����������Ϣ�Ĵ������
		MessageHandler handler = getHandler(request, response);

		// ������Ϣ�����߳��Ի�ȡ��Ϣ����
		mconsumer.searchMessage(session.getId(), messageTitle, handler);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// ��ȡServletConfig�������ڶ�ȡweb.xml����Ա�Servlet���õĳ�ʼ��������Ϣ
		ServletConfig config = getServletConfig();
		// ����������ֽ���MessageTitleParameterName�ĳ�ʼ���������򽫸ò�����Ӧ�Ĳ���ֵ��Ϊ�Զ������Ϣ���Ʋ�����
		if (config.getInitParameter("MessageTitleParameterName") != null) {
			messageTitleParameterName = config
					.getInitParameter("MessageTitleParameterName");
		}
	}

}
