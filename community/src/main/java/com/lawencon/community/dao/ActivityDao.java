package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
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

@Repository
public class ActivityDao extends AbstractJpaDao {

	@SuppressWarnings("unchecked")
	public List<Activity> getAll(int offset, int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Activity> listActivities = new ArrayList<>();

		sqlQuery.append(
				"SELECT a.id AS a_id, a.category_id, c.category_code, c.category_name, at.id AS at_id, at.type_code, at.activity_name, a.file_id, a.user_id, a.price, a.title, a.provider, a.activity_location, a.start_date, a.end_date,a.description,u.profile_id ,p.fullname, a.ver, a.is_active ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type at ON at.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");
		sqlQuery.append("ORDER BY a.created_at DESC LIMIT :limit OFFSET :offset");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("offset", offset).setParameter("limit", limit).getResultList();

		try {
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

		} catch (final Exception e) {
			e.printStackTrace();

		}

		return listActivities;
	}

	@SuppressWarnings("unchecked")
	public List<Activity> getAllByHighestPrice(int offset, int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Activity> listActivities = new ArrayList<>();

		sqlQuery.append(
				"SELECT a.id AS a_id, a.category_id, c.category_code, c.category_name, at.id AS at_id, at.type_code, at.activity_name, a.file_id, a.user_id, a.price, a.title, a.provider, a.activity_location, a.start_date, a.end_date,a.description,u.profile_id ,p.fullname, a.ver, a.is_active ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type at ON at.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");
		sqlQuery.append("ORDER BY a.price DESC LIMIT :limit OFFSET :offset");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("offset", offset).setParameter("limit", limit).getResultList();

		try {
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

		} catch (final Exception e) {
			e.printStackTrace();

		}

		return listActivities;
	}

	@SuppressWarnings("unchecked")
    public List<Activity> getAllByDateRange(LocalDate startDate, LocalDate endDate, String userId, Integer offset, Integer limit) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type at ON at.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE AND a.user_id = :userId AND a.start_date BETWEEN :startDate AND :endDate ");
		sqlQuery.append("ORDER BY a.created_at DESC ");
      final Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Activity.class);
      query.setParameter("userId", userId);
      query.setParameter("startDate", startDate);
      query.setParameter("endDate", endDate);

        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        List<Activity> lisActivity = query.getResultList();
        return lisActivity;
    }
	
	
	@SuppressWarnings("unchecked")
    public List<Activity> getAllByDateRange(LocalDate startDate, LocalDate endDate, Integer offset, Integer limit) {
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT * ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type at ON at.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE AND a.start_date BETWEEN :startDate AND :endDate ");
		sqlQuery.append("ORDER BY a.created_at DESC ");
      final Query query = ConnHandler.getManager().createNativeQuery(sqlQuery.toString(), Activity.class);

      query.setParameter("startDate", startDate);
      query.setParameter("endDate", endDate);

        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        List<Activity> lisActivity = query.getResultList();
        return lisActivity;
    }

	

	
	
	public Long getTotalParticipanByUserId(final String activityId, final String userId) {
		final StringBuilder sqlQuery = new StringBuilder();
		Long count =null;
		sqlQuery.append("SELECT COUNT(a.id) FROM t_activity a ");
		
		sqlQuery.append("INNER JOIN t_invoice i ");
		sqlQuery.append("ON i.activity_id = a.id ");
		sqlQuery.append("INNER JOIN t_payment p ");
		sqlQuery.append("ON p.invoice_id = i.id ");
		sqlQuery.append("WHERE a.id = :activityId ");
		sqlQuery.append("AND a.user_id = :userId ");
		sqlQuery.append("AND is_paid = TRUE ");

		count= Long.valueOf(ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				.setParameter("userId", userId)
				.setParameter("activityId",activityId)
				.getSingleResult().toString());
	
	return count;	
		
	}


	@SuppressWarnings("unchecked")
	public List<Activity> getAllByLowestPrice(int offset, int limit) {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Activity> listActivities = new ArrayList<>();

		sqlQuery.append(
				"SELECT a.id AS a_id, a.category_id, c.category_code, c.category_name, at.id AS at_id, at.type_code, at.activity_name, a.file_id, a.user_id, a.price, a.title, a.provider, a.activity_location, a.start_date, a.end_date,a.description,u.profile_id ,p.fullname, a.ver, a.is_active ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type at ON at.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ON a.category_id = c.id ");
		sqlQuery.append("INNER JOIN t_user u ON a.user_id = u.id ");
		sqlQuery.append("INNER JOIN t_profile p ON u.profile_id = p.id ");
		sqlQuery.append("WHERE a.is_active = TRUE ");
		sqlQuery.append("ORDER BY a.price DESC ");

		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).setMaxResults(limit)
				.setFirstResult((offset - 1) * limit).getResultList();

		try {
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

		} catch (final Exception e) {
			e.printStackTrace();

		}

		return listActivities;
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

	public List<Activity> getListActivityByCategoryAndType(final String categoryCode, final String typeCode)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT a.id, a.category_id, a.description, a.user_id,a.type_activity_id, a.file_id, a.title, a.provider, a.activity_location, ");
		sql.append(
				"a.start_date, a.end_date, a.price, a.created_at, a.created_by, a.updated_at, a.updated_by, a.ver, a.is_active ");
		sql.append("FROM t_activity a ");
		sql.append("JOIN t_activity_type at ON a.type_activity_id = at.id ");
		sql.append("JOIN t_category c ON a.category_id = c.id ");
		sql.append("WHERE 1=1 ");

		if (categoryCode != null && !categoryCode.isEmpty()) {
			sql.append("AND c.category_code = :categoryCode ");
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			sql.append("AND at.type_code = :typeCode ");
		}

		Query q = ConnHandler.getManager().createNativeQuery(sql.toString(), Activity.class);

		if (categoryCode != null && !categoryCode.isEmpty()) {
			q.setParameter("categoryCode", categoryCode);
		}

		if (typeCode != null && !typeCode.isEmpty()) {
			q.setParameter("typeCode", typeCode);
		}

		@SuppressWarnings("unchecked")
		List<Activity> listActivity = q.getResultList();

		if (listActivity.isEmpty()) {
			return null;
		}

		return listActivity;

	}

}
