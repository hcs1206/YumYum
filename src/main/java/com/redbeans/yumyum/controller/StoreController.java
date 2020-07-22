package com.redbeans.yumyum.controller;

import java.util.ArrayList;
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
		List<Review> Reviews = reviewService.findReviewById(id);
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		List<Store> stores = new ArrayList<Store>();

		if (Reviews.size() > 0) {
			for (Review r : Reviews) {
				String userName = r.getUserName();
				List<Review> uReviews = reviewService.findReviewByName(userName);
				for (Review r2 : uReviews) {
					if (!hm.containsKey(r2.getId())) {
						hm.put(r2.getId(), 1);
					} else {
						hm.put(r2.getId(), hm.get(r2.getId()) + 1);
					}
				}

			}

			for (String key : hm.keySet()) {

				int value = hm.get(key);
				if (stores.size() > 50) {
					break;
				}

				if (value > 50) {
					stores.add(storeService.findStoreDetail(key));
				}

			}
		}

		System.out.println(stores.size());

		if (stores.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);

	}

//	@RequestMapping(value = "/store/insert", method = RequestMethod.POST)
//	public ResponseEntity<String> addEmployee(@RequestBody m_store dto) throws Exception {
//		logger.info("5-------------addEmployee-----------------------------" + new Date());
//		logger.info("5-------------addEmployee-----------------------------" + dto);
//		int t = storeService.addEmployee(dto);
//		if (t == 1)
//			return new ResponseEntity<String>("success", HttpStatus.OK);
//		else
//			return new ResponseEntity<String>("fail", HttpStatus.OK);
//	}

//	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
//	public ResponseEntity<Yumyum> findEmployeeById(@PathVariable int id) throws Exception {
//		logger.info("1-------------findEmployeeById-----------------------------" + new Date());
//		Yumyum emp = yumyumService.findEmployeeById(id);
//		if (emp == null || emp.getId() == 0) {
//			return new ResponseEntity(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<Yumyum>(emp, HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/employee", method = RequestMethod.PUT)
//	public ResponseEntity<String> updateEmployee(@RequestBody Yumyum dto) throws Exception {
//		logger.info("1-------------updateEmployee-----------------------------" + new Date());
//		logger.info("1-------------updateEmployee-----------------------------" + dto);
//		boolean total = yumyumService.updateEmployee(dto);
//
//		if (!total) {
//			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<String>("success", HttpStatus.OK);
//	}
//
//	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.DELETE)
//	public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId) throws Exception {
//		logger.info("1-------------deleteEmployee-----------------------------" + new Date());
//		logger.info("1-------------deleteEmployee-----------------------------" + employeeId);
//
//		boolean total = yumyumService.deleteEmployee(employeeId);
//		;
//		if (!total) {
//			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<String>("success", HttpStatus.OK);
//	}

}
