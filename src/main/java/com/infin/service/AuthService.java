package com.infin.service;

import com.infin.dto.JwtAuthenticationResponse;
import com.infin.dto.LoginRequest;
import com.infin.dto.SignUpRequest;
import com.infin.entity.User;

import javax.validation.Valid;

public interface AuthService {

    User userSignUp(@Valid SignUpRequest signUpRequest);
    JwtAuthenticationResponse userSignIn(@Valid LoginRequest loginRequest);
}
