package com.auth0.core.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("oauth")
@RestController
@RequestMapping("/idmauth")
public class Auth0LoginResource {
	
	@Autowired
	private OAuth2AuthorizedClientService clientService;
	
	@GetMapping("/redirect")
	public ResponseEntity<String> redirect(OAuth2AuthenticationToken oauthToken) {
		OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
				oauthToken.getAuthorizedClientRegistrationId(), 
				oauthToken.getName());
		
		String token = client.getAccessToken().getTokenValue();
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

}
