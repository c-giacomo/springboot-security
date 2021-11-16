package com.auth0.core.common;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("http://localhost:4200")));
        configuration.setAllowedMethods(Collections.unmodifiableList(Arrays.asList("HEAD","GET", "POST", "PUT", "DELETE", "PATCH")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList("Authorization", "Cache-Control", 
        		"Content-Type", "Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Accept", "X-Requested-With", 
        		"remember-me", "Authorization", "WWW-Authenticate")));
        configuration.setExposedHeaders(Collections.unmodifiableList(Arrays.asList("Authorization", "Content-Disposition")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
