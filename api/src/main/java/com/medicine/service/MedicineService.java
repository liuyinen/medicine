package com.medicine.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.dao.MedicineDAO;
import com.medicine.entity.Medicine;
import com.user.entity.User;

@Service("medicineService")
public class MedicineService {
	@Autowired
	private MedicineDAO medicineDAO;

	/**
	 * 药品添加
	 * 
	 * @param medicine
	 * @return
	 */
	public boolean addMedicine(Medicine medicine) {
		return medicineDAO.addMedicine(medicine);
	}

	/**
	 * 药品查询（单条件）
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Medicine query(Object key, Object value) {
		return medicineDAO.query(key, value);
	}

	/**
	 * 药品查询（多条件）
	 * 
	 * @param condition
	 * @return
	 */
	public Medicine queryMedicine(Map<String, Object> condition) {
		return medicineDAO.queryMedicine(condition);
	}

	/**
	 * 查询药品列表
	 * 
	 * @param condition
	 * @param page
	 * @param limit
	 * @return
	 */
	public Map<String, Object> queryMedicineList(Map<String, Object> condition, Integer page, Integer limit) {
		return medicineDAO.queryMedicineList(condition, page, limit);
	}

	/**
	 * 药品更新（单记录）
	 * @param id
	 * @param key
	 * @param value
	 * @param update_time
	 * @return
	 */
	public Integer update(Integer id, Object key, Object value, Timestamp update_time) {
		return medicineDAO.update(id, key, value, update_time);
	}

	/**
	 * 药品更新
	 * 
	 * @param id
	 * @param mno
	 * @param mname
	 * @param mmode
	 * @param mefficacy
	 * @param update_time
	 * @return
	 */
	public Integer updateMedicine(Integer id, String mno, String mname, Integer stock, Integer mmode, String mefficacy,
			Timestamp update_time) {
		return medicineDAO.updateMedicine(id, mno, mname, stock, mmode, mefficacy, update_time);
	}

	/**
	 * 用户删除
	 * 
	 * @param id
	 * @param delete_time
	 * @return
	 */
	public Integer deleteMedicine(Integer id, Timestamp delete_time) {
		return medicineDAO.deleteMedicine(id, delete_time);
	}

	public Integer total() {
		return medicineDAO.total();
	}
	
	public List dataCount() {
		return medicineDAO.dataCount();
	}
	
	public List<Medicine> getNewListse() {
		return medicineDAO.getNewLists();
	}
}
