package com.redbeans.yumyum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbeans.yumyum.dao.StoreDAO;
import com.redbeans.yumyum.dto.Detail;
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
	public Detail findStoreDetail(String id) throws Exception {
		return storeDao.findStoreDetail(id);
	}

	@Override
	public Store findStoreById(String id) throws Exception {
		return storeDao.findStoreById(id);

	}

}
