package it.multiple.auth.jwt.security.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Profile("database")
@Configuration
@EnableWebSecurity
public class DBSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ApplicationUserDetailService applicationUserDetailService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(applicationUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and()
			.csrf().disable()
        	.authorizeRequests()
        	.antMatchers("/auth/db/login")
        	.permitAll()
        	.anyRequest()
        	.authenticated();
	}

}
