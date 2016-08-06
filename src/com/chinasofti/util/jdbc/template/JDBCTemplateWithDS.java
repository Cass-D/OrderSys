/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.template;

import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.chinasofti.util.jdbc.datasource.ICSSSimpleDatasource;

/**
 * <p>
 * Title: JDBCTemplate
 * </p>
 * <p>
 * Description: ʹ����ʵ�������������������ݿ����ӳص�JDBCģ��ģʽ��װ����
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
 * @see JDBCTemplate
 */
public class JDBCTemplateWithDS extends JDBCTemplate {
	/**
	 * ������ʵ���������ڹ�����������
	 * */
	private static JDBCTemplateWithDS instance;
	/**
	 * �������ݿ����ӳع���
	 * */
	ICSSSimpleDatasource dS;

	/**
	 * �����������ڹ�����װ���߶��󣬺͸��๹������ͬ�����๹��������Ҫ��ʼ���������ݿ����ӳص�JDBC����
	 * */
	JDBCTemplateWithDS(String driverName, String connectionString,
			String dbmsUserName, String dbmsPassword) {
		// ���ø��๹����
		super(driverName, connectionString, dbmsUserName, dbmsPassword);
		// ���콨�����ݿ����ӳض���
		dS = new ICSSSimpleDatasource();
		// �������ݿ����ӳ��е�JDBC�����ַ���
		dS.setConString(connectionString);
		// �������ݿ����ӳ��е�������
		dS.setDriverString(driverName);
		// �������ݿ����ӳ��е��û���
		dS.setDbUser(dbmsUserName);
		// �������ݿ����ӳ��е��û�����
		dS.setDbPass(dbmsPassword);
		// ��ʼ�����ݿ����ӳض�����Ҫ������Ԥ�ȴ����ض�����������
		dS.init();
		// TODO Auto-generated constructor stub
	}

	/**
	 * �������ݿ����Ӷ���Ĺ���
	 * 
	 * @return ���ػ�ȡ�������Ӷ���
	 * */
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		try {
			// �����ݿ����ӳ��з��ؿɹ�ʹ�õĿ�������
			return dS.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// ������쳣�����������쳣��Ϣ
			e.printStackTrace();
			// ���ؿ����Ӷ���
			return null;
		}
	}

	/**
	 * ��ȡJDBCģ��ģʽ��װ�����൥��ʵ���ľ�̬��������
	 * 
	 * @param configFilePath
	 *            JDBC���Ӳ��������ļ�·��
	 * @return ����JDBC�����൥��ʵ��
	 * */
	public static JDBCTemplateWithDS getJDBCHelper(String configFilePath) {
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
				instance = new JDBCTemplateWithDS(driverName, connectionString,
						dbmsUserName, dbmsPassword);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ����ʵ������
		return instance;
	}

	/**
	 * ����Ĭ�������ļ���ȡJDBCģ��ģʽ��װ�����൥��ʵ���ľ�̬��������
	 * 
	 * @return ����JDBC�����൥��ʵ��
	 * */
	public static JDBCTemplateWithDS getJDBCHelper() {
		// ����Ĭ�������ļ�����JDBC����ʵ��������
		return getJDBCHelper(Thread.currentThread().getContextClassLoader()
				.getResource("jdbc.xml").getPath());
	}

}
