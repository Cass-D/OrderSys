/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.service;

import javax.servlet.http.HttpServletRequest;

import com.chinasofti.web.common.httpequest.HttpRequestContext;

/**
 * <p>
 * Title: DomainProtectedService
 * </p>
 * <p>
 * Description: ��ֹ��������ֹ��վ�ύ���ݵķ������
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
public class DomainProtectedService {

	/**
	 * �ж��Ƿ�������Ƿ���վ�ύ���ݵķ���
	 * 
	 * @return �ж������false��ʾ��վ�Ϸ�����true��ʾ��վ����
	 * */
	public boolean isFromSameDomain() {
		// ��ȡ���ε��������
		HttpServletRequest request = HttpRequestContext.getRequest();
		// ��ȡ��վ��context root
		String path = request.getContextPath();
		// ��ȡ��վ������context root��������Ϣ
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		// ��ȡ��һ��ҳ��ĵ�ַ
		String fromUrl = request.getHeader("referer");
		// �ж��Ƿ���վ���󲢷��ؽ��
		return fromUrl != null && fromUrl.startsWith(basePath) ? true : false;

	}

}
