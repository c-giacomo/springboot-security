package com.client.oauth.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.core.common.CorsConfig;
import com.auth0.core.provider.Auth0Provider;

@Configuration
public class SecurityConfiguration {
	
	@Configuration
	@Order(1)
	public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Override
        protected void configure(HttpSecurity http) throws Exception {
			
	        http.cors()
	        	.and()
	        	.requestMatchers().antMatchers("/api/**")
	        	.and()
	        	.authorizeRequests().anyRequest().authenticated()
	            .and()
	            .oauth2ResourceServer().jwt(); // replace .jwt() with .opaqueToken() for Opaque Token case
		}
    }
	
	@Profile("oauth")
	@Configuration
	@Import(Auth0Provider.class)
	public static class Auth0Configuration {}
	
	@Configuration
	@Import(CorsConfig.class)
	public static class CorsConfiguration {}

}
