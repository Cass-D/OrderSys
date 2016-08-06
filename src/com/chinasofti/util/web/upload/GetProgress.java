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
 * Title: GetProgress
 * </p>
 * <p>
 * Description: ����Ajax��ȡ�ļ��ϴ�������Ϣ�ķ�������Servlet
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
public class GetProgress extends HttpServlet {

	/**
	 * ����Ajax������ϢGet HTTP����ķ���
	 * 
	 * @param request
	 *            Http�������
	 * @param response
	 *            Http��Ӧ����
	 * @throws ServletException
	 *             �����servlet���ʷ�����쳣�����׳��쳣��Ϣ
	 * @throws IOException
	 *             ���������ʳ������׳��쳣��Ϣ
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ���ñ������󷵻���Ӧ��MIME����
		response.setContentType("text/html");
		// ��ȡ��Կͻ��˵��ַ������
		PrintWriter out = response.getWriter();
		// ��ȡ��ǰ�ػ���Ľ�����Ϣ�����������������ͻ���
		out.print(MultipartRequestParser.getUploadProgress(request));

	}

}
