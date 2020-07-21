package com.redbeans.yumyum.dao;

import java.util.List;

import com.redbeans.yumyum.dto.Store;

public interface StoreDAO {
	
	public List<Store> findAllStores() throws Exception;
	public List<Store> findStoreByName(String name) throws Exception;
//	public Yumyum findEmployeeById(int id) throws Exception;
//	public int addEmployee(Yumyum emp) throws Exception;
//	public boolean updateEmployee(Yumyum emp) throws Exception;
//	public boolean deleteEmployee(int id) throws Exception;
//	
}
