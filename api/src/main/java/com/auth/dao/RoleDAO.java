package com.auth.dao;

import java.util.Date;
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

@Repository("roleDAO")
public class RoleDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 角色添加
	 * 
	 * @param role
	 * @return
	 */
	public Boolean addRole(Role role) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.getTransaction();
		boolean res = false;
		tran.begin();
		try {
			session.save(role);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		tran.commit();
		return res;
	}

	/**
	 * 角色查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Role query(Object key, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Role where delete_time is null and " + key + " = " + "'" + value + "'";
		Role res = (Role) session.createQuery(hql).uniqueResult();
		return res;
	}

	/**
	 * 角色查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Role queryRole(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from role where " + conditionString + "delete_time is null";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addEntity(Role.class);
		Role res = (Role) query.setMaxResults(1).uniqueResult();
		return res;
	}

	/**
	 * 获取菜单列表
	 * 
	 * @param condition
	 * @return conditionString
	 */
	@SuppressWarnings("unchecked")
	public List<Role> queryRoleList(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from role where " + conditionString + "delete_time is null order by id";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addEntity(Role.class);
		List<Role> list = query.list();
		return list;
	}

	/**
	 * 角色更新
	 * 
	 * @param model
	 */
	public Integer updateRole(Integer id, String rname, String mids, String description, Date update_time) {
		String sql = "update role set role_name=?,mids=?,description=?,update_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { rname, "(" + mids + ")", description, update_time, id });
		return res;
	}

	/**
	 * 角色刪除
	 * 
	 * @param id
	 */
	public Integer deleteRole(Integer id, Date delete_time) {
		String sql = "update role set delete_time=? where id=?";
		Integer res = jdbcTemplate.update(sql, new Object[] { delete_time, id });
		return res;
	}
}
