package com.user.controller;

import java.io.File;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth.entity.Role;
import com.auth.service.RoleService;
import com.client.entity.Client;
import com.client.service.ClientService;
import com.common.controller.Constants;
import com.common.controller.IsCheckUserLogin;
import com.common.controller.Password;
import com.common.exception.MyException;
import com.medicine.entity.Medicine;
import com.medicine.service.MedicineService;
import com.records.entity.Records;
import com.records.entity.UserVoClientVoRecords;
import com.records.service.RecordsService;
import com.user.entity.User;
import com.user.entity.UserVoRole;
import com.user.service.UserService;

@RestController("userController")
@RequestMapping(value = "/")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private RecordsService recordsService;
	@Resource
	private HttpServletRequest request;

	/**
	 * 经办人注册
	 * 
	 * @param map
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value = "/createUser.do")
	public Map<String, Object> register(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("username") && !map.containsKey("password") && !map.containsKey("phone")) {
			throw new MyException("00000", "参数获取错误！");
		}
		String username = map.get("username").trim();
		String password = map.get("password").trim();
		String phone = map.get("phone").trim();
		String sex = map.get("sex") == null ? null : map.get("sex").trim();
		String roleId = map.get("roleId");
		String introduction = map.get("introduction") == null ? null : map.get("introduction").trim();

		if (username.isEmpty() || username == null) {
			throw new MyException("000000", "经办人不能为空!");
		}

		if (password.isEmpty() || password == null) {
			throw new MyException("000000", "密码不能为空!");
		}

		if (phone.isEmpty() || phone == null) {
			throw new MyException("000000", "电话不能为空!");
		}

		if (!Pattern.matches("[0-9]*", phone)) {
			throw new MyException("000000", "非法字符!");
		}

		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		password = Password.encodeBase64(password);

		User info1 = userService.query("username", username);
		if (info1 != null) {
			throw new MyException("20001", "经办人已注册！");
		}

		User info2 = userService.query("phone", phone);
		if (info2 != null) {
			throw new MyException("20001", "电话已存在！");
		}

		Role info3 = roleService.query("id", Integer.parseInt(roleId));
		if (info3 == null) {
			throw new MyException("20006", "所选角色不存在");
		}

		User user = new User();

		user.setUsername(username);
		user.setPassword(password);
		user.setSex(sex);
		user.setPhone(phone);
		user.setRole_id(Integer.parseInt(roleId));
		user.setIntroduction(introduction);
		user.setCreate_time(createTime);

		Boolean addRes = userService.addUser(user);
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

	/**
	 * 经办人登录
	 * 
	 * @param map
	 * @param model
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value = "/login.do")
	public Map<String, Object> login(@RequestBody Map<String, String> map, Model model) throws MyException {
		if (!map.containsKey("username") && !map.containsKey("password")) {
			throw new MyException("20002", "账号密码获取错误！");
		}
		String username = map.get("username").trim();
		String password = map.get("password").trim();

		if (username.isEmpty() || username == null) {
			throw new MyException("000000", "密码不能为空!");
		}

		if (password.isEmpty() || password == null) {
			throw new MyException("000000", "密码不能为空!");
		}

		User user = userService.query("username", username);
		if (user == null) {
			throw new MyException("20003", "账号错误！");
		}

		if (!password.equals(Password.decodeBase64((String) user.getPassword()))) {
			throw new MyException("20004", "密码错误！");
		}

		request.getSession().setAttribute("user", user);

		Map<String, Object> msg = new HashMap<>();
		msg.put("token", "admin-token");
		msg.put("userInfo", user);
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("data", msg);
		return res;
	}

	/**
	 * 经办人信息
	 * 
	 * @param session
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/userInfo.do")
	public Map<String, Object> info(HttpSession session) throws MyException {
		User user = (User) session.getAttribute("user");
		System.out.println("======================");
		System.out.println(user);
		if (user == null) {
			throw new MyException("000000", "经办人获取错误");
		}

		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("data", user);
		return res;
	}

	/**
	 * 经办人列表
	 * 
	 * @param request
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/userList.do")
	public Map<String, Object> list(HttpServletRequest request) throws MyException {
		Integer page = Integer.parseInt(request.getParameter("page").trim());
		Integer limit = Integer.parseInt(request.getParameter("limit").trim());
		String username = request.getParameter("username");
		String phone = request.getParameter("phone");
		String rid = request.getParameter("rid");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if ((startTime != null && endTime == null) || startTime == null && endTime != null) {
			throw new MyException("000000", "时间范围选择有错");
		}

		Map<String, Object> condition = new HashMap<>();
		if (username != null && username != "") {
			condition.put("username", username.trim());
		}
		if (phone != null && phone != "") {
			condition.put("phone", phone.trim());
		}
		if (rid != null && rid != "") {
			condition.put("role_id", Integer.parseInt(rid));
		}

		if (startTime != null && endTime != null) {
			try {
				Timestamp start = Timestamp.valueOf(startTime);
				Timestamp end = Timestamp.valueOf(endTime);
				condition.put("create_time", "between '" + start + "' and '" + end + "'");
			} catch (Exception e) {
				throw new MyException("000000", "日期类型转换出错！");
			}

		}
		Map<String, Object> users = userService.queryUserList(condition, page, limit);
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("data", users);
		return res;
	}

	/**
	 * 经办人更新
	 * 
	 * @param map
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/updateUser.do")
	public Map<String, Object> updateUser(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("id") && !map.containsKey("role_id") && !map.containsKey("username")
				&& !map.containsKey("password") && !map.containsKey("sex") && !map.containsKey("phone")
				&& !map.containsKey("introduction")) {
			throw new MyException("20002", "信息获取失败！");
		}
		Integer id = Integer.parseInt(map.get("id").trim());
		String username = map.get("username").trim();
		String password = map.get("password").trim();
		String sex = map.get("sex").trim();
		String phone = map.get("phone").trim();
		String introduction = map.get("introduction").trim();
		Timestamp update_time = new Timestamp(System.currentTimeMillis());

		if (username.isEmpty() || username == null) {
			throw new MyException("000000", "密码不能为空!");
		}

		if (password.isEmpty() || password == null) {
			throw new MyException("000000", "密码不能为空!");
		}

		if (phone.isEmpty() || phone == null) {
			throw new MyException("000000", "电话不能为空!");
		}

		if (!Pattern.matches("[0-9]*", phone)) {
			throw new MyException("000000", "非法字符!");
		}

		User info1 = userService.query("username", username);
		if (info1 != null && info1.getId() != id) {
			throw new MyException("20001", "经办人名已注册！");
		}

		User info2 = userService.query("phone", phone);
		if (info2 != null && info2.getId() != id) {
			throw new MyException("20001", "电话已存在！");
		}

		User user = userService.query("username", username);
		if (!password.equals(user.getPassword())) {
			password = Password.encodeBase64(password);
		}

		Integer resV = userService.updateUser(id,username, password, sex, phone, introduction, update_time);

		Map<String, Object> res = new HashMap<>();
		if (resV == 1) {
			res.put("code", 20000);
			res.put("message", "成功");
		} else {
			res.put("code", 00000);
			res.put("message", "更新失败");
		}
		return res;
	}

	/**
	 * 经办人删除
	 * 
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/deleteUser.do")
	public Map<String, Object> deleteUser() throws MyException {
		if (request.getParameter("id") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		User user = userService.query("id", id);
		if (user == null) {
			throw new MyException("000000", "经办人不存在！");
		}
		Records records = recordsService.query("uid", user.getId());
		if (records != null) {
			throw new MyException("000000", "为顾客提供服务的经办人信息不能删除！");
		}
		Timestamp delete_time = new Timestamp(System.currentTimeMillis());
		Integer resV = userService.deleteUser(id, delete_time);
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

	/**
	 * 批量删除
	 * 
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/batchDeleteUser.do")
	public Map<String, Object> batchDelete() throws MyException {
		if (request.getParameter("ids") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		String str = request.getParameter("ids").trim();
		if(str.isEmpty()) {
			throw new MyException("000000", "请选择要删除的数据！");
		}
		String[] ids = str.split(",");
		for (int i = 0; i < ids.length; i++) {
			User user = userService.query("id", ids[i]);
			if (user == null) {
				throw new MyException("000000", "id为" + ids[i] + "的经办人不存在！");
			}
			Records records = recordsService.query("uid", user.getId());
			if (records != null) {
				throw new MyException("000000", user.getUsername() + "为顾客提供服务，不能删除！");
			}
			Timestamp delete_time = new Timestamp(System.currentTimeMillis());
			Integer resV = userService.deleteUser(user.getId(), delete_time);
			if (resV != 1) {
				throw new MyException("000000", user.getUsername() + "删除失败！");
			}
		}
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		return res;
	}

	/**
	 * 图片上传
	 * 
	 * @param img
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImg.do")
	public Map<String, Object> upload(MultipartFile img) throws MyException {
		if (img == null || request.getParameter("id") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		String originalFileName = img.getOriginalFilename();
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
		String fileName = UUID.randomUUID().toString() + suffix;
		String filePath = Constants.IMG_PATH + fileName;
		File saveFile = new File(filePath);

		User info = userService.query("id", id);
		if (info == null) {
			throw new MyException("000000", "经办人不存在！");
		}
		Map<String, Object> res = new HashMap<>();
		try {
			img.transferTo(saveFile);// 将上传的文件保存到服务器文件系统
			Timestamp update_time = new Timestamp(System.currentTimeMillis());
			Integer resV = userService.updateUserImg(id, fileName, update_time);
			if (resV == 1) {
				res.put("code", 20000);
				res.put("message", "成功");
			}
		} catch (IOException e) {
			res.put("code", 000000);
			res.put("message", "上传失败！");
			e.printStackTrace();
		}
		return res;

	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout.do")
	public Map<String, Object> logout(HttpSession session) {
		session.removeAttribute("user");
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		return res;
	}

	@IsCheckUserLogin(check = true)
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reportData.do")
	public Map<String, Object> reportData() {
		Map<String, Object> res = new HashMap<>();
		// 总数
		Map<String, Object> total = new HashMap<>();
		Integer clientTotal = clientService.total();
		Integer userTotal = userService.total();
		Integer medicineTotal = medicineService.total();
		Integer recordsTotal = recordsService.total();
		total.put("client", clientTotal);
		total.put("user", userTotal);
		total.put("medicine", medicineTotal);
		total.put("records", recordsTotal);

//		// 获取7天内的日期作为折线图的下标
		List sub = new ArrayList();
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		for (int i = 0; i < 7; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			dBefore = calendar.getTime();
			String startDate = sdf.format(dBefore);
			sub.add(startDate);
		}
		Collections.reverse(sub);

		Map<String, Object> lineChart = new HashMap<>();
		List clientDataList = clientService.dataCount();		
		List userDataList = userService.dataCount();
		List medicineDataList = medicineService.dataCount();
		List recordsDataList = recordsService.dataCount();
		lineChart.put("client", clientDataList);
		lineChart.put("user", userDataList);
		lineChart.put("medicine", medicineDataList);
		lineChart.put("records", recordsDataList);
		
		Map<String, Object> newLists = new HashMap<>();
		List<UserVoRole> userLists = userService.getNewListse();
		List<Client> clientLists = clientService.getNewListse();
		List<Medicine> medicineLists = medicineService.getNewListse();
		List<UserVoClientVoRecords> recordsLists = recordsService.getNewListse();
		newLists.put("user", userLists);
		newLists.put("client", clientLists);
		newLists.put("medicine", medicineLists);
		newLists.put("records", recordsLists);

		List userVoRole = userService.getUserData();
		List recordsVoMedicine = recordsService.getRecordsData();
		
		Map<String, Object> data = new HashMap<>();
		data.put("total", total);
		data.put("sub", sub);
		data.put("lineChart", lineChart);
		data.put("newLists", newLists);
		data.put("userVoRole", userVoRole);
		data.put("recordsVoMedicine", recordsVoMedicine);
		res.put("data", data);
		res.put("code", 20000);
		res.put("message", "成功");
		return res;
	}

}
