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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.login.PojoLoginReq;
import com.lawencon.community.pojo.login.PojoLoginRes;
import com.lawencon.community.pojo.profile.PojoForgetPasswordEmailReq;
import com.lawencon.community.pojo.profile.PojoPasswordUpdateReq;
import com.lawencon.community.pojo.user.PojoResGetAllUserByRole;
import com.lawencon.community.pojo.user.PojoSignUpReqInsert;
import com.lawencon.community.pojo.verificationcode.PojoResGetVerification;
import com.lawencon.community.pojo.verificationcode.PojoResGetVerificationCode;
import com.lawencon.community.pojo.verificationcode.PojoVerificationCodeReq;
import com.lawencon.community.service.JwtService;
import com.lawencon.community.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	private UserService userService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;

	public UserController(final UserService userService, final JwtService jwtService, 
			final AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	
	@PostMapping("sign-up")
	public ResponseEntity<PojoInsertRes>VerificationCode(@RequestBody  PojoVerificationCodeReq data){
		PojoInsertRes res = userService.verificationCode(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	@PostMapping("sign-up/verify")
	public ResponseEntity<PojoInsertRes>insert(@RequestBody PojoSignUpReqInsert data){
		PojoInsertRes res = userService.userRegistration(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PostMapping("sign-up/verify-code")
	public ResponseEntity<PojoResGetVerification>checkVerified(@RequestBody PojoResGetVerificationCode data){
		PojoResGetVerification res = userService.getVerified(data);
		return new ResponseEntity<PojoResGetVerification>(res, HttpStatus.OK);
	}
	
	
	@GetMapping("/{roleCode}")
	public ResponseEntity<List<PojoResGetAllUserByRole>>getAllUserCode(@PathVariable("roleCode")String roleCode){
		List<PojoResGetAllUserByRole> resGet = userService.getAllUserByRole(roleCode);
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody PojoLoginReq user) {
		final Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

		authenticationManager.authenticate(auth);
		final Optional<User> userOptional = userService.login(user.getEmail());

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);

		final Map<String, Object> claims = new HashMap<>();
		claims.put("exp", cal.getTime());
		claims.put("id", userOptional.get().getId());

		final PojoLoginRes loginRes = new PojoLoginRes();
		loginRes.setToken(jwtService.generateJwt(claims));
		loginRes.setUserId(userOptional.get().getId());
		loginRes.setFullname(userOptional.get().getProfile().getFullname());
		loginRes.setRoleCode(userOptional.get().getRole().getRoleCode());
		loginRes.setImgProfileId(userOptional.get().getProfile().getImageProfile().getId());

		return new ResponseEntity<>(loginRes, HttpStatus.OK);
	}
	
	@PostMapping("recover")
	public ResponseEntity<PojoInsertRes>getCodeVerify(@RequestBody PojoForgetPasswordEmailReq data){
		PojoInsertRes res = userService.sendCode(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@GetMapping("recover/verify")
	public ResponseEntity<PojoResGetVerification>checkVerifiedCode(@RequestBody PojoResGetVerificationCode data){
		PojoResGetVerification res = userService.getVerified(data);
		return new ResponseEntity<PojoResGetVerification>(res, HttpStatus.OK);
	}
	@GetMapping("recover/change-password")
	public ResponseEntity<PojoUpdateRes>updatePassword(@RequestBody PojoPasswordUpdateReq data){
		PojoUpdateRes res = userService.updatePassword(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
