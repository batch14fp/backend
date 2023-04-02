package com.lawencon.community.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleEnum;
import com.lawencon.community.constant.StatusEnum;
import com.lawencon.community.dao.CodeVerificationDao;
import com.lawencon.community.dao.IndustryDao;
import com.lawencon.community.dao.MemberStatusDao;
import com.lawencon.community.dao.PositionDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.WalletDao;
import com.lawencon.community.model.CodeVerification;
import com.lawencon.community.model.Industry;
import com.lawencon.community.model.MemberStatus;
import com.lawencon.community.model.Position;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Wallet;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.profile.PojoForgetPasswordEmailReq;
import com.lawencon.community.pojo.profile.PojoPasswordReqUpdate;
import com.lawencon.community.pojo.user.PojoAllUsersRes;
import com.lawencon.community.pojo.user.PojoAllUsersResData;
import com.lawencon.community.pojo.user.PojoSignUpReqInsert;
import com.lawencon.community.pojo.verificationcode.PojoResGetVerificationCode;
import com.lawencon.community.pojo.verificationcode.PojoVerificationCodeReqInsert;
import com.lawencon.community.pojo.verificationcode.PojoVerificationRes;
import com.lawencon.community.util.GenerateString;
import com.lawencon.security.principal.PrincipalService;

@Service
public class UserService implements UserDetailsService {
	private UserDao userDao;
	private ProfileDao profileDao;
	private RoleDao roleDao;
	private CodeVerificationDao codeVerificationDao;
	private EmailSenderService emailSenderService;
	private PositionDao positionDao;
	private IndustryDao industryDao;
	private MemberStatusDao memberStatusDao;
	private WalletDao walletDao;
	private SubscriptionDao subscriptionDao;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private PrincipalService principalService;
	public UserService(final SubscriptionDao subscriptionDao, final WalletDao walletDao, final UserDao userDao, final ProfileDao profileDao, final RoleDao roleDao,
			final EmailSenderService emailSenderService, final CodeVerificationDao codeVerificationDao,
			final PositionDao positionDao, final IndustryDao industryDao, final MemberStatusDao memberStatusDao) {
		this.userDao = userDao;
		this.profileDao = profileDao;
		this.roleDao = roleDao;
		this.positionDao = positionDao;
		this.industryDao = industryDao;
		this.emailSenderService = emailSenderService;
		this.memberStatusDao = memberStatusDao;
		this.codeVerificationDao = codeVerificationDao;
		this.walletDao = walletDao;
		this.subscriptionDao = subscriptionDao;
	};

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<User> user = userDao.login(username);

		if (user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(username, user.get().getUserPassword(),
					new ArrayList<>());
		}

		throw new UsernameNotFoundException("Wrong email and password!");
	}
	
	
	

	public Optional<User> login(final String email) {
		return userDao.login(email);
	}

	public PojoVerificationRes getVerified(PojoResGetVerificationCode data) {
		PojoVerificationRes res = new PojoVerificationRes();
		Optional<CodeVerification> codeVerification = codeVerificationDao.getByCode(data.getCode());

		codeVerification.ifPresent(result -> {
			res.setCode(result.getCode());
			res.setEmail(result.getEmail());
			res.setExpiredAt(result.getExpiredAt());
			res.setPassword(result.getUserPassword());
		});
		return res;
	}

	public PojoInsertRes userRegistration(PojoSignUpReqInsert data) {
		final PojoInsertRes res = new PojoInsertRes();
		ConnHandler.begin();
		final User system = userDao.getUserByRoleCode(RoleEnum.SYSTEM.getRoleCode()).get(0);

		final Profile profile = new Profile();
		final Position position = positionDao.getByIdRef(data.getPositionId());
		profile.setPosition(position);
		final Industry industry = industryDao.getByIdRef(data.getIndustryId());
		profile.setIndustry(industry);
		profile.setPhoneNumber(data.getPhoneNumber());
		final MemberStatus memberStatus = memberStatusDao.getByCode(StatusEnum.REGULAR.getStatusCode()).get();
		final MemberStatus memberStatusRef = memberStatusDao.getByIdRef(memberStatus.getId());
	
		
		profile.setFullname(data.getFullName());

		profile.setCompanyName(data.getCompany());
		profile.setCreatedBy(system.getId());
		Profile profileNew = profileDao.saveNoLogin(profile, () -> system.getId());

		
		final Subscription subs = new Subscription();
		subs.setProfile(profileNew);
		subs.setMemberStatus(memberStatusRef);
		subs.setStartDate(LocalDateTime.now());
		subs.setEndDate(LocalDateTime.now().plusYears(3));
		subs.setIsActive(true);
		subscriptionDao.save(subs);
		
		
		res.setId(profileNew.getId());

		final User user = new User();
		
		final Wallet wallet = new Wallet();
	
		wallet.setBalance(BigDecimal.ZERO);
		
		final Wallet walletNew = walletDao.save(wallet);

		final Role role = roleDao.getRoleByCode(RoleEnum.MEMBER.getRoleCode()).get();
		user.setRole(role);
		user.setEmail(data.getEmail());
		user.setUserPassword(encoder.encode(data.getPassword()).toString());
		user.setCreatedBy(system.getId());
		
		final Profile profileRef = profileDao.getByIdRef(profileNew.getId());
		final Wallet wallerRef = walletDao.getByIdRef(Wallet.class, walletNew.getId());
		user.setProfile(profileRef);
		user.setWallet(wallerRef);
		final User userNew = userDao.saveNoLogin(user, () -> system.getId());
		
	
		
		ConnHandler.commit();

		res.setId(userNew.getId());
		res.setMessage("Registration is Success");

		return res;
	}
	public PojoInsertRes insertUser(PojoSignUpReqInsert data) {
		final PojoInsertRes res = new PojoInsertRes();
		ConnHandler.begin();

		final Profile profile = new Profile();
		final Position position = positionDao.getByIdRef(data.getPositionId());
		profile.setPosition(position);
		final Industry industry = industryDao.getByIdRef(data.getIndustryId());
		profile.setIndustry(industry);
		profile.setPhoneNumber(data.getPhoneNumber());
		profile.setDob(data.getDob());
		profile.setFullname(data.getFullName());
		profile.setCompanyName(data.getCompany());
		Profile profileNew = profileDao.save(profile);
		res.setId(profileNew.getId());
		final User user = new User();
		final Wallet wallet = new Wallet();
		wallet.setBalance(BigDecimal.ZERO);
		final Wallet walletNew = walletDao.save(wallet);
		final Role role = roleDao.getRoleByCode(RoleEnum.ADMIN.getRoleCode()).get();
		user.setRole(role);
		user.setEmail(data.getEmail());
		user.setUserPassword(encoder.encode(data.getPassword()).toString());
		final Profile profileRef = profileDao.getByIdRef(profileNew.getId());
		final Wallet wallerRef = walletDao.getByIdRef(Wallet.class, walletNew.getId());
		user.setProfile(profileRef);
		user.setWallet(wallerRef);
		final User userNew = userDao.save(user);
		
	
		
		ConnHandler.commit();

		res.setId(userNew.getId());
		res.setMessage("Add User is Success");

		return res;
	}

	public PojoInsertRes verificationCode(final PojoVerificationCodeReqInsert data) {
		final PojoInsertRes res = new PojoInsertRes();

		final User system = userDao.getUserByRoleCode(RoleEnum.SYSTEM.getRoleCode()).get(0);

		final CodeVerification codeVerification = new CodeVerification();

		codeVerification.setEmail(data.getEmail());
		final String codeGenerated = GenerateString.generateCode(6);
		codeVerification.setCode(codeGenerated);
		codeVerification.setExpiredAt(LocalDateTime.now().plusMinutes(2));

		codeVerification.setUserPassword(encoder.encode(data.getPassword()));
		ConnHandler.begin();
		final CodeVerification codeNew = codeVerificationDao.saveNoLogin(codeVerification, () -> system.getId());
		codeVerification.setCreatedBy(system.getId());

		res.setId(codeNew.getId());
		res.setMessage("Please Cek email to get verfication code");

		new Thread(() -> {
			try {
				emailSenderService.sendEmail(data.getEmail(), codeGenerated);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}).start();; 
		ConnHandler.commit();
		return res;

	}


	public List<PojoAllUsersResData> getAllUserByRole(String roleCode) {
		final List<PojoAllUsersResData> listRes = new ArrayList<>();
		userDao.getUserByRoleCode(roleCode).forEach(data -> {
			PojoAllUsersResData res = new PojoAllUsersResData();
			res.setEmail(data.getEmail());
			res.setRoleId(data.getRole().getRoleCode());
			res.setFullname(data.getProfile().getFullname());
			res.setRoleName(data.getRole().getRoleName());
			listRes.add(res);

		});

		return listRes;

	}
	
	
	public PojoAllUsersRes getAllUser(int offset, int limit) {
		final PojoAllUsersRes res = new PojoAllUsersRes();	
		final List<PojoAllUsersResData> listRes = new ArrayList<>();
		userDao.getllUser(offset, limit).forEach(data -> {
			PojoAllUsersResData userData = new PojoAllUsersResData();
			userData.setEmail(data.getEmail());
			userData.setUserId(data.getId());
			userData.setRoleId(data.getRole().getRoleCode());
			userData.setFullname(data.getProfile().getFullname());
			userData.setRoleName(data.getRole().getRoleName());
			userData.setIsActive(data.getIsActive());
			userData.setVer(data.getVersion());
			listRes.add(userData);

		});
		res.setData(listRes);
		res.setTotal(Long.valueOf(getTotalCount()));

		return res;

	}
	public int getTotalCount() {

		return userDao.getTotalCount();
	}
	
	
	public PojoUpdateRes updatePassword(PojoPasswordReqUpdate data) {
		ConnHandler.begin();
		final PojoUpdateRes res = new PojoUpdateRes();
		final User user = userDao.getUserByProfileId(data.getProfileId());
		final User userRef = userDao.getByIdRef(user.getId());
		userDao.getByIdAndDetach(User.class, userRef.getId());
		if(encoder.matches(data.getOldPassword(), userRef.getUserPassword())) {
		if (data.getConfirmNewPassword().equals(data.getNewPassword())) {
		userRef.setUserPassword(encoder.encode(data.getConfirmNewPassword()));
		userRef.setVersion(data.getVer());
		final User userNew = userDao.saveAndFlush(userRef);
		ConnHandler.commit();
		res.setId(userNew.getId());
		res.setMessage("Password is updated");
		res.setVer(userNew.getVersion());
		
		}
		else {
			res.setId(userRef.getId());
			res.setMessage("Passwords did not match");
			res.setVer(userRef.getVersion());
		}
		
		}
		else {
			throw new RuntimeException ("Your Password Doesn't Match");
		}
		return res;
	}
	
	public PojoInsertRes  sendCode(PojoForgetPasswordEmailReq data) {
		final PojoInsertRes res = new PojoInsertRes();

		final User system = userDao.getUserByRoleCode(RoleEnum.SYSTEM.getRoleCode()).get(0);

		final CodeVerification codeVerification = new CodeVerification();

		codeVerification.setEmail(data.getEmail());
		
		final String codeGenerated = GenerateString.generateCode(6);
		codeVerification.setCode(codeGenerated);
		codeVerification.setExpiredAt(LocalDateTime.now().plusMinutes(2));
		
		codeVerification.setUserPassword(userDao.getUserByEmail(data.getEmail()).getUserPassword());
		ConnHandler.begin();
		final CodeVerification codeNew = codeVerificationDao.saveNoLogin(codeVerification, () -> system.getId());
		codeVerification.setCreatedBy(system.getId());

		res.setId(codeNew.getId());
		res.setMessage("Please Cek email to get verfication code");

		new Thread(() -> {
			try {
				emailSenderService.sendEmail(data.getEmail(), codeGenerated);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}).start();; 
		ConnHandler.commit();
		return res;
		
	}
	
	public PojoRes deleteById(String id) {
		ConnHandler.begin();
		final PojoRes pojoRes = new PojoRes();
		pojoRes.setMessage("Delete Success!");
		final PojoRes pojoResFail = new PojoRes();
		pojoResFail.setMessage("Delete Failed!");

		Boolean result = userDao.deleteById(User.class, id);
		ConnHandler.commit();
		if (result) {
			return pojoRes;
		} else {
			return pojoResFail;
		}

	}
	
	public User getUserPrincipal() {
		return userDao.getById(principalService.getAuthPrincipal()).get();
	
	}
	
	
	
	
	

}
