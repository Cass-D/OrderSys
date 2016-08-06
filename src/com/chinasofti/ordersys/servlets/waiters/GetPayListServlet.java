/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.waiters;

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
 * Title: GetPayListServlet
 * </p>
 * <p>
 * Description: ��ȡ��Ҫ�򵥶����б��Servlet
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
public class GetPayListServlet extends HttpServlet {

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
		// ������������������
		OrderService service = new OrderService();
		// �����ѯ����ҳ�����
		int page = 1;
		// ��������а���ҳ����Ϣ
		if (request.getParameter("page") != null) {
			// ��ȡ�����е�ҳ����Ϣ
			page = Integer.parseInt(request.getParameter("page"));
		}
		// ��ȡ���ҳ����
		int maxPage = service.getMaxPage(10, 0);
		// �Ե�ǰ��ҳ�������о������С��1����ֱ����ʾ��һҳ������
		page = page < 1 ? 1 : page;
		// �Ե�ǰ��ҳ�������о�������������ҳ�룬��ֱ����ʾ���һҳ������
		page = page > maxPage ? maxPage : page;
		// ����ҳ����Ϣ��ѯ��Ҫ�򵥵Ķ�����Ϣ
		ArrayList<OrderInfo> list = service.getNeedPayOrdersByPage(page, 10, 0);
		// ���Խ�����ṹ��Ϊxml�ĵ�
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("orderes");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ������ÿһ��������Ϣ
			for (OrderInfo info : list) {
				// ÿһ����������һ��������ǩ
				Element order = doc.createElement("order");
				// ��������Id��ǩ
				Element orderId = doc.createElement("orderId");
				// ���ö���ID��ǩ���ı�����
				orderId.setTextContent(info.getOrderId() + "");
				// ������ID��ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(orderId);
				// �������ű�ǩ
				Element tableId = doc.createElement("tableId");
				// �������ű�ǩ�ı�����
				tableId.setTextContent(info.getTableId() + "");
				// �����ű�ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(tableId);
				// �������Ա�ʺű�ǩ
				Element userAccount = doc.createElement("userAccount");
				// ���õ��Ա�ʺű�ǩ�ı�����
				userAccount.setTextContent(info.getUserAccount());
				// �����Ա�ʺű�ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(userAccount);
				// ����������ʼʱ���ǩ
				Element orderBeginDate = doc.createElement("orderBeginDate");
				// ��������ʱ���ʽ������
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// ���ö�����ʼʱ���ǩ�ı�����
				orderBeginDate.setTextContent(sdf.format(info
						.getOrderBeginDate()));
				// ��������ʼʱ���ǩ����Ϊ������ǩ�ӱ�ǩ
				order.appendChild(orderBeginDate);
				// ��������ǩ����Ϊ����ǩ�ӱ�ǩ
				root.appendChild(order);

			}
			// ������ǰҳ�����ı�ǩ
			Element pageNow = doc.createElement("page");
			// ���õ�ǰҳ������ǩ���ı�����
			pageNow.setTextContent(page + "");
			// ����ǰҳ������ǩ����Ϊ����ǩ���ӱ�ǩ
			root.appendChild(pageNow);
			// �������ҳ�����ı�ǩ
			Element maxPageElement = doc.createElement("maxPage");
			// �������ҳ������ǩ���ı�����
			maxPageElement.setTextContent(maxPage + "");
			// �����ҳ������ǩ����Ϊ����ǩ���ӱ�ǩ
			root.appendChild(maxPageElement);
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

}
