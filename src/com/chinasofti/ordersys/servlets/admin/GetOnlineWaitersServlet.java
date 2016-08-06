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

import com.chinasofti.ordersys.listeners.OrderSysListener;
import com.chinasofti.ordersys.vo.UserInfo;

/**
 * <p>
 * Title: GetOnlineWaitersServlet
 * </p>
 * <p>
 * Description: ��ȡ��ǰ���߲�������Ա�б��Servlet
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
public class GetOnlineWaitersServlet extends HttpServlet {

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
		// �Ӽ������л�ȡ���ߵķ���Ա�б�
		ArrayList<UserInfo> waiters = OrderSysListener.getOnlineWaiters();
		// ��ȡ���еĻỰ��Ŀ
		int sessions = OrderSysListener.onlineSessions;
		// ���Խ����߷���Ա�б�ṹ��Ϊxml�ĵ�
		try {
			// ����XML DOM��
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			// ����XML���ڵ�
			Element root = doc.createElement("users");
			// �����ڵ����DOM��
			doc.appendChild(root);
			// ѭ��������������еĹ���Ա��Ϣ
			for (UserInfo info : waiters) {
				// ÿһ��Ա������һ���û���ǩ�ڵ�
				Element user = doc.createElement("user");
				// �����û�id�ڵ��ǩ
				Element userId = doc.createElement("userId");
				// �����û�id��ǩ�ı�����
				userId.setTextContent(info.getUserId() + "");
				// ���û�id��ǩ����Ϊ�û���ǩ�ӱ�ǩ
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
				// ����ɫid��ǩ����Ϊ�û���ǩ���ӱ�ǩ
				user.appendChild(roleId);
				// ������ɫ����ǩ
				Element roleName = doc.createElement("roleName");
				// ���ý�ɫ����ǩ�ı�����
				roleName.setTextContent(info.getRoleName());
				// ����ɫ����ǩ����Ϊ�û���ǩ���ӱ�ǩ
				user.appendChild(roleName);
				// �����û�����״̬��ǩ
				Element locked = doc.createElement("locked");
				// �����û�����״̬��ǩ�ı�����
				locked.setTextContent(info.getLocked() + "");
				// ���û�����״̬��ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(locked);
				// ������ɫͷ���ǩ
				Element faceimg = doc.createElement("faceimg");
				// ���ý�ɫͷ���ǩ�ı�����
				faceimg.setTextContent(info.getFaceimg() + "");
				// ����ɫͷ���ǩ����Ϊ�û���ǩ�ӱ�ǩ
				user.appendChild(faceimg);
				// ����ɫ��ǩ����Ϊ����ǩ�ӽڵ�
				root.appendChild(user);

			}
			// �����Ự����ǩ
			Element sessionNum = doc.createElement("sessionNum");
			// ���ûỰ����ǩ�ı�����
			sessionNum.setTextContent(sessions + "");
			// ���Ự����ǩ����Ϊ����ǩ���ӱ�ǩ
			root.appendChild(sessionNum);
			// ��������Ա����ǩ
			Element waitersNum = doc.createElement("waitersNum");
			// ���÷���Ա����ǩ�ı�����
			waitersNum.setTextContent(waiters.size() + "");
			// ������Ա����ǩ����Ϊ����ǩ���ӱ�ǩ
			root.appendChild(waitersNum);
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
