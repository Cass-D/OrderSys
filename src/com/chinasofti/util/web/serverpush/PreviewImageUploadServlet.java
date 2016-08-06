package com.chinasofti.util.web.serverpush;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.util.web.upload.FormFile;
import com.chinasofti.util.web.upload.MultipartRequestParser;

public class PreviewImageUploadServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PreviewImageUploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		MultipartRequestParser parser = new MultipartRequestParser();
		PreviewImageInfo info = (PreviewImageInfo) parser.parse(request,
				"com.chinasofti.util.web.serverpush.PreviewImageInfo");
		FormFile img = info.getUploadFile();
		String path = getServletContext().getRealPath("/images");
		img.saveToFileSystem(request, path + "/" + img.getFileName());

		// ���������ݵ��ַ���������"utf-8",��ȷ���ú��ֹ��ȡ�������ݱ������
		request.setCharacterEncoding("utf-8");
		
		// �����������������ݵ���Ϣ������
		MessageProducer producer = new MessageProducer();
		// �������еĿͻ���

		// ��ÿһ���ͻ�������һ��������Ϣ
		producer.sendMessage(request.getSession().getId().toString(),
				"upstate", img.getFileName());

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
