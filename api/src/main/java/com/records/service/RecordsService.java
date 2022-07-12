package com.records.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.records.dao.RecordsDAO;
import com.records.entity.Records;
import com.records.entity.UserVoClientVoRecords;

@Service("recordsService")
public class RecordsService {
	@Autowired
	private RecordsDAO recordsDAO;

	/**
	 * 新增购买记录
	 * 
	 * @param records
	 * @return
	 */
	public boolean addRecords(Records records) {
		return recordsDAO.addRecords(records);
	}

	/**
	 * 购买记录查询（单条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Records query(Object key, Object value) {
		return recordsDAO.query(key, value);
	}

	/**
	 * 购买记录查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Records queryRecords(Map<String, Object> condition) {
		return recordsDAO.queryRecords(condition);
	}

	/**
	 * 查询购买记录列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryRecordsList(Map<String, Object> condition, Integer page, Integer limit) {
		return recordsDAO.queryRecordsList(condition, page, limit);
	}

	/**
	 * 购买记录更新
	 * 
	 * @param id
	 * @param uid
	 * @param mno
	 * @param number
	 * @param remark
	 * @param update_time
	 * @return
	 */
	public Integer updateRecords(Integer id, Integer uid, String mno, String remark, Timestamp update_time) {
		return recordsDAO.updateRecords(id, uid, mno, remark, update_time);
	}

	/**
	 * 购买记录刪除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteRecords(Integer id, Timestamp delete_time) {
		return recordsDAO.deleteRecords(id, delete_time);
	}

	public Integer total() {
		return recordsDAO.total();
	}

	public List dataCount() {
		return recordsDAO.dataCount();
	}
	
	public List<UserVoClientVoRecords> getNewListse() {
		return recordsDAO.getNewLists();
	}
	
	public List getRecordsData() {
		return recordsDAO.getRecordsData();
	}
}
