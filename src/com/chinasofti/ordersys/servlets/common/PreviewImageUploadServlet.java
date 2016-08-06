/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.util.web.serverpush.MessageProducer;
import com.chinasofti.util.web.upload.FormFile;
import com.chinasofti.util.web.upload.MultipartRequestParser;

/**
 * <p>
 * Title:PreviewImageUploadServlet
 * </p>
 * <p>
 * Description: �ϴ�ͼƬ�ļ������гɹ���ʵʱԤ����Servlet
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
public class PreviewImageUploadServlet extends HttpServlet {

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
		// ����Ĭ�ϵ�ͼƬ���·��
		String savepath = "/img/faces";
		// �ж�����������Ƿ�����Զ�����·��
		if (request.getParameter("path") != null) {
			// �����Զ���ͼƬ���·��
			savepath = request.getParameter("path");
		}
		// ��ȡ�����������
		MultipartRequestParser parser = new MultipartRequestParser();
		// �������ݲ���ȡ��Ӧvo
		PreviewImageInfo info = (PreviewImageInfo) parser.parse(request,
				"com.chinasofti.ordersys.servlets.common.PreviewImageInfo");
		// ��ȡ�ϴ��ļ�����
		FormFile img = info.getUploadFile();
		// ��ȡ���ͼ�������·��
		String path = getServletContext().getRealPath(savepath);
		// ���ϴ���ͼƬ��ŵ�����·����
		img.saveToFileSystem(request, path + "/" + img.getFileName());

		// ���������ݵ��ַ���������"utf-8",��ȷ���ú��ֹ��ȡ�������ݱ������
		request.setCharacterEncoding("utf-8");

		// �����������������ݵ���Ϣ������
		MessageProducer producer = new MessageProducer();

		// �򱾻Ự����һ���ļ��ϴ��ɹ���ʵʱ������Ϣ
		producer.sendMessage(request.getSession().getId().toString(),
				"upstate", img.getFileName());

	}

}
