package it.multiple.auth.jwt.resources;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import it.multiple.auth.jwt.constants.SecurityConstant;

@RestController
@RequestMapping("/auth")
public class Auth {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/inmemory/login")
    public ResponseEntity<String> login() throws JOSEException {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {

            LocalDateTime dateTime = LocalDateTime.now();

            //build claims
            JWTClaimsSet.Builder jwtClaimsSetBuilder = new JWTClaimsSet.Builder();
            dateTime = dateTime.plusMinutes(120);
            Date d = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            jwtClaimsSetBuilder.expirationTime(d);
            jwtClaimsSetBuilder.subject(authentication.getName());

            //signature
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSetBuilder.build());
            signedJWT.sign(new MACSigner(SecurityConstant.JWT_SECRET));

            return new ResponseEntity<>(signedJWT.serialize(), HttpStatus.OK);
        }
    }
	
	@PostMapping("/db/login")
    public ResponseEntity<String> login(@RequestParam("username") String username,
    		@RequestParam("password") String password) throws Exception {
    	
    	authenticate(username, password);
    	
    	final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	LocalDateTime dateTime = LocalDateTime.now();

        //build claims
        JWTClaimsSet.Builder jwtClaimsSetBuilder = new JWTClaimsSet.Builder();
        dateTime = dateTime.plusMinutes(120);
        Date d = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        jwtClaimsSetBuilder.expirationTime(d);
        jwtClaimsSetBuilder.subject(authentication.getName());
        
        jwtClaimsSetBuilder.claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtClaimsSetBuilder.build());
        signedJWT.sign(new MACSigner(SecurityConstant.JWT_SECRET));

        return new ResponseEntity<>(signedJWT.serialize(), HttpStatus.OK);
    }
	
	
	private void authenticate(String username, String password) throws Exception {
		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
