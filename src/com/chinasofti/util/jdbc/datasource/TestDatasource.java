/**
 *  Copyright 2014 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.util.jdbc.datasource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

/**
 * <p>
 * Title: TestDatasource
 * </p>
 * <p>
 * Description:
 * ���ݿ����ӳصĲ�����
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
public class TestDatasource {

	public static void main(String[] args) {
		try {
			//����XML������ȡ���ݿ����ӳض���
//			DataSource ds = (DataSource) XMLObjectFactoryWithCache
//					.getInstance().getBean("myds");
//			//�����ݿ����ӳ��л�ȡ���ݿ�����
//			Connection cons = ds.getConnection();
//			Statement stmt = con.createStatement();
//			stmt.executeUpdate("insert into t_stuinfo(stuname,stupass) values('dsname','dspass')");
//			stmt.close();
//			con.close();
//			con = ds.getConnection();
//			stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select * from t_stuinfo");
//			while (rs.next()) {
//				System.out.println(rs.getString(1) + "   " + rs.getString(2)
//						+ "  " + rs.getString(3));
//			}
//			rs.close();
//			stmt.close();
//			con.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
