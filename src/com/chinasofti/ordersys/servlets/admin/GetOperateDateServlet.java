/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
 * Title: GetOperateDateServlet
 * </p>
 * <p>
 * Description: ��ȡ��Ӫ���ݵ�Servlet
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
public class GetOperateDateServlet extends HttpServlet {

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
		// ���Դ����������ݽ��XML
		try {
			// ������������������
			OrderService service = new OrderService();
			// �������ڸ�ʽ������
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// ��ȡ��ʼʱ��
			Date begin = sdf.parse(request.getParameter("bt"));
			// ��ȡ����ʱ��
			Date end = sdf.parse(request.getParameter("et"));
			// ��ѯ�ᵥʱ���ڿ�ʼʱ�������ʱ��֮������ж�����Ϣ
			ArrayList<OrderInfo> list = service.getOrderInfoBetweenDate(begin,
					end);
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("orders");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ��������������еĶ�����Ϣ
			for (OrderInfo info : list) {
				// ��ȡÿ���������ܼ�
				float sumPrice = service.getSumPriceByOrderId(new Integer(info
						.getOrderId()));
				// // ÿһ����������һ��������ǩ�ڵ�
				Element order = doc.createElement("order");
				// ��������id��ǩ
				Element orderId = doc.createElement("orderId");
				// ���ö���id��ǩ�ı�����
				orderId.setTextContent(info.getOrderId() + "");
				// ������id��ǩ����Ϊ������ǩ���ӱ�ǩ
				order.appendChild(orderId);
				// �������ű�ǩ
				Element tableId = doc.createElement("tableId");
				// �������ű�ǩ�ı�����
				tableId.setTextContent(info.getTableId() + "");
				// �����ű�ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(tableId);
				// �����ܼ۱�ǩ
				Element sumPriceElement = doc.createElement("sumPrice");
				// �����ܼ۱�ǩ�ı�����
				sumPriceElement.setTextContent(sumPrice + "");
				// ���ܼ۱�ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(sumPriceElement);
				// ������ͷ���Ա�û�����ǩ
				Element userAccount = doc.createElement("userAccount");
				// ���õ�ͷ���Ա�û�����ǩ�ı�����
				userAccount.setTextContent(info.getUserAccount());
				// ����ͷ���Ա�û�����ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(userAccount);
				// ���������ᵥʱ���ǩ
				Element orderEndDate = doc.createElement("orderEndDate");
				// ����ʱ�䡢���ڸ�ʽ������
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// ���ýᵥʱ���ǩ����Ϊ��ʽ�����ʱ���ַ���
				orderEndDate.setTextContent(sdf.format(info.getOrderEndDate()));
				// ���ᵥʱ���ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(orderEndDate);
				// ��������ǩ����Ϊ����ǩ�ӱ�ǩ
				root.appendChild(order);

			}
			// ��������DOM��ת��ΪXML�ĵ��ṹ�ַ���������ͻ���
			TransformerFactory
					.newInstance()
					.newTransformer()
					.transform(new DOMSource(doc),
							new StreamResult(response.getOutputStream()));

			// �����ѯ��ת�������е��쳣��Ϣ
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
