/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.kitchen;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.util.web.serverpush.MessageProducer;

/**
 * <p>
 * Title:DishesDoneServlet
 * </p>
 * <p>
 * Description: ��Ʒ������ʹ�����Ϣ��Servlet
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
public class DishesDoneServlet extends HttpServlet {

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
		// ������Ӧ�����
		response.setCharacterEncoding("utf-8");
		// ��ȡ��Ʒ��Ӧ������
		String tableId = request.getParameter("tableId");
		// ��ȡ��Ʒ��
		String dishesName = request.getParameter("dishesName");
		// ����ʹ��ajax�ύ�������Ҫת��
		dishesName = new String(dishesName.getBytes("iso8859-1"), "utf-8");
		// ������Ϣ������
		MessageProducer producer = new MessageProducer();
		// ��ȡ����Ա�ȴ��б�
		ArrayList<String> list = GetRTDishesServlet.disheses;
		// ��������Ա�ȴ��б�
		for (int i = list.size() - 1; i >= 0; i--) {
			// ��ȡ�ض��ķ���ԱSessionID
			String id = list.get(i);
			// �Ը÷���Ա������Ʒ��ɵȴ����˵���Ϣ
			producer.sendMessage(id, "rtdishes", "����[" + tableId + "]�Ĳ�Ʒ["
					+ dishesName + "]�Ѿ�������ɣ��봫�ˣ�");
			// �ӵȴ��б���ɾ���÷���Ա
			list.remove(id);
		}
	}

}
