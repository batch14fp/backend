package com.lawencon.community.controller;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.lawencon.community.pojo.activity.PojoActivityReqInsert;
import com.lawencon.community.pojo.activity.PojoActivityReqUpdate;
import com.lawencon.community.pojo.activity.PojoActivityRes;
import com.lawencon.community.pojo.payment.PojoUserPaymentReqUpdate;
import com.lawencon.community.pojo.report.PojoReportActivityMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberRes;
import com.lawencon.community.service.ActivityService;
import com.lawencon.community.service.PaginationService;
import com.lawencon.community.service.PaymentService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("activities")
public class ActivityController {
	@Autowired
	private JasperUtil jasperUtil;

	private ActivityService activityService;
	private PaginationService paginationService;
	private PaymentService paymentService;

	public ActivityController(final PaginationService paginationService, final PaymentService paymentService,
			final ActivityService activityService) {
		this.activityService = activityService;
		this.paginationService = paginationService;
		this.paymentService = paymentService;
	}

	@GetMapping
	public ResponseEntity<List<PojoActivityRes>> getData(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		int offset = (page - 1) * size;
		final List<PojoActivityRes> dataList = activityService.getAll(offset, size);
		int totalCount = activityService.getTotalCount();
		int pageCount = paginationService.getPageCount(totalCount, size);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Total-Pages", String.valueOf(pageCount));
		return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	}

	@GetMapping("/lowest")
	public ResponseEntity<List<PojoActivityRes>> getDataByLowestPrice(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		final List<PojoActivityRes> dataList = activityService.getAllByLowestPrice(page, size);
		int totalCount = activityService.getTotalCount();
		int pageCount = paginationService.getPageCount(totalCount, size);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Total-Pages", String.valueOf(pageCount));
		return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	}

	@GetMapping("/highest")
	public ResponseEntity<List<PojoActivityRes>> getDataByHighestPrice(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		final List<PojoActivityRes> dataList = activityService.getAllByHighestPrice(page, size);
		int totalCount = activityService.getTotalCount();
		int pageCount = paginationService.getPageCount(totalCount, size);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(totalCount));
		headers.add("X-Total-Pages", String.valueOf(pageCount));
		return new ResponseEntity<>(dataList, headers, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PojoActivityRes> getActivity(@PathVariable("id") String id) {
		PojoActivityRes resGet = activityService.getById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insertActivity(@RequestBody PojoActivityReqInsert data) {
		PojoInsertRes resGet = activityService.save(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateActivity(@RequestBody PojoActivityReqUpdate data) {
		PojoUpdateRes resGet = activityService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PojoRes> deleteActivity(@PathVariable("id") String id) {
		PojoRes resDelete = activityService.deleteById(id);
		return new ResponseEntity<>(resDelete, HttpStatus.OK);
	}

	@GetMapping("/filter")
	public ResponseEntity<List<PojoActivityRes>> getListActivityByCategoryAndType(@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam(required = false) String categoryCode, @RequestParam(required = false) String typeCode) {
		try {
			List<PojoActivityRes> activities = activityService.getListActivityByCategoryAndType(categoryCode,
					typeCode,page,size);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}


//	@GetMapping("/report")
//	public ResponseEntity<List<PojoReportActivityMemberRes>> getAllByDateRange(@RequestParam String startDate,
//			@RequestParam String endDate, @RequestParam(required = false) Integer offset,
//			@RequestParam(required = false) Integer limit) {
//		List<PojoReportActivityMemberRes> activities = activityService.getMemberReport(
//				Date.valueOf(startDate).toLocalDate(), Date.valueOf(endDate).toLocalDate(), offset, limit);
//		return ResponseEntity.ok(activities);
//	}

	@GetMapping("/report/test")
	public ResponseEntity<byte[]> generateReport( @RequestParam String id, @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
		List<PojoReportActivityMemberRes> data = activityService.getMemberReport(id, Date.valueOf(startDate).toLocalDate(),
				Date.valueOf(endDate).toLocalDate(), offset, limit);
		Map<String, Object> params = new HashMap<>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		byte[] pdfBytes;
		try {
			pdfBytes = jasperUtil.responseToByteArray(data, params, "report");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "report.pdf");
		headers.setContentLength(pdfBytes.length);
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
	
	

	@GetMapping("/member/report/incomes")
	public ResponseEntity<List<PojoReportIncomesMemberRes>> getMemberReport( @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) String typeCode) {
		try {
			List<PojoReportIncomesMemberRes> activities = activityService.getMemberIncomesReport(Date.valueOf(startDate).toLocalDate(),Date.valueOf(endDate).toLocalDate(), typeCode);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	
	

	@PutMapping("/payment")
	public ResponseEntity<PojoRes> updateByUser(@RequestBody PojoUserPaymentReqUpdate data) {
		PojoRes resGet = paymentService.updateByUser(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}

}
