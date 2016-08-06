/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chinasofti.ordersys.service.waiters.OrderService;
import com.chinasofti.ordersys.vo.OrderInfo;

/**
 * <p>
 * Title: GetOrderDetailServlet
 * </p>
 * <p>
 * Description: ��ȡ������Ʒ�����Servlet
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
public class GetOrderDetailServlet extends HttpServlet {

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
	 * ����Post��ʽ����Servletʱ��service�����ص�,Ϊ�˿����Ժ��ƽ̨�����ԣ� ����Ӧ����xml���
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ���÷��ص�MIME����Ϊxml
		response.setContentType("text/xml");
		// ��ȡ���洫�ݵĶ���ID
		Integer orderId = new Integer(request.getParameter("orderId"));
		// ������������������
		OrderService service = new OrderService();
		// ��ѯ��Ӧ������Ʒ����
		ArrayList<OrderInfo> list = service.getOrderDetailById(orderId);
		// ��ѯ����������Ϣ
		OrderInfo info = service.getOrderById(orderId);
		// ���Դ����������ݽ��XML
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("order");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ��������Id��ǩ
			Element oidElement = doc.createElement("orderId");
			// ���ö���ID��ǩ�ı�����
			oidElement.setTextContent(info.getOrderId() + "");
			// ������ID��ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(oidElement);
			// ����������ͷ���Ա�û�����ǩ
			Element userAccountElement = doc.createElement("userAccount");
			// ���ö������Ա�û�����ǩ�ı�����
			userAccountElement.setTextContent(info.getUserAccount());
			// ���������Ա�û�����ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(userAccountElement);
			// �������ű�ǩ
			Element tid = doc.createElement("tableId");
			// �������ű�ǩ�ı�����
			tid.setTextContent(info.getTableId() + "");
			// �����ű�ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(tid);
			// ��������ʱ���ǩ
			Element orderBeginDateElement = doc.createElement("orderBeginDate");
			// ����ʱ�����ڸ�ʽת������
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// ���ÿ���ʱ���ǩ�ı�����
			orderBeginDateElement.setTextContent(sdf.format(info
					.getOrderBeginDate()));
			// ������ʱ���ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(orderBeginDateElement);
			// �����ᵥʱ���ǩ
			Element orderEndDateElement = doc.createElement("orderEndDate");
			// ���ýᵥʱ���ǩ�ı�����
			orderEndDateElement.setTextContent(sdf.format(info
					.getOrderEndDate()));
			// ���ᵥʱ���ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(orderEndDateElement);
			// ��ȡ�������ܼ�
			double sum = service.getSumPriceByOrderId(orderId);
			// �����ܼ۱�ǩ
			Element sumPrice = doc.createElement("sumPrice");
			// �����ܼ۱�ǩ�ı�����
			sumPrice.setTextContent(sum + "");
			// ���ܼ۱�ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(sumPrice);
			// ѭ���������������б�
			for (OrderInfo oi : list) {
				// ÿһ���������鴴��һ��������Ԫ��ǩ
				Element unit = doc.createElement("unit");
				// ��������Ԫ��ǩ����Ϊ����ǩ�ӱ�ǩ
				root.appendChild(unit);
				// ������Ʒ����ǩ
				Element dishesName = doc.createElement("dishesName");
				// ���ò�Ʒ����ǩ�ı�����
				dishesName.setTextContent(oi.getDishesName());
				// ����Ʒ����ǩ����Ϊ��Ԫ��ǩ�ӱ�ǩ
				unit.appendChild(dishesName);
				// ������Ʒ�۸��ǩ
				Element dishesPrice = doc.createElement("dishesPrice");
				// ���ò�Ʒ�۸��ǩ�ı�����
				dishesPrice.setTextContent(oi.getDishesPrice() + "");
				// ����Ʒ�۸��ǩ����Ϊ��Ԫ��ǩ�ӱ�ǩ
				unit.appendChild(dishesPrice);
				// ������Ʒ������ǩ
				Element num = doc.createElement("num");
				// ���ò�Ʒ������ǩ�ı�����
				num.setTextContent(oi.getNum() + "");
				// ����Ʒ������ǩ����Ϊ��Ԫ��ǩ�ӱ�ǩ
				unit.appendChild(num);
			}
			// ��������DOM��ת��ΪXML�ĵ��ṹ�ַ���������ͻ���
			TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new DOMSource(doc),
							new StreamResult(response.getOutputStream()));

			// response.getWriter().write(msg);
			// �����ѯ��ת�������е��쳣��Ϣ
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();
		}

	}

}
