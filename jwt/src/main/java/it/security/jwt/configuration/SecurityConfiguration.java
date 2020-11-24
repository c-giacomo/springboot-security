package it.security.jwt.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import it.security.jwt.handler.JwtAuthenticationFailureHandler;
import it.security.jwt.handler.JwtAuthenticationSuccessHandler;
import it.security.jwt.services.ApplicationUserDetailService;
import it.security.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private final ApplicationUserDetailService userDetailService;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("*")));
        configuration.setAllowedMethods(Collections.unmodifiableList(Arrays.asList("HEAD","GET", "POST", "PUT", "DELETE", "PATCH")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList("Authorization", "Cache-Control", 
        		"Content-Type", "Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Accept", "X-Requested-With", 
        		"remember-me", "Authorization", "WWW-Authenticate")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
	
	@Bean
	public JwtUtil jwtUtilBean() throws Exception {
		return new JwtUtil();
	}
	
	@Bean
	public JwtAuthorizationFilter authorizationTokenFilterBean() throws Exception {
		return new JwtAuthorizationFilter(jwtUtilBean());
	}
	
	public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter("/login");
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
		jwtAuthenticationFilter.setJwtUtilManager(jwtUtilBean());
		jwtAuthenticationFilter.setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
		return jwtAuthenticationFilter;
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().and()
			.csrf().disable()
        	.authorizeRequests()
        	.antMatchers("/api/public").permitAll()
        	.anyRequest()
        	.authenticated()
        	.and()
        	.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
        	.and()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and()
        	.addFilterAt(authenticationTokenFilterBean(), BasicAuthenticationFilter.class);
		
		http.addFilterBefore(authorizationTokenFilterBean(), BasicAuthenticationFilter.class);
		
	}

}
