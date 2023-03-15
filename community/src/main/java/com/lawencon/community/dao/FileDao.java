package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.File;

@Repository
public class FileDao extends BaseMasterDao<File> {

	@SuppressWarnings("unchecked")
	@Override
	public List<File> getAll() throws Exception {
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT id, file_content, file_extension, ver, is_active ");
	    sql.append("FROM t_file ");

	    final List<File> resultList = new ArrayList<>();

	    try {
	   
	        final List<Object[]> list = ConnHandler
	        		.getManager()
	        		.createNativeQuery(sql.toString())
	        		.getResultList();

	        for (Object[] obj : list) {
	            File file = new File();
	            file.setId(obj[0].toString());
	            file.setFileContent(obj[1].toString());
	            file.setFileExtension(obj[2].toString());
	            file.setVersion(Integer.valueOf(obj[3].toString()));
	            file.setIsActive(Boolean.valueOf(obj[3].toString()));
	            resultList.add(file);
	        }

	    } catch (Exception e) {
	        throw new Exception("Failed to retrieve data from database", e);
	    }

	    return resultList;
	}

	@Override
	public Optional<File> getById(String id) {
		return Optional.of(super.getById(File.class, id));
	}

	@Override
	public File getByIdRef(String id) {
		return super.getByIdRef(File.class, id);
	}

	@Override
	public Optional<File> getByIdAndDetach(String id) {

		return Optional.ofNullable(super.getByIdAndDetach(File.class, id));

	}

}
