package com.redbeans.yumyum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.redbeans.yumyum.dto.Review;
import com.redbeans.yumyum.dto.Store;
import com.redbeans.yumyum.service.ReviewService;
import com.redbeans.yumyum.service.StoreService;

//http://localhost:8080/yumyum/swagger-ui.html
@RestController
@RequestMapping("/api")
public class StoreController {

	public static final Logger logger = LoggerFactory.getLogger(StoreController.class);

	@Autowired
	private StoreService storeService;

	@Autowired
	private ReviewService reviewService;

	@RequestMapping(value = "/store/all", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> findAllStores() throws Exception {
		logger.info("---------------findAllStores-----------------------------" + new Date());
		List<Store> stores = storeService.findAllStores();
		if (stores.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}

	@RequestMapping(value = "/store/find/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> findStoreByName(@PathVariable String name) throws Exception {
		logger.info("------------------StoreByName-----------------------------" + new Date());

		List<Store> stores = storeService.findStoreByName(name);
		if (stores.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}

	@RequestMapping(value = "/store/detail/{id}", method = RequestMethod.GET)
	public ResponseEntity<Store> findStoreDetail(@PathVariable String id) throws Exception {
		logger.info("------------------findDetailById-----------------------" + new Date());
		Store store = storeService.findStoreDetail(id);
		if (store == null || store.getId() == "") {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Store>(store, HttpStatus.OK);
	}

	@RequestMapping(value = "/recommendStores/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Store>> recommendStores(@PathVariable String id) throws Exception {
		logger.info("---------------recommendStores-----------------------------" + new Date());
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		List<Store> stores = new ArrayList<Store>();

		// 음식점에 적힌 전체 리뷰 가져오기
		List<Review> Reviews = reviewService.findReviewById(id);
		if (Reviews.size() > 0) {
			for (Review r : Reviews) {
				String userName = r.getUserName();
				// 현재 음식점에 리뷰를 작성한 사용자가 다른 음식점에 작성한 리뷰들을 가져옴
				List<Review> uReviews = reviewService.findReviewByName(userName);
				for (Review r2 : uReviews) {
					// 작성한 다른 가게 만족도가 높을시 연관도를 높힘
					if (r2.getScore() >= 4 || r2.getVisitCount() > 1) {
						if (!hm.containsKey(r2.getId())) {
							hm.put(r2.getId(), 1);
						} else {
							hm.put(r2.getId(), hm.get(r2.getId()) + 1);
						}
					}
				}
			}

			List<String> keySetList = new ArrayList<>(hm.keySet());

			Collections.sort(keySetList, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return hm.get(o2).compareTo(hm.get(o1));
				}
			});

			// 가장 유사도가 높은 음식점 순으로 최대 50개를 선정
			for (String key : keySetList) {
				stores.add(storeService.findStoreDetail(key));
				if (stores.size() > 50) {
					break;
				}
			}
		}

		if (stores.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);

	}

}
