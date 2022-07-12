package com.auth.controller;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entity.Menu;
import com.auth.entity.Role;
import com.auth.service.MenuService;
import com.auth.service.RoleService;
import com.common.controller.IsCheckUserLogin;
import com.common.exception.MyException;
import com.user.entity.User;

@RestController("menuController")
@RequestMapping(value = "/")
public class MenuController {
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;

	/**
	 * 获取系统导航菜单
	 * 
	 * @param session
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userMenus.do")
	public Map<String, Object> leftList(HttpSession session) throws MyException {
		User user = (User) session.getAttribute("user");
		Role role = roleService.query("id", user.getRole_id());
		if (role == null) {
			throw new MyException("21003", "用户菜单获取失败！");
		}
		Map<String, Object> condition = new HashMap<>();
		condition.put("status", 1);
		if (!role.getMids().equals("*")) {
			condition.put("id", role.getMids());
		}
		ArrayList lists = menuService.queryMenuList(condition);

		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("detail", lists);
		return res;
	}

	/**
	 * 获取菜单列表
	 * 
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/menuList.do")
	public Map<String, Object> list() {
		ArrayList lists = menuService.queryMenuList(null);
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("list", lists);
		return res;
	}

	/**
	 * 添加导航菜单
	 * 
	 * @param name
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value = "/addMenu.do")
	public void addMenu(String name) throws MyException {
		name = name.trim();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());

		Menu info = menuService.query("menu_name", name);
		if (info != null) {
			throw new MyException("21001", "菜单名已存在！");
		}
		Menu menu = new Menu();
		menu.setTitle(name);
		menu.setCreate_time(createTime);
		menuService.addMenu(menu);
	}
}
