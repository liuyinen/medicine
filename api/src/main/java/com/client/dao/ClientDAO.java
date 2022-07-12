package com.client.dao;

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

import com.client.entity.Client;
import com.common.controller.CommonMethods;
import com.records.entity.Records;

//@Repository用于标注数据访问组件，即DAO组件
@Repository("clientDAO")
public class ClientDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 顾客添加
	 * 
	 * @param client
	 * @return
	 */
	public Boolean addClient(Client client) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.getTransaction();
		boolean res = false;
		tran.begin();
		try {
			session.save(client);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		tran.commit();
		return res;
	}

	/**
	 * 用户查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Client query(Object key, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Client where delete_time is null and " + key + " = " + "'" + value + "'";
		Client res = (Client) session.createQuery(hql).uniqueResult();
		return res;
	}

	/**
	 * 顾客查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Client queryClient(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from client where " + conditionString + "delete_time is null order by id";
		Session session = sessionFactory.getCurrentSession();
		Client client = (Client) session.createSQLQuery(sql).addEntity(Client.class).uniqueResult();
		return client;
	}

	/**
	 * 查询顾客列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryClientList(Map<String, Object> condition, Integer page, Integer limit) {
		String conditionString = "";
		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer("from Client where delete_time is null");

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
		List<Client> list = query1.setFirstResult((page - 1) * limit).setMaxResults(limit).list();
		String sql2 = "select COUNT(*) from client where " + conditionString + "delete_time is null";
		Integer total = jdbcTemplate.queryForObject(sql2, Integer.class);
		Map<String, Object> res = new HashMap<>();
		res.put("list", list);
		res.put("total", total);
		return res;
	}

	/**
	 * 顾客更新
	 * 
	 * @param id
	 * @param cname
	 * @param csex
	 * @param cage
	 * @param caddress
	 * @param cphone
	 * @param update_time
	 * @return
	 */
	public Integer updateClient(Integer id, String cname, String csex, Integer cage, String caddress, String cphone,
			Timestamp update_time) {
		String sql = "update client set cname=?,csex=?,cage=?,caddress=?,cphone=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { cname, csex, cage, caddress, cphone, update_time, id });
		return res;
	}

	/**
	 * 客戶刪除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteClient(Integer id, Timestamp delete_time) {
		String sql = "update client set delete_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { delete_time, id });
		return res;
	}

	public Integer total() {
		String sql = "select COUNT(*) from client where delete_time is null";
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
			String sql = "select COUNT(*) from client where create_time between '" + startDate + "' and '" + endDate
					+ "' and delete_time is null";
			Integer res = jdbcTemplate.queryForObject(sql, Integer.class);

			dataCount.add(res);
		}
		return dataCount;
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> getNewLists() {
		String sql = "select top 10 * from client where delete_time is null order by create_time desc";
		Session session = sessionFactory.getCurrentSession();
		List<Client> list = session.createSQLQuery(sql).addEntity(Client.class).list();
		return list;
	}
}
