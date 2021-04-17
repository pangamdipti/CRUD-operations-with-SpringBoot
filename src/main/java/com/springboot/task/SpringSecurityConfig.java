package com.springboot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/students").hasRole("USER")
		.antMatchers(HttpMethod.POST, "/students").hasRole("USER")
		.antMatchers(HttpMethod.GET, "/students").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/students/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN")
		.and()
		.csrf().disable();
	}
}
