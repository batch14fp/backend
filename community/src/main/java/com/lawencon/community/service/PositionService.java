package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.position.PojoPostionReq;
import com.lawencon.community.pojo.position.PojoResGetPostion;

@Service
public class PositionService extends BaseService<PojoResGetPostion>{

	private final PositionDao positionDao;

	public PositionService(PositionDao positionDao) {
		this.positionDao = positionDao;

	}

	
	@Override
	public List<PojoResGetPostion> getAll() {
		final List<PojoResGetPostion> positions = new ArrayList<>();
		positionDao.getAll().forEach(data -> {
			PojoResGetPostion pojoResGetPostion = new PojoResGetPostion();
			pojoResGetPostion.setPositionId(data.getId());
			pojoResGetPostion.setPositionCode(data.getPositionCode());
			pojoResGetPostion.setPositionName(data.getPositionName());
			positions.add(pojoResGetPostion);
		});
		return positions;

	}
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = positionDao.deleteById(Position.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}
	
	public PojoInsertRes save(PojoPostionReq data) {
		ConnHandler.begin();


		Position position = new Position();

		if (data.getPositionId() != null) {

			position = positionDao.getByIdAndDetach(data.getPositionId()).get();
			position.setPositionName(data.getPostionName());
			position.setPositionCode(data.getPostionCode());
			position.setIsActive(data.getIsActive());
			position.setVersion(data.getVer());
			
			

		} else {
			position.setPositionName(data.getPostionName());
			position.setPositionCode(data.getPostionCode());
			position.setIsActive(true);
			
		}

		positionDao.save(position);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}
	
	
	
	
	
	

	
	
	

}
