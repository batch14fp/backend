package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name = "t_activity_voucher")
public class ActivityVoucher  extends BaseEntity{
	
	
	
	@OneToOne
	@JoinColumn(name="voucher_id")
	private Voucher voucher;
	
	@OneToOne
	@JoinColumn(name="activity_id")
	private Activity activity;

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
	

}
