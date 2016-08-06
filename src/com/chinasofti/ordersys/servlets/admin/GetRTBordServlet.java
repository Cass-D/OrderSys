/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.admin;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasofti.util.web.serverpush.BaseGetPushMsgServlet;
import com.chinasofti.util.web.serverpush.Message;
import com.chinasofti.util.web.serverpush.MessageHandler;
import com.chinasofti.util.web.serverpush.ServerPushKey;

/**
 * <p>
 * Title: GetRTBordServlet
 * </p>
 * <p>
 * Description: ��ȡʵʱ������Ϣ��Servlet
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
public class GetRTBordServlet extends BaseGetPushMsgServlet {

	/**
	 * ��ȡʵʩ������Ϣ�ĵȴ��б�
	 * */
	public static ArrayList<String> bords = new ArrayList<String>();

	/**
	 * ��ȡʵʱ��Ϣ�������Ļص�
	 * 
	 * @param request
	 *            �������
	 * @param response
	 *            ��Ӧ����
	 * @return ��Servletʹ�õ�ʵʱ��Ϣ������
	 * */
	@Override
	public MessageHandler getHandler(HttpServletRequest request,
			HttpServletResponse response) {
		// ���������ַ���
		response.setCharacterEncoding("utf-8");
		// TODO Auto-generated method stub
		// ���Դ���ʵʱ��Ϣ
		try {
			// ��ȡ��Կͻ��˵��ı������
			final PrintWriter out = response.getWriter();
			// ������Ϣ������
			MessageHandler handler = new MessageHandler() {
				// ʵʱ��Ϣ����ص�����
				@Override
				public void handle(
						Hashtable<ServerPushKey, Message> messageQueue,
						ServerPushKey key, Message msg) {
					// ����Ϣ���ı�����ֱ�ӷ��͸��ͻ���
					out.print(msg.getMsg());
					// TODO Auto-generated method stub
				}
			};
			// ���ش����õ���Ϣ������
			return handler;
			// ���񴴽���Ϣ������ʱ�������쳣
		} catch (Exception ex) {
			// ����쳣��Ϣ
			ex.printStackTrace();
			//���ؿյ���Ϣ������
			return null;
		}
	}

	/**
	 * ��ʼ��ʵʱ��Ϣ��ȡ����ķ���
	 * 
	 * @param request
	 *            ������Ϣ
	 * @param response
	 *            ��Ӧ����
	 * @param session
	 *            �Ự���ٶ���
	 * */
	@Override
	public void initService(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		// TODO Auto-generated method stub
		// ����ǰ�Ự���뵽ʵʱ��Ϣ�ȴ��б�
		bords.add(session.getId());

	}

}
