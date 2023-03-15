package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PostType;


@Repository
public class PostTypeDao extends BaseMasterDao<PostType>{

	@SuppressWarnings("unchecked")
	@Override
	public List<PostType> getAll() {
		   final StringBuilder sb = new StringBuilder();
		    sb.append("SELECT id, type_code, type_name, polling_id ");
		    sb.append("FROM t_post_type ");
		    final List<Object[]> postTypeList = ConnHandler.getManager().createNativeQuery(sb.toString()).getResultList();
		    final List<PostType> result = new ArrayList<>();
		    for (Object[] obj : postTypeList) {
		        PostType postType = new PostType();
		        postType.setId((String) obj[0]);
		        postType.setTypeCode((String) obj[1]);
		        postType.setTypeName((String) obj[2]);
		        Polling polling = new Polling();
		        polling.setId((String) obj[3]);
		        postType.setPolling(polling);
		        result.add(postType);
		    }
		    return result;
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
