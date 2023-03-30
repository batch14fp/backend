package com.lawencon.base;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.lawencon.security.principal.PrincipalService;

/**
 * 
 * @author Agung Damas Saputra
 * 
 */
public class AbstractJpaDao {

	@Inject
	private PrincipalService principalService;

	public <T> T getById(Class<T> entityClass, final Object id) {
		final T data = em().find(entityClass, id);
		return data;
	}

	public <T> T getByIdRef(Class<T> entityClass, final Object id) {
		final T data = em().getReference(entityClass, id);
		return data;
	}

	public <T> T getByIdAndDetach(Class<T> entityClass, final Object id) {
		final T data = em().find(entityClass, id);
		if (data != null) {
			em().detach(data);
		}
		return data;
	}
	

	public <T extends BaseEntity> T save(T entity) {
		if (entity.getId() != null) {
			entity.setUpdatedBy(principalService.getAuthPrincipal());
			entity = em().merge(entity);
		} else {
			entity.setId(UUID.randomUUID().toString());
			entity.setCreatedBy(principalService.getAuthPrincipal());
			em().persist(entity);
		}

		return entity;
	}

	public <T extends BaseEntity> T saveAndFlush(T entity) {
		final T savedEntity = save(entity);

		if (em().contains(savedEntity)) {
			em().flush();
		}

		return entity;
	}

	public <T extends BaseEntity> T saveNoLogin(T entity, Supplier<String> getIdFunc) {
		if (getIdFunc == null)
			throw new RuntimeException("You must supply the ID");

		if (entity.getId() != null) {
			if (getIdFunc.get() == null)
				throw new RuntimeException("UpdatedBy is NULL");
			entity.setUpdatedBy(getIdFunc.get());
			entity = em().merge(entity);
		} else {
			if (getIdFunc.get() == null)
				throw new RuntimeException("CreatedBy is NULL");
			entity.setId(UUID.randomUUID().toString());
			entity.setCreatedBy(getIdFunc.get());
			em().persist(entity);
		}

		return entity;
	}

	public <T> void delete(final T entity) {
		em().remove(entity);
	}

	public <T> boolean deleteById(Class<T> entityClass, final Object entityId) {
		T entity = null;
		if (entityId != null && entityId instanceof String) {
			entity = em().find(entityClass, entityId);
		}

		if (entity != null) {
			delete(entity);
			return true;
		}

		return false;
	}

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	protected <C> TypedQuery<C> createQuery(Class<C> clazz, String sql) {
		return createQuery(clazz, sql, null, null);
	}

	protected <C> TypedQuery<C> createQuery(Class<C> clazz, String sql, Integer startPosition, Integer limit) {
		final TypedQuery<C> typedQuery = em().createQuery(sql, clazz);

		if (startPosition != null)
			typedQuery.setFirstResult(startPosition);
		if (limit != null)
			typedQuery.setMaxResults(limit);

		return typedQuery;
	}

	
	public <T> List<T> getAll(Class<T> entityClass) {
		final CriteriaBuilder cb = em().getCriteriaBuilder();
		final CriteriaQuery<T> cr = cb.createQuery(entityClass);
		final Root<T> root = cr.from(entityClass);
		cr.select(root);

		return em().createQuery(cr).getResultList();
	}

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
	
	

}