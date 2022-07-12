package com.records.dao;

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

import com.auth.entity.Role;
import com.client.entity.Client;
import com.common.controller.CommonMethods;
import com.medicine.entity.Medicine;
import com.records.entity.Records;
import com.records.entity.UserVoClientVoRecords;

@Repository("recordsDAO")
public class RecordsDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 新增购买记录
	 * 
	 * @param records
	 * @return
	 */
	public Boolean addRecords(Records records) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.getTransaction();
		boolean res = false;
		tran.begin();
		try {
			session.save(records);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		tran.commit();
		return res;
	}

	/**
	 * 购买记录查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Records query(Object key, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Records where delete_time is null and " + key + " = " + "'" + value + "'";
		List<Records> list = session.createQuery(hql).list();
		return ( list == null || list.size() == 0 ) ? null : list.get(0);
	}

	/**
	 * 购买记录查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Records queryRecords(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from records where " + conditionString + "delete_time is null order by id";
		Session session = sessionFactory.getCurrentSession();
		Records records = (Records) session.createSQLQuery(sql).addEntity(Records.class).uniqueResult();
		return records;
	}

	/**
	 * 购买记录列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryRecordsList(Map<String, Object> condition, Integer page, Integer limit) {
		String conditionString = "";
		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer(
				"select new com.records.entity.UserVoClientVoRecords(r.id,r.uid,r.cid,r.symptom,r.mno,r.number,r.remark,u.username,c.cname,r.create_time) from Records r,User u,Client c where r.uid=u.id and r.cid=c.id and r.delete_time is null");
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
			Iterator<String> iterator = condition.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = condition.get(key);
				if (value instanceof Integer) {
					hql.append(" and r." + key + "= '" + value + "'");
				} else if (key.equals("create_time")) {
					hql.append(" and r." + key + " " + value);
				} else {
					hql.append(" and r." + key + " like '%" + value + "%'");
				}
			}
		}
		hql.append(" order by r.id");
		Query query1 = session.createQuery(hql.toString());
		List<UserVoClientVoRecords> list = query1.setFirstResult((page - 1) * limit).setMaxResults(limit).list();
		String sql2 = "select COUNT(*) from records where " + conditionString + "delete_time is null";
		Integer total = jdbcTemplate.queryForObject(sql2, Integer.class);
		Map<String, Object> res = new HashMap<>();
		res.put("list", list);
		res.put("total", total);
		return res;
	}

	/**
	 * 购买记录更新
	 * 
	 * @param id
	 * @param uid
	 * @param mno
	 * @param number
	 * @param remark
	 * @param update_time
	 * @return
	 */
	public Integer updateRecords(Integer id, Integer uid, String mno, String remark, Timestamp update_time) {
		String sql = "update records set uid=?,mno=?,remark=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { uid, mno, remark, update_time, id });
		return res;
	}

	/**
	 * 购买记录刪除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteRecords(Integer id, Timestamp delete_time) {
		String sql = "update records set delete_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { delete_time, id });
		return res;
	}

	public Integer total() {
		String sql = "select COUNT(*) from records where delete_time is null";
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
			String sql = "select COUNT(*) from records where create_time between '" + startDate + "' and '" + endDate
					+ "' and delete_time is null";
			Integer res = jdbcTemplate.queryForObject(sql, Integer.class);

			dataCount.add(res);
		}
		return dataCount;
	}
	
	@SuppressWarnings("unchecked")
	public List getRecordsData() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select * from medicine where delete_time is null";
		List<Medicine> medicineList = session.createSQLQuery(sql).addEntity(Medicine.class).list();	
		List res = new ArrayList();
		for (Medicine  medicine : medicineList) {
			Map<String, Object> pie = new HashMap<>();
			String sqlToCount = "select COUNT(number) from records where delete_time is null and mno = " +  medicine.getMno();
			Integer count = jdbcTemplate.queryForObject(sqlToCount, Integer.class);
			pie.put("value",count);
			pie.put("name",  medicine.getMname());
			res.add(pie);
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserVoClientVoRecords> getNewLists() {
		StringBuffer hql = new StringBuffer(
				"select new com.records.entity.UserVoClientVoRecords(r.id,r.uid,r.cid,r.symptom,r.mno,r.number,r.remark,u.username,c.cname,r.create_time) from Records r,User u,Client c where r.uid=u.id and r.cid=c.id and r.delete_time is null");
		hql.append(" order by r.create_time");
		Session session = sessionFactory.getCurrentSession();
		List<UserVoClientVoRecords> list = session.createQuery(hql.toString()).setFirstResult(0).setMaxResults(10).list();
		return list;
	}
}
