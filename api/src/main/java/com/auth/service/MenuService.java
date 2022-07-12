package com.auth.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.dao.MenuDAO;
import com.auth.entity.Menu;
import com.auth.entity.MenuVoRole;

@Service("menuService")
public class MenuService {
	@Autowired
	private MenuDAO menuDAO;

	/**
	 * 菜单添加
	 * 
	 * @param menu
	 * @return
	 */
	public Boolean addMenu(Menu menu) {
		return menuDAO.addMenu(menu);
	}

	/**
	 * 菜单查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Menu query(Object key, Object value) {
		return menuDAO.query(key, value);
	}

	/**
	 * 菜单查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Menu queryMenu(Map<String, Object> condition) {
		return menuDAO.queryMenu(condition);
	}

	/**
	 * 菜单列表
	 * 
	 * @param condition
	 * @return
	 */
	public ArrayList queryMenuList(Map<String, Object> condition) {
		return menuDAO.queryMenuList(condition);
	}
}
