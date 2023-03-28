package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityVoucherDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityVoucher;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.voucher.PojoActivityVoucherRes;
import com.lawencon.community.pojo.voucher.PojoVoucherReqInsert;

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

	public PojoInsertRes save(PojoVoucherReqInsert data) {
		final PojoInsertRes res = new PojoInsertRes();
		ConnHandler.begin();
		if(data.getActivityId()!=null) {
		
		final Voucher voucher = new Voucher();
		voucher.setDiscountPercent((data.getDiscountPercent()/100));
		voucher.setExpDate(data.getExpDate());
		voucher.setLimitApplied(data.getLimitApplied());
		voucher.setUsedCount(data.getUsedCount());
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
	

	public List<PojoActivityVoucherRes> getListVoucher(String activityId){
		final List<PojoActivityVoucherRes> voucherList = new ArrayList<>();
		
		 activityVoucherDao.getListVoucher(activityId).forEach(data->{
			PojoActivityVoucherRes res = new PojoActivityVoucherRes();
			res.setVoucherId(data.getVoucher().getId());
			res.setVoucherCode(data.getVoucher().getVoucherCode());
			Long discountNum = (long) (data.getVoucher().getDiscountPercent()*100);
			res.setDiscountNum(discountNum);
			voucherList.add(res);
		});
		
		return voucherList;
		
	}

}
