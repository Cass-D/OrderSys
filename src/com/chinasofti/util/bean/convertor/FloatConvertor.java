/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean.convertor;
/**
 * <p>
 * Title: FloatConvertor
 * </p>
 * <p>
 * Description: ���ַ�������ת��ΪFloat�������ݵĹ���
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
public class FloatConvertor implements TypeConvertor{
	/**
	 * ִ������ת���ķ���
	 * 
	 * @param srcString
	 *            ԭʼ����ֵ
	 * @return ת����Ľ������
	 * */
	@Override
	public Object convertToObject(Object srcString) {
		// TODO Auto-generated method stub
		return new Float(srcString.toString());
	}

}
