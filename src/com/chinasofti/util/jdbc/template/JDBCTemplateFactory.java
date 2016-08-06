/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.template;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * Title: JDBCTemplateFactory
 * </p>
 * <p>
 * Description: ����XML�����ļ���ȡJDBCģ��ģʽ���ݿ���ʹ��ߵĹ�����
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
public class JDBCTemplateFactory {

	/**
	 * ����JDBC���ӹ��ߵ���ʵ������
	 * */
	private static JDBCTemplate instance;

	/**
	 * ˽�еĹ��췽�����������蹹��ʵ��
	 * */
	private JDBCTemplateFactory() {

	}

	/**
	 * ��ȡJDBCģ��ģʽ��װ�����൥��ʵ���ľ�̬��������
	 * @param configFilePath JDBC���Ӳ��������ļ�·��
	 * @return ����JDBC�����൥��ʵ��
	 * */
	public static JDBCTemplate getJDBCHelper(String configFilePath) {
		// �����ǰ�Ĺ���ʵ��Ϊ�գ�����Ҫ��ȡ�����ļ��󴴽�һ���µ�ʵ��
		if (instance == null) {
			try {
				// ���������ļ��д�ŵ���������
				String driverName = "";
				// ���������ļ��д�ŵ������ַ���
				String connectionString = "";
				// ���������ļ���ŵ����ݿ��½�û���
				String dbmsUserName = "";
				// ���������ļ��д�ŵ����ݿ��½����
				String dbmsPassword = "";

				// ����DocumentBuilder���󣬸ö���XML������Ϊ��״�ṹ������ڴ�
				DocumentBuilder db = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				// ����configFilePath·��ָ���XML�ļ�
				Document doc = db.parse(configFilePath);
				// ��XML DOM���ҵ���һ��jdbc��ǩ������ǩ��
				Node jdbcNode = doc.getElementsByTagName("jdbc").item(0);
				// ��ȡjdbc��ǩ���ӱ�ǩ
				NodeList args = jdbcNode.getChildNodes();
				// ����jdbc��ǩ���ӱ�ǩ
				for (int i = 0; i < args.getLength(); i++) {
					// �õ�jdbc��ǩ��һ���ӱ�ǩ
					Node arg = args.item(i);
					// �ж��ӱ�ǩ�ı�ǩ����������������򽫱�ǩ����ı����ݸ�ֵ����ǩ������
					if ("drivername".equals(arg.getNodeName())) {
						driverName = arg.getTextContent().trim();
						// �ж��ӱ�ǩ�ı�ǩ��������������ַ����򽫱�ǩ����ı����ݸ�ֵ�������ַ�������
					} else if ("connectionstring".equals(arg.getNodeName())) {
						connectionString = arg.getTextContent().trim();
						// �ж��ӱ�ǩ�ı�ǩ������������ݿ��û����򽫱�ǩ����ı����ݸ�ֵ�����ݿ��û���������
					} else if ("dbmsusername".equals(arg.getNodeName())) {
						dbmsUserName = arg.getTextContent().trim();
						// �ж��ӱ�ǩ�ı�ǩ������������ݿ������򽫱�ǩ����ı����ݸ�ֵ�����ݿ��������
					} else if ("dbmspassword".equals(arg.getNodeName())) {
						dbmsPassword = arg.getTextContent().trim();
					}

				}

				// ����XML�����ļ������Ľ�������µ�JDBC����ʵ��
				instance = new JDBCTemplate(driverName,
						connectionString, dbmsUserName, dbmsPassword);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ����ʵ������
		return instance;
	}

	/**
	 * ����Ĭ�������ļ���ȡJDBC����ʵ���ķ���
	 * @return ����JDBC���ߵ���ʵ��
	 * */
	public static JDBCTemplate getJDBCHelper() {
		// ����Ĭ�������ļ�����JDBC����ʵ��
		return getJDBCHelper(Thread.currentThread().getContextClassLoader().getResource("jdbc.xml").getPath());
	}

}
