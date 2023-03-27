package com.lawencon.community.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Subscription;

@Repository
public class SubscriptionDao extends AbstractJpaDao{
	

	public Optional<Subscription> getByProfileId(String profileId)  {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT s.id, s.start_date, s.end_date, s.created_at, s.created_by, s.is_active, s.ver, ");
		sqlQuery.append("m.id, m.status_name, m.period_day, m.code_status ");
		sqlQuery.append("FROM t_subscription s ");
		sqlQuery.append("INNER JOIN t_profile p ON p.id = s.profile_id ");
		sqlQuery.append("INNER JOIN t_member_status m ON m.id = s.member_status_id ");
		sqlQuery.append("WHERE s.is_active = true AND p.id = :profileId");

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());
		query.setParameter("profileId", profileId);
		Object[] obj = (Object[]) query.getSingleResult();

		Subscription subscription = new Subscription();
		subscription.setId(obj[0].toString());
		subscription.setStartDate(LocalDateTime.parse(obj[1].toString()));
		subscription.setEndDate(LocalDateTime.parse(obj[2].toString()));
		subscription.setCreatedAt(LocalDateTime.parse(obj[3].toString()));
		subscription.setCreatedBy(obj[4].toString());
		subscription.setIsActive(Boolean.valueOf(obj[5].toString()));
		subscription.setVersion(Integer.valueOf(obj[6].toString()));
		MemberStatus memberStatus = new MemberStatus();
		memberStatus.setId(obj[7].toString());
		memberStatus.setStatusName(obj[8].toString());
		memberStatus.setPeriodDay(Integer.parseInt(obj[9].toString()));
		memberStatus.setCodeStatus(obj[10].toString());
		subscription.setMemberStatus(memberStatus);

		return Optional.of(subscription);
	}

	
	public Optional<Subscription> getById(String id) {
		return Optional.ofNullable(super.getById(Subscription.class, id));
	}


	public Subscription getByIdRef(String id) {
		return super.getByIdRef(Subscription.class, id);
	}
	

	public Optional<Subscription> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Subscription.class, id));

	}
	
	
	

}
