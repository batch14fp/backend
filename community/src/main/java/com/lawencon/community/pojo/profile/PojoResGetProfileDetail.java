package com.lawencon.community.pojo.profile;

import java.math.BigInteger;
import java.time.LocalDate;

public class PojoResGetProfileDetail {
	private String userId;
	private String industryId;
	private String positionId;
	private String statusMemberId;
	private String fullname;
	private String email;
	private BigInteger userBalance;
	private String statusMember;
	private String phoneNumber;
	private LocalDate dob;
	private String country;
	private String province;
	private String city;
	private String postalCode;
	private String company;

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getStatusMemberId() {
		return statusMemberId;
	}
	public void setStatusMemberId(String statusMemberId) {
		this.statusMemberId = statusMemberId;
	}
	public BigInteger getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(BigInteger userBalance) {
		this.userBalance = userBalance;
	}
	public String getStatusMember() {
		return statusMember;
	}
	public void setStatusMember(String statusMember) {
		this.statusMember = statusMember;
	}
	
}