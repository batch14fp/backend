package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.PollingDao;
import com.lawencon.community.dao.PollingOptionDao;
import com.lawencon.community.dao.PollingResponDao;
import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.PostTypeDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingOption;
import com.lawencon.community.model.PollingRespon;
import com.lawencon.community.model.Post;
import com.lawencon.community.model.PostType;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.post.PojoOptionCountRes;
import com.lawencon.community.pojo.post.PojoPollingOptionReqInsert;
import com.lawencon.community.pojo.post.PojoPollingOptionReqUpdate;
import com.lawencon.community.pojo.post.PojoPollingReqInsert;
import com.lawencon.community.pojo.post.PojoPollingReqUpdate;
import com.lawencon.community.pojo.post.PojoPollingResponReq;
import com.lawencon.community.pojo.post.PojoPollingResponRes;
import com.lawencon.security.principal.PrincipalService;

@Service
public class PollingService {
	

	@Autowired
	private PrincipalService principalService;
	
	
	private PostDao postDao;
	private PollingDao pollingDao;
	private PostTypeDao postTypeDao;
	private PollingOptionDao pollingOptionDao;
	private UserDao userDao;
	private PollingResponDao pollingResponDao;
	
	public PollingService(final  PollingResponDao pollingResponDao, final UserDao userDao, final PostTypeDao postTypeDao, final PostDao postDao, final PollingDao pollingDao, final PollingOptionDao pollingOptionDao) {
		this.pollingOptionDao = pollingOptionDao;
		this.pollingDao = pollingDao;
		this.postDao  = postDao;
		this.postTypeDao = postTypeDao;
		this.userDao =userDao;
		this.pollingResponDao = pollingResponDao;
		
	}
	
	private void validateBkNotExist(String id) {
		if (pollingDao.getById(id).isEmpty()) {
			throw new RuntimeException("Polling cannot be empty.");
		}
	}
	
	public PojoInsertRes save(PojoPollingReqInsert data) {
	    ConnHandler.begin();

	    try {
	        final Polling polling = new Polling();
	        polling.setTitle(data.getPollingTitle());
	        polling.setEndAt(data.getEndAt());
	        polling.setIsOpen(true);
	        
	        final Post post = postDao.getByIdRef(data.getPostId());
	        post.setPolling(polling);
	        postDao.getByIdAndDetach(Post.class, post.getId());

	        final PostType postType = postTypeDao.getByIdRef(post.getPostType().getId());
	        post.setPostType(postType);

	        final Polling pollingNew = pollingDao.save(polling);

	        final List<PollingOption> options = new ArrayList<>();
	        for (PojoPollingOptionReqInsert option : data.getPollingOptions()) {
	            final PollingOption pollingOption = new PollingOption();
	            pollingOption.setPolling(pollingNew);
	            pollingOption.setContentPolling(option.getPollingContent());
	            options.add(pollingOption);
	        }
	        pollingOptionDao.saveAll(options);

	        ConnHandler.commit();

	        final PojoInsertRes res = new PojoInsertRes();
	        res.setId(pollingNew.getId());
	        res.setMessage("Save Success!");
	        return res;

	    } catch (Exception e) {
	        ConnHandler.rollback();
	        throw e;
	    }
	}
	
	
	public PojoPollingResponRes insertOptionPolling(PojoPollingResponReq data) throws Exception {
		ConnHandler.begin();
		final PollingRespon pollingRespon = new PollingRespon();
		final PollingOption pollingOption= pollingOptionDao.getByIdRef(data.getPollingOptionId());
		pollingRespon.setPollingOption(pollingOption);
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		pollingRespon.setUser(user);
		pollingRespon.setIsActive(true);
		pollingResponDao.save(pollingRespon);
		ConnHandler.commit();
		
		
		
	    final PojoPollingResponRes res = new PojoPollingResponRes();
		
			final List<PojoOptionCountRes> pollingOptionUserCounts = pollingOptionDao.countPollingOptionUsers(pollingOption.getPolling().getId());
		        res.setData(pollingOptionUserCounts);
		        res.setTotalRespondents(pollingOptionDao.countTotalPollingUsers(pollingOption.getPolling().getId()));
		        res.setTotalOption(pollingOptionDao.countTotalPollingUsers(pollingOption.getPolling().getId()));
		     
		        return res;
		

	}

	public PojoUpdateRes update(PojoPollingReqUpdate data) {
	    ConnHandler.begin();

	    try {
	        final Polling polling  = pollingDao.getByIdRef(data.getPollingId());
	        pollingDao.getByIdAndDetach(Polling.class, polling.getId());
	        polling.setEndAt(data.getEndAt());
	        polling.setTitle(data.getPollingTitle());

	        final List<PollingOption> options = new ArrayList<>();
	        for (PojoPollingOptionReqUpdate option : data.getPollingOptions()) {
	            final PollingOption pollingOption = pollingOptionDao.getByIdRef(option.getPollingOptionId());
	            pollingOptionDao.getByIdAndDetach(PollingOption.class, pollingOption.getId());
	            pollingOption.setPolling(polling);
	            pollingOption.setId(pollingOption.getId());
	            pollingOption.setContentPolling(option.getPollingOptionContent());
	            options.add(pollingOption);
	        }

	        pollingOptionDao.saveAll(options);

	        
	        
	      
	        ConnHandler.commit();

	        final PojoUpdateRes res = new PojoUpdateRes();
	        res.setId(polling.getId());
	        res.setMessage("Update Success!");
	        return res;

	    } catch (Exception e) {
	        ConnHandler.rollback();
	        throw e;
	    }
	}
	
	
	
	public PojoRes delete(String id) throws Exception {
		validateBkNotExist(id);
		
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		final List<PollingOption> pollingOptions = pollingOptionDao.getAllOptionByPollingId(id);;
		
		pollingOptionDao.delete(pollingOptions.getClass());
		
		Boolean result = pollingDao.deleteById(Polling.class, id);
	
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}

	
}
