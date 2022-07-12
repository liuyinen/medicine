package com.records.controller;

import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.client.entity.Client;
import com.client.service.ClientService;
import com.common.controller.IsCheckUserLogin;
import com.common.exception.MyException;
import com.medicine.entity.Medicine;
import com.medicine.service.MedicineService;
import com.records.entity.Records;
import com.records.service.RecordsService;
import com.user.entity.User;
import com.user.service.UserService;

@RestController("recordsController")
@RequestMapping(value = "/")
public class RecordsController {
	@Autowired
	private RecordsService recordsService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private UserService userService;
	@Autowired
	private MedicineService medicineService;
	@Resource
	private HttpServletRequest request;

	/**
	 * 新增购买记录
	 * 
	 * @param map
	 * @param session
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value = "/createRecords.do")
	public Map<String, Object> create(@RequestBody Map<String, String> map, HttpSession session) throws MyException {
		if (!map.containsKey("mno") && !map.containsKey("cname") && !map.containsKey("number")) {
			throw new MyException("00000", "信息获取错误！");
		}
		String mno = map.get("mno").trim();
		String cname = map.get("cname").trim();
		Integer number = Integer.parseInt(map.get("number").trim());
		String symptom = map.get("symptom") == null ? null : map.get("symptom").trim();
		String remark = map.get("remark") == null ? null : map.get("remark").trim();

		if (mno.isEmpty() || mno == null) {
			throw new MyException("000000", "药品编号不能为空!");
		}

		if (cname.isEmpty() || cname == null) {
			throw new MyException("000000", "顾客名称不能为空!");
		}

		Client client = clientService.query("cname", cname);
		if (client == null) {
			throw new MyException("000000", "顾客不存在，请先增加！");
		}

		Medicine medicine = medicineService.query("mno", mno);
		if (medicine == null) {
			throw new MyException("000000", "药品编号不存在！");
		}

		if (medicine.getStock() < number) {
			throw new MyException("000000", "药品库存不足！");
		}

		User user = (User) session.getAttribute("user");
		if (user == null) {
			throw new MyException("000000", "用戶获取错误");
		}

		Timestamp createTime = new Timestamp(System.currentTimeMillis());

		Records records = new Records();
		records.setCid(client.getId());
		records.setUid(user.getId());
		records.setMno(mno);
		records.setNumber(number);
		records.setSymptom(symptom);
		records.setRemark(remark);
		records.setCreate_time(createTime);

		Boolean addRes = recordsService.addRecords(records);
		Integer resV = medicineService.update(medicine.getId(), "stock", medicine.getStock() - number, createTime);
		if (resV != 1) {
			throw new MyException("000000", "购买记录添加成功，但药品库存更新失败！");
		}
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
	@RequestMapping(value = "/recordsList.do")
	public Map<String, Object> list(HttpServletRequest request) throws MyException {
		Integer page = Integer.parseInt(request.getParameter("page").trim());
		Integer limit = Integer.parseInt(request.getParameter("limit").trim());
		String mno = request.getParameter("mno");
		String uname = request.getParameter("uname");
		String cname = request.getParameter("cname");
		String symptom = request.getParameter("symptom");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if ((startTime != null && endTime == null) || startTime == null && endTime != null) {
			throw new MyException("000000", "时间范围选择有错");
		}

		Map<String, Object> condition = new HashMap<>();
		if (mno != null && mno != "") {
			condition.put("mno", mno.trim());
		}
		if (uname != null && uname != "") {
			uname = uname.trim();
			User user = userService.query("username", uname);
			if (user != null) {
				condition.put("uid", user.getId());
			}
		}
		if (cname != null && cname != "") {
			cname = cname.trim();
			Client client = clientService.query("cname", cname);
			if (client != null) {
				condition.put("cid", client.getId());
			}
		}
		if (symptom != null && symptom != "") {
			condition.put("symptom", symptom.trim());
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
		Map<String, Object> records = recordsService.queryRecordsList(condition, page, limit);
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("data", records);
		return res;
	}

	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/updateRecords.do")
	public Map<String, Object> update(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("mno") && !map.containsKey("uname")) {
			throw new MyException("20002", "信息获取失败！");
		}
		Integer id = Integer.parseInt(map.get("id").trim());
		String uname = map.get("uname").trim();
		String mno = map.get("mno").trim();
		String remark = map.get("remark") == null ? null : map.get("remark").trim();

		if (uname.isEmpty() || uname == null) {
			throw new MyException("000000", "用户名不能为空!");
		}
		if (mno.isEmpty() || mno == null) {
			throw new MyException("000000", "电话不能为空!");
		}

		User user = userService.query("username", uname);
		if (user == null) {
			throw new MyException("000000", "经办人不存在!");
		}

		Medicine medicine = medicineService.query("mno", mno);
		if (medicine == null) {
			throw new MyException("000000", "药品编号不存在!");
		}

		Timestamp update_time = new Timestamp(System.currentTimeMillis());

		Integer resV = recordsService.updateRecords(id, user.getId(), mno, remark, update_time);

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
	 * 顾客删除
	 * 
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/deleteRecords.do")
	public Map<String, Object> delete() throws MyException {
		if (request.getParameter("id") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		Records info = recordsService.query("id", id);
		if (info == null) {
			throw new MyException("000000", "购买记录不存在！");
		}
		Timestamp delete_time = new Timestamp(System.currentTimeMillis());
		Integer resV = recordsService.deleteRecords(id, delete_time);
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
	@RequestMapping(value = "/batchDeleteRecords.do")
	public Map<String, Object> batchDeleteRecords() throws MyException {
		if (request.getParameter("ids") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		String str = request.getParameter("ids").trim();
		if(str.isEmpty()) {
			throw new MyException("000000", "请选择要删除的数据！");
		}
		String[] ids = str.split(",");
		for (int i = 0; i < ids.length; i++) {
			Records records = recordsService.query("id", ids[i]);
			if (records == null) {
				throw new MyException("000000", "id为" + ids[i] + "的购买记录不存在！");
			}
			Timestamp delete_time = new Timestamp(System.currentTimeMillis());
			Integer resV = recordsService.deleteRecords(records.getId(), delete_time);
			if (resV != 1) {
				throw new MyException("000000", "id为" + records.getId() + "的购买记录删除失败！");
			}
		}
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		return res;
	}

}
