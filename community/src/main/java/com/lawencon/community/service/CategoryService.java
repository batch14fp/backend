package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.model.Category;

public class CategoryService {
	private final CategoryDao categoryDao;
	public CategoryService(final CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public List<Category> getAll(){
		final List<Category> res = new ArrayList<>();
		
		categoryDao.getAll().forEach(data ->{
		
		});
		
		return res;
	}
}
