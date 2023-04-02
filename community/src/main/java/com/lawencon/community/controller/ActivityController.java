package com.lawencon.community.controller;

import java.util.List;

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
import com.lawencon.community.pojo.activity.PojoActivityReqInsert;
import com.lawencon.community.pojo.activity.PojoActivityReqUpdate;
import com.lawencon.community.pojo.activity.PojoActivityRes;
import com.lawencon.community.pojo.activity.PojoUpcomingActivityByTypeRes;
import com.lawencon.community.pojo.activitytype.PojoActivityTypeRes;
import com.lawencon.community.pojo.bankpayment.PojoBankPaymentRes;
import com.lawencon.community.pojo.category.PojoCategoryRes;
import com.lawencon.community.pojo.memberstatus.PojoMemberStatusRes;
import com.lawencon.community.pojo.payment.PojoPaymentDetailRes;
import com.lawencon.community.pojo.payment.PojoPaymentDetailResData;
import com.lawencon.community.pojo.payment.PojoUserPaymentReqUpdate;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingRes;
import com.lawencon.community.pojo.voucher.PojoActivityVoucherRes;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedReq;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedRes;
import com.lawencon.community.service.ActivityService;
import com.lawencon.community.service.ActivityTypeService;
import com.lawencon.community.service.BankPaymentService;
import com.lawencon.community.service.CategoryService;
import com.lawencon.community.service.MemberStatusService;
import com.lawencon.community.service.PaymentService;
import com.lawencon.community.service.SalesSettingService;
import com.lawencon.community.service.VoucherService;

@RestController
@RequestMapping("activities")
public class ActivityController {

	private ActivityService activityService;
	private PaymentService paymentService;
	private VoucherService voucherService;
	private CategoryService categoryService;
	private ActivityTypeService activityTypeService;
	private MemberStatusService memberStatusService;
	private BankPaymentService bankPaymentService;
	private SalesSettingService salesSettingService;
	public ActivityController(final SalesSettingService salesSettingService, final BankPaymentService bankPaymentService, final MemberStatusService memberStatusService,final ActivityTypeService activityTypeService, final VoucherService voucherService, final PaymentService paymentService,
			final ActivityService activityService) {
		this.activityService = activityService;
		this.paymentService = paymentService;
		this.voucherService = voucherService;
		this.activityTypeService = activityTypeService;
		this.memberStatusService = memberStatusService;
		this.bankPaymentService = bankPaymentService;
		this.salesSettingService = salesSettingService;
	}

	@GetMapping
	public ResponseEntity<List<PojoActivityRes>> getData(@RequestParam("page") int page,
			@RequestParam("size") int size) {
		final List<PojoActivityRes> dataList = activityService.getAll(page, size);
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

	@GetMapping("/by-category-List")
	public ResponseEntity<List<PojoActivityRes>> getListActivityByCategoryAndType(
			@RequestParam(value = "categoryCodes", required = false) List<String> categoryCodes,
			@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "sortType", defaultValue = "created_at") String sortType) {
		try {
			List<PojoActivityRes> activities = activityService.getListActivityByListCategoryAndType(categoryCodes,
					typeCode, page, size, sortType);
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
			@RequestParam(value = "typeCode", required = false) String typeCode, @RequestParam(value = "page") int page,
			@RequestParam(value = "size") int size) {
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

	@GetMapping("/sort")
	public ResponseEntity<List<PojoActivityRes>> getDataActivity(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(required = false) String sortType,
			@RequestParam(defaultValue = "") String title) {
		final List<PojoActivityRes> dataList = activityService.getAllBySort(page, size, sortType, title);
		return new ResponseEntity<>(dataList, HttpStatus.OK);
	}

	@GetMapping("/filter")
	public ResponseEntity<List<PojoActivityRes>> getListActivityByCategoryAndType(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(value = "categoryCode", required = false) String categoryCode,
			@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "sortType", defaultValue = "created_at") String sortType) {
		try {
			List<PojoActivityRes> activities = activityService.getListActivityByCategoryAndType(categoryCode, typeCode,
					page, size, sortType);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/payment")
	public ResponseEntity<PojoRes> updateByUser(@RequestBody PojoUserPaymentReqUpdate data) {
		PojoRes resPut = paymentService.updateByUser(data);
		return new ResponseEntity<>(resPut, HttpStatus.CREATED);
	}

	@GetMapping("/payment/{id}")
	public ResponseEntity<PojoPaymentDetailResData> getPaymentById(@PathVariable("id") String id) {
		PojoPaymentDetailResData resGet = paymentService.getPaymentDetailById(id);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}

	@GetMapping("/vouchers-list")
	public ResponseEntity<List<PojoActivityVoucherRes>> getListVoucher(@RequestParam("activityId") String activityId) {
		final List<PojoActivityVoucherRes> res = voucherService.getListVoucher(activityId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/voucher/applied")
	public ResponseEntity<PojoVoucherAppliedRes> setVoucherCode(@RequestBody PojoVoucherAppliedReq data) {
		final PojoVoucherAppliedRes res = activityService.getVoucherApplied(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/{invoiceId}/payment/detail-payment")
	public ResponseEntity<PojoPaymentDetailResData> getDetailPayment(@PathVariable("invoiceId") String invoiceId) {
		final PojoPaymentDetailResData data = paymentService.getPaymentDetail(invoiceId);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/my-transactions")
	public ResponseEntity<PojoPaymentDetailRes> getAllMyTransaction(
			@RequestParam(value = "isPaid", required = false) Boolean isPaid,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "0") Integer limit) {

		final PojoPaymentDetailRes data = paymentService.getByUserId(isPaid, offset, limit);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/my-activity/filter")
	public ResponseEntity<List<PojoActivityRes>> getMyActivityByCategoryAndType(@RequestParam("page") int page,
			@RequestParam("size") int size, @RequestParam(value = "categoryCode", required = false) String categoryCode,
			@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "sortType", defaultValue = "created_at") String sortType) {
		try {
			List<PojoActivityRes> activities = activityService.getByUserIdActivityByCategoryAndType(categoryCode,
					typeCode, page, size, sortType);
			return ResponseEntity.ok(activities);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@GetMapping("/categories")
	public ResponseEntity<List<PojoCategoryRes>> getAllCategories() throws Exception {
		List<PojoCategoryRes> resGet = categoryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@GetMapping("/activity-types")
	public ResponseEntity<List<PojoActivityTypeRes>> getAllActivityTypes(){
		List<PojoActivityTypeRes> resGet = activityTypeService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@GetMapping("/activity-types/find")
	public ResponseEntity<PojoActivityTypeRes> getActivityTypeByCode(@RequestParam("typeCode") String typeCode){
	        final PojoActivityTypeRes data = activityTypeService.getByCode(typeCode);
	        return new ResponseEntity<>(data, HttpStatus.OK);
	}
	@GetMapping("/member-status")
	public ResponseEntity<List<PojoMemberStatusRes>>getAllMemberStatus(){
		List<PojoMemberStatusRes> resGet = memberStatusService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@GetMapping("/bank-payments")
	public ResponseEntity<List<PojoBankPaymentRes>> getAllBankPayments() {
		List<PojoBankPaymentRes> resGet = bankPaymentService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@GetMapping("/sales-settings")
	public ResponseEntity<PojoSalesSettingRes> getSalesSetting(){
		PojoSalesSettingRes resGet = salesSettingService.getSalesSetting();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	
	

}