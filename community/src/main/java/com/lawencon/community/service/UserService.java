package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.RoleEnum;
import com.lawencon.community.dao.CodeVerificationDao;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.CodeVerification;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.user.PojoSignUpReqInsert;
import com.lawencon.community.pojo.verificationcode.PojoVerificationCodeReq;
import com.lawencon.community.util.GenerateId;

@Service
public class UserService implements UserDetailsService {
	private UserDao userDao;
	private ProfileDao profileDao;
	private RoleDao roleDao;
	private CodeVerificationDao codeVerificationDao;
	private EmailSenderService emailSenderService;

	@Autowired
	private PasswordEncoder encoder;

	@PersistenceContext
	private EntityManager em;

	public UserService(final UserDao userDao, final ProfileDao profileDao, final RoleDao roleDao,
			final EmailSenderService emailSenderService, final CodeVerificationDao codeVerificationDao) {
		this.userDao = userDao;
		this.profileDao = profileDao;
		this.roleDao = roleDao;
		this.emailSenderService = emailSenderService;
		this.codeVerificationDao = codeVerificationDao;
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

	public PojoInsertRes userRegistration(PojoSignUpReqInsert data) {
		final PojoInsertRes res = new PojoInsertRes();
		ConnHandler.begin();
		final User system = userDao.getUserByRoleCode(RoleEnum.SYSTEM.getRoleCode()).get(0);

		Optional<CodeVerification> codeVerification = codeVerificationDao.getByCode(data.getCodeVerfication());
		// if(codeVerification.isPresent()) {

		final Profile profile = new Profile();

		profile.setCreatedBy(system.getId());
		Profile profileNew = profileDao.saveNoLogin(profile, () -> system.getId());

		res.setId(profileNew.getId());

		final User user = new User();

		final Role role = roleDao.getRoleByCode(RoleEnum.MEMBER.getRoleCode()).get();
		user.setRole(role);
		user.setEmail(data.getEmail());
		user.setUserPassword(encoder.encode(data.getPassword()));
		user.setCreatedBy(system.getId());
		user.setProfile(profileNew);
		final User userNew = userDao.saveNoLogin(user, () -> system.getId());
		ConnHandler.commit();

		res.setId(userNew.getId());
		res.setMessage("Registration is Success");

		return res;
//		}
//		else {
//			res.setMessage("Code Verification is wrong");
//		
//		return res;
//		}
	}

	public PojoInsertRes verificationCode(final PojoVerificationCodeReq data) {
		final PojoInsertRes res = new PojoInsertRes();

		final User system = userDao.getUserByRoleCode(RoleEnum.SYSTEM.getRoleCode()).get(0);

		final CodeVerification codeVerification = new CodeVerification();

		codeVerification.setEmail(data.getEmail());
		final String codeGenerated = GenerateId.generateCode(6);
		codeVerification.setCode(codeGenerated);
		codeVerification.setExpiredAt(LocalDateTime.now().plusMinutes(2));

		codeVerification.setUserPassword(encoder.encode(data.getPassword()));
		ConnHandler.begin();
		final CodeVerification codeNew = codeVerificationDao.saveNoLogin(codeVerification, () -> system.getId());
		codeVerification.setCreatedBy(system.getId());

		res.setId(codeNew.getId());
		res.setMessage("Please Cek email to get verfication code");

		new Thread(() -> emailSenderService.sendEmail(data.getEmail(), "  Hi, Welcome to WeCommunityApp \n  \n",
				" Your Code  : " + codeGenerated + "\n "
						+ "\n Please Input this code for verification your account\n\n\n\n Cheers,\n WeCommunity "))
				.start();
		ConnHandler.commit();
		return res;

	}

}
