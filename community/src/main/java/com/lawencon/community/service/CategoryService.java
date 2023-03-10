package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.category.PojoCategoryReq;
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
			pojoResGetCategory.setIsActive(data.getIsActive());
			listPojoResGetCategory.add(pojoResGetCategory);
		
		});
		return listPojoResGetCategory;
	}
	
	
	
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = categoryDao.deleteById(Category.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}
	
	public PojoInsertRes save(PojoCategoryReq data) {
		ConnHandler.begin();


		Category category = new Category();

		if (data.getCategoryId() != null) {

			category = categoryDao.getByIdAndDetach(data.getCategoryId()).get();
			category.setCategoryCode(data.getCategoryCode());;
			category.setCategoryName(data.getCategoryName());
			category.setIsActive(data.getIsActive());
			category.setVersion(data.getVer());
			
			

		} else {
			category.setCategoryCode(data.getCategoryCode());
			category.setCategoryName(data.getCategoryName());
			category.setIsActive(true);
			
		}

		categoryDao.save(category);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	
}
