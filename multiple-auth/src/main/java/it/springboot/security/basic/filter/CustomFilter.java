package it.springboot.security.basic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    	TODO • do some with authentication interface
        chain.doFilter(request, response);
    }

}