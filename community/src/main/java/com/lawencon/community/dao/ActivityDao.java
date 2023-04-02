package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.activity.PojoUpcomingActivityByTypeRes;
import com.lawencon.community.pojo.activity.PojoUpcomingActivityByTypeResData;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminResData;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberResData;
import com.lawencon.community.util.GenerateString;

@Repository
public class ActivityDao extends AbstractJpaDao {

	@SuppressWarnings("unchecked")
	public List<Activity> getAll(int offset, int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Activity> listActivities = new ArrayList<>();

		sqlQuery.append(
				"SELECT a.id AS a_id, a.category_id, c.category_code, c.category_name, tat.id AS tat_id, tat.type_code, tat.activity_name, a.file_id, a.user_id, a.price, a.title, a.provider, a.activity_location, a.start_date, a.end_date,a.description,u.profile_id ,p.fullname, a.ver, a.is_active, a.created_at  ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");
		sqlQuery.append("ORDER BY a.created_at DESC ");
		try {
			final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setFirstResult(offset).setMaxResults(limit)

					.getResultList();

			if (result != null) {
				for (final Object objs : result) {

					final Activity activity = new Activity();
					final Object[] obj = (Object[]) objs;
					activity.setId(obj[0].toString());

					final Category category = new Category();
					category.setId(obj[1].toString());
					category.setCategoryCode(obj[2].toString());
					category.setCategoryName(obj[3].toString());
					activity.setCategory(category);

					final ActivityType activityType = new ActivityType();
					activityType.setTypeCode(obj[5].toString());
					activityType.setActivityName(obj[6].toString());
					activity.setTypeActivity(activityType);

					if (obj[7].toString() != null) {
						final File file = new File();
						file.setId(obj[7].toString());
						activity.setFile(file);
					}

					final User user = new User();

					final Profile profile = new Profile();
					profile.setId(obj[16].toString());
					profile.setFullname(obj[17].toString());
					user.setProfile(profile);
					user.setId(obj[8].toString());
					activity.setUser(user);

					activity.setPrice(BigDecimal.valueOf(Long.valueOf(obj[9].toString())));
					activity.setTitle(obj[10].toString());
					activity.setProvider(obj[11].toString());
					activity.setActivityLocation(obj[12].toString());
					activity.setStartDate(Timestamp.valueOf(obj[13].toString()).toLocalDateTime());
					activity.setEndDate(Timestamp.valueOf(obj[14].toString()).toLocalDateTime());
					activity.setDescription(obj[15].toString());
					activity.setVersion(Integer.valueOf(obj[18].toString()));
					activity.setIsActive(Boolean.valueOf(obj[19].toString()));

					listActivities.add(activity);

				}

			}

		} catch (Exception e) {
			throw new RuntimeException("Error occurred while getting all activities: " + e.getMessage());
		}

		return listActivities;
	}

	@SuppressWarnings("unchecked")
	public List<Activity> searchActivities(int offset, int limit, String sortType, String title) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append(
				"SELECT a.id AS a_id, a.category_id, c.category_code, c.category_name, tat.id AS tat_id, tat.type_code, tat.activity_name, a.file_id, a.user_id, a.price, a.title, a.provider, a.activity_location, a.start_date, a.end_date,a.description,u.profile_id ,p.fullname, a.ver, a.is_active, a.created_at ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");

		if (title != null) {
			sqlQuery.append("AND a.title LIKE :title ");
		}
		if (sortType != null) {
			switch (sortType.toLowerCase()) {
			case "highest":
				sqlQuery.append("ORDER BY a.price DESC ");
				break;
			case "lowest":
				sqlQuery.append("ORDER BY a.price ASC ");
				break;
			case "created_at":
				sqlQuery.append("ORDER BY a.created_at DESC ");
				break;
			default:
				throw new IllegalArgumentException("Invalid sort type: " + sortType);
			}
		}

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setFirstResult((offset - 1) * limit).setMaxResults(limit);
		if (title != null) {
			query.setParameter("title", "%" + title + "%");
		}
		List<Object[]> result = query.getResultList();

		List<Activity> activities = new ArrayList<>();
		for (Object objs : result) {
			Object[] obj = (Object[]) objs;

			Activity activity = new Activity();
			activity.setId(obj[0].toString());

			Category category = new Category();
			category.setId(obj[1].toString());
			category.setCategoryCode(obj[2].toString());
			category.setCategoryName(obj[3].toString());
			activity.setCategory(category);

			ActivityType activityType = new ActivityType();
			activityType.setId(obj[4].toString());
			activityType.setTypeCode(obj[5].toString());
			activityType.setActivityName(obj[6].toString());
			activity.setTypeActivity(activityType);

			if (obj[7] != null) {
				File file = new File();
				file.setId(obj[7].toString());
				activity.setFile(file);
			}

			User user = new User();
			Profile profile = new Profile();
			profile.setId(obj[16].toString());
			profile.setFullname(obj[17].toString());
			user.setProfile(profile);
			user.setId(obj[8].toString());
			activity.setUser(user);

			activity.setPrice(BigDecimal.valueOf(Long.parseLong(obj[9].toString())));
			activity.setTitle(obj[10].toString());
			activity.setProvider(obj[11].toString());
			activity.setActivityLocation(obj[12].toString());
			activity.setStartDate(Timestamp.valueOf(obj[13].toString()).toLocalDateTime());
			activity.setEndDate(Timestamp.valueOf(obj[14].toString()).toLocalDateTime());
			activity.setDescription(obj[15].toString());
			activity.setVersion(Integer.parseInt(obj[18].toString()));
			activity.setIsActive(Boolean.parseBoolean(obj[19].toString()));
			activity.setCreatedAt(Timestamp.valueOf(obj[20].toString()).toLocalDateTime());

			activities.add(activity);
		}
		return activities;

	}

	@SuppressWarnings("unchecked")
	public List<PojoReportIncomesMemberResData> getActivityIncomeByUser(String userId, Float percentIncome,
			LocalDate startDate, LocalDate endDate, String typeCode, Integer offset, Integer limit) {
		final List<PojoReportIncomesMemberResData> resultList = new ArrayList<>();
		try {
		BigDecimal percentValue = new BigDecimal(Float.toString(percentIncome));
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT tat.activity_name,a.title,p.updated_at SUM(p.subtotal * :percentValue) as total_income ");
		sqlQuery.append("FROM t_payment p ");
		sqlQuery.append("INNER JOIN t_invoice i ON p.invoice_id = i.id ");
		sqlQuery.append("INNER JOIN t_activity a ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("WHERE i.user_id = :userId ");
		sqlQuery.append("AND p.is_paid = TRUE ");

		if (startDate != null && endDate != null) {
			sqlQuery.append("AND p.updated_at BETWEEN :startDate AND :endDate ");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			sqlQuery.append("AND tat.type_code = :typeCode ");
		}

		sqlQuery.append("GROUP BY a.type_activity_id, i.activity_id, tat.activity_name, a.title, p.updated_at ");

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());
		query.setParameter("userId", userId);
		query.setParameter("percentValue", percentValue);

		if (startDate != null && endDate != null) {
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			query.setParameter("typeCode", typeCode);
		}

		if (limit != null) {
			query.setMaxResults(limit);
		}
		if (offset != null) {
			query.setFirstResult(offset);
		}

		final List<Object> result = query.getResultList();
		for (Object objs : result) {
			final Object[] obj = (Object[]) objs;
			final PojoReportIncomesMemberResData data = new PojoReportIncomesMemberResData();

			data.setTitle(obj[0].toString());
			data.setType(obj[1].toString());
			if (obj[3] != null) {
				data.setTotalIncomes(BigDecimal.valueOf(Double.valueOf(obj[3].toString())));
			} else {
				data.setTotalIncomes(BigDecimal.ZERO);
			}
			data.setDateReceived(GenerateString.getIndonesianDate(Timestamp.valueOf(obj[2].toString()).toLocalDateTime()));
			resultList.add(data);
		}
		}
		catch(Exception e) {
		e.printStackTrace();
		
		}

		return resultList;
	}
	
	
	
	public Long getTotalActivityIncomeByUser(String userId,
			LocalDate startDate, LocalDate endDate, String typeCode) {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT COUNT(p.id) ");
		sqlQuery.append("FROM t_payment p ");
		sqlQuery.append("INNER JOIN t_invoice i ON p.invoice_id = i.id ");
		sqlQuery.append("INNER JOIN t_activity a ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("WHERE i.user_id = :userId ");
		sqlQuery.append("AND p.is_paid = TRUE ");

		if (startDate != null && endDate != null) {
			sqlQuery.append("AND p.updated_at BETWEEN :startDate AND :endDate ");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			sqlQuery.append("AND tat.type_code = :typeCode ");
		}

		sqlQuery.append("GROUP BY a.type_activity_id, i.activity_id, tat.activity_name, a.title ");

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());
		query.setParameter("userId", userId);

		if (startDate != null && endDate != null) {
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			query.setParameter("typeCode", typeCode);
		}
		final Long count=Long.valueOf(query.getSingleResult().toString());
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Activity> getAllByDateRange(LocalDate startDate, LocalDate endDate, String userId, String typeCode,
			Integer offset, Integer limit) {

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_invoice i ");
		sqlQuery.append("ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_payment p ");
		sqlQuery.append("ON p.invoice_id = i.id ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile pr ON u.profile_id = pr.id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");

		sqlQuery.append("AND p.is_paid = TRUE ");
		if (userId != null && !userId.isEmpty()) {
			sqlQuery.append("AND a.user_id = :userId ");
		}

		if (typeCode != null) {
			sqlQuery.append("AND tat.type_code = :typeCode ");
		}

		if (startDate != null && endDate != null) {
			sqlQuery.append("AND a.start_date ");
			sqlQuery.append("BETWEEN :startDate AND :endDate ");
		}

		sqlQuery.append("ORDER BY a.created_at DESC ");

		List<Activity> lisActivity = new ArrayList<>();

		try {
			final Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Activity.class);

			if (startDate != null && endDate != null) {
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
			}

			if (userId != null && !userId.isEmpty()) {
				query.setParameter("userId", userId);
			}

			if (typeCode != null) {
				query.setParameter("typeCode", typeCode);
			}

			if (limit != null) {
				query.setMaxResults(limit);
			}

			if (offset != null) {
				query.setFirstResult(offset);
			}

			lisActivity = query.getResultList();

		} catch (Exception e) {
			throw new RuntimeException("Error retrieving activities by date range" + e.getMessage());
		}

		return lisActivity;
	}
	
	

	public Long getTotalDataAllByDateRange(LocalDate startDate, LocalDate endDate, String userId, String typeCode) {

	    StringBuilder sqlQuery = new StringBuilder();
	    sqlQuery.append("SELECT COUNT(a.id) ");
	    sqlQuery.append("FROM t_activity a ");
	    sqlQuery.append("INNER JOIN t_invoice i ");
	    sqlQuery.append("ON i.activity_id = a.id ");
	    sqlQuery.append("INNER JOIN t_payment p ");
	    sqlQuery.append("ON p.invoice_id = i.id ");
	    sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
	    sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
	    sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
	    sqlQuery.append("INNER JOIN t_profile pr ON u.profile_id = pr.id ");
	    sqlQuery.append("WHERE a.is_active = TRUE ");

	    sqlQuery.append("AND p.is_paid = TRUE ");
	    if (userId != null && !userId.isEmpty()) {
	        sqlQuery.append("AND a.user_id = :userId ");
	    }

	    if (typeCode != null) {
	        sqlQuery.append("AND tat.type_code = :typeCode ");
	    }

	    if (startDate != null && endDate != null) {
	        sqlQuery.append("AND a.start_date BETWEEN :startDate AND :endDate ");
	    }


	    final Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());

	    if (startDate != null && endDate != null) {
	        query.setParameter("startDate", startDate);
	        query.setParameter("endDate", endDate);
	    }

	    if (userId != null && !userId.isEmpty()) {
	        query.setParameter("userId", userId);
	    }

	    if (typeCode != null) {
	        query.setParameter("typeCode", typeCode);
	    }

	   

	    Long count = Long.valueOf(query.getSingleResult().toString());

	    return count;
	}


	public Long getTotalParticipanByUserId(final String activityId, final String userId) {
		final StringBuilder sqlQuery = new StringBuilder();
		Long count = null;
		sqlQuery.append("SELECT COUNT(a.id) FROM t_activity a ");

		sqlQuery.append("INNER JOIN t_invoice i ");
		sqlQuery.append("ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_payment p ");
		sqlQuery.append("ON p.invoice_id = i.id ");
		sqlQuery.append("WHERE a.id = :activityId ");
		sqlQuery.append("AND a.user_id = :userId ");
		sqlQuery.append("AND p.is_paid = TRUE ");

		count = Long.valueOf(ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("userId", userId).setParameter("activityId", activityId).getSingleResult().toString());

		return count;

	}

	public Long getTotalParticipanByUserIdByType(final String typeCode, final String userId) {
		final StringBuilder sqlQuery = new StringBuilder();
		Long count = null;
		sqlQuery.append("SELECT COUNT(i.id) FROM t_invoice i ");
		sqlQuery.append("INNER JOIN t_payment p ");
		sqlQuery.append("ON p.invoice_id = i.id ");
		sqlQuery.append("INNER JOIN t_activity a ");
		sqlQuery.append("ON a.id  = i.activity_id ");
		sqlQuery.append("INNER JOIN t_activity_type tat ");
		sqlQuery.append("ON tat.id = a.type_activity_id ");
		sqlQuery.append("WHERE p.is_paid = TRUE  ");
		if (typeCode != null) {
			sqlQuery.append("AND tat.type_code = :typeCode ");
		}
		sqlQuery.append("AND a.user_id = :userId ");

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setParameter("userId", userId);
		if (typeCode != null) {
			query.setParameter("typeCode", typeCode);
		}

		count = Long.valueOf(query.getSingleResult().toString());

		return count;

	}

	public int getTotalCount() {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT COUNT(id) as total FROM t_activity ");
		sqlQuery.append("WHERE is_active = TRUE ");
		int totalCount = Integer
				.valueOf(ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).getSingleResult().toString());
		return totalCount;
	}

	public Optional<Activity> getById(String id) {
		return Optional.ofNullable(super.getById(Activity.class, id));
	}

	public Activity getByIdRef(String id) {

		return super.getByIdRef(Activity.class, id);
	}

	public Optional<Activity> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Activity.class, id));

	}
	@SuppressWarnings("unchecked")
	public List<Activity> getListActivityByCategoryAndType(final String categoryCode, final String typeCode,
	        final int offset, final int limit, final String userId, String sortType) throws Exception {
	    StringBuilder sql = new StringBuilder();
	    List<Activity> listActivity = new ArrayList<>();
	    try {
	    sql.append("SELECT a.id, a.category_id, a.description, a.user_id,a.type_activity_id, a.file_id, a.title, a.provider, a.activity_location, ");
	    sql.append("a.start_date, a.end_date, a.price, a.created_at, a.created_by, a.updated_at, a.updated_by, a.ver, a.is_active ");
	    sql.append("FROM t_activity a ");
	    sql.append("JOIN t_activity_type tat ON a.type_activity_id = tat.id ");
	    sql.append("JOIN t_category c ON a.category_id = c.id ");
	    sql.append("WHERE 1=1 ");

	    if (categoryCode != null) {
	        sql.append("AND c.category_code = :categoryCode ");
	    }

	    if (typeCode != null) {
	        sql.append("AND tat.type_code = :typeCode ");
	    }

	    if (userId != null && !userId.isEmpty()) {
	        sql.append("AND a.user_id = :userId ");
	    }
	    if (sortType != null) {
			switch (sortType.toLowerCase()) {
			case "highest":
				sql.append("ORDER BY a.price DESC ");
				break;
			case "lowest":
				sql.append("ORDER BY a.price ASC ");
				break;
			case "created_at":
				sql.append("ORDER BY a.created_at DESC ");
				break;
			default:
				throw new IllegalArgumentException("Invalid sort type: " + sortType);
			}
		}


	    Query q = ConnHandler.getManager().createNativeQuery(sql.toString(), Activity.class);

	    if (categoryCode != null ) {
	        q.setParameter("categoryCode", categoryCode);
	    }

	    if (typeCode != null ) {
	        q.setParameter("typeCode", typeCode);
	    }

	    if (userId != null && !userId.isEmpty()) {
	        q.setParameter("userId", userId);
	    }

	    q.setMaxResults(limit);
	    q.setFirstResult((offset - 1) * limit);

	    listActivity = q.getResultList();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }

	    if (listActivity == null || listActivity.isEmpty()) {
	        return Collections.emptyList();
	    }

	    return listActivity;
	}


	@SuppressWarnings("unchecked")
	public List<PojoReportIncomesAdminResData> getActivityIncome(Float percentIncome, LocalDate startDate,
			LocalDate endDate, String typeCode, Integer offset, Integer limit) {

		final List<PojoReportIncomesAdminResData> resultList = new ArrayList<>();
		BigDecimal percentValue = new BigDecimal(Float.toString(percentIncome));
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT pr.fullname,a.title,p.updated_at SUM(p.subtotal * :percentValue) as total_income ");
		sqlQuery.append("FROM t_payment p ");
		sqlQuery.append("INNER JOIN t_invoice i ON p.invoice_id = i.id ");
		sqlQuery.append("INNER JOIN t_activity a ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_user u ON u.id = i.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ON pr.id = u.profile_id ");
		sqlQuery.append("WHERE p.is_paid = TRUE ");
		if (startDate != null) {
			sqlQuery.append("AND p.updated_at >= :startDate ");
		}
		if (endDate != null) {
			sqlQuery.append("AND p.updated_at <= :endDate ");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			sqlQuery.append("AND tat.type_code = :typeCode ");
		}
		sqlQuery.append("GROUP BY a.type_activity_id, pr.fullname, a.title, p.updated_at ");

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());
		query.setParameter("percentValue", percentValue);

		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			query.setParameter("typeCode", typeCode);
		}

		if (limit != null) {
			query.setMaxResults(limit);
		}
		if (offset != null) {
			query.setFirstResult(offset);
		}

		final List<Object> result = query.getResultList();
		for (Object objs : result) {
			final Object[] obj = (Object[]) objs;
			final PojoReportIncomesAdminResData data = new PojoReportIncomesAdminResData();
			data.setMemberName(obj[0].toString());
			data.setType(obj[1].toString());

			if (obj[3] != null) {
				data.setTotalIncomes(BigDecimal.valueOf(Double.valueOf(obj[3].toString())));
			} else {
				data.setTotalIncomes(BigDecimal.ZERO);
			}
			data.setDateReceived(GenerateString.getIndonesianDate(Timestamp.valueOf(obj[2].toString()).toLocalDateTime()));
			resultList.add(data);
		}

		return resultList;
	}
	
	
	
	public Long getTotalDataActivityIncome( LocalDate startDate,
			LocalDate endDate, String typeCode) {
		final StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT COUNT(p.id) ");
		sqlQuery.append("FROM t_payment p ");
		sqlQuery.append("INNER JOIN t_invoice i ON p.invoice_id = i.id ");
		sqlQuery.append("INNER JOIN t_activity a ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_user u ON u.id = i.user_id ");
		sqlQuery.append("INNER JOIN t_profile pr ON pr.id = u.profile_id ");
		sqlQuery.append("WHERE p.is_paid = TRUE ");
		if (startDate != null) {
			sqlQuery.append("AND p.updated_at >= :startDate ");
		}
		if (endDate != null) {
			sqlQuery.append("AND p.updated_at <= :endDate ");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			sqlQuery.append("AND tat.type_code = :typeCode ");
		}
		sqlQuery.append("GROUP BY a.type_activity_id, pr.fullname, a.title ");

		Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());
	

		if (startDate != null) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null) {
			query.setParameter("endDate", endDate);
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			query.setParameter("typeCode", typeCode);
		}

		final Long result = Long.valueOf(query.getSingleResult().toString());

		return result;
	}

	public BigDecimal getTotalIncomeByUserId(String userId, Float percentIncome) {
		BigDecimal total = BigDecimal.ZERO;
		try {
			BigDecimal percentValue = new BigDecimal(Float.toString(percentIncome));
			final StringBuilder sqlQuery = new StringBuilder();
			sqlQuery.append("SELECT SUM(p.subtotal * :percentValue) as total_income ");
			sqlQuery.append("FROM t_payment p ");
			sqlQuery.append("INNER JOIN t_invoice i ON p.invoice_id = i.id ");
			sqlQuery.append("INNER JOIN t_activity a ON i.activity_id = a.id ");
			sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
			sqlQuery.append("WHERE i.user_id = :userId ");
			sqlQuery.append("AND p.is_paid = TRUE ");
			final Object result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
					.setParameter("userId", userId).setParameter("percentValue", percentValue).getSingleResult();

			if (result != null) {
				final Object[] obj = (Object[]) result;
				total = BigDecimal.valueOf(Double.valueOf(obj[0].toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return total;
	}

	@SuppressWarnings("unchecked")
	public PojoUpcomingActivityByTypeRes getAllUpcomingActivity(final int offset, final int limit,
			final String typeCode) {
		final PojoUpcomingActivityByTypeRes dataUpcoming = new PojoUpcomingActivityByTypeRes();
		final List<PojoUpcomingActivityByTypeResData> listUpcoming = new ArrayList<>();
		final StringBuilder sqlQuery = new StringBuilder();
		try {
			sqlQuery.append(
					"SELECT a.id, a.start_date, tat.activity_name, a.title, COALESCE(COUNT(CASE WHEN p.is_paid = TRUE THEN i.user_id END), 0) as total_participant ");
			sqlQuery.append("FROM t_activity a ");
			sqlQuery.append("LEFT JOIN t_invoice i ON i.activity_id = a.id ");
			sqlQuery.append("LEFT JOIN t_payment p ON p.invoice_id = i.id ");
			sqlQuery.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
			sqlQuery.append("WHERE a.start_date >= NOW() ");
			if (typeCode != null && !typeCode.isEmpty()) {
				sqlQuery.append("AND tat.type_code = :typeCode ");
			}
			sqlQuery.append("GROUP BY a.id, tat.activity_name, a.title, a.start_date ");
			sqlQuery.append("ORDER BY a.start_date DESC ");
			final Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString());

			query.setFirstResult(offset);

			query.setMaxResults(limit);

			if (typeCode != null && !typeCode.isEmpty()) {
				query.setParameter("typeCode", typeCode);
			}

			final StringBuilder countQueryBuilder = new StringBuilder();
			countQueryBuilder.append("SELECT COUNT(DISTINCT a.id) ");
			countQueryBuilder.append("FROM t_activity a ");
			countQueryBuilder.append("LEFT JOIN t_invoice i ON i.activity_id = a.id ");
			countQueryBuilder.append("LEFT JOIN t_payment p ON p.invoice_id = i.id ");
			countQueryBuilder.append("INNER JOIN t_activity_type tat ON tat.id = a.type_activity_id ");
			countQueryBuilder.append("WHERE a.start_date >= NOW() ");
			if (typeCode != null && !typeCode.isEmpty()) {
				countQueryBuilder.append("AND tat.type_code = :typeCode ");
			}
			final Query countQuery = ConnHandler.getManager().createNativeQuery(countQueryBuilder.toString());

			if (typeCode != null && !typeCode.isEmpty()) {
				countQuery.setParameter("typeCode", typeCode);
			}
			final Integer totalData = Integer.valueOf(countQuery.getSingleResult().toString());

			final List<Object[]> result = query.getResultList();
			for (Object[] obj : result) {
				final PojoUpcomingActivityByTypeResData data = new PojoUpcomingActivityByTypeResData();
				data.setActivityId(obj[0].toString());
				data.setStartDate(((Timestamp) obj[1]).toLocalDateTime());
				data.setActivityType(obj[2].toString());
				data.setTitle(obj[3].toString());
				data.setTotalParticipant(obj[4] == null ? 0 : Integer.valueOf(obj[4].toString()));
				listUpcoming.add(data);
			}
			
			dataUpcoming.setData(listUpcoming);
			dataUpcoming.setTotal(totalData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataUpcoming;
	}

	@SuppressWarnings("unchecked")
	public List<Activity> getListActivityByCategoriesAndType(final List<String> categoryCodes, final String typeCode,
			final int offset, final int limit, final String sortType) {

		StringBuilder sqlQuery = new StringBuilder();
		List<Activity> listActivity = new ArrayList<>();
		try {
			sqlQuery.append(
					"SELECT a.id, a.category_id, a.description, a.user_id,a.type_activity_id, a.file_id, a.title, a.provider, a.activity_location, ");
			sqlQuery.append(
					"a.start_date, a.end_date, a.price, a.created_at, a.created_by, a.updated_at, a.updated_by, a.ver, a.is_active, a.created_at ");
			sqlQuery.append("FROM t_activity a ");
			sqlQuery.append("JOIN t_activity_type tat ON a.type_activity_id = tat.id ");
			sqlQuery.append("JOIN t_category c ON a.category_id = c.id ");
			sqlQuery.append("WHERE 1=1 ");
			if (categoryCodes != null && !categoryCodes.isEmpty()) {
				sqlQuery.append("AND (");
				for (int i = 0; i < categoryCodes.size(); i++) {
					sqlQuery.append("c.category_code = :categoryCodes" + i);
					if (i < categoryCodes.size() - 1) {
						sqlQuery.append(" OR ");
					}
				}
				sqlQuery.append(") ");
			}
			if (typeCode != null && !typeCode.isEmpty()) {
				sqlQuery.append("AND tat.type_code = :typeCode ");
			}
			if (sortType != null) {
				switch (sortType.toLowerCase()) {
				case "highest":
					sqlQuery.append(" ORDER BY a.price DESC ");
					break;
				case "lowest":
					sqlQuery.append(" ORDER BY a.price ASC ");
					break;
				case "created_at":
					sqlQuery.append(" ORDER BY a.created_at DESC ");
					break;
				default:
					throw new IllegalArgumentException("Invalid sort type: " + sortType);
				}
			}

			Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Activity.class);

			if (categoryCodes != null && !categoryCodes.isEmpty()) {
				for (int i = 0; i < categoryCodes.size(); i++) {
					query.setParameter("categoryCodes" + i, categoryCodes.get(i));
				}
			}
			if (typeCode != null && !typeCode.isEmpty()) {
				query.setParameter("typeCode", typeCode);
			}

			query.setMaxResults(limit);
			query.setFirstResult((offset - 1) * limit);

			listActivity = query.getResultList();

			if (listActivity.isEmpty()) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listActivity;
	}

}
