package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.salessetting.PojoResGetSalesSetting;
import com.lawencon.community.service.SalesSettingService;

@RestController
@RequestMapping("sales-setting")
public class SalesSettingController {
	private SalesSettingService salesSettingService;
	
	public SalesSettingController(final SalesSettingService salesSettingService) {
		this.salesSettingService = salesSettingService;
	}
	
	@GetMapping
	public ResponseEntity<PojoResGetSalesSetting> getSalesSetting(){
		PojoResGetSalesSetting resGet = salesSettingService.getSalesSetting();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	
}
