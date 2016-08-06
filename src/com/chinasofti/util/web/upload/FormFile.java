/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Title: FormFile
 * </p>
 * <p>
 * Description: ������Servlet�ļ��ϴ���������ʱ�����ļ���Ϣ�Ĺ�����
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
public class FormFile {

	/**
	 * �ϴ��ļ����ļ���
	 * */
	private String fileName;
	/**
	 * �ϴ��ļ���MIME����
	 * */
	private String contextType;
	/**
	 * �������ʱ�ļ������ļ�ĩβ����2���ֽڵ�HTTP��β�����ַ�����ʵ�ʿ�����Ŀ��·��ʱ��Ҫ�����������ֽ�
	 * */
	private File tempFile;

	/**
	 * ��ȡ��ǰ�ϴ��ļ����ļ����ķ���
	 * 
	 * @return ���ظö����Ӧ���ϴ��ļ����ļ���
	 * */
	public String getFileName() {
		return fileName;
	}

	/**
	 * �����ϴ� �ļ��ļ����ķ���,�÷��������ϴ������ڲ�����
	 * 
	 * @param fileName
	 *            ��Http�������л�ȡ���ļ���
	 * */
	void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * ��ȡ��ǰ�ļ�MIME���͵ķ���
	 * 
	 * @return ���ظö����Ӧ�ļ���MIME���͵��ַ�������
	 * */
	public String getContextType() {
		return contextType;
	}

	/**
	 * �����ϴ� �ļ��ļ�MIME���͵ķ���,�÷��������ϴ������ڲ�����
	 * 
	 * @param contextType
	 *            ��Http�������л�ȡ���ļ�MIME����
	 * */
	void setContextType(String contextType) {
		this.contextType = contextType;
	}

	/**
	 * ��ȡ�ϴ��ļ����̻�����ʱ�ļ��ķ���
	 * 
	 * @return �����ϴ����ݱ����ڴ����ϵ���ʱ�ļ�
	 * */
	File getTempFile() {
		return tempFile;
	}

	/**
	 * �����ϴ� �ļ���ʱ����ķ���,�÷��������ϴ������ڲ�����
	 * 
	 * @param tempFile
	 *            ��Http�������л�ȡ���ļ�����
	 * */
	void setTempFile(File tempFile) {
		this.tempFile = tempFile;
	}

	/**
	 * ���ϴ����ļ����浽ʵ��·���ķ���
	 * 
	 * @param request
	 *            ��ǰ���������������session�����״̬��Ϣ,�������Ҫ״̬��Ϣ������ֱ�Ӵ���null
	 * @param path
	 *            �ļ����մ洢��ʵ��·��(�����ļ���)
	 * */
	public void saveToFileSystem(HttpServletRequest request, String path) {
		try {
			// �ж�request�����Ƿ�Ϊ��
			if (request != null) {
				// ��ȡsession�ػ�����
				HttpSession session = request.getSession(true);
				// ��session�е�״̬��Ϣ�趨Ϊ�����ڱ��桱״̬
				session.setAttribute(IUploadInfo.UPLOAD_STATE,
						IUploadInfo.UPLOAD_STATE_SAVE);
				// ��session�����ڱ�����ļ�����������Ϊ��ǰ�ļ����ļ���
				session.setAttribute(IUploadInfo.UPLOAD_SAVING_FILENAME,
						fileName);
			}
			// ���ø�����·������Ŀ���ļ�����
			File distFile = new File(path);
			// ��ȡ��ʱ�ļ���������
			FileInputStream fis = new FileInputStream(tempFile);
			// ��ȡĿ���ļ��������
			FileOutputStream fos = new FileOutputStream(distFile);
			// ��ȡ�ļ�����ܵ�
			FileChannel inChannel = fis.getChannel();
			// ��ȡ�ļ�����ܵ�
			FileChannel outChannel = fos.getChannel();
			// ������ܵ��е����ݣ�ԭ��ʱ�ļ��е����ݣ����䵽����ܵ���Ŀ���ļ�����,������ʱ�ļ��е�������ĩβΪ2���ֽڵ�HTTP�Ľ��������ַ�����˿���ʱҪ����������ַ�����
			inChannel.transferTo(0, inChannel.size() - 2, outChannel);
			// �ر�����ܵ�
			inChannel.close();
			// �ر�����ܵ�
			outChannel.close();
			// �ر���ʱ�ļ���������
			fis.close();
			// �ر�Ŀ���ļ��������
			fos.close();
			// ɾ��ԭ����ʱ�ļ�
			tempFile.delete();

		} catch (Exception ex) {
			// ��������쳣������쳣��Ϣ
			ex.printStackTrace();
		}
	}

}
