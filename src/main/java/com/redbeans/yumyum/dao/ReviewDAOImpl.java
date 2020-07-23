package com.redbeans.yumyum.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.redbeans.yumyum.dto.Review;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Review> findReviewById(String id) throws Exception {
		return sqlSession.selectList("review.findReviewById", id);
	}

	@Override
	public List<Review> findReviewByName(String UserName) throws Exception {
		return sqlSession.selectList("review.findReviewByName", UserName);
	}

}
