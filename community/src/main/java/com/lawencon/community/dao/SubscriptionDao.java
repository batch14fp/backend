package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Subscription;

@Repository
public class SubscriptionDao extends AbstractJpaDao {

	public Optional<Subscription> getByProfileId(String profileId) {
		StringBuilder sqlQuery = new StringBuilder();

		Subscription subscription = new Subscription();
		try {
			sqlQuery.append(
					"SELECT  s.id AS subscription_id, s.start_date, s.end_date, s.created_at, s.created_by, s.is_active, s.ver, ");
			sqlQuery.append("m.id AS member_status_id, m.status_name, m.period_day, m.code_status ");
			sqlQuery.append("FROM t_subscription s ");
			sqlQuery.append("INNER JOIN t_profile p ON p.id = s.profile_id ");
			sqlQuery.append("INNER JOIN t_member_status m ON m.id = s.member_status_id ");
			sqlQuery.append("WHERE s.is_active = true AND p.id = :profileId");

			Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("profileId", profileId).getSingleResult();

			if (result != null) {
				Object[] obj = (Object[]) result;

				subscription.setId(obj[0].toString());
				subscription.setStartDate(Timestamp.valueOf(obj[1].toString()).toLocalDateTime());
				subscription.setEndDate(Timestamp.valueOf(obj[2].toString()).toLocalDateTime());
				subscription.setCreatedAt(Timestamp.valueOf(obj[3].toString()).toLocalDateTime());
				subscription.setCreatedBy(obj[4].toString());
				subscription.setIsActive(Boolean.valueOf(obj[5].toString()));
				subscription.setVersion(Integer.valueOf(obj[6].toString()));

				final MemberStatus memberStatus = new MemberStatus();
				memberStatus.setId(obj[7].toString());
				memberStatus.setStatusName(obj[8].toString());
				memberStatus.setPeriodDay(Integer.valueOf(obj[9].toString()));
				memberStatus.setCodeStatus(obj[10].toString());
				subscription.setMemberStatus(memberStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(subscription);
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
