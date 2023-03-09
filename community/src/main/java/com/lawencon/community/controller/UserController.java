package com.lawencon.community.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.User;
import com.lawencon.community.pojo.user.PojoUserReqLogin;
import com.lawencon.community.pojo.user.PojoUserResLogin;
import com.lawencon.community.service.JwtService;
import com.lawencon.community.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	private UserService userService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;

	public UserController(final UserService userService, final JwtService jwtService, final AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@Validated @RequestBody PojoUserReqLogin user) {
		final Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		authenticationManager.authenticate(auth);
		final Optional<User> userOptional = userService.login(user.getEmail());

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);

		final Map<String, Object> claims = new HashMap<>();
		claims.put("exp", cal.getTime());
		claims.put("id", userOptional.get().getId());

		final PojoUserResLogin loginRes = new PojoUserResLogin();
		loginRes.setToken(jwtService.generateJwt(claims));
		loginRes.setFullName(userOptional.get().getProfile().getFullname());
		loginRes.setRoleCode(userOptional.get().getRole().getRoleCode());
		loginRes.setImgProfileId(userOptional.get().getProfile().getImageProfile().getId());

		return new ResponseEntity<>(loginRes, HttpStatus.OK);

	}
	
}
