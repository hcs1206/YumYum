package com.redbeans.yumyum.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.redbeans.yumyum.dto.Detail;
import com.redbeans.yumyum.dto.Store;

@Repository
public class StoreDAOImpl implements StoreDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Store> findAllStores() throws Exception {
		return sqlSession.selectList("m_store.findAllStores");
	}

	@Override
	public List<Store> findStoreByName(String name) throws Exception {

		return sqlSession.selectList("m_store.findStoreByName", "%" + name + "%");
	}
	
	@Override
	public Store findStoreById(String id) throws Exception {
		return sqlSession.selectOne("m_store.findStoreById", id);
	}

	@Override
	public Detail findStoreDetail(String id) throws Exception {
		return sqlSession.selectOne("detail.findStoreDetail", id);
	}

}
