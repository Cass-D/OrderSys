/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * <p>
 * Title: ObjectFactoryCacheSoftReference
 * </p>
 * <p>
 * Description: ����ʹ�õ�������
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
public class ObjectFactoryCacheSoftReference extends SoftReference {

	/**
	 * �����������õĶ����ڻ����еļ�����
	 * */
	String key = "";

	/**
	 * ���������õĹ��췽��
	 * 
	 * @param key
	 *            �������������õĶ����ڻ����еļ�����
	 * @param referent
	 *            �������������õ�ʵ�ʶ��󣬼�Ҫ���뵽�����е�JavaBean����
	 * @param q
	 *            �����������󶨵����ö��У���������������õĶ����������ջ��ƻ��գ����Զ��������ñ�����뵽��������
	 * */
	public ObjectFactoryCacheSoftReference(String key, Object referent,
			ReferenceQueue q) {
		// ���ø���Ĺ��췽��
		super(referent, q);
		// ��ʼ��������ֵ
		this.key = key;
	}	

}
