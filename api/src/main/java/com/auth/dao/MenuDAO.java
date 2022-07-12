package com.auth.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.auth.entity.Menu;
import com.common.controller.CommonMethods;

@Repository("menuDAO")
public class MenuDAO {
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 菜单添加
	 * 
	 * @param menu
	 * @return
	 */
	public Boolean addMenu(Menu menu) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tran = session.getTransaction();
		boolean res = false;
		tran.begin();
		try {
			session.save(menu);
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		tran.commit();
		return res;
	}

	/**
	 * 菜单查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Menu query(Object key, Object value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Menu where delete_time is null and " + key + " = " + "'" + value + "'";
		Menu res = (Menu) session.createQuery(hql).uniqueResult();
		return res;
	}

	/**
	 * 菜单查询 （多条件）
	 * 
	 * @param condition
	 * @return conditionString
	 */
	public Menu queryMenu(Map<String, Object> condition) {
		String conditionString = "";
		if (condition != null) {
			conditionString = CommonMethods.spliceCon(condition);
		}
		String sql = "select * from menu where " + conditionString + "delete_time is null";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql).addEntity(Menu.class);
		Menu res = (Menu) query.setMaxResults(1).uniqueResult();
		return res;
	}

	/**
	 * 菜单查询 多条
	 * 
	 * @param condition
	 * @return conditionString
	 */
	@SuppressWarnings("unchecked")
	public ArrayList queryMenuList(Map<String, Object> condition) {
		ArrayList<Menu> lists = menuTree(condition, 0);
		ArrayList menuList = new ArrayList();
		for (Menu list : lists) {
			Map<String, Object> menu = new HashMap<String, Object>();
			menu.put("id", list.getId());
			menu.put("name", list.getName());
			menu.put("alwaysShow", list.getAlwaysShow());
			menu.put("affix", list.getAffix());
			menu.put("status", list.getStatus());
			menu.put("cache", list.getCache());
			menu.put("pid", list.getPid());
			menu.put("path", list.getPath());
			menu.put("component", list.getComponent());
			menu.put("redirect", list.getRedirect());
			menu.put("sort", list.getSort());
			menu.put("meta", list.getMeta());
			ArrayList<Menu> childLists = menuTree(condition, list.getId());
			if (!childLists.isEmpty()) {
				menu.put("children", childLists);
			}
			menuList.add(menu);
		}
		return menuList;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Menu> menuTree(Map<String, Object> condition, Integer pid) {
		StringBuffer hql = new StringBuffer(
				"select new com.auth.entity.Menu(id,title,name,alwaysShow,affix,icon,cache,pid,path,component,redirect,sort,create_time) from Menu where pid = "
						+ pid + " and delete_time is null ");
		if (condition != null) {
			Iterator<String> iterator = condition.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = condition.get(key);
				if (value instanceof String && Pattern.matches("^\\(((\\d+,)*\\d+)+\\)$", (String) value)) {
					hql.append(" and " + key + " in " + value);
				} else {
					hql.append(" and " + key + "= '" + value + "'");
				}
			}
		}
		hql.append(" order by sort");
		Session session = sessionFactory.getCurrentSession();
		ArrayList<Menu> list = (ArrayList<Menu>) session.createQuery(hql.toString()).list();
		return list;
	}
}
