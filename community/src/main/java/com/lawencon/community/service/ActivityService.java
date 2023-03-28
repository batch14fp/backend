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
import com.lawencon.community.pojo.report.PojoReportActivityAdminResData;
import com.lawencon.community.pojo.report.PojoReportActivityMemberResData;
import com.lawencon.community.pojo.report.PojoReportCountMemberRes;
import com.lawencon.community.pojo.report.PojoReportIncomesMemberResData;
import com.lawencon.community.pojo.report.PojoResportIncomesAdminResData;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedReq;
import com.lawencon.community.pojo.voucher.PojoVoucherAppliedRes;
import com.lawencon.community.util.GenerateString;
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
	private final SalesSettingDao salesSettingDao;

	private final FileDao fileDao;

	public ActivityService(final SalesSettingDao salesSettingDao, final UserDao userDao,
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
			activity.setCreatedAt(data.getCreatedAt());
			activity.setVer(data.getVersion());
			activityList.add(activity);
		});
		return activityList;

	}

	public List<PojoReportActivityMemberResData> getMemberReport(final String id, final LocalDate startDate,
		final LocalDate endDate, Integer offset, Integer limit) {
		final List<PojoReportActivityMemberResData> res = new ArrayList<>();
		final User user = userDao.getByIdRef(id);
		final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate, user.getId(), offset,
				limit);
		for (int i = 0; i < activityList.size(); i++) {

			final PojoReportActivityMemberResData reportMember = new PojoReportActivityMemberResData();

			reportMember.setNo(i + 1);

			reportMember.setStartDate(activityList.get(i).getStartDate().toString());
			reportMember.setTitle(activityList.get(i).getTitle());
			reportMember.setType(activityList.get(i).getTypeActivity().getActivityName());
			reportMember.setTotalParticipants(getCountParticipant(activityList.get(i).getId(), user.getId()));

			res.add(reportMember);
		}

		return res;

	}

	public List<PojoReportIncomesMemberResData> getMemberIncomesReport(final LocalDate startDate, final LocalDate endDate,
			String typeCode) {
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		final List<PojoReportIncomesMemberResData> res = activityDao.getActivityIncomeByUser(user.getId(), percentMember,
				startDate, endDate, typeCode);
		return res;

	}

	public List<PojoResportIncomesAdminResData> getIncomesReportAdmin(final LocalDate startDate, final LocalDate endDate,
			String typeCode) {
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		final List<PojoResportIncomesAdminResData> res = activityDao.getActivityIncome(percentMember, startDate, endDate,
				typeCode);
		return res;
	}

	public List<PojoReportIncomesMemberResData> getMemberIncomesReportFile(final String userId, final LocalDate startDate,
			final LocalDate endDate, Integer offset, Integer limit, String categoryCode) {
		final Float percentMember = salesSettingDao.getSalesSetting().getSystemIncome();
		final List<PojoReportIncomesMemberResData> res = activityDao.getActivityIncomeByUser(userId, percentMember,
				startDate, endDate, categoryCode);
		return res;

	}

	public List<PojoReportActivityAdminResData> getAdminReport(final LocalDate startDate, final LocalDate endDate,
			Integer offset, Integer limit) {
		final List<PojoReportActivityAdminResData> res = new ArrayList<>();
		final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate, null, offset, limit);
		for (int i = 0; i < activityList.size(); i++) {

			final PojoReportActivityAdminResData reportMember = new PojoReportActivityAdminResData();

			reportMember.setNo(i + 1);
			reportMember.setStartDate(
					Timestamp.valueOf(activityList.get(i).getStartDate()).toLocalDateTime().toLocalDate());
			reportMember.setTitle(activityList.get(i).getTitle());
			reportMember.setMemberName(activityList.get(i).getUser().getProfile().getFullname());
			reportMember.setType(activityList.get(i).getTypeActivity().getActivityName());
			reportMember.setProviderName(activityList.get(i).getProvider());
			res.add(reportMember);
		}

		return res;

	}

//	public List<PojoReportActivityMemberRes> getMemberReportFile(final LocalDate startDate,final LocalDate endDate, Integer offset, Integer limit){
//		final JasperUtil jasperUtil = new JasperUtil();
//		final  List<PojoReportActivityMemberRes> res = new ArrayList<>();
//		try {
//		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
//		final List<Activity> activityList = activityDao.getAllByDateRange(startDate, endDate,user.getId(), offset, limit );
//		
//		
//		
//		for (int  i=0;i<activityList.size();i++) {
//
//			final PojoReportActivityMemberRes reportMember = new PojoReportActivityMemberRes();
//			
//			reportMember.setNo(i+1);
//			reportMember.setStartDate(activityList.get(i).getStartDate().toString());
//			reportMember.setTitle(activityList.get(i).getTitle());
//			reportMember.setTotalParticipants(getCountParticipant(activityList.get(i).getId(),user.getId() ));
//			
//			res.add(reportMember);
//		}
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("reportTitle", "Report Activity Member");
//		params.put("createdBy", "WeCommunity");
//		
//		byte[] reportBytes = jasperUtil.responseToByteArray(res, params, "report_activity_member");
//		
//		String outputFileName = "report_activity_member.pdf";
//		File outputFile = new File(outputFileName);
//		FileOutputStream outputStream = new FileOutputStream(outputFile);
//		outputStream.write(reportBytes);
//		outputStream.close();
//		
//		System.out.println("Jasper report generated successfully.");
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//			
//		return res;
//		
//	}
//	
//	
	public Long getCountParticipant(String activityId, String userId) {
		return activityDao.getTotalParticipanByUserId(activityId, userId);
	}

	public List<PojoActivityRes> getAllBySort(int offset, int limit, String sortType, String title) {
		final List<PojoActivityRes> activityList = new ArrayList<>();
		activityDao.searchActivities(offset, limit, sortType, title).forEach(data -> {
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
			int limit) throws Exception {
		final List<Activity> listActivity = activityDao.getListActivityByCategoryAndType(categoryCode, typeCode, offset,
				limit);
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

	public List<PojoActivityRes> getListActivityByListCategoryAndType(final List<String> categoryCodes ,
			final String typeCode, final int offset, final int limit) {
		final List<Activity> listActivity = activityDao.getListActivityByCategoriesAndType(categoryCodes,typeCode,
				offset, limit);

		if (listActivity == null || listActivity.isEmpty()) {
			return null;
		}
		final List<PojoActivityRes> pojoList = new ArrayList<>();
		for (Activity activity : listActivity) {
			final PojoActivityRes pojo = new PojoActivityRes();
			pojo.setActivityId(activity.getId());
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
	
	
	public PojoUpcomingActivityByTypeRes getUpcomingEvent(final int offset, final int limit, final String typeCode){
		final  PojoUpcomingActivityByTypeRes res = activityDao.getAllUpcomingActivity(offset, limit, typeCode);
		return res;
	}
	public PojoReportCountMemberRes getTotalData() {
		final Float percentMember = salesSettingDao.getSalesSetting().getMemberIncome();
		PojoReportCountMemberRes res = new PojoReportCountMemberRes();
		res.setTotalParticipantEvent(activityDao.getTotalParticipanByUserIdByType(ActivityTypeEnum.EVENT.getCode(), principalService.getAuthPrincipal()));
		res.setTotalParticipantCourse(activityDao.getTotalParticipanByUserIdByType(ActivityTypeEnum.COURSE.getCode(), principalService.getAuthPrincipal()));
		res.setTotalIncomes(activityDao.getTotalIncomeByUserId(principalService.getAuthPrincipal(), percentMember));
		res.setTotalAllParticipant(activityDao.getTotalParticipanByUserIdByType(null, principalService.getAuthPrincipal()));
		return res;
		
	}
	
	
	public PojoVoucherAppliedRes getVoucherApplied(PojoVoucherAppliedReq data) {
		final PojoVoucherAppliedRes res = new PojoVoucherAppliedRes();

		final List <ActivityVoucher> acticvityVoucherList = activityVoucherDao.getListActivityVoucher(data.getActivityId());
		acticvityVoucherList.forEach(activityVoucher->{
			LocalDate expDate = activityVoucher.getVoucher().getExpDate();
			  if (activityVoucher.getVoucher().getVoucherCode().equalsIgnoreCase(data.getVoucherCode()) && expDate.isBefore(LocalDate.now())) {
				     	if(activityVoucher.getVoucher().getLimitApplied()< activityVoucher.getVoucher().getUsedCount()) {
					res.setIsAllowed(true);
					   throw new IllegalStateException("Coupon code applied successfully ");
					
				}
				else {
					res.setIsAllowed(false);
					 throw new IllegalStateException("Sorry, the usage limit for this voucher code has been reached. It can no longer be used");
				}
			}
			else {
				res.setIsAllowed(false);
				 throw new IllegalStateException("Sorry, the voucher code you entered is not valid or has expired. Please try again with a different voucher code.");
			}
			
		});
		
		return res;
		
	}
	
	
}
