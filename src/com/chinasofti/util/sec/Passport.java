/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.sec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <p>
 * Title: Passport
 * </p>
 * <p>
 * Description:�Զ���Ŀ�������㷨����Ҫ���ڷǳ����������ַ������ܣ������������紫�����ݵ����ƣ���ͬʱ��ִ�м����㷨��ȡ���ļ��ܽ����ͬ
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
public class Passport {

	public Passport() {
		// TODO �Զ����ɹ��캯�����
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO �Զ����ɷ������
		Passport passport = new Passport();
		// String txt = "�����ı�";
		// String key = "chinasofti";
		// String jia_str = passport.passport_encrypt(txt, key);
		// String jie_str = passport.passport_decrypt(jia_str, key);
		// System.out.println("���ܺ������ԣ�" + jia_str);
		// System.out.println("���ܺ������ԣ�" + jie_str);

		System.out.println(passport.md5("admin"));

	}

	/**
	 * Md5����
	 * 
	 * @param x
	 *            ��Ҫ���ܵ��ַ���
	 * @return md5���ܽ��
	 * @throws Exception
	 */
	public String md5(String x) {
		// ��ȡժҪ����
		MessageDigest m = null;
		try {
			// MD5ժҪ����
			m = MessageDigest.getInstance("MD5");
			// ���±��ĸ�������λԪ��
			m.update(x.getBytes("UTF8"));
			// ����֧��ժҪ�쳣
		} catch (NoSuchAlgorithmException e) {
			// ����һ��MD5��Ϣ�ĸ� ��ʱ�����
			e.printStackTrace();
			// ����֧���ַ����쳣
		} catch (UnsupportedEncodingException e) {
			// ���±��ĸ�������λԪ�� ��ʱ�����
			e.printStackTrace();
		}
		// ������ʹ��λԪ��ı�����������,Ȼ�������ժ����
		byte s[] = m.digest();
		// System.out.println(s); // ������ܺ��λԪ��
		// ��������ַ�������
		StringBuilder result = new StringBuilder("");
		// ������ժ
		for (int i = 0; i < s.length; i++) {
			// ����ʮ������ת��
			result.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
					.substring(6));
		}
		// ���ؼ��ܽ��
		return result.toString();

	}

	/**
	 * ���������ַ����� MIME BASE64 ���롣�˱��뷽ʽ�����������ֻ���ͼƬҲ����������˳�����䡣�� BASE64
	 * �������ַ���ֻ����Ӣ����ĸ��Сд�����������֡��Ӻ��뷴б�ߣ��� 64 �������ַ�������������������ַ��� �����ȡ��
	 * BASE64���������ַ�����ԭ�����ַ��������ټ� 1/3 ���ҡ������ BASE64 ������Ϣ���Բο� RFC2045 �ļ�֮ 6.8 ��
	 * 
	 * @param txt
	 *            �ȴ������ԭ�ִ�
	 * @return �����Ľ��
	 */
	public String base64_decode(String txt) {
		// ���������
		BASE64Decoder base64_decode = new BASE64Decoder();
		// �������ַ���
		String str = "";
		try {
			// ��ȡ���ܽ��
			str = new String(base64_decode.decodeBuffer(txt));
		} catch (IOException e) {
			// ������쳣������쳣��Ϣ
			e.printStackTrace();
		}
		// ���ر�����
		return str;
	}

	/**
	 * Base64����ķ���
	 * 
	 * @param txt
	 *            Ҫ������ַ���
	 * @return ������
	 * */
	public String base64_encode(String txt) {
		// ����������
		BASE64Encoder base64_encode = new BASE64Encoder();
		// ���ر�����
		return base64_encode.encode(txt.getBytes());
	}

	/**
	 * Passport ���ܷ���
	 * 
	 * @param string
	 *            �ȴ����ܵ�ԭ�ִ�
	 * @param string
	 *            ˽���ܳ�(���ڽ��ܺͼ���)
	 * 
	 * @return string ԭ�ִ�����˽���ܳ׼��ܺ�Ľ��
	 */
	public String passport_encrypt(String txt, String key) {
		// �����������
		Random random = new Random();
		// ʹ����������������� 0~32000 ��ֵ
		String rad = String.valueOf(random.nextInt(32000));
		// ��ȡ���ֵ��md5��
		String encrypt_key = md5(rad);

		// ������ʼ��
		int ctr = 0;
		// �������ַ�������
		StringBuilder tmp = new StringBuilder("");

		// ��ȡmd5����ַ�������ʽ
		char encrypt_key_char[] = encrypt_key.toCharArray();
		// ��ȡ��ʼ�ı����ַ�������ʽ
		char txt_char[] = txt.toCharArray();
		// for ѭ����$i Ϊ�� 0 ��ʼ����С�� $txt �ִ����ȵ�����
		for (int i = 0; i < txt.length(); i++) {
			// ��� $ctr = $encrypt_key �ĳ��ȣ��� $ctr ����
			ctr = ctr == encrypt_key_char.length ? 0 : ctr;
			// $tmp �ִ���ĩβ������λ�����һλ����Ϊ $encrypt_key �ĵ� $ctr λ��
			// �ڶ�λ����Ϊ $txt �ĵ� $i λ�� $encrypt_key �� $ctr λȡ���Ȼ�� $ctr = $ctr + 1
			char tmp1 = txt_char[i];
			// �����ַ�
			char tmp4 = encrypt_key_char[ctr];
			// ����ڶ����ַ�
			char tmp2 = encrypt_key_char[ctr++];
			// ����λ����
			char tmp3 = (char) (tmp1 ^ tmp2);
			// ��ӽ������
			tmp.append(tmp4 + "" + tmp3);
		}
		// ���ؽ�������Ϊ passport_key() ��������ֵ�� base65 ������
		return base64_encode(passport_key(tmp.toString(), key));

	}

	/**
	 * Passport ���ܷ���
	 * 
	 * @param string
	 *            ���ܺ���ִ�
	 * @param string
	 *            ˽���ܳ�(���ڽ��ܺͼ���)
	 * 
	 * @return string �ִ�����˽���ܳ׽��ܺ�Ľ��
	 */
	public String passport_decrypt(String txt, String key) {

		// $txt �Ľ��Ϊ���ܺ���ִ����� base64 ���룬Ȼ����˽���ܳ�һ��
		// ���� passport_key() ���������ķ���ֵ
		txt = passport_key(base64_decode(txt), key);
		// ������ʼ��
		StringBuilder tmp = new StringBuilder("");
		// ��ȡ�ַ���������ʽ
		char txt_char[] = txt.toCharArray();
		// for ѭ����$i Ϊ�� 0 ��ʼ����С�� $txt �ִ����ȵ�����
		for (int i = 0; i < txt.length(); i++) {
			// $tmp �ִ���ĩβ����һλ��������Ϊ $txt �ĵ� $i λ��
			// �� $txt �ĵ� $i + 1 λȡ���Ȼ�� $i = $i + 1
			tmp.append((char) (txt_char[i] ^ txt_char[++i]));
		}

		// ���� $tmp ��ֵ��Ϊ���
		return tmp.toString();

	}

	/**
	 * Passport �ܳ״�����
	 * 
	 * @param string
	 *            �����ܻ�����ܵ��ִ�
	 * @param string
	 *            ˽���ܳ�(���ڽ��ܺͼ���)
	 * 
	 * @return string �������ܳ�
	 */
	String passport_key(String txt, String encrypt_key) {

		// �� $encrypt_key ��Ϊ $encrypt_key �� md5() ���ֵ
		encrypt_key = md5(encrypt_key);
		// ������ʼ��
		int ctr = 0;
		// ��������ַ�������
		StringBuilder tmp = new StringBuilder("");

		// ��ȡmd5���ַ�������ʽ
		char encrypt_key_char[] = encrypt_key.toCharArray();
		// ��ȡԭ�ı��ַ����������ʽ
		char txt_char[] = txt.toCharArray();
		// for ѭ����$i Ϊ�� 0 ��ʼ����С�� $txt �ִ����ȵ�����
		for (int i = 0; i < txt.length(); i++) {
			// ��� $ctr = $encrypt_key �ĳ��ȣ��� $ctr ����
			ctr = ctr == encrypt_key.length() ? 0 : ctr;
			// $tmp �ִ���ĩβ����һλ��������Ϊ $txt �ĵ� $i λ��
			// �� $encrypt_key �ĵ� $ctr + 1 λȡ���Ȼ�� $ctr = $ctr + 1
			char c = (char) (txt_char[i] ^ encrypt_key_char[ctr++]);
			// ׷�ӽ��
			tmp.append(c);
		}

		// ���� $tmp ��ֵ��Ϊ���
		return tmp.toString();

	}

}
