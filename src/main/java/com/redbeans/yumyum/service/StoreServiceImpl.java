package com.redbeans.yumyum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redbeans.yumyum.dao.StoreDAO;
import com.redbeans.yumyum.dto.Store;

@Service
public class StoreServiceImpl implements StoreService {
	
    @Autowired
	private StoreDAO storeDao;

    @Override
	public List<Store> findAllStores() throws Exception {
		return storeDao.findAllStores();
	}

	@Override
	public List<Store> findStoreByName(String name) throws Exception {
		return storeDao.findStoreByName(name);
	}

	@Override
	public Store findStoreDetail(String id) throws Exception {
		return storeDao.findStoreDetail(id);
	}
    
//  	@Override
//	public int addEmployee(Yumyum emp) throws Exception {
//		return employeeDao.addEmployee(emp);
//	}
//
//
//	@Override
//	public Yumyum findEmployeeById(int id) throws Exception {
//		return employeeDao.findEmployeeById(id);
//	}
//
//	@Override
//	@Transactional
//	public boolean updateEmployee(Yumyum emp) throws Exception {
//		return employeeDao.updateEmployee(emp);
//	}
//
//	@Override
//	@Transactional
//	public boolean deleteEmployee(int id) throws Exception {
//		return employeeDao.deleteEmployee(id);
//	}

	
}
