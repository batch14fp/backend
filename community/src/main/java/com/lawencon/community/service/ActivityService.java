package com.lawencon.community.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.ActivityVoucherDao;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityVoucher;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.activity.PojoActivityReqInsert;
import com.lawencon.community.pojo.activity.PojoActivityReqUpdate;
import com.lawencon.community.pojo.activity.PojoActivityRes;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ActivityService {

	@Autowired
	private PrincipalService principalService;

	private final ActivityDao activityDao;
	private final CategoryDao categoryDao;
	private final ActivityTypeDao activityTypeDao;
	private final VoucherDao voucherDao;
	private final ActivityVoucherDao activityVoucherDao;
	private final UserDao userDao;

	private final FileDao fileDao;

	public ActivityService(final UserDao userDao, final ActivityVoucherDao activityVoucherDao,
			final VoucherDao voucherDao, final ActivityDao activityDao, final CategoryDao categoryDao,
			final ActivityTypeDao activityTypeDao, final FileDao fileDao) {
		this.activityDao = activityDao;
		this.categoryDao = categoryDao;
		this.voucherDao = voucherDao;
		this.activityTypeDao = activityTypeDao;
		this.fileDao = fileDao;
		this.activityVoucherDao = activityVoucherDao;
		this.userDao = userDao;

	}

	public List<PojoActivityRes> getAll(int offset, int limit) {
		final List<PojoActivityRes> activityList = new ArrayList<>();

		activityDao.getAll(offset, limit).forEach(data -> {
			PojoActivityRes activity = new PojoActivityRes();
			activity.setActivityId(data.getId());
			activity.setCategoryCode(data.getCategory().getCategoryCode());
			activity.setCategoryName(data.getCategory().getCategoryName());
			activity.setTitle(data.getTitle());
			activity.setUserId(data.getUser().getId());
			activity.setFullname(data.getUser().getProfile().getFullname());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setActivityLocation(data.getActivityLocation());
			activity.setContent(data.getDescription());
			activity.setPrice(data.getPrice());
			activity.setProviders(data.getProvider());
			activity.setTypeCode(data.getTypeActivity().getTypeCode());
			activity.setTypeName(data.getTypeActivity().getActivityName());
			activity.setImgActivityId(data.getFile().getId());
			activity.setIsActive(data.getIsActive());
			activityList.add(activity);
		});
		return activityList;

	}

	public List<PojoActivityRes> getAllByHighestPrice(int offset, int limit) {
		final List<PojoActivityRes> activityList = new ArrayList<>();
		activityDao.getAllByHighestPrice(offset, limit).forEach(data -> {
			PojoActivityRes activity = new PojoActivityRes();
			activity.setActivityId(data.getId());
			activity.setCategoryCode(data.getCategory().getCategoryCode());
			activity.setCategoryName(data.getCategory().getCategoryName());
			activity.setTitle(data.getTitle());
			activity.setUserId(data.getUser().getId());
			activity.setFullname(data.getUser().getProfile().getFullname());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setActivityLocation(data.getActivityLocation());
			activity.setContent(data.getDescription());
			activity.setPrice(data.getPrice());
			activity.setProviders(data.getProvider());
			activity.setTypeCode(data.getTypeActivity().getTypeCode());
			activity.setTypeName(data.getTypeActivity().getActivityName());
			activity.setImgActivityId(data.getFile().getId());
			activity.setIsActive(data.getIsActive());
			activityList.add(activity);
		});
		return activityList;

	}

	public List<PojoActivityRes> getAllByLowestPrice(int offset, int limit) {
		final List<PojoActivityRes> activityList = new ArrayList<>();
		activityDao.getAllByLowestPrice(offset, limit).forEach(data -> {
			PojoActivityRes activity = new PojoActivityRes();
			activity.setActivityId(data.getId());
			activity.setCategoryCode(data.getCategory().getCategoryCode());
			activity.setCategoryName(data.getCategory().getCategoryName());
			activity.setTitle(data.getTitle());
			activity.setUserId(data.getUser().getId());
			activity.setFullname(data.getUser().getProfile().getFullname());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setActivityLocation(data.getActivityLocation());
			activity.setContent(data.getDescription());
			activity.setPrice(data.getPrice());
			activity.setProviders(data.getProvider());
			activity.setTypeCode(data.getTypeActivity().getTypeCode());
			activity.setTypeName(data.getTypeActivity().getActivityName());
			activity.setImgActivityId(data.getFile().getId());
			activity.setIsActive(data.getIsActive());
			activityList.add(activity);
		});
		return activityList;

	}

	public int getTotalCount() {

		return activityDao.getTotalCount();
	}

	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = activityDao.deleteById(Activity.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}
	}

	public PojoInsertRes save(PojoActivityReqInsert data) {
		ConnHandler.begin();
		Voucher voucherNew = null;
		final Activity activity = new Activity();
		final Voucher voucher = new Voucher();
		if (data.getVoucherCode() != null && data.getLimitApplied() != null) {
			voucher.setUsedCount(0);
			voucher.setIsActive(true);
			voucher.setVoucherCode(data.getVoucherCode());
			voucher.setLimitApplied(data.getLimitApplied());
			voucher.setExpDate(data.getEndAt());
			voucher.setDiscountPercent((data.getDiscountPercent() / 100));
			voucherNew = voucherDao.save(voucher);

		}
		activity.setCategory(categoryDao.getByIdRef(data.getCategoryId()));
		activity.setTitle(data.getTitle());
		activity.setStartDate(data.getStartDate());
		activity.setEndDate(data.getEndDate());
		activity.setActivityLocation(data.getActivityLocation());
		activity.setDescription(data.getContent());
		activity.setPrice(data.getPrice());
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		activity.setUser(user);
		activity.setProvider(data.getProviders());
		activity.setTypeActivity(activityTypeDao.getByIdRef(data.getTypeId()));
		activity.setFile(fileDao.getByIdRef(data.getImgActivityId()));
		activity.setIsActive(true);
		final Activity activityNew = activityDao.save(activity);

		if (activityNew.getId() != null && voucherNew.getId() != null) {
			final ActivityVoucher activityVoucher = new ActivityVoucher();
			activityVoucher.setActivity(activityNew);
			activityVoucher.setVoucher(voucherNew);
			activityVoucherDao.save(activityVoucher);
		}

		ConnHandler.commit();
		final PojoInsertRes pojoRes = new PojoInsertRes();
		pojoRes.setId(activityNew.getId());
		pojoRes.setMessage("Save Success!");
		return pojoRes;
	}

	public PojoUpdateRes update(PojoActivityReqUpdate data) {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		try {
			ConnHandler.begin();
			final Activity activity = activityDao.getByIdRef(data.getActivityId());
			activityDao.getByIdAndDetach(Activity.class, activity.getId());
			activity.setCategory(categoryDao.getByIdRef(data.getCategoryId()));
			activity.setTitle(data.getTitle());
			activity.setDescription(data.getContent());
			activity.setStartDate(data.getStartDate());
			activity.setEndDate(data.getEndDate());
			activity.setPrice(data.getPrice());
			activity.setActivityLocation(data.getActivityLocation());
			activity.setProvider(data.getProviders());
			activity.setTypeActivity(activityTypeDao.getByIdRef(data.getTypeId()));
			activity.setFile(fileDao.getByIdRef(data.getImgActivityId()));
			activity.setIsActive(data.getIsActive());
			activity.setVersion(data.getVer());

			final Activity activityNew = activityDao.saveAndFlush(activity);
			ConnHandler.commit();
			pojoUpdateRes.setId(activityNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(activityNew.getVersion());

		} catch (Exception e) {
			pojoUpdateRes.setId(data.getActivityId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		}
		return pojoUpdateRes;

	}

	public PojoActivityRes getById(String id) {
		final PojoActivityRes activity = new PojoActivityRes();
		final Activity data = activityDao.getByIdRef(id);
		activity.setActivityId(data.getId());
		activity.setCategoryCode(data.getCategory().getCategoryCode());
		activity.setCategoryName(data.getCategory().getCategoryName());
		activity.setTitle(data.getTitle());
		activity.setStartDate(data.getStartDate());
		activity.setEndDate(data.getEndDate());
		activity.setPrice(data.getPrice());
		activity.setProviders(data.getProvider());
		activity.setTypeCode(data.getTypeActivity().getTypeCode());
		activity.setTypeName(data.getTypeActivity().getActivityName());
		activity.setUserId(data.getUser().getId());
		activity.setFullname(data.getUser().getProfile().getFullname());
		activity.setActivityLocation(data.getActivityLocation());
		activity.setImgActivityId(data.getFile().getId());
		activity.setIsActive(data.getIsActive());
		return activity;

	}

	public List<PojoActivityRes> getListActivityByCategoryAndType(String categoryCode, String typeCode)
			throws Exception {
		final List<Activity> listActivity = activityDao.getListActivityByCategoryAndType(categoryCode, typeCode);
		if (listActivity == null || listActivity.isEmpty()) {
			return null;
		}

		final List<PojoActivityRes> pojoList = new ArrayList<>();
		for (Activity activity : listActivity) {
			final PojoActivityRes pojo = new PojoActivityRes();
			pojo.setActivityId(activity.getId());
			pojo.setTitle(activity.getTitle());
			pojo.setContent(activity.getDescription());
			pojo.setCategoryCode(activity.getCategory().getCategoryCode());
			pojo.setCategoryName(activity.getCategory().getCategoryName());
			pojo.setTypeCode(activity.getTypeActivity().getTypeCode());
			pojo.setTypeName(activity.getTypeActivity().getActivityName());
			pojo.setProviders(activity.getProvider());
			pojo.setPrice(activity.getPrice());
			pojo.setStartDate(activity.getStartDate());
			pojo.setEndDate(activity.getEndDate());
			pojo.setIsActive(activity.getIsActive());

			final LocalDateTime now = LocalDateTime.now();
			final Duration duration = Duration.between(activity.getCreatedAt(), now);
			String timeAgo = "";
			if (duration.toDays() > 0) {
				timeAgo = duration.toDays() + "d";
			} else if (duration.toHours() > 0) {
				timeAgo = duration.toHours() + "h";
			} else if (duration.toMinutes() > 0) {
				timeAgo = duration.toMinutes() + "m";
			} else {
				timeAgo = duration.getSeconds() + "s";
			}
			pojo.setTimeAgo(timeAgo);

			if (activity.getUser() != null) {
				String fullname = activity.getUser().getProfile().getFullname();
				pojo.setUserId(activity.getUser().getId());
				pojo.setFullname(fullname);
			}

			String imgActivityId = activity.getFile() != null ? activity.getFile().getId() : null;
			pojo.setImgActivityId(imgActivityId);

			pojoList.add(pojo);
		}

		return pojoList;
	}

}
