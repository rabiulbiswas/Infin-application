package com.infin.service;

import com.infin.dto.JwtAuthenticationResponse;
import com.infin.dto.LoginRequest;
import com.infin.dto.UserRequestDto;
import com.infin.entity.User;

import javax.validation.Valid;

public interface AuthService {

    User userSignUp(@Valid UserRequestDto signUpRequest);
    JwtAuthenticationResponse userSignIn(@Valid LoginRequest loginRequest);
}
