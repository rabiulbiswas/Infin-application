package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.JwtAuthenticationResponse;
import com.infin.dto.LoginRequest;
import com.infin.dto.UserRequestDto;
import com.infin.entity.User;
import com.infin.repository.UserRepository;

import com.infin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        ResponseEntity<?> resp= null;
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authService.userSignIn(loginRequest);

            if(jwtAuthenticationResponse.getIsVerified()==0){
                return new ResponseEntity(new ApiResponse(false, "Your account verification in-progress!"),
                        HttpStatus.OK);
            }
            resp= new ResponseEntity<JwtAuthenticationResponse> (jwtAuthenticationResponse,HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            resp = new ResponseEntity(new ApiResponse(false, "Unable to sign in!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDto signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = authService.userSignUp(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
