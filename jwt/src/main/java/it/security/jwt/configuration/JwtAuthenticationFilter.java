package it.security.jwt.configuration;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import it.security.jwt.constant.SecurityConstants;
import it.security.jwt.model.ApplicationUser;
import it.security.jwt.util.JwtUtil;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	
	private JwtUtil jwtUtil;
	
	protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}
	
	public void setJwtUtilManager(JwtUtil jwtUtil) throws Exception {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		try {
			ApplicationUser creds = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			
			return getAuthenticationManager().authenticate(authToken);
		} catch (MismatchedInputException e) {
			unsuccessfulAuthentication(request, response, new BadCredentialsException("Malformed Domain Credential"));
			return null;
		}
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        
        UserDetails principal = (UserDetails)authResult.getPrincipal();
        String token = jwtUtil.generateToken(principal);
        
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }	
}


