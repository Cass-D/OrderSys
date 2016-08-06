/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean.convertor;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * <p>
 * Title: SimpleDateConvertor
 * </p>
 * <p>
 * Description: ���ַ�������ת��Ϊjava.util.Date�������ݵĹ���
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
public class SimpleDateConvertor implements TypeConvertor {
	/**
	 * ִ������ת���ķ���
	 * 
	 * @param srcString
	 *            ԭʼ����ֵ
	 * @return ת����Ľ������
	 * */
	@Override
	public Object convertToObject(Object srcString) {
		// ��������ģʽ
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			// ���ַ�����������ģʽת��ΪDate����
			Date date=sdf.parse(srcString.toString());
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}

}
