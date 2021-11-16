package com.client.oauth.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicResources {
	
	@GetMapping("/sayhello")
	public ResponseEntity<String> sayHello() {
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

}
