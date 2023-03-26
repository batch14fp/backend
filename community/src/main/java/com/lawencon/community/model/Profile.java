package com.lawencon.community.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;




@Entity
@Table(name = "t_profile", 
    uniqueConstraints = {
            @UniqueConstraint(name = "phone_number_bk", 
                    columnNames = {"phoneNumber", }
            )})
			
public class Profile extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "position_id", nullable=false)
	private Position position;
	
	
	@OneToOne
	@JoinColumn(name = "imageProfile_Id")
	private File imageProfile;
	
	
	@OneToOne
	@JoinColumn(name = "industry_id", nullable=false)
	private Industry industry;
	
	
	
	@Column(length=50, nullable=false)
	private String fullname;
	
	@Column(length=50, nullable=false)
	private String companyName;
	
	private LocalDate dob;
	
	
	@Column(length=16, nullable=false)
	private String phoneNumber;

	@Column(length=30 )
	private String country;
	
	@Column(length=30 )
	private String city;
	
	@Column(length=30)
	private String province;
	
	@Column(length=10)
	private String postalCode;
	
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public File getImageProfile() {
		return imageProfile;
	}

	public void setImageProfile(File imageProfile) {
		this.imageProfile = imageProfile;
	}


	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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


	
	
	

}
