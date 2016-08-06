/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.ordersys.service.admin.DishesService;

import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.ordersys.vo.UserInfo;

/**
 * <p>
 * Title:ToModifyDishesServlet
 * </p>
 * <p>
 * Description: ��ת�޸Ĳ�Ʒ�������תServlet
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
public class ToModifyDishesServlet extends HttpServlet {

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
		// ������Ʒ����������
		DishesService service = new DishesService();
		// ��ȡҪ�޸ĵĲ�ƷID����ѯ��Ӧ�Ĳ�Ʒ��Ϣ
		DishesInfo info = service.getDishesById(new Integer(request
				.getParameter("dishesId")));
		// ����Ʒ��Ϣ����request������
		request.setAttribute("DISHES_INFO", info);
		// ��ת����Ʒ��Ϣ�޸Ľ���
		request.getRequestDispatcher("/pages/admin/modifydishes.jsp").forward(
				request, response);
	}

}
