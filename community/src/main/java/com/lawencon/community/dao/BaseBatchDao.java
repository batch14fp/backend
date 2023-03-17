package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.lawencon.base.BaseEntity;
import com.lawencon.base.ConnHandler;
import com.lawencon.security.principal.PrincipalService;

public class BaseBatchDao {

	@Inject
	private PrincipalService principalService;

	public <T extends BaseEntity> List<T> saveAll(List<T> entities) {
	    List<T> savedEntities = new ArrayList<>();
	    for (T entity : entities) {
	        if (entity.getId() != null) {
	            entity.setUpdatedBy(principalService.getAuthPrincipal());
	            entity = em().merge(entity);
	        } else {
	            entity.setId(UUID.randomUUID().toString());
	            entity.setCreatedBy(principalService.getAuthPrincipal());
	            em().persist(entity);
	        }
	        savedEntities.add(entity);
	    }
	    em().flush();
	    return savedEntities;
	}
	private EntityManager em() {
		return ConnHandler.getManager();
	}
}
