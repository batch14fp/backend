package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.pojo.category.PojoResGetCategory;


@Service
public class CategoryService extends BaseService<PojoResGetCategory>{
	private final CategoryDao categoryDao;
	public CategoryService(final CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	
	@Override
	public List<PojoResGetCategory> getAll(){
		final List<PojoResGetCategory>  listPojoResGetCategory = new ArrayList<>();
		categoryDao.getAll().forEach(data ->{
			PojoResGetCategory pojoResGetCategory = new PojoResGetCategory();
			pojoResGetCategory.setCategoryId(data.getId());
			pojoResGetCategory.setCategoryCode(data.getCategoryCode());
			pojoResGetCategory.setCategoryName(data.getCategoryName());
			listPojoResGetCategory.add(pojoResGetCategory);
		
		});
		return listPojoResGetCategory;
	}
}
