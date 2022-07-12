package com.user.dao;

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
import com.common.controller.CommonMethods;
import com.user.entity.User;
import com.user.entity.UserVoRole;

@Repository("userDAO")
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 用戶添加
	 * 
	 * @param user
	 * @return
	 */
	public Boolean addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.getTransaction();
		boolean res = false;
		tran.begin();
		try {
			session.save(user);
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
	@SuppressWarnings("unchecked")
	public User query(Object key, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from User where delete_time is null and " + key + " = " + "'" + value + "'";
		List<User> list = session.createQuery(hql).list();
		return ( list == null || list.size() == 0 ) ? null : list.get(0);
	}

	/**
	 * 用户查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public User queryUser(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from users where " + conditionString + "delete_time is null order by id";
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createSQLQuery(sql).addEntity(User.class).uniqueResult();
		return user;
	}

	/**
	 * 查询用户列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryUserList(Map<String, Object> condition, Integer page, Integer limit) {
		String conditionString = "";
		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer(
				"select new com.user.entity.UserVoRole(u.id,r.role_name,u.username,u.password,u.sex,u.phone,u.avatar,u.introduction,u.create_time) from User u,Role r where u.role_id = r.id and u.delete_time is null");

		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
			Iterator<String> iterator = condition.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = condition.get(key);
				if (value instanceof Integer) {
					hql.append(" and u." + key + "= '" + value + "'");
				} else if (key.equals("create_time")) {
					hql.append(" and u." + key + " " + value);
				} else {
					hql.append(" and u." + key + " like '%" + value + "%'");
				}
			}
		}
		hql.append(" order by u.id");
		Query query1 = session.createQuery(hql.toString());
		List<User> list = query1.setFirstResult((page - 1) * limit).setMaxResults(limit).list();
		String sql2 = "select COUNT(*) from users where " + conditionString + "delete_time is null";
		Integer total = jdbcTemplate.queryForObject(sql2, Integer.class);
		Map<String, Object> res = new HashMap<>();
		res.put("list", list);
		res.put("total", total);
		return res;
	}

	/**
	 * 用户更新
	 * 
	 * @param id
	 * @param role_id
	 * @param username
	 * @param password
	 * @param sex
	 * @param phone
	 * @param introduction
	 * @param update_time
	 * @return
	 */
	public Integer updateUser(Integer id,String username, String password, String sex, String phone,
			String introduction, Timestamp update_time) {
		String sql = "update users set username=?,password=?,sex=?,phone=?,introduction=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql,
				new Object[] { username, password, sex, phone, introduction, update_time, id });
		return res;
	}

	/**
	 * 用户图片上传
	 * 
	 * @param id
	 * @param fileName
	 * @param update_time
	 * @return
	 */
	public Integer updateUserImg(Integer id, String fileName, Timestamp update_time) {
		String sql = "update users set avatar=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { fileName, update_time, id });
		return res;
	}

	/**
	 * 用户删除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteUser(Integer id, Timestamp delete_time) {
		String sql = "update users set delete_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { delete_time, id });
		return res;
	}

	public Integer total() {
		String sql = "select COUNT(*) from users where delete_time is null";
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
			String sql = "select COUNT(*) from users where create_time between '" + startDate + "' and '" + endDate
					+ "' and delete_time is null";
			Integer res = jdbcTemplate.queryForObject(sql, Integer.class);

			dataCount.add(res);
		}
		return dataCount;
	}

	@SuppressWarnings("unchecked")
	public List getUserData() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select * from role where delete_time is null";
		List<Role> roleList = session.createSQLQuery(sql).addEntity(Role.class).list();	
		List res = new ArrayList();
		for (Role role : roleList) {
			Map<String, Object> pie = new HashMap<>();
			String sqlToCount = "select COUNT(*) from users where delete_time is null and role_id = " + role.getId();
			Integer count = jdbcTemplate.queryForObject(sqlToCount, Integer.class);
			pie.put("value",count);
			pie.put("name", role.getRole_name());
			res.add(pie);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<UserVoRole> getNewLists() {
		StringBuffer hql = new StringBuffer(
				"select new com.user.entity.UserVoRole(u.id,r.role_name,u.username,u.password,u.sex,u.phone,u.avatar,u.introduction,u.create_time) from User u,Role r where u.role_id = r.id and u.delete_time is null");
		hql.append(" order by r.create_time");
		Session session = sessionFactory.getCurrentSession();
		List<UserVoRole> list = session.createQuery(hql.toString()).setFirstResult(0).setMaxResults(10).list();
		return list;
	}

//	public Map<String, Object> queryUser(String username) {
//		Session session = sessionFactory.getCurrentSession();
//		String hql = "select u.role_id,u.username,u.password,u.avatar,u.introduction,r.role_name from User u,Role r where u.role_id = r.id and u.username = '"
//				+ username + "' and u.delete_time is null";
//		Object[] user = (Object[]) session.createQuery(hql).list().get(0);
//		Map<String, Object> map = new HashMap<String, Object>();
//		if (user != null) {
//			map.put("role_id", user[0]);
//			map.put("username", user[1]);
//			map.put("password", user[2]);
//			map.put("avater", user[3]);
//			map.put("introduction", user[4]);
//			map.put("role", user[5]);
//		}
//		return map;
//	}

}
