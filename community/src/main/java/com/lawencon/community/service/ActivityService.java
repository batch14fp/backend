package com.lawencon.community.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.ActivityTypeEnum;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.ActivityVoucherDao;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.InvoiceDao;
import com.lawencon.community.dao.SalesSettingDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.VoucherDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityVoucher;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.activity.PojoActivityReqInsert;
import com.lawencon.community.pojo.activity.PojoActivityReqUpdate;
import com.lawencon.community.pojo.activity.PojoActivityRes;
import com.lawencon.community.pojo.activity.PojoUpcomingActivityByTypeRes;
import com.lawencon.community.pojo.report.PojoReportActivityAdminRes;
import com.lawencon.community.pojo.report.PojoReportActivityAdminResData;
import com.lawencon.community.pojo.report.PojoReportActivityMemberRes;
import com.lawencon.community.pojo.report.PojoReportActivityMemberResData;
import com.lawencon.community.pojo.report.PojoReportCountMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminRes;
import com.lawencon.community.pojo.report.PojoReportIncomesAdminResData;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberResData;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedReq;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedRes;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ActivityService {

	private final ActivityDao activityDao;
	private final CategoryDao categoryDao;
	private final ActivityTypeDao activityTypeDao;
	private final VoucherDao voucherDao;
	private final ActivityVoucherDao activityVoucherDao;
	private final UserDao userDao;
	private final SalesSettingDao salesSettingDao;
	private final FileDao fileDao;
	private final InvoiceDao invoiceDao;

	@Autowired
	private PrincipalService principalService;

	public ActivityService(final InvoiceDao invoiceDao, final SalesSettingDao salesSettingDao, final UserDao userDao,
			final ActivityVoucherDao activityVoucherDao, final VoucherDao voucherDao, final ActivityDao activityDao,
			final CategoryDao categoryDao, final ActivityTypeDao activityTypeDao, final FileDao fileDao) {
		this.activityDao = activityDao;
		this.categoryDao = categoryDao;
		this.voucherDao = voucherDao;
		this.activityTypeDao = activityTypeDao;
		this.fileDao = fileDao;
		this.activityVoucherDao = activityVoucherDao;
		this.userDao = userDao;
		this.salesSettingDao = salesSettingDao;
		this.invoiceDao = invoiceDao;

	}

	private void validateNonBk(PojoActivityReqInsert activity) {

		if (activity.getCategoryId() == null) {
			throw new RuntimeException("Activity Category cannot be empty.");
		}
		if (activity.getTypeId() == null) {
			throw new RuntimeException("Activity Type cannot be empty.");
		}
		if (activity.getTitle() == null) {
			throw new RuntimeException("Activity Title cannot be empty.");
		}
		if (activity.getProviders() == null) {

			throw new RuntimeException("Activity Providers cannot be empty.");
		}
		if (activity.getStartDate() == null) {
			throw new RuntimeException("Activity Start Date cannot be empty.");
		}
		if (activity.getEndDate() == null) {
			throw new RuntimeException("Activity End Date cannot be empty.");
		}
	}
	

	
	private void validateBkNotExist(String id) {
		if(activityDao.getById(id).isEmpty()) {
			throw new RuntimeException("Activity cannot be empty.");
		}
	}

	private void validateNonBk(PojoActivityReqUpdate activity) {
		if (activity.getActivityId() == null) {
			throw new RuntimeException("Activity cannot be empty.");
		}
		if (activity.getVer() == null) {
			throw new RuntimeException("Activity version cannot be empty.");
		}
	}
	
	
	

	public List<PojoActivityRes> getAll(int offset, int limit) {
		final List<PojoActivityRes> activityList = new ArrayList<>();

		activityDao.getAll(offset, limit).forEach(data -> {
			PojoActivityRes activity = new PojoActivityRes();
			activity.setActivityId(data.getId());
			activity.setIsBought(getIsBought(data.getId(), principalService.getAuthPrincipal()));
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
			activity.setCreatedAt(data.getCreatedAt());
			activity.setVer(data.getVersion());
			activityList.add(activity);
		});
		return activityList;

	}

	public List<PojoReportActivityMemberResData> getMemberReportFile(final String id, final LocalDate startDate,
			final LocalDate endDate, Integer offset, Integer limit, String typeCode) {
		final List<PojoReportActivityMemberResData> res = new ArrayList<>();
		final User user = userDao.getByIdRef(id);
		final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate, user.getId(), typeCode,
				offset, limit);

		for (int i = 0; i < activityList.size(); i++) {

			final PojoReportActivityMemberResData reportMember = new PojoReportActivityMemberResData();

			reportMember.setNo(i + 1);
			reportMember.setStartDate(GenerateString.getIndonesianDate(activityList.get(i).getStartDate()));	reportMember.setTitle(activityList.get(i).getTitle());
			reportMember.setType(activityList.get(i).getTypeActivity().getActivityName());
			reportMember.setTotalParticipants(getCountParticipant(activityList.get(i).getId(), user.getId()));

			res.add(reportMember);
		}

		return res;

	}

	public PojoReportActivityMemberRes getMemberReport(final LocalDate startDate,

			final LocalDate endDate, Integer offset, Integer limit, String typeCode) {
		final PojoReportActivityMemberRes res = new PojoReportActivityMemberRes();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());

		try {
			final List<PojoReportActivityMemberResData> resList = new ArrayList<>();
			final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate, user.getId(),
					typeCode, offset,

					limit);

			for (int i = 0; i < activityList.size(); i++) {
				final PojoReportActivityMemberResData reportMember = new PojoReportActivityMemberResData();
				reportMember.setNo(i + 1);
				reportMember.setStartDate(GenerateString.getIndonesianDate(activityList.get(i).getStartDate()));
				reportMember.setTitle(activityList.get(i).getTitle());
				reportMember.setType(activityList.get(i).getTypeActivity().getActivityName());
				reportMember.setTotalParticipants(getCountParticipant(activityList.get(i).getId(), user.getId()));
				resList.add(reportMember);
			}
			res.setData(resList);
			res.setTotal(activityDao.getTotalDataAllByDateRange(startDate, endDate,  user.getId(), typeCode));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public List<PojoReportIncomesMemberResData> getMemberIncomesReportFile(final LocalDate startDate,
			final LocalDate endDate, String typeCode, String userId) {
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		final List<PojoReportIncomesMemberResData> res = activityDao.getActivityIncomeByUser(userId,
				percentMember, startDate, endDate, typeCode, null, null);
		return res;

	}

	public PojoReportIncomesMemberRes getMemberIncomesReport(final LocalDate startDate, final LocalDate endDate,
			String typeCode, Integer offset, Integer limit) {
		PojoReportIncomesMemberRes res = new PojoReportIncomesMemberRes();
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		final List<PojoReportIncomesMemberResData> resList = activityDao.getActivityIncomeByUser(user.getId(),
				percentMember, startDate, endDate, typeCode, offset, limit);
		res.setData(resList);
		res.setTotal(activityDao.getTotalActivityIncomeByUser(principalService.getAuthPrincipal(),startDate, endDate, typeCode));
		return res;

	}

	public List<PojoReportIncomesAdminResData> getIncomesReportAdminFile(final LocalDate startDate,
			final LocalDate endDate, String typeCode) {
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		final List<PojoReportIncomesAdminResData> res = activityDao.getActivityIncome(percentMember, startDate, endDate,
				typeCode, null, null);
		return res;
	}

	public PojoReportIncomesAdminRes getIncomesReportAdmin(final LocalDate startDate, final LocalDate endDate,
			String typeCode, Integer offset, Integer limit) {
		PojoReportIncomesAdminRes res = new PojoReportIncomesAdminRes();
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		final List<PojoReportIncomesAdminResData> resList = activityDao.getActivityIncome(percentMember, startDate,
				endDate, typeCode, offset, limit);

		res.setData(resList);
		res.setTotal((activityDao.getTotalDataActivityIncome(startDate, endDate, typeCode)));
		return res;
	}

	public List<PojoReportIncomesMemberResData> getMemberIncomesReportFile(final String userId,
			final LocalDate startDate, final LocalDate endDate, Integer offset, Integer limit, String categoryCode) {
		final Float percentMember = salesSettingDao.getSalesSetting().getSystemIncome();
		final List<PojoReportIncomesMemberResData> res = activityDao.getActivityIncomeByUser(userId, percentMember,
				startDate, endDate, categoryCode, null, null);
		return res;

	}

	public List<PojoReportActivityAdminResData> getAdminReportFile(final LocalDate startDate, final LocalDate endDate,
			final String typeCode) {
		final List<PojoReportActivityAdminResData> res = new ArrayList<>();
		final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate, null, typeCode, null,
				null);
		for (int i = 0; i < activityList.size(); i++) {

			final PojoReportActivityAdminResData reportMember = new PojoReportActivityAdminResData();

			reportMember.setNo(i + 1);
			reportMember.setStartDate(
					Timestamp.valueOf(activityList.get(i).getStartDate()).toLocalDateTime().toLocalDate().toString());
			reportMember.setTitle(activityList.get(i).getTitle());
			reportMember.setMemberName(activityList.get(i).getUser().getProfile().getFullname());
			reportMember.setType(activityList.get(i).getTypeActivity().getActivityName());
			reportMember.setProviderName(activityList.get(i).getProvider());
			reportMember.setTotalParticipants(
					getCountParticipant(activityList.get(i).getId(), activityList.get(i).getUser().getId()));
			res.add(reportMember);
		}
		return res;
	}

	public PojoReportActivityAdminRes getAdminReport(final LocalDate startDate, final LocalDate endDate, Integer offset,
			Integer limit, String typeCode) {
		final List<PojoReportActivityAdminResData> resList = new ArrayList<>();
		final PojoReportActivityAdminRes res = new PojoReportActivityAdminRes();

		final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate, null, typeCode, offset,
				limit);
		for (int i = 0; i < activityList.size(); i++) {

			final PojoReportActivityAdminResData reportMember = new PojoReportActivityAdminResData();

			reportMember.setNo(i + 1);
			reportMember.setStartDate(
					Timestamp.valueOf(activityList.get(i).getStartDate()).toLocalDateTime().toLocalDate().toString());

			reportMember.setTitle(activityList.get(i).getTitle());
			reportMember.setMemberName(activityList.get(i).getUser().getProfile().getFullname());
			reportMember.setType(activityList.get(i).getTypeActivity().getActivityName());
			reportMember.setProviderName(activityList.get(i).getProvider());
			resList.add(reportMember);
		}
		res.setData(resList);
		res.setTotal(activityDao.getTotalDataAllByDateRange(startDate, endDate,  null, typeCode));
		return res;

	}

	public Long getCountParticipant(String activityId, String userId) {
		return activityDao.getTotalParticipanByUserId(activityId, userId);
	}

	public List<PojoActivityRes> getAllBySort(int offset, int limit, String sortType, String title) {
		final List<PojoActivityRes> activityList = new ArrayList<>();
		activityDao.searchActivities(offset, limit, sortType, title).forEach(data -> {
			PojoActivityRes activity = new PojoActivityRes();
			activity.setActivityId(data.getId());
			activity.setIsBought(getIsBought(data.getId(),principalService.getAuthPrincipal()));
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
			activity.setCreatedAt(data.getCreatedAt());
			activity.setVer(data.getVersion());
			activityList.add(activity);
		});
		return activityList;

	}

	public int getTotalCount() {

		return activityDao.getTotalCount();
	}

	public PojoRes deleteById(String id) {
		validateBkNotExist(id);
		
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
		validateNonBk(data);

	
		Voucher voucherNew = null;
		final Activity activity = new Activity();
		final Voucher voucher = new Voucher();
		if (data.getVoucherCode() != null) {
			voucher.setUsedCount(0);
			voucher.setIsActive(true);
			voucher.setVoucherCode(data.getVoucherCode().toUpperCase());
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
		final File file = new File();
		file.setFileExtension(data.getFile().getExtension());
		file.setFileName(GenerateString.generateFileName(data.getFile().getExtension()));
		file.setFileContent(data.getFile().getFileContent());
		file.setIsActive(true);
		final File fileNew = fileDao.save(file);
		activity.setFile(fileNew);
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		activity.setUser(user);
		activity.setProvider(data.getProviders());
		activity.setTypeActivity(activityTypeDao.getByIdRef(data.getTypeId()));
		activity.setIsActive(true);
		final Activity activityNew = activityDao.save(activity);
		if (activityNew != null && voucherNew != null) {
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
		validateNonBk(data);
	
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
			final File file = fileDao.getByIdRef(data.getFile().getFileId());
			file.setFileContent(data.getFile().getFileContent());
			file.setFileExtension(data.getFile().getExtension());
			file.setVersion(data.getFile().getVer());
			final File fileUpdated = fileDao.saveAndFlush(file);
			activity.setFile(fileUpdated);
			activity.setIsActive(data.getIsActive());
			activity.setVersion(data.getVer());

			final Activity activityNew = activityDao.saveAndFlush(activity);
			ConnHandler.commit();
			pojoUpdateRes.setId(activityNew.getId());
			pojoUpdateRes.setMessage("Save Success!");
			pojoUpdateRes.setVer(activityNew.getVersion());

			pojoUpdateRes.setId(data.getActivityId());
			pojoUpdateRes.setMessage("Something wrong,you cannot update this data");
		
		return pojoUpdateRes;

	}

	public PojoActivityRes getById(String id) {
		final PojoActivityRes activity = new PojoActivityRes();
		final Activity data = activityDao.getByIdRef(id);
		activity.setActivityId(data.getId());
		activity.setIsBought(getIsBought(data.getId(), principalService.getAuthPrincipal()));
		activity.setCategoryCode(data.getCategory().getCategoryCode());
		activity.setCategoryName(data.getCategory().getCategoryName());
		activity.setTitle(data.getTitle());
		activity.setContent(data.getDescription());
		activity.setStartDate(data.getStartDate());
		activity.setEndDate(data.getEndDate());
		activity.setPrice(data.getPrice());
		activity.setActivityLocation(data.getActivityLocation());
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

	public List<PojoActivityRes> getListActivityByCategoryAndType(String categoryCode, String typeCode, int offset,
			int limit, String sortType) throws Exception {
		final List<Activity> listActivity = activityDao.getListActivityByCategoryAndType(categoryCode, typeCode, offset,
				limit, null, sortType);
		if (listActivity == null || listActivity.isEmpty()) {
			return null;
		}

		final List<PojoActivityRes> pojoList = new ArrayList<>();
		for (Activity activity : listActivity) {
			final PojoActivityRes pojo = new PojoActivityRes();
			pojo.setActivityId(activity.getId());
			pojo.setTitle(activity.getTitle());
			pojo.setIsBought(getIsBought(activity.getId(), principalService.getAuthPrincipal()));
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
			pojo.setCreatedAt(activity.getCreatedAt());
			pojo.setVer(activity.getVersion());

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

	public List<PojoActivityRes> getByUserIdActivityByCategoryAndType(String categoryCode, String typeCode, int offset,
			int limit, String sortType) throws Exception {
		final List<Activity> listActivity = activityDao.getListActivityByCategoryAndType(categoryCode, typeCode, offset,
				limit, principalService.getAuthPrincipal(),sortType );
		if (listActivity == null || listActivity.isEmpty()) {
			return null;
		}

		final List<PojoActivityRes> pojoList = new ArrayList<>();
		for (Activity activity : listActivity) {
			final PojoActivityRes pojo = new PojoActivityRes();
			pojo.setActivityId(activity.getId());
			pojo.setIsBought(getIsBought(activity.getId(), principalService.getAuthPrincipal()));
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
			pojo.setCreatedAt(activity.getCreatedAt());
			pojo.setVer(activity.getVersion());

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

	public List<PojoActivityRes> getListActivityByListCategoryAndType(final List<String> categoryCodes,
			final String typeCode, final int offset, final int limit, final String sortType) {
		final List<Activity> listActivity = activityDao.getListActivityByCategoriesAndType(categoryCodes, typeCode,
				offset, limit, sortType);

		if (listActivity == null || listActivity.isEmpty()) {
			return null;
		}
		final List<PojoActivityRes> pojoList = new ArrayList<>();
		for (Activity activity : listActivity) {
			final PojoActivityRes pojo = new PojoActivityRes();
			pojo.setActivityId(activity.getId());
			pojo.setIsBought(getIsBought(activity.getId(), principalService.getAuthPrincipal()));
			pojo.setTitle(activity.getTitle());
			pojo.setContent(activity.getDescription());
			pojo.setActivityLocation(activity.getActivityLocation());
			pojo.setCategoryCode(activity.getCategory().getCategoryCode());
			pojo.setCategoryName(activity.getCategory().getCategoryName());
			pojo.setTypeCode(activity.getTypeActivity().getTypeCode());
			pojo.setTypeName(activity.getTypeActivity().getActivityName());
			pojo.setProviders(activity.getProvider());
			pojo.setPrice(activity.getPrice());
			pojo.setStartDate(activity.getStartDate());
			pojo.setEndDate(activity.getEndDate());
			pojo.setIsActive(activity.getIsActive());
			pojo.setCreatedAt(activity.getCreatedAt());
			pojo.setVer(activity.getVersion());
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
	
	
	public Boolean getIsBought(String activityId, String userId) {
		return invoiceDao.getIsBought(activityId, userId);
	}

	public PojoUpcomingActivityByTypeRes getUpcomingEvent(final int offset, final int limit, final String typeCode) {
		final PojoUpcomingActivityByTypeRes res = activityDao.getAllUpcomingActivity(offset, limit, typeCode);
		return res;
	}

	public PojoReportCountMemberRes getTotalData() {
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		PojoReportCountMemberRes res = new PojoReportCountMemberRes();
		res.setTotalParticipantEvent(activityDao.getTotalParticipanByUserIdByType(ActivityTypeEnum.EVENT.getCode(),
				principalService.getAuthPrincipal()));
		res.setTotalParticipantCourse(activityDao.getTotalParticipanByUserIdByType(ActivityTypeEnum.COURSE.getCode(),
				principalService.getAuthPrincipal()));
		res.setTotalIncomes(activityDao.getTotalIncomeByUserId(principalService.getAuthPrincipal(), percentMember));
		res.setTotalAllParticipant(
				activityDao.getTotalParticipanByUserIdByType(null, principalService.getAuthPrincipal()));
		return res;

	}

	public PojoVoucherAppliedRes getVoucherApplied(PojoVoucherAppliedReq data) {
		PojoVoucherAppliedRes res = new PojoVoucherAppliedRes();

		final List<ActivityVoucher> acticvityVoucherList = activityVoucherDao
				.getListActivityVoucher(data.getActivityId());

		acticvityVoucherList.forEach(activityVoucher -> {
			LocalDate expDate = activityVoucher.getVoucher().getExpDate();
			if (activityVoucher.getVoucher().getVoucherCode().equalsIgnoreCase(data.getVoucherCode())
					&& expDate.isAfter(LocalDate.now())) {
				if (activityVoucher.getVoucher().getLimitApplied() > activityVoucher.getVoucher().getUsedCount()) {
					res.setIsAllowed(true);
					Voucher voucher = voucherDao.getByCode(data.getVoucherCode().toUpperCase()).get();
					res.setVoucherId(voucher.getId());

				} else {
					res.setIsAllowed(false);

				}
			} else {
				res.setIsAllowed(false);

			}

		});

		return res;

	}

}
