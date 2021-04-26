package it.security.multiple.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class HelloResource {
	
	@GetMapping("/hello")
	public String hello() {
		log.info("logged in...");
		return "HELLO!";
	}

}
