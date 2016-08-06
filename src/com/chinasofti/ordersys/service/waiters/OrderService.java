/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.service.waiters;

import java.util.ArrayList;
import java.util.Date;

import com.chinasofti.ordersys.vo.Cart;
import com.chinasofti.ordersys.vo.OrderInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;

/**
 * <p>
 * Title: OrderService
 * </p>
 * <p>
 * Description: ��������������
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
public class OrderService {

	/**
	 * ���Ӷ����ķŷ�
	 * 
	 * @param waiterId
	 *            ������͵ķ���Աid
	 * @param tableId
	 *            ������Ӧ������
	 * @return ��ӳɹ��Ķ�����Ӧ������ֵ(Long��)
	 * */
	public Object addOrder(int waiterId, int tableId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ȡ��Ӷ���ʱ��ʱ��
		Date now = new Date();
		// ִ�ж��������������ȡ���β�������ɹ����Զ���ȡ������ֵ
		Object[] key = helper
				.preparedInsertForGeneratedKeys(
						"insert into orderinfo(orderBeginDate,waiterId,tableId) values(?,?,?)",
						new Object[] { now, new Integer(waiterId),
								new Integer(tableId) });
		// ���ڶ�����ֻ�е�����������˽���һ�����ɵ�����ֵ����
		return key[0];
	}

	/**
	 * ��Ӷ�����Ʒ��ϸ��Ϣ�ķ���
	 * 
	 * @param unit
	 *            ������Ʒ����
	 * @param key
	 *            ��Ӧ�Ķ���Id
	 * */
	public void addOrderDishesMap(Cart.CartUnit unit, Object key) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ�ж�����Ʒ����������
		helper.executePreparedUpdate(
				"insert into orderdishes(orderReference,dishes,num) values(?,?,?)",
				new Object[] { key, new Integer(unit.getDishesId()),
						new Integer(unit.getNum()) });

	}

	/**
	 * �Է�ҳ��ʽ��ȡ��֧ͬ��״̬������Ϣ�ķ���
	 * 
	 * @param page
	 *            ��Ҫ��ȡ��ҳ����
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @param state
	 *            ��Ҫ��ѯ��֧��״̬��Ϣ
	 * @return ��ѯ����б�
	 * */
	public ArrayList<OrderInfo> getNeedPayOrdersByPage(int page, int pageSize,
			int state) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ArrayList<OrderInfo> list = helper
		// .preparedForPageList(
		// "select orderId,orderBeginDate,orderEndDate,waiterId,orderState,dishes,num from orderinfo,orderdishes where orderinfo.orderId=orderdishes.orderReference and orderinfo.orderState=0",
		// new Object[] {}, page, pageSize, OrderInfo.class);

		// ���в�ѯ����
		ArrayList<OrderInfo> list = helper
				.preparedForPageList(
						"select * from orderinfo,userInfo where orderState=? and userInfo.userId=orderinfo.waiterId",
						new Object[] { new Integer(state) }, page, pageSize,
						OrderInfo.class);
		// ���ز�ѯ�Ľ��
		return list;

	}

	/**
	 * ��ȡ�ض�֧��״̬��������ҳ��
	 * 
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @param state
	 *            ����֧��״̬
	 * @return ��ҳ��
	 * */
	public int getMaxPage(int pageSize, int state) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ѯ��������������Ŀ��
		Long rows = (Long) helper.preparedQueryForObject(
				"select count(*) from orderinfo where orderState=?",
				new Object[] { new Integer(state) });
		// ������ҳ��������
		return (int) ((rows.longValue() - 1) / pageSize + 1);
	}

	/**
	 * ��ȡ��֧ͬ��״̬������Ϣ�ķ���
	 * 
	 * @param state
	 *            ��Ҫ��ѯ��֧��״̬��Ϣ
	 * @return ������Ϣ����
	 */
	public ArrayList<OrderInfo> getNeedPayOrders(int state) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ�в�ѯ
		ArrayList<OrderInfo> list = helper
				.preparedQueryForList(
						"select * from orderinfo,userInfo where orderState=? and userInfo.userId=orderinfo.waiterId",
						new Object[] { new Integer(state) }, OrderInfo.class);
		// ���ز�ѯ���
		return list;

	}

	/**
	 * ����֧�������ķ���
	 * 
	 * @param orderId
	 *            ����֧�������Ķ�����
	 */
	public void requestPay(Integer orderId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ȡ�ᵥʱ��
		Date now = new Date();
		// ���ö�����״̬����Ϊ׼������
		helper.executePreparedUpdate(
				"update orderinfo set orderState=1,orderEndDate=? where orderId=?",
				new Object[] { now, new Integer(orderId) });

	}

	/**
	 * ���ݶ����Ż�ȡ��������ķ���
	 * 
	 * @param orderId
	 *            ��Ҫ��ȡ����Ķ�����
	 * @return ��ѯ���Ķ�����ϸ��Ϣ
	 * */
	public OrderInfo getOrderById(Integer orderId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ�в�ѯ�����ؽ��
		return helper
				.preparedQueryForList(
						"select * from orderinfo,userinfo where orderId=? and orderinfo.waiterId=userinfo.userId",
						new Object[] { orderId }, OrderInfo.class).get(0);

	}

	/**
	 * ��ȡ��һ�������ܼ�
	 * 
	 * @param Ҫ��ȡ�ܼ۵Ķ�����
	 * @return ��ѯ�����ܼ�
	 * */
	public float getSumPriceByOrderId(Integer orderId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ѯ�ܼ�
		Double sum = (Double) helper
				.preparedQueryForObject(
						"SELECT SUM(d.dishesPrice*od.num) FROM orderinfo a,dishesinfo d,orderdishes od WHERE a.orderId=od.orderReference AND od.dishes=d.dishesId AND a.orderId=?",
						new Object[] { orderId });
		// �����ܼ�
		return sum.floatValue();
	}

	/**
	 * ���ݶ����Ż�ȡ��������
	 * 
	 * @param Ҫ��ȡ����Ķ�����
	 * @return ���������б�
	 * */
	public ArrayList<OrderInfo> getOrderDetailById(Integer orderId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ѯ�����ض��������б�
		return helper
				.preparedQueryForList(
						"SELECT * FROM orderinfo o,userinfo u,orderdishes od,dishesinfo d WHERE orderId=? AND o.waiterId=u.userId AND od.orderReference=o.orderId AND d.dishesId=od.dishes",
						new Object[] { orderId }, OrderInfo.class);

	}

	/**
	 * �޸Ķ���֧��״̬�ķ���
	 * 
	 * @param orderId
	 *            Ҫ�޸�״̬�Ķ�����
	 * @param state
	 *            Ŀ��״ֵ̬
	 * */
	public void changeState(Integer orderId, int state) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ�ж���״̬���²���
		helper.executePreparedUpdate(
				"update orderinfo set orderState=? where orderId=?",
				new Object[] { new Integer(state), orderId });

	}

	/**
	 * ���ݽᵥʱ��β�ѯ������Ϣ�ķ���
	 * 
	 * @param beginDate
	 *            ��ѯ�Ŀ�ʼʱ��
	 * @param endDate
	 *            ��ѯ�Ľ���ʱ��
	 * @return �ᵥʱ���ڿ�ʼʱ��ͽ���ʱ��֮������ж����б�
	 * */
	public ArrayList<OrderInfo> getOrderInfoBetweenDate(Date beginDate,
			Date endDate) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ���ݽᵥʱ��β�ѯ������Ϣ
		ArrayList<OrderInfo> list = helper
				.preparedQueryForList(
						"select * from orderinfo,userInfo where orderState=2 and userInfo.userId=orderinfo.waiterId and orderinfo.orderEndDate between ? and ?",
						new Object[] { beginDate, endDate }, OrderInfo.class);
		// ���ؽ��
		return list;
	}

}
