/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.upload;

/**
 * <p>
 * Title: IUploadInfo
 * </p>
 * <p>
 * Description: ���淢�����ȡ������Ϣʱʹ�õ��ڽ��ַ���
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
public interface IUploadInfo {

	/**
	 * ��ʾ��ǰ���û��ȡ��Ϊʵ�ʽ�����Ϣ��session��
	 * */
	public String UPLOAD_PROGRESS = "ICSS_UTIL_WEB_UPLOAD_PROGRESS";
	/**
	 * ��ʾ��ǰ״̬Ϊ�ϴ��е�sessionֵ
	 * */
	public String UPLOAD_STATE_UPLOADING = "ICSS_UTIL_WEB_UPLOAD_UPLOADING";
	/**
	 * ��ʾ��ǰ״̬Ϊ���ڽ����̻������ݱ��浽ʵ��Ŀ���ļ���sessionֵ
	 * */
	public String UPLOAD_STATE_SAVE = "ICSS_UTIL_WEB_UPLOAD_SAVE";
	/**
	 * ��ʾ��ǰ���û��ȡ��Ϊ״̬��Ϣ��session��
	 * */
	public String UPLOAD_STATE = "ICSS_UTIL_WEB_UPLOAD_STATE";

	/**
	 * ��ʾ��ǰ���û��ȡ��Ϊ�����ϴ����ļ�����Ϣ��sessionֵ
	 * */
	public String UPLOAD_UPLOADING_FILENAME = "ICSS_UTIL_WEB_UPLOAD_UPLOADING_FILENAME";
	/**
	 * ��ʾ��ǰ���û��ȡ��Ϊ���ڱ�����ļ�����Ϣ��sessionֵ
	 * */
	public String UPLOAD_SAVING_FILENAME = "ICSS_UTIL_WEB_UPLOAD_SAVING_FILENAME";

}
