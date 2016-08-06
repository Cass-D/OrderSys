/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.bean.cache;

import java.lang.ref.ReferenceQueue;
import java.util.Hashtable;

/**
 * <p>
 * Title: FactoryBeanCache
 * </p>
 * <p>
 * Description: ����������ʵ���ڴ氲ȫ����Ĺ���
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
public class FactoryBeanCache {
	/**
	 * ���ڱ��滺�����ݵ�hashtable,���ΪJavaBean��ŵ������еļ�������ֵΪ��������������
	 * */
	private Hashtable<String, ObjectFactoryCacheSoftReference> cache = new Hashtable<String, ObjectFactoryCacheSoftReference>();
	/**
	 * ����֪ͨ���������������ö���
	 * */
	ReferenceQueue queue = new ReferenceQueue();

	/**
	 * ˢ�»��漯�ϣ������Ƿ��л�������������ջ��ƻ��գ�������ڱ����յĶ������������ô�Hashtble��ɾ��
	 * */
	private void refershCache() {
		// ������Ӧ�ñ��������ڻ�ȡ���ö����г��ӵ�������
		ObjectFactoryCacheSoftReference ref = null;
		// ѭ�����ӣ���������д��������ö�����˵���������ջ�����Ϊ��ǰ������ڴ���ڲ���ķ��գ��������õ�ֻӵ�������õĶ���������������ü����˶��У�
		while ((ref = (ObjectFactoryCacheSoftReference) queue.poll()) != null) {
			//�����ӵ������ô�Hashtable��ɾ��
			cache.remove(ref.key);
		}
	}

	/**
	 *��������뻺�� �ķ���
	 *@param bean ��Ҫ���������û���Ķ���
	 *@param key ��������뻺���ļ�����
	 * */
	public void putCacheBean(Object bean, String key) {
		//��ִ�л������ǰ��ˢ�»�������
		refershCache();
		//ֻ���ڻ�����ǰ�����ڸ����ļ�����������²ż��뻺�����
		if (!cache.containsKey(key)) {
			//������������������
			ObjectFactoryCacheSoftReference ref = new ObjectFactoryCacheSoftReference(
					key, bean, queue);			
			System.out.println("��bean��" + bean + "���뻺�棬keyֵΪ" + key);
			//�Ƴ�ǿ����
			bean = null;
			//ʹ�ø����ļ������������ô����Hashtable
			cache.put(key, ref);

		}

	}

	/**
	 *������ӻ��� ��ȡ������
	 *@param key ��������뻺���ļ�����
	 *@return ���شӻ��������л�ȡ����JavaBean
	 * */
	public Object getCacheBean(String key) {
		//��ִ�л������ǰ��ˢ�»�������
		refershCache();
		//��������д�����Ӧ�ļ����������������
		if (cache.containsKey(key)) {
			//��Hashtable�����ü�������ȡ������
			ObjectFactoryCacheSoftReference ref = cache.get(key);
			System.out.println("�ӻ����л�ȡ��Ϊ" + key + "��bean");
			//���������������õ�JavaBean����
			return ref.get();

		} else {
			//��������в�������Ӧ�ļ��������򷵻�null
			return null;
		}

	}

}
