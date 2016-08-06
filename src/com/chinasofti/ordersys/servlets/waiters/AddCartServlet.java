/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.waiters;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasofti.ordersys.vo.Cart;

/**
 * <p>
 * Title:AddCartServlet
 * </p>
 * <p>
 * Description: ���빺�ﳵ��Servlet
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
public class AddCartServlet extends HttpServlet {

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
		// ��ȡ�Ự����
		HttpSession session = request.getSession();
		// �������ﳵ����
		Cart cart = new Cart();
		// ����Ự�еĴ��ڹ��ﳵ
		if (session.getAttribute("CART") != null) {
			// ֱ�ӻ�ȡ�Ự�еĹ��ﳵ����
			cart = (Cart) session.getAttribute("CART");
		}
		// �������ű���
		Integer tableId = 1;
		// ����Ự�д���������Ϣ
		if (session.getAttribute("TABLE_ID") != null) {
			// ֱ�ӻ�ȡ������Ϣ
			tableId = (Integer) session.getAttribute("TABLE_ID");
		}
		//���ù��ﳵ��������Ϣ
		cart.setTableId(tableId.intValue());
		//��ȡ���μ��빺�ﳵ�Ĳ�Ʒ����
		int num = Integer.parseInt(request.getParameter("num"));
		//��ȡ���μ��빺�ﳵ�Ĳ�ƷID
		int dishesId = Integer.parseInt(request.getParameter("dishes"));
		//����ƷID�Ͳ�Ʒ�������뵽���ﳵ��Ʒ������
		cart.getUnits().add(cart.createUnit(dishesId, num));
		//�����ﳵ�������õ��Ự��
		session.setAttribute("CART", cart);

//		for (Cart.CartUnit unit : cart.getUnits()) {
//
//			System.out.println(unit.getDishesId() + "\t" + unit.getNum());
//
//		}
	}



}
