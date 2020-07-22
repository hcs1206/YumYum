package com.redbeans.yumyum.service;

import java.util.List;

import com.redbeans.yumyum.dto.Review;

public interface ReviewService {
	
	public List<Review> findReviewById(String id) throws Exception;
	public List<Review> findReviewByName(String UserName) throws Exception;
	

}
