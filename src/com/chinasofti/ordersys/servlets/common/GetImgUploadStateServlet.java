/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasofti.util.web.serverpush.BaseGetPushMsgServlet;
import com.chinasofti.util.web.serverpush.Message;
import com.chinasofti.util.web.serverpush.MessageHandler;
import com.chinasofti.util.web.serverpush.ServerPushKey;

/**
 * <p>
 * Title: GetImgUploadStateServlet
 * </p>
 * <p>
 * Description: �ϴ���ҪԤ��ͼ��ʱ��������ͻ��������ϴ������Ϣ��Servlet
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
public class GetImgUploadStateServlet extends BaseGetPushMsgServlet {
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
	public MessageHandler getHandler(final HttpServletRequest request,
			final HttpServletResponse response) {
		// TODO Auto-generated method stub
		MessageHandler handler = new MessageHandler() {
			// ʵ�ֵ���ȡ��Ϣ�����Ϣ����ʵ�ʴ���Ļص�������
			// �ص�������messageQueue���������˵�ǰϵͳʹ�õ���Ϣ�ȴ�����
			// �ص�������key���������˵�ǰ��ȡ������Ϣ���͵�Ŀ��sessionid����Ϣ����
			// �ص�������msg����������ʵ�ʵ���Ϣ����
			@Override
			public void handle(Hashtable<ServerPushKey, Message> messageQueue,
					ServerPushKey key, Message msg) {

				try {
					// ��ȡ��Կͻ��˵��ַ������
					PrintWriter pw = response.getWriter();
					// ����Ϣ�ַ���ֱ�ӷ��͸��ͻ��������
					pw.print(msg.getMsg());
				} catch (Exception ex) {
					// ��������쳣������쳣��Ϣ
					ex.printStackTrace();
				}
			}
		};
		// ���ش����õ���Ϣ������
		return handler;
	}

}
