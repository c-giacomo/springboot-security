package it.multiple.auth.jwt.security.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.multiple.auth.jwt.security.db.DBSecurityConfig;
import it.multiple.auth.jwt.security.inmemory.InMemorySecurityConfig;
import it.multiple.auth.jwt.security.jwt.JwtAuthenticationFilter;
import it.multiple.auth.jwt.security.jwt.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Configuration
    @Order(1)
    public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

        private static final String apiMatcher = "/api/**";

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.addFilterBefore(new JwtAuthenticationFilter(apiMatcher, super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);

            http.antMatcher(apiMatcher).authorizeRequests()
                    .anyRequest()
                    .authenticated();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(new JwtAuthenticationProvider());
        }
    }
	
	@Profile("in-memory")
    @Configuration
    @Import(InMemorySecurityConfig.class)
    public static class InMemoryConfig {}
	
	@Profile("database")
    @Configuration
    @Import(DBSecurityConfig.class)
    public static class DBConfig {}
	
	@Configuration
	@Import(CorsConfig.class)
	public static class CorsConfiguration {}

}
