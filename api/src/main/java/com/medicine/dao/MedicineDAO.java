package com.medicine.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.common.controller.CommonMethods;
import com.medicine.entity.Medicine;

@Repository("medicineDAO")
public class MedicineDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 药品添加
	 * 
	 * @param medicine
	 * @return
	 */
	public Boolean addMedicine(Medicine medicine) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.getTransaction();
		boolean res = false;
		tran.begin();
		try {
			session.save(medicine);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		tran.commit();
		return res;
	}

	/**
	 * 药品查询
	 * 
	 * @param condition
	 * @return
	 */
	public Medicine queryMedicine(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from medicine where " + conditionString + "delete_time is null order by id";
		Session session = sessionFactory.getCurrentSession();
		Medicine medicine = (Medicine) session.createSQLQuery(sql).addEntity(Medicine.class).uniqueResult();
		return medicine;
	}

	/**
	 * 药品查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Medicine query(Object key, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Medicine where delete_time is null and " + key + " = " + "'" + value + "'";
		Medicine res = (Medicine) session.createQuery(hql).uniqueResult();
		return res;
	}

	/**
	 * 查询药品列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryMedicineList(Map<String, Object> condition, Integer page, Integer limit) {
		String conditionString = "";
		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer("from Medicine where delete_time is null");

		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
			Iterator<String> iterator = condition.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = condition.get(key);
				if(key.equals("create_time")) {
					hql.append(" and " + key + " " + value);
				}else {
					hql.append(" and " + key + " like '%" + value + "%'");
				}
			}
		}
		hql.append(" order by id");
		Query query1 = session.createQuery(hql.toString());
		List<Medicine> list = query1.setFirstResult((page - 1) * limit).setMaxResults(limit).list();
		String sql2 = "select COUNT(*) from medicine where " + conditionString + "delete_time is null";
		Integer total = jdbcTemplate.queryForObject(sql2, Integer.class);
		Map<String, Object> res = new HashMap<>();
		res.put("list", list);
		res.put("total", total);
		return res;
	}

	/**
	 * 药品更新（单记录）
	 * 
	 * @param id
	 * @param key
	 * @param value
	 * @param update_time
	 * @return
	 */
	public Integer update(Integer id, Object key, Object value, Timestamp update_time) {
		String sql = "update medicine set " + key + "=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { value, update_time, id });
		return res;
	}

	/**
	 * 药品更新
	 * 
	 * @param id
	 * @param mno
	 * @param mname
	 * @param mmode
	 * @param mefficacy
	 * @param update_time
	 * @return
	 */
	public Integer updateMedicine(Integer id, String mno, String mname, Integer stock, Integer mmode, String mefficacy,
			Timestamp update_time) {
		String sql = "update medicine set mno=?,mname=?,stock=?,mmode=?,mefficacy=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { mno, mname, stock, mmode, mefficacy, update_time, id });
		return res;
	}

	/**
	 * 药品删除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteMedicine(Integer id, Timestamp delete_time) {
		String sql = "update medicine set delete_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { delete_time, id });
		return res;
	}

	public Integer total() {
		String sql = "select COUNT(*) from medicine where delete_time is null";
		Integer res = jdbcTemplate.queryForObject(sql, Integer.class);
		return res;
	}


	@SuppressWarnings("unchecked")
	public List dataCount() {
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		List dataCount = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < 7; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			dBefore = calendar.getTime(); // 得到前一天的时间
			String startDate = sdf.format(dBefore);
			startDate = startDate + " 00:00:00";
			String endDate = startDate.substring(0, 10) + " 23:59:59";
			String sql = "select COUNT(*) from medicine where create_time between '" + startDate + "' and '" + endDate
					+ "' and delete_time is null";
			Integer res = jdbcTemplate.queryForObject(sql, Integer.class);

			dataCount.add(res);
		}
		return dataCount;
	}
	
	@SuppressWarnings("unchecked")
	public List<Medicine> getNewLists() {
		String sql = "select top 10 * from medicine where delete_time is null order by create_time desc";
		Session session = sessionFactory.getCurrentSession();
		List<Medicine> list = session.createSQLQuery(sql).addEntity(Medicine.class).list();
		return list;
	}
}
