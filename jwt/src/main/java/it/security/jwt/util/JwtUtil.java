package it.security.jwt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.security.jwt.constant.SecurityConstants;

@Component
public class JwtUtil {
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.issuer}")
	private String issuer;
	
	@Value("${jwt.audience}")
	private String audience;

	/**
	 * @author gchiavolotti
	 * @param token
	 * @return username
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	/**
	 * @author gchiavolotti
	 * @param token
	 * @return expiration date
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	/**
	 * @author gchiavolotti
	 * @param token, Claims::T
	 * @return true if expires false otherwise
	 */

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
    
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails principal) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("roles", principal.getAuthorities());
		claims.put("subject", principal.getUsername());
		return doGenerateToken(claims);
	}

	@SuppressWarnings({"deprecation"})
	private String doGenerateToken(Map<String, Object> claims) {
		byte[] signingKey = secret.getBytes();
		
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, signingKey)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
				.setIssuer(issuer)
				.setAudience(audience)
				.setSubject(claims.get("subject").toString())
				.claim("roles", claims.get("roles"))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.compact();
	}

	public void validateToken(String token) {
		if (isTokenExpired(token)) throw new ExpiredJwtException(null, null, "JWT Token Expired");
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<SimpleGrantedAuthority> getAuthorities(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return (List<SimpleGrantedAuthority>) ((List<Map>)claims.get("roles"))
							.stream()
							.map(obj -> new SimpleGrantedAuthority((String)obj.get("authority")))
							.collect(Collectors.toList());
	}

}
