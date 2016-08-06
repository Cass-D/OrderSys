/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.waiters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.chinasofti.ordersys.servlets.admin.GetRTPayOrderServlet;
import com.chinasofti.ordersys.vo.OrderInfo;
import com.chinasofti.util.web.serverpush.MessageProducer;

/**
 * <p>
 * Title: RequestToPayServlet
 * </p>
 * <p>
 * Description: ���������Ԫ��������Ϣ��Servlet
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
public class RequestToPayServlet extends HttpServlet {

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
	 * ����Post��ʽ����Servletʱ��service�����ص���Ϊ�˿����Ժ��ƽ̨�����ԣ� ��Servlet����xml���
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ��Ҫ�򵥵Ķ���ID
		Integer orderId = new Integer(request.getParameter("orderId"));
		// ������������������
		OrderService service = new OrderService();
		// �޸����ݿ��еĶ���״̬��Ϣ
		service.requestPay(orderId);
		// ��ȡ��������
		OrderInfo info = service.getOrderById(orderId);
		// ���Խ�����ṹ��Ϊxml�ĵ�
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("order");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ��������ID�ڵ�
			Element oid = doc.createElement("orderId");
			// ���ö���ID�ڵ��ı�����
			oid.setTextContent(info.getOrderId() + "");
			// ������ID�ڵ�����Ϊ���ڵ��ӽڵ�
			root.appendChild(oid);
			// �������Ա�û�����ǩ
			Element userAccount = doc.createElement("userAccount");
			// ���õ��Ա�û�����ǩ�ı�����
			userAccount.setTextContent(info.getUserAccount());
			// �����Ա�û�����ǩ����Ϊ���ڵ��ӽڵ�
			root.appendChild(userAccount);
			// �������ű�ǩ
			Element tid = doc.createElement("tableId");
			// �������ű�ǩ�ı�����
			tid.setTextContent(info.getTableId() + "");
			// �����ű�ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(tid);
			// ����������ʼʱ���ǩ
			Element orderBeginDate = doc.createElement("orderBeginDate");
			// ��������ʱ���ʽ������
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// ���ö�����ʼʱ���ǩ�ı�����
			orderBeginDate.setTextContent(sdf.format(info.getOrderBeginDate()));
			// ��������ʼʱ���ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(orderBeginDate);
			// �����ᵥʱ���ǩ
			Element orderEndDate = doc.createElement("orderEndDate");
			// ���ýᵥʱ���ǩ�ı�����
			orderEndDate.setTextContent(sdf.format(info.getOrderEndDate()));
			// ���ڵ�ʱ���ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(orderEndDate);
			// ��ȡ�����ܽ��
			double sum = service.getSumPriceByOrderId(orderId);
			// �����ܽ���ǩ
			Element sumPrice = doc.createElement("sumPrice");
			// �����ܽ���ǩ�ı�����
			sumPrice.setTextContent(sum + "");
			// ���ܽ���ǩ����Ϊ����ǩ�ӱ�ǩ
			root.appendChild(sumPrice);
			// �����ַ��������
			StringWriter writer = new StringWriter();
			// ������ʽ�������
			PrintWriter pwriter = new PrintWriter(writer);
			// ��������DOM��ת��ΪXML�ĵ��ṹ�ַ���������ַ���
			TransformerFactory.newInstance().newTransformer()
					.transform(new DOMSource(doc), new StreamResult(pwriter));
			// ��ȡXML�ַ���
			String msg = writer.toString();
			// ��ʽ��������ر�
			pwriter.close();
			// �ַ���������ر�
			writer.close();
			// ��ȡ��������Ա�ȴ��б�
			ArrayList<String> list = GetRTPayOrderServlet.pays;
			// ������Ϣ������
			MessageProducer producer = new MessageProducer();
			// �������еĵȴ�����Ա
			for (int i = list.size() - 1; i >= 0; i--) {
				// ��ȡ����ԱsessionID
				String id = list.get(i);
				// Ϊ����Ա���Ͷ�������Ϣ
				producer.sendMessage(id, "rtpay", msg);
				// ��������Ա�ӵȴ��û��б���ɾ��
				list.remove(id);
			}

			// response.getWriter().write(msg);
			// �����쳣��Ϣ
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();
		}

	}

}
