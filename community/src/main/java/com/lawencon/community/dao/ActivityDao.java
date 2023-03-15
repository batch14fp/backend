package com.lawencon.community.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;

@Repository
public class ActivityDao extends BaseMasterDao<Activity> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getAll() {
		final StringBuilder sqlQuery = new StringBuilder();
		final List<Activity> listActivities = new ArrayList<>();

		sqlQuery.append(
				"SELECT a.id,a.category_id, c.category_code, c.category_name,at.id, at.type_code,at.activity_name,a.file_id,a.voucher_id,a.user_id,a.price,");
		sqlQuery.append("a.title,a.provider,a.activity_location, a.start_date,a.end_date,");
		sqlQuery.append("a.start_time,a.end_time,a.ver,a.is_active ");
		sqlQuery.append("FROM t_activity a ");
		sqlQuery.append("INNER JOIN t_activity_type at ");
		sqlQuery.append("ON at.id = a.type_activity_id ");
		sqlQuery.append("INNER JOIN t_category c ");
		sqlQuery.append("ON a.category_id = c.id ");
		sqlQuery.append("WHERE is_active = TRUE ");
		final List<Object> result = ConnHandler.getManager().createNativeQuery(sqlQuery.toString()).getResultList();

		try {
			if (result != null) {
				for (final Object objs : result) {

					final Activity activity = new Activity();
					final Object[] obj = (Object[]) objs;
					activity.setId(obj[0].toString());

					final Category category = new Category();
					category.setId(obj[0].toString());
					category.setCategoryCode(obj[1].toString());
					category.setCategoryName(obj[2].toString());
					activity.setCategory(category);

					final ActivityType activityType = new ActivityType();
					activityType.setTypeCode(obj[3].toString());
					activityType.setActivityName(obj[4].toString());
					activity.setTypeActivity(activityType);

					if (obj[5].toString() != null) {
						final File file = new File();
						file.setId(obj[5].toString());
						activity.setFile(file);
					}

					final Voucher voucher = new Voucher();
					voucher.setId(obj[6].toString());
					activity.setVoucher(voucher);

					final User user = new User();
					user.setId(obj[7].toString());
					activity.setUser(user);

					activity.setPrice(BigDecimal.valueOf(Long.valueOf(obj[8].toString())));
					activity.setTitle(obj[9].toString());
					activity.setProvider(obj[10].toString());
					activity.setActivityLocation(obj[11].toString());
					activity.setStartDate(Timestamp.valueOf(obj[12].toString()).toLocalDateTime());
					activity.setEndDate(Timestamp.valueOf(obj[13].toString()).toLocalDateTime());
					activity.setVersion(Integer.valueOf(obj[14].toString()));
					activity.setIsActive(Boolean.valueOf(obj[15].toString()));

					listActivities.add(activity);

				}

			}

		} catch (final Exception e) {
			e.printStackTrace();

		}

		return listActivities;
	}

	@Override
	public Optional<Activity> getById(String id) {
		return Optional.ofNullable(super.getById(Activity.class, id));
	}

	@Override
	public Activity getByIdRef(String id) {

		return super.getByIdRef(Activity.class, id);
	}

	@Override
	public Optional<Activity> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Activity.class, id));

	}

	public List<Activity> getListActivityByCategoryAndType(final String categoryCode, final String typeCode) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT a.id, a.category_id, a.type_activity_id, a.file_id, a.title, a.provider, a.activity_location, ");
		sql.append("a.start_date, a.end_date, a.price, a.created_at, a.created_by, a.updated_at, a.updated_by ");
		sql.append("FROM t_activity a ");
		sql.append("JOIN m_activity_type at ON a.type_activity_id = at.id ");
		sql.append("JOIN m_category c ON a.category_id = c.id ");
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
