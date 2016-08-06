/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.web.upload;

/**
 * <p>
 * Title: SubmitInformation
 * </p>
 * <p>
 * Description: ���ڲ����ļ��ϴ���JavaBean��
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
public class SubmitInformation {
	/**
	 * ���ֱ���ownerName��Ӧ������
	 * */
	private String ownerName;
	/**
	 * ���ֱ���ownerAge��Ӧ������
	 * */
	private int ownerAge;
	/**
	 * �ļ�����uploadFile��Ӧ������
	 * */
	private FormFile uploadFile;

	/**
	 * ����ownerName��getter����
	 * 
	 * @return ����ownerName���Ե�ֵ
	 * */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * ����ownerName��setter����
	 * 
	 * @param ownerName
	 *            ���Ե���ֵ
	 * */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * ����ownerAgeName��getter����
	 * 
	 * @return ����ownerAge���Ե�ֵ
	 * */
	public int getOwnerAge() {
		return ownerAge;
	}

	/**
	 * ����ownerAge��setter����
	 * 
	 * @param ownerAge
	 *            ���Ե���ֵ
	 * */
	public void setOwnerAge(int ownerAge) {
		this.ownerAge = ownerAge;
	}

	/**
	 * ����uploadFile��getter����
	 * 
	 * @return ����uploadFile���Ե�ֵ
	 * */
	public FormFile getUploadFile() {
		return uploadFile;
	}

	/**
	 * ����uploadFile��setter����
	 * 
	 * @param uploadFile
	 *            ���Ե���ֵ
	 * */
	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

}
