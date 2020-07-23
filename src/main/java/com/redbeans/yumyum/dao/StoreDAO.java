package com.redbeans.yumyum.dao;

import java.util.List;

import com.redbeans.yumyum.dto.Store;

public interface StoreDAO {

	public List<Store> findAllStores() throws Exception;

	public List<Store> findStoreByName(String name) throws Exception;

	public Store findStoreDetail(String id) throws Exception;
}
