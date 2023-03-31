package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.model.Position;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.position.PojoPositionReqInsert;
import com.lawencon.community.pojo.position.PojoPositionReqUpdate;
import com.lawencon.community.pojo.position.PojoPostionRes;

@Service
public class PositionService extends BaseService<PojoPostionRes> {

	private final PositionDao positionDao;

	public PositionService(PositionDao positionDao) {
		this.positionDao = positionDao;

	}

	private void validateBkNotNull(PojoPositionReqInsert position) {
		if (position.getPositionCode() == null) {
			throw new RuntimeException("Position Code cannot be empty.");
		}

	}

	private void validateNonBk(PojoPositionReqInsert position) {

		if (position.getPositionName() == null) {
			throw new RuntimeException("Position Name cannot be empty.");
		}

	}

	private void validateNonBk(PojoPositionReqUpdate position) {

		if (position.getPositionId() == null) {
			throw new RuntimeException("Position ID cannot be empty.");
		}

		if (position.getVer() == null) {
			throw new RuntimeException("Position version cannot be empty.");
		}
		if (position.getPositionName() == null) {
			throw new RuntimeException("Position Name cannot be empty.");
		}

	}

	private void validateBkNotExist(String id) {
		if (positionDao.getById(id).isEmpty()) {
			throw new RuntimeException("Position cannot be empty.");
		}
	}

	@Override
	public List<PojoPostionRes> getAll() {
		final List<PojoPostionRes> positions = new ArrayList<>();
		positionDao.getAll().forEach(data -> {
			PojoPostionRes pojoResGetPostion = new PojoPostionRes();
			pojoResGetPostion.setPositionId(data.getId());
			pojoResGetPostion.setPositionCode(data.getPositionCode());
			pojoResGetPostion.setPositionName(data.getPositionName());
			pojoResGetPostion.setIsActive(data.getIsActive());
			pojoResGetPostion.setVer(data.getVersion());
			positions.add(pojoResGetPostion);
		});
		return positions;

	}

	public PojoRes deleteById(String id) {
		
		validateBkNotExist(id);
		
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

	public PojoInsertRes save(PojoPositionReqInsert data) {
		validateBkNotNull(data);
		validateNonBk(data);
		
		ConnHandler.begin();

		final Position position = new Position();

		position.setPositionName(data.getPositionName());
		position.setPositionCode(data.getPositionCode());

		position.setIsActive(true);

		final Position positionNew = positionDao.save(position);
		ConnHandler.commit();

		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(positionNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	public PojoUpdateRes update(PojoPositionReqUpdate data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			validateNonBk(data);
			
			ConnHandler.begin();
			final Position position = positionDao.getByIdRef(data.getPositionId());
			positionDao.getByIdAndDetach(Position.class, position.getId());
			position.setId(position.getId());
			position.setPositionName(data.getPositionName());
			position.setIsActive(data.getIsActive());
			position.setVersion(data.getVer());
			final Position poistionNew = positionDao.saveAndFlush(position);
			ConnHandler.commit();

			pojoUpdateRes.setId(poistionNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(poistionNew.getVersion());

		} catch (Exception e) {
			e.printStackTrace();
			pojoUpdateRes.setId(data.getPositionId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}

}
