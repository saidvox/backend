package com.cafedebarrio.backend.service.impl;

import com.cafedebarrio.backend.dto.auth.request.LoginRequest;
import com.cafedebarrio.backend.dto.auth.response.AuthResponse;
import com.cafedebarrio.backend.security.JwtService;
import com.cafedebarrio.backend.service.AuthService;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.username(), request.password()));

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateAccessToken(userDetails);

		List<String> roles = userDetails.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();

		return new AuthResponse(
				token,
				"Bearer",
				jwtService.getAccessTokenExpiration(),
				userDetails.getUsername(),
				roles
		);
	}
}
