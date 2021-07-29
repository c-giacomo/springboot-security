package it.multiple.auth.jwt.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class Home {
	
	@GetMapping
	public String home() {
		return "SUCCESS";
	}

}
