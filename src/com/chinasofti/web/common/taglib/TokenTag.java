/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.web.common.taglib;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.chinasofti.web.common.httpequest.HttpRequestContext;

/**
 * <p>
 * Title: TokenTag
 * </p>
 * <p>
 * Description: ��ֹ���ظ��ύ�ı�ǩ
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
public class TokenTag extends TagSupport {

	/**
	 * ����������SESSION�е�������
	 * */
	public static final String TOKEN_SESSION_ATTR_NAME = "SUBMIT_TOKEN_ATTR_NAME_SESSION";
	/**
	 * ����������REQUEST�е�������
	 * */
	public static final String TOKEN_REQUEST_ATTR_NAME = "SUBMIT_TOKEN_ATTR_NAME_REQUEST";

	/**
	 * �ж���ǰ�������Ƿ�����Ϸ�����ֵ�ķ���
	 * 
	 * @return true-�����а����Ϸ�����ֵ,false-�����в������Ϸ�����ֵ
	 * */
	public static boolean isTokenValid() {
		// ��ȡ��ǰ�����е�����ֵ
		String requestToken = HttpRequestContext.getRequest().getParameter(
				TOKEN_REQUEST_ATTR_NAME);
		// ��ȡ�Ự�е�����ֵ
		Object sessionToken = HttpRequestContext.getRequest().getSession()
				.getAttribute(TOKEN_SESSION_ATTR_NAME);

		// System.out.println(requestToken+"            "+sessionToken);
		// �ж������Ƿ�Ϸ�
		return sessionToken != null
				&& sessionToken.toString().equals(requestToken);
	}

	/**
	 * �ͷ����Ƶķ���
	 * */
	public static void releaseToken() {
		// �ͷŻỰ����ֵ
		HttpRequestContext.getRequest().getSession()
				.setAttribute(TOKEN_SESSION_ATTR_NAME, "");

	}

	/**
	 * ������ǩ�ص�
	 * 
	 * @return �ص��������ִ�б�ʶ
	 * */
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		// ����UUID��ȡΨһ������ֵ
		String token = UUID.randomUUID().toString();
		// �ڻỰ�б�������ֵ
		pageContext.getSession().setAttribute(TOKEN_SESSION_ATTR_NAME, token);
		// ������������HTML�ַ���
		String tokenTag = "<input type=\"hidden\" name=\"SUBMIT_TOKEN_ATTR_NAME_REQUEST\" value=\""
				+ token + "\"/>";
		try {
			// ��ҳ��������������ַ���
			pageContext.getOut().print(tokenTag);
			// �����쳣
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// ����쳣��Ϣ
			e.printStackTrace();
		}
		// ����ǩ���������ִ��ҳ�����������
		return EVAL_PAGE;
	}

	/**
	 * ��ʼ��ǩ�ص�
	 * 
	 * @return �ص��������ִ�б�ʶ
	 * */
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		// ������ǩ��
		return SKIP_BODY;
	}

}
