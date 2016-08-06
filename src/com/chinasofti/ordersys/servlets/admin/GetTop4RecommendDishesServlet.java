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
 * Title: GetTop4RecommendDishesServlet
 * </p>
 * <p>
 * Description: ��ȡ����Ա����������Ҫ��ͷ4���Ƽ���Ʒ��Ϣ��Servlet
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
public class GetTop4RecommendDishesServlet extends HttpServlet {

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
		// ������Ʒ����������
		DishesService service = new DishesService();
		// ��ȡͷ4���Ƽ���Ʒ��Ϣ�б�
		ArrayList<DishesInfo> list = service.getTop4RecommendDishes();
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
				// ����Ʒ��ǩ����Ϊ����ǩ���ӱ�ǩ
				root.appendChild(dishes);

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

}
