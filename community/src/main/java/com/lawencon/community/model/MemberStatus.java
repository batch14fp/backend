package com.lawencon.community.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_member_status",
uniqueConstraints = {
        @UniqueConstraint(name = "member_status_bk", 
                columnNames = {"codeStatus" }
        )})
public class MemberStatus extends BaseEntity{

	@Column(length=30, nullable = false)
	private String statusName;
	
	private Integer periodDay;
	
	@Column(length=30, nullable = false)
	private String codeStatus;
	
	@Column(nullable = false)
	private BigDecimal price;
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getPeriodDay() {
		return periodDay;
	}
	public void setPeriodDay(Integer periodDay) {
		this.periodDay = periodDay;
	}
	public String getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	

}
