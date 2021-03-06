package com.redbeans.yumyum.service;

import java.util.List;

import com.redbeans.yumyum.dto.Detail;
import com.redbeans.yumyum.dto.Store;

public interface StoreService {
	public List<Store> findAllStores() throws Exception;

	public List<Store> findStoreByName(String name) throws Exception;
	
	public Store findStoreById(String id) throws Exception;

	public Detail findStoreDetail(String id) throws Exception;

}
