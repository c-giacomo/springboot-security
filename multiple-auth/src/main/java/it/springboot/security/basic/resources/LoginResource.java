package it.springboot.security.basic.resources;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/login")
public class LoginResource {
	
	@GetMapping
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(authentication.getName());
		String value = authentication.getName();
		return value;
	}
	
	@GetMapping("/whoami")
	public String whoAmI() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(authentication.getName());
		String value = authentication.getName();
		return value;
	}
	
	@GetMapping("/hello")
	public void hello() {
		log.info("ciao");
	}

}
