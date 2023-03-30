package com.lawencon.community.pojo.profile;

import java.time.LocalDate;

import com.lawencon.community.pojo.bankpayment.PojoBankPaymentReqUpdate;
import com.lawencon.community.pojo.file.PojoFileReqUpdate;

public class PojoProfileReqUpdate {
	private String profileId;
	private String fullname;
	private String company;
	private String country;
	private String province;
	private String city;
	private LocalDate dob;
	private String memberStatusId;
	private String walletId;
	private String postalCode;
	private String industryId;
	private String positionId;
	private String phoneNumber;
	private Integer walletVer;
	private PojoBankPaymentReqUpdate bankUserAccount;
	private PojoFileReqUpdate file;
	
	public PojoFileReqUpdate getFile() {
		return file;
	}
	public void setFile(PojoFileReqUpdate file) {
		this.file = file;
	}
	private Integer ver;
	private Boolean isActive;
	
	
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
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


	public String getMemberStatusId() {
		return memberStatusId;
	}
	public void setMemberStatusId(String memberStatusId) {
		this.memberStatusId = memberStatusId;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public PojoBankPaymentReqUpdate getBankUserAccount() {
		return bankUserAccount;
	}
	public void setBankUserAccount(PojoBankPaymentReqUpdate bankUserAccount) {
		this.bankUserAccount = bankUserAccount;
	}
	public Integer getWalletVer() {
		return walletVer;
	}
	public void setWalletVer(Integer walletVer) {
		this.walletVer = walletVer;
	}

	
	
}
