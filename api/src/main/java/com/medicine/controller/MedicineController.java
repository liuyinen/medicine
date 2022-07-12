package com.medicine.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.common.controller.IsCheckUserLogin;
import com.common.exception.MyException;
import com.medicine.entity.Medicine;
import com.medicine.service.MedicineService;
import com.records.entity.Records;
import com.records.service.RecordsService;

@RestController("medicineController")
@RequestMapping(value = "/")
public class MedicineController {
	@Autowired
	private MedicineService medicineService;
	@Autowired
	private RecordsService recordsService;
	@Resource
	private HttpServletRequest request;

	@ResponseBody
	@RequestMapping(value = "/createMedicine.do")
	public Map<String, Object> create(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("mno") && !map.containsKey("mname") && !map.containsKey("mmode")
				&& !map.containsKey("mefficacy")) {
			throw new MyException("00000", "信息获取错误！");
		}
		String mno = map.get("mno").trim();
		String mname = map.get("mname").trim();
		String stock = map.get("stock") == null ? null : map.get("stock").trim();
		String mmode = map.get("mmode").trim();
		String mefficacy = map.get("mefficacy") == null ? null : map.get("mefficacy").trim();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());

		Medicine Info1 = medicineService.query("mno", mno);
		if (Info1 != null) {
			throw new MyException("00000", "药品编号已存在！");
		}

		Medicine Info2 = medicineService.query("mname", mname);
		if (Info2 != null) {
			throw new MyException("00000", "药品编号已存在！");
		}

		Medicine medicine = new Medicine();

		medicine.setMno(mno);
		medicine.setMname(mname);
		medicine.setStock(Integer.parseInt(stock));
		medicine.setMmode(mmode);
		medicine.setMefficacy(mefficacy);
		medicine.setCreate_time(createTime);

		Boolean addRes = medicineService.addMedicine(medicine);
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
	 * 药品列表
	 * 
	 * @param request
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/medicineList.do")
	public Map<String, Object> list(HttpServletRequest request) throws MyException {
		Integer page = Integer.parseInt(request.getParameter("page").trim());
		Integer limit = Integer.parseInt(request.getParameter("limit").trim());
		String mno = request.getParameter("mno");
		String mname = request.getParameter("mname");
		String mmode = request.getParameter("mmode");
		String mefficacy = request.getParameter("mefficacy");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if ((startTime != null && endTime == null) || startTime == null && endTime != null) {
			throw new MyException("000000", "时间范围选择有错");
		}

		Map<String, Object> condition = new HashMap<>();
		if (mno != null && mno != "") {
			condition.put("mno", mno.trim());
		}
		if (mname != null && mname != "") {
			condition.put("mname", mname.trim());
		}
		if (mmode != null && mmode != "") {
			condition.put("mmode", mmode.trim());
		}
		if (mefficacy != null && mefficacy != "") {
			condition.put("mefficacy", mefficacy.trim());
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
		Map<String, Object> users = medicineService.queryMedicineList(condition, page, limit);
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("data", users);
		return res;
	}

	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/updateMedicine.do")
	public Map<String, Object> update(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("mno") && !map.containsKey("mname") && !map.containsKey("stock")
				&& !map.containsKey("mnode") && !map.containsKey("mefficacy")) {
			throw new MyException("20002", "信息获取失败！");
		}

		Integer id = Integer.parseInt(map.get("id").trim());
		String mno = map.get("mno");
		String mname = map.get("mname");
		String stock = map.get("stock");
		String mmode = map.get("mmode");
		String mefficacy = map.get("mefficacy");
		Timestamp update_time = new Timestamp(System.currentTimeMillis());

		if (!Pattern.matches("[0-9]*", stock)) {
			throw new MyException("000000", "非法字符!");
		}

		if (mno.isEmpty() || mno == null) {
			throw new MyException("000000", "药品编号不能为空!");
		}

		if (mname.isEmpty() || mname == null) {
			throw new MyException("000000", "药品名不能为空!");
		}

		Medicine Info2 = medicineService.query("mno", mno);
		if (Info2 != null && Info2.getId() != id) {
			throw new MyException("00000", "药品编号已存在！");
		}

		Medicine Info1 = medicineService.query("mname", mname);
		if (Info1 != null && Info1.getId() != id) {
			throw new MyException("00000", "药品名已存在！");
		}

		Integer resV = medicineService.updateMedicine(id, mno.trim(), mname.trim(), Integer.parseInt(stock),
				Integer.parseInt(mmode), mefficacy.trim(), update_time);

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

	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/deleteMedicine.do")
	public Map<String, Object> delete() throws MyException {
		if (request.getParameter("id") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		Integer id = Integer.parseInt(request.getParameter("id").trim());

		Medicine medicine = medicineService.query("id", id);
		if (medicine != null && medicine.getId() != id) {
			throw new MyException("00000", "药品不存在！");
		}

		Records records = recordsService.query("mno", medicine.getMno());
		if (records != null) {
			throw new MyException("000000", medicine.getMname() + "已有顾客购买，不能删除！");
		}

		Timestamp delete_time = new Timestamp(System.currentTimeMillis());
		Integer resV = medicineService.deleteMedicine(id, delete_time);
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
	 * 批量删除
	 * 
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/batchDeleteMedicine.do")
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
			Medicine medicine = medicineService.query("id", ids[i]);
			if (medicine == null) {
				throw new MyException("000000", "id为" + ids[i] + "的药品不存在！");
			}			
	
			Records records = recordsService.query("mno", medicine.getMno());
			if (records != null) {
				throw new MyException("000000", medicine.getMname() + "已有顾客购买，不能删除！");
			}
			Timestamp delete_time = new Timestamp(System.currentTimeMillis());
			Integer resV = medicineService.deleteMedicine(medicine.getId(), delete_time);
			if (resV != 1) {
				throw new MyException("000000", medicine.getMname() + "删除失败！");
			}
		}
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		return res;
	}
}
