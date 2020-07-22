package com.redbeans.yumyum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redbeans.yumyum.dao.ReviewDAO;
import com.redbeans.yumyum.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDAO reviewDao;
	
	@Override
	public List<Review> findReviewById(String id) throws Exception {
		return reviewDao.findReviewById(id);
	}

	@Override
	public List<Review> findReviewByName(String UserName) throws Exception {
		return reviewDao.findReviewByName(UserName);
	}

}
