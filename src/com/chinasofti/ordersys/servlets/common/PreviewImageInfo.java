/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.servlets.common;

import com.chinasofti.util.web.upload.FormFile;

/**
 * <p>
 * Title: PreviewImageInfo
 * </p>
 * <p>
 * Description: ����ͼ���ϴ���Ԥ����vo����
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
public class PreviewImageInfo {
	/**
	 * �ϴ���ͼƬ�ļ�
	 * */
	FormFile uploadFile;

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

}
