/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.util.web.serverpush.MessageProducer;

/**
 * <p>
 * Title: SendBordServlet
 * </p>
 * <p>
 * Description: ����ʵʱ�����Servlet
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
public class SendBordServlet extends HttpServlet {

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
		// ������Ӧ�ַ���
		response.setCharacterEncoding("utf-8");
		// ��ȡ������Ϣ
		String bord = request.getParameter("bord");
		// ����ʵʩ��Ϣ������
		MessageProducer producer = new MessageProducer();
		// ��ȡʵʱ������Ϣ�ȴ��б�
		ArrayList<String> list = GetRTBordServlet.bords;
		// �����ȴ��б�
		for (int i = list.size() - 1; i >= 0; i--) {
			// ��ȡ�ȴ���Ϣ���û�sessionID
			String id = list.get(i);
			// ��Ը�sessionID����Ϣ���⡢����������Ϣ
			producer.sendMessage(id, "rtbord", bord);
			// ����sessionID�ӵȴ��б���ɾ��
			list.remove(id);
		}
	}



}
