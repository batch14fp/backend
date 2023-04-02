package com.lawencon.community.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lawencon.community.filter.AuthorizationFilter;
import com.lawencon.community.service.UserService;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http,
			final AuthorizationFilter authorizationFilter) throws Exception {

		http.cors();
		http.csrf().disable();

		http.addFilterAt(authorizationFilter, BasicAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(final HttpSecurity http, final UserService userService,
			final BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userService)
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}

	@Bean
	public List<RequestMatcher> matchers() {
		final ArrayList<RequestMatcher> matcher = new ArrayList<>();
		matcher.add(new AntPathRequestMatcher("/users/login", HttpMethod.POST.name()));
		matcher.add(new AntPathRequestMatcher("/users/sign-up/**", HttpMethod.POST.name()));
		matcher.add(new AntPathRequestMatcher("/users/sign-up/**", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/industries", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/positions", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/files/**", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/report/admin/activity/file/**", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/report/member/activity/file/**", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/report/admin/incomes/file/**", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/report/member/incomes/file/**", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/users/industries", HttpMethod.GET.name()));
		matcher.add(new AntPathRequestMatcher("/users/positions", HttpMethod.GET.name()));
		return matcher;
	}
	
	@Bean
	public  WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200")
				 .allowedHeaders("*").allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.DELETE.name(),
				HttpMethod.PATCH.name());
			}
		};
	}
}
