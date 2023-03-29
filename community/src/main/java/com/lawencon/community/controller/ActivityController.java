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
import com.lawencon.community.pojo.activity.PojoUpcomingActivityByTypeRes;
import com.lawencon.community.pojo.payment.PojoUserPaymentReqUpdate;
import com.lawencon.community.pojo.report.PojoReportActivityAdminRes;
import com.lawencon.community.pojo.report.PojoReportActivityAdminResData;
import com.lawencon.community.pojo.report.PojoReportActivityMemberRes;
import com.lawencon.community.pojo.report.PojoReportActivityMemberResData;
import com.lawencon.community.pojo.report.PojoReportCountMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminRes;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminResData;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberResData;
import com.lawencon.community.pojo.voucher.PojoActivityVoucherRes;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedReq;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedRes;
import com.lawencon.community.service.ActivityService;
import com.lawencon.community.service.PaymentService;
import com.lawencon.community.service.VoucherService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("activities")
public class ActivityController {
	@Autowired
	private JasperUtil jasperUtil;

	private ActivityService activityService;
	private PaymentService paymentService;
	private VoucherService voucherService;

	public ActivityController(final  VoucherService voucherService,  final PaymentService paymentService,
			final ActivityService activityService) {
		this.activityService = activityService;
		this.paymentService = paymentService;
		this.voucherService =voucherService;
	}

	@GetMapping
	public ResponseEntity<List<PojoActivityRes>> getData(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		final List<PojoActivityRes> dataList = activityService.getAll(page, size);
		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	@GetMapping("/sort")
	public ResponseEntity<List<PojoActivityRes>> getDataActivity(@RequestParam("page") int page,
			@RequestParam("size") int size,
		    @RequestParam(required = false) String sortType,  
		    @RequestParam(defaultValue = "") String title)  {
		final List<PojoActivityRes> dataList = activityService.getAllBySort(page, size,sortType, title);
		return new ResponseEntity<>(dataList, HttpStatus.OK);
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
			@RequestParam(value="categoryCode", required = false) String categoryCode, @RequestParam(value="typeCode", required = false) String typeCode) {
		try {
			List<PojoActivityRes> activities = activityService.getListActivityByCategoryAndType(categoryCode,
					typeCode,page,size);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	  @GetMapping("/listByCategoryAndType")
	    public ResponseEntity<List<PojoActivityRes>> getListActivityByCategoryAndType(
	            @RequestParam(value = "categoryCodes", required = false)  List<String>categoryCodes,
	    		@RequestParam(value = "typeCode", required = false) String typeCode,
	            @RequestParam(value = "page", defaultValue = "0") int page,
	            @RequestParam(value = "size", defaultValue = "10") int size
	    ) {
	        try {
	            List<PojoActivityRes> activities = activityService.getListActivityByListCategoryAndType(categoryCodes, typeCode, page, size);
	            if (activities == null) {
	            
	                return ResponseEntity.noContent().build();
	            }
	            return ResponseEntity.ok(activities);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }
	  
	  
	  @GetMapping("/upcoming")
	    public ResponseEntity<PojoUpcomingActivityByTypeRes> getUpcomingActivity(
	            @RequestParam(value = "typeCode", required = false) String typeCode,
	            @RequestParam(value = "page", required = false) int page,
	            @RequestParam(value = "size",required = false) int size
	    ) {
	        try {
	        	PojoUpcomingActivityByTypeRes activities = activityService.getUpcomingEvent(page, size, typeCode);
	            if (activities == null) {
	                return ResponseEntity.noContent().build();
	            }
	            return ResponseEntity.ok(activities);
	        } catch (Exception e) {
	        	e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	@GetMapping("member/report")
	public ResponseEntity<PojoReportActivityMemberRes> getAllByDateRange(@RequestParam String startDate,
			@RequestParam String endDate, @RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit) {
		PojoReportActivityMemberRes activities = activityService.getMemberReport(Date.valueOf(startDate).toLocalDate(), Date.valueOf(endDate).toLocalDate(), offset, limit);
		return ResponseEntity.ok(activities);
	}

	@GetMapping("member/report/file")
	public ResponseEntity<byte[]> generateReportFile( @RequestParam String id, @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
		List<PojoReportActivityMemberResData> data = activityService.getMemberReportFile(id, Date.valueOf(startDate).toLocalDate(),
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
	
	@GetMapping("admin/report")
	public ResponseEntity<PojoReportActivityAdminRes> getAllByDateRangeAdmin(@RequestParam String startDate,
			@RequestParam String endDate, @RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit) {
		PojoReportActivityAdminRes activities = activityService.getAdminReport(
				Date.valueOf(startDate).toLocalDate(), Date.valueOf(endDate).toLocalDate(), offset, limit);
		return ResponseEntity.ok(activities);
	}

	@GetMapping("admin/report/file")
	public ResponseEntity<byte[]> generateReportFileAdmin(  @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
		List<PojoReportActivityAdminResData> data = activityService.getAdminReportFile( Date.valueOf(startDate).toLocalDate(),
				Date.valueOf(endDate).toLocalDate());
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
	public ResponseEntity<PojoReportIncomesMemberRes> getMemberReport( @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) String typeCode, @RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit) {
		try {
			PojoReportIncomesMemberRes activities = activityService.getMemberIncomesReport(Date.valueOf(startDate).toLocalDate(),Date.valueOf(endDate).toLocalDate(), typeCode, offset,  limit);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("member/report/incomes/file")
	public ResponseEntity<byte[]> generateReportFileIncomesMember(  @RequestParam String startDate, @RequestParam String endDate,
		 @RequestParam(required = false) String typeCode) {
		List<PojoReportIncomesMemberResData> data = activityService.getMemberIncomesReportFile( Date.valueOf(startDate).toLocalDate(),
				Date.valueOf(endDate).toLocalDate(),typeCode);
		Map<String, Object> params = new HashMap<>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		byte[] pdfBytes;
		try {
			pdfBytes = jasperUtil.responseToByteArray(data, params, "report-income-member");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "report.pdf");
		headers.setContentLength(pdfBytes.length);
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/admin/report/incomes")
	public ResponseEntity<PojoReportIncomesAdminRes> getAdminReports( @RequestParam String startDate, @RequestParam String endDate,
			@RequestParam(required = false) String typeCode, @RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit) {
		try {
			PojoReportIncomesAdminRes activities = activityService.getIncomesReportAdmin(Date.valueOf(startDate).toLocalDate(),Date.valueOf(endDate).toLocalDate(), typeCode, offset, limit);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("admin/report/incomes/file")
	public ResponseEntity<byte[]> generateReportFileIncomesAdmin(  @RequestParam String startDate, @RequestParam String endDate,
		 @RequestParam(required = false) String typeCode) {
		List<PojoReportIncomesAdminResData> data = activityService.getIncomesReportAdminFile( Date.valueOf(startDate).toLocalDate(),
				Date.valueOf(endDate).toLocalDate(),typeCode);
		Map<String, Object> params = new HashMap<>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		byte[] pdfBytes;
		try {
			pdfBytes = jasperUtil.responseToByteArray(data, params, "report-income-admin");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "report.pdf");
		headers.setContentLength(pdfBytes.length);
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
	

	@PutMapping("/payment")
	public ResponseEntity<PojoRes> updateByUser(@RequestBody PojoUserPaymentReqUpdate data) {
		PojoRes resGet = paymentService.updateByUser(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	
	@GetMapping("/total")
	public ResponseEntity<PojoReportCountMemberRes> getDataActivity(){
		final PojoReportCountMemberRes res = activityService.getTotalData();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@GetMapping("/vouchers-list")
	public ResponseEntity<List<PojoActivityVoucherRes>> getListVooucher(@RequestParam("activityId") String activityId){
		final List<PojoActivityVoucherRes> res = voucherService.getListVoucher(activityId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@PostMapping("/voucher/applied")
	public ResponseEntity<PojoVoucherAppliedRes> setVoucherCode(@RequestBody PojoVoucherAppliedReq data){
		final PojoVoucherAppliedRes res = activityService.getVoucherApplied(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}




}
