/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Hashtable;

import com.chinasofti.util.bean.convertor.BooleanConvertor;
import com.chinasofti.util.bean.convertor.ByteConvertor;
import com.chinasofti.util.bean.convertor.CharConvertor;
import com.chinasofti.util.bean.convertor.DoubleConvertor;
import com.chinasofti.util.bean.convertor.FloatConvertor;
import com.chinasofti.util.bean.convertor.IntConvertor;
import com.chinasofti.util.bean.convertor.LongConvertor;
import com.chinasofti.util.bean.convertor.ShortConvertor;
import com.chinasofti.util.bean.convertor.StringConvertor;
import com.chinasofti.util.bean.convertor.TypeConvertor;
/**
 * <p>
 * Title: BeanUtil
 * </p>
 * <p>
 * Description: ���÷������ʡ���JAVA Bean���ԵĹ�����
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
public class BeanUtil {

	/**
	 * ��ȡ��ǰ������ʹ�õ�����ת��������
	 * @return ��ǰ��������ʹ�õ�����ת��������
	 * */
	public Hashtable<String, TypeConvertor> getConvertors() {
		return convertors;
	}

	/**
	 * ���õ�ǰ������ʹ�õ�����ת��������
	 * @param convertors ϣ������ʹ�õ��µ�����ת��������
	 * */
	public void setConvertors(Hashtable<String, TypeConvertor> convertors) {
		this.convertors = convertors;
	}

	/**
	 * �������ڴ��б����Ӧ���͵�ת������ת����֧���ַ���-�ض����ͣ�����-�ض����ͣ���-�ض����͵�ת��
	 * */ 
	private Hashtable<String, TypeConvertor> convertors = new Hashtable<String, TypeConvertor>();

	/**
	 * ��ʼ�����ߣ�����Ĭ�ϵ�����ת������Ĭ������ת������Ҫ�ṩ�ַ�����������������֮���ת��
	 * */ 
	public BeanUtil() {

		// ���ض����͵�����ת������������ת�����б�
		convertors.put("java.lang.String", new StringConvertor());
		convertors.put("int", new IntConvertor());
		convertors.put("java.lang.Integer", new IntConvertor());
		convertors.put("byte", new ByteConvertor());
		convertors.put("java.lang.Byte", new ByteConvertor());
		convertors.put("short", new ShortConvertor());
		convertors.put("java.lang.Short", new ShortConvertor());
		convertors.put("long", new LongConvertor());
		convertors.put("java.lang.Long", new LongConvertor());
		convertors.put("float", new FloatConvertor());
		convertors.put("java.lang.Float", new FloatConvertor());
		convertors.put("double", new DoubleConvertor());
		convertors.put("java.lang.Double", new DoubleConvertor());
		convertors.put("boolean", new BooleanConvertor());
		convertors.put("java.lang.Boolean", new BooleanConvertor());
		convertors.put("char", new CharConvertor());
		convertors.put("java.lang.Character", new CharConvertor());
//		convertors.put("java.util.Date", new SimpleDateConvertor());
//		convertors.put("java.sql.Date", new SimpleDateConvertor());
	}

	/**
	 * ����ĳ���ض�������������Ҫʹ�õ�����ת�������ṩ�Զ���ת���������ò�����������������ת������Ҳ���Ը���Ĭ��ת����
	 * 
	 * @param targetType
	 *            ��Ҫת����Ŀ����������
	 * @param convertor
	 *            Ŀ��������Ҫʹ�õ���������ת����
	 * */
	public void setConvertor(String targetType, TypeConvertor convertor) {
		convertors.put(targetType, convertor);
	}

	
	/**
	 * �Ƴ�ĳ���ض�������������Ҫʹ�õ�����ת�����ķ���
	 * 
	 * @param targetType
	 *            ��Ҫת����Ŀ����������
	 * */
	public void removeConvertor(String targetType) {
		convertors.remove(targetType);
	}

	/**
	 * �������������������Ҫʹ�õ�����ת�����ķ���
	 * */
	public void clearConvertor() {
		convertors.clear();
	}

	/**
	 * �����ַ����Ͷ�Ӧת�������Bean
	 * 
	 * @param bean
	 *            Ҫ���Ķ���
	 * @param propertyName
	 *            Ҫ����������
	 * @param propertyValue
	 *            Ҫ��������ֵ
	 * @param IgnoreCase
	 *            ����Bean�ж�Ӧ����ʱ�Ƿ���Դ�Сд
	 * */
	public void setBeanProperty(Object bean, String propertyName,
			String propertyValue, boolean IgnoreCase) {
		// ������ʡ��ȡָ��Java Bean������������Ϣ����
		PropertyDescriptor[] pds = getBeanPropertyDescriptor(bean);
		// ����Bean����������
		for (PropertyDescriptor pd : pds) {
			// �ж����������������Ƿ�����ڵ�ǰBean��
			if (((!IgnoreCase) && pd.getName().equals(propertyName))
					|| (IgnoreCase && pd.getName().equalsIgnoreCase(
							propertyName))) {
				// �����Bean���ҵ�����Ӧ�����ԣ����ȡ���Զ�Ӧ��Setter����
				Method setter = pd.getWriteMethod();
				// ��ȡSetter������һ������������˵�������������Ե�����˵������
				String typeString = setter.getParameterTypes()[0]
						.getCanonicalName();				
				Object value = propertyValue;
				// ���������������Ͷ�Ӧ������ת����
				if (convertors.get(typeString) != null) {
					// ������ڸ����Ͷ�Ӧ������ת����������������ת������ԭ����ֵת��ΪĿ������
					value = convertors.get(typeString).convertToObject(
							propertyValue);
				}

				try {
					// ���÷���������Ե�Setter����Ϊ������ֵ
					setter.invoke(bean, new Object[] { value });
				} catch (Exception ex) {
					// ���ִ�в��ɹ������׳�FillBeanException���Զ���ҵ���쳣
					throw new FillBeanException(bean.getClass()
							.getCanonicalName(), propertyName);
				}

			}

		}

	}

	/**
	 * ���ö���Ͷ�Ӧ��ת�������Bean
	 * 
	 * @param bean
	 *            Ҫ���Ķ���
	 * @param propertyName
	 *            Ҫ����������
	 * @param propertyValue
	 *            Ҫ��������ֵ
	 * @param IgnoreCase
	 *            ����Bean�ж�Ӧ����ʱ�Ƿ���Դ�Сд
	 * */
	public void setBeanProperty(Object bean, String propertyName,
			Object propertyValue, boolean IgnoreCase) {
		// ������ʡ��ȡָ��Java Bean������������Ϣ����
		PropertyDescriptor[] pds = getBeanPropertyDescriptor(bean);
		// ����Bean����������
		for (PropertyDescriptor pd : pds) {
			// �ж����������������Ƿ�����ڵ�ǰBean��
			if (((!IgnoreCase) && pd.getName().equals(propertyName))
					|| (IgnoreCase && pd.getName().equalsIgnoreCase(
							propertyName))) {
				// �����Bean���ҵ�����Ӧ�����ԣ����ȡ���Զ�Ӧ��Setter����
				Method setter = pd.getWriteMethod();
				// ��ȡSetter������һ������������˵�������������Ե�����˵������
				String typeString = setter.getParameterTypes()[0]
						.getCanonicalName();
				try {
					// ���������������Ͷ�Ӧ������ת����
					if (convertors.get(typeString) != null) {
						// ������ڸ����Ͷ�Ӧ������ת����������������ת������ԭ����ֵת��ΪĿ������
						propertyValue = convertors.get(typeString)
								.convertToObject(propertyValue);
					}
					// ���÷���������Ե�Setter����Ϊ������ֵ
					setter.invoke(bean, new Object[] { propertyValue });
				} catch (Exception ex) {
					// ���ִ�в��ɹ������׳�FillBeanException���Զ���ҵ���쳣
					throw new FillBeanException(bean.getClass()
							.getCanonicalName(), propertyName);
				}
			}
		}
	}

	/**
	 * ������ʡ��ȡJavaBean���������ķ���
	 * @param bean Ҫ��ʡ��JavaBean����
	 * @return ����JavaBean���������������������
	 * */
	private PropertyDescriptor[] getBeanPropertyDescriptor(Object bean) {
		try {
			// ��ʵ��������Bean���������ʡ
			BeanInfo binfo = Introspector.getBeanInfo(bean.getClass());
			// ��ȡ����������Ϣ
			PropertyDescriptor[] pds = binfo.getPropertyDescriptors();
			// ��������������Ϣ
			return pds;
		} catch (Exception e) {
			// ��������쳣��������쳣��Ϣ
			e.printStackTrace();
			// �ڷ����쳣������£�����null
			return null;
			// TODO: handle exception
		}

	}

}
