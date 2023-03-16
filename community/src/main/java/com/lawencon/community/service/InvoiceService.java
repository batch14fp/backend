package com.lawencon.community.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.InvoiceDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.security.principal.PrincipalService;

@Service
public class InvoiceService {
	private InvoiceDao invoiceDao;
	private UserDao userDao;
	private ActivityDao activityDao;
//	private 
	
	@Inject
	private PrincipalService principalService;
	
	public InvoiceService(final InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
	
	public PojoInsertRes save() {
		ConnHandler.begin();
		final User user = new User();
		
		
		return new PojoInsertRes();
	}
	
}
