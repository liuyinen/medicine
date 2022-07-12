package com.client.controller;

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

import com.client.entity.Client;
import com.client.service.ClientService;
import com.common.controller.IsCheckUserLogin;
import com.common.exception.MyException;
import com.records.entity.Records;
import com.records.service.RecordsService;

//@controller用于告诉服务器，这个类是Spring MVC中的控制层，这个类可以接收和处理用户请求
@RestController("clientController")
@RequestMapping(value = "/")
public class ClientController {
	@Autowired
	private ClientService clientService;
	@Resource
	private HttpServletRequest request;
	@Autowired
	private RecordsService recordsService;

	/**
	 * 顾客注册
	 * 
	 * @param map
	 * @return
	 * @throws MyException
	 */
	@ResponseBody
	@RequestMapping(value = "/createClient.do")
	public Map<String, Object> create(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("cname") && !map.containsKey("cphone")) {
			throw new MyException("00000", "信息获取错误！");
		}
		String cname = map.get("cname").trim();
		String csex = map.get("csex") == null ? null : map.get("csex").trim();
		String cage = map.get("cage") == null ? null : map.get("cage").trim();
		String cphone = map.get("cphone").trim();
		String caddress = map.get("caddress") == null ? null : map.get("caddress").trim();

		if (cname.isEmpty() || cname == null) {
			throw new MyException("000000", "顾客名不能为空!");
		}

		if (cphone.isEmpty() || cphone == null) {
			throw new MyException("000000", "电话不能为空!");
		}

		if (!Pattern.matches("[0-9]*", cphone) || !Pattern.matches("[0-9]*", cage)) {
			throw new MyException("000000", "非法字符!");
		}

		Timestamp createTime = new Timestamp(System.currentTimeMillis());

		Client info1 = clientService.query("cname", cname);
		if (info1 != null) {
			throw new MyException("000000", "客戶已注册！");
		}

		Client info2 = clientService.query("cphone", cphone);
		if (info2 != null) {
			throw new MyException("000000", "手机号已注册！");
		}

		Client client = new Client();
		client.setCname(cname);
		client.setCsex(csex);
		client.setCage(Integer.parseInt(cage));
		client.setCphone(cphone);
		client.setCaddress(caddress);
		client.setCreate_time(createTime);

		Boolean addRes = clientService.addClient(client);
		Map<String, Object> res = new HashMap<>();
		if (addRes == true) {
			res.put("code", 20000);
			res.put("message", "成功");
		} else {
			res.put("code", 00000);
			res.put("message", "失敗");
		}
		return res;
	}

	/**
	 * 顾客列表
	 * 
	 * @param request
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/clientList.do")
	public Map<String, Object> list(HttpServletRequest request) throws MyException {
		Integer page = Integer.parseInt(request.getParameter("page").trim());
		Integer limit = Integer.parseInt(request.getParameter("limit").trim());
		String cname = request.getParameter("cname");
		String cphone = request.getParameter("cphone");
		String caddress = request.getParameter("caddress");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if ((startTime != null && endTime == null) || startTime == null && endTime != null) {
			throw new MyException("000000", "时间范围选择有错");
		}

		Map<String, Object> condition = new HashMap<>();
		if (cname != null && cname != "") {
			condition.put("cname", cname.trim());
		}
		if (cphone != null && cphone != "") {
			condition.put("cphone", cphone.trim());
		}
		if (caddress != null && caddress != "") {
			condition.put("cphone", caddress.trim());
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

		Map<String, Object> clients = clientService.queryClientList(condition, page, limit);
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		res.put("data", clients);
		return res;
	}

	/**
	 * 顾客信息更新
	 * 
	 * @param map
	 * @return
	 * @throws MyException
	 */
	@IsCheckUserLogin(check = true)
	@ResponseBody
	@RequestMapping(value = "/updateClient.do")
	public Map<String, Object> update(@RequestBody Map<String, String> map) throws MyException {
		if (!map.containsKey("id") && !map.containsKey("cname") && !map.containsKey("id") && !map.containsKey("cname")
				&& !map.containsKey("cphone")) {
			throw new MyException("20002", "信息获取失败！");
		}
		Integer id = Integer.parseInt(map.get("id").trim());
		String cname = map.get("cname").trim();
		String csex = map.get("csex") == null ? null : map.get("csex").trim();
		String cage = map.get("cage") == null ? null : map.get("cage").trim();
		String caddress = map.get("caddress") == null ? null : map.get("caddress").trim();
		String cphone = map.get("cphone").trim();
		Timestamp update_time = new Timestamp(System.currentTimeMillis());

		if (cname.isEmpty() || cname == null) {
			throw new MyException("000000", "用户名不能为空!");
		}
		if (cphone.isEmpty() || cphone == null) {
			throw new MyException("000000", "电话不能为空!");
		}

		if (!Pattern.matches("[0-9]*", cphone) || !Pattern.matches("[0-9]*", cage)) {
			throw new MyException("000000", "非法字符!");
		}

		Client info1 = clientService.query("cname", cname);
		if (info1 != null && info1.getId() != id) {
			throw new MyException("20001", "顾客名已注册！");
		}
		
		Client info2 = clientService.query("cphone", cphone);
		if (info2 != null && info2.getId() != id) {
			throw new MyException("000000", "手机号已注册！");
		}
		
		Integer resV = clientService.updateClient(id, cname, csex, Integer.parseInt(cage), caddress, cphone,
				update_time);

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
	@RequestMapping(value = "/deleteClient.do")
	public Map<String, Object> delete() throws MyException {
		if (request.getParameter("id") == null) {
			throw new MyException("000000", "参数获取错误！");
		}
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		Client client = clientService.query("id", id);
		if (client == null) {
			throw new MyException("000000", "顾客不存在！");
		}
		Records records = recordsService.query("cid", client.getId());
		if (records != null) {
			throw new MyException("000000", client.getCname() + "存在购买记录，不能删除！");
		}
		Timestamp delete_time = new Timestamp(System.currentTimeMillis());
		Integer resV = clientService.deleteClient(id, delete_time);
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
	@RequestMapping(value = "/batchDeleteClient.do")
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
			Client client = clientService.query("id", ids[i]);
			if (client == null) {
				throw new MyException("000000", "id为" + ids[i] + "的客戶不存在！");
			}
			Records records = recordsService.query("cid", client.getId());
			if (records != null) {
				throw new MyException("000000", client.getCname() + "存在购买记录，不能删除！");
			}
			Timestamp delete_time = new Timestamp(System.currentTimeMillis());
			Integer resV = clientService.deleteClient(client.getId(), delete_time);
			if (resV != 1) {
				throw new MyException("000000", client.getCname() + "删除失败！");
			}
		}
		Map<String, Object> res = new HashMap<>();
		res.put("code", 20000);
		res.put("message", "成功");
		return res;
	}
}
