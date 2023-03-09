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

import com.lawencon.community.dao.UserDao;
import com.lawencon.community.model.User;

@Service
public class UserService implements UserDetailsService{
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@PersistenceContext
	private EntityManager em;
	
	public UserService(UserDao userDao) {
		this.userDao = userDao;
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

}
