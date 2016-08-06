/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.util.web.upload.MultipartRequestParser;
import com.chinasofti.web.common.taglib.TokenTag;

/**
 * <p>
 * Title: ModifyDishesServlet
 * </p>
 * <p>
 * Description: �޸Ĳ�Ʒ��Ϣ��Servlet
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
public class ModifyDishesServlet extends HttpServlet {

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
		// �ж��Ƿ���ڱ��ύ����
		if (TokenTag.isTokenValid()) {
			// ������Ʒ����������
			DishesService service = new DishesService();
			// �������������������
			MultipartRequestParser parser = new MultipartRequestParser();
			// ������ȡDishesInfo��Ʒ��Ϣ����
			DishesInfo info = (DishesInfo) parser.parse(request,
					DishesInfo.class);
			// ִ�в�Ʒ��Ϣ�޸Ĺ���
			service.modifyDishes(info);
			// �ͷű��ύ����
			TokenTag.releaseToken();
		}
		// ��ת����Ʒ�������
		response.sendRedirect("/OrderSys/todishesadmin.order");
	}



}
