package com.client.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.client.dao.ClientDAO;
import com.client.entity.Client;
import com.medicine.entity.Medicine;

//@Service用于标注业务层组件
@Service("clientService")
public class ClientService {
	@Autowired
	private ClientDAO clientDAO;

	/**
	 * 顾客添加
	 * 
	 * @param user
	 * @return
	 */
	public boolean addClient(Client client) {
		return clientDAO.addClient(client);
	}

	/**
	 * 顾客查询（单条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Client query(Object key, Object value) {
		return clientDAO.query(key, value);
	}

	/**
	 * 顾客查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Client queryClient(Map<String, Object> condition) {
		return clientDAO.queryClient(condition);
	}

	/**
	 * 查询顾客列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryClientList(Map<String, Object> condition, Integer page, Integer limit) {
		return clientDAO.queryClientList(condition, page, limit);
	}

	/**
	 * 客戶更新
	 * 
	 * @param id
	 * @param cname
	 * @param csex
	 * @param cage
	 * @param caddress
	 * @param cphone
	 * @param update_time
	 * @return
	 */
	public Integer updateClient(Integer id, String cname, String csex, Integer cage, String caddress, String cphone,
			Timestamp update_time) {
		return clientDAO.updateClient(id, cname, csex, cage, caddress, cphone, update_time);
	}

	/**
	 * 客戶刪除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteClient(Integer id, Timestamp delete_time) {
		return clientDAO.deleteClient(id, delete_time);
	}

	public Integer total() {
		return clientDAO.total();
	}
	
	public List dataCount() {
		return clientDAO.dataCount();
	}
	
	public List<Client> getNewListse() {
		return clientDAO.getNewLists();
	}
}
