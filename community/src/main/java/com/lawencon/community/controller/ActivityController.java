package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.activity.PojoActivityInsertReq;
import com.lawencon.community.pojo.activity.PojoActivityUpdateReq;
import com.lawencon.community.pojo.activity.PojoResGetActivity;
import com.lawencon.community.pojo.payment.PojoUserPaymentUpdateReq;
import com.lawencon.community.service.ActivityService;
import com.lawencon.community.service.PaginationService;
import com.lawencon.community.service.PaymentService;

@RestController
@RequestMapping("activities")
public class ActivityController {
	private ActivityService activityService;
	private PaginationService paginationService;
	private PaymentService paymentService;

	public ActivityController(final PaginationService paginationService, final PaymentService paymentService,final ActivityService activityService) {
		this.activityService = activityService;
		this.paginationService = paginationService;
		this.paymentService = paymentService;
	}

	@GetMapping
	public ResponseEntity<List<PojoResGetActivity>> getData(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		int offset = (page - 1) * size;
		final List<PojoResGetActivity> dataList = activityService.getAll(offset, size);
		int totalCount = activityService.getTotalCount();
		int pageCount = paginationService.getPageCount(totalCount, size);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Total-Pages", String.valueOf(pageCount));
		return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	}

	@GetMapping("/lowest")
	public ResponseEntity<List<PojoResGetActivity>> getDataByLowestPrice(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		int offset = (page - 1) * size;
		final List<PojoResGetActivity> dataList = activityService.getAllByLowestPrice(offset, size);
		int totalCount = activityService.getTotalCount();
		int pageCount = paginationService.getPageCount(totalCount, size);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Total-Pages", String.valueOf(pageCount));
		return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	}
	
	@GetMapping("/highest")
	public ResponseEntity<List<PojoResGetActivity>> getDataByHighestPrice(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		int offset = (page - 1) * size;
		final List<PojoResGetActivity> dataList = activityService.getAllByHighestPrice(offset, size);
		int totalCount = activityService.getTotalCount();
		int pageCount = paginationService.getPageCount(totalCount, size);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Total-Pages", String.valueOf(pageCount));
		return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PojoResGetActivity> getActivity(@PathVariable("id") String id) {
		PojoResGetActivity resGet = activityService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertActivity(@RequestBody PojoActivityInsertReq data) {
		PojoInsertRes resGet = activityService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateActivity(@RequestBody PojoActivityUpdateReq data) {
		PojoUpdateRes resGet = activityService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteActivity(@PathVariable("id") String id) {
		PojoRes resDelete = activityService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

	@GetMapping("/filter")
	public ResponseEntity<List<PojoResGetActivity>> getListActivityByCategoryAndType(
			@RequestParam(required = false) String categoryCode, @RequestParam(required = false) String typeCode) {
		try {
			List<PojoResGetActivity> activities = activityService.getListActivityByCategoryAndType(categoryCode,
					typeCode);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@PutMapping("/payment")
	public ResponseEntity<PojoRes> updateByUser(@RequestBody PojoUserPaymentUpdateReq data){
		PojoRes resGet = paymentService.updateByUser(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
}
