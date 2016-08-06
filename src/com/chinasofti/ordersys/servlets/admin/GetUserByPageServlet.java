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

import com.chinasofti.ordersys.service.admin.UserService;
import com.chinasofti.ordersys.vo.UserInfo;

/**
 * <p>
 * Title: GetUserByPageServlet
 * </p>
 * <p>
 * Description: �Է�ҳ�ķ�ʽ��ȡ�û���Ϣ��Servlet
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
public class GetUserByPageServlet extends HttpServlet {

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
		// �����û�����������
		UserService service = new UserService();
		// ��ȡ���ҳ����
		int maxPage = service.getMaxPage(10);
		// �Ե�ǰ��ҳ�������о������С��1����ֱ����ʾ��һҳ������
		page = page < 1 ? 1 : page;
		// �Ե�ǰ��ҳ�������о�������������ҳ�룬��ֱ����ʾ���һҳ������
		page = page > maxPage ? maxPage : page;
		// ���з�ҳ���ݲ�ѯ
		ArrayList<UserInfo> list = service.getByPage(page, 10);
		// ���Խ�����ṹ��Ϊxml�ĵ�
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("users");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ��������������е��û���Ϣ
			for (UserInfo info : list) {
				// ÿһ���û�����һ���û���ǩ
				Element user = doc.createElement("user");
				// �����û�ID��ǩ
				Element userId = doc.createElement("userId");
				// �����û�ID��ǩ�ı�����
				userId.setTextContent(info.getUserId() + "");
				// ���û�ID��ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(userId);
				// �����û�����ǩ
				Element userAccount = doc.createElement("userAccount");
				// �����û�����ǩ�ı�����
				userAccount.setTextContent(info.getUserAccount());
				// ���û�����ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(userAccount);
				// ������ɫid��ǩ
				Element roleId = doc.createElement("roleId");
				// ���ý�ɫid��ǩ�ı�����
				roleId.setTextContent(info.getRoleId() + "");
				// ����ɫid��ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(roleId);
				// ������ɫ����ǩ
				Element roleName = doc.createElement("roleName");
				// ���ý�ɫ����ǩ�ı�����
				roleName.setTextContent(info.getRoleName());
				// ����ɫ����ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(roleName);
				// ������ɫ������Ϣ��ǩ
				Element locked = doc.createElement("locked");
				// ���ý�ɫ������Ϣ��ǩ�ı�����
				locked.setTextContent(info.getLocked() + "");
				// ����ɫ������Ϣ��ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(locked);
				// ������ɫͷ���ǩ
				Element faceimg = doc.createElement("faceimg");
				// ���ý�ɫͷ���ǩ�ı�����
				faceimg.setTextContent(info.getFaceimg() + "");
				// ����ͷ���ǩΪ�û���ǩ�ӱ�ǩ
				user.appendChild(faceimg);
				// �����û���ǩΪ����ǩ�ӱ�ǩ
				root.appendChild(user);
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
		// for (UserInfo info : list) {
		// System.out.println(info.getUserId() + "\t" + info.getUserAccount());
		// }
	}

}
