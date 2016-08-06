/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.waiters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.service.waiters.OrderService;
import com.chinasofti.ordersys.servlets.kitchen.GetRTOrderServlet;
import com.chinasofti.ordersys.vo.Cart;
import com.chinasofti.ordersys.vo.UserInfo;
import com.chinasofti.util.web.serverpush.MessageProducer;

/**
 * <p>
 * Title:CommitCartServlet
 * </p>
 * <p>
 * Description: �ύ��͹��ﳵ������Ʒ��Ϣ���͸������Ա��Servlet
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
public class CommitCartServlet extends HttpServlet {

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
		// ������Ӧ����
		response.setCharacterEncoding("utf-8");
		// ���÷��ص�MIME����Ϊxml
		response.setContentType("text/xml");
		// ������Ʒ����������
		DishesService service = new DishesService();
		// ��ȡ�Ự����
		HttpSession session = request.getSession();
		// �������ﳵ����
		Cart cart = new Cart();
		// �������ű���
		Integer tableId = new Integer(1);
		// ���Session�б�����������Ϣ
		if (session.getAttribute("TABLE_ID") != null) {
			// ֱ�ӻ�ȡ������Ϣ
			tableId = (Integer) session.getAttribute("TABLE_ID");

		}
		// ����Ự�д��ڹ��ﳵ��Ϣ
		if (session.getAttribute("CART") != null) {
			// ֱ�ӻ�ȡ�Ự�еĹ��ﳵ����
			cart = (Cart) session.getAttribute("CART");
		}
		// �����ͷ���ԱID����
		int waiterId = 1;
		// ���Session�д��ڵ�¼��Ϣ
		if (session.getAttribute("USER_INFO") != null) {
			// ��ȡ���û����û�ID
			waiterId = ((UserInfo) session.getAttribute("USER_INFO"))
					.getUserId();
		}
		// ������������������
		OrderService oservice = new OrderService();
		// ���������������ݿⲢ��ȡ������Ӷ���������
		Object key = oservice.addOrder(waiterId, tableId);
		// System.out.println(key);
		// ���Խ�����ṹ��Ϊxml�ĵ�
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("disheses");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ��������������еĶ�����Ʒ����
			for (Cart.CartUnit unit : cart.getUnits()) {
				// ��������Ʒ����ӳ����Ϣ�������ݿ�
				oservice.addOrderDishesMap(unit, key);
				// ÿһ����Ʒ����һ��dishes��ǩ�ڵ�
				Element dishes = doc.createElement("dishes");
				// �������ű�ǩ
				Element tid = doc.createElement("tableId");
				// �������ű�ǩ�ı�����
				tid.setTextContent(tableId.intValue() + "");
				// �����ű�ǩ����Ϊ��Ʒ��ǩ�ӱ�ǩ
				dishes.appendChild(tid);
				// ������Ʒ����ǩ
				Element dishesName = doc.createElement("dishesName");
				// ��ȡ��Ʒ����
				String dname = service.getDishesById(
						new Integer(unit.getDishesId())).getDishesName();
				// ���ò�Ʒ����ǩ�ı�����
				dishesName.setTextContent(dname);
				// ����Ʒ���Ʊ�ǩ����Ϊ��Ʒ��ǩ�ӱ�ǩ
				dishes.appendChild(dishesName);
				// ������Ʒ������ǩ
				Element num = doc.createElement("num");
				// ����������ǩ�ı�����
				num.setTextContent(unit.getNum() + "");
				// ��������ǩ����Ϊ��Ʒ��ǩ�ӱ�ǩ
				dishes.appendChild(num);
				// ����Ʒ��ǩ����Ϊ����ǩ�ӱ�ǩ
				root.appendChild(dishes);

			}
			// �����ַ������
			StringWriter writer = new StringWriter();
			// ������ʽ�������
			PrintWriter pwriter = new PrintWriter(writer);
			// ��������DOM��ת��ΪXML�ĵ��ṹ�ַ���������ַ��������
			TransformerFactory.newInstance().newTransformer()
					.transform(new DOMSource(doc), new StreamResult(pwriter));
			// ��ȡXML�ַ���
			String msg = writer.toString();
			// �رո�ʽ�������
			pwriter.close();
			// �ر��ַ��������
			writer.close();
			// ��ȡ����ȴ��б�
			ArrayList<String> list = GetRTOrderServlet.kitchens;
			// ������Ϣ������
			MessageProducer producer = new MessageProducer();
			// ����ÿһ������ȴ��û�
			for (int i = list.size() - 1; i >= 0; i--) {
				// ��ȡ�����ȴ��û���sessionID
				String id = list.get(i);
				// Ϊ���û����ɵ�˶�����Ϣ
				producer.sendMessage(id, "rtorder", msg);
				// �ڵȴ��б���ɾ�����û�
				list.remove(id);
			}
			// �����յĹ��ﳵ����
			cart = new Cart();
			// ��ջỰ���ﳵ
			session.setAttribute("CART", cart);

			// response.getWriter().write(writer.toString());
			// �����쳣
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();
		}
	}

}
