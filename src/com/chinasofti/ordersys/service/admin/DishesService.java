/**
 *  Copyright 2015 ChinaSoft International Ltd. All rights reserved.
 */
package com.chinasofti.ordersys.service.admin;

import java.util.ArrayList;

import com.chinasofti.ordersys.vo.DishesInfo;
import com.chinasofti.util.jdbc.template.JDBCTemplateWithDS;

/**
 * <p>
 * Title: DishesService
 * </p>
 * <p>
 * Description: ��Ʒ����������
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
public class DishesService {
	/**
	 * ��ҳ��ȡ��Ʒ���ݵķ���
	 * 
	 * @param page
	 *            Ҫ��ȡ���ݵ�ҳ��
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @return ��ǰҳ�Ĳ�Ʒ�����б�
	 * */
	public ArrayList<DishesInfo> getDishesInfoByPage(int page, int pageSize) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ͨ����ѯ����ȡ��Ӧҳ������
		ArrayList<DishesInfo> list = helper.preparedForPageList(
				"select * from dishesinfo order by recommend desc,dishesId",
				new Object[] {}, page, pageSize, DishesInfo.class);
		// ���ؽ��
		return list;

	}

	/**
	 * ��ȡ��Ʒ��Ϣ�����ҳ��
	 * 
	 * @param pageSize
	 *            ÿҳ��ʾ����Ŀ��
	 * @return ��ǰ���ݿ������ݵ����ҳ��
	 * */
	public int getMaxPage(int pageSize) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ȡ���ҳ����Ϣ
		Long rows = (Long) helper.preparedQueryForObject(
				"select count(*) from dishesinfo", new Object[] {});
		// �������ҳ��
		return (int) ((rows.longValue() - 1) / pageSize + 1);
	}

	/**
	 * ���ݲ�ƷIDֵɾ����Ʒ��Ϣ�ķ���
	 * 
	 * @param dishesId
	 *            Ҫɾ���Ĳ�ƷId
	 * */
	public void deleteDishesById(Integer dishesId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ��ɾ������
		helper.executePreparedUpdate("delete from dishesinfo where dishesId=?",
				new Object[] { dishesId });
	}

	/**
	 * ��Ӳ�Ʒ�ķ���
	 * 
	 * @param info
	 *            ��Ҫ��ӵĲ�Ʒ��Ϣ
	 * */
	public void addDishes(DishesInfo info) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ����Ӳ���
		helper.executePreparedUpdate(
				"insert into dishesinfo(dishesName,dishesDiscript,dishesTxt,dishesImg,recommend,dishesPrice) values(?,?,?,?,?,?)",
				new Object[] { info.getDishesName(), info.getDishesDiscript(),
						info.getDishesTxt(), info.getDishesImg(),
						new Integer(info.getRecommend()),
						new Float(info.getDishesPrice()) });

	}

	/**
	 * ����dishesId��ȡ��Ʒ��ϸ��Ϣ�ķ���
	 * 
	 * @param dishesId
	 *            Ҫ��ȡ��Ϣ���ض���ƷId
	 * @return ���ظ�id�Ĳ�Ʒ��ϸ��Ϣ
	 * */
	public DishesInfo getDishesById(Integer dishesId) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ѯ����id�Ĳ�Ʒ��ϸ��Ϣ
		ArrayList<DishesInfo> list = helper.preparedQueryForList(
				"select * from dishesinfo where dishesId=?",
				new Object[] { dishesId }, DishesInfo.class);
		// ���ز�ѯ���
		return list.get(0);
	}

	/**
	 * �޸Ĳ�Ʒ��Ϣ�ķ���
	 * 
	 * @param Info
	 *            Ҫ�޸ĵĲ�Ʒ��Ϣ������dishesIdΪ�޸����ݣ�������ϢΪ�޸ĵ�Ŀ��ֵ
	 * */
	public void modifyDishes(DishesInfo info) {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ִ���޸Ĳ���
		helper.executePreparedUpdate(
				"update dishesinfo set dishesName=?,dishesDiscript=?,dishesTxt=?,dishesImg=?,recommend=?,dishesPrice=? where dishesId=?",
				new Object[] { info.getDishesName(), info.getDishesDiscript(),
						info.getDishesTxt(), info.getDishesImg(),
						new Integer(info.getRecommend()),
						new Float(info.getDishesPrice()),
						new Integer(info.getDishesId()) });

	}

	/**
	 * ��ȡͷ4���Ƽ���Ʒ����Ϣ
	 * 
	 * @return ͷ4���Ƽ���Ʒ�б�
	 * */
	public ArrayList<DishesInfo> getTop4RecommendDishes() {
		// ��ȡ�������ӳص����ݿ�ģ��������߶���
		JDBCTemplateWithDS helper = JDBCTemplateWithDS.getJDBCHelper();
		// ��ѯͷ4���Ƽ��Ĳ�Ʒ��Ϣ
		ArrayList<DishesInfo> list = helper.preparedForPageList(
				"select * from dishesinfo where recommend=1 order by dishesId",
				new Object[] {}, 1, 4, DishesInfo.class);

		// ArrayList<DishesInfo> list = helper
		// .preparedQueryForList(
		// "select * from dishesinfo where recommend=1 order by dishesId limit 0,4",
		// new Object[] {}, DishesInfo.class);
		// ���ؽ��
		return list;

	}

}
