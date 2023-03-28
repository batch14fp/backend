package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PostType;


@Repository
public class PostTypeDao extends BaseMasterDao<PostType>{

	@SuppressWarnings("unchecked")
	@Override
	public List<PostType> getAll() {
		   final StringBuilder sb = new StringBuilder();
		    sb.append("SELECT id, type_code, type_name, ver , is_active ");
		    sb.append("FROM t_post_type ");
		    final List<PostType> result = new ArrayList<>();
		    try {
		    final List<Object[]> postTypeList = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
		   
		    for (Object[] obj : postTypeList) {
		        PostType postType = new PostType();
		        postType.setId((String) obj[0]);
		        postType.setTypeCode((String) obj[1]);
		        postType.setTypeName((String) obj[2]);
		        postType.setVersion(Integer.valueOf(obj[3].toString()));
		        postType.setIsActive(Boolean.valueOf(obj[4].toString()));
		      
		   
		        result.add(postType);
		    }
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }
		    return result;
	}
	
	
	public PostType getByCode(final String code) {
		   final StringBuilder sqlQuery = new StringBuilder();
		   sqlQuery.append("SELECT id, type_code, type_name, ver , is_active ");
		   sqlQuery.append("FROM t_post_type ");
		   sqlQuery.append("WHERE type_code = :code");
		   final PostType postType = new PostType();
		   final Optional<Object> result = Optional.ofNullable(ConnHandler.getManager().createNativeQuery(sqlQuery.toString())
				   .setParameter("code", code)
				   .getResultList());
			   if(result.isPresent()){
			   final Object[] obj = (Object[]) result.get();
		   
		        postType.setId((String) obj[0]);
		        postType.setTypeCode((String) obj[1]);
		        postType.setTypeName((String) obj[2]);
		        postType.setVersion(Integer.valueOf(obj[3].toString()));
		        postType.setIsActive(Boolean.valueOf(obj[4].toString()));
			   }
		    return postType;
	}


	@Override
	public Optional<PostType> getById(String id) {
		return Optional.ofNullable(super.getById(PostType.class, id));
	}

	
	@Override
	public PostType getByIdRef(String id) {
		return super.getByIdRef(PostType.class, id);
	}
	
	@Override
	public Optional<PostType> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(PostType.class, id));

	}
	

	
	
	

}
