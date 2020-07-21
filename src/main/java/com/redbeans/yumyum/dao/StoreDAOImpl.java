package com.redbeans.yumyum.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		
		return sqlSession.selectList("m_store.findStoreByName", "%"+name+"%");
	}

	@Override
	public Store findStoreDetail(String id) throws Exception {
		return sqlSession.selectOne("m_store.findStoreDetail", id);
	}

//	@Override
//	public int addEmployee(Yumyum emp) throws Exception {
//		return sqlSession.insert("s_emp.addEmployee", emp);
//	}
//
//	@Override
//	public Yumyum findEmployeeById(int id) throws Exception {
//		System.out.println(">>>>" + sqlSession.selectOne("s_emp.findEmployeeById", id));
//		return sqlSession.selectOne("s_emp.findEmployeeById", id);
//	}
//
//	@Override
//	public boolean updateEmployee(Yumyum emp) throws Exception {
//		sqlSession.update("s_emp.updateEmployee", emp);
//		return true;
//	}
//
//	@Override
//	public boolean deleteEmployee(int id) throws Exception {
//		sqlSession.delete("s_emp.deleteEmployee", id);
//		return true;
//	}

}
