package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Category;

@Repository
public class CategoryDao extends BaseMasterDao<Category> {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() throws Exception {

		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT id,  category_name, category_code, ver, is_active ");
		sb.append("FROM t_category ");
		sb.append("WHERE is_active = TRUE");

		final List<Object[]> resultList = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
		final List<Category> categories = new ArrayList<>();
		try {
			for (Object[] obj : resultList) {
				final Category category = new Category();
				category.setId(obj[0].toString());
				category.setCategoryName(obj[1].toString());
				category.setCategoryCode(obj[2].toString());
				category.setVersion(Integer.valueOf(obj[3].toString()));
				category.setIsActive(Boolean.valueOf(obj[4].toString()));
				categories.add(category);
			}
		} catch (Exception e) {
			throw new Exception("Failed to retrieve data from database", e);
		}
		return categories;
	}

	public Category findByCode(String categoryCode) {
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT id, category_name, category_code FROM t_category ");
		sb.append("WHERE category_code = :code ");
		sb.append("AND is_active = TRUE");
		final Object result = ConnHandler.getManager().createNativeQuery(sb.toString())
				.setParameter("code", categoryCode).getSingleResult();

		final Category category = new Category();
		try {
			final Object[] obj = (Object[]) result;
			category.setId(obj[0].toString());
			category.setCategoryName(obj[1].toString());
			category.setCategoryCode(obj[2].toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return category;
	}

	@Override
	public Optional<Category> getById(String id) {
		return Optional.ofNullable(super.getById(Category.class, id));
	}

	@Override
	public Category getByIdRef(String id) {
		return super.getByIdRef(Category.class, id);
	}

	@Override
	public Optional<Category> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(Category.class, id));

	}

}
