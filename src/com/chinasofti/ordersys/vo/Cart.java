/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.vo;

import java.util.ArrayList;

/**
 * <p>
 * Title:Cart
 * </p>
 * <p>
 * Description: �㵥���ﳵVO
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
public class Cart {

	/**
	 * ��������
	 * */
	private int tableId;

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	/**
	 * ������Ʒ�����б�
	 * */
	private ArrayList<CartUnit> units = new ArrayList<CartUnit>();

	public ArrayList<CartUnit> getUnits() {
		return units;
	}

	public void setUnits(ArrayList<CartUnit> units) {
		this.units = units;
	}

	/**
	 * ������Ʒ������󷽷�
	 * 
	 * @param dishesId
	 *            ��Ʒ���
	 * @param num
	 *            ��Ʒ����
	 * @return �����õĲ�Ʒ�������
	 * */
	public CartUnit createUnit(int dishesId, int num) {
		return new CartUnit(dishesId, num);
	}

	/**
	 * <p>
	 * Title:CartUnit
	 * </p>
	 * <p>
	 * Description: ���ﳵ��Ʒ��ԪVO
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
	public class CartUnit {

		/**
		 * ��ƷID
		 * */
		private int dishesId;
		/**
		 * ��Ʒ����
		 * */
		private int num;

		private CartUnit(int dishesId, int num) {
			super();
			this.dishesId = dishesId;
			this.num = num;
		}

		public int getDishesId() {
			return dishesId;
		}

		public void setDishesId(int dishesId) {
			this.dishesId = dishesId;
		}

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

	}

}
