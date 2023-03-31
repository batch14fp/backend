package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;


@Entity
@Table(name = "t_activity_voucher",
uniqueConstraints = {
        @UniqueConstraint(name = "activity_voucher_ck", 
        columnNames = {"voucher_id", "activity_id" }
        )
        })
public class ActivityVoucher  extends BaseEntity{

	@OneToOne
	@JoinColumn(name="voucher_id", nullable=false)
	private Voucher voucher;
	
	@OneToOne
	@JoinColumn(name="activity_id", nullable=false)
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
