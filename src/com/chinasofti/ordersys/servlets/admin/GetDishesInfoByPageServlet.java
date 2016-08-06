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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.chinasofti.ordersys.service.admin.DishesService;
import com.chinasofti.ordersys.vo.DishesInfo;

/**
 * <p>
 * Title: GetDishesInfoByPageServlet
 * </p>
 * <p>
 * Description: �Է�ҳ��ʽ��ȡ��Ʒ��Ϣ��Servlet
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
public class GetDishesInfoByPageServlet extends HttpServlet {

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
		// ��ȡϣ����ʾ��ҳ����
		int page = Integer.parseInt(request.getParameter("page"));
		// ������Ʒ����������
		DishesService service = new DishesService();
		// ��ȡ���ҳ����
		int maxPage = service.getMaxPage(8);
		// �Ե�ǰ��ҳ�������о������С��1����ֱ����ʾ��һҳ������
		page = page < 1 ? 1 : page;
		// �Ե�ǰ��ҳ�������о�������������ҳ�룬��ֱ����ʾ���һҳ������
		page = page > maxPage ? maxPage : page;
		// ���з�ҳ���ݲ�ѯ
		ArrayList<DishesInfo> list = service.getDishesInfoByPage(page, 8);
		// ���Խ�����ṹ��Ϊxml�ĵ�
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("disheses");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ��������������еĲ�Ʒ��Ϣ
			for (DishesInfo info : list) {
				// ÿһ����Ʒ����һ��dishes��ǩ�ڵ�
				Element dishes = doc.createElement("dishes");
				// ������ƷID��ǩ
				Element dishesId = doc.createElement("dishesId");
				// ���ò�ƷID��ǩ���ı�����
				dishesId.setTextContent(info.getDishesId() + "");
				// ����ƷID��ǩ����Ϊ��Ʒ��ǩ�ӱ�ǩ
				dishes.appendChild(dishesId);
				// ������Ʒ����ǩ
				Element dishesName = doc.createElement("dishesName");
				// ���ò�Ʒ����ǩ���ı�����
				dishesName.setTextContent(info.getDishesName());
				// ����Ʒ����ǩ����Ϊ��Ʒ��ǩ���ӱ�ǩ
				dishes.appendChild(dishesName);
				// ������Ʒ������ǩ
				Element dishesDiscript = doc.createElement("dishesDiscript");
				// ���ò�Ʒ������ǩ�ı�����
				dishesDiscript.setTextContent(info.getDishesDiscript());
				// ����Ʒ������ǩ����Ϊ��Ʒ��ǩ�ӱ�ǩ
				dishes.appendChild(dishesDiscript);
				// ������ƷͼƬ��ǩ
				Element dishesImg = doc.createElement("dishesImg");
				// ���ò�ƷͼƬ��ǩ���ı�����
				dishesImg.setTextContent(info.getDishesImg());
				// ����ƷͼƬ��ǩ����Ϊ��Ʒ��ǩ���ӱ�ǩ
				dishes.appendChild(dishesImg);
				// ������Ʒ��ϸ�ı���ǩ
				Element dishesTxt = doc.createElement("dishesTxt");
				// ��ȡ��Ʒ��ϸ��������
				String txt = info.getDishesTxt();
				// ���ո��滻Ϊ����ָ���
				txt = txt.replaceAll(" ", "ordersysspace");
				// ��\r�滻Ϊ���ַ���
				txt = txt.replaceAll("\r", "");
				// �������滻Ϊ����ָ���
				txt = txt.replaceAll("\n", "ordersysbreak");
				// ��˫�����滻Ϊת���ַ�
				txt = txt.replaceAll("\"", "\\\\\"");
				// ���������滻Ϊת���ַ�
				txt = txt.replaceAll("\'", "\\\\\'");
				// ���ò�Ʒ��ϸ�ı���ǩ���ı�����
				dishesTxt.setTextContent(txt);
				// ����Ʒ��ϸ�ı���ǩ����Ϊ��Ʒ��ǩ���ӱ�ǩ
				dishes.appendChild(dishesTxt);
				// �����Ƿ��Ƽ��ӱ�ǩ
				Element recommend = doc.createElement("recommend");
				// �����Ƿ��Ƽ���Ʒ��ǩ�ı�����
				recommend.setTextContent(info.getRecommend() + "");
				// ���Ƿ��Ƽ���Ʒ��ǩ����Ϊ��Ʒ��ǩ���ӱ�ǩ
				dishes.appendChild(recommend);
				// ������Ʒ�۸��ǩ
				Element dishesPrice = doc.createElement("dishesPrice");
				// ���ò�Ʒ�۸��ǩ�ı�����
				dishesPrice.setTextContent(info.getDishesPrice() + "");
				// ����Ʒ�۸��ǩ����Ϊ��Ʒ��ǩ�ӱ�ǩ
				dishes.appendChild(dishesPrice);
				// ����Ʒ��ǩ����Ϊ����ǩ���ӱ�ǩ
				root.appendChild(dishes);

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
