/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.datasource;

import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Vector;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * <p>
 * Title: ICSSSimpleDatasource
 * </p>
 * <p>
 * Description:
 * ���ݿ����ӳض������ӳ�ʹ�ô���ģʽ�������ð�װģʽ��ԭ����Connection�ӿ��ڲ�ͬ��JDK�汾�лᷢ��ϸ΢�ı仯��������ģʽ�򲻹������ֱ仯
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
public class ICSSSimpleDatasource implements DataSource {

	/**
	 * �������ӵ���������
	 * */
	private Vector<DSConnectionContext> dbPool = new Vector<DSConnectionContext>();

	/**
	 * ���ӳص��������
	 * */
	private int maxConnections = 100;

	/**
	 * ���ܻ�ȡ����ʱ�ĵȴ�ʱ�䣬��λ������
	 * */
	private int waitTimeOut = 300;

	/**
	 * ��������Ŀ����ʱ���Զ������µ����ӵ�����
	 * */
	private int incrementalConnections = 5;

	/**
	 * ��ʼ��ʱ����������,�������ܹ��Զ�����init()�������г�ʼ��ʱ���ܹ���Ч
	 * */
	private int initConnections = 5;

	/***
	 * ���Ի�ȡ���ӵĴ���
	 */
	private int waitTimes = 5;

	/**
	 * JDBC���������ַ���
	 * */
	private String driverString = "";

	/**
	 * JDBC�����ַ���
	 * */
	private String conString = "";// �����ַ���

	/**
	 * ���ݿ������û���
	 * */
	private String dbUser = "";// �������ݿ���û���
	/**
	 * ���ݿ���������
	 * */
	private String dbPass = "";// �������ݿ������

	/**
	 * ��ȡ��ǰ���������ӳ�
	 * 
	 * @return ���ش�����ӳ��������ӵļ�������
	 */
	public Vector<DSConnectionContext> getDbPool() {
		return dbPool;
	}

	/**
	 * ���õ�ǰ���ݿ����ӳص����������
	 * 
	 * @param maxConnections
	 *            ϣ�����ӳر��������
	 * */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	/**
	 * ����ÿ���û���ȡ���ݿ�����ʱ�ĳ�ʱʱ��
	 * 
	 * @param waitTimeOut
	 *            �û��ȴ���ʱʱ�䣬��λΪ����
	 * */
	public void setWaitTimeOut(int waitTimeOut) {
		this.waitTimeOut = waitTimeOut;
	}

	/**
	 * ���õ��޷���ȡ��������ʱ����������������Ŀ
	 * 
	 * @param incrementalConnections
	 *            �Զ���չ���ӵ���Ŀ��������������չ�������ӳص��������
	 * */
	public void setIncrementalConnections(int incrementalConnections) {
		this.incrementalConnections = incrementalConnections;
	}

	/**
	 * ���ô������ӳ�ʱ�ĳ�ʼ��������
	 * 
	 * @param initConnections
	 *            �������ӳ�ʱ�ĳ�ʼ����������ֻ���ڹ��������ܹ�����init��ʼ��������ʱ����Ч
	 * */
	public void setInitConnections(int initConnections) {
		this.initConnections = initConnections;
	}

	/**
	 * �����û����Ի�ȡ���ӵĵȴ�����
	 * 
	 * @param waitTimes
	 *            �û����Գ��Ի�ȡ���ӵĴ���
	 * */
	public void setWaitTimes(int waitTimes) {
		this.waitTimes = waitTimes;
	}

	/**
	 * �������ӳش���JDBC����ʱʹ�õ������ַ���
	 * 
	 * @param driverString
	 *            JDBC�����ַ���
	 * */
	public void setDriverString(String driverString) {
		this.driverString = driverString;
	}

	/**
	 * �������ӳش���JDBC����ʱʹ�õ������ַ���
	 * 
	 * @param conString
	 *            JDBC�����ַ���
	 * */
	public void setConString(String conString) {
		this.conString = conString;
	}

	/**
	 * ���ô�������ʱ��Ҫ�ṩ�����ݿ��û���
	 * 
	 * @param dbUser
	 *            ���ݿ��û���
	 * */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	/**
	 * ���ô�������ʱ��Ҫ�ṩ�����ݿ�����
	 * 
	 * @param dbPass
	 *            ���ݿ��û�����
	 * */
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}

	/**
	 * ��ʼ�����ӳصķ�������Ҫ���������ڴ������ӳض�������������Ժ��Զ�����
	 * */
	public void init() {
		// �����µ����ӳ�����
		dbPool = new Vector();
		// ���ݳ�ʼ�������ӳ�������С������Ӧ��Ŀ������
		createConnections(initConnections);
	}

	/**
	 * �����ض����������ݿ����ӵķ���
	 * 
	 * @param incremental
	 *            ��Ҫ������������Ŀ
	 * */
	private void createConnections(int incremental) {
		// ÿ��ѭ������һ��JDBC����
		for (int i = 0; i < incremental; i++) {
			// �ж��Ƿ񵽴����ӳع涨�����������
			if (dbPool.size() >= maxConnections) {
				// ����Ѿ�����������������������������
				break;
			}

			// ����JDBC���Ӳ����д���󹹽����������Ķ���
			DSConnectionContext conT = createConnection();
			if (conT != null) {
				// ��������ɹ����򽫱��λ�ȡ�������������Ķ���������ӳ�������
				dbPool.addElement(conT);
			}
		}
	}

	/**
	 * �����������ӵķ���
	 * 
	 * @return �����ɹ�������������
	 * */
	private DSConnectionContext createConnection() {
		try {
			// ����JDBC��������
			Class.forName(driverString);
			// ���ø����������ַ������û��������봴��JDBC����
			Connection con = DriverManager.getConnection(conString, dbUser,
					dbPass);
			// �ж��������Ƿ������ӳ��еĵ�һ������
			if (dbPool.size() == 0) {
				// ����Ǵ����ĵ�һ�����ӣ����ȡ���ݿ��Ԫ����
				DatabaseMetaData metaData = con.getMetaData();
				// �������ݿ�Ԫ���ݻ�ȡ���ݿ���������������
				int driverMaxConnection = metaData.getMaxConnections();
				// ������ݿ�����������������Ʋ��Ҹ���ĿС�ڸ��������ݿ����ӳ������������ݿ��޶�������������Ϊ��ǰ���ӳص�����
				if (driverMaxConnection > 0
						&& maxConnections > driverMaxConnection) {
					setMaxConnections(driverMaxConnection);
				}
			}
			// �������ݿ�����������
			DSConnectionContext conT = new DSConnectionContext(con);
			// �������ӵĴ�����������
			DSConnectionInvocationHandler conHandle = new DSConnectionInvocationHandler(
					conT);
			// ���������Ժ�����Ӷ���
			Connection proxyCon = (Connection) Proxy.newProxyInstance(con
					.getClass().getClassLoader(),
					new Class[] { Connection.class }, conHandle);
			// �������Ժ�����Ӷ��󴫵ݸ�����������
			conT.proxyConnection = proxyCon;
			// ���ػ�ȡ��������������
			return conT;
		} catch (Exception e) {
			// ��������쳣��������쳣��Ϣ
			e.printStackTrace();
			// �ڳ����쳣������·���null
			return null;
		}
	}

	/**
	 * ���ͻ��˳��Ի�ȡ���Ӳ��ɹ�ʱ�ȴ�
	 * 
	 * @param mSeconds
	 *            ���εȴ���ʱ�䣬��λΪ����
	 * */
	private void clientWait(int mSeconds) {

		try {
			// �����ñ��߳�����һ��ʱ�䣬���ߵ�ʱ���ɲ���mSecondsȷ��
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
			// �����쳣������쳣��Ϣ
			e.printStackTrace();

		}
	}

	/**
	 * ���ݿ����ӳػ�ȡ���ӵĺ��ķ���
	 * 
	 * @return ���ش����ӳ��л�ȡ���Ŀ������Ӷ��󣬸ö����ѱ���������close����ʱ����ر����Ӷ���ֱ�ӽ������ӵ�״̬����Ϊ����
	 * */
	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		// ������ӳر���Ϊ�գ��򷵻�null
		if (dbPool == null) {
			return null;
		}
		// ���Դ����ӳ������в��ҿ��е����Ӷ���
		Connection con = getFreeConnection();
		// ����ͻ��˵ȴ�����
		int wTimes = 0;
		// ���û��ֱ�ӻ�ȡ���������ӣ����ҵ�ǰ�ȴ�������û�е���涨�����ȴ����������ӳ�һ��ʱ������³���
		while (con == null && wTimes < waitTimes) {
			// �ȴ������ۼ�
			wTimes++;
			// �ȴ��ӳ�
			clientWait(waitTimeOut);
			// �ٴγ��Ի�ȡ����
			con = getFreeConnection();
		}
		// ���ػ�ȡ�������Ӷ���
		return con;
	}

	/**
	 * ��ͼ���ҿ������ӣ�����������ӳ�Ŀǰ�����ڿ������ӣ�����ͼ��չ���ӳ��������ٴβ���
	 * 
	 * @return ���ҵ��Ŀ������ӣ�����޷��õ��������ӣ��򷵻�null
	 * */
	private Connection getFreeConnection() {
		// ����ֱ�ӻ�ȡ��ǰ�����еĿ�������
		Connection con = findFreeConnection();
		// �����ǰ������û�п������ӣ�������չ���ӳ�����
		if (con == null) {
			// ����һ������������
			createConnections(incrementalConnections);
			// �ٴγ��Ի�ȡ���ӳ��еĿ�������
			con = findFreeConnection();
			// �����Ȼ�޷���ȡ����������˵�������Ѿ�����涨��Ŀ�����е����Ӷ��Ѿ���ռ�ã�����null
			if (con == null) {
				return null;
			}
		}
		// ���ػ�ȡ�������Ӷ���
		return con;
	}

	/**
	 * �����������ӳ��п������ӵķ���
	 * 
	 * @return ���ҵ��Ŀ������ӣ������ǰ���ӳ���û�п������ӣ��򷵻�null
	 * */
	private Connection findFreeConnection() {

		// �����������������Ķ���
		DSConnectionContext conT = null;

		try {
			// �����������ӳ�����
			for (int i = 0; i < dbPool.size(); i++) {
				// ��ȡ����һ�����������Ķ���
				conT = dbPool.elementAt(i);
				// �ж���ǰ�����Ƿ�ռ�ã����״̬Ϊ���У���ֱ��ռ�õ�ǰ����
				if (!conT.busyFlag) {
					// ռ�õ�ǰ����
					conT.busyFlag = true;
					// ���������Ϣ
					System.out.println("ʹ�õ�" + i + "�����Ӵ�������");
					// ���ش����Ժ�����Ӷ���
					return conT.proxyConnection;
				}
			}
			//������е����Ӷ��Ѿ���ռ�ã��򷵻�null
			return null;
		} catch (Exception ex) {
			//�����쳣���ʱ����쳣��Ϣ
			ex.printStackTrace();
			//�ڷ����쳣ʱ����null
			return null;
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
