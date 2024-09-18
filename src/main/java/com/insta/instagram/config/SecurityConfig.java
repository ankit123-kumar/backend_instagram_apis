package com.insta.instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.csrf().disable()// Disable CSRF for stateless APIs
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST, "/signup").permitAll()// Allow unauthenticated access to /signup
	
		   .anyRequest().authenticated()
		.and()
		.addFilterAfter(new JwtTokenGenerator(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class)
		
		.formLogin().and().httpBasic();
		
		return httpSecurity.build();
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
		
	}
}
