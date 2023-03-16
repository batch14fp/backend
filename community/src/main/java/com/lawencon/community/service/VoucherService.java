package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityVoucherDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityVoucher;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.voucher.PojoVoucherInsertReq;

@Service
public class VoucherService {
	private VoucherDao voucherDao;
	private ActivityVoucherDao activityVoucherDao;
	private ActivityDao activityDao;
	
	public VoucherService(final ActivityDao activityDao, final VoucherDao voucherDao, final ActivityVoucherDao activityVoucherDao) {
		this.voucherDao = voucherDao;
		this.activityVoucherDao = activityVoucherDao;
		this.activityDao = activityDao;
	}

	public PojoInsertRes save(PojoVoucherInsertReq data) {
		final PojoInsertRes res = new PojoInsertRes();
		ConnHandler.begin();
		if(data.getActivityId()!=null) {
		
		final Voucher voucher = new Voucher();
		voucher.setDiscountPercent((data.getDiscountPercent()/100));
		voucher.setExpDate(data.getExpDate());
		voucher.setLimitApplied(null);
		voucher.setUsedCount(null);
		voucher.setIsActive(true);
		
		final Voucher voucherNew = voucherDao.save(voucher);
		
		
		final ActivityVoucher activityVoucher = new ActivityVoucher();
		final Activity activity = activityDao.getByIdRef(data.getActivityId());
		activityVoucher.setVoucher(voucherNew);
		activityVoucher.setActivity(activity);
		activityVoucher.setIsActive(true);
		
		final ActivityVoucher activityVoucherNew  = activityVoucherDao.save(activityVoucher);
		
		
		res.setId(activityVoucherNew.getId());
		res.setMessage("Save Success!");
		}
		else {

			res.setMessage("Id actvity is null");
			
		}
		
		
		return res;
	}

}
