/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean.convertor;

/**
 * <p>
 * Title: TypeConvertor
 * </p>
 * <p>
 * Description: ����ת�����ӿڣ����JavaBeanʱ��������ת������ԭʼ����ת��ΪĿ������
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
public interface TypeConvertor {
	/**
	 * ִ������ת���ķ���
	 * 
	 * @param srcString
	 *            ԭʼ����ֵ
	 * @return ת����Ľ������
	 * */
	public Object convertToObject(Object srcString);

}
