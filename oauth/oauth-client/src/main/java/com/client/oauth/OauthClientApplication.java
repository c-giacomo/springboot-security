package com.client.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
	"com.client.oauth",
	"com.client.oauth.resources",
	"com.client.oauth.security.config",
	"com.auth0.core.common",
	"com.auth0.core.provider"
})
public class OauthClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthClientApplication.class, args);
	}

}
