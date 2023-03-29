package com.lawencon.community.pojo.profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.pojo.socialmedia.PojoSocialMediaRes;

public class PojoProfileDetailRes {
	
	private String userId;
	private String profileId;
	private String industryId;
	private String positionId;
	private String statusMemberId;
	private String fullname;
	private String email;
	private String walletId;
	private Integer walletVer;
	private BigDecimal userBalance;
	private String bankPanymentId;
	private String accountNumber;
	private String accountName;
	private String statusMember;
	private String phoneNumber;
	private LocalDate dob;
	private String country;
	private String province;
	private String city;
	private String postalCode;
	private String company;
	private String imageId;
	private Integer imageVer;
	private LocalDateTime startDateMember;
	private LocalDateTime endDateMember;	
	private Integer ver;
	private List<PojoSocialMediaRes> socialMediaList;

	public String getWalletId() {
		return walletId;
	}
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public Integer getWalletVer() {
		return walletVer;
	}
	public void setWalletVer(Integer walletVer) {
		this.walletVer = walletVer;
	}
	public String getBankPanymentId() {
		return bankPanymentId;
	}
	public void setBankPanymentId(String bankPanymentId) {
		this.bankPanymentId = bankPanymentId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
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
	
	public BigDecimal getUserBalance() {
		return userBalance;
	}
	public void setUserBalance(BigDecimal userBalance) {
		this.userBalance = userBalance;
	}
	public String getStatusMember() {
		return statusMember;
	}
	public void setStatusMember(String statusMember) {
		this.statusMember = statusMember;
	}
	public List<PojoSocialMediaRes> getSocialMediaList() {
		return socialMediaList;
	}
	public void setSocialMediaList(List<PojoSocialMediaRes> socialMediaList) {
		this.socialMediaList = socialMediaList;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public Integer getImageVer() {
		return imageVer;
	}
	public void setImageVer(Integer imageVer) {
		this.imageVer = imageVer;
	}
	public LocalDateTime getStartDateMember() {
		return startDateMember;
	}
	public void setStartDateMember(LocalDateTime startDateMember) {
		this.startDateMember = startDateMember;
	}
	public LocalDateTime getEndDateMember() {
		return endDateMember;
	}
	public void setEndDateMember(LocalDateTime endDateMember) {
		this.endDateMember = endDateMember;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}

	
	
}