/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.template;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static java.sql.Types.*;

import com.chinasofti.util.bean.BeanUtil;
import com.chinasofti.util.jdbc.template.specialsqloperation.MySQLSpecialOperation;
import com.chinasofti.util.jdbc.template.specialsqloperation.OracleSpecialOperation;

import com.chinasofti.util.jdbc.template.specialsqloperation.SpecialSQLOperation;

/**
 * <p>
 * Title: JDBCTemplate
 * </p>
 * <p>
 * Description: JDBCģ��ģʽ��װ����
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
public class JDBCTemplate {

	/**
	 * ��Ų�ͬ���ݿ��Ʒ�����⴦�����ӳ�伯�ϣ���������ӳ���ΪJDBC�����������⴦����Ҫ������ݿ��ѯ��ҳ
	 * */
	private static Hashtable<String, Class<? extends SpecialSQLOperation>> driverDBMSMapping = new Hashtable<String, Class<? extends SpecialSQLOperation>>();

	/**
	 * �򹤾�������ض����ݿ��Ʒ��Ӧ���⴦�����ķ���
	 * 
	 * @param driverName
	 *            ���ݿ�JDBC��������
	 * @param operationClass
	 *            ��Ӧ�����⴦����
	 * */
	public static void addDriverDBMSMapping(String driverName,
			Class<? extends SpecialSQLOperation> operationClass) {

		driverDBMSMapping.put(driverName, operationClass);
	}

	/**
	 * �������÷������ʡ�Զ����ʵ��Bean�Ĺ���
	 * */
	private BeanUtil beanUtil = new BeanUtil();

	/**
	 * ���ڱ���JDBC����ʱע���������
	 * */
	private String driverName;

	/**
	 * ���ڱ���JDBC����ʱʹ�õ������ַ���
	 * */
	private String connectionString;

	/**
	 * ���ڱ����������ݿ�ʱ������֤�����ݿ����ϵͳ��½�û���
	 * */
	private String dbmsUserName;

	/**
	 * ���ڱ����������ݿ�ʱ������֤�����ݿ����ϵͳ��½����
	 * */
	private String dbmsPassword;

	/**
	 * ����JDBC��������
	 * 
	 * @param driverName
	 *            �ṩ�����ӹ��ߵ�JDBC������
	 * */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * ����JDBC�����ַ���
	 * 
	 * @param connectionString
	 *            �ṩ�����ӹ��ߵ����ݿ������ַ���
	 * */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * �������ݿ��½�û���
	 * 
	 * @param dbmsUserName
	 *            ���ݿ��½��
	 * */
	public void setDbmsUserName(String dbmsUserName) {
		this.dbmsUserName = dbmsUserName;
	}

	/**
	 * �������ݿ��½����
	 * 
	 * @param dbmsPassword
	 *            ���ݿ��½����
	 * */
	public void setDbmsPassword(String dbmsPassword) {
		this.dbmsPassword = dbmsPassword;
	}

	/**
	 * ��ȡ���ݿ����ӵķ���
	 * 
	 * @return ��������ض����ݿ������������ݿ����Ӷ������JDBC���Դ����ܴ������ӣ��򷵻�null
	 * */
	public Connection getConnection() {

		try {
			// ����������ؽ���ǰ�����������ʵ��ע�������Ĳ���
			Class.forName(driverName);
			// �����ṩ�������ַ��������ݿ��½�˺Ŵ���JDBC���ݿ�����
			Connection con = DriverManager.getConnection(connectionString,
					dbmsUserName, dbmsPassword);

			// �������Ӷ���
			return con;
		} catch (Exception e) {
			// ����쳣��Ϣ
			e.printStackTrace();
			// ����ڴ������ӵĹ����з����쳣�����˻������򷵻�nullsss
			return null;
		}

	}

	/**
	 * ���췽���������ṩ�Ĳ�������ģ��ģʽ���ݿ��������
	 * 
	 * @param driverName
	 *            JDBC��������
	 * @param connectionString
	 *            JDBC�����ַ���
	 * @param dbmsUserName
	 *            ���ݿ��û���
	 * @param dbmsPassword
	 *            ���ݿ��½����
	 * */
	JDBCTemplate(String driverName, String connectionString,
			String dbmsUserName, String dbmsPassword) {
		super();
		// ��ʼ��������
		this.driverName = driverName;
		// ��ʼ�������ַ���
		this.connectionString = connectionString;
		// ��ʼ���û���
		this.dbmsUserName = dbmsUserName;
		// ��ʼ������
		this.dbmsPassword = dbmsPassword;
		// ���MYSQL�������������
		addDriverDBMSMapping("com.mysql.jdbc.Driver",
				MySQLSpecialOperation.class);
		// ���Oracle�������������
		addDriverDBMSMapping("oracle.jdbc.dirver.OracleDriver",
				OracleSpecialOperation.class);

	}

	/**
	 * ������ͨ������ִ��SQL���ķ���
	 * 
	 * @param callback
	 *            JDBC�ص�����
	 * @return JDBCִ�з��ؽ��
	 * */
	public Object execute(JDBCCallback callback) {
		// ���巵��ֵ����
		Object result = null;
		Connection con = null;
		Statement statement = null;
		try {
			// ��ȡ���ݿ�����
			con = getConnection();
			// ��ȡ��Ԥ�����������
			statement = con.createStatement();
			// ִ�лص�����
			result = callback.doWithStatement(statement);

		} catch (Exception e) {
			// ������쳣������쳣��Ϣ
			e.printStackTrace();
			// TODO: handle exception
		} finally {

			// �ر��������ͷ���Դ
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// �ر����Ӷ����ͷ���Դ
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// �������ս��
		return result;
	}

	/**
	 * ����Ԥ����������ִ��SQL���ķ���
	 * 
	 * @param callback
	 *            JDBC�ص�����
	 * @return JDBCִ�з��ؽ��
	 * */
	public Object execute(String preparedSQL, JDBCCallback callback) {
		// ���巵��ֵ����
		Object result = null;
		Connection con = null;
		PreparedStatement statement = null;
		try {
			// ��ȡ���ݿ�����
			con = getConnection();
			// �����ṩ�Ĳ�������Ԥ����������

			statement = con.prepareStatement(preparedSQL,
					Statement.RETURN_GENERATED_KEYS);

			// ���ûص�����ִ��SQL��䲢��ȡ����ֵ
			result = callback.doWithStatement(statement);
			// �ر�������
			statement.close();
			// �ر����Ӷ���
			con.close();
		} catch (Exception e) {

			// ������쳣������쳣��Ϣ
			e.printStackTrace();
		} finally {

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		// ����ִ�к�Ľ��
		return result;
	}

	/**
	 * ������ͨ������ִ�е������ݸ��²����ķ���
	 * 
	 * @param sql
	 *            ��Ҫִ�е����ݸ���SQL���
	 * @return �������ݸ��²����漰���������������ִ�й��̷����쳣���򷵻�-1
	 * */
	public int executeUpdate(final String sql) {

		// �����������ڲ��ഴ��ִ�����ݸ��²����Ļص�����
		JDBCCallback updateCallback = new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// ���巵��ֵ�����ִ�й����г����쳣���򷵻�-1
				int result = -1;
				try {
					// ִ�д��ݹ��������ݸ���SQL����
					result = statement.executeUpdate(sql);
				} catch (Exception e) {
					// ������쳣��������쳣��Ϣ
					e.printStackTrace();
				}
				// ������ֵ��װ�ɶ���󷵻�
				return new Integer(result);
			}
		};
		// ִ�в����ṩ��SQL��䣬�������ݸ��²����ظ����漰������
		return ((Integer) execute(updateCallback)).intValue();
	}

	/**
	 * ����Ԥ����������ִ�е������ݸ��²����ķ���
	 * 
	 * @param preparedSQL
	 *            ��Ҫִ�е����ݸ��µ�Ԥ����SQL���
	 * @param data
	 *            ��Ÿ������ݵĲ�������
	 * @return �������ݸ��²����漰���������������ִ�й��̷����쳣���򷵻�-1
	 * */
	public int executePreparedUpdate(String preparedSQL, final Object[] data) {
		// ����ִ�и��²����Ļص�����
		JDBCCallback updateCallback = new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				try {
					// ��ȡԤ����������
					PreparedStatement pstatement = (PreparedStatement) statement;
					// ��������ֵ
					for (int i = 0; i < data.length; i++) {
						// Ϊ�ض���ռλ�����ö�Ӧ�Ĳ���ֵ
						pstatement.setObject(i + 1, data[i]);
					}
					// ����Ԥ����������ִ�����ݸ��µ�SQL��䲢��÷���ֵ
					int result = pstatement.executeUpdate();
					// ������ֵ��װΪ����󷵻�
					return new Integer(result);

				} catch (Exception e) {
					// ��������쳣��������쳣��Ϣ
					e.printStackTrace();
					// �����쳣������-1��Ϊ������
					return new Integer(-1);
					// TODO: handle exception
				}

			}
		};
		return ((Integer) execute(preparedSQL, updateCallback)).intValue();
	}

	/**
	 * ������ͨ������ִ���������²����ķ���
	 * 
	 * @param �����Ҫ�������²�����SQL��������
	 * @return ����ÿ����������漰��������Ŀ��
	 * */
	public int[] executeBatch(final String[] batchSQLs) {
		// ����ִ��������Ļص�����
		JDBCCallback batchCallback = new JDBCCallback() {
			// ʵ��ִ��������Ļص�����
			@Override
			public Object doWithStatement(Statement statement) {

				try {
					for (String sql : batchSQLs) {
						// �����е�ÿһ���ַ���������Ҫִ�е�һ��SQL�����ӵ���������
						statement.addBatch(sql);
					}
					// ִ��������������£������ؽ��
					return statement.executeBatch();
				} catch (Exception e) {
					// ��������쳣��������쳣��Ϣ
					e.printStackTrace();
					// ���쳣����£�����nulls
					return null;
				}
			}
		};

		// ִ�лص����������յĽ��
		return (int[]) execute(batchCallback);

	}

	/**
	 * ����Ԥ����������ִ�����������ݸ��µķ���
	 * 
	 * @param preparedSQL
	 *            ����Ԥ�����������SQL���
	 * @param data
	 *            ����ÿһ�θ��²����Ĳ��������б��б��ÿ��Ԫ�ض���Object���飬�������Ԫ�ر���Ԥ����SQL�����ռλ����ʵ������
	 * @param batchSize
	 *            ÿ��ִ�и��²���������
	 * */
	public void executePreparedBatch(String preparedSQL,
			final List<Object[]> data, final int batchSize) {

		// ����ִ��������Ļص�����
		JDBCCallback batchCallback = new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				try {
					// ��ȡԤ����������
					PreparedStatement pstatement = (PreparedStatement) statement;
					// ��������������ݵ��б�
					for (int i = 0; i < data.size(); i++) {
						// ��ȡ�б��е�һ��Ԫ�أ�ÿ��Ԫ�ض���һ��Object[]���飬�������д���˵��θ��²����漰������
						Object[] updateData = data.get(i);
						// ������������
						for (int index = 0; index < updateData.length; index++) {
							// ����˳���ռλ�����ʵ������
							pstatement.setObject(index + 1, updateData[index]);
						}
						// �����θ��²�����������������
						pstatement.addBatch();
						// �ж��Ƿ񵽴�涨�ĵ����������������
						if (i % batchSize == 0) {
							// ִ��������
							pstatement.executeBatch();
							// �����ʷ����������
							pstatement.clearBatch();
						}

					}
					// ִ��ʣ�������������
					return pstatement.executeBatch();
				} catch (Exception e) {
					// ���ִ�й����з����쳣��������쳣��Ϣ
					e.printStackTrace();
					// �������쳣������·���null
					return null;
				}

			}

		};

		execute(preparedSQL, batchCallback);
	}

	/**
	 * ����Ԥ���������󼰷�Ԥ����������ִ�л�����������ݸ��µķ���
	 * 
	 * @param preparedSQL
	 *            ����Ԥ�����������SQL���
	 * @param data
	 *            ����ÿһ�θ��²����Ĳ��������б��б��ÿ��Ԫ�ض���Object���飬�������Ԫ�ر���Ԥ����SQL�����ռλ����ʵ������
	 * @param batchSize
	 *            ÿ��ִ�и��²���������
	 * @param batchSQLs
	 *            ��Ҫ������ִ�еķ�Ԥ����SQL�������
	 * */
	public void executeMixedBatch(String preparedSQL,
			final List<Object[]> data, final int batchSize,
			final String[] batchSQLs) {

		JDBCCallback batchCallback = new JDBCCallback() {

			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				try {
					// ��ȡԤ����������
					PreparedStatement pstatement = (PreparedStatement) statement;
					// ��������������ݵ��б�
					for (int i = 0; i < data.size(); i++) {
						// ��ȡ�б��е�һ��Ԫ�أ�ÿ��Ԫ�ض���һ��Object[]���飬�������д���˵��θ��²����漰������
						Object[] updateData = data.get(i);
						// ������������
						for (int index = 0; index < updateData.length; index++) {
							// ����˳���ռλ�����ʵ������
							pstatement.setObject(index + 1, updateData[index]);
						}
						// �����θ��²�����������������
						pstatement.addBatch();
						// �ж��Ƿ񵽴�涨�ĵ����������������
						if (i % batchSize == 0) {
							// ִ��������
							pstatement.executeBatch();
							// �����ʷ����������
							pstatement.clearBatch();
						}
					}
					// ������Ԥ�����������SQL���
					for (String sql : batchSQLs) {
						// ��ÿһ��SQL����������������
						pstatement.addBatch(sql);
					}
					// ִ��ʣ�������������
					pstatement.executeBatch();
					// ���������践��ֵ
					return null;
				} catch (Exception e) {
					// ��������쳣���������쳣��Ϣ
					e.printStackTrace();
					// ���������践��ֵ
					return null;
				}
			}
		};
		// ִ��������
		execute(preparedSQL, batchCallback);
	}

	/**
	 * ������ͨ������ִ��SQL��ѯ�ķ���
	 * 
	 * @param sql
	 *            ִ�в�ѯ��SQL���
	 * @param persistenceClass
	 *            ��Ų�ѯ�����ʵ������Ϣ
	 * @return ����ִ�в�ѯ��Ľ����
	 * */
	public <T> ArrayList<T> queryForList(final String sql,
			final Class<T> persistenceClass) {
		// ����ִ�в�ѯ�Ļص�����
		JDBCCallback queryCallback = new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				try {
					// ִ��SQL��ѯ
					ResultSet rs = statement.executeQuery(sql);
					// ��������е������Զ���䵽����������
					ArrayList<T> resultList = resultSet2List(rs,
							persistenceClass);
					// �رս�������ͷ���Դ
					rs.close();
					// ���ؽ������
					return resultList;
				} catch (Exception e) {
					// �����ִ�й����г����쳣��������쳣��Ϣ
					e.printStackTrace();
					// �����쳣������£�����null
					return null;
				}
			}
		};
		// ִ�в�ѯ�ص������շ��ؽ��
		return (ArrayList<T>) execute(queryCallback);

	}

	/**
	 * ����Ԥ����������ִ��SQL��ѯ�ķ���
	 * 
	 * @param preparedSQL
	 *            ִ�в�ѯ��Ԥ����SQL���
	 * @param args
	 *            ִ�в�ѯ������ֵ����
	 * @param persistenceClass
	 *            ��Ų�ѯ�����ʵ������Ϣ
	 * @return ����ִ�в�ѯ��Ľ����
	 * */
	public <T> ArrayList<T> preparedQueryForList(final String preparedSQL,
			final Object[] args, final Class<T> persistenceClass) {

		// ����ִ�в�ѯ�Ļص�����
		JDBCCallback queryCallback = new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {

				try {
					// ��ȡԤ����������
					PreparedStatement pstatement = (PreparedStatement) statement;
					// ������ѯ����ֵ����
					for (int i = 0; i < args.length; i++) {
						// Ϊ��Ӧ��ռλ�����ö�Ӧ��ֵ
						pstatement.setObject(i + 1, args[i]);
					}
					// ִ�в�ѯ����
					ResultSet rs = pstatement.executeQuery();
					// ��������е������Զ���䵽����������
					ArrayList<T> resultList = resultSet2List(rs,
							persistenceClass);
					// �رս�������ͷ���Դ
					rs.close();
					// ���ؽ������
					return resultList;

				} catch (Exception e) {
					// �����ִ�й����г����쳣��������쳣��Ϣ
					e.printStackTrace();
					// �����쳣������£�����null
					return null;
				}

			}
		};
		// ִ�в�ѯ�ص������շ��ؽ��
		return (ArrayList<T>) execute(preparedSQL, queryCallback);

	}

	/**
	 * ��ѯ�������ݲ������ת��ΪObject����ķ���
	 * 
	 * @param sql
	 *            ִ�в�ѯ��SQL���
	 * @return ���صĲ�ѯ���
	 * */
	public Object queryForObject(final String sql) {
		// ������ѯ�ص���ִ�в�ѯ�����ؽ��
		Object returnObj = execute(new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				// ���Բ�ѯ���
				try {
					// ��ȡ��ѯ�����
					ResultSet rs = statement.executeQuery(sql);
					// �ƶ��α�
					rs.next();
					// ��ȡ��ѯ����Ψһ����
					Object obj = rs.getObject(1);
					// �رս����
					rs.close();
					// ���ؽ��
					return obj;
					// �����쳣
				} catch (Exception ex) {
					// ����쳣��Ϣ
					ex.printStackTrace();
					// ���ؿս��
					return null;
				}
			}
		});
		// ���ػ�ȡ���Ľ������
		return returnObj;

	}

	/**
	 * ��ѯ������ݲ������ת��Ϊ��������ķ���
	 * 
	 * @param sql
	 *            ִ�в�ѯ��SQL���
	 * @return ���صĲ�ѯ���
	 * */
	public Object[] queryForObjectArray(final String sql) {
		// ������ѯ�ص���ִ�в�ѯ�����ؽ��
		Object[] array = (Object[]) execute(new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				// ���Բ�ѯ���
				try {
					// ��ȡ��ѯ�����
					ResultSet rs = statement.executeQuery(sql);
					// ResultSetMetaData metaData = rs.getMetaData();
					// int columnCount = metaData.getColumnCount();
					// ��������ĵ�����¼ת��Ϊ��������
					Object[] resultArray = singleLineResut2ObjectArray(rs);
					// rs.next();
					// for (int i = 1; i <= columnCount; i++) {
					// resultArray[i - 1] = rs.getObject(i);
					// }
					// �رս����
					rs.close();
					// ���ؽ������
					return resultArray;
					// �����쳣
				} catch (Exception ex) {
					// ����쳣��Ϣ
					ex.printStackTrace();
					// ���ؿ�����
					return null;
				}
			}
		});
		// ���ؽ������
		return array;
	}

	/**
	 * ����Ԥ�����������ѯ������ݲ������ת��Ϊ��������ķ���
	 * 
	 * @param sql
	 *            ִ�в�ѯ��SQL���
	 * @param args
	 *            Ԥ����SQL������
	 * @return ���صĲ�ѯ�������
	 * */
	public Object[] preparedQueryForObjectArray(String preparedSQL,
			final Object[] args) {
		// ������ѯ�ص���ִ�в�ѯ�����ؽ��
		Object[] array = (Object[]) execute(preparedSQL, new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				// ������������ΪԤ����������
				PreparedStatement stmt = (PreparedStatement) statement;
				// ���Բ�ѯ���
				try {
					// ����˳���ռλ��������������
					for (int i = 0; i < args.length; i++) {
						// Ϊ��Ӧ��ռλ�����ö�Ӧ��ֵ
						stmt.setObject(i + 1, args[i]);
					}
					// ��ȡ��ѯ�����
					ResultSet rs = stmt.executeQuery();
					// ResultSetMetaData metaData = rs.getMetaData();
					// int columnCount = metaData.getColumnCount();
					// ��������ĵ�����¼ת��Ϊ��������
					Object[] resultArray = singleLineResut2ObjectArray(rs);
					// rs.next();
					// for (int i = 1; i <= columnCount; i++) {
					// resultArray[i - 1] = rs.getObject(i);
					// }
					// �رս����
					rs.close();
					// ���ؽ������
					return resultArray;
					// �����쳣
				} catch (Exception ex) {
					// ����쳣��Ϣ
					ex.printStackTrace();
					// ���ؿ�����
					return null;
				}
			}
		});
		// ���ؽ������
		return array;
	}

	/**
	 * ִ�в�����������ز���ʱ���ɵ�����ֵ�б�ķ���
	 * 
	 * @param insertSQL
	 *            ִ�в��������SQL���
	 * @return ���β������ִ�к��Զ����ɵ�����ֵ����
	 * */
	public Object[] insertForGeneratedKeys(final String insertSQL) {
		// ������ѯ�ص���ִ�в�ѯ�����ؽ��
		Object[] generatedKeys = (Object[]) execute(new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// TODO Auto-generated method stub
				// ����ִ�в������
				try {
					// ִ�в��������Ҫ�󷵻��Զ����ɵ�����ֵ�б�
					statement.executeUpdate(insertSQL,
							Statement.RETURN_GENERATED_KEYS);
					// ��ȡ����ֵ�����
					ResultSet rs = statement.getGeneratedKeys();
					// ������ֵ�����ת��ΪObject����
					Object[] keys = singleLineResut2ObjectArray(rs);
					// �رս����
					rs.close();
					// ��������ֵ����
					return keys;
					// �����쳣
				} catch (Exception ex) {
					// ����쳣��Ϣ
					ex.printStackTrace();
					// ����null����
					return null;
				}
			}
		});
		// ��������ֵ����
		return generatedKeys;
	}

	/**
	 * ����Ԥ����������ִ�в�����������ز���ʱ���ɵ�����ֵ�б�ķ���
	 * 
	 * @param sql
	 *            ִ�в��������SQL���
	 * @param args
	 *            Ԥ����SQL������
	 * @return ���β������ִ�к��Զ����ɵ�����ֵ����
	 * */
	public Object[] preparedInsertForGeneratedKeys(String preparedInsertSQL,
			final Object[] args) {
		// ������ѯ�ص���ִ�в�ѯ�����ؽ��
		Object[] generatedKeys = (Object[]) execute(preparedInsertSQL,
				new JDBCCallback() {
					// ʵ�ֻص�����
					@Override
					public Object doWithStatement(Statement statement) {
						// TODO Auto-generated method stub
						// ����ִ�в������
						try {
							// ������������ΪԤ����������
							PreparedStatement stmt = (PreparedStatement) statement;
							// ����˳���ռλ��������������
							for (int i = 0; i < args.length; i++) {
								// Ϊ�ض���ռλ�����ö�Ӧ�Ĳ���ֵ
								stmt.setObject(i + 1, args[i]);
							}
							// ִ�в������
							stmt.executeUpdate();
							// ��ȡ����ֵ�����
							ResultSet rs = stmt.getGeneratedKeys();
							// ������ֵ�����ת��ΪObject����
							Object[] keys = singleLineResut2ObjectArray(rs);
							// �رս����
							rs.close();
							// ������������
							return keys;
							// �����쳣
						} catch (Exception e) {
							// ����쳣��Ϣ
							e.printStackTrace();
							// ����null����
							return null;
							// TODO: handle exception
						}

					}
				});
		// ��������ֵ����
		return generatedKeys;
	}

	/**
	 * ��ֻ��һ�еĽ��������ת��Ϊ��������ķ���
	 * 
	 * @param rs
	 *            Ҫת���Ľ����
	 * @return ����ת����Ľ������
	 * */
	private Object[] singleLineResut2ObjectArray(ResultSet rs) {
		// ���Խ���ת������
		try {
			// ��ȡ�����Ԫ����
			ResultSetMetaData metaData = rs.getMetaData();
			// ��ȡ���������
			int columnNum = metaData.getColumnCount();
			// �����������
			Object[] array = new Object[columnNum];
			// �ƶ��α�
			rs.next();
			// �����������ÿһ��
			for (int i = 0; i < columnNum; i++) {
				// ȡ��������ж�Ӧ�е����ݲ�������������Ӧ��λ����
				array[i] = rs.getObject(i + 1);
			}
			// ���ؽ������
			return array;
			// �����쳣
		} catch (Exception e) {
			// ����쳣��Ϣ
			e.printStackTrace();
			// ����null����
			return null;
			// TODO: handle exception
		}

	}

	/**
	 * ����Ԥ�����������ѯ�������ݲ������ת��ΪObject����ķ���
	 * 
	 * @param preparedSQL
	 *            ִ�в�ѯ��SQL���
	 * @param args
	 *            Ԥ����SQL������
	 * @return ���صĲ�ѯ���
	 * */
	public Object preparedQueryForObject(String preparedSQL, final Object[] args) {
		// ������ѯ�ص���ִ�в�ѯ�����ؽ��
		Object returnObj = execute(preparedSQL, new JDBCCallback() {
			// ʵ�ֻص�����
			@Override
			public Object doWithStatement(Statement statement) {
				// ������������ΪԤ����������
				PreparedStatement stmt = (PreparedStatement) statement;
				// ����ִ�в�ѯ����
				try {
					// ����˳���ռλ��������������
					for (int i = 0; i < args.length; i++) {
						// Ϊ��Ӧ��ռλ�����ö�Ӧ��ֵ
						stmt.setObject(i + 1, args[i]);
					}
					// ��ȡ��ѯ�����
					ResultSet rs = stmt.executeQuery();
					// �ƶ�������α�
					rs.next();
					// ��ȡ������еĵ�һ�������
					Object obj = rs.getObject(1);
					// �رս����
					rs.close();
					// ���ؽ������
					return obj;
					// �����쳣
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					// ����쳣��Ϣ
					e.printStackTrace();
					// ���ؿն���
					return null;
				}
				// TODO Auto-generated method stub

			}
		});
		// ���ؽ������
		return returnObj;

	}

	/**
	 * ����Ԥ����������ʵ�����ݷ�ҳ��ʾ��ѯ�ķ���
	 * 
	 * @param preparedSQL
	 *            ��ѯ���
	 * @param args
	 *            Ԥ�����ѯ����������
	 * @param page
	 *            Ҫ��ѯ��ҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @param persistenceClass
	 *            ��ѯ�����Ӧ��ʵ����
	 * @return ���ز�ѯ���
	 * */
	public <T> ArrayList<T> preparedForPageList(String preparedSQL,
			Object[] args, int page, int pageSize, Class<T> persistenceClass) {
		// ��ȡ��ҳ���ݲ�����
		return preparedForOffsetList(preparedSQL, args, (page - 1) * pageSize,
				pageSize, persistenceClass);
	}

	/**
	 * ����Ԥ����������ʵ������offset��ѯ�ķ���
	 * 
	 * @param preparedSQL
	 *            ��ѯ���
	 * @param args
	 *            Ԥ�����ѯ����������
	 * @param page
	 *            Ҫ��ѯ��ҳ��
	 * @param offset
	 *            ƫ����
	 * @param rowNum
	 *            Ҫ��ȡ����Ŀ��
	 * @return ���ز�ѯ���
	 * */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> preparedForOffsetList(String preparedSQL,
			final Object[] args, final int offset, final int rowNum,
			final Class<T> persistenceClass) {
		// �����������
		ArrayList<T> list = new ArrayList<T>();
		// ��ȡ��ͬ���ݿ������TOPN�������ݿ�
		String modifySQL = getSpecialTopNSQL(preparedSQL);

		// System.out.println(modifySQL);
		// �����ѯ���û�б仯��˵��û���ҵ���Ӧ�������������
		if (modifySQL == preparedSQL) {

			// System.out.println("Common WAY");
			// ִ�в�ѯ��䲢��ȡ�������
			list = (ArrayList<T>) execute(preparedSQL, new JDBCCallback() {
				// ʵ�ֻص�
				@Override
				public Object doWithStatement(Statement statement) {
					// TODO Auto-generated method stub
					// ���������������
					PreparedStatement stmt = (PreparedStatement) statement;
					// ���Բ�ѯ
					try {
						// ������������
						for (int i = 0; i < args.length; i++) {
							// Ϊ��Ӧ��ռλ�����ö�Ӧ��ֵ
							stmt.setObject(i + 1, args[i]);
						}
						ResultSet rs = stmt.executeQuery();
						// ��������е������Զ���䵽����������
						ArrayList<T> resultList = resultSet2List(rs, offset,
								rowNum, persistenceClass);
						// �رս�������ͷ���Դ
						rs.close();
						// ���ؽ������
						return resultList;
						// �����쳣
					} catch (Exception e) {
						// ����쳣��Ϣ
						e.printStackTrace();
						// ���ؿն���
						return null;

						// TODO: handle exception
					}

				}
			});
			// ������ҵ��˶�Ӧ������������󲢻�ȡ�������TOPN����SQL���
		} else {

			// System.out.println("Special WAY");
			// ִ�в�ѯ��䲢��ȡ�������
			list = (ArrayList<T>) execute(modifySQL, new JDBCCallback() {
				// ʵ�ֻص�
				@Override
				public Object doWithStatement(Statement statement) {
					// ������������
					PreparedStatement stmt = (PreparedStatement) statement;
					// ���Ի�ȡ����
					try {
						// ���÷�ҳ��ѯ����
						driverDBMSMapping
								.get(driverName)
								.newInstance()
								.setTopNQueryParameter(stmt, args, offset,
										rowNum);
						// ִ�в�ѯ����
						ResultSet rs = stmt.executeQuery();
						// ��ȡ�������
						ArrayList<T> resultList = resultSet2List(rs,
								persistenceClass);
						// �رս����
						rs.close();
						// ���ؽ������
						return resultList;
						// �����쳣
					} catch (Exception e) {
						// ����쳣��Ϣ
						e.printStackTrace();
						// ���ؿն���
						return null;
						// TODO: handle exception
					}

				}
			});

		}
		// ���ؽ������
		return list;
	}

	/**
	 * ��ȡ�������ݿ�TOPN����SQL���ķ���
	 * 
	 * @param sql
	 *            ԭʼSQL���
	 * @return ��ȡ��������������
	 * */
	public String getSpecialTopNSQL(String sql) {
		// ���Ի�ȡ�������
		try {
			// ���������������������򷵻��µ����������䣬���򷵻�ԭʼ��ѯ���
			sql = driverDBMSMapping.containsKey(driverName) ? driverDBMSMapping
					.get(driverName).newInstance().getTopNSQL(sql, true) : sql;
			// �����쳣
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// ����쳣��Ϣ
			e.printStackTrace();
		}
		// ���ؽ��
		return sql;
	}

	/**
	 * ��ȡ�������ķ���
	 * 
	 * @return ���ݿ�������
	 * */
	public String getDriverName() {
		// ����������
		return driverName;
	}

	/**
	 * ��JDBC�����ת��Ϊʵ����󼯺ϵķ���
	 * 
	 * @param rs
	 *            �����ݿ��в�ѯ�õ��Ľ��������
	 * @param persistenceClass
	 *            ��Ӧ��ʵ������Ϣ
	 * @return ����ת���Ľ������
	 * */
	private <T> ArrayList<T> resultSet2List(ResultSet rs,
			Class<T> persistenceClass) {
		// �����������
		ArrayList<T> resultList = new ArrayList<T>();
		try {
			// ��ȡ�������Ԫ���ݣ����Ի�ȡ������е���������ÿ���е�������
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// �õ���������е�������
			int columnCount = rsMetaData.getColumnCount();
			// ѭ�����������
			while (rs.next()) {
				// ������һ��ʵ�����
				T entityObject = persistenceClass.newInstance();
				// ѭ���������е����е�Ԫ��
				for (int cindex = 1; cindex <= columnCount; cindex++) {
					// ��ȡ����ǰ��Ԫ�Ķ�Ӧ������
					String cname = rsMetaData.getColumnName(cindex);
					// �ж���Ԫ�������ݵ�JDBC����
					switch (rsMetaData.getColumnType(cindex)) {
					// ����BLOB����
					case BLOB:
						// ��ȡBLOB��������
						Blob blob = rs.getBlob(cindex);
						// ���ʵ������е��������
						beanUtil.setBeanProperty(entityObject, cname, blob,
								true);
						break;
					// ����CLOB����
					case CLOB:
						// ��ȡCLOB��������
						Clob clob = rs.getClob(cindex);
						// ���ʵ������е��������
						beanUtil.setBeanProperty(entityObject, cname, clob,
								true);
						break;
					// ��������ʱ������
					case TIME:
					case TIMESTAMP:
					case DATE:
						// ��ȡ��������
						// System.out.println(rs.getTimestamp(cindex));
						Timestamp time = rs.getTimestamp(cindex);
						if (time != null) {
							// long timestamp=rs.getTimestamp(cindex).getTime();
							Date date = new Date(time.getTime());
							// ���ʵ������е��������
							beanUtil.setBeanProperty(entityObject, cname, date,
									true);
						}
						break;
					// ���������ճ�����
					default:
						// ���ַ�������ʽ��ȡ����ֵ
						String value = rs.getString(cindex);
						// ����ת�������и�ʽת���������ص�����
						beanUtil.setBeanProperty(entityObject, cname, value,
								true);
						break;

					}

				}
				// �����δ�����ʵ��������������
				resultList.add(entityObject);

			}
		} catch (Exception e) {
			// ���ִ�еĹ����������쳣��������쳣��Ϣ
			e.printStackTrace();
			// �����쳣������£�����null
			return null;
		}
		// �������յĽ������
		return resultList;
	}

	private <T> ArrayList<T> resultSet2List(ResultSet rs, int offset,
			int rowNum, Class<T> persistenceClass) {
		// �����������
		ArrayList<T> resultList = new ArrayList<T>();
		try {
			// ��ȡ�������Ԫ���ݣ����Ի�ȡ������е���������ÿ���е�������
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// �õ���������е�������
			int columnCount = rsMetaData.getColumnCount();

			for (int i = 0; i < offset; i++) {
				rs.next();
			}

			// ѭ�����������
			while (rs.next() && rowNum > 0) {
				// ������һ��ʵ�����
				T entityObject = persistenceClass.newInstance();
				// ѭ���������е����е�Ԫ��
				for (int cindex = 1; cindex <= columnCount; cindex++) {
					// ��ȡ����ǰ��Ԫ�Ķ�Ӧ������
					String cname = rsMetaData.getColumnName(cindex);
					// �ж���Ԫ�������ݵ�JDBC����
					switch (rsMetaData.getColumnType(cindex)) {
					// ����BLOB����
					case BLOB:
						// ��ȡBLOB��������
						Blob blob = rs.getBlob(cindex);
						// ���ʵ������е��������
						beanUtil.setBeanProperty(entityObject, cname, blob,
								true);
						break;
					// ����CLOB����
					case CLOB:
						// ��ȡCLOB��������
						Clob clob = rs.getClob(cindex);
						// ���ʵ������е��������
						beanUtil.setBeanProperty(entityObject, cname, clob,
								true);
						break;
					// ��������ʱ������
					case TIME:
					case TIMESTAMP:
					case DATE:
						// ��ȡ��������
						Date date = rs.getDate(cindex);
						// ���ʵ������е��������
						beanUtil.setBeanProperty(entityObject, cname, date,
								true);
						break;
					// ���������ճ�����
					default:
						// ���ַ�������ʽ��ȡ����ֵ
						String value = rs.getString(cindex);
						// ����ת�������и�ʽת���������ص�����
						beanUtil.setBeanProperty(entityObject, cname, value,
								true);
						break;

					}

				}
				// �����δ�����ʵ��������������
				resultList.add(entityObject);
				rowNum--;

			}
		} catch (Exception e) {
			// ���ִ�еĹ����������쳣��������쳣��Ϣ
			e.printStackTrace();
			// �����쳣������£�����null
			return null;
		}
		// �������յĽ������
		return resultList;
	}

}
