package com.client.oauth.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PrivateResource {
	
	@GetMapping("/private")
	public ResponseEntity<String> sayHello() {
		return new ResponseEntity<String>("private", HttpStatus.OK);
	}

}
