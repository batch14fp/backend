package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.category.PojoCategoryReqInsert;
import com.lawencon.community.pojo.category.PojoCategoryReqUpdate;
import com.lawencon.community.pojo.category.PojoCategoryRes;


@Service
public class CategoryService extends BaseService<PojoCategoryRes>{
	private final CategoryDao categoryDao;
	public CategoryService(final CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	

	private void validateBkNotNull(PojoCategoryReqInsert category) {
	    if(category.getCategoryCode() == null) {
	        throw new RuntimeException("Category Code cannot be empty.");
	    }
	
	}

	private void validateNonBk(PojoCategoryReqInsert category) {
	
	    if(category.getCategoryName() == null) {
	        throw new RuntimeException("Category Name cannot be empty.");
	    }
	   
	}
	private void validateNonBk(PojoCategoryReqUpdate category) {

		 if(	category.getCategoryId() == null) {
		        throw new RuntimeException("Category ID cannot be empty.");
		    }
		
		
		 if(	category.getVer() == null) {
		        throw new RuntimeException("Category version cannot be empty.");
		    }
		 if(	category.getCategoryName() == null) {
		        throw new RuntimeException("Category Name cannot be empty.");
		    }
		 
		}
	
	private void validateBkNotExist(String id) {
		if (categoryDao.getById(id).isEmpty()) {
			throw new RuntimeException("Category cannot be empty.");
		}
	}

	@Override
	public List<PojoCategoryRes> getAll() throws Exception{
		final List<PojoCategoryRes>  listPojoResGetCategory = new ArrayList<>();
		categoryDao.getAll().forEach(data ->{
			PojoCategoryRes pojoResGetCategory = new PojoCategoryRes();
			pojoResGetCategory.setCategoryId(data.getId());
			pojoResGetCategory.setCategoryCode(data.getCategoryCode());
			pojoResGetCategory.setCategoryName(data.getCategoryName());
			pojoResGetCategory.setVer(data.getVersion());
			pojoResGetCategory.setIsActive(data.getIsActive());
			listPojoResGetCategory.add(pojoResGetCategory);
		
		});
		return listPojoResGetCategory;
	}
	
	
	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		final Boolean result = categoryDao.deleteById(Category.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}
	
	public PojoUpdateRes update(PojoCategoryReqUpdate data) {
		validateNonBk(data);
		
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			Category category = categoryDao.getByIdRef(data.getCategoryId());
			categoryDao.getByIdAndDetach(Category.class, category.getId());
				category.setId(category.getId());
				category.setCategoryName(data.getCategoryName());
				category.setIsActive(data.getIsActive());
				category.setVersion(data.getVer());
			final Category categoryNew = categoryDao.saveAndFlush(category);
			ConnHandler.commit();
			pojoUpdateRes.setId(categoryNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(categoryNew.getVersion());
		
		} catch (Exception e) {
			pojoUpdateRes.setId(data.getCategoryId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update the data");
		}
		return pojoUpdateRes;
		
		
	}
	public PojoInsertRes save(PojoCategoryReqInsert data) {
		validateBkNotNull(data);
		validateNonBk(data);
		
		ConnHandler.begin();
		Category category = new Category();
			category.setCategoryCode(data.getCategoryCode());;
			category.setCategoryName(data.getCategoryName());
			category.setIsActive(true);
			
		final Category categoryNew = categoryDao.save(category);
		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(categoryNew.getId());
		pojoRes.setMessage("Update Success!");
		return pojoRes;
	}
	
	
	
}
