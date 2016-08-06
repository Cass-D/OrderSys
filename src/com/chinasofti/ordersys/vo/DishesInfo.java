/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.vo;

/**
 * <p>
 * Title:DishesInfo
 * </p>
 * <p>
 * Description: ��Ʒ��ϢVO
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
public class DishesInfo {
	/**
	 * ��ƷID
	 * */
	private int dishesId;
	/**
	 * ��Ʒ����
	 * */
	private String dishesName;
	/**
	 * ��Ʒ����
	 * */
	private String dishesDiscript;
	/**
	 * ��ƷͼƬ
	 * */
	private String dishesImg;
	/**
	 * ��Ʒ��ϸ�����ı�
	 * */
	private String dishesTxt;
	/**
	 * �Ƿ��Ƽ���Ʒ��ʶ
	 * */
	private int recommend;
	/**
	 * ��Ʒ����
	 * */
	private float dishesPrice;

	public int getDishesId() {
		return dishesId;
	}

	public void setDishesId(int dishesId) {
		this.dishesId = dishesId;
	}

	public String getDishesName() {
		return dishesName;
	}

	public void setDishesName(String dishesName) {
		this.dishesName = dishesName;
	}

	public String getDishesDiscript() {
		return dishesDiscript;
	}

	public void setDishesDiscript(String dishesDiscript) {
		this.dishesDiscript = dishesDiscript;
	}

	public String getDishesImg() {
		return dishesImg;
	}

	public void setDishesImg(String dishesImg) {
		this.dishesImg = dishesImg;
	}

	public String getDishesTxt() {
		return dishesTxt;
	}

	public void setDishesTxt(String dishesTxt) {
		this.dishesTxt = dishesTxt;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public float getDishesPrice() {
		return dishesPrice;
	}

	public void setDishesPrice(float dishesPrice) {
		this.dishesPrice = dishesPrice;
	}

}
