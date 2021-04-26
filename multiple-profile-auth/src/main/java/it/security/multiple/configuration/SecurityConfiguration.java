package it.security.multiple.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration {
	
	@Profile("no-auth")
    @EnableWebSecurity
    public static class SecurityDisabledConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().permitAll();
        }
    }
	
	@Profile("in-memory")
    @EnableWebSecurity
    public static class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication().withUser("User").password("{noop}1234").roles("ADMIN");
	    }
		
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated()
            			.and().httpBasic();
        }
    }

}
