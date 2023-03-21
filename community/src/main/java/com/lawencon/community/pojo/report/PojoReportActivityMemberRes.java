package com.lawencon.community.pojo.report;

import java.time.LocalDate;

public class PojoReportActivityMemberRes {
	
	private Integer no;
	private String title;
	private LocalDate startDate;
	private Long totalParticipants;

	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public Long getTotalParticipants() {
		return totalParticipants;
	}
	public void setTotalParticipants(Long totalParticipants) {
		this.totalParticipants = totalParticipants;
	}


}
