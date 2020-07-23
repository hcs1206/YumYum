package com.redbeans.yumyum.dao;

import java.util.List;

import com.redbeans.yumyum.dto.Review;

public interface ReviewDAO {

	public List<Review> findReviewById(String id) throws Exception;

	public List<Review> findReviewByName(String UserName) throws Exception;
}
