package com.project.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okta.spring.boot.oauth.Okta;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//protect the endpoint /api/orders
		http.authorizeRequests()
			.antMatchers("/api/orders/**")
			.authenticated()
			.and()
			.oauth2ResourceServer()
			.jwt();
		
		//add cros filters
		
		http.cors();
		
		//force a non-empty response body for 404's to make the reponse more friendly
		Okta.configureResourceServer401ResponseBody(http);
		
		//disbale CSRF since we are not using Cookies for session tracking
		http.csrf().disable();
	}

	
}
