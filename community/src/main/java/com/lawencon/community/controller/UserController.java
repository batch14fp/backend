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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.constant.RoleEnum;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.industry.PojoIndustryRes;
import com.lawencon.community.pojo.login.PojoLoginReq;
import com.lawencon.community.pojo.login.PojoLoginRes;
import com.lawencon.community.pojo.position.PojoPostionRes;
import com.lawencon.community.pojo.profile.PojoForgetPasswordEmailReq;
import com.lawencon.community.pojo.profile.PojoPasswordReqUpdate;
import com.lawencon.community.pojo.user.PojoSignUpReqInsert;
import com.lawencon.community.pojo.verificationcode.PojoResGetVerificationCode;
import com.lawencon.community.pojo.verificationcode.PojoVerificationCodeReqInsert;
import com.lawencon.community.pojo.verificationcode.PojoVerificationRes;
import com.lawencon.community.service.IndustryService;
import com.lawencon.community.service.JwtService;
import com.lawencon.community.service.PositionService;
import com.lawencon.community.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	private UserService userService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	private SubscriptionDao subscriptionDao;
	private IndustryService industryService;
	private PositionService positionService;


	public UserController(final PositionService positionService, final IndustryService industryService, final SubscriptionDao subscriptionDao,  final UserService userService, final JwtService jwtService, 
			final AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.subscriptionDao = subscriptionDao;
		this.authenticationManager = authenticationManager;
		this.industryService = industryService;
		this.positionService = positionService;
	}
	@PostMapping("sign-up")
	public ResponseEntity<PojoInsertRes>sendVerificationCode(@RequestBody  PojoVerificationCodeReqInsert data){
		PojoInsertRes res = userService.verificationCode(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PostMapping("sign-up/verify-code")
	public ResponseEntity<PojoVerificationRes>checkVerified(@RequestBody PojoResGetVerificationCode data){
		PojoVerificationRes res = userService.getVerified(data);
		return new ResponseEntity<PojoVerificationRes>(res, HttpStatus.OK);
	}
	@PostMapping("sign-up/verify-code/verified")
	public ResponseEntity<PojoInsertRes>insertUserVerified(@RequestBody PojoSignUpReqInsert data){
		PojoInsertRes res = userService.userRegistration(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
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
		loginRes.setPosition(userOptional.get().getProfile().getPosition().getPositionName());
	
		
		if(userOptional.get().getRole().getRoleCode().equalsIgnoreCase(RoleEnum.MEMBER.getRoleCode())) {
		final Subscription subs = subscriptionDao.getByProfileId(userOptional.get().getProfile().getId()).get();
		final Subscription subsRef = subscriptionDao.getByIdRef(Subscription.class, subs.getId());
		loginRes.setMemberCode(subsRef.getMemberStatus().getCodeStatus());
		}
		
		if(userOptional.get().getProfile().getImageProfile()!=null) {
		loginRes.setImageId(userOptional.get().getProfile().getImageProfile().getId());
		}
		return new ResponseEntity<>(loginRes, HttpStatus.OK);
	}
	@PostMapping("recover")
	public ResponseEntity<PojoInsertRes>getCodeVerify(@RequestBody PojoForgetPasswordEmailReq data){
		PojoInsertRes res = userService.sendCode(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	@GetMapping("recover/verify")
	public ResponseEntity<PojoVerificationRes>checkVerifiedCode(@RequestBody PojoResGetVerificationCode data){
		PojoVerificationRes res = userService.getVerified(data);
		return new ResponseEntity<PojoVerificationRes>(res, HttpStatus.OK);
	}
	@GetMapping("recover/verify/change-password")
	public ResponseEntity<PojoUpdateRes>updatePassword(@RequestBody PojoPasswordReqUpdate data){
		PojoUpdateRes res = userService.updatePassword(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/industries")
	public ResponseEntity<List<PojoIndustryRes>> getAllIndustries(){
		List<PojoIndustryRes> resGet = industryService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
	@GetMapping("/positions")
	public ResponseEntity<List<PojoPostionRes>> getAllPositions(){
		List<PojoPostionRes> resGet = positionService.getAll();
		return new ResponseEntity<>(resGet, HttpStatus.OK);
	}
}
