package com.cafedebarrio.backend.service;

import com.cafedebarrio.backend.dto.auth.request.LoginRequest;
import com.cafedebarrio.backend.dto.auth.response.AuthResponse;

public interface AuthService {

	AuthResponse login(LoginRequest request);
}
