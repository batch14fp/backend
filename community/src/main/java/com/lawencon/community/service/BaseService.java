package com.lawencon.community.service;

import java.util.List;

public abstract class BaseService<T> {
	
	
	abstract List<T> getAll() throws Exception;

	

	

}
