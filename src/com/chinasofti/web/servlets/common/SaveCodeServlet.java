/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.web.servlets.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasofti.web.common.service.SaveCodeService;

/**
 * <p>
 * Title: SaveCodeServlet
 * </p>
 * <p>
 * Description: �����֤���Servlet
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
public class SaveCodeServlet extends HttpServlet {

	/**
	 * ��֤���ַ����ڻỰ�е�������
	 * */
	public static final String CODE_SESSION_ATTR_NAME = "web_app_savecode_value";

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

		// ͼƬ����Ҫ�������Ӧͷ
		response.setHeader("Pragma", "No-cache");
		// ͼƬ����Ҫ�������Ӧͷ
		response.setHeader("Cache-Control", "no-cache");
		// ͼƬ����Ҫ�������Ӧͷ
		response.setDateHeader("Expires", 0);
		// ������ӦMIME����ΪJPEGͼƬ
		response.setContentType("image/jpeg");
		// ������֤��������
		SaveCodeService codeService = new SaveCodeService(
				"abcdefghijklmnopqrstuvwxyz123456789".toUpperCase()
						.toCharArray(), 100, 25, 6);
		// ������֤��ͼƬ
		codeService.createSaveCodeImage();
		// ��ȡ��֤��ͼƬ
		BufferedImage img = codeService.getImage();
		// ��ȡ��֤���ַ���
		String codeString = codeService.getCodeString();
		// ��ȡ�Ự����
		HttpSession session = request.getSession();
		// ����֤���ַ�������Ự
		session.setAttribute(CODE_SESSION_ATTR_NAME, codeString);
		try {
			// ������ͼƬ����Ϊ����ͼƬ���ݲ�����Ӧ�������������ͻ���
			ImageIO.write(img, "JPEG", response.getOutputStream());
			// �����쳣
		} catch (Exception e) {

			// TODO: handle exception
		}
	}

}
