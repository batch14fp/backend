package com.lawencon.community.controller;

import java.util.Base64;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.File;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.payment.PojoConfirmPaymentReqUpdate;
import com.lawencon.community.pojo.payment.PojoPaymentDetailRes;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingReqUpdate;
import com.lawencon.community.pojo.salessetting.PojoSalesSettingRes;
import com.lawencon.community.service.FileService;
import com.lawencon.community.service.PaymentService;
import com.lawencon.community.service.SalesSettingService;

@RestController
@RequestMapping("admin")
public class AdminPaymentController {
	private PaymentService paymentService;
	private SalesSettingService salesSettingService;
	private FileService fileService;
	
	public AdminPaymentController(final FileService fileService,final PaymentService paymentService, SalesSettingService salesSettingService) {
		this.paymentService = paymentService;
		this.salesSettingService = salesSettingService;
		this.fileService = fileService;
		
	}

	
	@GetMapping("/payments")
	public ResponseEntity<PojoPaymentDetailRes> getAllTransactions(@RequestParam(value="isPaid",required=false) Boolean isPaid, @RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="0") Integer limit){
        final PojoPaymentDetailRes data = paymentService.getAll(isPaid, offset, limit);
        return new ResponseEntity<>(data, HttpStatus.OK);
	}
	@PutMapping("/payments")
	public ResponseEntity<PojoRes> updateByAdmin(@RequestBody PojoConfirmPaymentReqUpdate data){
		PojoRes resGet = paymentService.updateByAdmin(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
	@GetMapping("/payments/file/{id}")
    public ResponseEntity<?> getFileById(@PathVariable("id") String id) {
        final Optional<File> file = fileService.getById(id);
        final String fileName = "attachment";
        final byte[] fileBytes = Base64.getDecoder().decode(file.get().getFileContent());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + "." + file.get().getFileExtension())
                .body(fileBytes);
    }
	@GetMapping("/sales-settings")
	public ResponseEntity<PojoSalesSettingRes> getSalesSetting(){
		PojoSalesSettingRes resGet = salesSettingService.getSalesSetting();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@PutMapping("/sales-settings")
	public ResponseEntity<PojoUpdateRes> updateSetting(@RequestBody PojoSalesSettingReqUpdate data){
		PojoUpdateRes resGet = salesSettingService.update(data);
		return new ResponseEntity<>(resGet, HttpStatus.CREATED);
	}
}
	
	
	
	
	
	
	
	
	
	
	

	
	


