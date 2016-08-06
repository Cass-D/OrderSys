/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.vo;

/**
 * <p>
 * Title:UserInfo
 * </p>
 * <p>
 * Description: �û���ϢVO
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
public class UserInfo {
	/**
	 * �û�ID
	 * */
	private int userId;
	/**
	 * �û��˻�
	 * */
	private String userAccount;
	/**
	 * �û�����
	 * */
	private String userPass;
	/**
	 * �û���ɫID
	 * */
	private int roleId;
	/**
	 * �û���ɫ��
	 * */
	private String roleName;
	/**
	 * �û��Ƿ������ı�ʶ
	 * */
	private int locked;
	/**
	 * �û�ͷ��·��
	 * */
	private String faceimg = "default.jpg";

	public String getFaceimg() {
		return faceimg;
	}

	public void setFaceimg(String faceimg) {
		this.faceimg = faceimg;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {

		this.locked = locked;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
