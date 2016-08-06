/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Title: ExchangeRatePublishServlet
 * </p>
 * <p>
 * Description:
 * ������������ڽ�����Ϣ�ĺ���Servlet�����û���Ҫ��ȡ������Ϣʱ��ֱ�������Servlet,����Servlet���ݱ�ʾ��Ҫ������Ϣ����Ĳ���
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
public class GetExchangeRateServlet extends HttpServlet {
	/**
	 * ���ڴ�ŵ�ǰϵͳ�е����пͻ��ˣ������е�ÿһ��Ԫ�ض��ǵ�һ�ͻ��˵�sessionid
	 * */
	static Vector clients = new Vector();
	/**
	 * ϣ����ȡ������Ϣʱ���ṩ��Servlet����Ϣ���ƵĲ�����
	 * */
	String messageTitleParameterName = "messageTitle";

	/**
	 * �����ȡʵʱ������Ϣ��Get HTTP���󷽷�������ServletҪ��ͬ�����󷵻���ͬ�Ľ������˵���doPost����ͳһ����
	 * 
	 * @param request
	 *            Http�������
	 * @param response
	 *            Http��Ӧ����
	 * @throws ServletException
	 *             �����servlet���ʷ�����쳣�����׳��쳣��Ϣ
	 * @throws IOException
	 *             ���������ʳ������׳��쳣��Ϣ
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ����doPost��������
		doPost(request, response);
	}

	/**
	 * ����Post����ķ���
	 * 
	 * @param request
	 *            http�������
	 * @param response
	 *            http��Ӧ����
	 * @throws ServletException
	 *             �����servlet���ʷ�����쳣�����׳��쳣��Ϣ
	 * @throws IOException
	 *             ���������ʳ������׳��쳣��Ϣ
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ������ȷ�������ַ����Է�ֹ��������
		request.setCharacterEncoding("utf-8");
		// ���õ��ͻ����������������ݵ��ַ���
		response.setCharacterEncoding("utf-8");
		// ��ȡ�û���session�Ự����
		HttpSession session = request.getSession(true);
		// �жϵ�ǰ�Ự��idֵ�Ƿ�λ���û�������
		if (!clients.contains(session.getId())) {
			// �����ǰ�ػ���idֵû�����û������У�˵��������һ���µ��û��������û���sessionidֵ�����û�����
			clients.add(session.getId());
		}

		// ��ȡ�û�ϣ��ץȡ���ݵ�����
		String messageTitle = request.getParameter(messageTitleParameterName);
		// �����������������͵���Ϣ������
		MessageConsumer mconsumer = new MessageConsumer();

		// ������Ҫ�������ڲ��������ʹ����Ӧ������˶���һ��final�汾
		final HttpServletResponse rsp = response;
		// ������Ϣ�Ĵ������
		MessageHandler handler = new MessageHandler() {
			// ʵ�ֵ���ȡ��Ϣ�����Ϣ����ʵ�ʴ���Ļص�������
			// �ص�������messageQueue���������˵�ǰϵͳʹ�õ���Ϣ�ȴ�����
			// �ص�������key���������˵�ǰ��ȡ������Ϣ���͵�Ŀ��sessionid����Ϣ����
			// �ص�������msg����������ʵ�ʵ���Ϣ����
			@Override
			public void handle(Hashtable<ServerPushKey, Message> messageQueue,
					ServerPushKey key, Message msg) {

				try {
					// ��ȡ��Կͻ��˵��ַ������
					PrintWriter pw = rsp.getWriter();
					// ����Ϣ�ַ���ֱ�ӷ��͸��ͻ��������
					pw.print(msg.getMsg());
				} catch (Exception ex) {
					// ��������쳣������쳣��Ϣ
					ex.printStackTrace();
				}
			}
		};
		// ������Ϣ�����߳��Ի�ȡ��Ϣ����
		mconsumer.searchMessage(session.getId(), messageTitle, handler);
	}

	/**
	 * ��ʼ��Servlet�ķ�������Ҫ�����ǻ�ȡ�Զ������Ϣ���Ʋ�����
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
