package com.lawencon.community.service;

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

import com.lawencon.community.constant.RoleEnum;
import com.lawencon.community.dao.ProfileDao;
import com.lawencon.community.dao.RoleDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.Profile;
import com.lawencon.community.model.Role;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.user.PojoSignUpReqInsert;

@Service
public class UserService implements UserDetailsService {
	private UserDao userDao;
	private ProfileDao profileDao;
	private RoleDao roleDao;

	@Autowired	
	private PasswordEncoder encoder;
	
	@PersistenceContext
	private EntityManager em;
	
	public UserService(final UserDao userDao, final  ProfileDao profileDao, final RoleDao roleDao) {
		this.userDao = userDao;
		this.profileDao = profileDao;
		this.roleDao = roleDao;
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
	
	public PojoInsertRes insertUser(final PojoSignUpReqInsert  data) {
		final PojoInsertRes res = new PojoInsertRes();
		final User system = userDao.getUserByRoleCode(RoleEnum.SYSTEM.getRoleCode()).get(0);
		final User user = new User();
	
		user.setEmail(data.getEmail());
		user.setUserPassword(data.getPassword());
	
		final Role role = roleDao.getRoleByCode(RoleEnum.MEMBER.getRoleCode()).get();
		user.setRole(role);
		user.setProfileSocialMedia(null);
		
		final Profile profile = new Profile();
		profileDao.saveNoLogin(profile, ()->system.getId());
		user.setProfile(profile);
		
		userDao.saveNoLogin(user, ()->system.getId());
		user.setCreatedBy(system.getId());

		return res;
	}
	
	
	



	
	

}
