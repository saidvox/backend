package com.cafedebarrio.backend.security;

import com.cafedebarrio.backend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

	private final JwtProperties jwtProperties;
	private final SecretKey signingKey;

	public JwtService(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
		if (jwtProperties.secret() == null || jwtProperties.secret().length() < 32) {
			throw new IllegalStateException("La propiedad jwt.secret debe tener al menos 32 caracteres");
		}
		this.signingKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(UserDetails userDetails) {
		Date issuedAt = new Date();
		Date expiration = new Date(issuedAt.getTime() + jwtProperties.accessTokenExpiration());

		return Jwts.builder()
				.subject(userDetails.getUsername())
				.issuer(jwtProperties.issuer())
				.issuedAt(issuedAt)
				.expiration(expiration)
				.claim("roles", extractRoles(userDetails))
				.signWith(signingKey)
				.compact();
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		try {
			Claims claims = extractAllClaims(token);
			return claims.getSubject().equals(userDetails.getUsername())
					&& jwtProperties.issuer().equals(claims.getIssuer())
					&& claims.getExpiration().after(new Date());
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}

	public long getAccessTokenExpiration() {
		return jwtProperties.accessTokenExpiration();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private List<String> extractRoles(UserDetails userDetails) {
		return userDetails.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
	}
}
