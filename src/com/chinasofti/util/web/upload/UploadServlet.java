/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.upload;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Title: UploadServlet
 * </p>
 * <p>
 * Description: �����ļ��ϴ��ķ�������servlet
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
public class UploadServlet extends HttpServlet {

	/**
	 * ����Get����ķ�������ServletҪ��ͬ�����󷵻���ͬ�Ľ������˵���doPost����ͳһ����
	 * 
	 * @param request
	 *            http�������
	 * @param response
	 *            http��Ӧ����
	 * @throws ServletException
	 *             �����servlet���ʷ�����쳣�����׳��쳣��Ϣ
	 * @throws IOException
	 *             ���������ʳ������׳��쳣��Ϣ
	 * @see #doPost(HttpServletRequest, HttpServletResponse)
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ����doPost��������
		doPost(request, response);
	}

	/**
	 * ����Post����ķ�������������£�������ļ��ϴ�����Ӧ�÷���Post����
	 * 
	 * @param request
	 *            http�������
	 * @param response
	 *            http��Ӧ����
	 * @throws ServletException
	 *             �����servlet���ʷ�����쳣�����׳��쳣��Ϣ
	 * @throws IOException
	 *             ���������ʳ������׳��쳣��Ϣ
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ����Http�����������
		MultipartRequestParser parser = new MultipartRequestParser();
		// ����Http��������������������Ľ������com.chinasofti.util.web.upload.SubmitInformation��Ķ�����
		SubmitInformation info = (SubmitInformation) parser.parse(request,
				"com.chinasofti.util.web.upload.SubmitInformation");
		// ���info�������ownerName����ֵ������֤����������߹���
		System.out.println(info.getOwnerName());
		// ���info�������ownerAge����ֵ������֤����������߹���
		System.out.println(info.getOwnerAge());
		// ��info�е�uploadFile������ϴ��ļ�ʵ�ʱ��浽Ŀ��·����
		info.getUploadFile().saveToFileSystem(
				request,
				getServletContext().getRealPath("/upload") + "/"
						+ info.getUploadFile().getFileName());

	}

}
