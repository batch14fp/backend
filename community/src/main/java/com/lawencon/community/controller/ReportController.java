package com.lawencon.community.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.report.PojoReportActivityAdminRes;
import com.lawencon.community.pojo.report.PojoReportActivityAdminResData;
import com.lawencon.community.pojo.report.PojoReportActivityMemberRes;
import com.lawencon.community.pojo.report.PojoReportActivityMemberResData;
import com.lawencon.community.pojo.report.PojoReportCountMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminRes;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminResData;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberResData;
import com.lawencon.community.service.ActivityService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("reports")
public class ReportController {
	@Autowired
	private JasperUtil jasperUtil;

	private ActivityService activityService;


	public ReportController(ActivityService activityService) {
		this.activityService = activityService;
	
	}
	@GetMapping("/member/activity")
	public ResponseEntity<PojoReportActivityMemberRes> getAllByDateRange(
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String typeCode) {
		try {
			LocalDate startDateParam = null;
			LocalDate endDateParam = null;
			if ((startDate != null && endDate != null)) {
				if (endDate.equalsIgnoreCase("undefined")) {
					endDate = null;
				} else {
					startDateParam = Date.valueOf(startDate).toLocalDate();
				}
				if (startDate.equalsIgnoreCase("undefined")) {
					startDate = null;
				} else {
					endDateParam = Date.valueOf(endDate).toLocalDate();
				}
			}
			PojoReportActivityMemberRes activities = activityService.getMemberReport(startDateParam, endDateParam,
					offset, limit, typeCode);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
	@GetMapping("/member/total")
	public ResponseEntity<PojoReportCountMemberRes> getDataActivity() {
		final PojoReportCountMemberRes res = activityService.getTotalData();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/member/activity/file")
	public ResponseEntity<byte[]> generateReportFile(@RequestParam String id,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String typeCode) throws Exception {
		LocalDate startDateParam = null;
		LocalDate endDateParam = null;
		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = null;
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = null;
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
		List<PojoReportActivityMemberResData> data = activityService.getMemberReportFile(id, startDateParam,
				endDateParam, offset, limit, typeCode);
		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = data.get(data.size()-1).getStartDate();
			} else {
				endDate = data.get(data.size()-1).getStartDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = data.get(0).getStartDate();
			} else {
				startDate = data.get(0).getStartDate();
			}
		}
		Map<String, Object> params = new HashMap<>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		byte[] pdfBytes;
	
			pdfBytes = jasperUtil.responseToByteArray(data, params, "report");
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "report.pdf");
		headers.setContentLength(pdfBytes.length);
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}
	
	@GetMapping("/member/incomes")
	public ResponseEntity<PojoReportIncomesMemberRes> getMemberReport(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String typeCode,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
		try {
			LocalDate startDateParam = null;
			LocalDate endDateParam = null;

			if ((startDate != null && endDate != null)) {
				if (endDate.equalsIgnoreCase("undefined")) {
					endDate = null;
				} else {
					startDateParam = Date.valueOf(startDate).toLocalDate();
				}
				if (startDate.equalsIgnoreCase("undefined")) {
					startDate = null;
				} else {
					endDateParam = Date.valueOf(endDate).toLocalDate();
				}
			}
			PojoReportIncomesMemberRes activities = activityService.getMemberIncomesReport(startDateParam, endDateParam,
					typeCode, offset, limit);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/member/incomes/file")
	public ResponseEntity<byte[]> generateReportFileIncomesMember(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String typeCode,
			@RequestParam("userId") String userId) {
		LocalDate startDateParam = null;
		LocalDate endDateParam = null;

		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = null;
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = null;
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
		List<PojoReportIncomesMemberResData> data = activityService.getMemberIncomesReportFile(startDateParam,
				endDateParam, typeCode, userId);
		Map<String, Object> params = new HashMap<>();
		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = data.get((data.size()-1)).getDateReceived();
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = data.get(0).getDateReceived();
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
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


	@GetMapping("/admin/activity")
	public ResponseEntity<PojoReportActivityAdminRes> getAllByDateRangeAdmin(
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String typeCode) {
		LocalDate startDateParam = null;
		LocalDate endDateParam = null;

		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = null;
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = null;
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
		PojoReportActivityAdminRes activities = activityService.getAdminReport(startDateParam, endDateParam, offset,
				limit, typeCode);
		return ResponseEntity.ok(activities);
	}

	@GetMapping("/admin/activity/file")
	public ResponseEntity<byte[]> generateReportFileAdmin(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) Integer offset,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String typeCode) throws Exception {
		LocalDate startDateParam = null;
		LocalDate endDateParam = null;
		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = null;
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = null;
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
		List<PojoReportActivityAdminResData> data = activityService.getAdminReportFile( startDateParam, endDateParam, typeCode);
		Map<String, Object> params = new HashMap<>();
		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = data.get(data.size()-1).getStartDate();
			} else {
				endDate = data.get(data.size()-1).getStartDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = data.get(0).getStartDate();
			} else {
				startDate = data.get(0).getStartDate();

			}
		}
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		byte[] pdfBytes;
	
			pdfBytes = jasperUtil.responseToByteArray(data, params, "report-admin");
		
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", "report.pdf");
		headers.setContentLength(pdfBytes.length);
		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}


	@GetMapping("/admin/incomes")
	public ResponseEntity<PojoReportIncomesAdminRes> getAdminReports(@RequestParam(required = false) String startDate,
			@RequestParam(required = false)  String endDate, @RequestParam(required = false) String typeCode,
			@RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit) {
		LocalDate startDateParam = null;
		LocalDate endDateParam = null;

		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = null;
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = null;
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
		try {
			PojoReportIncomesAdminRes activities = activityService.getIncomesReportAdmin(startDateParam, endDateParam,
					typeCode, offset, limit);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/admin/incomes/file")
	public ResponseEntity<byte[]> generateReportFileIncomesAdmin(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String typeCode) {
		LocalDate startDateParam = null;
		LocalDate endDateParam = null;

		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = null;
			} else {
				startDateParam = Date.valueOf(startDate).toLocalDate();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = null;
			} else {
				endDateParam = Date.valueOf(endDate).toLocalDate();
			}
		}
		List<PojoReportIncomesAdminResData> data = activityService.getIncomesReportAdminFile(startDateParam,
				endDateParam, typeCode);
		Map<String, Object> params = new HashMap<>();
		if ((startDate != null && endDate != null)) {
			if (endDate.equalsIgnoreCase("undefined")) {
				endDate = data.get((data.size()-1)).getDateReceived();
			} else {
				endDate = data.get((data.size()-1)).getDateReceived();endDate = data.get((data.size()-1)).getDateReceived();
			}
			if (startDate.equalsIgnoreCase("undefined")) {
				startDate = data.get(0).getDateReceived();
			} else {
				startDate = data.get(0).getDateReceived();
			}
		}
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
	
	

}
