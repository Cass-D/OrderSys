/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.serverpush;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: ExchangeRatePublishServlet
 * </p>
 * <p>
 * Description: ����ʵʱ������Ϣ�ķ�������Servlet
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
public class ExchangeRatePublishServlet extends HttpServlet {

	/**
	 * ������ʵʱ������Ϣ��Get HTTP���󷽷�������ServletҪ��ͬ�����󷵻���ͬ�Ľ������˵���doPost����ͳһ����
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
		// ���������ݵ��ַ���������"utf-8",��ȷ���ú��ֹ��ȡ�������ݱ������
		request.setCharacterEncoding("utf-8");
		// ��ȡ�ͻ��˷��͹����Ļ�������
		String message = request.getParameter("inputRate");
		// �����������������ݵ���Ϣ������
		MessageProducer producer = new MessageProducer();
		// �������еĿͻ���
		for (int i = 0; i < GetExchangeRateServlet.clients.size(); i++) {
			//��ÿһ���ͻ�������һ��������Ϣ
			producer.sendMessage(GetExchangeRateServlet.clients.get(i)
					.toString(), "rtrate", "1$ = " + message + "RMB");
		}		
	}
}
