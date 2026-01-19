package com.prodapt.license_tracker.auth.service;

import com.prodapt.license_tracker.auth.dto.LoginRequest;
import com.prodapt.license_tracker.auth.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}
