package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.BankPaymentDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.ProfileSocialMediaDao;
import com.lawencon.community.dao.SocialMediaDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.WalletDao;
import com.lawencon.community.model.BankPayment;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Wallet;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoProfileDetailRes;
import com.lawencon.community.pojo.profile.PojoProfileReqUpdate;
import com.lawencon.community.pojo.socialmedia.PojoSocialMediaRes;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ProfileService {
	private ProfileDao profileDao;
	private UserDao userDao;
	private SubscriptionDao subscriptionDao;
	private ProfileSocialMediaDao profileSocialMediaDao;
	private IndustryDao industryDao;
	private PositionDao positionDao;
	private FileDao fileDao;
	private BankPaymentDao bankPaymentDao;
	private WalletDao walletDao;

	@Autowired
	private PrincipalService principalService;

	public ProfileService(final WalletDao walletDao, final BankPaymentDao bankPaymentDao,
			final SubscriptionDao subscriptionDao, final SocialMediaDao socialMediaDao, final FileDao fileDao,
			final PositionDao positionDao, final IndustryDao industryDao,
			final ProfileSocialMediaDao profileSocialMediaDao, final ProfileDao profileDao, final UserDao userDao) {
		this.profileDao = profileDao;
		this.userDao = userDao;
		this.fileDao = fileDao;
		this.profileSocialMediaDao = profileSocialMediaDao;
		this.subscriptionDao = subscriptionDao;
		this.industryDao = industryDao;
		this.positionDao = positionDao;
		this.bankPaymentDao = bankPaymentDao;
		this.walletDao = walletDao;

	}

	public PojoProfileDetailRes getDetailProfile() throws Exception {
		final User user = userDao.getByIdRef(principalService.getAuthPrincipal());
		final Profile profile = profileDao.getByIdRef(user.getProfile().getId());
		final PojoProfileDetailRes resGetProfile = new PojoProfileDetailRes();
		resGetProfile.setUserId(principalService.getAuthPrincipal());
		resGetProfile.setProfileId(profile.getId());
		resGetProfile.setFullname(profile.getFullname());
		resGetProfile.setEmail(user.getEmail());
		resGetProfile.setVer(profile.getVersion());
		resGetProfile.setCompany(profile.getCompanyName());
		final Subscription subs = subscriptionDao.getByProfileId(profile.getId()).get();
		final Subscription subsRef = subscriptionDao.getByIdRef(subs.getId());
		resGetProfile.setStatusMemberId(subsRef.getMemberStatus().getId());
		resGetProfile.setStatusMember(subsRef.getMemberStatus().getStatusName());
		resGetProfile.setStartDateMember(subsRef.getStartDate());
		resGetProfile.setEndDateMember(subsRef.getEndDate());
		resGetProfile.setIndustryId(profile.getIndustry().getId());
		resGetProfile.setPositionId(profile.getPosition().getId());
		resGetProfile.setProvince(profile.getProvince());
		resGetProfile.setCountry(profile.getCountry());
		if (profile.getImageProfile() != null) {
			resGetProfile.setImageId(profile.getImageProfile().getId());
			resGetProfile.setImageVer(profile.getImageProfile().getVersion());

		}
		resGetProfile.setUserBalance(user.getWallet().getBalance());
		resGetProfile.setWalletId(user.getWallet().getId());
		resGetProfile.setWalletVer(user.getWallet().getVersion());
		if (user.getWallet().getBankPayment() != null) {
			resGetProfile.setAccountName(user.getWallet().getBankPayment().getAccountName());
			resGetProfile.setBankPanymentId(user.getWallet().getBankPayment().getId());
			resGetProfile.setAccountNumber(user.getWallet().getBankPayment().getAccountNumber());
		}
		resGetProfile.setCity(user.getProfile().getCity());
		final List<PojoSocialMediaRes> socialMediaList = new ArrayList<>();

		profileSocialMediaDao.getSocialMediaByProfileId(profile.getId()).forEach(data -> {
			final PojoSocialMediaRes socialMedia = new PojoSocialMediaRes();
			socialMedia.setProfileSocialMediaId(data.getId());
			socialMedia.setPlatformName(data.getSocialMedia().getPlatformName());
			socialMedia.setSocialMediaId(data.getSocialMedia().getId());
			socialMedia.setUrl(data.getUrl());
			socialMedia.setVer(data.getVersion());
			socialMedia.setIsActive(data.getIsActive());
			socialMediaList.add(socialMedia);
		});

		resGetProfile.setSocialMediaList(socialMediaList);
		resGetProfile.setPostalCode(user.getProfile().getPostalCode());
		resGetProfile.setPhoneNumber(user.getProfile().getPhoneNumber());
		resGetProfile.setDob(user.getProfile().getDob());
		return resGetProfile;
	}

	public PojoUpdateRes update(PojoProfileReqUpdate data) throws Exception {
		final PojoUpdateRes pojoUpdateRes = new PojoUpdateRes();
		ConnHandler.begin();

		final Profile profile = profileDao.getByIdRef(data.getProfileId());
		profileDao.getByIdAndDetach(Profile.class, profile.getId());
		profile.setId(profile.getId());
		profile.setFullname(data.getFullname());
		profile.setCompanyName(data.getCompany());
		profile.setCountry(data.getCountry());
		profile.setCity(data.getCity());

		if (data.getFile() != null) {
			File file = new File();
			if (data.getFile().getFileId() != null) {
				file = fileDao.getByIdRef(data.getFile().getFileId());
				file.setVersion(data.getFile().getVer());
			}
			file.setFileExtension(data.getFile().getExtension());
			file.setFileContent(data.getFile().getFileContent());
			file.setFileName(GenerateString.generateFileName(data.getFile().getExtension()));
			file = fileDao.save(file);
			profile.setImageProfile(file);

		}

		BankPayment bankPaymentRef = new BankPayment();
		Optional<Wallet> walletOptional = walletDao.getByUserId(principalService.getAuthPrincipal());
		if (walletOptional.isPresent()) {
			Wallet walletRef = walletDao.getByIdRef(walletOptional.get().getId());
			if (walletRef.getBankPayment() != null) {
				bankPaymentRef = bankPaymentDao.getByIdRef(walletRef.getBankPayment().getId());
				bankPaymentDao.getByIdAndDetach(bankPaymentRef.getId());
				bankPaymentRef.setVersion(data.getBankUserAccount().getVer());
				bankPaymentRef.setAccountNumber(data.getBankUserAccount().getAccountNumber());
				bankPaymentRef.setAccountName(data.getBankUserAccount().getAccountName());
				bankPaymentRef.setBankName(data.getBankUserAccount().getBankPaymentName());
				final BankPayment bankPaymentUpdated = bankPaymentDao.save(bankPaymentRef);
				walletRef.setBankPayment(bankPaymentUpdated);
				walletRef.setVersion(data.getWalletVer());
				walletDao.saveAndFlush(walletRef);
			} else if (data.getBankUserAccount().getAccountNumber() != null) {
				bankPaymentRef.setAccountNumber(data.getBankUserAccount().getAccountNumber());
				bankPaymentRef.setAccountName(data.getBankUserAccount().getAccountName());
				bankPaymentRef.setBankName(data.getBankUserAccount().getBankPaymentName());
				final BankPayment bankPaymentSave = bankPaymentDao.save(bankPaymentRef);
				walletRef.setBankPayment(bankPaymentSave);
				walletRef.setVersion(data.getWalletVer());
				walletDao.saveAndFlush(walletRef);
			}

		}
		profile.setProvince(data.getProvince());
		final Industry industry = industryDao.getByIdRef(data.getIndustryId());
		profile.setIndustry(industry);
		final Position position = positionDao.getByIdRef(data.getPositionId());
		profile.setPosition(position);
		profile.setPostalCode(data.getPostalCode());
		profile.setDob(data.getDob());
		profile.setVersion(data.getVer());
		profileDao.saveAndFlush(profile);
		ConnHandler.commit();
		pojoUpdateRes.setId(profile.getId());
		pojoUpdateRes.setMessage("Save Success!");
		pojoUpdateRes.setVer(profile.getVersion());
		return pojoUpdateRes;

	}
	
}
