package com.auth.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entity.Role;
import com.auth.service.MenuService;
import com.auth.service.RoleService;
import com.client.entity.Client;
import com.common.controller.IsCheckUserLogin;
import com.common.exception.MyException;
import com.user.entity.User;
import com.user.service.UserService;

@RestController("roleController")
@RequestMapping(value = "/")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	@Resource
	private HttpServletRequest request;

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/roleList.do")
	public Map<String, Object> list() {
		List<Role> roles = roleService.queryRoleList(null);
		ArrayList roleList = new ArrayList();
		for (Role role : roles) {
			Map<String, Object> route = new HashMap<>();
			route.put("id", role.getId());
			route.put("role_name", role.getRole_name());
			route.put("description", role.getDescription());
			Map<String, Object> condition = new HashMap<>();
			condition.put("status", 1);
			if (!role.getMids().equals("*")) {
				condition.put("id", role.getMids());
			}
			ArrayList lists = menuService.queryMenuList(condition);
			if (lists != null) {
				route.put("routes", lists);
			}
			route.put("create_time", role.getCreate_time());
			roleList.add(route);
		}
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("total", roleList.size());
		res.put("data", roleList);
		return res;
	}

	/**
	 * 角色添加
	 * 
	 * @param name
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/createRole.do")
	public Map<String, Object> create(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("name") && !map.containsKey("mids")) {
			throw new MyException("00000", "信息获取错误！");
		}
		String rname = map.get("name").trim();
		String description = map.get("description");
		String mids = map.get("mids");

		if (rname.isEmpty() || rname == null) {
			throw new MyException("000000", "角色名不能为空!");
		}
		
		if (mids.isEmpty() || mids == null) {
			throw new MyException("000000", "菜单不能为空!");
		}

		Date createTime = new Timestamp(System.currentTimeMillis());

		Role info = roleService.query("role_name", rname);
		if (info != null) {
			throw new MyException("000000", "角色名已存在！");
		}

		Role role = new Role();
		role.setRole_name(rname);
		role.setDescription(description);
		role.setMids("(" + mids + ")");
		role.setCreate_time(createTime);

		Boolean addRes = roleService.addRole(role);
		Map<String, Object> res = new HashMap<>();
		if (addRes == true) {
			res.put("code", 20000);
			res.put("message", "成功");
		} else {
			res.put("code", 00000);
			res.put("message", "失败");
		}
		return res;
	}

	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/updateRole.do")
	public Map<String, Object> update(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("id") && !map.containsKey("role_name") && !map.containsKey("mids")) {
			throw new MyException("20002", "信息获取失败！");
		}
		Integer id = Integer.parseInt(map.get("id").trim());
		String rname = map.get("role_name").trim();
		String mids = map.get("mids").trim();
		String description = map.get("description");
		Date update_time = new Timestamp(System.currentTimeMillis());

		if (rname.isEmpty() || rname == null) {
			throw new MyException("000000", "角色名不能为空!");
		}
		if (mids.isEmpty() || mids == null) {
			throw new MyException("000000", "权限不能为空!");
		}

		Role info = roleService.query("role_name", rname);
		if (info != null && info.getId() != id) {
			throw new MyException("20001", "角色名已注册！");
		}
		Integer resV = roleService.updateRole(id, rname, mids, description, update_time);

		Map<String, Object> res = new HashMap<>();
		if (resV == 1) {
			res.put("code", 20000);
			res.put("message", "成功");
		} else {
			res.put("code", 00000);
			res.put("message", "失败");
		}
		return res;
	}

	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/deleteRole.do")
	public Map<String, Object> delete() throws MyException {
		if (request.getParameter("id") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		Role role = roleService.query("id", id);
		if (role == null) {
			throw new MyException("000000", "角色不存在！");
		}
		User user = userService.query("role_id", role.getId());
		if (user != null) {
			throw new MyException("000000", "该角色存在经办人，不能删除！");
		}
		Date delete_time = new Timestamp(System.currentTimeMillis());
		Integer resV = roleService.deleteRole(id, delete_time);
		Map<String, Object> res = new HashMap<>();
		if (resV == 1) {
			res.put("code", 20000);
			res.put("message", "成功");
		} else {
			res.put("code", 00000);
			res.put("message", "失败");
		}
		return res;
	}
}
