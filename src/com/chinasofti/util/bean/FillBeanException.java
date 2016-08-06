/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean;

/**
 * <p>
 * Title: FillBeanException
 * </p>
 * <p>
 * Description: �Զ����ҵ���쳣�࣬�������JavaBean����ʱ�����쳣״��
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
public class FillBeanException extends RuntimeException {
	/**
	 * ���췽���������쳣��Ϣ
	 * 
	 * @param beanClassName
	 *            �����쳣ʱ��������JavaBean������
	 * @param propertyName
	 *            �����쳣ʱ����������������
	 * */
	public FillBeanException(String beanClassName, String propertyName) {
		super("���Bean��" + beanClassName + "������" + propertyName + "ʱ�����쳣");
	}
}
