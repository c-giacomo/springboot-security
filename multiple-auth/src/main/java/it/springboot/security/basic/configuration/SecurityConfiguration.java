package it.springboot.security.basic.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final ApplicationUserDetailService userDetailService;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("guest").password("{noop}guest1234").roles("USER");
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("*")));
        configuration.setAllowedMethods(Collections.unmodifiableList(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList("Authorization", "Cache-Control", 
        		"Content-Type", "Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Accept", "X-Requested-With", 
        		"remember-me", "Authorization", "WWW-Authenticate")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
//			.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class).cors().and() // • ADDING CorsFilter custom class
			.cors().and()
			.csrf().disable()
        	.authorizeRequests()
//        	.antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // • NOT USE IT - BYPASS CORS HEADERS
        	.anyRequest()
        	.authenticated()
        	.and()
        	.httpBasic();
		
//		http.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class); • ADDING CustomFilter class fired at every access
	}


}
